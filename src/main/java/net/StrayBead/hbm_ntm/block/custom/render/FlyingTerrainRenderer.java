package net.StrayBead.hbm_ntm.block.custom.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.StrayBead.hbm_ntm.block.custom.entity.FlyingTerrainEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class FlyingTerrainRenderer extends EntityRenderer<FlyingTerrainEntity> {
    public FlyingTerrainRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(FlyingTerrainEntity entity, float yaw, float partialTicks, PoseStack ps, MultiBufferSource buffer, int light) {
        ps.pushPose();

        ps.mulPose(Axis.XP.rotationDegrees(entity.rotationX));
        ps.mulPose(Axis.YP.rotationDegrees(entity.rotationY));
        ps.mulPose(Axis.ZP.rotationDegrees(entity.rotationZ));

        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();

        for (Map.Entry<BlockPos, BlockState> entry : entity.getBlocks().entrySet()) {
            ps.pushPose();
            BlockPos relPos = entry.getKey();
            ps.translate(relPos.getX() - 0.5, relPos.getY(), relPos.getZ() - 0.5);

            // Render the individual block
            dispatcher.renderSingleBlock(entry.getValue(), ps, buffer, light, OverlayTexture.NO_OVERLAY);
            ps.popPose();
        }

        ps.popPose();
        super.render(entity, yaw, partialTicks, ps, buffer, light);
    }

    @Override
    public ResourceLocation getTextureLocation(FlyingTerrainEntity pEntity) {
        return InventoryMenu.BLOCK_ATLAS;
    }
}
