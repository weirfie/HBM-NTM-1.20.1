package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.StrayBead.hbm_ntm.block.custom.entity.ControlRodTopBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;

public class ControlRodTopRenderer implements BlockEntityRenderer<ControlRodTopBlockEntity> {
    public static boolean canStretch = false;

    public ControlRodTopRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(ControlRodTopBlockEntity entity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
        BlockState state = entity.getBlockState();
        RenderType type = ItemBlockRenderTypes.getChunkRenderType(state);
        VertexConsumer vertexConsumer = buffer.getBuffer(type);

        float maxTick = 40.0f;
        float currentTick = entity.animationTick;

        if (entity.isActive && currentTick < maxTick) currentTick += partialTicks;
        else if (!entity.isActive && currentTick > 0) currentTick -= partialTicks;

        currentTick = Math.max(0, Math.min(maxTick, currentTick));

        float expansionAmount = 2.0f;
        float scaleY = 1.0f + (currentTick / maxTick) * expansionAmount;

        poseStack.pushPose();

        poseStack.scale(1.0f, scaleY, 1.0f);

        dispatcher.getModelRenderer().tesselateBlock(
                entity.getLevel(),
                dispatcher.getBlockModel(state),
                state,
                entity.getBlockPos(),
                poseStack,
                vertexConsumer,
                false,
                entity.getLevel().random,
                state.getSeed(entity.getBlockPos()),
                combinedOverlay,
                ModelData.EMPTY,
                type
        );

        poseStack.popPose();
    }
}