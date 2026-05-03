package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.CatalyticReformerMenu;
import net.StrayBead.hbm_ntm.screen.HydrotreaterMenu;
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

public class HydrotreaterBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(9) {
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
        @Override
        public boolean isFluidValid(FluidStack stack) {
            return !stack.getFluid().getFluidType().toString().contains("liquid_hydrogen");
        }
    };

    private final FluidTank INPUT_TANK = new FluidTank(64000) {
        @Override
        protected void onContentsChanged() {
            syncBlock();
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid().getFluidType().toString().contains("liquid_hydrogen");
        }
    };

    private final FluidTank OUTPUT_TANK_1 = createOutputTank(40000, 1);
    private final FluidTank OUTPUT_TANK_2 = createOutputTank(40000, 2);

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

    public HydrotreaterBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntites.HYDROTREATER.get(), p_155229_, p_155230_);
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

    public void setTank1(FluidStack stack) { this.OUTPUT_TANK_1.setFluid(stack); }
    public void setTank2(FluidStack stack) { this.OUTPUT_TANK_2.setFluid(stack); }

    private void syncBlock() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public void setInputFluid(FluidStack stack) {
        this.INPUT_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private final CombinedFluidHandler combinedHandler = new CombinedFluidHandler(FLUID_TANK, INPUT_TANK, OUTPUT_TANK_1, OUTPUT_TANK_2);
    private final LazyOptional<IFluidHandler> fluidCapability = LazyOptional.of(() -> combinedHandler);

    @Override
    public Component getDisplayName() {
        return Component.literal("Hydrotreater");
    }

    public FluidStack getInputStack() {
        return this.INPUT_TANK.getFluid();
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
        return new HydrotreaterMenu(id, inventory, this, this.data);
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
        nbt.put("InputTank", INPUT_TANK.writeToNBT(new CompoundTag()));
        nbt.put("OutputTank1", OUTPUT_TANK_1.writeToNBT(new CompoundTag()));
        nbt.put("OutputTank2", OUTPUT_TANK_2.writeToNBT(new CompoundTag()));
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        FLUID_TANK.readFromNBT(nbt);
        if (nbt.contains("InputTank"))
            INPUT_TANK.readFromNBT(nbt.getCompound("InputTank"));
        if (nbt.contains("OutputTank1"))
            OUTPUT_TANK_1.readFromNBT(nbt.getCompound("OutputTank1"));
        if (nbt.contains("OutputTank2"))
            OUTPUT_TANK_2.readFromNBT(nbt.getCompound("OutputTank2"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, HydrotreaterBlockEntity pEntity) {
        if (level.isClientSide()) {
            return;
        }

        if (pEntity.ENERGY_STORAGE.getEnergyStored() > 20) {
            if (pEntity.FLUID_TANK.getFluidAmount() > 25 && pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.CRUDE_OIL.get()) {
                if (pEntity.INPUT_TANK.getFluidAmount() > 25 && pEntity.INPUT_TANK.getFluid().getFluid() == ModFluids.LIQUID_HYDROGEN.get()) {
                    pEntity.ENERGY_STORAGE.extractEnergy(20, false);
                    pEntity.FLUID_TANK.drain(25, IFluidHandler.FluidAction.EXECUTE);
                    pEntity.INPUT_TANK.drain(25, IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK_1.fill(new FluidStack(ModFluids.DESULFURIZED_CRUDE_OIL.get(), 25), IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK_2.fill(new FluidStack(ModFluids.SOUR_GAS.get(), 25), IFluidHandler.FluidAction.EXECUTE);
                }
            }
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        fluidCapability.invalidate();
    }
}
