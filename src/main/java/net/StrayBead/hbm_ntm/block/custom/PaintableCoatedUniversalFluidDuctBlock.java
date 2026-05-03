package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.custom.entity.CopperCableBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.FluidBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.ModBlockEntites;
import net.StrayBead.hbm_ntm.block.custom.entity.PaintableCoatedUniversalFluidDuctBlockEntity;
import net.StrayBead.hbm_ntm.event.FluidColorRegistry;
import net.StrayBead.hbm_ntm.item.custom.FluidIdentifierItem;
import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class PaintableCoatedUniversalFluidDuctBlock extends BaseEntityBlock {
    public PaintableCoatedUniversalFluidDuctBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
        List<ItemStack> dropsOriginal = super.getDrops(state, builder);
        if (!dropsOriginal.isEmpty())
            return dropsOriginal;
        return Collections.singletonList(new ItemStack(this, 1));
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 2);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        world.scheduleTick(pos, this, 2);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PaintableCoatedUniversalFluidDuctBlockEntity(pos, state);
    }

    @Override
    public BlockState getAppearance(BlockState state, BlockAndTintGetter level, BlockPos pos, Direction side, @Nullable BlockState queryState, @Nullable BlockPos queryPos) {
        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof PaintableCoatedUniversalFluidDuctBlockEntity duct) {
            BlockState paint = duct.getPaintState();
            if (paint != null && !paint.isAir()) {
                return paint;
            }
        }
        return super.getAppearance(state, level, pos, side, queryState, queryPos);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() instanceof FluidIdentifierItem identifier) {
            if (!level.isClientSide) {
                String fluidName = identifier.getFluidName();
                int color = FluidColorRegistry.getColor(fluidName);

                if (player.isCrouching()) {
                    propagateColor(level, pos, color, fluidName, new java.util.HashSet<>());
                    player.displayClientMessage(Component.literal("Line set to: " + fluidName), true);
                } else {
                    BlockEntity be = level.getBlockEntity(pos);
                    if (be instanceof PaintableCoatedUniversalFluidDuctBlockEntity fluidBE) {
                        player.displayClientMessage(Component.literal("Duct set to: " + fluidName), true);
                    }
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        Block heldBlock = Block.byItem(stack.getItem());
        if (heldBlock != Blocks.AIR && PaintableCoatedUniversalFluidDuctBlockEntity.ALLOWED_PAINT_BLOCKS.contains(heldBlock)) {

            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof PaintableCoatedUniversalFluidDuctBlockEntity ductBE) {
                if (!level.isClientSide) {
                    ductBE.paintWith(stack);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        return super.use(state, level, pos, player, hand, hit);
    }

    private void propagateColor(Level level, BlockPos pos, int color, String fluidName, java.util.Set<BlockPos> visited) {
        if (visited.contains(pos) || visited.size() > 512) return;
        visited.add(pos);

        BlockEntity be = level.getBlockEntity(pos);
        if (be instanceof PaintableCoatedUniversalFluidDuctBlockEntity fluidBE) {

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.relative(direction);
                BlockState neighborState = level.getBlockState(neighborPos);

                if (neighborState.getBlock() instanceof PaintableCoatedUniversalFluidDuctBlock) {
                    propagateColor(level, neighborPos, color, fluidName, visited);
                }
            }
        }
    }

    @Override
    public boolean triggerEvent(BlockState state, Level world, BlockPos pos, int eventID, int eventParam) {
        super.triggerEvent(state, world, pos, eventID, eventParam);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity == null ? false : blockEntity.triggerEvent(eventID, eventParam);
    }

    @Override
    public MenuProvider getMenuProvider(BlockState state, Level worldIn, BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        return tileEntity instanceof MenuProvider menuProvider ? menuProvider : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) return null;
        return level.isClientSide ? null : createTickerHelper(type, ModBlockEntites.COATED_FLUID_DUCT.get(),
                PaintableCoatedUniversalFluidDuctBlockEntity::tick);
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PaintableCoatedUniversalFluidDuctBlockEntity be) {
                Containers.dropContents(world, pos, be);
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level world, BlockPos pos) {
        BlockEntity tileentity = world.getBlockEntity(pos);
        if (tileentity instanceof CopperCableBlockEntity be)
            return AbstractContainerMenu.getRedstoneSignalFromContainer(be);
        else
            return 0;
    }
}
