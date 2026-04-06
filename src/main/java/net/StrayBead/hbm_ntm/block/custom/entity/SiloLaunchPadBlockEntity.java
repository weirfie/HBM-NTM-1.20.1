package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.entity.ModEntities;
import net.StrayBead.hbm_ntm.entity.custom.AntiBallisticMissileEntity;
import net.StrayBead.hbm_ntm.entity.custom.HighExplosiveMissileEntity;
import net.StrayBead.hbm_ntm.entity.custom.IncendiaryMissileEntity;
import net.StrayBead.hbm_ntm.entity.custom.NuclearMissileEntity;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.FluidSyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.BoilerMenu;
import net.StrayBead.hbm_ntm.screen.SiloLaunchPadMenu;
import net.StrayBead.hbm_ntm.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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

import java.util.List;
import java.util.function.Consumer;

public class SiloLaunchPadBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemHandler = new ItemStackHandler(7) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    public final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 50000) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private static final int ENERGY_REQ = 32;

    private final FluidTank FLUID_TANK = new FluidTank(34000) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if (!level.isClientSide()) {
                ModMessages.sendToClients(new FluidSyncS2CPacket(this.fluid, worldPosition));
            }
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return stack.getFluid() == ModFluids.KEROSENE.get();
        }
    };

    public void setFluid(FluidStack stack) {
        this.FLUID_TANK.setFluid(stack);
    }

    public FluidStack getFluidStack() {
        return this.FLUID_TANK.getFluid();
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();
    private LazyOptional<IFluidHandler> lazyFluidHandler = LazyOptional.empty();

    protected final ContainerData data;

    public SiloLaunchPadBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.SILO_LAUNCH_PAD.get(), pos, state);
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

    @Override
    public Component getDisplayName() {
        return Component.literal("Silo Launch Pad");
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
            return lazyFluidHandler.cast();

        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
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
        nbt.putInt("silo_launch_pad.energy", ENERGY_STORAGE.getEnergyStored());
        nbt = FLUID_TANK.writeToNBT(nbt);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        FLUID_TANK.readFromNBT(nbt);
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, SiloLaunchPadBlockEntity pEntity) {
        if (level.isClientSide()) {
            return;
        }

        BlockPos abovePos = new BlockPos(pos.getX(), pos.getY() + 3, pos.getZ());
        AABB scanArea = new AABB(abovePos).inflate(0.3D);
        ItemStack slotStack = pEntity.itemHandler.getStackInSlot(0);

        List<HighExplosiveMissileEntity> existingMissiles = level.getEntitiesOfClass(
                HighExplosiveMissileEntity.class,
                scanArea
        );
        List<IncendiaryMissileEntity> existingIncendiaryMissiles = level.getEntitiesOfClass(
                IncendiaryMissileEntity.class,
                scanArea
        );
        List<AntiBallisticMissileEntity> existingAntiBallisticMissiles = level.getEntitiesOfClass(
                AntiBallisticMissileEntity.class,
                scanArea
        );
        List<NuclearMissileEntity> existingNuclearMissiles = level.getEntitiesOfClass(
                NuclearMissileEntity.class,
                scanArea
        );
        boolean isMissilePresent = !existingMissiles.isEmpty();
        boolean isIncendiaryMissilePresent = !existingIncendiaryMissiles.isEmpty();
        boolean isAntiBallisticMissilePresent = !existingAntiBallisticMissiles.isEmpty();
        boolean isNuclearMissilePresent = !existingNuclearMissiles.isEmpty();

        if (slotStack.getItem() == ModItems.HIGH_EXPLOSIVE_MISSILE.get()) {
            if (!isMissilePresent) {
                if (level instanceof ServerLevel serverLevel) {
                    HighExplosiveMissileEntity missile = ModEntities.HIGH_EXPLOSIVE_MISSILE_ENTITY.get().create(serverLevel);
                    if (missile != null) {
                        missile.moveTo(pos.getX() + 0.5D, pos.getY() + 3.0D, pos.getZ() + 0.5D,
                                serverLevel.getRandom().nextFloat() * 360F, 90.0F);
                        missile.setXRot(0F);
                        missile.xRotO = 0f;

                        missile.setActivated(false);
                        missile.setNoGravity(true);

                        serverLevel.addFreshEntity(missile);
                    }
                }
            }
        } else if (slotStack.getItem() == ModItems.INCENDIARY_MISSILE.get()) {
            if (!isIncendiaryMissilePresent) {
                if (level instanceof ServerLevel serverLevel) {
                    IncendiaryMissileEntity missile = ModEntities.INCENDIARY_MISSILE_ENTITY.get().create(serverLevel);
                    if (missile != null) {
                        missile.moveTo(pos.getX() + 0.5D, pos.getY() + 3.0D, pos.getZ() + 0.5D,
                                serverLevel.getRandom().nextFloat() * 360F, 90.0F);
                        missile.setXRot(0F);
                        missile.xRotO = 0f;

                        missile.setActivated(false);
                        missile.setNoGravity(true);

                        serverLevel.addFreshEntity(missile);
                    }
                }
            }
        } else if (slotStack.getItem() == ModItems.NUCLEAR_MISSILE.get()) {
            if (!isNuclearMissilePresent) {
                if (level instanceof ServerLevel serverLevel) {
                    NuclearMissileEntity missile = ModEntities.NUCLEAR_MISSILE_ENTITY.get().create(serverLevel);
                    if (missile != null) {
                        missile.moveTo(pos.getX() + 0.5D, pos.getY() + 3.0D, pos.getZ() + 0.5D,
                                serverLevel.getRandom().nextFloat() * 360F, 90.0F);
                        missile.setXRot(0F);
                        missile.xRotO = 0f;

                        missile.setActivated(false);
                        missile.setNoGravity(true);

                        serverLevel.addFreshEntity(missile);
                    }
                }
            }
        } else if (slotStack.getItem() == ModItems.ANTI_BALLISTIC_MISSILE.get()) {
            if (!isAntiBallisticMissilePresent) {
                if (level instanceof ServerLevel serverLevel) {
                    AntiBallisticMissileEntity missile = ModEntities.ANTI_BALLISTIC_MISSILE_ENTITY.get().create(serverLevel);
                    if (missile != null) {
                        missile.moveTo(pos.getX() + 0.5D, pos.getY() + 3.0D, pos.getZ() + 0.5D,
                                serverLevel.getRandom().nextFloat() * 360F, 90.0F);
                        missile.setXRot(0F);
                        missile.xRotO = 0f;

                        missile.setActivated(false);
                        missile.setNoGravity(true);

                        serverLevel.addFreshEntity(missile);
                    }
                }
            }
        }
        else if (slotStack.isEmpty()) {
            if (isMissilePresent) {
                for (HighExplosiveMissileEntity missile : existingMissiles) {
                    if (!missile.isActivated()) {
                        missile.discard();
                    }
                }
            }
            if (isIncendiaryMissilePresent) {
                for (IncendiaryMissileEntity missile : existingIncendiaryMissiles) {
                    if (!missile.isActivated()) {
                        missile.discard();
                    }
                }
            }
            if (isAntiBallisticMissilePresent) {
                for (AntiBallisticMissileEntity missile : existingAntiBallisticMissiles) {
                    if (!missile.isActivated()) {
                        missile.discard();
                    }
                }
            }
            if (isNuclearMissilePresent) {
                for (NuclearMissileEntity missile : existingNuclearMissiles) {
                    if (!missile.isActivated()) {
                        missile.discard();
                    }
                }
            }
        }
    }

    private static boolean hasEnoughFluid(SiloLaunchPadBlockEntity pEntity) {
        return pEntity.FLUID_TANK.getFluidAmount() >= 100 && pEntity.FLUID_TANK.getFluid().getFluid() == ModFluids.KEROSENE.get();
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SiloLaunchPadMenu(id, inventory, this, this.data);
    }

    private static void fillTankWithFluid(SiloLaunchPadBlockEntity pEntity, FluidStack stack, @NotNull ItemStack container) {
        pEntity.FLUID_TANK.fill(stack, IFluidHandler.FluidAction.EXECUTE);

        pEntity.itemHandler.extractItem(0, 1, false);
        pEntity.itemHandler.insertItem(0, container, false);
    }

    private static boolean hasFluidItemInSourceSlot(SiloLaunchPadBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getCount() > 0;
    }

    private static void extractEnergy(SiloLaunchPadBlockEntity pEntity) {
        pEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private static boolean hasGemInFirstSlot(SiloLaunchPadBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.INFINITE_BATTERY.get();
    }
}
