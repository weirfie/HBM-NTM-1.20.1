package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.custom.entity.CopperCableBlockEntity;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.concurrent.atomic.AtomicBoolean;

public class CrudeOilPipeBlockUpdateTickProcedure {

    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (world.isClientSide()) return;

        BlockPos sourcePos = BlockPos.containing(x, y, z);
        int maxTransferPerSide = 100;

        BlockEntity sourceEnt = world.getBlockEntity(sourcePos);
        if (sourceEnt == null) return;

        sourceEnt.getCapability(ForgeCapabilities.FLUID_HANDLER, null).ifPresent(sourceCap -> {

            AtomicBoolean changed = new AtomicBoolean(false);

            for (Direction dir : Direction.values()) {

                BlockPos targetPos = sourcePos.relative(dir);
                BlockEntity targetEnt = world.getBlockEntity(targetPos);

                if (targetEnt == null) continue;

                targetEnt.getCapability(ForgeCapabilities.FLUID_HANDLER, dir.getOpposite()).ifPresent(targetCap -> {

                    if (sourceCap.getTanks() == 0 || targetCap.getTanks() == 0)
                        return;

                    FluidStack sourceFluid = sourceCap.getFluidInTank(0);
                    FluidStack targetFluid = targetCap.getFluidInTank(0);

                    if (sourceFluid.isEmpty())
                        return;

                    if (!targetFluid.isEmpty() && !targetFluid.isFluidEqual(sourceFluid))
                        return;

                    int sourceAmount = sourceFluid.getAmount();
                    int targetAmount = targetFluid.getAmount();

                    int amountToMove = 0;

                    // Pipe → Pipe equalization
                    if (targetEnt.getBlockState().getBlock() instanceof CrudeOilPipeBlock) {

                        if (sourceAmount > targetAmount) {
                            amountToMove = Math.min(
                                    (sourceAmount - targetAmount) / 2,
                                    maxTransferPerSide
                            );
                        }

                    } else {
                        amountToMove = Math.min(sourceAmount, maxTransferPerSide);
                    }

                    if (amountToMove <= 0)
                        return;

                    FluidStack toDrain = new FluidStack(sourceFluid, amountToMove);

                    int accepted = targetCap.fill(toDrain, IFluidHandler.FluidAction.SIMULATE);
                    if (accepted <= 0)
                        return;

                    FluidStack drained = sourceCap.drain(accepted, IFluidHandler.FluidAction.EXECUTE);
                    targetCap.fill(drained, IFluidHandler.FluidAction.EXECUTE);

                    targetEnt.setChanged();
                    changed.set(true);

                    if (world instanceof ServerLevel serverLevel) {
                        serverLevel.getChunkSource().blockChanged(targetPos);
                        serverLevel.getChunkSource().blockChanged(sourcePos);
                    }
                });
            }

            if (changed.get()) {
                sourceEnt.setChanged();
            }
        });
    }
}
