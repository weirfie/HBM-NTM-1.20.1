package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.custom.BedrockOreProcessorBlock;
import net.StrayBead.hbm_ntm.block.custom.ElectricGroundwaterPumpBlock;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ElectricGroundwaterPumpBlockEntity extends BlockEntity {
    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 256) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    private final FluidTank FLUID_TANK = new FluidTank(64000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.WATER;
        }
    };

    private void syncBlock() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public FluidTank getFluidTank() {
        return this.FLUID_TANK;
    }

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    public ElectricGroundwaterPumpBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.ELECTRIC_GROUNDWATER_PUMP.get(), pos, state);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        if (!this.remove && cap == ForgeCapabilities.FLUID_HANDLER)
            return lazyFluidHandler.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("pump.energy", ENERGY_STORAGE.getEnergyStored());
        nbt.putInt("pump.progress", progress);
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.progress = nbt.getInt("pump.progress");
        FLUID_TANK.readFromNBT(nbt);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    public int progress = 0;
    private int maxProgress = 78;
    private boolean isGoingDown = false;

    public static void tick(Level level, BlockPos pos, BlockState state, ElectricGroundwaterPumpBlockEntity pEntity) {
        if (level.isClientSide()) return;

        if (pEntity.ENERGY_STORAGE.getEnergyStored() > 10) {
            if (Mth.nextInt(RandomSource.create(), 1, 70) < 2) {
                level.playSound(null, pos, SoundEvents.PLAYER_SPLASH, SoundSource.NEUTRAL, 2f, 1f);
            }
            if (pEntity.progress < pEntity.maxProgress && !pEntity.isGoingDown) {
                pEntity.ENERGY_STORAGE.extractEnergy(10, false);
                pEntity.progress++;
            } else {
                pEntity.ENERGY_STORAGE.extractEnergy(10, false);
                if (!pEntity.isGoingDown) {
                    pEntity.isGoingDown = true;
                } else {
                    pEntity.progress--;
                    if (pEntity.progress <= 0) {
                        pEntity.isGoingDown = false;
                    }
                }
            }

            if (level.getGameTime() % 10 == 0) {
                pEntity.FLUID_TANK.fill(new FluidStack(Fluids.WATER, 200), IFluidHandler.FluidAction.EXECUTE);
            }
        } else {
            if (pEntity.progress > 0) pEntity.progress--;
        }

        boolean currentlyWorking = pEntity.progress > 0 || pEntity.ENERGY_STORAGE.getEnergyStored() > 10;
        if (state.getValue(ElectricGroundwaterPumpBlock.WORKING) != currentlyWorking) {
            level.setBlock(pos, state.setValue(ElectricGroundwaterPumpBlock.WORKING, currentlyWorking), 3);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        lazyFluidHandler.invalidate();
    }
}
