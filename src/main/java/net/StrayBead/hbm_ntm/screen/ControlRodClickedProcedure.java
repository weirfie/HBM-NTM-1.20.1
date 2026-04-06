package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

public class ControlRodClickedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        boolean found = false;
        double sx = 0;
        double sy = 0;
        double sz = 0;
        sx = -50;
        found = false;
        for (int index0 = 0; index0 < 100; index0++) {
            sy = -50;
            for (int index1 = 0; index1 < 100; index1++) {
                sz = -50;
                for (int index2 = 0; index2 < 100; index2++) {
                    if ((world.getBlockState(BlockPos.containing(x + sx, y + sy, z + sz))).getBlock() == ModBlocks.CONTROL_ROD.get()) {
                        {
                            BlockEntity _ent = world.getBlockEntity(BlockPos.containing(Math.floor(x + sx), Math.floor(y + sy), Math.floor(z + sz)));
                            int _amount = 800;
                            if (_ent != null)
                                _ent.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(capability -> capability.receiveEnergy(_amount, false));
                        }
                    }
                    sz = sz + 1;
                }
                sy = sy + 1;
            }
            sx = sx + 1;
        }
    }
}
