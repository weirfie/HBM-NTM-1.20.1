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
public class LittleBoyShockwaveRenderer {
    private static final List<ShockwaveData> SHOCKWAVES = new ArrayList<>();

    public static void addShockwave(Vec3 pos, float maxRadius, int maxAge) {
        SHOCKWAVES.add(new ShockwaveData(pos, maxRadius, maxAge, 0.68f));
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

            float lifeAlpha = 1.0f - ((float) wave.age / (float) wave.maxAge);
            lifeAlpha = Math.max(0, Math.min(1.0f, lifeAlpha)) * wave.alpha;

            bf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

            if (wave.radius > 90.0f) {
                addStaticYBand(ps, bf, wave.radius, 80.0f, 120.0f, lifeAlpha * 0.9f);
            }
            if (wave.radius > 150.0f) {
                addStaticYBand(ps, bf, wave.radius, 180.0f, 230.0f, lifeAlpha * 0.9f);
            }

            tess.end();
            ps.popPose();
        }

        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static void addStaticYBand(PoseStack ps, BufferBuilder bf, float radius, float yStart, float yEnd, float alpha) {
        if (radius < yEnd) return;

        Matrix4f mat = ps.last().pose();

        int detail = 48;

        int r = 255, g = 255, b = 255, a = (int) (alpha * 255);

        float rStart = (float) Math.sqrt(radius * radius - (yStart * yStart));
        float rEnd = (float) Math.sqrt(radius * radius - (yEnd * yEnd));

        for (int j = 0; j < detail; j++) {
            double lng0 = 2 * Math.PI * (double) (j) / detail;
            float cos0 = (float) Math.cos(lng0);
            float sin0 = (float) Math.sin(lng0);

            double lng1 = 2 * Math.PI * (double) (j + 1) / detail;
            float cos1 = (float) Math.cos(lng1);
            float sin1 = (float) Math.sin(lng1);

            bf.vertex(mat, cos1 * rStart, yStart, sin1 * rStart).color(r, g, b, a).endVertex();
            bf.vertex(mat, cos1 * rEnd, yEnd, sin1 * rEnd).color(r, g, b, a).endVertex();
            bf.vertex(mat, cos0 * rEnd, yEnd, sin0 * rEnd).color(r, g, b, a).endVertex();
            bf.vertex(mat, cos0 * rStart, yStart, sin0 * rStart).color(r, g, b, a).endVertex();
        }
    }
}
