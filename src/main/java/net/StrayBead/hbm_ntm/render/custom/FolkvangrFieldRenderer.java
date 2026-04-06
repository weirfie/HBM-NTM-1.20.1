package net.StrayBead.hbm_ntm.render.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FolkvangrFieldRenderer {

    private static class FieldInstance {
        Vec3 pos;
        float maxRadius;
        int ticks;
        int maxTicks;

        FieldInstance(Vec3 pos, float maxRadius, int duration) {
            this.pos = pos;
            this.maxRadius = maxRadius;
            this.maxTicks = duration;
            this.ticks = 0;
        }
    }

    private static final List<FieldInstance> ACTIVE_FIELDS = new ArrayList<>();

    public static void addField(Vec3 pos, float radius, int duration) {
        ACTIVE_FIELDS.add(new FieldInstance(pos, radius, duration));
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) return;
        if (ACTIVE_FIELDS.isEmpty()) return;

        PoseStack poseStack = event.getPoseStack();
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();

        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        poseStack.pushPose();
        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4f matrix = poseStack.last().pose();

        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        for (int i = ACTIVE_FIELDS.size() - 1; i >= 0; i--) {
            FieldInstance field = ACTIVE_FIELDS.get(i);

            float progress = (float) field.ticks / (float) field.maxTicks;

            float currentRadius = field.maxRadius * progress;

            drawSphere(matrix, bufferBuilder, field.pos, currentRadius);

            field.ticks++;
            if (field.ticks >= field.maxTicks) {
                ACTIVE_FIELDS.remove(i);
            }
        }

        tessellator.end();
        poseStack.popPose();

        RenderSystem.enableCull();
        RenderSystem.enableBlend();
    }

    private static void drawSphere(Matrix4f matrix, VertexConsumer builder, Vec3 pos, float radius) {
        int segments = 15;

        for (int i = 0; i < segments; i++) {
            float lat0 = (float) Math.PI * (-0.5f + (float) i / segments);
            float z0 = (float) Math.sin(lat0);
            float zr0 = (float) Math.cos(lat0);

            float lat1 = (float) Math.PI * (-0.5f + (float) (i + 1) / segments);
            float z1 = (float) Math.sin(lat1);
            float zr1 = (float) Math.cos(lat1);

            for (int j = 0; j < segments; j++) {
                float lng0 = (float) (2.0 * Math.PI * (float) j / segments);
                float x0 = (float) Math.cos(lng0);
                float y0 = (float) Math.sin(lng0);

                float lng1 = (float) (2.0 * Math.PI * (float) (j + 1) / segments);
                float x1 = (float) Math.cos(lng1);
                float y1 = (float) Math.sin(lng1);

                builder.vertex(matrix, (float)pos.x + x0 * zr0 * radius, (float)pos.y + y0 * zr0 * radius, (float)pos.z + z0 * radius)
                        .color(0, 255, 255, 255).endVertex();

                builder.vertex(matrix, (float)pos.x + x0 * zr1 * radius, (float)pos.y + y0 * zr1 * radius, (float)pos.z + z1 * radius)
                        .color(0, 255, 255, 255).endVertex();

                builder.vertex(matrix, (float)pos.x + x1 * zr1 * radius, (float)pos.y + y1 * zr1 * radius, (float)pos.z + z1 * radius)
                        .color(0, 255, 255, 255).endVertex();

                builder.vertex(matrix, (float)pos.x + x1 * zr0 * radius, (float)pos.y + y1 * zr0 * radius, (float)pos.z + z0 * radius)
                        .color(0, 255, 255, 255).endVertex();
            }
        }
    }
}
