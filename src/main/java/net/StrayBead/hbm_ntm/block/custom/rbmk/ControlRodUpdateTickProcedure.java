package net.StrayBead.hbm_ntm.block.custom.rbmk;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.ControlRodTopBlock;
import net.StrayBead.hbm_ntm.block.custom.entity.ControlRodTopBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.core.BlockPos;

public class ControlRodUpdateTickProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        double efficiency = 1.0;
        int maxHeight = 74;

        for (int i = 0; i < maxHeight; i++) {
            BlockPos checkPos = BlockPos.containing(x, y + i, z);
            BlockEntity be = world.getBlockEntity(checkPos);

            if (be instanceof ControlRodTopBlockEntity top) {
                float currentTick = top.animationTick;
                float maxTick = 40.0f;
                efficiency = Math.max(0, (maxTick - currentTick) / maxTick);
                break;
            }

            if (!(world.getBlockState(checkPos).getBlock() instanceof ControlRodBlock)) {
                if (!(world.getBlockState(checkPos).getBlock() instanceof ControlRodTopBlock)) break;
            }
        }

        int baseAmount = 100;
        int finalAmount = (int) (baseAmount * efficiency);

        if (finalAmount <= 0) return;

        BlockPos[] neighbors = {
                BlockPos.containing(x + 1, y, z),
                BlockPos.containing(x - 1, y, z),
                BlockPos.containing(x, y, z + 1),
                BlockPos.containing(x, y, z - 1)
        };

        for (BlockPos pos : neighbors) {
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() == ModBlocks.GRAPHITE_MODERATOR.get()) {
                extractEnergy(world, pos, finalAmount);
            }
            if (state.getBlock() == ModBlocks.FUEL_ROD.get()) {
                extractEnergy(world, pos, finalAmount);
            }
        }
    }

    private static void extractEnergy(LevelAccessor world, BlockPos pos, int amount) {
        BlockEntity _ent = world.getBlockEntity(pos);
        if (_ent != null) {
            _ent.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(cap -> {
                if (cap.canExtract()) {
                    cap.extractEnergy(amount, false);
                }
            });
        }
    }
}
