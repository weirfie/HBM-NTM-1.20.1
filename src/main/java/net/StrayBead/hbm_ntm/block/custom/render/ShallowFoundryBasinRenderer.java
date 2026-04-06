package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.StrayBead.hbm_ntm.block.custom.entity.ShallowFoundryBasinBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class ShallowFoundryBasinRenderer implements BlockEntityRenderer<ShallowFoundryBasinBlockEntity> {
    public ShallowFoundryBasinRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ShallowFoundryBasinBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        ItemStack mold = pBlockEntity.getMold();

        if (!mold.isEmpty()) {
            pPoseStack.pushPose();

            pPoseStack.translate(0.5f, 0.1f, 0.5f);

            pPoseStack.mulPose(Axis.XP.rotationDegrees(90f));

            pPoseStack.scale(0.9f, 0.9f, 1.0f);

            Minecraft.getInstance().getItemRenderer().renderStatic(
                    mold,
                    ItemDisplayContext.FIXED,
                    pPackedLight,
                    pPackedOverlay,
                    pPoseStack,
                    pBuffer,
                    pBlockEntity.getLevel(),
                    0
            );

            pPoseStack.popPose();
        }

        FluidStack fluidStack = pBlockEntity.getFluidStack();
        if (fluidStack.isEmpty()) return;

        float capacity = 10000f;
        float fluidAmount = fluidStack.getAmount();

        float maxFluidHeight = 0.4375f;
        float height = (fluidAmount / capacity) * maxFluidHeight;

        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = extensions.getStillTexture(fluidStack);

        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(stillTexture);

        VertexConsumer builder = pBuffer.getBuffer(RenderType.solid());
        Matrix4f matrix = pPoseStack.last().pose();

        float yStart = 0.13f;
        float yEnd = yStart + height;

        renderFace(matrix, builder, sprite, 0.0625f, 0.9375f, yEnd, 0.0625f, 0.9375f, pPackedLight);
    }

    private void renderFace(Matrix4f matrix, VertexConsumer builder, TextureAtlasSprite sprite,
                            float x1, float x2, float y, float z1, float z2, int light) {
        builder.vertex(matrix, x1, y, z1).color(255, 255, 255, 255).uv(sprite.getU0(), sprite.getV0()).uv2(light).normal(0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x1, y, z2).color(255, 255, 255, 255).uv(sprite.getU0(), sprite.getV1()).uv2(light).normal(0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x2, y, z2).color(255, 255, 255, 255).uv(sprite.getU1(), sprite.getV1()).uv2(light).normal(0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x2, y, z1).color(255, 255, 255, 255).uv(sprite.getU1(), sprite.getV0()).uv2(light).normal(0f, 1f, 0f).endVertex();
    }
}
