package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ConveyorSplitterBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public ConveyorSplitterBlock(Properties p_49795_) {
        super(p_49795_);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(world, pos, state, placer, stack);

        Direction facing = state.getValue(FACING);

        Direction sideDir = facing.getClockWise();

        BlockPos sidePos = pos.relative(sideDir);

        BlockState secondBlockState = ModBlocks.CONVEYOR_SPLITTER.get().defaultBlockState()
                .setValue(FACING, facing);

        if (world.getBlockState(sidePos).canBeReplaced()) {
            world.setBlock(sidePos, secondBlockState, 3);
        }

        BlockPos behindPos = pos.relative(facing.getOpposite());
        BlockState behindState = world.getBlockState(behindPos);

        if (behindState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            world.setBlock(behindPos, behindState.setValue(BlockStateProperties.HORIZONTAL_FACING, facing), 3);
        }
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, world, pos, oldState, isMoving);
        world.scheduleTick(pos, this, 6);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        Direction[] directions = {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST};

        for (Direction dir : directions) {
            BlockPos checkPos = pos.relative(dir);

            List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, new AABB(checkPos));
            Vec3 beltPos;
            Vec3 beltPos2 = null;

            if (state.getValue(FACING) == Direction.EAST) {
                if (!items.isEmpty()) {
                    ItemEntity itemEntity = items.get(0);
                    ItemStack toInsert = itemEntity.getItem();
                    if ((level.getBlockState(BlockPos.containing(x, y, z - 1))).getBlock() == ModBlocks.CONVEYOR_SPLITTER.get()) {
                        if ((level.getBlockState(BlockPos.containing(x + 1, y, z - 1))).getBlock() == ModBlocks.CONVEYOR_BELT.get()) {
                            beltPos2 = new Vec3(x + 1, y, z - 1);
                        }
                    }
                    if ((level.getBlockState(BlockPos.containing(x + 1, y, z))).getBlock() == ModBlocks.CONVEYOR_BELT.get()) {
                        beltPos = new Vec3(x + 1, y, z);
                        if (beltPos2 == null) {
                            ItemEntity entity = new ItemEntity(level,
                                    beltPos.x + 1, beltPos.y + 0.7, beltPos.z + 0.5,
                                    toInsert);

                            entity.setDeltaMovement(0, 0, 0);
                            level.addFreshEntity(entity);
                            itemEntity.discard();
                        } else {
                            if (Math.random() < 0.5) {
                                ItemEntity entity = new ItemEntity(level,
                                        beltPos2.x + 1, beltPos2.y + 0.7, beltPos2.z + 0.5,
                                        toInsert);

                                entity.setDeltaMovement(0, 0, 0);
                                level.addFreshEntity(entity);
                                itemEntity.discard();
                            } else {
                                ItemEntity entity = new ItemEntity(level,
                                        beltPos.x + 1, beltPos.y + 0.7, beltPos.z + 0.5,
                                        toInsert);

                                entity.setDeltaMovement(0, 0, 0);
                                level.addFreshEntity(entity);
                                itemEntity.discard();
                            }
                        }
                    }
                }
            } else if (state.getValue(FACING) == Direction.NORTH) {
                if (!items.isEmpty()) {
                    ItemEntity itemEntity = items.get(0);
                    ItemStack toInsert = itemEntity.getItem();
                    if ((level.getBlockState(BlockPos.containing(x + 1, y, z))).getBlock() == ModBlocks.CONVEYOR_SPLITTER.get()) {
                        if ((level.getBlockState(BlockPos.containing(x + 1, y, z - 1))).getBlock() == ModBlocks.CONVEYOR_BELT.get()) {
                            beltPos2 = new Vec3(x + 1, y, z - 1);
                        }
                    }
                    if ((level.getBlockState(BlockPos.containing(x, y, z - 1))).getBlock() == ModBlocks.CONVEYOR_BELT.get()) {
                        beltPos = new Vec3(x, y, z - 1);
                        if (beltPos2 == null) {
                            ItemEntity entity = new ItemEntity(level,
                                    beltPos.x + 0.5, beltPos.y + 0.7, beltPos.z - 1,
                                    toInsert);

                            entity.setDeltaMovement(0, 0, 0);
                            level.addFreshEntity(entity);
                            itemEntity.discard();
                        } else {
                            if (Math.random() < 0.5) {
                                ItemEntity entity = new ItemEntity(level,
                                        beltPos2.x + 0.5, beltPos2.y + 0.7, beltPos2.z - 1,
                                        toInsert);

                                entity.setDeltaMovement(0, 0, 0);
                                level.addFreshEntity(entity);
                                itemEntity.discard();
                            } else {
                                ItemEntity entity = new ItemEntity(level,
                                        beltPos.x + 0.5, beltPos.y + 0.7, beltPos.z - 1,
                                        toInsert);

                                entity.setDeltaMovement(0, 0, 0);
                                level.addFreshEntity(entity);
                                itemEntity.discard();
                            }
                        }
                    }
                }
            } else if (state.getValue(FACING) == Direction.WEST) {
                if (!items.isEmpty()) {
                    ItemEntity itemEntity = items.get(0);
                    ItemStack toInsert = itemEntity.getItem();
                    if ((level.getBlockState(BlockPos.containing(x, y, z - 1))).getBlock() == ModBlocks.CONVEYOR_SPLITTER.get()) {
                        if ((level.getBlockState(BlockPos.containing(x - 1, y, z - 1))).getBlock() == ModBlocks.CONVEYOR_BELT.get()) {
                            beltPos2 = new Vec3(x - 1, y, z - 1);
                        }
                    }
                    if ((level.getBlockState(BlockPos.containing(x - 1, y, z))).getBlock() == ModBlocks.CONVEYOR_BELT.get()) {
                        beltPos = new Vec3(x - 1, y, z);
                        if (beltPos2 == null) {
                            ItemEntity entity = new ItemEntity(level,
                                    beltPos.x - 1, beltPos.y + 0.7, beltPos.z + 0.5,
                                    toInsert);

                            entity.setDeltaMovement(0, 0, 0);
                            level.addFreshEntity(entity);
                            itemEntity.discard();
                        } else {
                            if (Math.random() < 0.5) {
                                ItemEntity entity = new ItemEntity(level,
                                        beltPos2.x - 1, beltPos2.y + 0.7, beltPos2.z + 0.5,
                                        toInsert);

                                entity.setDeltaMovement(0, 0, 0);
                                level.addFreshEntity(entity);
                                itemEntity.discard();
                            } else {
                                ItemEntity entity = new ItemEntity(level,
                                        beltPos.x - 1, beltPos.y + 0.7, beltPos.z + 0.5,
                                        toInsert);

                                entity.setDeltaMovement(0, 0, 0);
                                level.addFreshEntity(entity);
                                itemEntity.discard();
                            }
                        }
                    }
                }
            } else if (state.getValue(FACING) == Direction.SOUTH) {
                if (!items.isEmpty()) {
                    ItemEntity itemEntity = items.get(0);
                    ItemStack toInsert = itemEntity.getItem();
                    if ((level.getBlockState(BlockPos.containing(x - 1, y, z))).getBlock() == ModBlocks.CONVEYOR_SPLITTER.get()) {
                        if ((level.getBlockState(BlockPos.containing(x - 1, y, z + 1))).getBlock() == ModBlocks.CONVEYOR_BELT.get()) {
                            beltPos2 = new Vec3(x - 1, y, z + 1);
                        }
                    }
                    if ((level.getBlockState(BlockPos.containing(x, y, z + 1))).getBlock() == ModBlocks.CONVEYOR_BELT.get()) {
                        beltPos = new Vec3(x, y, z + 1);
                        if (beltPos2 == null) {
                            ItemEntity entity = new ItemEntity(level,
                                    beltPos.x + 0.5, beltPos.y + 0.7, beltPos.z + 1,
                                    toInsert);

                            entity.setDeltaMovement(0, 0, 0);
                            level.addFreshEntity(entity);
                            itemEntity.discard();
                        } else {
                            if (Math.random() < 0.5) {
                                ItemEntity entity = new ItemEntity(level,
                                        beltPos2.x + 0.5, beltPos2.y + 0.7, beltPos2.z + 1,
                                        toInsert);

                                entity.setDeltaMovement(0, 0, 0);
                                level.addFreshEntity(entity);
                                itemEntity.discard();
                            } else {
                                ItemEntity entity = new ItemEntity(level,
                                        beltPos.x + 0.5, beltPos.y + 0.7, beltPos.z + 1,
                                        toInsert);

                                entity.setDeltaMovement(0, 0, 0);
                                level.addFreshEntity(entity);
                                itemEntity.discard();
                            }
                        }
                    }
                }
            }
        }

        level.scheduleTick(pos, this, 6);
    }

    private void spawnItem(ServerLevel level, BlockPos pos, ItemStack stack) {
        ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.2, pos.getZ() + 0.5, stack);
        entity.setDeltaMovement(0, 0, 0);
        level.addFreshEntity(entity);
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
}
