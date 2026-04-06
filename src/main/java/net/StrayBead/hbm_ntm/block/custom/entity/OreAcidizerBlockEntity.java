package net.StrayBead.hbm_ntm.block.custom.entity;

import io.netty.buffer.Unpooled;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.screen.CentrifugeGuiMenu;
import net.StrayBead.hbm_ntm.screen.OreAcidizerGuiMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.stream.IntStream;

public class OreAcidizerBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

    public OreAcidizerBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntites.ORE_ACIDIZER.get(), position, state);
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (!this.tryLoadLootTable(compound))
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, this.stacks);
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks);
        }
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

    public static void tick(Level level, BlockPos pos, BlockState state, OreAcidizerBlockEntity entity) {
        if (level.isClientSide) return;

        ItemStack inputStack = entity.stacks.get(0);

        if (inputStack.getItem() == ModItems.CENTRIFUGED_URANIUM_BEDROCK_ORE.get()) {

            if (canOutput(entity)) {
                inputStack.shrink(1);

                entity.stacks.set(1, new ItemStack(ModItems.CLEANED_URANIUM_BEDROCK_ORE.get(), 1));

                entity.setChanged();
            }
        } else if (inputStack.getItem() == ModItems.SEPARATED_URANIUM_BEDROCK_ORE.get()) {
            if (canOutput(entity)) {
                inputStack.shrink(1);

                entity.stacks.set(1, new ItemStack(ModItems.PURIFIED_URANIUM_BEDROCK_ORE.get(), 1));

                entity.setChanged();            }
        } else if (inputStack.getItem() == Items.REDSTONE_ORE) {
            if (canOutput(entity)) {
                inputStack.shrink(1);

                entity.stacks.set(1, new ItemStack(ModItems.REDSTONE_CRYSTALS.get(), 1));

                entity.setChanged();            }
        } else if (inputStack.getItem() == ModBlocks.RARE_EARTH_ORE.get().asItem()) {
            if (canOutput(entity)) {
                inputStack.shrink(1);

                entity.stacks.set(1, new ItemStack(ModItems.RARE_EARTH_CRYSTALS.get(), 1));

                entity.setChanged();
            }
        } else if (inputStack.getItem() == ModItems.DESHREADY_BLEND.get().asItem()) {
            if (canOutput(entity)) {
                inputStack.shrink(1);

                entity.stacks.set(1, new ItemStack(ModItems.DESH_INGOT.get(), 1));

                entity.setChanged();
            }
        }
    }

    private static boolean canOutput(OreAcidizerBlockEntity entity) {
        return entity.stacks.get(1).isEmpty();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks)
            if (!itemstack.isEmpty())
                return false;
        return true;
    }

    @Override
    public Component getDefaultName() {
        return Component.literal("acidizer");
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new OreAcidizerGuiMenu(id, inventory, new FriendlyByteBuf(Unpooled.buffer()).writeBlockPos(this.worldPosition));
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Acidizer");
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
}
