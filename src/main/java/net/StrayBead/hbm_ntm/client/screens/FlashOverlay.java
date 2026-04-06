package net.StrayBead.hbm_ntm.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class FlashOverlay {
    private static boolean active = false;
    private static long startTime = 0;

    private static final long FADE_IN_DURATION = 1200;
    private static final long HOLD_DURATION = 2500;
    private static final long FADE_OUT_DURATION = 3500;

    private static final long DISPLAY_DURATION = FADE_IN_DURATION + HOLD_DURATION + FADE_OUT_DURATION;

    public static void triggerFlash() {
        active = true;
        startTime = System.currentTimeMillis();
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onRenderOverlay(RenderGuiEvent.Pre event) {
        if (!active) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        long elapsed = System.currentTimeMillis() - startTime;
        if (elapsed > DISPLAY_DURATION) {
            active = false;
            return;
        }

        float alpha;
        if (elapsed < FADE_IN_DURATION) {
            alpha = (float) elapsed / FADE_IN_DURATION;
        } else if (elapsed < FADE_IN_DURATION + HOLD_DURATION) {
            alpha = 1.0f;
        } else {
            long fadeOutElapsed = elapsed - (FADE_IN_DURATION + HOLD_DURATION);
            alpha = 1.0f - ((float) fadeOutElapsed / FADE_OUT_DURATION);
        }

        alpha = Mth.clamp(alpha, 0.0f, 1.0f);

        int w = event.getWindow().getGuiScaledWidth();
        int h = event.getWindow().getGuiScaledHeight();

        int alphaInt = (int) (alpha * 255.0F);
        int color = (alphaInt << 24) | 0xFFFFFF;

        GuiGraphics guiGraphics = event.getGuiGraphics();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.fill(0, 0, w, h, color);
        RenderSystem.disableBlend();
    }
}
