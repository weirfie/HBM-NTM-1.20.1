package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.block.custom.entity.WaterTankBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;

import java.util.concurrent.atomic.AtomicInteger;

public class FluidMeasurerRightClickedOnBlockProcedure {
    public static void execute(LevelAccessor world, double x, double y, double z, Direction direction, Entity entity) {
        if (direction == null || entity == null || !(entity instanceof Player player))
            return;

        BlockPos pos = BlockPos.containing(x, y, z);
        BlockEntity be = world.getBlockEntity(pos);

        if (be != null) {
            be.getCapability(ForgeCapabilities.FLUID_HANDLER, direction).ifPresent(handler -> {
                MutableComponent message = Component.literal("--- Block Analysis ---").withStyle(ChatFormatting.GOLD);

                if (be instanceof WaterTankBlockEntity tank) {
                    String filter = tank.getAllowedFluid();
                    String displayFilter = filter.isEmpty() ? "None (Accepts All)" : formatName(filter);
                    message.append("\n").append(Component.literal("Filter: ").withStyle(ChatFormatting.GRAY))
                            .append(Component.literal(displayFilter).withStyle(ChatFormatting.AQUA));
                }

                // 3. Standard Tank Info
                for (int i = 0; i < handler.getTanks(); i++) {
                    FluidStack stack = handler.getFluidInTank(i);
                    int amount = stack.getAmount();
                    int capacity = handler.getTankCapacity(i);

                    Component fluidName = stack.isEmpty() ?
                            Component.literal("Empty") :
                            Component.translatable(stack.getTranslationKey());

                    message.append("\nTank " + i + ": ")
                            .append(fluidName)
                            .append(" (" + amount + "/" + capacity + " mB)");
                }

                player.displayClientMessage(message, false);
            });
        } else {
            player.displayClientMessage(Component.literal("No Fluid Handler found here."), true);
        }
    }

    private static String formatName(String name) {
        return java.util.Arrays.stream(name.split("_"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(java.util.stream.Collectors.joining(" "));
    }
}
