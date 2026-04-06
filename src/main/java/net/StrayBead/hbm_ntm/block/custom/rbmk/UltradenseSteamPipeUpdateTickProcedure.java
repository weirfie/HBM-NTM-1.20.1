package net.StrayBead.hbm_ntm.block.custom.rbmk;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class UltradenseSteamPipeUpdateTickProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        BlockPos sourcePos = BlockPos.containing(x, y, z);
        int maxTransfer = 100;

        for (Direction dir : Direction.values()) {
            BlockPos targetPos = sourcePos.relative(dir);
            BlockEntity sourceEnt = world.getBlockEntity(sourcePos);
            BlockEntity targetEnt = world.getBlockEntity(targetPos);

            if (sourceEnt != null && targetEnt != null) {
                sourceEnt.getCapability(ForgeCapabilities.FLUID_HANDLER, null).ifPresent(sourceCap -> {
                    targetEnt.getCapability(ForgeCapabilities.FLUID_HANDLER, dir.getOpposite()).ifPresent(targetCap -> {

                        int sourceLevel = sourceCap.getFluidInTank(0).getAmount();
                        int targetLevel = targetCap.getFluidInTank(0).getAmount();

                        if (sourceLevel > targetLevel) {
                            int difference = sourceLevel - targetLevel;

                            int amountToMove = difference / 2;

                            if (amountToMove > maxTransfer) {
                                amountToMove = maxTransfer;
                            }

                            if (amountToMove > 0) {
                                FluidStack toDrain = sourceCap.drain(amountToMove, IFluidHandler.FluidAction.SIMULATE);
                                if (!toDrain.isEmpty()) {
                                    int accepted = targetCap.fill(toDrain, IFluidHandler.FluidAction.SIMULATE);

                                    if (accepted > 0) {
                                        FluidStack actuallyDrained = sourceCap.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                                        targetCap.fill(actuallyDrained, IFluidHandler.FluidAction.EXECUTE);
                                    }
                                }
                            }
                        }
                    });
                });
            }
        }
    }
}
