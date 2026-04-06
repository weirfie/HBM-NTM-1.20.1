package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class CoriumBlock extends LiquidBlock {
    public CoriumBlock() {
        super(() -> ModFluids.CORIUM.get(), BlockBehaviour.Properties.of().mapColor(MapColor.FIRE).strength(100f).noCollission().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
    }

    @Override
    public void onPlace(BlockState p_54754_, Level level, BlockPos pos, BlockState p_54757_, boolean p_54758_) {
        super.onPlace(p_54754_, level, pos, p_54757_, p_54758_);
        level.scheduleTick(pos, this, 1);
    }

    @Override
    public void tick(BlockState p_222945_, ServerLevel level, BlockPos pos, RandomSource p_222948_) {
        super.tick(p_222945_, level, pos, p_222948_);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos neighborPos = pos.relative(direction);

            if (level.getBlockState(neighborPos).getBlock() == ModBlocks.DUCRETE_BRICKS.get()) {
                level.setBlock(neighborPos, Blocks.AIR.defaultBlockState(), 3);
            }
            if (level.getBlockState(neighborPos).getBlock() == ModBlocks.PWR_NEUTRON_REFLECTOR.get()) {
                level.setBlock(neighborPos, Blocks.AIR.defaultBlockState(), 3);
            }
            if (level.getBlockState(neighborPos).getBlock() == ModBlocks.PWR_CONTROL_ROD.get()) {
                level.setBlock(neighborPos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
        level.scheduleTick(pos, this, 1);
    }

    @Override
    public void entityInside(BlockState p_60495_, Level p_60496_, BlockPos p_60497_, Entity entity) {
        super.entityInside(p_60495_, p_60496_, p_60497_, entity);
        entity.setSecondsOnFire(2);
        entity.setDeltaMovement(entity.getDeltaMovement().scale(0.1D));
    }
}
