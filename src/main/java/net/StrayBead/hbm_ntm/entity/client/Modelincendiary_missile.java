package net.StrayBead.hbm_ntm.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class Modelincendiary_missile<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation("modid", "incendiary_missile"), "main");
    private final ModelPart incendiary_missile;
    private final ModelPart fin1;
    private final ModelPart fin4;
    private final ModelPart fin2;
    private final ModelPart fin3;

    public Modelincendiary_missile(ModelPart root) {
        this.incendiary_missile = root.getChild("incendiary_missile");
        this.fin1 = this.incendiary_missile.getChild("fin1");
        this.fin4 = this.incendiary_missile.getChild("fin4");
        this.fin2 = this.incendiary_missile.getChild("fin2");
        this.fin3 = this.incendiary_missile.getChild("fin3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition incendiary_missile = partdefinition.addOrReplaceChild("incendiary_missile",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-6.4647F, -48.876F, -6.9361F, 13.0F, 78.0F, 13.0F, new CubeDeformation(0.0F))
                        .texOffs(52, 0)
                        .addBox(-3.4647F, -79.876F, -3.9361F, 7.0F, 31.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5353F, -12.124F, 0.9361F, 3.1416F, 0.0F, 0.0F));

        PartDefinition fin1 = incendiary_missile.addOrReplaceChild("fin1", CubeListBuilder.create().texOffs(52, 38)
                        .addBox(0.9142F, -7.439F, -19.1027F, 1.0F, 25.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(80, 0)
                        .addBox(0.9142F, -10.439F, -15.1027F, 1.0F, 22.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(72, 38)
                        .addBox(0.9142F, -15.439F, -11.1027F, 1.0F, 22.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0177F, 18.563F, 0.218F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r1 = fin1.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(10, 90).addBox(-1.0F, -15.0F, -1.0F, 1.0F, 15.0F, 4.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.9142F, -6.539F, -18.4027F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r2 = fin1.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(72, 90).addBox(-1.0F, -15.0F, -1.0F, 1.0F, 15.0F, 4.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.9142F, 15.561F, -17.1027F, -0.6981F, 0.0F, 0.0F));

        PartDefinition fin4 = incendiary_missile.addOrReplaceChild("fin4", CubeListBuilder.create().texOffs(62, 38)
                        .addBox(0.9142F, -7.439F, -19.1027F, 1.0F, 25.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(82, 78)
                        .addBox(0.9142F, -10.439F, -15.1027F, 1.0F, 22.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(72, 64)
                        .addBox(0.9142F, -15.439F, -11.1027F, 1.0F, 22.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0177F, 18.563F, 0.218F, 0.0F, -2.3562F, 0.0F));

        PartDefinition cube_r3 = fin4.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(20, 90).addBox(-1.0F, -15.0F, -1.0F, 1.0F, 15.0F, 4.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.9142F, -6.539F, -18.4027F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r4 = fin4.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(92, 26).addBox(-1.0F, -15.0F, -1.0F, 1.0F, 15.0F, 4.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.9142F, 15.561F, -17.1027F, -0.6981F, 0.0F, 0.0F));

        PartDefinition fin2 = incendiary_missile.addOrReplaceChild("fin2", CubeListBuilder.create().texOffs(52, 67)
                        .addBox(0.2071F, -7.439F, -19.8098F, 1.0F, 25.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 90)
                        .addBox(0.2071F, -10.439F, -15.8098F, 1.0F, 22.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(82, 26)
                        .addBox(0.2071F, -15.439F, -11.8098F, 1.0F, 22.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0177F, 18.563F, 0.218F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r5 = fin2.addOrReplaceChild("cube_r5",
                CubeListBuilder.create().texOffs(30, 90).addBox(-1.0F, -15.0F, -1.0F, 1.0F, 15.0F, 4.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.2071F, -6.539F, -19.1098F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r6 = fin2.addOrReplaceChild("cube_r6",
                CubeListBuilder.create().texOffs(92, 45).addBox(-1.0F, -15.0F, -1.0F, 1.0F, 15.0F, 4.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.2071F, 15.561F, -17.8098F, -0.6981F, 0.0F, 0.0F));

        PartDefinition fin3 = incendiary_missile.addOrReplaceChild("fin3", CubeListBuilder.create().texOffs(62, 67)
                        .addBox(0.2071F, -7.439F, -19.8098F, 1.0F, 25.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(90, 0)
                        .addBox(0.2071F, -10.439F, -15.8098F, 1.0F, 22.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(82, 52)
                        .addBox(0.2071F, -15.439F, -11.8098F, 1.0F, 22.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0177F, 18.563F, 0.218F, 0.0F, 2.3562F, 0.0F));

        PartDefinition cube_r7 = fin3.addOrReplaceChild("cube_r7",
                CubeListBuilder.create().texOffs(40, 90).addBox(-1.0F, -15.0F, -1.0F, 1.0F, 15.0F, 4.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.2071F, -6.539F, -19.1098F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r8 = fin3.addOrReplaceChild("cube_r8",
                CubeListBuilder.create().texOffs(92, 64).addBox(-1.0F, -15.0F, -1.0F, 1.0F, 15.0F, 4.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.2071F, 15.561F, -17.8098F, -0.6981F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
                          float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        incendiary_missile.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
