package net.StrayBead.hbm_ntm.potion.custom;

import net.StrayBead.hbm_ntm.block.custom.entity.FlyingTerrainEntity;
import net.StrayBead.hbm_ntm.potion.ModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class HasSimCardMobEffect extends MobEffect {
    private boolean isAggressive = false;
    private boolean isExtremelyAggressive = false;

    public HasSimCardMobEffect() {
        super(MobEffectCategory.HARMFUL, -3407872);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);
    }

    public void setAggressive(boolean aggressive) {
        this.isAggressive = aggressive;
    }

    public boolean isAggressive() {
        return isAggressive;
    }

    public void setExtremelyAggressive(boolean aggressive) {
        this.isExtremelyAggressive = aggressive;
    }

    public boolean isExtremelyAggressive() {
        return isExtremelyAggressive;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide()) return;

        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();

        if (isAggressive) {
            if (entity.tickCount % 20 == 0) {
                List<LivingEntity> nearby = entity.level().getEntitiesOfClass(
                        LivingEntity.class,
                        entity.getBoundingBox().inflate(15.0),
                        e -> e != entity && !e.hasEffect(ModMobEffects.HAS_SIM_CARD.get())
                );
                for (LivingEntity victim : nearby) {
                    victim.addEffect(new MobEffectInstance(ModMobEffects.HAS_SIM_CARD.get(), 1000000, 1, false, false));
                }
            }

            if (entity instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                if (!serverPlayer.getAbilities().instabuild && !serverPlayer.isSpectator()) {

                    if (serverPlayer.tickCount % 4 == 0) serverPlayer.swing(serverPlayer.getUsedItemHand(), true);
                    serverPlayer.setSprinting(true);
                    serverPlayer.setMaxUpStep(1.25f);

                    LivingEntity target = serverPlayer.level().getNearestEntity(
                            LivingEntity.class,
                            net.minecraft.world.entity.ai.targeting.TargetingConditions.DEFAULT,
                            serverPlayer, x, y, z,
                            serverPlayer.getBoundingBox().inflate(30.0)
                    );

                    if (target != null && target != serverPlayer) {
                        Vec3 dir = target.position().subtract(serverPlayer.position()).normalize();
                        double speed = isExtremelyAggressive ? 1.4 : 1.0;

                        serverPlayer.setDeltaMovement(dir.x * speed, serverPlayer.getDeltaMovement().y, dir.z * speed);
                        serverPlayer.hurtMarked = true;

                        serverPlayer.lookAt(net.minecraft.commands.arguments.EntityAnchorArgument.Anchor.EYES,
                                target.position().add(0, target.getEyeHeight() * 0.8, 0));

                        BlockPos legPos = BlockPos.containing(x + dir.x * 0.8, y, z + dir.z * 0.8);
                        BlockPos headPos = legPos.above();

                        if (!serverPlayer.level().getBlockState(legPos).isAir()) {
                            if (serverPlayer.onGround()) {
                                serverPlayer.jumpFromGround();
                            }
                        }

                        if (!serverPlayer.level().getBlockState(headPos).isAir()) {
                            serverPlayer.level().destroyBlock(legPos, false);
                            serverPlayer.level().destroyBlock(headPos, false);
                        }

                        if (serverPlayer.distanceTo(target) <= 3.5) {
                            float dmg = isExtremelyAggressive ? 10.0f : 4.0f;
                            target.hurt(serverPlayer.damageSources().playerAttack(serverPlayer), dmg);
                            target.knockback(0.5, -dir.x, -dir.z);
                        }
                    }
                }
            }
            else if (entity instanceof Mob mob) {
                LivingEntity mobTarget = entity.level().getNearestEntity(
                        LivingEntity.class,
                        net.minecraft.world.entity.ai.targeting.TargetingConditions.DEFAULT,
                        mob,
                        x, y, z,
                        mob.getBoundingBox().inflate(15.0)
                );

                if (mobTarget != null) {
                    mob.getNavigation().moveTo(mobTarget, isExtremelyAggressive ? 2.0D : 1.5D);

                    if (mob.distanceTo(mobTarget) < 2.0F) {
                        mobTarget.hurt(mob.damageSources().mobAttack(mob), isExtremelyAggressive ? 8f : 0.5f);

                        if (isExtremelyAggressive && Mth.nextInt(entity.getRandom(), 1, 1000) == 1) {
                            entity.level().explode(null, x, y, z, 2f, Level.ExplosionInteraction.NONE);
                        }
                    }
                }
            }
        }
        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}