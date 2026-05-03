package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class HydraulicFrackingTowerBlock extends BaseEntityBlock{
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public HydraulicFrackingTowerBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, net.minecraft.world.item.ItemStack stack) {
        super.setPlacedBy(level, pos, state, entity, stack);
        if (!level.isClientSide) {
            setBlockGenericBoundingBox(level, pos.above(1), pos);
            setBlockGenericBoundingBox(level, pos.above(2), pos);

            for (int i = 0; i < 2; i++) {
                int yOff = i;
                setBlockGenericBoundingBox(level, pos.offset(-3, yOff, -3), pos);
                setBlockGenericBoundingBox(level, pos.offset(-2, yOff, -3), pos);
                setBlockGenericBoundingBox(level, pos.offset(-2, yOff, -2), pos);
                setBlockGenericBoundingBox(level, pos.offset(-3, yOff, -2), pos);

                setBlockGenericBoundingBox(level, pos.offset(3, yOff, 3), pos);
                setBlockGenericBoundingBox(level, pos.offset(2, yOff, 3), pos);
                setBlockGenericBoundingBox(level, pos.offset(2, yOff, 2), pos);
                setBlockGenericBoundingBox(level, pos.offset(3, yOff, 2), pos);

                setBlockGenericBoundingBox(level, pos.offset(-3, yOff, 3), pos);
                setBlockGenericBoundingBox(level, pos.offset(-2, yOff, 3), pos);
                setBlockGenericBoundingBox(level, pos.offset(-2, yOff, 2), pos);
                setBlockGenericBoundingBox(level, pos.offset(-3, yOff, 2), pos);

                setBlockGenericBoundingBox(level, pos.offset(3, yOff, -3), pos);
                setBlockGenericBoundingBox(level, pos.offset(2, yOff, -3), pos);
                setBlockGenericBoundingBox(level, pos.offset(2, yOff, -2), pos);
                setBlockGenericBoundingBox(level, pos.offset(3, yOff, -2), pos);
            }
            for (int x = -3; x <= 3; x++) {
                for (int y = 0; y < 2; y++) {
                    for (int z = -3; z <= 3; z++) {
                        BlockPos target = pos.offset(x, y + 2, z);

                        if (target.equals(pos)) continue;

                        level.setBlock(target, ModBlocks.GENERIC_BOUNDING_BOX.get().defaultBlockState(), 3);

                        BlockEntity be = level.getBlockEntity(target);
                        if (be instanceof GenericBoundingBoxBE boundingBE) {
                            boundingBE.setCorePos(pos);
                        }
                    }
                }
            }
            for (int x = -2; x <= 2; x++) {
                for (int y = 0; y < 7; y++) {
                    for (int z = -2; z <= 2; z++) {
                        BlockPos target = pos.offset(x, y + 4, z);

                        if (target.equals(pos)) continue;

                        level.setBlock(target, ModBlocks.GENERIC_BOUNDING_BOX.get().defaultBlockState(), 3);

                        BlockEntity be = level.getBlockEntity(target);
                        if (be instanceof GenericBoundingBoxBE boundingBE) {
                            boundingBE.setCorePos(pos);
                        }
                    }
                }
            }
            for (int x = -1; x <= 1; x++) {
                for (int y = 0; y < 14; y++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos target = pos.offset(x, y + 11, z);

                        if (target.equals(pos)) continue;

                        level.setBlock(target, ModBlocks.GENERIC_BOUNDING_BOX.get().defaultBlockState(), 3);

                        BlockEntity be = level.getBlockEntity(target);
                        if (be instanceof GenericBoundingBoxBE boundingBE) {
                            boundingBE.setCorePos(pos);
                        }
                    }
                }
            }
        }
    }

    public void setBlockGenericBoundingBox(Level level, BlockPos pos, BlockPos corePos) {
        level.setBlock(pos, ModBlocks.GENERIC_BOUNDING_BOX.get().defaultBlockState(), 3);
        if (level.getBlockEntity(pos) instanceof GenericBoundingBoxBE boundingBE) {
            boundingBE.setCorePos(corePos);
        }
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
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
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof HydraulicFrackingTowerBlockEntity) {
                ((HydraulicFrackingTowerBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof HydraulicFrackingTowerBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (HydraulicFrackingTowerBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HydraulicFrackingTowerBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntites.HYDRAULIC_FRACKING_TOWER_ENTITY.get(), HydraulicFrackingTowerBlockEntity::tick);
    }
}
