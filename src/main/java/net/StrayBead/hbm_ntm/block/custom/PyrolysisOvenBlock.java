package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.BedrockOreProcessorBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.GenericBoundingBoxBE;
import net.StrayBead.hbm_ntm.block.custom.entity.ModBlockEntites;
import net.StrayBead.hbm_ntm.block.custom.entity.PyrolysisOvenBlockEntity;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class PyrolysisOvenBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 4, 16);

    public PyrolysisOvenBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    private BlockPos rotateOffset(int x, int y, int z, Direction facing) {
        return switch (facing) {
            case NORTH -> new BlockPos(x, y, z);
            case SOUTH -> new BlockPos(-x, y, -z);
            case WEST  -> new BlockPos(z, y, -x);
            case EAST  -> new BlockPos(-z, y, x);
            default -> new BlockPos(x, y, z);
        };
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, net.minecraft.world.item.ItemStack stack) {
        super.setPlacedBy(level, pos, state, entity, stack);
        Direction facing = state.getValue(FACING);

        for (int x = -2; x <= 2; x++) {
            for (int y = 0; y <= 2; y++) {
                for (int z = -3; z <= 3; z++) {

                    BlockPos offset = rotateOffset(x, y, z, facing);
                    BlockPos target = pos.offset(offset);

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

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    @Override
    public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
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
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof PyrolysisOvenBlockEntity) {
                ((PyrolysisOvenBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                 Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof PyrolysisOvenBlockEntity) {
                NetworkHooks.openScreen(((ServerPlayer)pPlayer), (PyrolysisOvenBlockEntity)entity, pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PyrolysisOvenBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntites.PYROLYSIS_OVEN_ENTITY.get(), PyrolysisOvenBlockEntity::tick);
    }
}
