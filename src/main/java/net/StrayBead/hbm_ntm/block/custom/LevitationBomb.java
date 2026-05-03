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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LevitationBomb extends Block {
    public LevitationBomb(Properties properties) {
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
            triggerLevitation(level, pos);
        }
    }

    private void triggerLevitation(Level level, BlockPos center) {
        int radius = 10;
        int heightOffset = 30;

        Map<BlockPos, BlockState> capturedTerrain = new HashMap<>();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    if ((x * x) + (y * y) + (z * z) <= (radius * radius)) {
                        BlockPos currentPos = center.offset(x, y, z);
                        BlockState currentState = level.getBlockState(currentPos);

                        if (!currentState.isAir()) {
                            capturedTerrain.put(new BlockPos(x, y, z), currentState);
                            level.setBlock(currentPos, Blocks.AIR.defaultBlockState(), 3);
                        }
                    }
                }
            }
        }

        capturedTerrain.forEach((relativePos, state) -> {
            BlockPos targetPos = center.offset(relativePos.getX(), relativePos.getY() + heightOffset, relativePos.getZ());

            level.setBlock(targetPos, state, 3);
        });

        level.removeBlock(center, false);
    }
}
