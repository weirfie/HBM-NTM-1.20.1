package net.StrayBead.hbm_ntm.entity.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.StrayBead.hbm_ntm.entity.client.Modelhigh_explosive_missile;
import net.StrayBead.hbm_ntm.entity.client.Modelnuclear_missile;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class NuclearMissileRenderer extends EntityRenderer<NuclearMissileEntity> {
    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/entities/nuclear_missile_entity.png");
    private final Modelnuclear_missile model;

    public NuclearMissileRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new Modelnuclear_missile(context.bakeLayer(Modelnuclear_missile.LAYER_LOCATION));
    }

    @Override
    public void render(NuclearMissileEntity entityIn, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferIn, int packedLightIn) {
        poseStack.pushPose();

        float lerpY = Mth.rotLerp(partialTicks, entityIn.yRotO, entityIn.getYRot());
        float lerpX = Mth.lerp(partialTicks, entityIn.xRotO, entityIn.getXRot());

        poseStack.translate(0, 0.5, 0);

        poseStack.mulPose(Axis.YP.rotationDegrees(lerpY));

        poseStack.mulPose(Axis.XP.rotationDegrees(lerpX + 90.0F));

        VertexConsumer vb = bufferIn.getBuffer(RenderType.entityCutout(this.getTextureLocation(entityIn)));
        model.renderToBuffer(poseStack, vb, packedLightIn, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);

        poseStack.popPose();
        super.render(entityIn, entityYaw, partialTicks, poseStack, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(NuclearMissileEntity entity) {
        return texture;
    }
}
