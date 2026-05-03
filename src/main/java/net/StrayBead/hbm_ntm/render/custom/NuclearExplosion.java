package net.StrayBead.hbm_ntm.render.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public class NuclearExplosion {
    public static void create(ServerLevel world, double x, double y, double z) {
        RandomSource random = RandomSource.create();
        BlockPos center = BlockPos.containing(x, y, z);

        // bottom
        for (int i = 0; i < 400; i++) {
            float theta = random.nextFloat() * ((float)Math.PI * 2f);
            float phi = random.nextFloat() * (float)Math.PI;

            float speed = 2f + random.nextFloat() * 0.5f;

            float dx = Mth.sin(phi) * Mth.cos(theta) * speed;
            float dy = Mth.cos(phi) * speed;
            float dz = Mth.sin(phi) * Mth.sin(theta) * speed;

            float size = 1.0f + random.nextFloat() * 2.0f;
            int life = 80 + random.nextInt(40);

            NuclearParticleManager.addParticle(
                    (float)x, (float)y + Mth.nextInt(RandomSource.create(), -1, 3), (float)z,
                    size,
                    1f, 1f, 1f, 1.0f,
                    dx, 0, dz,
                    life,
                    0,
                    false, false, false, false, false, true
            );
        }

        // stem
        for (int i = 0; i < 300; i++) {
            float theta = random.nextFloat() * ((float)Math.PI * 2f);
            float phi = random.nextFloat() * (float)Math.PI;

            float speed = 0.005f + random.nextFloat() * 0.5f;

            float dx = Mth.sin(phi) * Mth.cos(theta) * speed;
            float dy = Mth.cos(phi) * (1.5f + random.nextFloat() * 0.5f);
            float dz = Mth.sin(phi) * Mth.sin(theta) * speed;

            float size = 1.0f + random.nextFloat() * 2.0f;
            int life = 130 + random.nextInt(40);

            NuclearParticleManager.addParticle(
                    (float)x, (float)y + Mth.nextInt(RandomSource.create(), -5, 3), (float)z,
                    size,
                    1f, 1f, 1f, 1.0f,
                    dx, dy, dz,
                    life,
                    0,
                    false, false, false, false, false, true
            );
        }

        // fireball
        for (int i = 0; i < 350; i++) {
            float theta = random.nextFloat() * ((float)Math.PI * 2f);
            float phi = random.nextFloat() * (float)Math.PI;

            float speed = 0.7f + random.nextFloat() * 0.5f;

            float dx = Mth.sin(phi) * Mth.cos(theta) * speed;
            float dy = Mth.cos(phi) * (0.02f + random.nextFloat() * 0.5f);
            float dz = Mth.sin(phi) * Mth.sin(theta) * speed;

            float size = 1.0f + random.nextFloat() * 2.0f;
            int life = 130 + random.nextInt(40);

            NuclearParticleManager.addParticle(
                    (float)x, (float)y + Mth.nextInt(RandomSource.create(), 4, 8), (float)z,
                    size,
                    1f, 1f, 1f, 1.0f,
                    dx, Mth.nextFloat(RandomSource.create(), 1.2f, 1.4f), dz,
                    life,
                    0,
                    false, false, false, false, false, true
            );
        }

        for (int i = 0; i < 17; i++) {
            world.explode(null, center.getX() + Mth.nextInt(RandomSource.create(), -11, 11), center.getY(), center.getZ() + Mth.nextInt(RandomSource.create(), -11, 11), 10f, true, Level.ExplosionInteraction.BLOCK);
        }
    }
}
