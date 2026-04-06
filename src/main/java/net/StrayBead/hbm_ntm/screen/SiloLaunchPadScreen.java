package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.screen.renderer.EnergyInfoArea;
import net.StrayBead.hbm_ntm.screen.renderer.FluidTankRenderer;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;

import com.mojang.blaze3d.systems.RenderSystem;

public class SiloLaunchPadScreen extends AbstractContainerScreen<SiloLaunchPadMenu> {
    private EnergyInfoArea energyInfoArea;
    private FluidTankRenderer renderer;

    public SiloLaunchPadScreen(SiloLaunchPadMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 179;
        this.imageHeight = 214;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/silo_launch_pad.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        if (mouseX > leftPos + 7 && mouseX < leftPos + 31 && mouseY > topPos + 5 && mouseY < topPos + 29)
            guiGraphics.renderTooltip(font, Component.translatable("gui.hbm_ntm.silo_launch_pad.tooltip_short_range_target_designator"), mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 117, this.topPos + 74, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 117, this.topPos + 58, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 117, this.topPos + 42, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 117, this.topPos + 26, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 134, this.topPos + 74, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 134, this.topPos + 58, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 134, this.topPos + 42, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 134, this.topPos + 26, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 151, this.topPos + 74, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 151, this.topPos + 58, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 151, this.topPos + 42, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 151, this.topPos + 26, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/infinite_battery.png"), this.leftPos + 117, this.topPos + 91, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/full_bucket_gui.png"), this.leftPos + 135, this.topPos + 91, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/full_bucket_gui.png"), this.leftPos + 153, this.topPos + 91, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bucket_gui.png"), this.leftPos + 135, this.topPos + 109, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bucket_gui.png"), this.leftPos + 153, this.topPos + 109, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/button_on.png"), this.leftPos + 12, this.topPos + 101, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/torchinfo.png"), this.leftPos + 153, this.topPos + 7, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/question.png"), this.leftPos + 12, this.topPos + 8, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/blank.png"), this.leftPos + 42, this.topPos + 83, 0, 0, 32, 32, 32, 32);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/blank.png"), this.leftPos + 74, this.topPos + 83, 0, 0, 32, 32, 32, 32);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/blank.png"), this.leftPos + 42, this.topPos + 51, 0, 0, 32, 32, 32, 32);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/blank.png"), this.leftPos + 74, this.topPos + 51, 0, 0, 32, 32, 32, 32);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/blank.png"), this.leftPos + 42, this.topPos + 19, 0, 0, 32, 32, 32, 32);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/blank.png"), this.leftPos + 74, this.topPos + 19, 0, 0, 32, 32, 32, 32);

        energyInfoArea.draw(guiGraphics);
        renderer.render(guiGraphics, x + 55, y + 15, menu.getFluidStack());

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
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(pGuiGraphics, pMouseX, pMouseY, x, y);
        renderFluidAreaTooltips(pGuiGraphics, pMouseX, pMouseY, x, y);
    }

    private void renderFluidAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 55, 15, 16, 61)) {

            pGuiGraphics.renderComponentTooltip(font,
                    renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    pMouseX - x, pMouseY - y);
        }
    }

    private void renderEnergyAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 118, 27, 14, 62)) {
            pGuiGraphics.renderComponentTooltip(font, energyInfoArea.getTooltips(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    public void init() {
        super.init();
        assignEnergyInfoArea();
        assignFluidRenderer();
    }

    private void assignFluidRenderer() {
        renderer = new FluidTankRenderer(64000, true, 16, 61);
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        energyInfoArea = new EnergyInfoArea(x + 118, y + 27, menu.blockEntity.getEnergyStorage(), 14, 62);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
