package net.StrayBead.hbm_ntm.block.custom;

import net.minecraft.world.phys.Vec3;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlackHoleManager {
    public static final CopyOnWriteArrayList<BlackHoleData> ACTIVE_HOLES = new CopyOnWriteArrayList<>();

    public static class BlackHoleData {
        public final Vec3 position;
        public final float radius;

        public BlackHoleData(Vec3 position, float radius) {
            this.position = position;
            this.radius = radius;
        }
    }

    public static void spawn(Vec3 pos, float radius) {
        ACTIVE_HOLES.add(new BlackHoleData(pos, radius));
    }
}
