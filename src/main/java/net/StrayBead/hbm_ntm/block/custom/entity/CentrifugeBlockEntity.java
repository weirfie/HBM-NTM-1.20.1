package net.StrayBead.hbm_ntm.block.custom.entity;

import io.netty.buffer.Unpooled;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.StrayBead.hbm_ntm.recipe.CentrifugeRecipe;
import net.StrayBead.hbm_ntm.screen.CentrifugeGuiMenu;
import net.StrayBead.hbm_ntm.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class CentrifugeBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private int progress = 0;
    private final int MAX_PROGRESS = 40;
    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

    public CentrifugeBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntites.CENTRIFUGE.get(), position, state);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (!this.tryLoadLootTable(compound))
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, this.stacks);
        ENERGY_STORAGE.setEnergy(compound.getInt("centrifuge.energy"));
    }

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(500000, 10000) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private static final int ENERGY_REQ = 32;
    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks);
        }
        compound.putInt("centrifuge.energy", ENERGY_STORAGE.getEnergyStored());
    }

    public IEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergyLevel(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergyHandler.invalidate();
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithFullMetadata();
    }

    @Override
    public int getContainerSize() {
        return stacks.size();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, CentrifugeBlockEntity entity) {
        if (level.isClientSide) return;

        if (entity.ENERGY_STORAGE.getEnergyStored() >= 100) {
            SimpleContainer inventory = new SimpleContainer(1);
            inventory.setItem(0, entity.stacks.get(3));

            var match = level.getRecipeManager()
                    .getRecipeFor(CentrifugeRecipe.Type.INSTANCE, inventory, level);

            if (match.isPresent() && canInsertOutputs(entity, match.get().getOutputs())) {
                entity.ENERGY_STORAGE.extractEnergy(100, false);
                entity.progress++;

                if (entity.progress >= entity.MAX_PROGRESS) {
                    craftItem(entity, match.get());
                    entity.progress = 0;
                    entity.setChanged();
                }
            } else {
                entity.progress = 0;
            }
        } else {
            entity.progress = 0;
        }
    }

    private static void craftItem(CentrifugeBlockEntity entity, CentrifugeRecipe recipe) {
        entity.stacks.get(3).shrink(1);
        NonNullList<ItemStack> outputs = recipe.getOutputs();

        for (int i = 0; i < outputs.size() && i < 4; i++) {
            entity.stacks.set(4 + i, outputs.get(i).copy());
        }
    }

    private static boolean canInsertOutputs(CentrifugeBlockEntity entity, NonNullList<ItemStack> outputs) {
        return entity.stacks.get(4).isEmpty() &&
                entity.stacks.get(5).isEmpty() &&
                entity.stacks.get(6).isEmpty() &&
                entity.stacks.get(7).isEmpty();
    }

    private static boolean canOutput(CentrifugeBlockEntity entity) {
        return entity.stacks.get(4).isEmpty() &&
                entity.stacks.get(5).isEmpty() &&
                entity.stacks.get(6).isEmpty() &&
                entity.stacks.get(7).isEmpty();
    }

    protected final net.minecraft.world.inventory.ContainerData data = new net.minecraft.world.inventory.ContainerData() {
        @Override
        public int get(int index) {
            return index == 0 ? CentrifugeBlockEntity.this.progress : CentrifugeBlockEntity.this.MAX_PROGRESS;
        }
        @Override
        public void set(int index, int value) {
            if (index == 0) CentrifugeBlockEntity.this.progress = value;
        }
        @Override
        public int getCount() {
            return 2;
        }
    };

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks)
            if (!itemstack.isEmpty())
                return false;
        return true;
    }

    @Override
    public Component getDefaultName() {
        return Component.literal("centrifuge");
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new CentrifugeGuiMenu(id, inventory,
                new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition),
                this.data);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Centrifuge");
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.stacks;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> stacks) {
        this.stacks = stacks;
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return IntStream.range(0, this.getContainerSize()).toArray();
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
        return this.canPlaceItem(index, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return true;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if(capability == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        if (!this.remove && capability == ForgeCapabilities.ITEM_HANDLER) {
            if (facing == null) {
                return handlers[0].cast();
            }
            return handlers[facing.ordinal()].cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : handlers)
            handler.invalidate();
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }
}
