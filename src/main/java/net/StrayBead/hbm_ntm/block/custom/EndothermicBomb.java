package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.render.custom.NuclearExplosion;
import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.List;

public class EndothermicBomb extends Block {
    public EndothermicBomb(Properties properties) {
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
            triggerFreeze((ServerLevel) level, pos);
            level.removeBlock(pos, false);
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 5.0f, Level.ExplosionInteraction.BLOCK);
        }
    }

    private void triggerFreeze(ServerLevel level, BlockPos center) {
        int radius = 15;
        double radiusSq = radius * radius;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    double distSq = (x * x) + (y * y) + (z * z);

                    if (distSq <= radiusSq) {
                        double chance = 1.0 - (distSq / radiusSq);
                        if (level.random.nextDouble() < (chance + 0.15)) {

                            BlockPos currentPos = center.offset(x, y, z);
                            BlockState state = level.getBlockState(currentPos);

                            if (state.is(Blocks.GRASS_BLOCK)) {
                                level.setBlock(currentPos, Blocks.SNOW_BLOCK.defaultBlockState(), 3);
                            }
                            else if (state.is(Blocks.WATER) || state.getFluidState().isSource()) {
                                level.setBlock(currentPos, Blocks.ICE.defaultBlockState(), 3);
                            }
                        }
                    }
                }
            }
        }

        AABB area = new AABB(center).inflate(radius);
        List<Entity> entities = level.getEntities(null, area);

        for (Entity target : entities) {
            BlockPos entityPos = target.blockPosition();

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = 0; dy <= 2; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {

                        BlockPos icePos = entityPos.offset(dx, dy, dz);

                        if (level.getBlockState(icePos).isAir()) {
                            level.setBlock(icePos, Blocks.ICE.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }
    }
}
