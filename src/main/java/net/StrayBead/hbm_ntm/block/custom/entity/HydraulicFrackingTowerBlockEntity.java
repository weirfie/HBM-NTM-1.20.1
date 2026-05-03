package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.HydraulicFrackingTowerBlock;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.HydraulicFrackingTowerMenu;
import net.StrayBead.hbm_ntm.screen.OilDerrickMenu;
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


public class HydraulicFrackingTowerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(8) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(600000, 10000) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private static final int ENERGY_REQ = 32;

    private final FluidTank FLUID_TANK = new FluidTank(640000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == ModFluids.CRUDE_OIL.get();
        }
    };

    private final FluidTank FRACKING_TANK = new FluidTank(640000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == ModFluids.FRACKING_SOLUTION.get();
        }
    };

    private final FluidTank OUTPUT_TANK = new FluidTank(64000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }
    };

    public FluidTank getFluidTank() {
        return this.FLUID_TANK;
    }

    public FluidTank getOutputTank() {
        return this.OUTPUT_TANK;
    }

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private final CombinedFluidHandler combinedHandler = new CombinedFluidHandler(FLUID_TANK, FRACKING_TANK, OUTPUT_TANK);
    private final LazyOptional<IFluidHandler> fluidCapability = LazyOptional.of(() -> combinedHandler);
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;

    public HydraulicFrackingTowerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.HYDRAULIC_FRACKING_TOWER_ENTITY.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return 0;
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
    public Component getDisplayName() {
        return Component.literal("Hydraulic Fracking Tower");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        ModMessages.sendToClients(new EnergySyncS2CPacket(this.ENERGY_STORAGE.getEnergyStored(), getBlockPos()));
        ModMessages.sendToClients(new FluidSyncS2CPacket(this.getFluidStack(), worldPosition));
        return new HydraulicFrackingTowerMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        if(cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        if(cap == ForgeCapabilities.FLUID_HANDLER) {
            return fluidCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, HydraulicFrackingTowerBlockEntity pEntity) {
        if (level.isClientSide()) return;

        if (hasEnoughEnergy(pEntity) && pEntity.FRACKING_TANK.getFluidAmount() >= 10) {

            boolean foundBlock = false;
            for (int i = 1; i < pos.getY() + 64; i++) {
                BlockPos checkPos = pos.below(i);
                if (isOilReservoir(level.getBlockState(checkPos))) {
                    foundBlock = true;
                    break;
                }
            }

            if (foundBlock) {
                int filledCrude = pEntity.FLUID_TANK.fill(new FluidStack(ModFluids.CRUDE_OIL.get(), 20), IFluidHandler.FluidAction.SIMULATE);
                int filledGas = pEntity.OUTPUT_TANK.fill(new FluidStack(ModFluids.NATURAL_GAS.get(), 20), IFluidHandler.FluidAction.SIMULATE);

                if (filledCrude == 20 && filledGas == 20) {
                    pEntity.FLUID_TANK.fill(new FluidStack(ModFluids.CRUDE_OIL.get(), 20), IFluidHandler.FluidAction.EXECUTE);
                    pEntity.OUTPUT_TANK.fill(new FluidStack(ModFluids.NATURAL_GAS.get(), 20), IFluidHandler.FluidAction.EXECUTE);

                    pEntity.FRACKING_TANK.drain(10, IFluidHandler.FluidAction.EXECUTE);

                    pEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);

                    setChanged(level, pos, state);
                    level.sendBlockUpdated(pos, state, state, 3);
                }
            }
        }

        if (pEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.INFINITE_BATTERY.get()) {
            pEntity.ENERGY_STORAGE.receiveEnergy(5000, false);
        }

        if (!pEntity.FLUID_TANK.getFluid().isEmpty()) {
            pEntity.pushFluidToNeighbors();
        }
    }

    private void pushFluidToNeighbors() {
        FluidStack stackInTank = FLUID_TANK.getFluid();
        if (stackInTank.isEmpty()) return;

        int pushAmount = 100;

        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = worldPosition.relative(dir);
            BlockEntity neighborBE = level.getBlockEntity(neighborPos);

            if (neighborBE != null) {
                if (neighborBE instanceof FluidBlockEntity ductBE) {
                    if (!ductBE.getAllowedFluid().equals("crude_oil") && !ductBE.getAllowedFluid().equals("natural_gas")) {
                        continue;
                    }
                }
                else if (level.getBlockState(neighborPos).is(ModBlocks.WATER_DUCT.get())) {
                    if (!stackInTank.getFluid().isSame(Fluids.WATER)) {
                        continue;
                    }
                }

                neighborBE.getCapability(ForgeCapabilities.FLUID_HANDLER, dir.getOpposite()).ifPresent(handler -> {
                    FluidStack toPush = new FluidStack(stackInTank.getFluid(), Math.min(stackInTank.getAmount(), pushAmount));
                    int accepted = handler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);
                    if (accepted > 0) {
                        this.FLUID_TANK.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                    }
                });
            }
        }
    }

    private static boolean isOilReservoir(BlockState state) {
        return state.is(ModBlocks.OIL_RESERVOIR.get());
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        lazyFluidHandler = LazyOptional.of(() -> combinedHandler);
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
        nbt.putInt("energy", ENERGY_STORAGE.getEnergyStored());

        nbt.put("CrudeOilTank", FLUID_TANK.writeToNBT(new CompoundTag()));
        nbt.put("OutputTank", OUTPUT_TANK.writeToNBT(new CompoundTag()));
        nbt.put("FrackingTank", FRACKING_TANK.writeToNBT(new CompoundTag()));

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        ENERGY_STORAGE.setEnergy(nbt.getInt("energy"));

        if (nbt.contains("CrudeOilTank"))
            FLUID_TANK.readFromNBT(nbt.getCompound("CrudeOilTank"));
        if (nbt.contains("OutputTank"))
            OUTPUT_TANK.readFromNBT(nbt.getCompound("OutputTank"));
        if (nbt.contains("FrackingTank"))
            FRACKING_TANK.readFromNBT(nbt.getCompound("FrackingTank"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private static void fillTankWithFluid(HydraulicFrackingTowerBlockEntity pEntity, FluidStack stack, ItemStack container) {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        pEntity.itemHandler.extractItem(0, 1, false);
        pEntity.itemHandler.insertItem(0, container, false);
    }

    private static boolean hasEnoughEnergy(HydraulicFrackingTowerBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }
}
