package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.custom.CompressorBlock;
import net.StrayBead.hbm_ntm.fluid.GenericFluid;
import net.StrayBead.hbm_ntm.fluid.GenericFluidType;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.CompressorMenu;
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
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CompressorBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
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

    public final ContainerData data;
    private int selectedPressure = 0;

    public CompressorBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntites.COMPRESSOR.get(), p_155229_, p_155230_);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                if (index == 0) return selectedPressure;
                return 0;
            }

            @Override
            public void set(int index, int value) {
                if (index == 0) {
                    selectedPressure = value;
                    setChanged();
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    public FluidTank getOutputTank() { return this.OUTPUT_TANK_1; }

    public void setOutputTank(FluidStack stack) { this.OUTPUT_TANK_1.setFluid(stack); }

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
    private final CombinedFluidHandler combinedHandler = new CombinedFluidHandler(FLUID_TANK, OUTPUT_TANK_1);
    private final LazyOptional<IFluidHandler> fluidCapability = LazyOptional.of(() -> combinedHandler);

    @Override
    public Component getDisplayName() {
        return Component.literal("Compressor");
    }

    private static class CombinedFluidHandler implements IFluidHandler {
        private final FluidTank inputTank;
        private final FluidTank outputTank;

        public CombinedFluidHandler(FluidTank input, FluidTank output) {
            this.inputTank = input;
            this.outputTank = output;
        }

        @Override public int getTanks() { return 2; }
        @Override public FluidStack getFluidInTank(int tank) { return tank == 0 ? inputTank.getFluid() : outputTank.getFluid(); }
        @Override public int getTankCapacity(int tank) { return tank == 0 ? inputTank.getCapacity() : outputTank.getCapacity(); }
        @Override public boolean isFluidValid(int tank, FluidStack stack) { return tank == 0 && inputTank.isFluidValid(stack); }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return inputTank.fill(resource, action);
        }

        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            // STRICT: Only allow draining from the output tank.
            return outputTank.drain(resource, action);
        }

        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            return outputTank.drain(maxDrain, action);
        }
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new CompressorMenu(id, inventory, this, this.data);
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

        if (!this.remove && cap == ForgeCapabilities.FLUID_HANDLER) {
            if (side == null) {
                return fluidCapability.cast();
            }

            Direction facing = getBlockState().getValue(CompressorBlock.FACING);

            Direction leftSide = facing.getCounterClockWise();
            Direction rightSide = facing.getClockWise();

            if (side == leftSide) {
                return lazyFluidHandler.cast();
            } else if (side == rightSide) {
                return LazyOptional.of(() -> OUTPUT_TANK_1).cast();
            }

            return LazyOptional.empty();
        }

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == null) return lazyItemHandler.cast();
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
        nbt = FLUID_TANK.writeToNBT(nbt);

        nbt.putInt("Pressure", selectedPressure);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        FLUID_TANK.readFromNBT(nbt);
        if (nbt.contains("OutputTank1"))
            OUTPUT_TANK_1.readFromNBT(nbt.getCompound("OutputTank1"));

        selectedPressure = nbt.getInt("Pressure");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CompressorBlockEntity pEntity) {
        if (level.isClientSide()) return;

        if (pEntity.ENERGY_STORAGE.getEnergyStored() < 20) return;

        FluidStack input = pEntity.FLUID_TANK.getFluid();
        if (input.isEmpty() || input.getAmount() < 50) return;

        int targetPressure = pEntity.selectedPressure;
        int currentPressure = getPressure(input);

        if (targetPressure <= currentPressure) return;

        if (input.getFluid().getFluidType() instanceof GenericFluidType type) {

            FluidStack outputStack = GenericFluidType.getPressurizedStack(input.getFluid(), 50, targetPressure);

            int filled = pEntity.OUTPUT_TANK_1.fill(outputStack, IFluidHandler.FluidAction.EXECUTE);

            if (filled > 0) {
                pEntity.FLUID_TANK.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                pEntity.ENERGY_STORAGE.extractEnergy(20, false);
                pEntity.syncBlock();
            }
        }
    }

    public static int getPressure(FluidStack stack) {
        if (!stack.hasTag()) return 0;
        return stack.getTag().getInt("Pressure");
    }

    public static void setPressure(FluidStack stack, int pressure) {
        stack.getOrCreateTag().putInt("Pressure", pressure);
    }

    public void setPressure(int pressure) {
        this.selectedPressure = pressure;
        setChanged();

        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        fluidCapability.invalidate();
    }
}
