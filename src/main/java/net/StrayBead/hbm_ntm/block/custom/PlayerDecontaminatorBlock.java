package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.potion.ModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class PlayerDecontaminatorBlock extends Block {
    public PlayerDecontaminatorBlock(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void onPlace(BlockState p_60566_, Level world, BlockPos pos, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, world, pos, p_60569_, p_60570_);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState p_222945_, ServerLevel world, BlockPos pos, RandomSource p_222948_) {
        super.tick(p_222945_, world, pos, p_222948_);
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        world.sendParticles(ParticleTypes.CLOUD, x, y + 1, z, 3, 0.01, 0.01, 0.01, 0.1);
        world.scheduleTick(pos, this, 1);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState blockstate, Entity entity) {
        super.stepOn(world, pos, blockstate, entity);
        if (entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(ModMobEffects.RADIATION_POISONING.get())) {
            _livEnt0.removeEffect(ModMobEffects.RADIATION_POISONING.get());
        }
    }
}
