package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.core.BlockPos;

public class DrilledGraphiteUpdateTickProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        if ((world.getBlockState(BlockPos.containing(x + 1, y, z))).getBlock() == ModBlocks.DRILLED_GRAPHITE_NEUTRON_SOURCE.get()) {
            if (world instanceof Level _level && !_level.isClientSide())
                _level.explode(null, x, y, z, 4, Level.ExplosionInteraction.BLOCK);
        }
    }
}
