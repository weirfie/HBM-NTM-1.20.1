package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.ThePrototypeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class IgniterItem extends Item {
    public IgniterItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Player player = context.getPlayer();

        if (player != null && player.isShiftKeyDown()) {

            if (state.is(ModBlocks.THE_PROTOTYPE.get())) {

                if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
                    ThePrototypeBlock.detonate(serverLevel, pos);
                }

                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }

        return InteractionResult.PASS;
    }
}
