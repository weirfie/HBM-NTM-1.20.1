package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.custom.entity.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class WaterDuctUpdateTickProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        BlockPos sourcePos = BlockPos.containing(x, y, z);
        int maxTransfer = 100;

        for (Direction dir : Direction.values()) {
            BlockPos targetPos = sourcePos.relative(dir);
            BlockEntity sourceEnt = world.getBlockEntity(sourcePos);
            BlockEntity rawTargetEnt = world.getBlockEntity(targetPos);

            if (sourceEnt != null && rawTargetEnt != null) {
                BlockEntity targetEnt = getActualTarget(rawTargetEnt);

                sourceEnt.getCapability(ForgeCapabilities.FLUID_HANDLER, dir).ifPresent(sourceCap -> {
                    rawTargetEnt.getCapability(ForgeCapabilities.FLUID_HANDLER, dir.getOpposite()).ifPresent(targetCap -> {

                        for (int i = 0; i < sourceCap.getTanks(); i++) {
                            FluidStack sourceStack = sourceCap.getFluidInTank(i);
                            if (sourceStack.isEmpty()) continue;
                            if (targetEnt instanceof BoilerBlockEntity boilerBlockEntity) {
                                String ductFilter = getDuctFilter(sourceEnt);

                                if (!ductFilter.equals("hot_crude_oil")) {
                                    int boilerPushAmount = 2000;
                                    FluidStack moveStack = new FluidStack(sourceStack.getFluid(), boilerPushAmount);

                                    int accepted = targetCap.fill(moveStack, IFluidHandler.FluidAction.SIMULATE);
                                    if (accepted > 0) {
                                        FluidStack drained = sourceCap.drain(new FluidStack(sourceStack.getFluid(), accepted), IFluidHandler.FluidAction.EXECUTE);
                                        targetCap.fill(drained, IFluidHandler.FluidAction.EXECUTE);
                                    }
                                }
                                continue;
                            } else if (targetEnt instanceof OilRefineryBlockEntity oilRefineryBlockEntity) {
                                String ductFilter = getDuctFilter(sourceEnt);

                                if (!ductFilter.equals("heavy_oil") && !ductFilter.equals("naphtha") && !ductFilter.equals("petroleum_gas") && !ductFilter.equals("light_oil")) {
                                    int boilerPushAmount = 4000;
                                    FluidStack moveStack = new FluidStack(sourceStack.getFluid(), boilerPushAmount);

                                    int accepted = targetCap.fill(moveStack, IFluidHandler.FluidAction.SIMULATE);
                                    if (accepted > 0) {
                                        FluidStack drained = sourceCap.drain(new FluidStack(sourceStack.getFluid(), accepted), IFluidHandler.FluidAction.EXECUTE);
                                        targetCap.fill(drained, IFluidHandler.FluidAction.EXECUTE);
                                    }
                                }
                                continue;
                            } else if (targetEnt instanceof WaterTankBlockEntity waterTankBlockEntity) {
                                if (waterTankBlockEntity.IS_OUTPUTTING) {
                                    continue;
                                }
                                String ductFilter = getDuctFilter(sourceEnt);

                                int boilerPushAmount = 2000;
                                FluidStack moveStack = new FluidStack(sourceStack.getFluid(), boilerPushAmount);

                                int accepted = targetCap.fill(moveStack, IFluidHandler.FluidAction.SIMULATE);
                                if (accepted > 0) {
                                    FluidStack drained = sourceCap.drain(new FluidStack(sourceStack.getFluid(), accepted), IFluidHandler.FluidAction.EXECUTE);
                                    targetCap.fill(drained, IFluidHandler.FluidAction.EXECUTE);
                                }
                                continue;
                            } else if (targetEnt instanceof SteelBarrelBlockEntity steelBarrelBlockEntity) {
                                if (steelBarrelBlockEntity.IS_OUTPUTTING) {
                                    continue;
                                }
                                String ductFilter = getDuctFilter(sourceEnt);

                                int boilerPushAmount = 2000;
                                FluidStack moveStack = new FluidStack(sourceStack.getFluid(), boilerPushAmount);

                                int accepted = targetCap.fill(moveStack, IFluidHandler.FluidAction.SIMULATE);
                                if (accepted > 0) {
                                    FluidStack drained = sourceCap.drain(new FluidStack(sourceStack.getFluid(), accepted), IFluidHandler.FluidAction.EXECUTE);
                                    targetCap.fill(drained, IFluidHandler.FluidAction.EXECUTE);
                                }
                                continue;
                            } else if (targetEnt instanceof ZirnoxNuclearReactorBlockEntity) {
                                String ductFilter = getDuctFilter(sourceEnt);

                                if (ductFilter.contains("carbon_dioxide")) {
                                    int targetTankIndex = 1;
                                    int zirnoxPushAmount = 2000;

                                    if (targetCap.isFluidValid(targetTankIndex, sourceStack)) {
                                        int currentAmount = targetCap.getFluidInTank(targetTankIndex).getAmount();
                                        int capacity = targetCap.getTankCapacity(targetTankIndex);
                                        int space = capacity - currentAmount;

                                        int amountToMove = Math.min(Math.min(sourceStack.getAmount(), zirnoxPushAmount), space);

                                        if (amountToMove > 0) {
                                            FluidStack drained = sourceCap.drain(new FluidStack(sourceStack.getFluid(), amountToMove), IFluidHandler.FluidAction.EXECUTE);
                                            targetCap.fill(drained, IFluidHandler.FluidAction.EXECUTE);
                                        }
                                    }
                                    continue;
                                }
                            } else if (targetEnt instanceof CatalyticCrackingTowerBlockEntity crackingTower) {
                                String ductFilter = getDuctFilter(sourceEnt);

                                if (sourceStack.getFluid().getFluidType().toString().contains("steam") || ductFilter.equals("steam")) {
                                    int steamTankIndex = 1;
                                    int pushAmount = 1000;

                                    int space = targetCap.getTankCapacity(steamTankIndex) - targetCap.getFluidInTank(steamTankIndex).getAmount();
                                    int amountToMove = Math.min(Math.min(sourceStack.getAmount(), pushAmount), space);

                                    if (amountToMove > 0) {
                                        FluidStack drained = sourceCap.drain(new FluidStack(sourceStack.getFluid(), amountToMove), IFluidHandler.FluidAction.EXECUTE);
                                        targetCap.fill(drained, IFluidHandler.FluidAction.EXECUTE);
                                    }
                                } else {
                                    int oilPushAmount = 2000;
                                    FluidStack moveStack = new FluidStack(sourceStack.getFluid(), oilPushAmount);
                                    int accepted = targetCap.fill(moveStack, IFluidHandler.FluidAction.SIMULATE);
                                    if (accepted > 0) {
                                        FluidStack drained = sourceCap.drain(new FluidStack(sourceStack.getFluid(), accepted), IFluidHandler.FluidAction.EXECUTE);
                                        targetCap.fill(drained, IFluidHandler.FluidAction.EXECUTE);
                                    }
                                }
                                continue;
                            }

                            for (int j = 0; j < targetCap.getTanks(); j++) {
                                FluidStack targetStack = targetCap.getFluidInTank(j);
                                if (targetStack.isEmpty() || targetStack.getFluid().isSame(sourceStack.getFluid())) {
                                    int sourceLevel = sourceStack.getAmount();
                                    int targetLevel = targetStack.getAmount();

                                    if (sourceLevel > targetLevel) {
                                        int difference = sourceLevel - targetLevel;
                                        int amountToMove = Math.min(difference / 2, maxTransfer);

                                        if (amountToMove > 0) {
                                            FluidStack moveStack = new FluidStack(sourceStack.getFluid(), amountToMove);
                                            int accepted = targetCap.fill(moveStack, IFluidHandler.FluidAction.SIMULATE);
                                            if (accepted > 0) {
                                                FluidStack drained = sourceCap.drain(new FluidStack(sourceStack.getFluid(), accepted), IFluidHandler.FluidAction.EXECUTE);
                                                targetCap.fill(drained, IFluidHandler.FluidAction.EXECUTE);
                                            }
                                        }
                                    }
                                    break;
                                }
                            }
                        }
                    });
                });
            }
        }
    }

    private static BlockEntity getActualTarget(BlockEntity targetEnt) {
        if (targetEnt instanceof GenericBoundingBoxBE boundingBox) {
            BlockPos corePos = boundingBox.getCorePos();
            if (corePos != null && !corePos.equals(BlockPos.ZERO)) {
                return boundingBox.getLevel().getBlockEntity(corePos);
            }
        }
        return targetEnt;
    }

    private static String getDuctFilter(BlockEntity entity) {
        if (entity instanceof FluidBlockEntity duct) {
            return duct.getAllowedFluid();
        }
        if (entity instanceof PaintableCoatedUniversalFluidDuctBlockEntity duct) {
            return duct.getAllowedFluid();
        }
        return "";
    }
}