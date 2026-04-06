package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.screen.renderer.FluidTankRenderer;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;

public class MeltdownBlastFurnaceScreen extends AbstractContainerScreen<MeltdownBlastFurnaceMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(HBMNTM.MOD_ID,"textures/screens/blast_furnace_gui.png");
    private FluidTankRenderer renderer;

    public MeltdownBlastFurnaceScreen(MeltdownBlastFurnaceMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        assignFluidRenderer();
    }

    private void assignFluidRenderer() {
        renderer = new FluidTankRenderer(6400, true, 14, 46);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderFluidAreaTooltips(pGuiGraphics, pMouseX, pMouseY, x, y);
        pGuiGraphics.drawString(this.font, Component.literal("Blast Furnace"), 53, 4, -12829636, false);
    }

    private void renderFluidAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 44, 24, 14, 46)) {

            pGuiGraphics.renderComponentTooltip(font,
                    renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    pMouseX - x, pMouseY - y);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 43, this.topPos + 55, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 43, this.topPos + 39, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 43, this.topPos + 23, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meterstick.png"), this.leftPos + 24, this.topPos + 37, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meterstick.png"), this.leftPos + 28, this.topPos + 37, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meterstickup.png"), this.leftPos + 80, this.topPos + 40, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow.png"), this.leftPos + 107, this.topPos + 39, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/smelt.png"), this.leftPos + 61, this.topPos + 38, 0, 0, 16, 16, 16, 16);

        renderSmeltProgress(guiGraphics, x, y);
        renderProgressArrow(guiGraphics, x, y);
        renderer.render(guiGraphics, x + 44, y + 24, menu.getFluidStack());
    }

    private void renderSmeltProgress(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isCrafting()) {

            guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/smelt_filled.png"),
                    this.leftPos + 61,
                    this.topPos + 38,
                    0,
                    16,
                    16,
                    16,
                    16, 16);
        }
    }

    private void renderProgressArrow(GuiGraphics pGuiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            int scaledProgress = menu.getScaledProgress();
            pGuiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_filled.png"),
                    this.leftPos + 107, this.topPos + 39, 0, 0, scaledProgress, 16, 16, 16);
        }
    }

    @Override
    public void render(GuiGraphics pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
