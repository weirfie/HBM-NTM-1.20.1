package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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

public class CatalyticCrackingTowerBlockEntity extends BlockEntity {
    public CatalyticCrackingTowerBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntites.CATALYTIC_CRACKING_TOWER.get(), p_155229_, p_155230_);
    }

    private final FluidTank FLUID_TANK = new FluidTank(64000) {
        @Override
        public boolean isFluidValid(FluidStack stack) {
            String name = stack.getFluid().getFluidType().toString();
            return !name.contains("steam");
        }

        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }
    };

    private final FluidTank OUTPUT_TANK = new FluidTank(64000) {
        @Override
        public boolean isFluidValid(FluidStack stack) {
            String name = stack.getFluid().getFluidType().toString();
            return !name.contains("steam");
        }

        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }
    };

    private final FluidTank OUTPUT_TANK_2 = new FluidTank(64000) {
        @Override
        public boolean isFluidValid(FluidStack stack) {
            String name = stack.getFluid().getFluidType().toString();
            return !name.contains("steam");
        }

        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }
    };

    private final FluidTank steamTank = new FluidTank(64000) {
        @Override
        protected void onContentsChanged() {
            syncBlock();
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            String name = stack.getFluid().getFluidType().toString();
            return name.contains("steam");
        }
    };

    private void syncBlock() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public FluidTank getOutputTank1() {
        return this.OUTPUT_TANK;
    }

    public FluidTank getOutputTank2() {
        return this.OUTPUT_TANK_2;
    }

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private static class CombinedFluidHandler implements IFluidHandler {
        private final FluidTank[] tanks;

        public CombinedFluidHandler(FluidTank... tanks) {
            this.tanks = tanks;
        }

        @Override public int getTanks() { return tanks.length; }
        @Override public FluidStack getFluidInTank(int tank) { return tanks[tank].getFluid(); }
        @Override public int getTankCapacity(int tank) { return tanks[tank].getCapacity(); }
        @Override public boolean isFluidValid(int tank, FluidStack stack) { return tanks[tank].isFluidValid(stack); }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            for (FluidTank tank : tanks) {
                if (tank.isFluidValid(resource)) return tank.fill(resource, action);
            }
            return 0;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            for (FluidTank tank : tanks) {
                if (tank.isFluidValid(resource)) return tank.drain(resource, action);
            }
            return FluidStack.EMPTY;
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            for (FluidTank tank : tanks) {
                if (!tank.getFluid().isEmpty()) return tank.drain(maxDrain, action);
            }
            return FluidStack.EMPTY;
        }
    }

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private final CombinedFluidHandler combinedHandler = new CombinedFluidHandler(FLUID_TANK, steamTank, OUTPUT_TANK, OUTPUT_TANK_2);
    private final LazyOptional<IFluidHandler> fluidCapability = LazyOptional.of(() -> combinedHandler);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == ForgeCapabilities.FLUID_HANDLER)
            return fluidCapability.cast();

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("SteamTank", steamTank.writeToNBT(new CompoundTag()));
        nbt.put("OutputTank", OUTPUT_TANK.writeToNBT(new CompoundTag()));
        nbt.put("OutputTank2", OUTPUT_TANK_2.writeToNBT(new CompoundTag()));
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        FLUID_TANK.readFromNBT(nbt);
        if (nbt.contains("SteamTank"))
            steamTank.readFromNBT(nbt.getCompound("SteamTank"));
        if (nbt.contains("OutputTank"))
            OUTPUT_TANK.readFromNBT(nbt.getCompound("OutputTank"));
        if (nbt.contains("OutputTank"))
            OUTPUT_TANK_2.readFromNBT(nbt.getCompound("OutputTank2"));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CatalyticCrackingTowerBlockEntity pEntity) {
        if (level.isClientSide()) {
            return;
        }

        if (hasEnoughSteam(pEntity)) {
            if (pEntity.FLUID_TANK.getFluidAmount() > 100) {
                if (pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.CRUDE_OIL.get()) {
                    pEntity.FLUID_TANK.drain(100, IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK.fill(new FluidStack(ModFluids.CRACKED_OIL.get(), 80), IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK_2.fill(new FluidStack(ModFluids.PETROLEUM_GAS.get(), 20), IFluidHandler.FluidAction.EXECUTE);
                } else if (pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.BITUMEN.get()) {
                    pEntity.FLUID_TANK.drain(100, IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK.fill(new FluidStack(ModFluids.CRUDE_OIL.get(), 80), IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK_2.fill(new FluidStack(ModFluids.AROMATIC_HYDROCARBONS.get(), 20), IFluidHandler.FluidAction.EXECUTE);
                } else if (pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.NATURAL_GAS.get()) {
                    pEntity.FLUID_TANK.drain(100, IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK.fill(new FluidStack(ModFluids.PETROLEUM_GAS.get(), 80), IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK_2.fill(new FluidStack(ModFluids.UNSATURATED_HYDROCARBONS.get(), 20), IFluidHandler.FluidAction.EXECUTE);
                } else if (pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.INDUSTRIAL_OIL.get()) {
                    pEntity.FLUID_TANK.drain(100, IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK.fill(new FluidStack(ModFluids.NAPHTHA.get(), 80), IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK_2.fill(new FluidStack(ModFluids.PETROLEUM_GAS.get(), 20), IFluidHandler.FluidAction.EXECUTE);
                } else if (pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.HEAVY_HEATING_OIL.get()) {
                    pEntity.FLUID_TANK.drain(100, IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK.fill(new FluidStack(ModFluids.HEATING_OIL.get(), 80), IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK_2.fill(new FluidStack(ModFluids.REFORMATE_GAS.get(), 20), IFluidHandler.FluidAction.EXECUTE);
                } else if (pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.REFORMATE.get()) {
                    pEntity.FLUID_TANK.drain(100, IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK.fill(new FluidStack(ModFluids.UNSATURATED_HYDROCARBONS.get(), 80), IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK_2.fill(new FluidStack(ModFluids.REFORMATE_GAS.get(), 20), IFluidHandler.FluidAction.EXECUTE);
                }
            }
        }
    }

    private static boolean hasEnoughSteam(CatalyticCrackingTowerBlockEntity pEntity) {
        return (pEntity.steamTank.getFluidAmount() > 200);
    }
}
