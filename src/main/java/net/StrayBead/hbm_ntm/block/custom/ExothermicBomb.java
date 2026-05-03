package net.StrayBead.hbm_ntm.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class ExothermicBomb extends Block {
    public ExothermicBomb(Properties properties) {
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean isMoving) {
        checkRedstonePower(level, pos);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        checkRedstonePower(level, pos);
    }

    private void checkRedstonePower(Level level, BlockPos pos) {
        if (!level.isClientSide && level.hasNeighborSignal(pos)) {
            triggerScorch((ServerLevel) level, pos);
            level.removeBlock(pos, false);
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 5.0f, Level.ExplosionInteraction.BLOCK);
        }
    }

    private void triggerScorch(ServerLevel level, BlockPos center) {
        int radius = 15;
        double radiusSq = radius * radius;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    double distSq = (x * x) + (y * y) + (z * z);

                    if (distSq <= radiusSq) {
                        double chance = 1.0 - (distSq / radiusSq);

                        if (level.random.nextDouble() < (chance + 0.2)) {
                            BlockPos currentPos = center.offset(x, y, z);
                            BlockState state = level.getBlockState(currentPos);

                            if (state.is(Blocks.GRASS_BLOCK)) {
                                level.setBlock(currentPos, Blocks.DIRT.defaultBlockState(), 3);
                            }
                            else if (state.is(Blocks.DIRT)) {
                                if (level.random.nextFloat() < 0.1f) {
                                    level.setBlock(currentPos, Blocks.LAVA.defaultBlockState(), 3);
                                } else {
                                    level.setBlock(currentPos, Blocks.NETHERRACK.defaultBlockState(), 3);
                                }
                            }
                            else if (state.is(Blocks.TALL_GRASS) || state.is(Blocks.OAK_LEAVES)) {
                                level.setBlock(currentPos, Blocks.AIR.defaultBlockState(), 3);
                            }
                        }
                    }
                }
            }
        }

        AABB area = new AABB(center).inflate(radius);
        List<Entity> entities = level.getEntities(null, area);

        for (Entity target : entities) {
            if (target instanceof LivingEntity living) {
                living.setSecondsOnFire(10);
            }
        }
    }
}
