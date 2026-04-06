package net.StrayBead.hbm_ntm.block.custom.rbmk;

import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class CoolingTowerBlockUpdateTickProcedure {
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

                        FluidStack neighborFluid = targetCap.getFluidInTank(0);
                        if (neighborFluid.getFluid().isSame(ModFluids.STEAM.get())) {
                            int spaceInSource = sourceCap.getTankCapacity(0) - sourceCap.getFluidInTank(0).getAmount();
                            int toPull = Math.min(Math.min(neighborFluid.getAmount(), spaceInSource), maxTransfer);

                            if (toPull > 0) {
                                FluidStack drained = targetCap.drain(new FluidStack(ModFluids.STEAM.get(), toPull), IFluidHandler.FluidAction.EXECUTE);
                                sourceCap.fill(drained, IFluidHandler.FluidAction.EXECUTE);
                            }
                        }

                        FluidStack internalWater = sourceCap.getFluidInTank(1);
                        if (internalWater.getFluid().isSame(Fluids.WATER)) {
                            int toPush = Math.min(internalWater.getAmount(), maxTransfer);

                            if (toPush > 0) {
                                int accepted = targetCap.fill(new FluidStack(Fluids.WATER, toPush), IFluidHandler.FluidAction.SIMULATE);
                                if (accepted > 0) {
                                    sourceCap.drain(new FluidStack(Fluids.WATER, accepted), IFluidHandler.FluidAction.EXECUTE);
                                    targetCap.fill(new FluidStack(Fluids.WATER, accepted), IFluidHandler.FluidAction.EXECUTE);
                                }
                            }
                        }
                    });
                });
            }
        }
    }
}
