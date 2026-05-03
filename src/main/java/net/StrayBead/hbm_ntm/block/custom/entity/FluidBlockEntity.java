package net.StrayBead.hbm_ntm.block.custom.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class FluidBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
    private int filterColor = 0xFFFFFF;
    private String allowedFluid = "";
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

    public FluidBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntites.FLUID_BLOCK_ENTITY.get(), position, state);
    }

    public void setFilterColor(int color) {
        this.filterColor = color;
        this.setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public void onDataPacket(net.minecraft.network.Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());

        if (level != null && level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public String getAllowedFluid() {
        return this.allowedFluid;
    }

    public void setAllowedFluid(String fluidName) {
        this.allowedFluid = fluidName;
        this.setChanged();
    }

    public String getFluidFilter() {
        return this.allowedFluid == null ? "" : this.allowedFluid;
    }

    public void setFilterAndFluid(int color, String fluidName) {
        this.setFilterColor(color);
        this.allowedFluid = fluidName;
    }

    public int getFilterColor() {
        return this.filterColor;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (!this.tryLoadLootTable(compound))
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, this.stacks);
        if (compound.get("fluidTank") instanceof CompoundTag compoundTag)
            fluidTank.readFromNBT(compoundTag);
        if (compound.contains("filterColor")) {
            this.filterColor = compound.getInt("filterColor");
        }
        this.allowedFluid = compound.getString("allowedFluid");
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks);
        }
        compound.put("fluidTank", fluidTank.writeToNBT(new CompoundTag()));
        compound.putString("allowedFluid", this.allowedFluid);
        compound.putInt("filterColor", this.filterColor);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public int getContainerSize() {
        return stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks)
            if (!itemstack.isEmpty())
                return false;
        return true;
    }

    @Override
    public Component getDefaultName() {
        return Component.literal("fluidblock");
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows(id, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Fluidblock");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }
    public final FluidTank fluidTank = new FluidTank(10000) {
        @Override
        public boolean isFluidValid(FluidStack stack) {
            if (allowedFluid == null || allowedFluid.isEmpty()) return true;

            String incomingFullName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).toString();

            String incomingPath = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stack.getFluid()).getPath();

            return incomingFullName.equals(allowedFluid) || incomingPath.equals(allowedFluid);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (!isFluidValid(resource)) return 0;
            return super.fill(resource, action);
        }

        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
            }
        }
    };

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER)
            return handlers[facing.ordinal()].cast();
        if (!this.remove && capability == ForgeCapabilities.FLUID_HANDLER)
            return LazyOptional.of(() -> fluidTank).cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : handlers)
            handler.invalidate();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FluidBlockEntity entity) {
        if (level.isClientSide) return;

        if (entity.fluidTank.getFluidAmount() > 0) {
            entity.distributeFluid();
        }
    }

    private void distributeFluid() {
        int transferSpeed = 1000;

        for (Direction direction : Direction.values()) {
            if (fluidTank.getFluidAmount() <= 0) break;

            BlockPos neighborPos = worldPosition.relative(direction);
            BlockEntity neighborBE = level.getBlockEntity(neighborPos);

            if (neighborBE != null) {
                neighborBE.getCapability(ForgeCapabilities.FLUID_HANDLER, direction.getOpposite()).ifPresent(handler -> {
                    if (neighborBE instanceof FluidBlockEntity neighborDuct) {
                        if (!neighborDuct.getAllowedFluid().equals(this.getAllowedFluid())) {
                            return;
                        }
                    }

                    FluidStack toDrain = new FluidStack(fluidTank.getFluid(),
                            Math.min(fluidTank.getFluidAmount(), transferSpeed));

                    int filled = handler.fill(toDrain, IFluidHandler.FluidAction.EXECUTE);
                    if (filled > 0) {
                        fluidTank.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                    }
                });
            }
        }
    }
}
