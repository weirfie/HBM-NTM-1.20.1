package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.rbmk.FuelRodBlock;
import net.StrayBead.hbm_ntm.block.custom.rbmk.NormalFuelRodBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class XenonScanProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Player entity) {
        int totalFuelRods = 0;
        int xenonCount = 0;

        int horizontalRange = 70;
        int minY = world.getMinBuildHeight();
        int maxY = world.getMaxBuildHeight();

        double sx = 0;
        double sy = 0;
        double sz = 0;
        sx = -50;
        for (int index0 = 0; index0 < 100; index0++) {
            sy = -50;
            for (int index1 = 0; index1 < 100; index1++) {
                sz = -50;
                for (int index2 = 0; index2 < 100; index2++) {
                    if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() == ModBlocks.FUEL_ROD_NORMAL.get()) {
                        totalFuelRods++;
                        System.out.println(totalFuelRods);
                        if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getValue(NormalFuelRodBlock.ISXENON)) {
                            xenonCount++;
                        }
                    }
                    sz = sz + 1;
                }
                sy = sy + 1;
            }
            sx = sx + 1;
        }

        String result = xenonCount + "/" + totalFuelRods;

        System.out.println("GUI SCAN -> Total: " + totalFuelRods + " | Xenon: " + xenonCount);

        if (entity instanceof ServerPlayer) {
            ControlPanelGuiMenu.guistate.put("xenon_result", result);
        }
    }
}
