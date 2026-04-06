package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.StrayBead.hbm_ntm.block.custom.entity.BurnerPressBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;

public class BurnerPressBlockEntityRenderer implements BlockEntityRenderer<BurnerPressBlockEntity> {
    public BurnerPressBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BurnerPressBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        int lightLevel = getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos());

        ItemStack itemStack = pBlockEntity.getRenderStack();
        pPoseStack.pushPose();
        pPoseStack.translate(0.5f, 0.9f, 0.5f);
        pPoseStack.scale(0.25f, 0.25f, 0.25f);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(90));

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED,
                getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);

        pPoseStack.popPose();

        if (pBlockEntity.getProgress() > 0) {
            float progress = (float) pBlockEntity.getProgress() / pBlockEntity.getMaxProgress();
            float startY = 1.6f;
            float endY = 1.0f;
            float currentY = startY - ((startY - endY) * progress);

            var blockRenderer = Minecraft.getInstance().getBlockRenderer();
            var ironBlockState = Blocks.IRON_BLOCK.defaultBlockState();

            pPoseStack.pushPose();
            pPoseStack.translate(0.5f, currentY, 0.5f);
            pPoseStack.scale(0.8f, 0.2f, 0.8f);
            pPoseStack.translate(-0.5f, -0.5f, -0.5f);

            blockRenderer.renderSingleBlock(ironBlockState, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay, net.minecraftforge.client.model.data.ModelData.EMPTY, null);
            pPoseStack.popPose();

            pPoseStack.pushPose();
            float beamTop = 2.0f;
            float beamBottom = currentY + 0.1f;
            float beamHeight = beamTop - beamBottom;

            pPoseStack.translate(0.5f, beamBottom + (beamHeight / 2.0f), 0.5f);
            pPoseStack.scale(0.15f, beamHeight, 0.15f);
            pPoseStack.translate(-0.5f, -0.5f, -0.5f);

            blockRenderer.renderSingleBlock(ironBlockState, pPoseStack, pBufferSource, pPackedLight, pPackedOverlay, net.minecraftforge.client.model.data.ModelData.EMPTY, null);
            pPoseStack.popPose();
        }
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
