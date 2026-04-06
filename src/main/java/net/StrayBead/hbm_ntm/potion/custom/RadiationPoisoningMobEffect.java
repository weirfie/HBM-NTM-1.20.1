package net.StrayBead.hbm_ntm.potion.custom;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

public class RadiationPoisoningMobEffect extends MobEffect {
    public RadiationPoisoningMobEffect() {
        super(MobEffectCategory.HARMFUL, -3407872);
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        super.addAttributeModifiers(entity, attributeMap, amplifier);

        if (entity.level().isClientSide() || entity.isRemoved() || entity instanceof Zombie) return;

        int dynamicDelay = Math.max(10, 100 - (amplifier * 40));

        HBMNTM.queueServerWork(dynamicDelay, () -> {
            if (entity.isAlive() && !entity.isRemoved() && !(entity instanceof Zombie)) {
                if (entity instanceof Player player && player.isCreative()) {
                    return;
                }

                if (amplifier >= 7) {
                    entity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 2000, 1));
                }
                if (amplifier >= 15) {
                    entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 2000, 1));
                }

                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 2000, 3));
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 2000, 3));

                HBMNTM.queueServerWork(1000, () -> {
                    if (!entity.isRemoved() && entity.isAlive() && amplifier >= 4) {
                        if (entity instanceof Villager) {
                            zombify(entity);
                        } else {
                            entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 2000, 1));
                        }
                    }
                });
            }
        });
    }

    private void zombify(LivingEntity entity) {
        if (entity.level() instanceof ServerLevel level) {
            BlockPos pos = entity.blockPosition();

            entity.discard();

            Zombie zombie = EntityType.ZOMBIE.spawn(level, pos, MobSpawnType.MOB_SUMMONED);
            if (zombie != null) {
                zombie.setYRot(level.getRandom().nextFloat() * 360F);
                zombie.removeEffect(this);
            }
        }
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!entity.level().isClientSide() && !entity.isRemoved() && !(entity instanceof Zombie)) {
            if (entity instanceof Player player && player.isCreative()) {
                return;
            }

            if (amplifier >= 20) {
                entity.hurt(entity.damageSources().magic(), 2.0F);
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 1));
                entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1));

                if (entity instanceof Villager) {
                    if (Math.random() < 0.3) {
                        zombify(entity);
                        return;
                    }
                }
            } else if (amplifier >= 1) {
                entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 80, 0));
            } else {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 0));
            }
        }
        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }
}