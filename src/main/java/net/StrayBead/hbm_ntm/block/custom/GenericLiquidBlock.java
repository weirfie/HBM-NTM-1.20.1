package net.StrayBead.hbm_ntm.block.custom;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

public class GenericLiquidBlock extends LiquidBlock {
    public GenericLiquidBlock(Supplier<? extends FlowingFluid> fluid, MapColor color) {
        super(fluid, BlockBehaviour.Properties.of()
                .mapColor(color)
                .strength(100f)
                .noCollission()
                .noLootTable()
                .liquid()
                .pushReaction(PushReaction.DESTROY)
                .sound(SoundType.EMPTY)
                .replaceable());
    }
}
