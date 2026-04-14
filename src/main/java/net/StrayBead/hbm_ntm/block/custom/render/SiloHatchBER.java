package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.StrayBead.hbm_ntm.block.custom.SiloHatchBlock;
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

public class SiloHatchBER implements BlockEntityRenderer<SiloHatchBE> {

    private static final ResourceLocation HATCH_MODEL = new ResourceLocation("hbm_ntm", "block/silo_hatch_door");

    public SiloHatchBER(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(SiloHatchBE be, float partialTick, PoseStack pose, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState state = be.getBlockState();
        if (!(state.getBlock() instanceof SiloHatchBlock)) return;

        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();
        float smoothProgress = Mth.lerp(partialTick, be.prevOpenProgress, be.openProgress);
        float angle = smoothProgress * 90f;

        pose.pushPose();

        pose.translate(0.5, 0.0, 0.5);
        float rotationY = state.getValue(SiloHatchBlock.FACING).toYRot();
        pose.mulPose(Axis.YP.rotationDegrees(-rotationY));

        pose.translate(0.0, 0.5, 2.3);

        pose.mulPose(Axis.XP.rotationDegrees(angle));

        pose.translate(-0.5, -0.75, -3.0);

        BakedModel hatchModel = Minecraft.getInstance().getModelManager().getModel(HATCH_MODEL);
        dispatcher.getModelRenderer().renderModel(
                pose.last(),
                buffer.getBuffer(RenderType.cutout()),
                state,
                hatchModel,
                1.0f, 1.0f, 1.0f,
                packedLight,
                packedOverlay,
                ModelData.EMPTY,
                RenderType.cutout()
        );

        pose.popPose();
    }
}
