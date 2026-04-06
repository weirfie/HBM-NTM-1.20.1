package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.BoilerBlock;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.BoilerMenu;
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
import net.minecraft.world.level.block.Blocks;
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

import java.util.Map;

public class BoilerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent() || stack.getItem() == ModItems.INFINITE_BATTERY.get();
                case 1 -> stack.getItem() == ModItems.MINECRAFT_GRADE_COPPER.get();
                case 2 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };

    public FluidTank getSteamTank() {
        return this.steamTank;
    }

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
            return stack.getFluid() == Fluids.WATER || stack.getFluid() == ModFluids.CRUDE_OIL.get();
        }
    };

    private final FluidTank steamTank = new FluidTank(64000) {
        @Override
        protected void onContentsChanged() {
            syncBlock();
        }
    };

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
    private final CombinedFluidHandler combinedHandler = new CombinedFluidHandler(FLUID_TANK, steamTank);
    private final LazyOptional<IFluidHandler> fluidCapability = LazyOptional.of(() -> combinedHandler);


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public BoilerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.BOILER.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BoilerBlockEntity.this.progress;
                    case 1 -> BoilerBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BoilerBlockEntity.this.progress = value;
                    case 1 -> BoilerBlockEntity.this.maxProgress = value;
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
        return Component.literal("Boiler");
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
        return new BoilerMenu(id, inventory, this, this.data);
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
            return fluidCapability.cast();

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
        nbt.putInt("boiler.progress", this.progress);
        nbt.putInt("boiler.energy", ENERGY_STORAGE.getEnergyStored());
        nbt.put("SteamTank", steamTank.writeToNBT(new CompoundTag()));
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("boiler.progress");
        FLUID_TANK.readFromNBT(nbt);
        if (nbt.contains("SteamTank"))
            steamTank.readFromNBT(nbt.getCompound("SteamTank"));
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BoilerBlockEntity pEntity) {
        if(level.isClientSide()) {
            return;
        }

        if(hasGemInFirstSlot(pEntity)) {
            pEntity.ENERGY_STORAGE.receiveEnergy(1000, false);
        }

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);

            if (neighborState.is(ModBlocks.STEAM_PIPE.get())) {
                if (pEntity.steamTank.getFluidAmount() > 100) {
                    pEntity.steamTank.drain(100, IFluidHandler.FluidAction.EXECUTE);
                    {
                        BlockEntity _ent = level.getBlockEntity(neighborPos);
                        int _amount = 100;
                        if (_ent != null)
                            _ent.getCapability(ForgeCapabilities.FLUID_HANDLER, null).ifPresent(capability -> capability.fill(new FluidStack(ModFluids.STEAM.get(), _amount), IFluidHandler.FluidAction.EXECUTE));
                    }
                }
            }
        }

        if (!pEntity.steamTank.isEmpty()) {
            pEntity.pushFluidToNeighbors();
        }

        if (hasEnoughEnergy(pEntity) && pEntity.FLUID_TANK.getFluidAmount() > 100 && hasHeatSourceBlockBelow(level, pos)) {
            if (pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.CRUDE_OIL.get()) {

                pEntity.FLUID_TANK.drain(100, IFluidHandler.FluidAction.EXECUTE);
                pEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
                pEntity.steamTank.fill(new FluidStack(ModFluids.HOT_CRUDE_OIL.get(), 100), IFluidHandler.FluidAction.EXECUTE);

                pEntity.setChanged();
            }
        }

        if (hasEnoughEnergy(pEntity) && hasHeatSourceBlockBelow(level, pos)) {
            if (hasEnoughFluid(pEntity)) {
                pEntity.FLUID_TANK.drain(100, IFluidHandler.FluidAction.EXECUTE);
                pEntity.steamTank.fill(new FluidStack(ModFluids.STEAM.get(), 100), IFluidHandler.FluidAction.EXECUTE);
            }
        }

        if(hasRecipe(pEntity) && hasEnoughEnergy(pEntity) && hasEnoughFluid(pEntity) && hasHeatSourceBlockBelow(level, pos)) {
            pEntity.progress++;
            extractEnergy(pEntity);
            setChanged(level, pos, state);

            if(pEntity.progress >= pEntity.maxProgress) {
                craftItem(pEntity);
            }
        } else {
            pEntity.resetProgress();
            setChanged(level, pos, state);
        }

        if (hasFluidItemInSourceSlot(pEntity)) {
            transferItemFluidToFluidTank(pEntity);
        }
    }

    private static boolean hasHeatSourceBlockBelow(Level level, BlockPos pos) {
        return level.getBlockState(BlockPos.containing(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() == ModBlocks.FIREBOX.get() || level.getBlockState(BlockPos.containing(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() == ModBlocks.HEATING_OVEN.get();
    }

    private void pushFluidToNeighbors() {
        FluidStack stackInTank = steamTank.getFluid();
        if (stackInTank.isEmpty()) return;

        int pushAmount = 100;

        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = worldPosition.relative(dir);
            BlockEntity neighborBE = level.getBlockEntity(neighborPos);

            if (neighborBE != null) {
                if (neighborBE instanceof FluidBlockEntity ductBE) {
                    if (!ductBE.getAllowedFluid().equals("hot_crude_oil")) {
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
                        this.steamTank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                        this.setChanged();
                    }
                });
            }
        }
    }

    private static boolean hasEnoughFluid(BoilerBlockEntity pEntity) {
        return pEntity.FLUID_TANK.getFluidAmount() >= 100 && pEntity.FLUID_TANK.getFluid().getFluid() == Fluids.WATER;
    }

    private static void transferItemFluidToFluidTank(BoilerBlockEntity pEntity) {
        pEntity.itemHandler.getStackInSlot(0).getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(handler -> {
            int drainAmount = Math.min(pEntity.FLUID_TANK.getSpace(), 1000);

            FluidStack stack = handler.drain(drainAmount, IFluidHandler.FluidAction.SIMULATE);
            if (pEntity.FLUID_TANK.isFluidValid(stack)) {
                stack = handler.drain(drainAmount, IFluidHandler.FluidAction.EXECUTE);
                fillTankWithFluid(pEntity, stack, handler.getContainer());
            }
        });
    }

    private static void fillTankWithFluid(BoilerBlockEntity pEntity, FluidStack stack, @NotNull ItemStack container) {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        pEntity.itemHandler.extractItem(0, 1, false);
        pEntity.itemHandler.insertItem(0, container, false);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        fluidCapability.invalidate();
    }

    private static boolean hasFluidItemInSourceSlot(BoilerBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getCount() > 0;
    }

    private static void extractEnergy(BoilerBlockEntity pEntity) {
        pEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private static boolean hasEnoughEnergy(BoilerBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ * pEntity.maxProgress;
    }

    private static boolean hasGemInFirstSlot(BoilerBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.INFINITE_BATTERY.get();
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private static void craftItem(BoilerBlockEntity pEntity) {

        if(hasRecipe(pEntity)) {
            pEntity.FLUID_TANK.drain(500, IFluidHandler.FluidAction.EXECUTE);
            pEntity.steamTank.fill(new FluidStack(ModFluids.STEAM.get(), 500), IFluidHandler.FluidAction.EXECUTE);
            pEntity.itemHandler.extractItem(1, 1, false);
            pEntity.itemHandler.setStackInSlot(2, new ItemStack(ModItems.ADVANCED_ALLOY_INGOT.get(),
                    pEntity.itemHandler.getStackInSlot(2).getCount() + 1));

            pEntity.resetProgress();
        }
    }

    private static boolean hasRecipe(BoilerBlockEntity entity) {
        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        boolean hasRawGemInFirstSlot = entity.itemHandler.getStackInSlot(1).getItem() == ModItems.MINECRAFT_GRADE_COPPER.get();

        return hasRawGemInFirstSlot && canInsertAmountIntoOutputSlot(inventory) &&
                canInsertItemIntoOutputSlot(inventory, new ItemStack(ModItems.ADVANCED_ALLOY_INGOT.get(), 1));
    }

    private static boolean canInsertItemIntoOutputSlot(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(2).getItem() == stack.getItem() || inventory.getItem(2).isEmpty();
    }

    private static boolean canInsertAmountIntoOutputSlot(SimpleContainer inventory) {
        return inventory.getItem(2).getMaxStackSize() > inventory.getItem(2).getCount();
    }
}
