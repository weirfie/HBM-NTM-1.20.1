package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ConveyorChainLiftBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public ConveyorChainLiftBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
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
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof ItemEntity || entity instanceof LivingEntity) {
            Direction facing = state.getValue(FACING);

            double liftSpeed = 0.15D;
            double moveSpeed = -0.1D;

            double velX = facing.getStepX() * moveSpeed;
            double velZ = facing.getStepZ() * moveSpeed;

            entity.setDeltaMovement(velX, liftSpeed, velZ);

            centerEntityOnBelt(entity, pos, facing);

            entity.hasImpulse = true;
            entity.fallDistance = 0;
        }
    }

    private void centerEntityOnBelt(Entity entity, BlockPos pos, Direction dir) {
        if (dir.getAxis() == Direction.Axis.Z) {
            double targetX = pos.getX() + 0.5D;
            double deltaX = targetX - entity.getX();
            entity.push(deltaX * 0.1D, 0, 0);
        }
        else if (dir.getAxis() == Direction.Axis.X) {
            double targetZ = pos.getZ() + 0.5D;
            double deltaZ = targetZ - entity.getZ();
            entity.push(0, 0, deltaZ * 0.1D);
        }
    }

    @Override
    public void onPlace(BlockState blockstate, Level world, BlockPos pos, BlockState oldState, boolean moving) {
        super.onPlace(blockstate, world, pos, oldState, moving);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        super.setPlacedBy(world, pos, state, entity, stack);
        if (!world.isClientSide()) {
            if (world.getBlockState(BlockPos.containing(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() == ModBlocks.CONVEYOR_CHAIN_LIFT.get()) {
                world.setBlock(BlockPos.containing(pos.getX(), pos.getY() - 1, pos.getZ()), ModBlocks.CONVEYOR_LIFT.get().defaultBlockState(), 3);
                {
                    Direction _dir = (new Object() {
                        public Direction getDirection(BlockState _bs) {
                            Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
                            if (_prop instanceof DirectionProperty _dp)
                                return _bs.getValue(_dp);
                            _prop = _bs.getBlock().getStateDefinition().getProperty("axis");
                            return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().toArray()[0] instanceof Direction.Axis
                                    ? Direction.fromAxisAndDirection((Direction.Axis) _bs.getValue(_ep), Direction.AxisDirection.POSITIVE)
                                    : Direction.NORTH;
                        }
                    }.getDirection(state));
                    BlockPos _pos = BlockPos.containing(pos.getX(), pos.getY() - 1, pos.getZ());
                    BlockState _bs = world.getBlockState(_pos);
                    Property<?> _property = _bs.getBlock().getStateDefinition().getProperty("facing");
                    if (_property instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(_dir)) {
                        world.setBlock(_pos, _bs.setValue(_dp, _dir), 3);
                    } else {
                        _property = _bs.getBlock().getStateDefinition().getProperty("axis");
                        if (_property instanceof EnumProperty _ap && _ap.getPossibleValues().contains(_dir.getAxis()))
                            world.setBlock(_pos, _bs.setValue(_ap, _dir.getAxis()), 3);
                    }
                }
            }
        }
    }

    @Override
    public void tick(BlockState blockstate, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(blockstate, world, pos, random);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        world.scheduleTick(pos, this, 1);
    }
}
