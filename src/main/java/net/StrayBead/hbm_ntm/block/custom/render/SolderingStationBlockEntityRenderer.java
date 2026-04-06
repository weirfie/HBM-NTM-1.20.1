package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.StrayBead.hbm_ntm.block.custom.entity.SolderingStationBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class SolderingStationBlockEntityRenderer implements BlockEntityRenderer<SolderingStationBlockEntity> {
    public SolderingStationBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(SolderingStationBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pCombinedLight, int pCombinedOverlay) {

        ItemStack stack = pBlockEntity.getRenderStack();
        if (stack.isEmpty() || !pBlockEntity.isCrafting()) return;

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        pPoseStack.pushPose();

        pPoseStack.translate(0.5, 1.5, 0.5);
        pPoseStack.scale(0.7f, 0.7f, 0.7f);

        itemRenderer.renderStatic(stack, ItemDisplayContext.GUI, pCombinedLight,
                OverlayTexture.NO_OVERLAY, pPoseStack, pBuffer, pBlockEntity.getLevel(), 1);

        pPoseStack.popPose();
    }
}
