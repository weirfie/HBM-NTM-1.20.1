package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ShallowFoundryBasinBlockEntity extends BlockEntity {
    private int coolingTimer = 0;
    private static final int MAX_COOLING_TIME = 100;
    private ItemStack currentMold = ItemStack.EMPTY;

    public ShallowFoundryBasinBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntites.SHALLOW_FOUNDRY_BASIN.get(), p_155229_, p_155230_);
    }

    private final FluidTank FLUID_TANK = new FluidTank(10000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (level != null && !level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
            coolingTimer = 0;
        }
    };

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag nbt = super.getUpdateTag();
        saveAdditional(nbt);
        return nbt;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private void syncBlock() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public ItemStack getMold() {
        return currentMold;
    }

    public void setMold(ItemStack mold) {
        this.currentMold = mold;
        syncBlock();
    }

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable net.minecraft.core.Direction side) {
        if (!this.remove && cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onDataPacket(net.minecraft.network.Connection net, net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null) {
            this.load(tag);
        }
    }

    @Override
    public void onLoad() {
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("FluidDeclaration", FLUID_TANK.writeToNBT(new CompoundTag()));
        nbt.putInt("CoolingTimer", coolingTimer);
        nbt.put("BasinMold", currentMold.serializeNBT());
        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        if (nbt.contains("FluidDeclaration")) {
            FLUID_TANK.readFromNBT(nbt.getCompound("FluidDeclaration"));
        }
        if (nbt.contains("BasinMold")) {
            this.currentMold = ItemStack.of(nbt.getCompound("BasinMold"));
        }
        this.coolingTimer = nbt.getInt("CoolingTimer");
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ShallowFoundryBasinBlockEntity pEntity) {
        if (level.isClientSide()) return;

        FluidStack tankFluid = pEntity.FLUID_TANK.getFluid();
        ItemStack currentMold = pEntity.getMold();

        if (tankFluid.isEmpty() || currentMold.isEmpty()) {
            pEntity.coolingTimer = 0;
            return;
        }

        java.util.Optional<net.StrayBead.hbm_ntm.recipe.BasinCastingRecipe> match = level.getRecipeManager()
                .getAllRecipesFor(net.StrayBead.hbm_ntm.recipe.BasinCastingRecipe.Type.INSTANCE)
                .stream()
                .filter(recipe -> recipe.matchesRequirements(tankFluid, currentMold))
                .findFirst();

        if (match.isPresent()) {
            net.StrayBead.hbm_ntm.recipe.BasinCastingRecipe recipe = match.get();

            if (tankFluid.getAmount() >= recipe.getFluidAmount()) {
                pEntity.coolingTimer++;
                if (pEntity.coolingTimer >= MAX_COOLING_TIME) {
                    pEntity.finishCasting(level, pos, recipe);
                }
            } else {
                pEntity.coolingTimer = 0;
            }
        } else {
            pEntity.coolingTimer = 0;
        }
    }

    private void finishCasting(Level level, BlockPos pos, net.StrayBead.hbm_ntm.recipe.BasinCastingRecipe recipe) {
        int amountInTank = this.FLUID_TANK.getFluidAmount();
        int recipeCost = recipe.getFluidAmount();

        int units = amountInTank / recipeCost;
        int finalCount = units * recipe.getResultItem(level.registryAccess()).getCount();

        if (finalCount > 64) finalCount = 64;

        if (finalCount > 0) {
            ItemStack outputStack = new ItemStack(recipe.getResultItem(level.registryAccess()).getItem(), finalCount);
            ItemEntity itemEntity = new ItemEntity(level,
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    outputStack);
            level.addFreshEntity(itemEntity);
        }

        this.FLUID_TANK.setFluid(FluidStack.EMPTY);
        this.coolingTimer = 0;

        setChanged();
        level.sendBlockUpdated(pos, getBlockState(), getBlockState(), 3);
    }
}
