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

public class Modelanti_ballistic_missile<T extends Entity> extends EntityModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            new ResourceLocation("modid", "anti_ballistic_missile"), "main");
    private final ModelPart anti_ballistic_missile;

    public Modelanti_ballistic_missile(ModelPart root) {
        this.anti_ballistic_missile = root.getChild("anti_ballistic_missile");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition anti_ballistic_missile = partdefinition.addOrReplaceChild("anti_ballistic_missile",
                CubeListBuilder.create().texOffs(70, 18)
                        .addBox(-7.1324F, 26.4288F, -7.0863F, 6.0F, 14.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(100, 120)
                        .addBox(-6.1324F, 19.4288F, -6.0863F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(120, 120)
                        .addBox(0.8676F, 19.4288F, -6.0863F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 91)
                        .addBox(-14.1324F, 17.4288F, -0.0863F, 13.0F, 17.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(12, 123)
                        .addBox(-6.1324F, 19.4288F, 0.9137F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(70, 121)
                        .addBox(0.8676F, 19.4288F, 0.9137F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(84, 38)
                        .addBox(-7.1324F, 26.4288F, 0.9137F, 6.0F, 14.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(84, 58)
                        .addBox(0.8676F, 26.4288F, 0.9137F, 6.0F, 14.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(84, 78)
                        .addBox(0.8676F, 26.4288F, -7.0863F, 6.0F, 14.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-3.1324F, -14.5712F, -3.0863F, 6.0F, 55.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 0)
                        .addBox(-5.1324F, -41.5712F, -6.0863F, 11.0F, 27.0F, 12.0F, new CubeDeformation(0.0F))
                        .texOffs(70, 0)
                        .addBox(-3.1324F, -52.5712F, -3.5863F, 7.0F, 11.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 113)
                        .addBox(-1.1324F, -71.5712F, -1.5863F, 3.0F, 19.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.1324F, -16.4288F, 0.0863F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r1 = anti_ballistic_missile.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(64, 39).addBox(-4.0F, -55.0F, -3.0F, 4.0F, 46.0F, 6.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2676F, 40.4288F, -0.7863F, -1.6143F, 1.5271F, -1.6143F));

        PartDefinition cube_r2 = anti_ballistic_missile.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(0, 61).addBox(-4.0F, -55.0F, -3.0F, 4.0F, 46.0F, 6.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2676F, 40.4288F, -3.1863F, 1.6144F, 1.5272F, 1.6144F));

        PartDefinition cube_r3 = anti_ballistic_missile.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(44, 39).addBox(-4.0F, -55.0F, -3.0F, 4.0F, 46.0F, 6.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.8676F, 40.4288F, -0.0863F, 0.0F, 0.0F, 0.0436F));

        PartDefinition cube_r4 = anti_ballistic_missile.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(24, 39).addBox(-4.0F, -55.0F, -3.0F, 4.0F, 46.0F, 6.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.8676F, 40.4288F, -0.0863F, 0.0F, 0.0F, -0.0436F));

        PartDefinition cube_r5 = anti_ballistic_missile.addOrReplaceChild("cube_r5",
                CubeListBuilder.create().texOffs(108, 90).addBox(-4.5882F, -15.1544F, 0.0F, 14.0F, 8.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, -24.9856F, 0.1637F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r6 = anti_ballistic_missile.addOrReplaceChild("cube_r6",
                CubeListBuilder.create().texOffs(98, 98).addBox(-13.818F, -7.5857F, 0.0F, 13.0F, 15.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, -24.9856F, 0.1637F, 0.0F, 0.0F, 0.0F));

        PartDefinition cube_r7 = anti_ballistic_missile.addOrReplaceChild("cube_r7",
                CubeListBuilder.create().texOffs(72, 113).addBox(-9.4672F, -15.099F, 0.0F, 14.0F, 8.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, -24.9856F, 0.1637F, 0.0F, 0.0F, -2.3562F));

        PartDefinition cube_r8 = anti_ballistic_missile.addOrReplaceChild("cube_r8",
                CubeListBuilder.create().texOffs(120, 17).addBox(-8.6794F, -14.7354F, 0.0F, 13.0F, 7.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, -24.9856F, 0.1637F, 3.1416F, 0.0F, -0.7854F));

        PartDefinition cube_r9 = anti_ballistic_missile.addOrReplaceChild("cube_r9",
                CubeListBuilder.create().texOffs(108, 35).addBox(-13.318F, -7.5857F, 0.0F, 12.0F, 15.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, -24.9856F, 0.1637F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r10 = anti_ballistic_missile.addOrReplaceChild("cube_r10",
                CubeListBuilder.create().texOffs(120, 24).addBox(-3.9418F, -14.508F, 0.0F, 13.0F, 7.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, -24.9856F, 0.1637F, -3.1416F, 0.0F, -2.3562F));

        PartDefinition cube_r11 = anti_ballistic_missile.addOrReplaceChild("cube_r11",
                CubeListBuilder.create().texOffs(72, 98).addBox(-13.818F, -7.5857F, 0.0F, 13.0F, 15.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, -24.9856F, 0.1637F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r12 = anti_ballistic_missile.addOrReplaceChild("cube_r12",
                CubeListBuilder.create().texOffs(108, 82).addBox(-9.4672F, -15.099F, 0.0F, 14.0F, 8.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, -24.9856F, 0.1637F, -1.5708F, -0.7854F, -1.5708F));

        PartDefinition cube_r13 = anti_ballistic_missile.addOrReplaceChild("cube_r13",
                CubeListBuilder.create().texOffs(108, 74).addBox(-4.5882F, -15.1544F, 0.0F, 14.0F, 8.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, -24.9856F, 0.1637F, -1.5708F, 0.7854F, -1.5708F));

        PartDefinition cube_r14 = anti_ballistic_missile.addOrReplaceChild("cube_r14",
                CubeListBuilder.create().texOffs(20, 108).addBox(-13.318F, -7.5857F, 0.0F, 12.0F, 15.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, -24.9856F, 0.1637F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r15 = anti_ballistic_missile.addOrReplaceChild("cube_r15",
                CubeListBuilder.create().texOffs(44, 116).addBox(-8.6794F, -14.7354F, 0.0F, 13.0F, 7.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, -24.9856F, 0.1637F, 1.5708F, 0.7854F, -1.5708F));

        PartDefinition cube_r16 = anti_ballistic_missile.addOrReplaceChild("cube_r16",
                CubeListBuilder.create().texOffs(100, 113).addBox(-3.9418F, -14.508F, 0.0F, 13.0F, 7.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, -24.9856F, 0.1637F, 1.5708F, -0.7854F, -1.5708F));

        PartDefinition cube_r17 = anti_ballistic_missile.addOrReplaceChild("cube_r17",
                CubeListBuilder.create().texOffs(98, 0).addBox(-14.068F, -3.2464F, 0.0F, 13.0F, 17.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, 20.6752F, -0.0863F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r18 = anti_ballistic_missile.addOrReplaceChild("cube_r18",
                CubeListBuilder.create().texOffs(108, 66).addBox(-7.8333F, -12.2629F, 0.0F, 14.0F, 8.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, 20.6752F, -0.0863F, 1.5708F, -0.7854F, -1.5708F));

        PartDefinition cube_r19 = anti_ballistic_missile.addOrReplaceChild("cube_r19",
                CubeListBuilder.create().texOffs(94, 18).addBox(-14.068F, -3.2464F, 0.0F, 13.0F, 17.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, 20.6752F, -0.0863F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r20 = anti_ballistic_missile.addOrReplaceChild("cube_r20",
                CubeListBuilder.create().texOffs(108, 58).addBox(-7.8333F, -12.2629F, 0.0F, 14.0F, 8.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, 20.6752F, -0.0863F, 3.1416F, 0.0F, -2.3562F));

        PartDefinition cube_r21 = anti_ballistic_missile.addOrReplaceChild("cube_r21",
                CubeListBuilder.create().texOffs(46, 91).addBox(-14.068F, -3.2464F, 0.0F, 13.0F, 17.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, 20.6752F, -0.0863F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r22 = anti_ballistic_missile.addOrReplaceChild("cube_r22",
                CubeListBuilder.create().texOffs(108, 50).addBox(-7.8333F, -12.2629F, 0.0F, 14.0F, 8.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, 20.6752F, -0.0863F, -1.5708F, 0.7854F, -1.5708F));

        PartDefinition cube_r23 = anti_ballistic_missile.addOrReplaceChild("cube_r23",
                CubeListBuilder.create().texOffs(44, 108).addBox(-7.8333F, -12.2629F, 0.0F, 14.0F, 8.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.0644F, 20.6752F, -0.0863F, 0.0F, 0.0F, -0.7854F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
                          float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay,
                               float red, float green, float blue, float alpha) {
        anti_ballistic_missile.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
