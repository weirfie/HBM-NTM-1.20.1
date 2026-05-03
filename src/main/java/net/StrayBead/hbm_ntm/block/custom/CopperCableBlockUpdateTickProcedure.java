package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.custom.entity.*;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.EnergySyncS2CPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.concurrent.atomic.AtomicBoolean;

public class CopperCableBlockUpdateTickProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        if (world.isClientSide()) return;

        BlockPos sourcePos = BlockPos.containing(x, y, z);
        int maxTransferPerSide = 200;

        BlockEntity sourceEnt = world.getBlockEntity(sourcePos);
        if (!(sourceEnt instanceof CopperCableBlockEntity cableEnt) && !(sourceEnt instanceof PaintableCopperCableBlockEntity e)) return;

        sourceEnt.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(sourceCap -> {
            AtomicBoolean changed = new AtomicBoolean(false);

            for (Direction dir : Direction.values()) {
                BlockPos targetPos = sourcePos.relative(dir);
                BlockEntity targetEnt = world.getBlockEntity(targetPos);

                if (targetEnt != null) {
                    if (targetEnt instanceof SparkEnergyBatteryBlockEntity) {
                        continue;
                    }
                    if (targetEnt instanceof TurbineBlockEntity) {
                        continue;
                    }
                    if (targetEnt instanceof WoodBurningGeneratorBlockEntity) {
                        continue;
                    }
                    if (targetEnt instanceof FlareStackBlockEntity) {
                        continue;
                    }
                    targetEnt.getCapability(ForgeCapabilities.ENERGY, null).ifPresent(targetCap -> {
                        if (sourceCap.canExtract() && targetCap.canReceive()) {
                            int amountToMove;

                            if (!(targetEnt.getBlockState().getBlock() instanceof CopperCableBlock)) {
                                amountToMove = Math.min(sourceCap.getEnergyStored(), maxTransferPerSide);
                            } else {
                                int energyInSource = sourceCap.getEnergyStored();
                                int energyInTarget = targetCap.getEnergyStored();
                                amountToMove = (energyInSource > energyInTarget) ? Math.min((energyInSource - energyInTarget) / 2, maxTransferPerSide) : 0;
                            }

                            if (amountToMove > 0) {
                                int extracted = sourceCap.extractEnergy(amountToMove, true);
                                int accepted = targetCap.receiveEnergy(extracted, true);

                                if (accepted > 0) {
                                    int finalExtract = sourceCap.extractEnergy(accepted, false);
                                    targetCap.receiveEnergy(finalExtract, false);

                                    targetEnt.setChanged();
                                    changed.set(true);

                                    if (world instanceof ServerLevel serverLevel) {
                                        ModMessages.sendToClients(new EnergySyncS2CPacket(targetCap.getEnergyStored(), targetPos));
                                        serverLevel.getChunkSource().blockChanged(targetPos);

                                        ModMessages.sendToClients(new EnergySyncS2CPacket(sourceCap.getEnergyStored(), sourcePos));
                                        serverLevel.getChunkSource().blockChanged(sourcePos);
                                    }
                                }
                            }
                        }
                    });
                }
            }

            if (changed.get()) {
                sourceEnt.setChanged();
            }
        });
    }
}
