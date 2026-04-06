package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class DieselBlock extends LiquidBlock {
    public DieselBlock() {
        super(() -> ModFluids.DIESEL.get(), Properties.of().mapColor(MapColor.WATER).strength(100f).noCollission().noLootTable().liquid().pushReaction(PushReaction.DESTROY).sound(SoundType.EMPTY).replaceable());
    }
}
