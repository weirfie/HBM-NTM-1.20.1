package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;

public class ScrewdriverItem extends Item {
    public ScrewdriverItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        LevelAccessor world = context.getLevel();
        double x = context.getClickedPos().getX();
        double y = context.getClickedPos().getY();
        double z = context.getClickedPos().getZ();
        if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ModBlocks.CONVEYOR_BELT.get()) {
            world.setBlock(BlockPos.containing(x, y, z), ModBlocks.CONVEYOR_BELT_FACING_Z.get().defaultBlockState(), 3);
            {
                Direction _dir = Direction.NORTH;
                BlockPos _pos = BlockPos.containing(x, y, z);
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
        if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == ModBlocks.CONVEYOR_BELT_FACING_Z.get()) {
            if ((new Object() {
                public Direction getDirection(BlockPos pos) {
                    BlockState _bs = world.getBlockState(pos);
                    Property<?> property = _bs.getBlock().getStateDefinition().getProperty("facing");
                    if (property != null && _bs.getValue(property) instanceof Direction _dir)
                        return _dir;
                    else if (_bs.hasProperty(BlockStateProperties.AXIS))
                        return Direction.fromAxisAndDirection(_bs.getValue(BlockStateProperties.AXIS), Direction.AxisDirection.POSITIVE);
                    else if (_bs.hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
                        return Direction.fromAxisAndDirection(_bs.getValue(BlockStateProperties.HORIZONTAL_AXIS), Direction.AxisDirection.POSITIVE);
                    return Direction.NORTH;
                }
            }.getDirection(BlockPos.containing(x, y, z))) == Direction.NORTH) {
                {
                    Direction _dir = Direction.SOUTH;
                    BlockPos _pos = BlockPos.containing(x, y, z);
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
            } else if ((new Object() {
                public Direction getDirection(BlockPos pos) {
                    BlockState _bs = world.getBlockState(pos);
                    Property<?> property = _bs.getBlock().getStateDefinition().getProperty("facing");
                    if (property != null && _bs.getValue(property) instanceof Direction _dir)
                        return _dir;
                    else if (_bs.hasProperty(BlockStateProperties.AXIS))
                        return Direction.fromAxisAndDirection(_bs.getValue(BlockStateProperties.AXIS), Direction.AxisDirection.POSITIVE);
                    else if (_bs.hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
                        return Direction.fromAxisAndDirection(_bs.getValue(BlockStateProperties.HORIZONTAL_AXIS), Direction.AxisDirection.POSITIVE);
                    return Direction.NORTH;
                }
            }.getDirection(BlockPos.containing(x, y, z))) == Direction.SOUTH) {
                {
                    Direction _dir = Direction.WEST;
                    BlockPos _pos = BlockPos.containing(x, y, z);
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
            } else if ((new Object() {
                public Direction getDirection(BlockPos pos) {
                    BlockState _bs = world.getBlockState(pos);
                    Property<?> property = _bs.getBlock().getStateDefinition().getProperty("facing");
                    if (property != null && _bs.getValue(property) instanceof Direction _dir)
                        return _dir;
                    else if (_bs.hasProperty(BlockStateProperties.AXIS))
                        return Direction.fromAxisAndDirection(_bs.getValue(BlockStateProperties.AXIS), Direction.AxisDirection.POSITIVE);
                    else if (_bs.hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
                        return Direction.fromAxisAndDirection(_bs.getValue(BlockStateProperties.HORIZONTAL_AXIS), Direction.AxisDirection.POSITIVE);
                    return Direction.NORTH;
                }
            }.getDirection(BlockPos.containing(x, y, z))) == Direction.WEST) {
                {
                    Direction _dir = Direction.EAST;
                    BlockPos _pos = BlockPos.containing(x, y, z);
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
            } else if ((new Object() {
                public Direction getDirection(BlockPos pos) {
                    BlockState _bs = world.getBlockState(pos);
                    Property<?> property = _bs.getBlock().getStateDefinition().getProperty("facing");
                    if (property != null && _bs.getValue(property) instanceof Direction _dir)
                        return _dir;
                    else if (_bs.hasProperty(BlockStateProperties.AXIS))
                        return Direction.fromAxisAndDirection(_bs.getValue(BlockStateProperties.AXIS), Direction.AxisDirection.POSITIVE);
                    else if (_bs.hasProperty(BlockStateProperties.HORIZONTAL_AXIS))
                        return Direction.fromAxisAndDirection(_bs.getValue(BlockStateProperties.HORIZONTAL_AXIS), Direction.AxisDirection.POSITIVE);
                    return Direction.NORTH;
                }
            }.getDirection(BlockPos.containing(x, y, z))) == Direction.EAST) {
                {
                    Direction _dir = Direction.NORTH;
                    BlockPos _pos = BlockPos.containing(x, y, z);
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
        return super.useOn(context);
    }
}
