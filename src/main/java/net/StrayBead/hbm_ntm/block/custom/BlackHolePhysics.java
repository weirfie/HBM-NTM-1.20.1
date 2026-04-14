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

@Mod.EventBusSubscriber(modid = "hbm_ntm", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlackHolePhysics {

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.level.isClientSide || event.phase != TickEvent.Phase.END) return;

        ServerLevel level = (ServerLevel) event.level;
        RandomSource random = level.getRandom();

        for (BlackHoleManager.BlackHoleData bh : BlackHoleManager.ACTIVE_HOLES) {
            double pullRadius = bh.radius * 12.0;
            double eventHorizon = bh.radius * 0.8;

            AABB bounds = new AABB(bh.position.subtract(pullRadius, pullRadius, pullRadius),
                    bh.position.add(pullRadius, pullRadius, pullRadius));

            for (Entity entity : level.getEntitiesOfClass(Entity.class, bounds, e -> e.isAlive())) {
                double distSq = entity.position().distanceToSqr(bh.position);
                double distance = Math.sqrt(distSq);

                if (distance < eventHorizon) {
                    entity.hurt(new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 2000);
                    continue;
                }

                Vec3 dir = bh.position.subtract(entity.position()).normalize();

                double strength = (bh.radius * 0.15) / Math.max(distance * 0.5, 1.0);

                Vec3 motion = entity.getDeltaMovement();
                Vec3 newMotion = motion.add(dir.scale(strength)).scale(0.98);

                double maxSpeed = 1.5;
                if (newMotion.length() > maxSpeed) {
                    newMotion = newMotion.normalize().scale(maxSpeed);
                }

                entity.setDeltaMovement(newMotion);
                entity.hasImpulse = true;
                entity.fallDistance = 0;
            }

            int currentFallingBlocks = level.getEntitiesOfClass(FallingBlockEntity.class, bounds).size();

            if (currentFallingBlocks < 40) {
                int attempts = 8;
                for (int i = 0; i < attempts; i++) {
                    BlockPos targetPos = BlockPos.containing(
                            bh.position.x + (random.nextDouble() - 0.5) * pullRadius * 1.2,
                            bh.position.y + (random.nextDouble() - 0.5) * pullRadius * 1.2,
                            bh.position.z + (random.nextDouble() - 0.5) * pullRadius * 1.2
                    );

                    BlockState state = level.getBlockState(targetPos);

                    if (!state.isAir() && state.getDestroySpeed(level, targetPos) >= 0) {
                        FallingBlockEntity falling = FallingBlockEntity.fall(level, targetPos, state);
                        falling.time = 1;
                        falling.dropItem = false;

                        level.setBlock(targetPos, Blocks.AIR.defaultBlockState(), 3);
                        break;
                    }
                }
            }
        }
    }
}
