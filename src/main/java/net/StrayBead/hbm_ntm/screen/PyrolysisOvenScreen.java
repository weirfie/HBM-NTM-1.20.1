package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.screen.renderer.EnergyInfoArea;
import net.StrayBead.hbm_ntm.screen.renderer.FluidTankRenderer;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

public class PyrolysisOvenScreen extends AbstractContainerScreen<PyrolysisOvenMenu> {
    private EnergyInfoArea energyInfoArea;
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(HBMNTM.MOD_ID,"textures/screens/pyrolysis_oven_gui.png");
    private FluidTankRenderer renderer;
    private FluidTankRenderer outputRenderer;

    public PyrolysisOvenScreen(PyrolysisOvenMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();
        assignFluidRenderer();
        outputRenderer = new FluidTankRenderer(40000, true, 14, 48);
    }

    private void assignFluidRenderer() {
        renderer = new FluidTankRenderer(64000, true, 16, 61);
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        energyInfoArea = new EnergyInfoArea(x + 152, y + 17, menu.blockEntity.getEnergyStorage(), 16, 52);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        pGuiGraphics.drawString(this.font, Component.literal("Pyrolysis Oven"), 35, 4, -12829636, false);

        renderEnergyAreaTooltips(pGuiGraphics, pMouseX, pMouseY, x, y);
        renderFluidAreaTooltips(pGuiGraphics, pMouseX, pMouseY, x, y);
        renderOutputTooltip(pGuiGraphics, pMouseX, pMouseY, x, y, 89, 23, menu.getTank1());
    }

    private void renderFluidAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 55, 15, 16, 61)) {

            pGuiGraphics.renderComponentTooltip(font,
                    renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    pMouseX - x, pMouseY - y);
        }
    }

    private void renderEnergyAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 152, 17, 16, 52)) {
            pGuiGraphics.renderComponentTooltip(font, energyInfoArea.getTooltips(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos - 1, 0, 0, 256, 256, 256, 256);

        renderProgressArrow(pGuiGraphics, x, y);
        energyInfoArea.draw(pGuiGraphics);
        renderer.render(pGuiGraphics, x + 55, y + 15, menu.getFluidStack());
        outputRenderer.render(pGuiGraphics, x + 89, y + 23, menu.getTank1());
    }

    private void renderProgressArrow(GuiGraphics pGuiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            pGuiGraphics.blit(TEXTURE, x + 57, y + 43, 176, -4, menu.getScaledProgress(), 16);
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

    private void renderOutputTooltip(GuiGraphics graphics, int mx, int my, int x, int y, int offsetX, int offsetY, FluidStack stack) {
        if(isMouseAboveArea(mx, my, x, y, offsetX, offsetY, 16, 64)) {
            graphics.renderComponentTooltip(font, outputRenderer.getTooltip(stack, TooltipFlag.Default.NORMAL), mx - x, my - y);
        }
    }
}
