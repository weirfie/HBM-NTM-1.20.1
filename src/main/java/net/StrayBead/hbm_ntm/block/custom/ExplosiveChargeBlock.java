package net.StrayBead.hbm_ntm.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ExplosiveChargeBlock extends Block {
    public ExplosiveChargeBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void neighborChanged(BlockState blockState, Level world, BlockPos pos, Block neighborBlock, BlockPos fromPos, boolean moving) {
        super.neighborChanged(blockState, world, pos, neighborBlock, fromPos, moving);
        if (world.getBestNeighborSignal(pos) > 0) {
            if (world instanceof ServerLevel level) {
                Explosion.create(level, pos.getX(), pos.getY(), pos.getZ(), 4f, 600);
            }
        }
    }
}
