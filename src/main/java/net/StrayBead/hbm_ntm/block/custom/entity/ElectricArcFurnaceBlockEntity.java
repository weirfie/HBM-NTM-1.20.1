package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.custom.BoilerBlock;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.recipe.ModRecipes;
import net.StrayBead.hbm_ntm.screen.ElectricArcFurnaceMenu;
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
import net.minecraft.world.item.Items;
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

import java.util.Map;

public class ElectricArcFurnaceBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(30) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            if (slot >= 1 && slot <= 20) {
                return false;
            }

            return super.isItemValid(slot, stack);
        }
    };

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 10000) {
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
    };

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private final Map<Direction, LazyOptional<WrappedHandler>> directionWrappedHandlerMap =
            Map.of(Direction.DOWN, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2, (i, s) -> false)),
                    Direction.NORTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 1,
                            (index, stack) -> itemHandler.isItemValid(1, stack))),
                    Direction.SOUTH, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 2, (i, s) -> false)),
                    Direction.EAST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (i) -> i == 1,
                            (index, stack) -> itemHandler.isItemValid(1, stack))),
                    Direction.WEST, LazyOptional.of(() -> new WrappedHandler(itemHandler, (index) -> index == 0 || index == 1,
                            (index, stack) -> itemHandler.isItemValid(0, stack) || itemHandler.isItemValid(1, stack))));

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public ElectricArcFurnaceBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.ELECTRIC_ARC_FURNACE.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> ElectricArcFurnaceBlockEntity.this.progress;
                    case 1 -> ElectricArcFurnaceBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> ElectricArcFurnaceBlockEntity.this.progress = value;
                    case 1 -> ElectricArcFurnaceBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Electric Arc Furnace");
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

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ElectricArcFurnaceMenu(id, inventory, this, this.data);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }
        if (!this.remove && cap == ForgeCapabilities.FLUID_HANDLER)
            return lazyFluidHandler.cast();

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }

            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(BoilerBlock.FACING);

                if(side == Direction.UP || side == Direction.DOWN) {
                    return directionWrappedHandlerMap.get(side).cast();
                }

                return switch (localDir) {
                    default -> directionWrappedHandlerMap.get(side.getOpposite()).cast();
                    case EAST -> directionWrappedHandlerMap.get(side.getClockWise()).cast();
                    case SOUTH -> directionWrappedHandlerMap.get(side).cast();
                    case WEST -> directionWrappedHandlerMap.get(side.getCounterClockWise()).cast();
                };
            }
        }

        if (cap == ForgeCapabilities.FLUID_HANDLER) {
            return lazyFluidHandler.cast();
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
        nbt.putInt("furnace.progress", this.progress);
        nbt.putInt("furnace.energy", ENERGY_STORAGE.getEnergyStored());
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("furnace.progress");
        FLUID_TANK.readFromNBT(nbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ElectricArcFurnaceBlockEntity pEntity) {
        if (level.isClientSide()) return;

        if (pEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.INFINITE_BATTERY.get()) {
            pEntity.ENERGY_STORAGE.receiveEnergy(5000, false);
        }

        boolean hasElectrodes = hasValidElectrodes(pEntity);
        boolean hasEnergy = pEntity.ENERGY_STORAGE.getEnergyStored() >= 50;

        if (hasElectrodes && hasEnergy) {
            ItemStack inputStack = pEntity.itemHandler.getStackInSlot(21);

            SimpleContainer container = new SimpleContainer(inputStack);
            var recipe = level.getRecipeManager().getRecipeFor(ModRecipes.ARC_FURNACE_TYPE.get(), container, level);

            if (recipe.isPresent()) {
                for (int i = 1; i <= 20; i++) {
                    if (pEntity.itemHandler.getStackInSlot(i).isEmpty() && !inputStack.isEmpty()) {
                        pEntity.itemHandler.setStackInSlot(i, inputStack.split(1));
                    }
                }
            }

            if (canProcess(pEntity, level)) {
                pEntity.progress++;
                pEntity.ENERGY_STORAGE.extractEnergy(50, false);

                if (pEntity.progress >= pEntity.maxProgress) {
                    executeArcFurnaceCraft(pEntity, level);
                    pEntity.progress = 0;
                }
            } else {
                pEntity.progress = 0;
            }
        } else {
            pEntity.progress = 0;
        }
    }

    private static boolean canProcess(ElectricArcFurnaceBlockEntity pEntity, Level level) {
        for (int i = 1; i <= 20; i++) {
            ItemStack stack = pEntity.itemHandler.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            SimpleContainer container = new SimpleContainer(stack);
            if (level.getRecipeManager().getRecipeFor(ModRecipes.ARC_FURNACE_TYPE.get(), container, level).isPresent()) {
                return true;
            }
        }
        return false;
    }

    private static void executeArcFurnaceCraft(ElectricArcFurnaceBlockEntity pEntity, Level level) {
        for (int i = 1; i <= 20; i++) {
            ItemStack stack = pEntity.itemHandler.getStackInSlot(i);
            if (stack.isEmpty()) continue;

            SimpleContainer container = new SimpleContainer(stack);
            var recipe = level.getRecipeManager().getRecipeFor(ModRecipes.ARC_FURNACE_TYPE.get(), container, level);

            if (recipe.isPresent()) {
                pEntity.itemHandler.setStackInSlot(i, recipe.get().getResultItem(level.registryAccess()).copy());
            }
        }

        for (int slot = 27; slot <= 29; slot++) {
            ItemStack electrode = pEntity.itemHandler.getStackInSlot(slot);
            if (!electrode.isEmpty() && electrode.isDamageableItem()) {
                electrode.setDamageValue(electrode.getDamageValue() + 5);
            }
        }
    }

    private static boolean hasFlintToProcess(ElectricArcFurnaceBlockEntity pEntity) {
        for (int i = 1; i <= 20; i++) {
            if (pEntity.itemHandler.getStackInSlot(i).is(Items.FLINT)) return true;
        }
        return false;
    }

    private static ItemStack getMoltenVariant(ItemStack stack) {
        if (stack.is(ModItems.GRAPHITE_ELECTRODE.get())) return new ItemStack(ModItems.MOLTEN_GRAPHITE_ELECTRODE.get());
        if (stack.is(ModItems.LANTHANIUM_ELECTRODE.get())) return new ItemStack(ModItems.MOLTEN_LANTHANIUM_ELECTRODE.get());
        if (stack.is(ModItems.DESH_ELECTRODE.get())) return new ItemStack(ModItems.MOLTEN_DESH_ELECTRODE.get());
        if (stack.is(ModItems.SATURNITE_ELECTRODE.get())) return new ItemStack(ModItems.MOLTEN_SATURNATE_ELECTRODE.get());
        return ItemStack.EMPTY;
    }

    private static boolean hasValidElectrodes(ElectricArcFurnaceBlockEntity pEntity) {
        ItemStack s1 = pEntity.itemHandler.getStackInSlot(27);
        ItemStack s2 = pEntity.itemHandler.getStackInSlot(28);
        ItemStack s3 = pEntity.itemHandler.getStackInSlot(29);

        return (s1.is(ModItems.GRAPHITE_ELECTRODE.get()) && s2.is(ModItems.GRAPHITE_ELECTRODE.get()) && s3.is(ModItems.GRAPHITE_ELECTRODE.get())) ||
                (s1.is(ModItems.SATURNITE_ELECTRODE.get()) && s2.is(ModItems.SATURNITE_ELECTRODE.get()) && s3.is(ModItems.SATURNITE_ELECTRODE.get())) ||
                (s1.is(ModItems.LANTHANIUM_ELECTRODE.get()) && s2.is(ModItems.LANTHANIUM_ELECTRODE.get()) && s3.is(ModItems.LANTHANIUM_ELECTRODE.get())) ||
                (s1.is(ModItems.DESH_ELECTRODE.get()) && s2.is(ModItems.DESH_ELECTRODE.get()) && s3.is(ModItems.DESH_ELECTRODE.get()));
    }
}
