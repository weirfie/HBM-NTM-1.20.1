package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.StrayBead.hbm_ntm.block.custom.TimeBomb;
import net.StrayBead.hbm_ntm.block.custom.entity.TimeBombEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.client.gui.Font;

public class TimeBombRenderer implements BlockEntityRenderer<TimeBombEntity> {
    private final Font font;

    public TimeBombRenderer(BlockEntityRendererProvider.Context context) {
        this.font = context.getFont();
    }

    @Override
    public void render(TimeBombEntity be, float partialTicks, PoseStack pose, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        int totalSeconds = be.getTicksLeft() / 20;
        String text = String.format("%02d:%02d", totalSeconds / 60, totalSeconds % 60);

        pose.pushPose();

        Direction.Axis axis = be.getBlockState().getValue(TimeBomb.AXIS);

        if (axis == Direction.Axis.Y) {
            pose.translate(0.5, 0.32, 0.5);
            pose.mulPose(Axis.XP.rotationDegrees(-90));
        }
        else if (axis == Direction.Axis.X) {
            pose.translate(0.32, 0.5, 0.5);
            pose.mulPose(Axis.YP.rotationDegrees(90));
        }
        else {
            pose.translate(0.5, 0.5, 0.32);
        }

        pose.scale(0.012f, -0.012f, 0.012f);

        float xCenter = -font.width(text) / 2f;

        this.font.drawInBatch(text, xCenter, -4, 0x00FF00, false,
                pose.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, 15728880);

        pose.popPose();
    }
}
