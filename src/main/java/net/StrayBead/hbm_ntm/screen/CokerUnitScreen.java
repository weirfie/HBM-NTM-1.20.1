package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.StrayBead.hbm_ntm.screen.renderer.EnergyInfoArea;
import net.StrayBead.hbm_ntm.screen.renderer.FluidTankRenderer;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;

public class CokerUnitScreen extends AbstractContainerScreen<CokerUnitMenu> {
    private final static HashMap<String, Object> guistate = CokerUnitMenu.guistate;
    private FluidTankRenderer renderer;
    private FluidTankRenderer outputRenderer;

    public CokerUnitScreen(CokerUnitMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 180;
        this.imageHeight = 170;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/gui_coker.png");

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
        int arrowX = x + 68;
        int arrowY = y + 31;

        guiGraphics.blit(texture, this.leftPos + 2, this.topPos - 17, 0, 0, 256, 256, 256, 256);
        renderer.render(guiGraphics, x + 55, y + 15, menu.getFluidStack());

        outputRenderer.render(guiGraphics, x + 89, y + 23, menu.getTank1());

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

    @Override
    protected void renderLabels(GuiGraphics pPoseStack, int pMouseX, int pMouseY) {
        pPoseStack.drawString(this.font, Component.literal("Coker Unit"), 64, -12, -3751773, false);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderFluidAreaTooltips(pPoseStack, pMouseX, pMouseY, x, y);

        renderOutputTooltip(pPoseStack, pMouseX, pMouseY, x, y, 11, 23, menu.getFluidStack());

        renderOutputTooltip(pPoseStack, pMouseX, pMouseY, x, y, 89, 23, menu.getTank1());
    }

    private void renderOutputTooltip(GuiGraphics graphics, int mx, int my, int x, int y, int offsetX, int offsetY, FluidStack stack) {
        if(isMouseAboveArea(mx, my, x, y, offsetX, offsetY, 16, 64)) {
            graphics.renderComponentTooltip(font, outputRenderer.getTooltip(stack, TooltipFlag.Default.NORMAL), mx - x, my - y);
        }
    }

    @Override
    public void init() {
        super.init();
        assignFluidRenderer();
        outputRenderer = new FluidTankRenderer(40000, true, 14, 48);
    }

    private void assignFluidRenderer() {
        renderer = new FluidTankRenderer(640000, true, 16, 61);
    }

    private void renderFluidAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 55, 15, 36, 61)) {

            pGuiGraphics.renderComponentTooltip(font,
                    renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    pMouseX - x, pMouseY - y);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
