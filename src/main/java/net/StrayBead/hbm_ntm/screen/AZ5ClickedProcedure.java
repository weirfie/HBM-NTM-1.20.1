package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.ControlRodTopBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class AZ5ClickedProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        int range = 100;

        for (int sx = -range; sx <= range; sx++) {
            for (int sy = -range; sy <= range; sy++) {
                for (int sz = -range; sz <= range; sz++) {
                    BlockPos targetPos = BlockPos.containing(x + sx, y + sy, z + sz);
                    BlockState state = world.getBlockState(targetPos);

                    if (state.getBlock() == ModBlocks.CONTROL_ROD_TOP.get()) {
                        if (world.getBlockEntity(targetPos) instanceof ControlRodTopBlockEntity be) {
                            be.targetTick = 0;
                            be.setChanged();
                            if (world instanceof Level level)
                                level.sendBlockUpdated(targetPos, state, state, 3);
                        }
                    }

                    else if (state.getBlock() == ModBlocks.FUEL_ROD.get()) {
                        BlockEntity be = world.getBlockEntity(targetPos);
                        if (be != null) {
                            be.getCapability(ForgeCapabilities.ENERGY).ifPresent(cap -> {
                                if (cap.getEnergyStored() >= 15000) {
                                    cap.receiveEnergy(30000, false);
                                }
                            });
                        }
                    }
                }
            }
        }
    }
}
