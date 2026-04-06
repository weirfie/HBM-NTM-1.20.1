package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.AssemblyMachineBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.CatalyticCrackingTowerBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.GenericBoundingBoxBE;
import net.StrayBead.hbm_ntm.block.custom.entity.ModBlockEntites;
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

public class CatalyticCrackingTowerBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 4, 16);

    public CatalyticCrackingTowerBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, net.minecraft.world.item.ItemStack stack) {
        if (level.isClientSide) return;
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);

        Direction facing = state.getValue(FACING).getOpposite();
        BlockPos actualCorePos = pos.relative(facing, 3);

        BlockEntity oldBe = level.getBlockEntity(pos);
        level.setBlock(actualCorePos, state, 3);

        level.setBlock(pos, ModBlocks.GENERIC_BOUNDING_BOX.get().defaultBlockState(), 3);
        if (level.getBlockEntity(pos) instanceof GenericBoundingBoxBE boundingBE) {
            boundingBE.setCorePos(actualCorePos);
        }

        int depth = 7;
        int width = 7;
        int height = 13;

        for (int y = 0; y < height; y++) {
            for (int x = -width / 2; x <= width / 2; x++) {
                for (int z = -depth / 2; z <= depth / 2; z++) {
                    BlockPos target = actualCorePos.offset(x, y, z);

                    if (target.equals(actualCorePos) || target.equals(pos)) continue;

                    if (level.getBlockState(target).canBeReplaced()) {
                        level.setBlock(target, ModBlocks.GENERIC_BOUNDING_BOX.get().defaultBlockState(), 3);

                        BlockEntity targetBe = level.getBlockEntity(target);
                        if (targetBe instanceof GenericBoundingBoxBE boundingBE) {
                            boundingBE.setCorePos(actualCorePos);
                        }
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
        Direction facing = context.getHorizontalDirection();
        BlockPos clickedPos = context.getClickedPos();
        Level level = context.getLevel();

        BlockPos offsetPos = clickedPos.relative(facing, 4);

        // Check if the area is clear (Optional but recommended)
        if (level.getBlockState(offsetPos).canBeReplaced(context)) {
            // We can't easily change the 'clickedPos' inside this method to return a different block,
            // so we return the default state, and let setPlacedBy handle the relative bounding boxes.
            return this.defaultBlockState().setValue(FACING, facing.getOpposite());
        }

        return this.defaultBlockState().setValue(FACING, facing.getOpposite());
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
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CatalyticCrackingTowerBlockEntity(blockPos, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntites.CATALYTIC_CRACKING_TOWER.get(), CatalyticCrackingTowerBlockEntity::tick);
    }
}
