package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.entity.custom.ShockwaveProjectileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.BlockHitResult;

public class FluidMeasurerItem extends Item {
    public FluidMeasurerItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();

        // Get the position of the block the player clicked on
        BlockPos clickedPos = context.getClickedPos(); // Get the clicked block position

        // Adjust the Y position slightly above the block
        double x = clickedPos.getX(); // Center of the block
        double y = clickedPos.getY(); // Slightly above the block (adjust if needed)
        double z = clickedPos.getZ(); // Center of the block

        if (!level.isClientSide) {
            FluidMeasurerRightClickedOnBlockProcedure.execute(level, x, y, z, context.getClickedFace(), player);
        }

        return InteractionResult.SUCCESS; // Return success after action is completed
    }
}
