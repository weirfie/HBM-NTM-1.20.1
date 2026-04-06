package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.StrayBead.hbm_ntm.block.custom.entity.CrucibleBlockEntity;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix4f;

public class CrucibleRenderer implements BlockEntityRenderer<CrucibleBlockEntity> {
    public CrucibleRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CrucibleBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {

        FluidStack fluidStack = pBlockEntity.getFluidStack();
        if (fluidStack.isEmpty()) {
            return;
        }

        float capacity = 64000f;
        float fluidAmount = fluidStack.getAmount();

        float minHeight = 0.3125f;
        float maxHeight = 0.9375f;
        float heightRange = maxHeight - minHeight;
        float currentHeight = minHeight + ((fluidAmount / capacity) * heightRange);

        IClientFluidTypeExtensions extensions = IClientFluidTypeExtensions.of(Fluids.LAVA);
        TextureAtlasSprite sprite = Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(extensions.getStillTexture());

        VertexConsumer builder = pBuffer.getBuffer(RenderType.solid());
        Matrix4f matrix = pPoseStack.last().pose();

        renderFace(matrix, builder, sprite, -0.5f, 1.5f, currentHeight, -0.5f, 1.5f, pPackedLight);
    }

    private void renderFace(Matrix4f matrix, VertexConsumer builder, TextureAtlasSprite sprite,
                            float x1, float x2, float y, float z1, float z2, int light) {
        builder.vertex(matrix, x1, y, z1).color(255, 255, 255, 255).uv(sprite.getU0(), sprite.getV0()).uv2(light).normal(0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x1, y, z2).color(255, 255, 255, 255).uv(sprite.getU0(), sprite.getV1()).uv2(light).normal(0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x2, y, z2).color(255, 255, 255, 255).uv(sprite.getU1(), sprite.getV1()).uv2(light).normal(0f, 1f, 0f).endVertex();
        builder.vertex(matrix, x2, y, z1).color(255, 255, 255, 255).uv(sprite.getU1(), sprite.getV0()).uv2(light).normal(0f, 1f, 0f).endVertex();
    }
}
