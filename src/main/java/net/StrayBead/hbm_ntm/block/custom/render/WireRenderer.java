package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.StrayBead.hbm_ntm.block.custom.LargeElectricityPylonBlock;
import net.StrayBead.hbm_ntm.block.custom.entity.PylonBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
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
public class WireRenderer {

    private static final float SAG_AMOUNT = 1.3f;
    private static final int SEGMENTS = 12;

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;

        PoseStack poseStack = event.getPoseStack();
        Vec3 cameraPos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();

        // We use RenderType.lines() but we MUST provide light data to the builder
        VertexConsumer builder = bufferSource.getBuffer(RenderType.lines());

        poseStack.pushPose();
        poseStack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        Matrix4f posMatrix = poseStack.last().pose();

        float topSideOffset = 4.1f;
        float topHeightOffset = 13.4f;
        float bottomSideOffset = 4.1f;
        float bottomHeightOffset = 12f;

        for (PylonBlockEntity pylon : PylonBlockEntity.LOADED_PYLONS) {
            BlockPos startPos = pylon.getBlockPos();
            Direction startFacing = pylon.getBlockState().getValue(LargeElectricityPylonBlock.FACING);

            int light = LevelRenderer.getLightColor(pylon.getLevel(), startPos);

            for (BlockPos endPos : pylon.getConnections()) {
                Direction endFacing = startFacing;
                if (pylon.getLevel().getBlockEntity(endPos) instanceof PylonBlockEntity endPylon) {
                    endFacing = endPylon.getBlockState().getValue(LargeElectricityPylonBlock.FACING);
                }

                renderDoubleWire(posMatrix, builder, startPos, startFacing, endPos, endFacing, topSideOffset, topHeightOffset, light);
                renderDoubleWire(posMatrix, builder, startPos, startFacing, endPos, endFacing, bottomSideOffset, bottomHeightOffset, light);
            }
        }

        poseStack.popPose();
        bufferSource.endBatch(RenderType.lines());
    }

    private static void renderDoubleWire(Matrix4f matrix, VertexConsumer builder, BlockPos startPos, Direction startFacing, BlockPos endPos, Direction endFacing, float sideOffset, float heightOffset, int light) {
        Vec3 p1Left = getOffsetPos(startPos, startFacing, -sideOffset, heightOffset);
        Vec3 p1Right = getOffsetPos(startPos, startFacing, sideOffset, heightOffset);
        Vec3 p2Left = getOffsetPos(endPos, endFacing, -sideOffset, heightOffset);
        Vec3 p2Right = getOffsetPos(endPos, endFacing, sideOffset, heightOffset);

        if (startFacing == endFacing.getOpposite()) {
            renderCurvedWire(matrix, builder, p1Left, p2Right, light);
            renderCurvedWire(matrix, builder, p1Right, p2Left, light);
        } else {
            renderCurvedWire(matrix, builder, p1Left, p2Left, light);
            renderCurvedWire(matrix, builder, p1Right, p2Right, light);
        }
    }

    private static void renderCurvedWire(Matrix4f matrix, VertexConsumer builder, Vec3 start, Vec3 end, int light) {
        Vec3 previousStep = start;
        for (int i = 1; i <= SEGMENTS; i++) {
            float ratio = (float) i / SEGMENTS;
            double x = start.x + (end.x - start.x) * ratio;
            double z = start.z + (end.z - start.z) * ratio;
            double y = start.y + (end.y - start.y) * ratio;
            double sag = 4 * ratio * (1.0f - ratio) * SAG_AMOUNT;
            y -= sag;

            Vec3 currentStep = new Vec3(x, y, z);
            drawLineSegment(matrix, builder, previousStep, currentStep, light);
            previousStep = currentStep;
        }
    }

    private static void drawLineSegment(Matrix4f matrix, VertexConsumer builder, Vec3 s, Vec3 e, int light) {
        // uv2(light) is the critical part that stops the "glow"
        builder.vertex(matrix, (float)s.x, (float)s.y, (float)s.z)
                .color(184, 115, 51, 255)
                .uv2(light) // Sets the lightmap brightness
                .normal(1, 0, 0)
                .endVertex();

        builder.vertex(matrix, (float)e.x, (float)e.y, (float)e.z)
                .color(184, 115, 51, 255)
                .uv2(light) // Sets the lightmap brightness
                .normal(1, 0, 0)
                .endVertex();
    }

    private static Vec3 getOffsetPos(BlockPos pos, Direction facing, float sideOffset, float heightOffset) {
        double x = pos.getX() + 0.5;
        double y = pos.getY() + heightOffset;
        double z = pos.getZ() + 0.5;
        Direction sideDir = facing.getClockWise();
        x += sideDir.getStepX() * sideOffset;
        z += sideDir.getStepZ() * sideOffset;
        return new Vec3(x, y, z);
    }
}