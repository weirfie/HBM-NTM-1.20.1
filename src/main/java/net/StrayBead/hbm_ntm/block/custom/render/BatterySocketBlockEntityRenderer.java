package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.StrayBead.hbm_ntm.block.custom.BatterySocketBlock;
import net.StrayBead.hbm_ntm.block.custom.entity.BatterySocketBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;

public class BatterySocketBlockEntityRenderer implements BlockEntityRenderer<BatterySocketBlockEntity> {
    public BatterySocketBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(BatterySocketBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack,
                       MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {

        ItemStack itemStack = pBlockEntity.getRenderStack();
        if (itemStack.isEmpty()) return;

        BlockState state = pBlockEntity.getBlockState();
        if (!(state.getBlock() instanceof BatterySocketBlock)) return;

        Direction facing = state.getValue(BatterySocketBlock.FACING);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        pPoseStack.pushPose();

        float xOff = 0.5f;
        float yOff = 0.5f;
        float zOff = 0.5f;

        switch (facing) {
            case NORTH -> { xOff = 1.0f; zOff = 1.0f; }
            case SOUTH -> { xOff = 0.0f; zOff = 0.0f; }
            case WEST  -> { xOff = 1.0f; zOff = 0.0f; }
            case EAST  -> { xOff = 0.0f; zOff = 1.0f; }
        }

        pPoseStack.translate(xOff, yOff, zOff);

        pPoseStack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));

        float scale = 0.9f;
        pPoseStack.scale(scale, scale, scale);

        itemRenderer.renderStatic(itemStack, ItemDisplayContext.FIXED,
                getLightLevel(pBlockEntity.getLevel(), pBlockEntity.getBlockPos()),
                OverlayTexture.NO_OVERLAY, pPoseStack, pBufferSource, pBlockEntity.getLevel(), 1);

        pPoseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }
}
