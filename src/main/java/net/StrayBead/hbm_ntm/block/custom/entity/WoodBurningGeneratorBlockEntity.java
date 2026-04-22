package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.custom.BoilerBlock;
import net.StrayBead.hbm_ntm.block.custom.WoodBurningGeneratorBlock;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.BoilerMenu;
import net.StrayBead.hbm_ntm.screen.WoodBurningGeneratorMenu;
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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WoodBurningGeneratorBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;
    private boolean IS_ON = false;
    private int burnTimer = 0;
    private static final int BURN_INTERVAL = 300;

    public WoodBurningGeneratorBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntites.WOOD_BURNING_GENERATOR.get(), blockPos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> WoodBurningGeneratorBlockEntity.this.progress;
                    case 1 -> WoodBurningGeneratorBlockEntity.this.maxProgress;
                    case 2 -> WoodBurningGeneratorBlockEntity.this.IS_ON ? 1 : 0;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> WoodBurningGeneratorBlockEntity.this.progress = value;
                    case 1 -> WoodBurningGeneratorBlockEntity.this.maxProgress = value;
                    case 2 -> WoodBurningGeneratorBlockEntity.this.IS_ON = value != 0;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(50000, 100000) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private static final int ENERGY_REQ = 50;

    private final FluidTank FLUID_TANK = new FluidTank(100000) {
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

    @Override
    public Component getDisplayName() {
        return Component.literal("Wood Burning Generator");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new WoodBurningGeneratorMenu(id, inventory, this, this.data);
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
            return lazyFluidHandler.cast();
        }
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            if(side == null) {
                return lazyItemHandler.cast();
            }

            if(directionWrappedHandlerMap.containsKey(side)) {
                Direction localDir = this.getBlockState().getValue(WoodBurningGeneratorBlock.FACING);

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
        nbt.putInt("wood_burning_generator.progress", this.progress);
        nbt.putInt("wood_burning_generator.energy", ENERGY_STORAGE.getEnergyStored());
        nbt.putBoolean("wood_burning_generator.is_on", this.IS_ON);
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    public boolean isActive() {
        return this.data.get(2) != 0;
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("wood_burning_generator.progress");
        IS_ON = nbt.getBoolean("wood_burning_generator.is_on");
        FLUID_TANK.readFromNBT(nbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WoodBurningGeneratorBlockEntity pEntity) {
        if (level.isClientSide()) {
            return;
        }

        if (pEntity.ENERGY_STORAGE.getEnergyStored() > 0) {
            pushEnergyToNeighbors(pEntity, pos, level);
        }

        if (pEntity.IS_ON) {
            if (pEntity.itemHandler.getStackInSlot(0).getItem() == Items.OAK_LOG) {
                pEntity.burnTimer++;
                if (pEntity.burnTimer >= BURN_INTERVAL) {
                    pEntity.itemHandler.extractItem(0, 1, false);

                    pEntity.burnTimer = 0;

                }
                pEntity.ENERGY_STORAGE.receiveEnergy(ENERGY_REQ, false);
            }
        }
    }

    private static void pushEnergyToNeighbors(WoodBurningGeneratorBlockEntity pEntity, BlockPos pos, Level level) {
        for (Direction dir : Direction.values()) {
            BlockPos neighborPos = pos.relative(dir);
            BlockEntity neighborBE = level.getBlockEntity(neighborPos);

            if (neighborBE instanceof CopperCableBlockEntity cable) {
                int toSend = pEntity.ENERGY_STORAGE.extractEnergy(200, true);
                int accepted = cable.energyStorage.receiveEnergy(toSend, false);
                pEntity.ENERGY_STORAGE.extractEnergy(accepted, false);
            }
        }
    }

    private static void extractEnergy(WoodBurningGeneratorBlockEntity pEntity) {
        pEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private static boolean hasEnoughEnergy(WoodBurningGeneratorBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ * pEntity.maxProgress;
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
