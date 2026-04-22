package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.BoilerMenu;
import net.StrayBead.hbm_ntm.screen.ElectrolysisMachineMenu;
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
import net.minecraft.world.level.material.Fluids;
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

public class ElectrolysisMachineBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(14) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 256) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    protected final ContainerData data;
    public ElectrolysisMachineBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntites.ELECTROLYSIS_MACHINE.get(), p_155229_, p_155230_);
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

    public final FluidTank FLUID_TANK = new FluidTank(64000) {

        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }
    };

    public final FluidTank OUTPUT_TANK = new FluidTank(64000) {
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

    public final FluidTank OUTPUT_TANK_2 = new FluidTank(64000) {
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

    public void setOutputTank1(FluidStack stack) { this.OUTPUT_TANK.setFluid(stack); }
    public void setOutputTank2(FluidStack stack) { this.OUTPUT_TANK_2.setFluid(stack); }

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Electrolysis Machine");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ElectrolysisMachineMenu(id, inventory, this, this.data);
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

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private final CombinedFluidHandler combinedHandler = new CombinedFluidHandler(FLUID_TANK, OUTPUT_TANK, OUTPUT_TANK_2);
    private final LazyOptional<IFluidHandler> fluidCapability = LazyOptional.of(() -> combinedHandler);

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == ForgeCapabilities.FLUID_HANDLER)
            return fluidCapability.cast();
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if(cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
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
        nbt.put("OutputTank", OUTPUT_TANK.writeToNBT(new CompoundTag()));
        nbt.putInt("electrolysis_machine.energy", ENERGY_STORAGE.getEnergyStored());
        nbt.put("OutputTank2", OUTPUT_TANK_2.writeToNBT(new CompoundTag()));
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        FLUID_TANK.readFromNBT(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        if (nbt.contains("OutputTank"))
            OUTPUT_TANK.readFromNBT(nbt.getCompound("OutputTank"));
        if (nbt.contains("OutputTank"))
            OUTPUT_TANK_2.readFromNBT(nbt.getCompound("OutputTank2"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ElectrolysisMachineBlockEntity pEntity) {
        if (level.isClientSide()) {
            return;
        }

        if (pEntity.itemHandler.getStackInSlot(11).getItem() == ModItems.INFINITE_BATTERY.get()) {
            pEntity.ENERGY_STORAGE.receiveEnergy(10000, false);
        }

        if (hasEnoughEnergy(pEntity)) {
            if (pEntity.FLUID_TANK.getFluidAmount() > 50) {
                if (pEntity.FLUID_TANK.getFluid().getFluid() == Fluids.WATER) {
                    pEntity.ENERGY_STORAGE.extractEnergy(200, false);
                    pEntity.FLUID_TANK.drain(50, IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK.fill(new FluidStack(ModFluids.LIQUID_HYDROGEN.get(), 25), IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK_2.fill(new FluidStack(ModFluids.PETROLEUM_GAS.get(), 25), IFluidHandler.FluidAction.EXECUTE);
                } else if (pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.VITROIL.get()) {
                    pEntity.ENERGY_STORAGE.extractEnergy(200, false);
                    pEntity.FLUID_TANK.drain(50, IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK.fill(new FluidStack(ModFluids.SULFURIC_ACID.get(), 25), IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK_2.fill(new FluidStack(ModFluids.CHLORINE_GAS.get(), 25), IFluidHandler.FluidAction.EXECUTE);
                    pEntity.itemHandler.insertItem(8, new ItemStack(ModItems.IRON_POWDER.get()), false);
                    pEntity.itemHandler.insertItem(9, new ItemStack(ModItems.DROP_OF_MERCURY.get()), false);
                }
            }
        }
    }

    private static boolean hasEnoughEnergy(ElectrolysisMachineBlockEntity pEntity) {
        return (pEntity.ENERGY_STORAGE.getEnergyStored() > 200);
    }
}
