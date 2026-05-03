package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.StrayBead.hbm_ntm.block.custom.entity.PaintableCoatedUniversalFluidDuctBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.PaintableCopperCableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PaintableCableRenderer implements BlockEntityRenderer<PaintableCopperCableBlockEntity> {

    public PaintableCableRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(PaintableCopperCableBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState paintState = entity.getPaintState();
        if (paintState == null || paintState.isAir()) return;

        Level level = entity.getLevel();
        BlockPos pos = entity.getBlockPos();
        TextureAtlasSprite sprite = Minecraft.getInstance().getBlockRenderer().getBlockModel(paintState).getParticleIcon();
        VertexConsumer consumer = buffer.getBuffer(RenderType.cutout());
        float o = 0.001f;

        poseStack.pushPose();

        poseStack.pushPose();
        rotateCentered(poseStack, new Quaternionf().rotationY((float)Math.toRadians(180)));
        renderFace(poseStack, consumer, sprite, 1 + o, getLight(level, pos.north(), packedLight), 0, 0, 1);
        poseStack.popPose();

        // 2. Render South (Forward)
        renderFace(poseStack, consumer, sprite, 1 + o, getLight(level, pos.south(), packedLight), 0, 0, 1);

        // 3. Render Top (Up)
        poseStack.pushPose();
        rotateCentered(poseStack, new Quaternionf().rotationX((float)Math.toRadians(-90)));
        renderFace(poseStack, consumer, sprite, 1 + o, getLight(level, pos.above(), packedLight), 0, 0, 1);
        poseStack.popPose();

        // 4. Render Bottom (Down)
        poseStack.pushPose();
        rotateCentered(poseStack, new Quaternionf().rotationX((float)Math.toRadians(90)));
        renderFace(poseStack, consumer, sprite, 1 + o, getLight(level, pos.below(), packedLight), 0, 0, 1);
        poseStack.popPose();

        // 5. Render East (Right)
        poseStack.pushPose();
        rotateCentered(poseStack, new Quaternionf().rotationY((float)Math.toRadians(-90)));
        renderFace(poseStack, consumer, sprite, 1 + o, getLight(level, pos.east(), packedLight), 0, 0, 1);
        poseStack.popPose();

        // 6. Render West (Left)
        poseStack.pushPose();
        rotateCentered(poseStack, new Quaternionf().rotationY((float)Math.toRadians(90)));
        renderFace(poseStack, consumer, sprite, 1 + o, getLight(level, pos.west(), packedLight), 0, 0, 1);
        poseStack.popPose();

        poseStack.popPose();
    }

    private void rotateCentered(PoseStack poseStack, Quaternionf quaternion) {
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(quaternion);
        poseStack.translate(-0.5f, -0.5f, -0.5f);
    }

    private int getLight(Level level, BlockPos neighborPos, int defaultLight) {
        return LevelRenderer.getLightColor(level, neighborPos);
    }

    private void renderFace(PoseStack poseStack, VertexConsumer consumer, TextureAtlasSprite sprite, float zOffset, int light, float nx, float ny, float nz) {
        renderQuad(poseStack, consumer, sprite, 0, 9/16f, zOffset, 1f, 1f, light, nx, ny, nz);
        renderQuad(poseStack, consumer, sprite, 0, 0, zOffset, 1f, 7/16f, light, nx, ny, nz);
        renderQuad(poseStack, consumer, sprite, 0, 7/16f, zOffset, 7/16f, 9/16f, light, nx, ny, nz);
        renderQuad(poseStack, consumer, sprite, 9/16f, 7/16f, zOffset, 1f, 9/16f, light, nx, ny, nz);
    }

    private void renderQuad(PoseStack poseStack, VertexConsumer consumer, TextureAtlasSprite sprite, float minX, float minY, float z, float maxX, float maxY, int light, float nx, float ny, float nz) {
        Matrix4f posMatrix = poseStack.last().pose();
        Matrix3f normalMatrix = poseStack.last().normal();

        Vector3f transformedNormal = normalMatrix.transform(new Vector3f(nx, ny, nz));

        float minU = sprite.getU(minX * 16);
        float maxU = sprite.getU(maxX * 16);
        float minV = sprite.getV((1 - maxY) * 16);
        float maxV = sprite.getV((1 - minY) * 16);

        consumer.vertex(posMatrix, minX, minY, z).color(255, 255, 255, 255).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(transformedNormal.x(), transformedNormal.y(), transformedNormal.z()).endVertex();
        consumer.vertex(posMatrix, maxX, minY, z).color(255, 255, 255, 255).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(transformedNormal.x(), transformedNormal.y(), transformedNormal.z()).endVertex();
        consumer.vertex(posMatrix, maxX, maxY, z).color(255, 255, 255, 255).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(transformedNormal.x(), transformedNormal.y(), transformedNormal.z()).endVertex();
        consumer.vertex(posMatrix, minX, maxY, z).color(255, 255, 255, 255).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(transformedNormal.x(), transformedNormal.y(), transformedNormal.z()).endVertex();
    }
}