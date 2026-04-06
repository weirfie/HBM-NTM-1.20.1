package net.StrayBead.hbm_ntm.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(modid = "hbm_ntm", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientExplosionEffects {
    public static BlockPos explosionCenter = null;
    private static final float RADIUS = 800f;
    public static long effectStartTime = 0;
    private static final float FADE_DURATION_TICKS = 1000f;

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            if (explosionCenter == null) return;

            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.level == null) return;

            double distSq = mc.player.distanceToSqr(explosionCenter.getX(), explosionCenter.getY(), explosionCenter.getZ());
            if (distSq < RADIUS * RADIUS) {
                long timePassed = mc.level.getGameTime() - effectStartTime;
                float progress = Math.min(1.0f, timePassed / FADE_DURATION_TICKS);

                renderSkybox(event.getPoseStack(), progress);
            }
        }
    }

    private static void renderSkybox(PoseStack poseStack, float progress) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        int r = 75, g = 85, b = 75;
        int a = (int) (200 * progress);

        float size = 150.0F;

        Matrix4f matrix = poseStack.last().pose();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        bufferbuilder.vertex(matrix, -size,  size, -size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix, -size,  size,  size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix,  size,  size,  size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix,  size,  size, -size).color(r, g, b, a).endVertex();

        // Bottom
        bufferbuilder.vertex(matrix, -size, -size, -size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix,  size, -size, -size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix,  size, -size,  size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix, -size, -size,  size).color(r, g, b, a).endVertex();
        // North
        bufferbuilder.vertex(matrix, -size, -size, -size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix, -size,  size, -size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix,  size,  size, -size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix,  size, -size, -size).color(r, g, b, a).endVertex();
        // South
        bufferbuilder.vertex(matrix, -size, -size,  size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix,  size, -size,  size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix,  size,  size,  size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix, -size,  size,  size).color(r, g, b, a).endVertex();
        // West
        bufferbuilder.vertex(matrix, -size, -size, -size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix, -size, -size,  size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix, -size,  size,  size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix, -size,  size, -size).color(r, g, b, a).endVertex();
        // East
        bufferbuilder.vertex(matrix,  size, -size, -size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix,  size,  size, -size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix,  size,  size,  size).color(r, g, b, a).endVertex();
        bufferbuilder.vertex(matrix,  size, -size,  size).color(r, g, b, a).endVertex();

        tesselator.end();

        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
    }
}
