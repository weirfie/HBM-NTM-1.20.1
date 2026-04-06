package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.ControlRodTopBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class PowerDownButtonClickedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        int range = 100;
        for (int sx = -range; sx <= range; sx++) {
            for (int sy = -range; sy <= range; sy++) {
                for (int sz = -range; sz <= range; sz++) {
                    BlockPos pos = BlockPos.containing(x + sx, y + sy, z + sz);
                    if (world.getBlockState(pos).getBlock() == ModBlocks.CONTROL_ROD_TOP.get()) {
                        if (world.getBlockEntity(pos) instanceof ControlRodTopBlockEntity be) {

                            be.isActive = true;
                            be.targetTick = Math.max(0.0f, be.targetTick - 2.0f);

                            be.setChanged();
                            if (world instanceof Level level) {
                                level.sendBlockUpdated(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
                            }
                        }
                    }
                }
            }
        }
    }
}
