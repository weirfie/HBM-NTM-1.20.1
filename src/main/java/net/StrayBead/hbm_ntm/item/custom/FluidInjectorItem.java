package net.StrayBead.hbm_ntm.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class FluidInjectorItem extends Item {
    public FluidInjectorItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        BlockPos clickedPos = context.getClickedPos();

        double x = clickedPos.getX();
        double y = clickedPos.getY();
        double z = clickedPos.getZ();

        if (!level.isClientSide) {
            FluidInjectorRightClickedOnBlockProcedure.execute(level, x, y, z, context.getClickedFace(), player);
        }

        return InteractionResult.SUCCESS;
    }
}
