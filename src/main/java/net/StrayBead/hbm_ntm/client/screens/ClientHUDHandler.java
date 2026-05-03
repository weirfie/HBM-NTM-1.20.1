package net.StrayBead.hbm_ntm.client.screens;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.FlowGaugePipeBlock;
import net.StrayBead.hbm_ntm.block.custom.entity.FlowGaugePipeBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.FractionatingTowerBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.GenericBoundingBoxBE;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = HBMNTM.MOD_ID, value = Dist.CLIENT)
public class ClientHUDHandler {

    @SubscribeEvent
    public static void onRenderGui(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay().id() != VanillaGuiOverlay.CROSSHAIR.id()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        HitResult hit = mc.hitResult;
        if (hit instanceof BlockHitResult blockHit) {
            BlockPos pos = blockHit.getBlockPos();
            BlockState state = mc.level.getBlockState(pos);

            if (state.getBlock() instanceof FlowGaugePipeBlock) {
                renderFlowGaugeInfo(event.getGuiGraphics(), mc, pos);
                return;
            }

            if (state.is(ModBlocks.GENERIC_BOUNDING_BOX.get())) {
                if (mc.level.getBlockEntity(pos) instanceof GenericBoundingBoxBE boundingBox) {
                    BlockPos corePos = boundingBox.getCorePos();
                    if (corePos == null) return;
                    BlockState coreState = mc.level.getBlockState(corePos);

                    if (coreState.is(ModBlocks.CATALYTIC_CRACKING_TOWER.get())) {
                        renderCrackingTowerInfo(event.getGuiGraphics(), mc);
                    } else if (coreState.is(ModBlocks.FRACTIONATING_TOWER.get())) {
                        renderFractionatingTowerInfo(event.getGuiGraphics(), mc);
                    }
                }
            }
        }
    }

    private static void renderFlowGaugeInfo(net.minecraft.client.gui.GuiGraphics graphics, Minecraft mc, BlockPos pos) {
        BlockEntity be = mc.level.getBlockEntity(pos);
        if (!(be instanceof FlowGaugePipeBlockEntity gaugeBE)) return;

        List<Component> lines = new ArrayList<>();

        lines.add(Component.literal("Flow Gauge Pipe").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD));

        String fluidName = gaugeBE.getAllowedFluid();
        int hexColor = gaugeBE.getFilterColor();

        if (fluidName == null || fluidName.isEmpty()) {
            lines.add(Component.literal("No Filter Set (Accepts All)").withStyle(ChatFormatting.GRAY));
        } else {
            lines.add(Component.literal(fluidName)
                    .withStyle(style -> style.withColor(net.minecraft.network.chat.TextColor.fromRgb(hexColor))));
        }

        int currentAmount = gaugeBE.getCurrentFluidAmount();
        int maxAmount = gaugeBE.getMaxFluidAmount();
        int flowPerTick = gaugeBE.getCurrentFlowRateTick();
        lines.add(Component.literal(currentAmount + " / " + maxAmount + " mB   |   " + flowPerTick + " mB/t").withStyle(ChatFormatting.WHITE));

        int flowPerSecond = gaugeBE.getCurrentFlowRateSecond();
        lines.add(Component.literal(currentAmount + " / " + maxAmount + " mB   |   " + flowPerSecond + " mB/s").withStyle(ChatFormatting.WHITE));

        int maxWidth = 0;
        for (Component line : lines) {
            maxWidth = Math.max(maxWidth, mc.font.width(line));
        }

        int startX = (graphics.guiWidth() / 2) + 15;
        int startY = (graphics.guiHeight() / 2) - ((lines.size() * 10) / 2);

        graphics.fill(startX - 5, startY - 5, startX + maxWidth + 5, startY + (lines.size() * 10) + 5, 0xAA000000);

