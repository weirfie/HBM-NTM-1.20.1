package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.BoilerMenu;
import net.StrayBead.hbm_ntm.screen.CrucibleMenu;
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

public class CrucibleBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(10) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
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

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 256) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public CrucibleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.CRUCIBLE.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CrucibleBlockEntity.this.progress;
                    case 1 -> CrucibleBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CrucibleBlockEntity.this.progress = value;
                    case 1 -> CrucibleBlockEntity.this.maxProgress = value;
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
        return Component.literal("Crucible");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new CrucibleMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!this.remove && cap == ForgeCapabilities.FLUID_HANDLER)
            return lazyFluidHandler.cast();

        if(cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }

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
        nbt.putInt("crucible.progress", this.progress);
        nbt.putInt("crucible.energy", ENERGY_STORAGE.getEnergyStored());
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        progress = nbt.getInt("crucible.progress");
        FLUID_TANK.readFromNBT(nbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CrucibleBlockEntity pEntity) {
        if (level.isClientSide()) return;

        if (hasHeatSourceBlockBelow(level, pos)) {
            pEntity.ENERGY_STORAGE.receiveEnergy(20, false);
        }

        if (pEntity.ENERGY_STORAGE.getEnergyStored() > 100) {
            if (pEntity.itemHandler.getStackInSlot(9).getItem() == ModItems.CRUCIBLE_TEMPLATES.get("steel_production").get()) {
                if (pEntity.itemHandler.getStackInSlot(0).getItem() == Items.IRON_INGOT) {
                    pEntity.ENERGY_STORAGE.extractEnergy(100, false);
                    pEntity.itemHandler.getStackInSlot(0).shrink(1);
                    pEntity.FLUID_TANK.fill(new FluidStack(ModFluids.MOLTEN_STEEL.get(), 250), IFluidHandler.FluidAction.EXECUTE);
                }
            } else if (pEntity.itemHandler.getStackInSlot(9).getItem() == ModItems.CRUCIBLE_TEMPLATES.get("copper_production_from_malachite").get()) {
                if (pEntity.itemHandler.getStackInSlot(0).getItem() == ModBlocks.MALACHITE.get().asItem()) {
                    pEntity.ENERGY_STORAGE.extractEnergy(100, false);
                    pEntity.itemHandler.getStackInSlot(0).shrink(1);
                    pEntity.FLUID_TANK.fill(new FluidStack(ModFluids.MOLTEN_COPPER.get(), 100), IFluidHandler.FluidAction.EXECUTE);
                }
            } else if (pEntity.itemHandler.getStackInSlot(9).getItem() == ModItems.CRUCIBLE_TEMPLATES.get("iron_production_from_hematite").get()) {
                if (pEntity.itemHandler.getStackInSlot(0).getItem() == ModBlocks.HEMATITE.get().asItem()) {
                    pEntity.ENERGY_STORAGE.extractEnergy(100, false);
                    pEntity.itemHandler.getStackInSlot(0).shrink(1);
                    pEntity.FLUID_TANK.fill(new FluidStack(ModFluids.MOLTEN_IRON.get(), 100), IFluidHandler.FluidAction.EXECUTE);
                }
            } else if (pEntity.itemHandler.getStackInSlot(9).getItem() == ModItems.CRUCIBLE_TEMPLATES.get("aluminum_production").get()) {
                if (pEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.CRYOLITE_CHUNK.get()) {
                    pEntity.ENERGY_STORAGE.extractEnergy(100, false);
                    pEntity.itemHandler.getStackInSlot(0).shrink(1);
                    pEntity.FLUID_TANK.fill(new FluidStack(ModFluids.MOLTEN_ALUMINUM.get(), 100), IFluidHandler.FluidAction.EXECUTE);
                }
            } else if (pEntity.itemHandler.getStackInSlot(9).getItem() == ModItems.CRUCIBLE_TEMPLATES.get("red_copper_production").get()) {
                if (pEntity.itemHandler.getStackInSlot(0).getItem() == Items.REDSTONE) {
                    pEntity.ENERGY_STORAGE.extractEnergy(100, false);
                    pEntity.itemHandler.getStackInSlot(0).shrink(1);
                    pEntity.FLUID_TANK.fill(new FluidStack(ModFluids.MOLTEN_REDSTONE.get(), 100), IFluidHandler.FluidAction.EXECUTE);
                }
            }
        }

        if (!pEntity.FLUID_TANK.isEmpty()) {
            Iterable<BlockPos> area = BlockPos.betweenClosed(pos.offset(-2, -1, -2), pos.offset(2, 0, 2));

            for (BlockPos targetPos : area) {
                if (targetPos.equals(pos)) continue;

                BlockEntity targetBE = level.getBlockEntity(targetPos);
                if (targetBE instanceof ShallowFoundryBasinBlockEntity basin) {

                    FluidStack transferStack = new FluidStack(pEntity.FLUID_TANK.getFluid(), 100);

                    basin.getCapability(ForgeCapabilities.FLUID_HANDLER).ifPresent(handler -> {
                        int filled = handler.fill(transferStack, IFluidHandler.FluidAction.SIMULATE);

                        if (filled > 0) {
                            handler.fill(transferStack, IFluidHandler.FluidAction.EXECUTE);
                            pEntity.FLUID_TANK.drain(filled, IFluidHandler.FluidAction.EXECUTE);
                        }
                    });

                    if (pEntity.FLUID_TANK.isEmpty()) break;
                }
            }
        }

        if (!pEntity.FLUID_TANK.isEmpty() && pEntity.FLUID_TANK.getFluid().getFluid().isSame(ModFluids.MOLTEN_STEEL.get())) {

            net.minecraft.world.phys.AABB burnArea = new net.minecraft.world.phys.AABB(pos).inflate(2, 1, 2);

            java.util.List<net.minecraft.world.entity.LivingEntity> entities = level.getEntitiesOfClass(
                    net.minecraft.world.entity.LivingEntity.class,
                    burnArea
            );

            for (net.minecraft.world.entity.LivingEntity entity : entities) {
                if (!entity.fireImmune()) {
                    entity.setSecondsOnFire(5);
                    entity.hurt(level.damageSources().hotFloor(), 2.0F);
                }
            }
        }
    }

    private static boolean hasHeatSourceBlockBelow(Level level, BlockPos pos) {
        return level.getBlockState(BlockPos.containing(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() == ModBlocks.FIREBOX.get() || level.getBlockState(BlockPos.containing(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() == ModBlocks.HEATING_OVEN.get();
    }

    private static void fillTankWithFluid(CrucibleBlockEntity pEntity, FluidStack stack, @NotNull ItemStack container) {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        pEntity.itemHandler.extractItem(0, 1, false);
        pEntity.itemHandler.insertItem(0, container, false);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        lazyFluidHandler.invalidate();
    }

    private void resetProgress() {
        this.progress = 0;
    }
}
