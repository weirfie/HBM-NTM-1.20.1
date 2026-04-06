package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.StrayBead.hbm_ntm.client.ShockwaveData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "hbm_ntm", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShockwaveRenderer {
    private static final List<ShockwaveData> SHOCKWAVES = new ArrayList<>();

    public static void addShockwave(Vec3 pos, float maxRadius, int maxAge) {
        SHOCKWAVES.add(new ShockwaveData(pos, maxRadius, maxAge, 1.4f));
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();

            if (mc.level != null) {
                SHOCKWAVES.forEach(wave -> wave.tick(mc.level));
            }

            SHOCKWAVES.removeIf(s -> s.age >= s.maxAge);
        }
    }


    @SubscribeEvent
    public static void renderShockwave(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES || SHOCKWAVES.isEmpty()) return;

        PoseStack ps = event.getPoseStack();
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tess = Tesselator.getInstance();
        BufferBuilder bf = tess.getBuilder();

        for (ShockwaveData wave : SHOCKWAVES) {
            ps.pushPose();
            ps.translate(wave.center.x - cameraPos.x, wave.center.y - cameraPos.y, wave.center.z - cameraPos.z);

            float alpha = 1.0f - ((float) wave.age / (float) wave.maxAge);

            alpha = Math.max(0, Math.min(1.0f, alpha));

            renderSphere(ps, bf, wave.radius, wave.alpha);

            tess.end();
            ps.popPose();
        }

        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static void renderSphere(PoseStack ps, BufferBuilder bf, float radius, float alpha) {
        Matrix4f mat = ps.last().pose();
        bf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        int detail = 32;
        int r = 255, g = 255, b = 255, a = (int) (alpha * 100);

        for (int i = 0; i < detail; i++) {
            double lat0 = Math.PI * (-0.5 + (double) (i) / detail);
            float z0 = (float) Math.sin(lat0);
            float zr0 = (float) Math.cos(lat0);

            double lat1 = Math.PI * (-0.5 + (double) (i + 1) / detail);
            float z1 = (float) Math.sin(lat1);
            float zr1 = (float) Math.cos(lat1);

            for (int j = 0; j < detail; j++) {
                double lng0 = 2 * Math.PI * (double) (j) / detail;
                float x0 = (float) Math.cos(lng0);
                float y0 = (float) Math.sin(lng0);

                double lng1 = 2 * Math.PI * (double) (j + 1) / detail;
                float x1 = (float) Math.cos(lng1);
                float y1 = (float) Math.sin(lng1);

                bf.vertex(mat, x1 * zr0 * radius, y1 * zr0 * radius, z0 * radius).color(r, g, b, a).endVertex();
                bf.vertex(mat, x1 * zr1 * radius, y1 * zr1 * radius, z1 * radius).color(r, g, b, a).endVertex();
                bf.vertex(mat, x0 * zr1 * radius, y0 * zr1 * radius, z1 * radius).color(r, g, b, a).endVertex();
                bf.vertex(mat, x0 * zr0 * radius, y0 * zr0 * radius, z0 * radius).color(r, g, b, a).endVertex();
            }
        }
    }
}
