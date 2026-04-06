package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.OilDerrickMenu;
import net.StrayBead.hbm_ntm.screen.OilRefineryMenu;
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

public class OilRefineryBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(12) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
                case 1 -> true;
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
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

    private final FluidTank FLUID_TANK = new FluidTank(64000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == Fluids.WATER || stack.getFluid() == ModFluids.HOT_CRUDE_OIL.get();
        }
    };

    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    private final FluidTank heavyOilTank = createOutputTank(40000, 1);
    private final FluidTank naphthaTank = createOutputTank(40000, 2);
    private final FluidTank lightOilTank = createOutputTank(40000, 3);
    private final FluidTank petroleumGasTank = createOutputTank(40000, 4);

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

    public FluidTank getHeavyOilTank() { return this.heavyOilTank; }
    public FluidTank getNaphthaTank() { return this.naphthaTank; }
    public FluidTank getLightOilTank() { return this.lightOilTank; }
    public FluidTank getPetroleumGasTank() { return this.petroleumGasTank; }

    public void setHeavyOil(FluidStack stack) { this.heavyOilTank.setFluid(stack); }
    public void setNaphtha(FluidStack stack) { this.naphthaTank.setFluid(stack); }
    public void setLightOil(FluidStack stack) { this.lightOilTank.setFluid(stack); }
    public void setPetroleumGas(FluidStack stack) { this.petroleumGasTank.setFluid(stack); }

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private final CombinedFluidHandler combinedFluidHandler = new CombinedFluidHandler(
            FLUID_TANK, heavyOilTank, naphthaTank, lightOilTank, petroleumGasTank
    );
    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;

    public OilRefineryBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.OIL_REFINERY.get(), pos, state);
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

    @Override
    public Component getDisplayName() {
        return Component.literal("Oil Refinery");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        ModMessages.sendToClients(new EnergySyncS2CPacket(this.ENERGY_STORAGE.getEnergyStored(), getBlockPos()));
        ModMessages.sendToClients(new FluidSyncS2CPacket(this.getFluidStack(), worldPosition));
        return new OilRefineryMenu(id, inventory, this, this.data);
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
            return lazyFluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, OilRefineryBlockEntity pEntity) {
        if (level.isClientSide()) {
            return;
        }

        pEntity.pushFluidToNeighbors();

        if (pEntity.FLUID_TANK.getFluidAmount() > 40 && hasEnoughEnergy(pEntity)) {
            if (pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.HOT_CRUDE_OIL.get()) {
                pEntity.FLUID_TANK.drain(40, IFluidHandler.FluidAction.EXECUTE);
                pEntity.petroleumGasTank.fill(new FluidStack(ModFluids.PETROLEUM_GAS.get(), 10), IFluidHandler.FluidAction.EXECUTE);
                pEntity.lightOilTank.fill(new FluidStack(ModFluids.LIGHT_OIL.get(), 10), IFluidHandler.FluidAction.EXECUTE);
                pEntity.naphthaTank.fill(new FluidStack(ModFluids.NAPHTHA.get(), 10), IFluidHandler.FluidAction.EXECUTE);
                pEntity.heavyOilTank.fill(new FluidStack(ModFluids.HEAVY_OIL.get(), 10), IFluidHandler.FluidAction.EXECUTE);
            }
        }
    }

    private void pushFluidToNeighbors() {
        FluidTank[] outputTanks = {heavyOilTank, naphthaTank, lightOilTank, petroleumGasTank};
        int pushAmount = 5;

        for (FluidTank tank : outputTanks) {
            FluidStack stackInTank = tank.getFluid();
            if (stackInTank.isEmpty()) continue;

            String currentFluidName = net.minecraftforge.registries.ForgeRegistries.FLUIDS.getKey(stackInTank.getFluid()).getPath();

            for (Direction dir : Direction.values()) {
                BlockPos neighborPos = worldPosition.relative(dir);
                BlockEntity neighborBE = level.getBlockEntity(neighborPos);

                if (neighborBE != null) {
                    if (neighborBE instanceof FluidBlockEntity duct) {
                        String filter = duct.getAllowedFluid();

                        if (filter.isEmpty() || !filter.equals(currentFluidName)) {
                            continue;
                        }
                    } else {
                    }

                    neighborBE.getCapability(ForgeCapabilities.FLUID_HANDLER, dir.getOpposite()).ifPresent(handler -> {
                        FluidStack toPush = new FluidStack(stackInTank.getFluid(), Math.min(stackInTank.getAmount(), pushAmount));
                        int filled = handler.fill(toPush, IFluidHandler.FluidAction.EXECUTE);

                        if (filled > 0) {
                            tank.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                        }
                    });
                }
            }
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
        lazyFluidHandler = LazyOptional.of(() -> combinedFluidHandler);
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
        nbt.putInt("oil_refinery.energy", ENERGY_STORAGE.getEnergyStored());
        nbt.put("input_tank", FLUID_TANK.writeToNBT(new CompoundTag()));
        nbt.put("heavy_tank", heavyOilTank.writeToNBT(new CompoundTag()));
        nbt.put("naphtha_tank", naphthaTank.writeToNBT(new CompoundTag()));
        nbt.put("light_tank", lightOilTank.writeToNBT(new CompoundTag()));
        nbt.put("gas_tank", petroleumGasTank.writeToNBT(new CompoundTag()));

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        ENERGY_STORAGE.setEnergy(nbt.getInt("oil_refinery.energy"));
        FLUID_TANK.readFromNBT(nbt.getCompound("input_tank"));
        heavyOilTank.readFromNBT(nbt.getCompound("heavy_tank"));
        naphthaTank.readFromNBT(nbt.getCompound("naphtha_tank"));
        lightOilTank.readFromNBT(nbt.getCompound("light_tank"));
        petroleumGasTank.readFromNBT(nbt.getCompound("gas_tank"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    private static void fillTankWithFluid(OilRefineryBlockEntity pEntity, FluidStack stack, ItemStack container) {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        pEntity.itemHandler.extractItem(0, 1, false);
        pEntity.itemHandler.insertItem(0, container, false);
    }

    private static boolean hasEnoughEnergy(OilRefineryBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
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
            if (tanks[0].isFluidValid(resource)) return tanks[0].fill(resource, action);
            return 0;
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            for (FluidTank tank : tanks) {
                if (tank.getFluid().isFluidEqual(resource)) return tank.drain(resource, action);
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
}
