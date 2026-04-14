package net.StrayBead.hbm_ntm.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

import java.util.Map;

public class SteelDecoBlock extends Block {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;
    public static final BooleanProperty UP = BlockStateProperties.UP;
    public static final BooleanProperty DOWN = BlockStateProperties.DOWN;

    public SteelDecoBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false).setValue(EAST, false)
                .setValue(SOUTH, false).setValue(WEST, false)
                .setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        return this.defaultBlockState()
                .setValue(NORTH, level.getBlockState(pos.north()).is(this))
                .setValue(SOUTH, level.getBlockState(pos.south()).is(this))
                .setValue(EAST, level.getBlockState(pos.east()).is(this))
                .setValue(WEST, level.getBlockState(pos.west()).is(this))
                .setValue(UP, level.getBlockState(pos.above()).is(this))
                .setValue(DOWN, level.getBlockState(pos.below()).is(this));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                  LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        boolean isSame = neighborState.is(this);
        return switch (direction) {
            case NORTH -> state.setValue(NORTH, isSame);
            case SOUTH -> state.setValue(SOUTH, isSame);
            case EAST -> state.setValue(EAST, isSame);
            case WEST -> state.setValue(WEST, isSame);
            case UP -> state.setValue(UP, isSame);
            case DOWN -> state.setValue(DOWN, isSame);
        };
    }
}