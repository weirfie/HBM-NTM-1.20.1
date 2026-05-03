package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.StrayBead.hbm_ntm.block.custom.FelBlock;
import net.StrayBead.hbm_ntm.block.custom.entity.FelBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FelLaserRenderer {

    private static final int LASER_LENGTH = 21;
    private static final int SEGMENTS_PER_BLOCK = 2;

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;

        PoseStack poseStack = event.getPoseStack();
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

        VertexConsumer builder = bufferSource.getBuffer(RenderType.lightning());

        poseStack.pushPose();
        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4f posMatrix = poseStack.last().pose();

        float time = event.getRenderTick() + event.getPartialTick();

        for (FelBlockEntity fel : FelBlockEntity.LOADED_FELS) {
            if (!fel.isFiring) continue;
            BlockPos startPos = fel.getBlockPos();
            Direction facing = fel.getBlockState().getValue(FelBlock.FACING);
            renderLaserBeam(posMatrix, builder, startPos, facing, time);
        }

        poseStack.popPose();
        bufferSource.endBatch(RenderType.lightning());
    }

    private static void renderLaserBeam(Matrix4f matrix, VertexConsumer builder, BlockPos pos, Direction facing, float time) {
        Vec3 start = new Vec3(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
        start = start.add(facing.getStepX() * 0.5, facing.getStepY() * 0.5, facing.getStepZ() * 0.5);
        Vec3 forward = new Vec3(facing.getStepX(), facing.getStepY(), facing.getStepZ());

        Vec3 up = (facing == Direction.UP || facing == Direction.DOWN) ? new Vec3(0, 0, 1) : new Vec3(0, 1, 0);
        Vec3 right = forward.cross(up).normalize();
        up = right.cross(forward).normalize();

        Vec3 diag1 = up.add(right).normalize();
        Vec3 diag2 = up.subtract(right).normalize();

        int totalSegments = LASER_LENGTH * SEGMENTS_PER_BLOCK;
        float segmentLength = 1.0f / SEGMENTS_PER_BLOCK;

        for (int i = 0; i < totalSegments; i++) {
            float dist1 = i * segmentLength;
            float dist2 = (i + 1) * segmentLength;

            Vec3 p1 = start.add(forward.scale(dist1));
            Vec3 p2 = start.add(forward.scale(dist2));

            float bulge1 = (float) Math.max(0, Math.sin((dist1 * 3.0f) - (time * 0.6f)) * 0.08f);
            float bulge2 = (float) Math.max(0, Math.sin((dist2 * 3.0f) - (time * 0.6f)) * 0.08f);

            float r1 = 0.05f + bulge1;
            float r2 = 0.05f + bulge2;

            drawQuad(matrix, builder, p1, p2, diag1, r1, r2, 255, 0, 0, 150);
            drawQuad(matrix, builder, p1, p2, diag2, r1, r2, 255, 0, 0, 150);
            drawQuad(matrix, builder, p1, p2, diag1, r1/2, r2/2, 255, 200, 200, 255);
            drawQuad(matrix, builder, p1, p2, diag2, r1/2, r2/2, 255, 200, 200, 255);
        }
    }

    private static void drawQuad(Matrix4f matrix, VertexConsumer builder, Vec3 p1, Vec3 p2, Vec3 offsetAxis, float r1, float r2, int r, int g, int b, int a) {
        Vec3 off1 = offsetAxis.scale(r1);
        Vec3 off2 = offsetAxis.scale(r2);

        builder.vertex(matrix, (float)(p1.x - off1.x), (float)(p1.y - off1.y), (float)(p1.z - off1.z)).color(r, g, b, a).endVertex();
        builder.vertex(matrix, (float)(p1.x + off1.x), (float)(p1.y + off1.y), (float)(p1.z + off1.z)).color(r, g, b, a).endVertex();
        builder.vertex(matrix, (float)(p2.x + off2.x), (float)(p2.y + off2.y), (float)(p2.z + off2.z)).color(r, g, b, a).endVertex();
        builder.vertex(matrix, (float)(p2.x - off2.x), (float)(p2.y - off2.y), (float)(p2.z - off2.z)).color(r, g, b, a).endVertex();

        builder.vertex(matrix, (float)(p1.x - off1.x), (float)(p1.y - off1.y), (float)(p1.z - off1.z)).color(r, g, b, a).endVertex();
        builder.vertex(matrix, (float)(p2.x - off2.x), (float)(p2.y - off2.y), (float)(p2.z - off2.z)).color(r, g, b, a).endVertex();
        builder.vertex(matrix, (float)(p2.x + off2.x), (float)(p2.y + off2.y), (float)(p2.z + off2.z)).color(r, g, b, a).endVertex();
        builder.vertex(matrix, (float)(p1.x + off1.x), (float)(p1.y + off1.y), (float)(p1.z + off1.z)).color(r, g, b, a).endVertex();
    }
}