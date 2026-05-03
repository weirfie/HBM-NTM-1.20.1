package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.screen.BoilerMenu;
import net.StrayBead.hbm_ntm.screen.FelMenu;
import net.StrayBead.hbm_ntm.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
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
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class FelBlockEntity extends BlockEntity implements MenuProvider {
    public boolean IS_ON = false;
    public static final Set<FelBlockEntity> LOADED_FELS = Collections.newSetFromMap(new WeakHashMap<>());
    public boolean isFiring = false;

    private final ItemStackHandler itemHandler = new ItemStackHandler(2) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(60000, 10000) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    private void syncBlock() {
        setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    protected final ContainerData data;

    public FelBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.FEL.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> IS_ON ? 1 : 0;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> IS_ON = value != 0;
                }
            }

            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("FEL");
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (level != null && level.isClientSide()) {
            LOADED_FELS.remove(this);
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.putBoolean("is_firing", isFiring);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new FelMenu(id, inventory, this, this.data);
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    public boolean isOn() {
        return this.data.get(0) != 0;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }
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

        if (level != null && level.isClientSide()) {
            LOADED_FELS.add(this);
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("fel.energy", ENERGY_STORAGE.getEnergyStored());
        nbt.putBoolean("is_on", IS_ON);

        super.saveAdditional(nbt);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        IS_ON = nbt.getBoolean("is_on");
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        isFiring = nbt.getBoolean("is_firing");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, FelBlockEntity pEntity) {
        if (level.isClientSide()) {
            return;
        }

        if (pEntity.itemHandler.getStackInSlot(1).getItem() == ModItems.INFINITE_BATTERY.get()) {
            pEntity.ENERGY_STORAGE.receiveEnergy(5000, false);
        }

        boolean conditionsMet = false;
        if (pEntity.IS_ON) {
            if (pEntity.ENERGY_STORAGE.getEnergyStored() > 30) {
                if (pEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.BISMUTH_LASER_CRYSTAL.get() ||
                        pEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.DESH_LASER_CRYSTAL.get() ||
                        pEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.CMB_SCHRABIDATE_ANTIMATTER_LASER_CRYSTAL.get() ||
                        pEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.DIGAMMA_LASER_CRYSTAL.get() ||
                        pEntity.itemHandler.getStackInSlot(0).getItem() == ModItems.SPARK_LASER_CRYSTAL.get()) {
                    pEntity.ENERGY_STORAGE.extractEnergy(30, false);
                    pEntity.ENERGY_STORAGE.extractEnergy(30, false);
                    conditionsMet = true;

                    Direction facing = state.getValue(net.StrayBead.hbm_ntm.block.custom.FelBlock.FACING);

                    for (int i = 1; i <= 21; i++) {
                        BlockPos targetPos = pos.relative(facing, i);

                        net.minecraft.world.phys.AABB hitBox = new net.minecraft.world.phys.AABB(targetPos);
                        java.util.List<net.minecraft.world.entity.LivingEntity> entities =
                                level.getEntitiesOfClass(net.minecraft.world.entity.LivingEntity.class, hitBox);

                        for (net.minecraft.world.entity.LivingEntity victim : entities) {
                            victim.setSecondsOnFire(5);
                            victim.addEffect(new net.minecraft.world.effect.MobEffectInstance(net.minecraft.world.effect.MobEffects.BLINDNESS, 100, 0)); // 100 ticks = 5 seconds
                        }

                        BlockPos belowPos = targetPos.below();
                        BlockEntity belowEntity = level.getBlockEntity(belowPos);
                        if (belowEntity instanceof SilexBlockEntity) {
                            belowEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(energy -> {
                                energy.receiveEnergy(200, false);
                            });
                        }

                        BlockState targetState = level.getBlockState(targetPos.above());
                        if (!targetState.isAir() && !targetState.is(net.minecraft.world.level.block.Blocks.FIRE)) {
                            if (targetState.getDestroySpeed(level, targetPos.above()) >= 0) {
                                level.destroyBlock(targetPos.above(), true);
                                level.setBlockAndUpdate(targetPos.above(), net.minecraft.world.level.block.Blocks.FIRE.defaultBlockState());
                                level.playSound(null, targetPos.above(), net.minecraft.sounds.SoundEvents.FIRE_EXTINGUISH, net.minecraft.sounds.SoundSource.BLOCKS, 1.0f, 1.0f);

                                break;
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        if (pEntity.isFiring != conditionsMet) {
            pEntity.isFiring = conditionsMet;
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }
}
