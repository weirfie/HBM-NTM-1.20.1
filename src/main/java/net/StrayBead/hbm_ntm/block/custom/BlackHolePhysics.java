package net.StrayBead.hbm_ntm.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "hbm_ntm", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlackHolePhysics {

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.level.isClientSide || event.phase != TickEvent.Phase.END) return;

        ServerLevel level = (ServerLevel) event.level;
        long gameTime = level.getGameTime();
        RandomSource random = level.getRandom();

        for (BlackHoleManager.BlackHoleData bh : BlackHoleManager.ACTIVE_HOLES) {
            // Expansion of the pull and event horizon radii
            double pullRadius = bh.radius * 20.0;
            double eventHorizon = bh.radius * 1.2;
            double eventHorizonSq = eventHorizon * eventHorizon;

            AABB pullBounds = new AABB(
                    bh.position.x - pullRadius, bh.position.y - pullRadius, bh.position.z - pullRadius,
                    bh.position.x + pullRadius, bh.position.y + pullRadius, bh.position.z + pullRadius
            );

            List<Entity> entities = level.getEntitiesOfClass(Entity.class, pullBounds, Entity::isAlive);
            int fallingBlockCount = 0;

            for (Entity entity : entities) {
                double distSq = entity.position().distanceToSqr(bh.position);

                // 1. CONSUMPTION LOGIC
                if (distSq < eventHorizonSq) {
                    entity.discard();
                    continue;
                }

                if (entity instanceof FallingBlockEntity fb) {
                    fallingBlockCount++;

                    // Reset time to prevent solidification/despawning
                    fb.time = 1;

                    // If a falling block somehow stops moving (stuck), eat it anyway to clear the buffer
                    if (fb.getDeltaMovement().lengthSqr() < 0.001) {
                        fb.discard();
                        continue;
                    }
                }

                // 2. PULL PHYSICS
                double distance = Math.sqrt(distSq);
                Vec3 dir = bh.position.subtract(entity.position()).normalize();

                // Increased strength slightly to ensure they reach the center
                double strength = (bh.radius * 0.3) / Math.max(distance * 0.5, 1.0);

                Vec3 motion = entity.getDeltaMovement();
                // Physics: add pull vector, apply air resistance (scale)
                entity.setDeltaMovement(motion.add(dir.scale(strength)).scale(0.95));
                entity.hasImpulse = true;
                entity.fallDistance = 0;
            }

            // 3. SPAWNING LOGIC (Throttled for performance)
            if (gameTime % 2 == 0 && fallingBlockCount < 80) {
                int maxGrab = 4;
                int grabbed = 0;
                int attempts = 40;

                for (int i = 0; i < attempts && grabbed < maxGrab; i++) {
                    BlockPos targetPos = BlockPos.containing(
                            bh.position.x + (random.nextDouble() - 0.5) * pullRadius * 1.5,
                            bh.position.y + (random.nextDouble() - 0.5) * pullRadius * 1.5,
                            bh.position.z + (random.nextDouble() - 0.5) * pullRadius * 1.5
                    );

                    if (level.hasChunkAt(targetPos)) {
                        BlockState state = level.getBlockState(targetPos);

                        if (!state.isAir() && state.getDestroySpeed(level, targetPos) >= 0) {

                            FallingBlockEntity falling = FallingBlockEntity.fall(level, targetPos, state);

                            if (falling != null) {
                                falling.time = 1;
                                falling.dropItem = false;
                                falling.setNoGravity(true);

                                falling.setPos(targetPos.getX() + 0.5D, targetPos.getY(), targetPos.getZ() + 0.5D);

                                level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 3);

                                grabbed++;
                            }
                        }
                    }
                }
            }
        }
    }
}