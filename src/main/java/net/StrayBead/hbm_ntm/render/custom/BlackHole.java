package net.StrayBead.hbm_ntm.render.custom;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.StrayBead.hbm_ntm.block.custom.BlackHoleManager;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlackHole {

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES || BlackHoleManager.ACTIVE_HOLES.isEmpty()) return;

        PoseStack poseStack = event.getPoseStack();
        Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
        Vec3 cameraPos = camera.getPosition();

        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder builder = tesselator.getBuilder();

        for (BlackHoleManager.BlackHoleData bh : BlackHoleManager.ACTIVE_HOLES) {
            poseStack.pushPose();

            poseStack.translate(bh.position.x - cameraPos.x, bh.position.y - cameraPos.y, bh.position.z - cameraPos.z);

            double distanceToCam = cameraPos.distanceTo(bh.position);

            float entryFactor = (float) Mth.clamp(1.0 - (distanceToCam / bh.radius), 0.0, 1.0);

            if (entryFactor < 1.0f) {
                renderFlatDisk(poseStack.last().pose(), builder, bh.radius * 1.5f, bh.radius * 4.0f, entryFactor);

                poseStack.pushPose();
                poseStack.mulPose(Axis.YP.rotationDegrees(-camera.getYRot()));
                poseStack.mulPose(Axis.XP.rotationDegrees(camera.getXRot()));

                renderHaloRing(poseStack.last().pose(), builder, bh.radius * 1.2f, bh.radius * 2.5f, entryFactor);
                poseStack.popPose();
            }

            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
            RenderSystem.depthMask(false);
            Matrix4f matrix = poseStack.last().pose();
            renderSphere(matrix, builder, bh.radius * 1.05f, 255, 200, 100, (int)(150 * (1.0 - entryFactor)));

            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            renderSphere(matrix, builder, bh.radius, 0, 0, 0, 255);

            poseStack.popPose();

            if (distanceToCam < bh.radius * 1.5) {
                poseStack.pushPose();
                poseStack.translate(0, 0, 0);
                Matrix4f camMatrix = poseStack.last().pose();

                RenderSystem.depthMask(false);
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

                float warpIntensity = (float) Math.pow(Mth.clamp((bh.radius * 1.5 - distanceToCam) / (bh.radius * 1.5), 0.0, 1.0), 2);
                long time = System.currentTimeMillis();

                renderTrippySphere(camMatrix, builder, 4.0f, time, warpIntensity, 15, 0, 25, (int)(150 * warpIntensity));

                renderTrippySphere(camMatrix, builder, 3.5f, time + 1500, warpIntensity * 1.5f, 0, 0, 0, (int)(255 * warpIntensity));

                RenderSystem.depthMask(true);
                poseStack.popPose();
            }
        }

        RenderSystem.enableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
    }

    private static void renderSphere(Matrix4f matrix, BufferBuilder builder, float radius, int r, int g, int b, int a) {
        int latitudeBands = 24;
        int longitudeBands = 24;

        if (a <= 0) return;

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        for (int latNumber = 0; latNumber < latitudeBands; latNumber++) {
            float theta1 = (float) (latNumber * Math.PI / latitudeBands);
            float theta2 = (float) ((latNumber + 1) * Math.PI / latitudeBands);
            for (int longNumber = 0; longNumber < longitudeBands; longNumber++) {
                float phi1 = (float) (longNumber * 2 * Math.PI / longitudeBands);
                float phi2 = (float) ((longNumber + 1) * 2 * Math.PI / longitudeBands);

                addVertex(builder, matrix, theta1, phi1, radius, r, g, b, a);
                addVertex(builder, matrix, theta2, phi1, radius, r, g, b, a);
                addVertex(builder, matrix, theta2, phi2, radius, r, g, b, a);
                addVertex(builder, matrix, theta1, phi2, radius, r, g, b, a);
            }
        }
        Tesselator.getInstance().end();
    }

    private static void addVertex(BufferBuilder builder, Matrix4f matrix, float theta, float phi, float radius, int r, int g, int b, int a) {
        float x = (float) (Math.sin(theta) * Math.cos(phi)) * radius;
        float y = (float) Math.cos(theta) * radius;
        float z = (float) (Math.sin(theta) * Math.sin(phi)) * radius;
        builder.vertex(matrix, x, y, z).color(r, g, b, a).endVertex();
    }

    private static void renderFlatDisk(Matrix4f matrix, BufferBuilder builder, float inner, float outer, float entryFactor) {
        int segments = 64;
        int alpha = (int) (200 * (1.0 - entryFactor));
        if (alpha <= 0) return;

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        for (int i = 0; i < segments; i++) {
            float angle1 = (float) (i * 2 * Math.PI / segments);
            float angle2 = (float) ((i + 1) * 2 * Math.PI / segments);

            builder.vertex(matrix, (float)Math.cos(angle1)*inner, 0, (float)Math.sin(angle1)*inner).color(255, 120, 20, alpha).endVertex();
            builder.vertex(matrix, (float)Math.cos(angle2)*inner, 0, (float)Math.sin(angle2)*inner).color(255, 120, 20, alpha).endVertex();
            builder.vertex(matrix, (float)Math.cos(angle2)*outer, 0, (float)Math.sin(angle2)*outer).color(255, 30, 0, 0).endVertex();
            builder.vertex(matrix, (float)Math.cos(angle1)*outer, 0, (float)Math.sin(angle1)*outer).color(255, 30, 0, 0).endVertex();
        }
        Tesselator.getInstance().end();
    }

    private static void renderHaloRing(Matrix4f matrix, BufferBuilder builder, float inner, float outer, float entryFactor) {
        int segments = 64;
        int alpha = (int) (180 * (1.0 - entryFactor));
        if (alpha <= 0) return;

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        for (int i = 0; i < segments; i++) {
            float angle1 = (float) (i * 2 * Math.PI / segments);
            float angle2 = (float) ((i + 1) * 2 * Math.PI / segments);

            builder.vertex(matrix, (float)Math.cos(angle1)*inner, (float)Math.sin(angle1)*inner, 0).color(255, 150, 40, alpha).endVertex();
            builder.vertex(matrix, (float)Math.cos(angle2)*inner, (float)Math.sin(angle2)*inner, 0).color(255, 150, 40, alpha).endVertex();
            builder.vertex(matrix, (float)Math.cos(angle2)*outer, (float)Math.sin(angle2)*outer, 0).color(255, 30, 0, 0).endVertex();
            builder.vertex(matrix, (float)Math.cos(angle1)*outer, (float)Math.sin(angle1)*outer, 0).color(255, 30, 0, 0).endVertex();
        }
        Tesselator.getInstance().end();
    }

    private static void renderTrippySphere(Matrix4f matrix, BufferBuilder builder, float baseRadius, long time, float warpIntensity, int r, int g, int b, int a) {
        int latitudeBands = 32;
        int longitudeBands = 32;
        if (a <= 0) return;

        float timeF = (time % 100000L) / 300.0f;

        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        for (int latNumber = 0; latNumber < latitudeBands; latNumber++) {
            float theta1 = (float) (latNumber * Math.PI / latitudeBands);
            float theta2 = (float) ((latNumber + 1) * Math.PI / latitudeBands);
            for (int longNumber = 0; longNumber < longitudeBands; longNumber++) {
                float phi1 = (float) (longNumber * 2 * Math.PI / longitudeBands);
                float phi2 = (float) ((longNumber + 1) * 2 * Math.PI / longitudeBands);

                addTrippyVertex(builder, matrix, theta1, phi1, baseRadius, timeF, warpIntensity, r, g, b, a);
                addTrippyVertex(builder, matrix, theta2, phi1, baseRadius, timeF, warpIntensity, r, g, b, a);
                addTrippyVertex(builder, matrix, theta2, phi2, baseRadius, timeF, warpIntensity, r, g, b, a);
                addTrippyVertex(builder, matrix, theta1, phi2, baseRadius, timeF, warpIntensity, r, g, b, a);
            }
        }
        Tesselator.getInstance().end();
    }

    private static void addTrippyVertex(BufferBuilder builder, Matrix4f matrix, float theta, float phi, float baseRadius, float timeF, float warpIntensity, int r, int g, int b, int a) {
        float wave = (float) Math.sin(theta * 5.0 + timeF) * (float) Math.cos(phi * 5.0 - timeF * 0.8f);
        float currentRadius = baseRadius + (wave * warpIntensity * 2.5f);

        float twistedTheta = theta + (float) Math.sin(timeF * 0.5f) * warpIntensity * 0.5f;
        float twistedPhi = phi + (float) Math.cos(timeF * 0.3f) * warpIntensity * 0.5f;

        float x = (float) (Math.sin(twistedTheta) * Math.cos(twistedPhi)) * currentRadius;
        float y = (float) Math.cos(twistedTheta) * currentRadius;
        float z = (float) (Math.sin(twistedTheta) * Math.sin(twistedPhi)) * currentRadius;

        builder.vertex(matrix, x, y, z).color(r, g, b, a).endVertex();
    }
}
