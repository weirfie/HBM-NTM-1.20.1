package net.StrayBead.hbm_ntm.block.custom;

import net.minecraft.world.level.LevelAccessor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RadiatedGraphiteOnBlockAddedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        boolean found = false;
        double sx = 0;
        double sy = 0;
        double sz = 0;
        scheduler.schedule(() -> {
            PlaceBlock.execute(world, x, y, z);
        }, 50000, TimeUnit.MILLISECONDS);
    }
}
