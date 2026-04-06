package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.StrayBead.hbm_ntm.block.custom.entity.PaintableCoatedUniversalFluidDuctBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class DuctRenderer implements BlockEntityRenderer<PaintableCoatedUniversalFluidDuctBlockEntity> {
    // 0xF000F0 is the internal constant for Full Brightness (Sky 15, Block 15)
    private static final int FULL_BRIGHT = 0xF000F0;

        public DuctRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(PaintableCoatedUniversalFluidDuctBlockEntity entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState paintState = entity.getPaintState();
        if (paintState == null || paintState.isAir()) return;

        Level level = entity.getLevel();
        BlockPos pos = entity.getBlockPos();
        TextureAtlasSprite sprite = Minecraft.getInstance().getBlockRenderer().getBlockModel(paintState).getParticleIcon();
        VertexConsumer consumer = buffer.getBuffer(RenderType.cutout());
        float o = 0.001f;

        poseStack.pushPose();

        int selfLight = packedLight;

        int lightUp    = Math.max(selfLight, LevelRenderer.getLightColor(level, pos.above()));
        int lightDown  = Math.max(selfLight, LevelRenderer.getLightColor(level, pos.below()));
        int lightNorth = Math.max(selfLight, LevelRenderer.getLightColor(level, pos.north()));
        int lightSouth = Math.max(selfLight, LevelRenderer.getLightColor(level, pos.south()));
        int lightEast  = Math.max(selfLight, LevelRenderer.getLightColor(level, pos.east()));
        int lightWest  = Math.max(selfLight, LevelRenderer.getLightColor(level, pos.west()));
        System.out.println("Light north: " + lightNorth);
        System.out.println("Light south: " + lightSouth);
        System.out.println("Light up: " + lightUp);
        System.out.println("Light down: " + lightDown);
        System.out.println("Light east: " + lightEast);
        System.out.println("Light west: " + lightWest);
        System.out.println("Time of day: " + level.getGameTime());

        renderFace(poseStack, consumer, sprite, -o, lightNorth, 0, 0, -1);
        renderFace(poseStack, consumer, sprite, 1 + o, lightSouth, 0, 0, 1);

        poseStack.pushPose();
        rotateCentered(poseStack, new Quaternionf().rotationX((float)Math.toRadians(90)));
        renderFace(poseStack, consumer, sprite, 1 + o, lightUp, 0, 1, 0);
        poseStack.popPose();

        poseStack.pushPose();
        rotateCentered(poseStack, new Quaternionf().rotationX((float)Math.toRadians(-90)));
        renderFace(poseStack, consumer, sprite, 1 + o, lightDown, 0, -1, 0);
        poseStack.popPose();

        poseStack.pushPose();
        rotateCentered(poseStack, new Quaternionf().rotationY((float)Math.toRadians(90)));
        renderFace(poseStack, consumer, sprite, 1 + o, lightEast, 1, 0, 0);
        poseStack.popPose();

        poseStack.pushPose();
        rotateCentered(poseStack, new Quaternionf().rotationY((float)Math.toRadians(-90)));
        renderFace(poseStack, consumer, sprite, 1 + o, lightWest, -1, 0, 0);
        poseStack.popPose();

        poseStack.popPose();
    }

    private void rotateCentered(PoseStack poseStack, Quaternionf quaternion) {
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(quaternion);
        poseStack.translate(-0.5f, -0.5f, -0.5f);
    }

    private void renderFace(PoseStack poseStack, VertexConsumer consumer, TextureAtlasSprite sprite, float offset, int light, float nx, float ny, float nz) {
        renderQuad(poseStack, consumer, sprite, 0, 11/16f, offset, 1f, 1f, light, nx, ny, nz);
        renderQuad(poseStack, consumer, sprite, 0, 0, offset, 1f, 5/16f, light, nx, ny, nz);
        renderQuad(poseStack, consumer, sprite, 0, 5/16f, offset, 5/16f, 11/16f, light, nx, ny, nz);
        renderQuad(poseStack, consumer, sprite, 11/16f, 5/16f, offset, 1f, 11/16f, light, nx, ny, nz);
    }

    private void renderQuad(PoseStack poseStack, VertexConsumer consumer, TextureAtlasSprite sprite, float minX, float minY, float z, float maxX, float maxY, int light, float nx, float ny, float nz) {
        Matrix4f matrix = poseStack.last().pose();
        float minU = sprite.getU(minX * 16);
        float maxU = sprite.getU(maxX * 16);
        float minV = sprite.getV((1 - maxY) * 16);
        float maxV = sprite.getV((1 - minY) * 16);

        consumer.vertex(matrix, minX, minY, z).color(255, 255, 255, 255).uv(minU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(nx, ny, nz).endVertex();
        consumer.vertex(matrix, maxX, minY, z).color(255, 255, 255, 255).uv(maxU, maxV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(nx, ny, nz).endVertex();
        consumer.vertex(matrix, maxX, maxY, z).color(255, 255, 255, 255).uv(maxU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(nx, ny, nz).endVertex();
        consumer.vertex(matrix, minX, maxY, z).color(255, 255, 255, 255).uv(minU, minV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(nx, ny, nz).endVertex();
    }
}