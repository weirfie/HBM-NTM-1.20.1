package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.potion.ModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class BlockOfRadium extends Block {
    public BlockOfRadium(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, world, pos, oldState, isMoving);
        world.scheduleTick(pos, this, 20);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        double radius = 20.0;
        int maxAmplifier = 10;

        AABB area = new AABB(pos).inflate(radius);
        List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity entity : entities) {
            double distance = entity.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
            double actualDistance = Math.sqrt(distance);

            if (actualDistance <= radius) {
                double pct = 1.0 - (actualDistance / radius);

                int amplifier = (int) Math.round(pct * maxAmplifier);

                entity.addEffect(new MobEffectInstance(
                        ModMobEffects.RADIATION_POISONING.get(),
                        40,
                        amplifier,
                        false,
                        true
                ));
            }
        }

        world.scheduleTick(pos, this, 20);
    }
}