        for (int i = 0; i < lines.size(); i++) {
            graphics.drawString(mc.font, lines.get(i), startX, startY + (i * 10), 0xFFFFFF, true);
        }
    }

    private static void renderFractionatingTowerInfo(net.minecraft.client.gui.GuiGraphics graphics, Minecraft mc) {
        HitResult hit = mc.hitResult;
        if (!(hit instanceof BlockHitResult blockHit)) return;

        // Get the BlockEntity to check the allowedFluid string
        BlockEntity be = mc.level.getBlockEntity(blockHit.getBlockPos());
        String currentFluid = "";

        if (be instanceof GenericBoundingBoxBE bbox) {
            BlockEntity core = mc.level.getBlockEntity(bbox.getCorePos());
            if (core instanceof FractionatingTowerBlockEntity tower) {
                currentFluid = tower.getAllowedFluid();
            }
        }

        List<Component> lines = new ArrayList<>();
        lines.add(Component.literal("FRACTIONATING TOWER").withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE));
        lines.add(Component.literal("Mechanism: Fractional Distillation").withStyle(ChatFormatting.WHITE));
        lines.add(Component.empty());

        boolean foundRecipe = false;

        if (currentFluid.contains("heavy_oil")) {
            lines.add(addRecipeLine("Heavy Oil", "Bitumen (20)", "Industrial Oil (20)"));
            foundRecipe = true;
        } else if (currentFluid.contains("light_oil") && !currentFluid.contains("vacuum")) {
            lines.add(addRecipeLine("Light Oil", "Diesel (20)", "Kerosene (20)"));
            foundRecipe = true;
        } else if (currentFluid.contains("vacuum_light_oil")) {
            lines.add(addRecipeLine("Vac Light Oil", "Kerosene (20)", "Reformate Gas (20)"));
            foundRecipe = true;
        } else if (currentFluid.contains("vacuum_heavy_oil")) {
            lines.add(addRecipeLine("Vac Heavy Oil", "Industrial Oil (20)", "Heavy Heating Oil (20)"));
            foundRecipe = true;
        } else if (currentFluid.contains("naphtha")) {
            lines.add(addRecipeLine("Naphtha", "Heating Oil (20)", "Diesel (20)"));
            foundRecipe = true;
        }

        if (foundRecipe || currentFluid.isEmpty()) {
            if(currentFluid.isEmpty()) {
                lines.add(Component.literal("No Fluid Selected").withStyle(ChatFormatting.GRAY));
            }
            drawCenteredHUD(graphics, mc, lines);
        }
    }

    private static void renderCrackingTowerInfo(net.minecraft.client.gui.GuiGraphics graphics, Minecraft mc) {
        List<Component> lines = new ArrayList<>();

        lines.add(Component.literal("CATALYTIC CRACKING TOWER").withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE));
        lines.add(Component.literal("Mechanism: Thermal Decomposition").withStyle(ChatFormatting.WHITE));
        lines.add(Component.literal("Requirement: >200mB Steam").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
        lines.add(Component.empty());
        lines.add(Component.literal("--- Processing Matrix (100mB Input) ---").withStyle(ChatFormatting.GRAY));
        lines.add(addRecipeLine("Crude Oil", "Cracked Oil (80)", "Petroleum Gas (20)"));
        lines.add(addRecipeLine("Bitumen", "Crude Oil (80)", "Aromatic Hydro (20)"));
        lines.add(addRecipeLine("Natural Gas", "Petroleum Gas (80)", "Unsaturated Hydro (20)"));
        lines.add(addRecipeLine("Industrial Oil", "Naphtha (80)", "Petroleum Gas (20)"));
        lines.add(addRecipeLine("Heavy Heating Oil", "Heating Oil (80)", "Reformate Gas (20)"));
        lines.add(addRecipeLine("Reformate", "Unsaturated Hydro (80)", "Reformate Gas (20)"));

        int maxWidth = 0;
        for (Component line : lines) {
            maxWidth = Math.max(maxWidth, mc.font.width(line));
        }

        int x = (graphics.guiWidth() / 2) - (maxWidth / 2);
        int y = (graphics.guiHeight() / 2) - 80;

        graphics.fill(x - 5, y - 5, x + maxWidth + 5, y + (lines.size() * 10) + 5, 0xAA000000);

        for (int i = 0; i < lines.size(); i++) {
            Component line = lines.get(i);
            graphics.drawString(mc.font, line, x, y + (i * 10), 0xFFFFFF);
        }
    }

    private static Component addRecipeLine(String input, String out1, String out2) {
        return Component.literal("• " + input + " ").withStyle(ChatFormatting.YELLOW)
                .append(Component.literal("-> ").withStyle(ChatFormatting.WHITE))
                .append(Component.literal(out1 + " + " + out2).withStyle(ChatFormatting.GREEN));
    }

    private static void drawCenteredHUD(net.minecraft.client.gui.GuiGraphics graphics, Minecraft mc, List<Component> lines) {
        int lineHeight = 10;
        int totalHeight = lines.size() * lineHeight;

        int maxWidth = 0;
        for (Component line : lines) {
            maxWidth = Math.max(maxWidth, mc.font.width(line));
        }

        int x = (graphics.guiWidth() / 2) - (maxWidth / 2);
        int y = (graphics.guiHeight() / 2) - (totalHeight / 2);

        for (int i = 0; i < lines.size(); i++) {
            graphics.drawString(mc.font, lines.get(i), x, y + (i * lineHeight), 0xFFFFFF, true);
        }
    }
}