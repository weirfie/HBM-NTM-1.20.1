package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class ButtonClickedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        boolean found = false;
        double sx = 0;
        double sy = 0;
        double sz = 0;
        sx = -30;
        found = false;
        for (int index0 = 0; index0 < 60; index0++) {
            sy = -30;
            for (int index1 = 0; index1 < 60; index1++) {
                sz = -30;
                for (int index2 = 0; index2 < 60; index2++) {
                    if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() == ModBlocks.CONTROL_ROD.get()) {
                        if ((world.getBlockState(BlockPos.containing(Math.floor(x + sx), Math.floor(y + sy) + 1, Math.floor(z + sz)))).getBlock() == Blocks.AIR) {
                            world.setBlock(BlockPos.containing(Math.floor(x + sx), Math.floor(y + sy) + 1, Math.floor(z + sz)), Blocks.DIORITE_SLAB.defaultBlockState(), 3);
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
