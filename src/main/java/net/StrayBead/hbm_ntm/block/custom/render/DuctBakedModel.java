package net.StrayBead.hbm_ntm.block.custom.render;

import net.StrayBead.hbm_ntm.block.custom.entity.PaintableCoatedUniversalFluidDuctBlockEntity;

public class DuctBakedModel extends net.minecraftforge.client.model.BakedModelWrapper<net.minecraft.client.resources.model.BakedModel> {
    public DuctBakedModel(net.minecraft.client.resources.model.BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public java.util.List<net.minecraft.client.renderer.block.model.BakedQuad> getQuads(@javax.annotation.Nullable net.minecraft.world.level.block.state.BlockState state, @javax.annotation.Nullable net.minecraft.core.Direction side, net.minecraft.util.RandomSource rand, net.minecraftforge.client.model.data.ModelData data, @javax.annotation.Nullable net.minecraft.client.renderer.RenderType renderType) {
        var originalQuads = super.getQuads(state, side, rand, data, renderType);
        var paint = data.get(PaintableCoatedUniversalFluidDuctBlockEntity.PAINT_PROPERTY);

        if (paint == null || paint.isAir()) return originalQuads;

        var paintSprite = net.minecraft.client.Minecraft.getInstance().getBlockRenderer()
                .getBlockModel(paint).getParticleIcon(net.minecraftforge.client.model.data.ModelData.EMPTY);

        java.util.List<net.minecraft.client.renderer.block.model.BakedQuad> remapped = new java.util.ArrayList<>();
        for (var quad : originalQuads) {
            remapped.add(new net.minecraft.client.renderer.block.model.BakedQuad(
                    quad.getVertices(), quad.getTintIndex(), quad.getDirection(),
                    paintSprite, quad.isShade()
            ));
        }
        return remapped;
    }
}
