package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.StrayBead.hbm_ntm.block.custom.BedrockOreProcessorBlock;
import net.StrayBead.hbm_ntm.block.custom.ElectricGroundwaterPumpBlock;
import net.StrayBead.hbm_ntm.block.custom.entity.BedrockOreProcessorBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.ElectricGroundwaterPumpBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;

public class ElectricGroundwaterPumpBER implements BlockEntityRenderer<ElectricGroundwaterPumpBlockEntity> {

    private static final ResourceLocation PROCESSOR_TOP_MODEL = new ResourceLocation("hbm_ntm", "block/electric_groundwater_pump_top");

    public ElectricGroundwaterPumpBER(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ElectricGroundwaterPumpBlockEntity be, float partialTick, PoseStack pose, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState state = be.getBlockState();

        if (!(state.getBlock() instanceof ElectricGroundwaterPumpBlock) || !state.getValue(ElectricGroundwaterPumpBlock.WORKING)) {
            return;
        }

        float time = (be.getLevel().getGameTime() + partialTick);

        float totalCycle = 156f;
        float cyclePos = time % totalCycle;

        float offsetY;
        float maxElevation = 1.0f;

        if (cyclePos < (totalCycle / 2f)) {
            offsetY = (cyclePos / (totalCycle / 2f)) * maxElevation;
        } else {
            offsetY = maxElevation - ((cyclePos - (totalCycle / 2f)) / (totalCycle / 2f)) * maxElevation;
        }

        renderPumpTop(pose, buffer, state, offsetY, packedLight, packedOverlay);
    }

    private void renderPumpTop(PoseStack pose, MultiBufferSource buffer, BlockState state, float offsetY, int packedLight, int packedOverlay) {
        pose.pushPose();

        pose.translate(0.5, 0.5, 0.5);

        float rotationY = state.getValue(ElectricGroundwaterPumpBlock.FACING).toYRot();
        pose.mulPose(Axis.YP.rotationDegrees(-rotationY));

        pose.translate(0.0, offsetY, 0.0);

        pose.translate(-0.5, -0.5, -0.5);

        BakedModel hatchModel = Minecraft.getInstance().getModelManager().getModel(PROCESSOR_TOP_MODEL);
        Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(
                pose.last(), buffer.getBuffer(RenderType.cutout()), state, hatchModel,
                1.0f, 1.0f, 1.0f, packedLight, packedOverlay, ModelData.EMPTY, RenderType.cutout()
        );

        pose.popPose();
    }
}
