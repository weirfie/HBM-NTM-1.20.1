package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class FluidInjectorRightClickedOnBlockProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity) {
        if (world instanceof Level level && !level.isClientSide()) {
            BlockEntity ent = level.getBlockEntity(BlockPos.containing(x, y, z));
            if (ent != null) {
                ent.getCapability(ForgeCapabilities.FLUID_HANDLER, null)
                        .ifPresent(cap -> {
                            int filled = cap.fill(
                                    new FluidStack(ModFluids.CRUDE_OIL.get(), 100),
                                    IFluidHandler.FluidAction.EXECUTE
                            );
                            System.out.println("SERVER Filled: " + filled);
                        });
            }
        }

    }
}
