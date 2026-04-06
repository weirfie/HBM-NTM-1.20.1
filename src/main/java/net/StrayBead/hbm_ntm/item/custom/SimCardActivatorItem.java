package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.potion.ModMobEffects;
import net.StrayBead.hbm_ntm.potion.custom.HasSimCardMobEffect;
import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class SimCardActivatorItem extends Item {
    public SimCardActivatorItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        InteractionResultHolder<ItemStack> ar = super.use(world, entity, hand);
        world.playSound(null, entity.blockPosition(), ModSounds.SIM_CARD_ACTIVATION.get(), SoundSource.MASTER, 10f, 1f);
        HBMNTM.queueServerWork(260, () -> {
            world.playSound(null, entity.blockPosition(), ModSounds.FREE_BIRD.get(), SoundSource.MASTER, 10f, 1f);
            {
                final Vec3 _center = new Vec3(entity.getX(), entity.getY(), entity.getZ());
                List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(200 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
                for (Entity entityiterator : _entfound) {
                    if (entityiterator instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(ModMobEffects.HAS_SIM_CARD.get())) {
                        if (ModMobEffects.HAS_SIM_CARD.get() instanceof HasSimCardMobEffect effect) {
                            effect.setAggressive(true);
                            HBMNTM.queueServerWork(3700, () -> {
                                effect.setExtremelyAggressive(true);
                            });
                        }
                    }
                }
            }
        });
        return ar;
    }
}
