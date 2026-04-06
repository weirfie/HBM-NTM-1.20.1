package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.StrayBead.hbm_ntm.screen.renderer.EnergyInfoArea;
import net.StrayBead.hbm_ntm.screen.renderer.FluidTankRenderer;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;

import java.util.HashMap;

public class ArcWelderScreen extends AbstractContainerScreen<ArcWelderMenu> {
    private EnergyInfoArea energyInfoArea;
    private final static HashMap<String, Object> guistate = ArcWelderMenu.guistate;
    private FluidTankRenderer renderer;

    public ArcWelderScreen(ArcWelderMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/arc_welder_gui.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(texture, this.leftPos, this.topPos - 20, 0, 0, 256, 256, 256, 256);

        if(menu.isCrafting()) {
            int scaledProgress = menu.getScaledProgress();
            guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/welder_arrow_filled.png"),
                    this.leftPos + 72, this.topPos + 18, 0, 0, scaledProgress, 15, 31, 13);
        }

        energyInfoArea.draw(guiGraphics);
        renderer.render(guiGraphics, x + 9, y + 9, menu.getFluidStack());

        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    private void assignFluidRenderer() {
        renderer = new FluidTankRenderer(64000, true, 14, 46);
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        energyInfoArea = new EnergyInfoArea(x + 152, y + 10, menu.blockEntity.getEnergyStorage(), 16, 46);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.literal("Arc Welder"), 42, -14, -12829636, false);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(guiGraphics, mouseX, mouseY, x, y);
        renderFluidAreaTooltips(guiGraphics, mouseX, mouseY, x, y);
    }

    private void renderFluidAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 9, 9, 14, 46)) {

            pGuiGraphics.renderComponentTooltip(font,
                    renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    pMouseX - x, pMouseY - y);
        }
    }

    private void renderEnergyAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 152, 10, 14, 46)) {
            pGuiGraphics.renderComponentTooltip(font, energyInfoArea.getTooltips(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    public void init() {
        super.init();
        assignEnergyInfoArea();
        assignFluidRenderer();
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
