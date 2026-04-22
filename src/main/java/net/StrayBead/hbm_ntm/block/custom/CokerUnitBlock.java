package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class CokerUnitBlock extends BaseEntityBlock {
    public CokerUnitBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, net.minecraft.world.item.ItemStack stack) {
        super.setPlacedBy(level, pos, state, entity, stack);
        if (!level.isClientSide) {
            level.setBlock(BlockPos.containing(pos.getX() + 2, pos.getY(), pos.getZ() + 2), ModBlocks.GENERIC_BOUNDING_BOX.get().defaultBlockState(), 3);
            BlockEntity targetBe1 = level.getBlockEntity(BlockPos.containing(pos.getX() + 2, pos.getY(), pos.getZ() + 2));
            if (targetBe1 instanceof GenericBoundingBoxBE boundingBE) {
                boundingBE.setCorePos(pos);
            }
            level.setBlock(BlockPos.containing(pos.getX() - 2, pos.getY(), pos.getZ() + 2), ModBlocks.GENERIC_BOUNDING_BOX.get().defaultBlockState(), 3);
            BlockEntity targetBe2 = level.getBlockEntity(BlockPos.containing(pos.getX() - 2, pos.getY(), pos.getZ() + 2));
            if (targetBe2 instanceof GenericBoundingBoxBE boundingBE) {
                boundingBE.setCorePos(pos);
            }
            level.setBlock(BlockPos.containing(pos.getX() - 2, pos.getY(), pos.getZ() - 2), ModBlocks.GENERIC_BOUNDING_BOX.get().defaultBlockState(), 3);
            BlockEntity targetBe3 = level.getBlockEntity(BlockPos.containing(pos.getX() - 2, pos.getY(), pos.getZ() - 2));
            if (targetBe3 instanceof GenericBoundingBoxBE boundingBE) {
                boundingBE.setCorePos(pos);
            }
            level.setBlock(BlockPos.containing(pos.getX() + 2, pos.getY(), pos.getZ() - 2), ModBlocks.GENERIC_BOUNDING_BOX.get().defaultBlockState(), 3);
            BlockEntity targetBe4 = level.getBlockEntity(BlockPos.containing(pos.getX() + 2, pos.getY(), pos.getZ() - 2));
            if (targetBe4 instanceof GenericBoundingBoxBE boundingBE) {
                boundingBE.setCorePos(pos);
            }

            for (int x = -1; x <= 1; x++) {
                for (int y = 0; y < 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos target = pos.offset(x, y, z);

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
                for (int y = 0; y < 6; y++) {
                    for (int z = -2; z <= 2; z++) {
                        BlockPos target = pos.offset(x, y + 1, z);

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
                for (int y = 0; y < 16; y++) {
                    for (int z = -1; z <= 1; z++) {
                        BlockPos target = pos.offset(x, y + 7, z);

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

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof CokerUnitBlockEntity) {
                ((CokerUnitBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof CokerUnitBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (CokerUnitBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CokerUnitBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntites.COKER_UNIT.get(), CokerUnitBlockEntity::tick);
    }
}
