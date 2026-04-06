package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.potion.ModMobEffects;
import net.StrayBead.hbm_ntm.render.custom.ParticleManager;
import net.StrayBead.hbm_ntm.render.custom.RadiationParticleManager;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class TitaniumOreBlock extends Block {
    public TitaniumOreBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void onPlace(BlockState p_60566_, Level level, BlockPos pos, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, level, pos, p_60569_, p_60570_);
        level.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        super.tick(state, level, pos, random);
        if (Mth.nextInt(RandomSource.create(), 1, 100) < 5) {
            float rand1 = Mth.nextInt(RandomSource.create(), -200, 200);
            float rand2 = Mth.nextInt(RandomSource.create(), -200, 200);
            RadiationParticleManager.addParticle(pos.getX() + rand1, level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) (pos.getX() + rand1), (int) (pos.getZ() + rand2)) + 4, pos.getZ() + rand2, Mth.nextFloat(RandomSource.create(), 1, 20), 120f / 255f, 155f / 255f, 115f / 255f, 0.0f, 0, 0, 0, Mth.nextInt(RandomSource.create(), 400, 1000), 0.0f, false, false, false, false, true);
        }

        if (level.getGameTime() % 5 == 0) {
            double range = 200.0;
            AABB area = new AABB(pos).inflate(range);
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area);

            for (LivingEntity entity : entities) {
                double distance = Math.sqrt(entity.distanceToSqr(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));

                if (distance <= range) {
                    double ratio = 1.0 - (distance / range);
                    int amplifier = (int) Math.round(20.0 * Math.pow(ratio, 3));

                    entity.addEffect(new MobEffectInstance(ModMobEffects.RADIATION_POISONING.get(), 20, amplifier, false, true));
                }
            }
        }
        level.scheduleTick(pos, this, 1);
    }
}
