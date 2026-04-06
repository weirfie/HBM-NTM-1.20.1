package net.StrayBead.hbm_ntm.entity.client;

import net.minecraft.world.entity.Entity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.EntityModel;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;

public class Modelnuclear_missile<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("hbm_ntm", "modelnuclear_missile"), "main");
    public final ModelPart nuclear_missile;

    public Modelnuclear_missile(ModelPart root) {
        this.nuclear_missile = root.getChild("nuclear_missile");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition nuclear_missile = partdefinition.addOrReplaceChild("nuclear_missile",
                CubeListBuilder.create().texOffs(180, 184).addBox(-6.9662F, 62.5345F, -7.2258F, 14.0F, 5.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(244, 38).addBox(-5.9662F, 59.5345F, -6.2258F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                        .texOffs(260, 106).addBox(-4.9662F, 56.5345F, -5.2258F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(260, 145).addBox(-3.9662F, 54.5345F, -4.2258F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(204, 203)
                        .addBox(-4.9662F, 10.5345F, -17.2258F, 10.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(30, 0).addBox(-4.9662F, -105.4655F, -15.2258F, 10.0F, 116.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(194, 0)
                        .addBox(-25.9662F, 62.5345F, -7.2258F, 14.0F, 5.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(178, 252).addBox(-24.9662F, 59.5345F, -6.2258F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(260, 119)
                        .addBox(-23.9662F, 56.5345F, -5.2258F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(260, 155).addBox(-22.9662F, 54.5345F, -4.2258F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(194, 19)
                        .addBox(12.0338F, 62.5345F, -7.2258F, 14.0F, 5.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(252, 202).addBox(13.0338F, 59.5345F, -6.2258F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(260, 132)
                        .addBox(14.0338F, 56.5345F, -5.2258F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(260, 165).addBox(15.0338F, 54.5345F, -4.2258F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(168, 83)
                        .addBox(15.0338F, 46.5345F, -8.2258F, 13.0F, 8.0F, 15.0F, new CubeDeformation(0.0F)).texOffs(154, 80).addBox(-16.9662F, 52.5345F, -10.2258F, 33.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)).texOffs(168, 66)
                        .addBox(-12.9662F, -111.4655F, -4.2258F, 27.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(154, 120).addBox(-10.9662F, -133.4655F, -9.2258F, 22.0F, 22.0F, 19.0F, new CubeDeformation(0.0F)).texOffs(250, 0)
                        .addBox(-5.9662F, -146.4655F, -5.2258F, 11.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(168, 106).addBox(-10.9662F, -111.4655F, 4.7742F, 23.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(240, 59)
                        .addBox(-10.9662F, -111.4655F, -9.2258F, 23.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(252, 236).addBox(-6.9662F, -111.4655F, 9.7742F, 16.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(250, 24)
                        .addBox(-8.9662F, -111.4655F, -14.2258F, 17.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(194, 38).addBox(15.0338F, 38.5345F, -7.2258F, 12.0F, 8.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(252, 217)
                        .addBox(15.0338F, 30.5345F, -6.2258F, 11.0F, 8.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(260, 72).addBox(15.0338F, 22.5345F, -5.2258F, 10.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(178, 267)
                        .addBox(15.0338F, 14.5345F, -3.2258F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(168, 0).addBox(15.5338F, -51.4655F, -4.2258F, 6.0F, 59.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(270, 262)
                        .addBox(15.5338F, -61.4655F, -2.2258F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0338F, -43.5345F, 0.2258F, 0.0F, 0.0F, -3.1416F));
        PartDefinition cube_r1 = nuclear_missile.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(154, 161).addBox(-2.5F, -12.25F, -3.5F, 6.0F, 59.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(204, 267).addBox(-2.5F, -22.25F, -1.5F, 4.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-17.9662F, -39.2155F, -0.7258F, 0.0F, 3.1416F, 0.0F));
        PartDefinition cube_r2 = nuclear_missile.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(260, 89).addBox(-5.25F, -12.0F, -4.5F, 10.0F, 8.0F, 9.0F, new CubeDeformation(0.0F)).texOffs(270, 249).addBox(-5.25F, -20.0F, -2.5F, 8.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(226, 252)
                        .addBox(-5.25F, -4.0F, -5.5F, 11.0F, 8.0F, 11.0F, new CubeDeformation(0.0F)).texOffs(236, 181).addBox(-5.25F, 4.0F, -6.5F, 12.0F, 8.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(180, 161)
                        .addBox(-5.25F, 12.0F, -7.5F, 13.0F, 8.0F, 15.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-21.7162F, 34.5345F, -0.7258F, 0.0F, 3.1416F, 0.0F));
        PartDefinition cube_r3 = nuclear_missile.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(0, 121).addBox(2.5847F, -92.5F, -4.476F, 8.0F, 116.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(154, 227).addBox(1.5847F, 23.5F, -6.476F, 10.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.3423F, -12.9655F, -12.3927F, 0.0F, -0.5236F, 0.0F));
        PartDefinition cube_r4 = nuclear_missile.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(26, 121).addBox(6.7983F, -92.5F, -9.4031F, 8.0F, 116.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(126, 242).addBox(6.7983F, 23.5F, -11.4031F, 9.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.3423F, -12.9655F, -12.3927F, 0.0F, -1.0472F, 0.0F));
        PartDefinition cube_r5 = nuclear_missile.addOrReplaceChild("cube_r5",
                CubeListBuilder.create().texOffs(88, 0).addBox(-3.5F, -95.0F, 1.5F, 9.0F, 116.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(180, 203).addBox(-3.5F, 21.0F, -0.5F, 10.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(17.6338F, -10.4655F, -1.0258F, 0.0F, -1.5708F, 0.0F));
        PartDefinition cube_r6 = nuclear_missile.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(116, 0).addBox(-16.2705F, -92.5F, -9.4191F, 9.0F, 116.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2423F, -12.9655F, 13.6073F, -3.1416F, -1.0472F, 3.1416F));
        PartDefinition cube_r7 = nuclear_missile.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(60, 0).addBox(-11.1364F, -92.5F, -4.7564F, 9.0F, 116.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2423F, -12.9655F, 13.6073F, -3.1416F, -0.5236F, 3.1416F));
        PartDefinition cube_r8 = nuclear_missile.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(52, 121).addBox(-4.3085F, -92.5F, -2.833F, 8.0F, 116.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2423F, -12.9655F, 13.6073F, -3.1416F, 0.0F, 3.1416F));
        PartDefinition cube_r9 = nuclear_missile.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).addBox(1.5847F, -92.5F, -4.476F, 10.0F, 116.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2423F, -12.9655F, 13.6073F, -3.1416F, 0.5236F, 3.1416F));
        PartDefinition cube_r10 = nuclear_missile.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(144, 0).addBox(7.7983F, -92.5F, -9.4031F, 8.0F, 116.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2423F, -12.9655F, 13.6073F, -3.1416F, 1.0472F, 3.1416F));
        PartDefinition cube_r11 = nuclear_missile.addOrReplaceChild("cube_r11",
                CubeListBuilder.create().texOffs(78, 121).addBox(-11.7F, -92.5F, -2.833F, 9.0F, 116.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(228, 203).addBox(-12.7F, 23.5F, -4.833F, 10.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.8662F, -12.9655F, -6.7927F, 0.0F, 1.5708F, 0.0F));
        PartDefinition cube_r12 = nuclear_missile.addOrReplaceChild("cube_r12",
                CubeListBuilder.create().texOffs(104, 121).addBox(-15.2705F, -92.5F, -9.4191F, 9.0F, 116.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(102, 241).addBox(-16.2705F, 23.5F, -11.4191F, 10.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.3423F, -12.9655F, -12.3927F, 0.0F, 1.0472F, 0.0F));
        PartDefinition cube_r13 = nuclear_missile.addOrReplaceChild("cube_r13",
                CubeListBuilder.create().texOffs(130, 121).addBox(-10.1364F, -92.5F, -4.7564F, 7.0F, 116.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 242).addBox(-11.1364F, 23.5F, -6.7564F, 9.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.3423F, -12.9655F, -12.3927F, 0.0F, 0.5236F, 0.0F));
        PartDefinition cube_r14 = nuclear_missile.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(22, 242).addBox(6.7983F, -23.5F, -11.4031F, 9.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2423F, 34.0345F, 13.6073F, -3.1416F, 1.0472F, 3.1416F));
        PartDefinition cube_r15 = nuclear_missile.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(236, 83).addBox(-5.3085F, -23.5F, -4.833F, 10.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2423F, 34.0345F, 13.6073F, -3.1416F, 0.0F, 3.1416F));
        PartDefinition cube_r16 = nuclear_missile.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(236, 132).addBox(1.5847F, -23.5F, -6.476F, 10.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2423F, 34.0345F, 13.6073F, -3.1416F, 0.5236F, 3.1416F));
        PartDefinition cube_r17 = nuclear_missile.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(44, 242).addBox(-11.1364F, -23.5F, -6.7564F, 9.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2423F, 34.0345F, 13.6073F, -3.1416F, -0.5236F, 3.1416F));
        PartDefinition cube_r18 = nuclear_missile.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(78, 241).addBox(-16.2705F, -23.5F, -11.4191F, 10.0F, 47.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2423F, 34.0345F, 13.6073F, -3.1416F, -1.0472F, 3.1416F));
        return LayerDefinition.create(meshdefinition, 512, 512);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        nuclear_missile.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}

