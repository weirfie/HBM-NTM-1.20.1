package net.StrayBead.hbm_ntm.client;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ShockwaveData {
    public Vec3 center;
    public float radius;
    public float maxRadius;
    public float speed;
    public int age;
    public float alpha = 0.7f;
    public int maxAge;
    public static boolean hasHitPlayer = false;

    private final Set<UUID> hitEntities = new HashSet<>();

    public ShockwaveData(Vec3 center, float maxRadius, int maxAge, float speed) {
        this.center = center;
        this.maxRadius = maxRadius;
        this.maxAge = maxAge;
        this.speed = speed;
        this.radius = 30;
        this.age = 0;
    }

    public void tick(Level level) {
        this.age++;
        if (this.radius > maxRadius - 120) {
            this.alpha *= 0.95f;
        }

        float progress = (float) age / (float) maxAge;
        float invProgress = 1.0f - progress;
        float curve = (1.0f - invProgress * invProgress * invProgress);
        this.radius = Math.min(maxRadius, (maxRadius * curve) * speed);

        if (this.age < maxAge) {
            AABB searchBox = new AABB(center, center).inflate(this.radius);
            List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, searchBox);

            for (LivingEntity entity : targets) {
                double dist = entity.position().distanceTo(this.center);

                if (!hitEntities.contains(entity.getUUID()) && dist <= this.radius) {
                    hitEntities.add(entity.getUUID());

                    if (level.isClientSide) {
                        level.playLocalSound(entity.getX(), entity.getY(), entity.getZ(),
                                ModSounds.NUCLEAR_EXPLOSION.get(), SoundSource.MASTER, 1.0f, 1.0f, false);
                    } else {
                        level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                                ModSounds.NUCLEAR_EXPLOSION.get(), SoundSource.MASTER, 1.0f, 1.0f);
                    }

                    Vec3 pushDir = entity.position().subtract(this.center).normalize();
                    float power = 3.0f * (1.0f - progress);

                    entity.setDeltaMovement(pushDir.x * power, 0.6f, pushDir.z * power);

                    entity.hurtMarked = true;
                    if (entity instanceof Player) {
                        hasHitPlayer = true;
                        HBMNTM.queueServerWork(20, () -> {
                            hasHitPlayer = false;
                        });
                    }
                }
            }
        }
    }
}
