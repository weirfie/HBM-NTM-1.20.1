package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.render.custom.NuclearExplosion;
import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

public class MiningChargeBlock extends Block {
    public MiningChargeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, isMoving);
        checkRedstonePower(level, pos);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        checkRedstonePower(level, pos);
    }

    private void checkRedstonePower(Level level, BlockPos pos) {
        if (!level.isClientSide && level.hasNeighborSignal(pos)) {
            Explosion.create((ServerLevel) level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 2, 700);
            level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 5f, Level.ExplosionInteraction.BLOCK);
            level.removeBlock(pos, false);
        }
    }
}
