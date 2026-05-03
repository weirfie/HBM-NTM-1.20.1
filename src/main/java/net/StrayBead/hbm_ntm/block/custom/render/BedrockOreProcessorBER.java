package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.StrayBead.hbm_ntm.block.custom.BedrockOreProcessorBlock;
import net.StrayBead.hbm_ntm.block.custom.SiloHatchBlock;
import net.StrayBead.hbm_ntm.block.custom.entity.BedrockOreProcessorBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.SiloHatchBE;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public class BedrockOreProcessorBER implements BlockEntityRenderer<BedrockOreProcessorBlockEntity> {

    private static final ResourceLocation PROCESSOR_TOP_MODEL = new ResourceLocation("hbm_ntm", "block/bedrock_ore_processor_top");

    public BedrockOreProcessorBER(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(BedrockOreProcessorBlockEntity be, float partialTick, PoseStack pose, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState state = be.getBlockState();
        if (!(state.getBlock() instanceof BedrockOreProcessorBlock) || !state.getValue(BedrockOreProcessorBlock.WORKING)) {
            return;
        }

        pose.pushPose();

        pose.translate(-3.5, 0.5, 0.5);

        float rotationY = state.getValue(BedrockOreProcessorBlock.FACING).toYRot();
        pose.mulPose(Axis.YP.rotationDegrees(-rotationY));

        float offsetZ = 0;
        if (be.progress > 0 && be.getLevel() != null) {
            float time = be.getLevel().getGameTime() + partialTick;

            float cycleTime = time % 100;
            float maxDistance = 3.0f;

            if (cycleTime < 20) {
                offsetZ = (cycleTime / 20f) * maxDistance;
            }
            else if (cycleTime < 80) {
                offsetZ = maxDistance;
            }
            else {
                float returnProgress = (cycleTime - 80) / 20f;
                offsetZ = maxDistance - (returnProgress * maxDistance);
            }
        }

        pose.translate(0.0, 0.0, offsetZ);

        pose.translate(-0.5, -0.5, -0.5);

        BakedModel hatchModel = Minecraft.getInstance().getModelManager().getModel(PROCESSOR_TOP_MODEL);
        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(
                pose.last(), buffer.getBuffer(RenderType.cutout()), state, hatchModel,
                1.0f, 1.0f, 1.0f, packedLight, packedOverlay, ModelData.EMPTY, RenderType.cutout()
        );

        pose.popPose();
    }
}
