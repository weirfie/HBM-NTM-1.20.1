package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class NuclearSirenBlock extends Block {

    public NuclearSirenBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                Block neighborBlock, BlockPos fromPos, boolean moving) {
        if (!level.isClientSide && level.getBestNeighborSignal(pos) > 0) {
            level.scheduleTick(pos, this, 1);
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.getBestNeighborSignal(pos) <= 0) {
            return;
        }

        level.playSound(null, pos,
                ModSounds.NUCLEAR_SIREN.get(),
                SoundSource.MASTER,
                10f, 1f);

        AABB panicArea = new AABB(pos).inflate(200);
        List<Villager> villagers = level.getEntitiesOfClass(Villager.class, panicArea);

        for (Villager villager : villagers) {
            villager.getBrain().setMemory(
                    MemoryModuleType.HEARD_BELL_TIME,
                    level.getGameTime()
            );
        }

        level.scheduleTick(pos, this, 240);
    }
}