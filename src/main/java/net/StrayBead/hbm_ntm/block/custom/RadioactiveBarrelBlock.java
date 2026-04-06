package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RadioactiveBarrelBlock extends Block {
    public RadioactiveBarrelBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
        return 1f;
    }

    @Override
    public void wasExploded(Level world, BlockPos pos, Explosion e) {
        super.wasExploded(world, pos, e);
        double sx = 0;
        double sy = 0;
        double sz = 0;
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        sx = -30;
        for (int index0 = 0; index0 < 60; index0++) {
            sy = -30;
            for (int index1 = 0; index1 < 60; index1++) {
                sz = -30;
                for (int index2 = 0; index2 < 60; index2++) {
                    if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() == ModBlocks.URANIUM_ORE.get()) {
                        if (Mth.nextInt(RandomSource.create(), 1, 100) == 1) {
                            world.setBlock(BlockPos.containing(x + sx, y + sy, z + sz), ModBlocks.SCHRABIDIUM_ORE.get().defaultBlockState(), 3);
                        }
                    }
                    sz = sz + 1;
                }
                sy = sy + 1;
            }
            sx = sx + 1;
        }
    }
}
