package net.StrayBead.hbm_ntm.block.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class UraniumOre extends Block {
    public UraniumOre(Properties p_49795_) {
        super(p_49795_);
    }

    @Override
    public void stepOn(Level level, BlockPos p_152432_, BlockState p_152433_, Entity entity) {
        super.stepOn(level, p_152432_, p_152433_, entity);
        if (!level.isClientSide) {
            entity.sendSystemMessage(Component.literal("My lungs are burning.")
                    .withStyle(ChatFormatting.RED));
        }
    }
}
