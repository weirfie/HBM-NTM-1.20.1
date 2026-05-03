package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.IntStream;

public class PaintableCoatedUniversalFluidDuctBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {
    private NonNullList<ItemStack> stacks = NonNullList.<ItemStack>withSize(9, ItemStack.EMPTY);
    private int filterColor = 0xFFFFFF;
    public static final ModelProperty<BlockState> PAINT_PROPERTY = new ModelProperty<>();
    private BlockState paintState = Blocks.AIR.defaultBlockState();
    public static final List<Block> ALLOWED_PAINT_BLOCKS = List.of(
            Blocks.STONE, Blocks.OAK_PLANKS, Blocks.IRON_BLOCK, Blocks.BRICKS, ModBlocks.CONCRETE_BRICKS.get(), ModBlocks.DUCRETE.get(), ModBlocks.CONCRETE_TILE.get(), ModBlocks.REINFORCED_DUCRETE.get(), ModBlocks.REINFORCED_STONE.get(),
            Blocks.BLACK_CONCRETE, Blocks.CYAN_CONCRETE, Blocks.BLUE_CONCRETE, Blocks.GRAY_CONCRETE, Blocks.BROWN_CONCRETE, Blocks.GREEN_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE, Blocks.LIME_CONCRETE,
            Blocks.YELLOW_CONCRETE, Blocks.ORANGE_CONCRETE, Blocks.PINK_CONCRETE, Blocks.PURPLE_CONCRETE, Blocks.MAGENTA_CONCRETE, Blocks.RED_CONCRETE, Blocks.WHITE_CONCRETE
    );
    private final LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.values());

    public PaintableCoatedUniversalFluidDuctBlockEntity(BlockPos position, BlockState state) {
        super(ModBlockEntites.COATED_FLUID_DUCT.get(), position, state);
    }

    public void paintWith(ItemStack stack) {
        Block block = Block.byItem(stack.getItem());

        if (block != Blocks.AIR && ALLOWED_PAINT_BLOCKS.contains(block)) {
            this.setPaintState(block.defaultBlockState());
        }
    }

    public void setPaintState(BlockState state) {
        this.paintState = state;
        this.setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public BlockState getPaintState() {
        return this.paintState;
    }

    public void setFilterColor(int color) {
        this.filterColor = color;
        this.setChanged();
        if (level != null) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public void onDataPacket(net.minecraft.network.Connection net, ClientboundBlockEntityDataPacket pkt) {
        this.load(pkt.getTag());

        if (level != null && level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    public ModelData getModelData() {
        return ModelData.builder()
                .with(PAINT_PROPERTY, this.paintState)
                .build();
    }

    public int getFilterColor() {
        return this.filterColor;
    }

    @Override
    public void load(CompoundTag compound) {
        super.load(compound);
        if (!this.tryLoadLootTable(compound))
            this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compound, this.stacks);
        if (compound.get("fluidTank") instanceof CompoundTag compoundTag)
            fluidTank.readFromNBT(compoundTag);
        if (compound.contains("filterColor")) {
            this.filterColor = compound.getInt("filterColor");
        }
        if (compound.contains("paintState")) {
            this.paintState = NbtUtils.readBlockState(this.level.holderLookup(Registries.BLOCK), compound.getCompound("paintState"));
        }
        if (compound.contains("paintState")) {
            this.paintState = NbtUtils.readBlockState(
                    net.minecraft.core.registries.BuiltInRegistries.BLOCK.asLookup(),
                    compound.getCompound("paintState")
            );
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.stacks);
        }
        compound.put("paintState", NbtUtils.writeBlockState(this.paintState));
        compound.put("fluidTank", fluidTank.writeToNBT(new CompoundTag()));
        compound.putInt("filterColor", this.filterColor);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public int getContainerSize() {
        return stacks.size();
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
        return Component.literal("painted_universal_fluid_duct");
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return ChestMenu.threeRows(id, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Painted Universal Fluid Duct");
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
    public final FluidTank fluidTank = new FluidTank(10000) {

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (!isFluidValid(resource)) return 0;
            return super.fill(resource, action);
        }

        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 2);
            }
        }
    };

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == ForgeCapabilities.ITEM_HANDLER)
            return handlers[facing.ordinal()].cast();
        if (!this.remove && capability == ForgeCapabilities.FLUID_HANDLER)
            return LazyOptional.of(() -> fluidTank).cast();
        return super.getCapability(capability, facing);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        for (LazyOptional<? extends IItemHandler> handler : handlers)
            handler.invalidate();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PaintableCoatedUniversalFluidDuctBlockEntity entity) {
        if (level.isClientSide) return;

        if (entity.fluidTank.getFluidAmount() > 0) {
            entity.distributeFluid();
        }
    }

    private void distributeFluid() {
        int maxTransfer = 1000;

        for (Direction direction : Direction.values()) {
            if (fluidTank.getFluidAmount() <= 0) break;

            BlockPos neighborPos = worldPosition.relative(direction);
            BlockEntity neighborBE = level.getBlockEntity(neighborPos);

            if (neighborBE != null) {
                neighborBE.getCapability(ForgeCapabilities.FLUID_HANDLER, direction.getOpposite()).ifPresent(handler -> {

                    int myAmount = fluidTank.getFluidAmount();
                    int neighborAmount = handler.getFluidInTank(0).getAmount();

                    if (myAmount > neighborAmount) {
                        int difference = myAmount - neighborAmount;
                        int toMove = Math.min(difference / 2, maxTransfer);

                        if (toMove > 0) {
                            FluidStack moveStack = new FluidStack(fluidTank.getFluid(), toMove);

                            int accepted = handler.fill(moveStack, IFluidHandler.FluidAction.EXECUTE);

                            if (accepted > 0) {
                                fluidTank.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                            }
                        }
                    }
                });
            }
        }
    }
}
