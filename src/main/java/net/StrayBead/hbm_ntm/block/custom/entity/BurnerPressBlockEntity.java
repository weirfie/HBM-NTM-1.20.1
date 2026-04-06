package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.network.packet.ItemStackSyncS2CPacket;
import net.StrayBead.hbm_ntm.recipe.BurnerPressRecipe;
import net.StrayBead.hbm_ntm.screen.AssemblyMachineMenu;
import net.StrayBead.hbm_ntm.screen.BurnerPressGuiMenu;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

import java.util.Map;
import java.util.Optional;

public class BurnerPressBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                ModMessages.sendToClients(new ItemStackSyncS2CPacket(this, worldPosition));
            }
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 50;

    private static final Map<Item, Item> PRESS_RECIPES = Map.of(
            Items.COPPER_INGOT, ModItems.COPPER_PLATE.get(),
            ModItems.STEEL.get(), ModItems.STEEL_PLATE.get()
    );

    public BurnerPressBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntites.BURNER_PRESS.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> BurnerPressBlockEntity.this.progress;
                    case 1 -> BurnerPressBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> BurnerPressBlockEntity.this.progress = value;
                    case 1 -> BurnerPressBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(200000, 50000) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };

    private static final int ENERGY_REQ = 32;

    @Override
    public Component getDisplayName() {
        return Component.literal("Burner Press");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new BurnerPressGuiMenu(id, inventory, this, this.data);
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
    }

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
        lazyEnergyHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("inventory", itemHandler.serializeNBT());
        nbt.putInt("burner_press.energy", ENERGY_STORAGE.getEnergyStored());
        nbt.putInt("burner_press.progress", this.progress);

        super.saveAdditional(nbt);

        System.out.println("Saved Burner Press NBT! Slot 0 has: " + itemHandler.getStackInSlot(0));
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));
        ENERGY_STORAGE.setEnergy(nbt.getInt("burner_press.energy"));
        progress = nbt.getInt("burner_press.progress");
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, BurnerPressBlockEntity pEntity) {
        if (level.isClientSide()) return;

        ItemStack fuelSource = pEntity.itemHandler.getStackInSlot(3);
        if (fuelSource.getItem() == Items.COAL || fuelSource.getItem() == Items.CHARCOAL) {
            fuelSource.shrink(1);
            pEntity.ENERGY_STORAGE.receiveEnergy(300, false);
        }

        if (hasRecipe(pEntity)) {
            if (hasEnoughEnergy(pEntity)) {
                pEntity.ENERGY_STORAGE.extractEnergy(3, false);
                pEntity.progress++;
                level.sendBlockUpdated(pos, state, state, 3);
                setChanged(level, pos, state);

                if (pEntity.progress >= pEntity.maxProgress) {
                    craftItem(pEntity);
                }
            }
        } else {
            if (pEntity.progress > 0) {
                pEntity.progress = 0;
                setChanged(level, pos, state);
            }
        }
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
        this.progress = tag.getInt("burner_press.progress");
    }

    private static boolean hasRecipe(BurnerPressBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }
        Optional<BurnerPressRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(BurnerPressRecipe.Type.INSTANCE, inventory, level);

        return recipe.isPresent() && canInsertIntoOutput(pEntity, recipe.get().getResultItem(level.registryAccess()));
    }

    private static void craftItem(BurnerPressBlockEntity pEntity) {
        Level level = pEntity.level;
        SimpleContainer inventory = new SimpleContainer(pEntity.itemHandler.getSlots());
        for (int i = 0; i < pEntity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, pEntity.itemHandler.getStackInSlot(i));
        }

        Optional<BurnerPressRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(BurnerPressRecipe.Type.INSTANCE, inventory, level);

        if (recipe.isPresent()) {
            ItemStack result = recipe.get().getResultItem(level.registryAccess());

            pEntity.itemHandler.extractItem(1, 1, false);

            ItemStack stamp = pEntity.itemHandler.getStackInSlot(0);
            if (stamp.hurt(1, level.getRandom(), null)) {
                stamp.shrink(1);
            }

            pEntity.itemHandler.insertItem(2, result.copy(), false);

            pEntity.progress = 0;
        }
    }

    private static boolean canInsertIntoOutput(BurnerPressBlockEntity pEntity, ItemStack result) {
        ItemStack outputSlot = pEntity.itemHandler.getStackInSlot(2);
        return outputSlot.isEmpty() || (outputSlot.is(result.getItem()) && outputSlot.getCount() + result.getCount() <= outputSlot.getMaxStackSize());
    }

    private static boolean hasEnoughEnergy(BurnerPressBlockEntity pEntity) {
        return pEntity.ENERGY_STORAGE.getEnergyStored() >= ENERGY_REQ;
    }

    public int getProgress() {
        return this.progress;
    }

    public int getMaxProgress() {
        return this.maxProgress;
    }

    private static boolean hasIngredients(BurnerPressBlockEntity pEntity, ItemStack[] requirements) {
        for (ItemStack requirement : requirements) {
            int count = 0;
            for (int i = 0; i < 13; i++) {
                ItemStack stack = pEntity.itemHandler.getStackInSlot(i);
                if (stack.is(requirement.getItem())) {
                    count += stack.getCount();
                }
            }
            if (count < requirement.getCount()) return false;
        }
        return true;
    }

    private static void extractEnergy(BurnerPressBlockEntity pEntity) {
        pEntity.ENERGY_STORAGE.extractEnergy(ENERGY_REQ, false);
    }

    private static boolean hasBatteryInFirstSlot(BurnerPressBlockEntity pEntity) {
        return pEntity.itemHandler.getStackInSlot(0).getItem() == ModBlocks.REDSTONE_BATTERY.get().asItem();
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public ItemStack getRenderStack() {
        ItemStack stack;

        if(!itemHandler.getStackInSlot(1).isEmpty()) {
            stack = itemHandler.getStackInSlot(1);
        } else {
            stack = itemHandler.getStackInSlot(1);
        }

        return stack;
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public net.minecraft.network.protocol.Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getUpdatePacket() {
        return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);
    }

    public void setHandler(ItemStackHandler itemStackHandler) {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, itemStackHandler.getStackInSlot(i));
        }
    }
}
