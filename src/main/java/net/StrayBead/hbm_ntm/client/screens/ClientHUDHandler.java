package net.StrayBead.hbm_ntm.client.screens;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.GenericBoundingBoxBE;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
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

    private static void renderFractionatingTowerInfo(net.minecraft.client.gui.GuiGraphics graphics, Minecraft mc) {
        List<Component> lines = new ArrayList<>();

        lines.add(Component.literal("FRACTIONATING TOWER").withStyle(ChatFormatting.GOLD, ChatFormatting.BOLD, ChatFormatting.UNDERLINE));
        lines.add(Component.literal("Mechanism: Fractional Distillation").withStyle(ChatFormatting.WHITE));
        lines.add(Component.literal("Requirement: Heat Gradient").withStyle(ChatFormatting.AQUA, ChatFormatting.ITALIC));
        lines.add(Component.empty());
        lines.add(Component.literal("--- Distillation Matrix (40mB Input) ---").withStyle(ChatFormatting.GRAY));

        lines.add(addRecipeLine("Heavy Oil", "Bitumen (20)", "Industrial Oil (20)"));
        lines.add(addRecipeLine("Light Oil", "Diesel (20)", "Kerosene (20)"));
        lines.add(addRecipeLine("Vac Light Oil", "Kerosene (20)", "Reformate Gas (20)"));
        lines.add(addRecipeLine("Vac Heavy Oil", "Industrial Oil (20)", "Heavy Heating Oil (20)"));
        lines.add(addRecipeLine("Naphtha", "Heating Oil (20)", "Diesel (20)"));

        drawCenteredHUD(graphics, mc, lines);
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
        int maxWidth = 0;
        for (Component line : lines) {
            maxWidth = Math.max(maxWidth, mc.font.width(line));
        }

        int x = (graphics.guiWidth() / 2) - (maxWidth / 2);
        int y = (graphics.guiHeight() / 2) - 85;

        graphics.fill(x - 5, y - 5, x + maxWidth + 5, y + (lines.size() * 10) + 5, 0xAA000000);

        for (int i = 0; i < lines.size(); i++) {
            graphics.drawString(mc.font, lines.get(i), x, y + (i * 10), 0xFFFFFF);
        }
    }
}