package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.CatalyticReformerMenu;
import net.StrayBead.hbm_ntm.screen.VacuumRefineryMenu;
import net.StrayBead.hbm_ntm.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CatalyticReformerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(11) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return super.isItemValid(slot, stack);
        }
    };

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 256) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private static final int ENERGY_REQ = 32;

    private final FluidTank FLUID_TANK = new FluidTank(64000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }
    };

    private final FluidTank OUTPUT_TANK_1 = createOutputTank(40000, 1);
    private final FluidTank OUTPUT_TANK_2 = createOutputTank(40000, 2);
    private final FluidTank OUTPUT_TANK_3 = createOutputTank(40000, 3);

    private FluidTank createOutputTank(int capacity, int tankID) {
        return new FluidTank(capacity) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                if (level != null && !level.isClientSide()) {
                    ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition, tankID));
                }
            }
        };
    }

    protected final ContainerData data;

    public CatalyticReformerBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntites.CATALYTIC_REFORMER.get(), p_155229_, p_155230_);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                }
            }

            @Override
            public int getCount() {
                return 0;
            }
        };
    }

    public FluidTank getTank1() { return this.OUTPUT_TANK_1; }
    public FluidTank getTank2() { return this.OUTPUT_TANK_2; }
    public FluidTank getTank3() { return this.OUTPUT_TANK_3; }

    public void setTank1(FluidStack stack) { this.OUTPUT_TANK_1.setFluid(stack); }
    public void setTank2(FluidStack stack) { this.OUTPUT_TANK_2.setFluid(stack); }
    public void setTank3(FluidStack stack) { this.OUTPUT_TANK_3.setFluid(stack); }

    private void syncBlock() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private final CombinedFluidHandler combinedHandler = new CombinedFluidHandler(FLUID_TANK, OUTPUT_TANK_1, OUTPUT_TANK_2, OUTPUT_TANK_3);
    private final LazyOptional<IFluidHandler> fluidCapability = LazyOptional.of(() -> combinedHandler);

    @Override
    public Component getDisplayName() {
        return Component.literal("Catalytic Reformer");
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

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new CatalyticReformerMenu(id, inventory, this, this.data);
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
            return fluidCapability.cast();

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) {
                return lazyItemHandler.cast();
            }
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        lazyFluidHandler = LazyOptional.of(() -> FLUID_TANK);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
        lazyFluidHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.put("OutputTank1", OUTPUT_TANK_1.writeToNBT(new CompoundTag()));
        nbt.put("OutputTank2", OUTPUT_TANK_2.writeToNBT(new CompoundTag()));
        nbt.put("OutputTank3", OUTPUT_TANK_3.writeToNBT(new CompoundTag()));
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        FLUID_TANK.readFromNBT(nbt);
        if (nbt.contains("OutputTank1"))
            OUTPUT_TANK_1.readFromNBT(nbt.getCompound("OutputTank1"));
        if (nbt.contains("OutputTank2"))
            OUTPUT_TANK_2.readFromNBT(nbt.getCompound("OutputTank2"));
        if (nbt.contains("OutputTank3"))
            OUTPUT_TANK_3.readFromNBT(nbt.getCompound("OutputTank3"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CatalyticReformerBlockEntity pEntity) {
        if (level.isClientSide()) {
            return;
        }

        if (pEntity.ENERGY_STORAGE.getEnergyStored() > 20) {
            if (pEntity.FLUID_TANK.getFluidAmount() > 50 && pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.NAPHTHA.get()) {
                pEntity.ENERGY_STORAGE.extractEnergy(20, false);
                pEntity.FLUID_TANK.drain(50, IFluidHandler.FluidAction.EXECUTE);
                pEntity.OUTPUT_TANK_1.fill(new FluidStack(ModFluids.REFORMATE.get(), 20), IFluidHandler.FluidAction.EXECUTE);
                pEntity.OUTPUT_TANK_2.fill(new FluidStack(ModFluids.PETROLEUM_GAS.get(), 15), IFluidHandler.FluidAction.EXECUTE);
                pEntity.OUTPUT_TANK_3.fill(new FluidStack(ModFluids.LIQUID_HYDROGEN.get(), 10), IFluidHandler.FluidAction.EXECUTE);

                ModMessages.sendToClients(new FluidSyncS2CPacket(pEntity.FLUID_TANK.getFluid(), pos));
                pEntity.syncBlock();
            } else if (pEntity.FLUID_TANK.getFluidAmount() > 50 && pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.SOUR_GAS.get()) {
                pEntity.ENERGY_STORAGE.extractEnergy(20, false);
                pEntity.FLUID_TANK.drain(50, IFluidHandler.FluidAction.EXECUTE);
                pEntity.OUTPUT_TANK_1.fill(new FluidStack(ModFluids.SULFURIC_ACID.get(), 20), IFluidHandler.FluidAction.EXECUTE);
                pEntity.OUTPUT_TANK_2.fill(new FluidStack(ModFluids.PETROLEUM_GAS.get(), 15), IFluidHandler.FluidAction.EXECUTE);
                pEntity.OUTPUT_TANK_3.fill(new FluidStack(ModFluids.LIQUID_HYDROGEN.get(), 10), IFluidHandler.FluidAction.EXECUTE);

                ModMessages.sendToClients(new FluidSyncS2CPacket(pEntity.FLUID_TANK.getFluid(), pos));
                pEntity.syncBlock();
            } else if (pEntity.FLUID_TANK.getFluidAmount() > 50 && pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.HEATING_OIL.get()) {
                pEntity.ENERGY_STORAGE.extractEnergy(20, false);
                pEntity.FLUID_TANK.drain(50, IFluidHandler.FluidAction.EXECUTE);
                pEntity.OUTPUT_TANK_1.fill(new FluidStack(ModFluids.NAPHTHA.get(), 20), IFluidHandler.FluidAction.EXECUTE);
                pEntity.OUTPUT_TANK_2.fill(new FluidStack(ModFluids.PETROLEUM_GAS.get(), 15), IFluidHandler.FluidAction.EXECUTE);
                pEntity.OUTPUT_TANK_3.fill(new FluidStack(ModFluids.LIQUID_HYDROGEN.get(), 10), IFluidHandler.FluidAction.EXECUTE);

                ModMessages.sendToClients(new FluidSyncS2CPacket(pEntity.FLUID_TANK.getFluid(), pos));
                pEntity.syncBlock();
            }
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        fluidCapability.invalidate();
    }
}
