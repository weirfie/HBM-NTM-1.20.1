package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.custom.entity.FlyingTerrainEntity;
import net.StrayBead.hbm_ntm.block.custom.render.ShockwaveRenderer;
import net.StrayBead.hbm_ntm.render.custom.ExplosionParticleManager;
import net.StrayBead.hbm_ntm.render.custom.ParticleManager;
import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class Explosion {
    public static void create(ServerLevel world, double x, double y, double z, float power, int numParticles) {
        RandomSource random = RandomSource.create();
        BlockPos center = BlockPos.containing(x, y, z);

        float scale = power / 4.0f;

        double fireballY = y + (power * 2.5);
        int scaledFireballParticles = (int) (numParticles * scale * 2.0f);
        for (int i = 0; i < scaledFireballParticles; i++) {
            double offsetX = random.nextGaussian() * power * 1.2;
            double offsetY = random.nextGaussian() * power * 1.0;
            double offsetZ = random.nextGaussian() * power * 1.2;

            float brightness = 0.8f + (random.nextFloat() * 0.2f);
            float r = Math.min(1.0f, 1.0f * brightness);
            float g = Math.min(1.0f, 1.0f * brightness);
            float b = Math.min(1.0f, 0.2f * brightness);

            float particleSize = power * (0.02f + random.nextFloat());

            ExplosionParticleManager.addParticle((float) (x + offsetX), (float) (fireballY + offsetY), (float) (z + offsetZ), particleSize, r, g, b, 1.0f, Mth.nextFloat(random, -0.3f, 0.3f), power * 0.4f, Mth.nextFloat(random, -0.3f, 0.3f), (int) (power * 30), 0.005f, true, 0.2f);
        }

        // --- STEM EXPLOSION AREA ---
        int totalStemParticles = (int) ((numParticles / 2) * scale);
        for (int i = 0; i < totalStemParticles; i++) {
            float lerp = random.nextFloat();
            double segmentY = y + (lerp * (fireballY - y));

            double stemWidth = (power * 0.6) * (1.2 + (lerp * 1.0));
            double offsetX = random.nextGaussian() * stemWidth;
            double offsetZ = random.nextGaussian() * stemWidth;
            double offsetY = random.nextGaussian() * 5;

            float brightness = 0.7f + (random.nextFloat() * 0.3f);
            float r = Math.min(1.0f, 1.0f * brightness);
            float g = Math.min(1.0f, 0.6f * brightness);
            float b = Math.min(1.0f, 0.2f * brightness);

            ExplosionParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) (segmentY + offsetY) - (power * 2),
                    (float) (z + offsetZ),
                    power / 1.5f,
                    r, g, b, 1.0f,
                    Mth.nextFloat(random, -0.3f, 0.3f), power * 0.375f, Mth.nextFloat(random, -0.3f, 0.3f),
                    (int) (power * 30),
                    0.1f,
                    true
            );
        }

        if (power > 8) {
            ShockwaveRenderer.addShockwave(new Vec3(x, y, z), 300f, 200);
        }

        // --- BASE EXPLOSION AREA ---
        int scaledBaseParticles = (int) (numParticles * 4 * scale);
        for (int i = 0; i < scaledBaseParticles; i++) {
            double offsetX = random.nextGaussian() * power * 2;
            double offsetY = random.nextGaussian() * power / 3;
            double offsetZ = random.nextGaussian() * power * 2;

            float baseR = 133f / 255f;
            float baseG = 133f / 255f;
            float baseB = 133f / 255f;

            float brightness = 0.5f + (random.nextFloat() * 0.3f);

            float r = Math.min(1.0f, baseR * brightness);
            float g = Math.min(1.0f, baseG * brightness);
            float b = Math.min(1.0f, baseB * brightness);

            ExplosionParticleManager.addParticle((float) (x + offsetX), (float) ((y + 1) + offsetY), (float) (z + offsetZ), power / 4, r, g, b, 1.0f, Mth.nextFloat(random, -0.8f, 0.8f), 0.05f, Mth.nextFloat(random, -0.8f, 0.8f), (int) (power * 30), 0.01f, true);
        }

        // Initial Shockwave launch
        for (int i = 0; i < 360; i += 30) {
            double radians = Math.toRadians(i);
            double spawnX = center.getX() + Math.cos(radians) * 2;
            double spawnZ = center.getZ() + Math.sin(radians) * 2;
            double spawnY = world.getHeight(Heightmap.Types.MOTION_BLOCKING, (int) x, (int) z);
            spawnMovingOverLandShockwaveParticles(spawnX, spawnY + 1, spawnZ, (float) (power * 1.5), center.getX(), center.getY(), center.getZ(), 5 * scale, 0, 5 * scale, 3, 100);
        }

        // --- EXPANDING RADIUS / GROUND PARTICLES ---
        int maxRadius = (int) (power * 25);
        for (int r = 0; r <= maxRadius; r += 2) {
            final int radius = r;
            int delay = r / 4;
            HBMNTM.queueServerWork(delay, () -> {
                double step = Mth.nextDouble(random, 4 / scale, 10 / scale);
                for (double a = 0; a < 360; a += step) {
                    double radians = Math.toRadians(a);
                    double spawnX = x + Math.cos(radians) * radius;
                    double spawnZ = z + Math.sin(radians) * radius;
                    double spawnY = world.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) spawnX, (int) spawnZ);

                    if (radius < (power * 10)) {
                        float brightness = 0.5f + (random.nextFloat() * 0.3f);
                        float baseR = 0.7f; float baseG = 0.7f; float baseB = 0.7f;
                        float rCol = Math.min(1.0f, baseR * brightness);
                        float gCol = Math.min(1.0f, baseG * brightness);
                        float bCol = Math.min(1.0f, baseB * brightness);

                        {
                            final Vec3 _center = new Vec3(spawnX, spawnY, spawnZ);
                            List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(4 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                            for (Entity entityiterator : _entfound) {
                                if (entityiterator instanceof FlyingTerrainEntity) continue;
                                double dx = entityiterator.getX() - x;
                                double dz = entityiterator.getZ() - z;
                                double distance = Math.sqrt(dx * dx + dz * dz);

                                if (distance > 0) {
                                    double strength = (power * 0.5);
                                    double throwX = (dx / distance) * strength;
                                    double throwZ = (dz / distance) * strength;

                                    double throwY = 0.5 + (power * 0.1);

                                    entityiterator.setDeltaMovement(new Vec3(throwX, throwY, throwZ));

                                    entityiterator.hasImpulse = true;
                                }

                                entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.EXPLOSION)), 50);
                            }
                        }

                        ExplosionParticleManager.addParticle((float) spawnX + Mth.nextFloat(random, -3f * scale, 3f * scale), (float) spawnY + Mth.nextFloat(random, -1f, 4f * scale), (float) spawnZ + Mth.nextFloat(random, -3f * scale, 3f * scale), Mth.nextFloat(random, 1.2f * scale, 3f * scale), rCol, bCol, gCol, 1.0f, 0, 0, 0, (int) (power * 30), 0, true);
                    }
                    world.getServer().getCommands().performPrefixedCommand(new CommandSourceStack(CommandSource.NULL, new Vec3(spawnX, spawnY + 1, spawnZ), Vec2.ZERO, world, 4, "", Component.literal(""), world.getServer(), null).withSuppressedOutput(),
                            "particle minecraft:explosion ~ ~1 ~ 0.01 0.01 0.01 0 1 force @a");
                }
            });
        }

        // --- FLYING TERRAIN ENTITIES ---
        int terrainCount = (int) (200 * (scale * scale));
        terrainCount = Math.min(terrainCount, 1000);
        for (int i = 0; i < terrainCount; i++) {
            double angle = random.nextDouble() * Math.PI * 2;
            double distance = 2 + (random.nextDouble() * power * 3);

            int spawnX = (int)(x + Math.cos(angle) * distance);
            int spawnZ = (int)(z + Math.sin(angle) * distance);
            int spawnY = world.getHeight(Heightmap.Types.MOTION_BLOCKING, spawnX, spawnZ);

            int minSize = (int) (1.5 * scale);
            int maxSize = (int) (5 * scale);
            int cappedMaxSize = Math.min(maxSize, 50);
            int finalSize = Math.max(1, minSize + random.nextInt(Math.max(1, cappedMaxSize)));

            FlyingTerrainEntity chunk = new FlyingTerrainEntity(world, new BlockPos(spawnX, spawnY + 2, spawnZ), finalSize);

            chunk.setDeltaMovement(new Vec3(
                    random.nextGaussian() * 0.3 * scale,
                    (0.4 + random.nextDouble() * 0.7) * scale,
                    random.nextGaussian() * 0.3 * scale
            ));

            world.addFreshEntity(chunk);
        }

        // --- PLAYER EFFECTS (SOUND & SHAKE) ---
        float effectRadius = power * 100.0f; // Baseline 400 at 4.0 power
        for (net.minecraft.world.entity.player.Player player : world.players()) {
            double distance = Math.sqrt(player.distanceToSqr(x, y, z));

            if (distance <= effectRadius) {
                int soundDelay = (int) (distance / 7);

                HBMNTM.queueServerWork(soundDelay, () -> {
                    // Sound volume scales with power
                    world.playSound(null, player.getX(), player.getY(), player.getZ(),
                            ModSounds.EXPLOSION_HIT.get(),
                            net.minecraft.sounds.SoundSource.AMBIENT,
                            2.5f * power,
                            1.0f
                    );

                    for (int j = 0; j < 8; j++) {
                        int iterationDelay = j;

                        HBMNTM.queueServerWork(iterationDelay, () -> {
                            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                                // Shake intensity scales with power and inversely with distance
                                float intensity = (float) ((power / Math.max(1, distance)) * 20.0f);
                                float shakeX = (random.nextFloat() * intensity) - (intensity / 2f);
                                float shakeY = (random.nextFloat() * intensity) - (intensity / 2f);

                                serverPlayer.setXRot(serverPlayer.getXRot() + shakeX);
                                serverPlayer.setYRot(serverPlayer.getYRot() + shakeY);
                            }
                        });
                    }
                });
            }
        }
    }

    public static void spawnMovingOverLandShockwaveParticles(double x, double y, double z, float size, double centerX, double centerY, double centerZ, double dx, double dy, double dz, int count, int maxAge) {
        RandomSource random = RandomSource.create();

        double diffX = x - centerX;
        double diffY = y - centerY;
        double diffZ = z - centerZ;

        double distance = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);

        float vx = distance > 0 ? (float) ((diffX / distance) * 2.5f) : 0.0f;
        float vy = distance > 0 ? (float) ((diffY / distance) * 2.5f) : 0.0f;
        float vz = distance > 0 ? (float) ((diffZ / distance) * 2.5f) : 0.0f;

        for (int i = 0; i < count; i++) {
            float brightness = 0.2f + (random.nextFloat() * 0.3f);

            double offsetX = random.nextGaussian() * dx;
            double offsetY = random.nextGaussian() * dy;
            double offsetZ = random.nextGaussian() * dz;

            float baseR = 0.3f; float baseG = 0.3f; float baseB = 0.3f;
            float r = Math.min(1.0f, baseR * brightness);
            float g = Math.min(1.0f, baseG * brightness);
            float b = Math.min(1.0f, baseB * brightness);
            int age = maxAge + random.nextInt(40);

            ParticleManager.addParticle(
                    (float) (x + offsetX),
                    (float) ((y + 2) + offsetY),
                    (float) (z + offsetZ),
                    size,
                    r, g, b,
                    1f,
                    vx, 0, vz,
                    age, 0.0f, false, false, false, true, false, true
            );
        }
    }
}
