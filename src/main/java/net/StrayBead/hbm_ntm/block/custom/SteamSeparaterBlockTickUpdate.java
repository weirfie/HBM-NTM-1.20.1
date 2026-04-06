package net.StrayBead.hbm_ntm.block.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

public class SteamSeparaterBlockTickUpdate {
    public static void execute(LevelAccessor world, double x, double y, double z) {
        BlockPos currentPos = BlockPos.containing(x, y, z);
        BlockPos abovePos = currentPos.above();
        BlockPos belowPos = currentPos.below();

        BlockState stateBelow = world.getBlockState(belowPos);
        if (!isValidSteamPipe(stateBelow)) return;

        BlockEntity entityAbove = world.getBlockEntity(abovePos);
        BlockEntity entityBelow = world.getBlockEntity(belowPos);

        if (entityAbove != null && entityBelow != null) {
            entityAbove.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.DOWN).ifPresent(sourceHandler -> {
                entityBelow.getCapability(ForgeCapabilities.FLUID_HANDLER, Direction.UP).ifPresent(destHandler -> {

                    int transferRate = 100;
                    for (int i = 0; i < sourceHandler.getTanks(); i++) {
                        FluidStack fluidInTank = sourceHandler.getFluidInTank(i);

                        if (!fluidInTank.isEmpty() && fluidInTank.getFluid().isSame(ModFluids.STEAM.get()) && fluidInTank.getAmount() > 0) {

                            int amountToMove = Math.min(fluidInTank.getAmount(), transferRate);
                            FluidStack steamStack = new FluidStack(ModFluids.STEAM.get(), amountToMove);

                            int accepted = destHandler.fill(steamStack, IFluidHandler.FluidAction.SIMULATE);

                            if (accepted > 0) {
                                FluidStack extracted = sourceHandler.drain(new FluidStack(ModFluids.STEAM.get(), accepted), IFluidHandler.FluidAction.EXECUTE);

                                if (!extracted.isEmpty()) {
                                    destHandler.fill(extracted, IFluidHandler.FluidAction.EXECUTE);
                                }
                            }
                            break;
                        }
                    }
                });
            });
        }
    }

    private static boolean isValidSteamPipe(BlockState state) {
        Block block = state.getBlock();
        return block == ModBlocks.ULTRA_DENSE_STEAM_PIPE.get() ||
                ForgeRegistries.BLOCKS.getKey(block).getPath().contains("steam_pipe");
    }
}
