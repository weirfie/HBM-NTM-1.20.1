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

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.fluids.FluidStack;

public class ChemicalPlantScreen extends AbstractContainerScreen<ChemicalPlantMenu> {
    private final static HashMap<String, Object> guistate = ChemicalPlantMenu.guistate;
    private EnergyInfoArea energyInfoArea;
    private FluidTankRenderer renderer;
    private FluidTankRenderer outputRenderer;

    public ChemicalPlantScreen(ChemicalPlantMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 179;
        this.imageHeight = 221;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/chemical_plant_gui.png");

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

        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 11, this.topPos + 39, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 11, this.topPos + 23, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 29, this.topPos + 39, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 29, this.topPos + 23, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 47, this.topPos + 39, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 47, this.topPos + 23, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow.png"), this.leftPos + 68, this.topPos + 31, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 89, this.topPos + 39, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow.png"), x + 68, y + 31, 0, 0, 16, 16, 16, 16);

        if (menu.isCrafting()) {
            int progressWidth = menu.getScaledProgress();

            int clampedWidth = Math.min(progressWidth, 16);

            guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_filled.png"),
                    arrowX, arrowY,
                    0, 0,
                    clampedWidth, 16,
                    16, 16
            );
        }

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 89, this.topPos + 23, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 107, this.topPos + 39, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 107, this.topPos + 23, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 125, this.topPos + 39, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 125, this.topPos + 23, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/template_upgrade_slot.png"), this.leftPos + 152, this.topPos + 118, 0, 0, 18, 18, 18, 18);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/template_upgrade_slot.png"), this.leftPos + 152, this.topPos + 100, 0, 0, 18, 18, 18, 18);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/infinite_battery.png"), this.leftPos + 153, this.topPos + 79, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 153, this.topPos + 62, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 153, this.topPos + 46, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 153, this.topPos + 30, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 153, this.topPos + 14, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/metercleared.png"), this.leftPos + 61, this.topPos + 118, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/metercleared.png"), this.leftPos + 77, this.topPos + 118, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/metercleared.png"), this.leftPos + 93, this.topPos + 118, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/metercleared.png"), this.leftPos + 109, this.topPos + 118, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/metercleared.png"), this.leftPos + 125, this.topPos + 118, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/chemistry_template_base.png"), this.leftPos + 43, this.topPos + 118, 0, 0, 16, 16, 16, 16);

        energyInfoArea.draw(guiGraphics);
        renderer.render(guiGraphics, x + 55, y + 15, menu.getFluidStack());

        renderer.render(guiGraphics, x + 29, y + 23, menu.getInputTank2());
        renderer.render(guiGraphics, x + 47, y + 23, menu.getInputTank3());

        outputRenderer.render(guiGraphics, x + 89, y + 23, menu.getTank1());
        outputRenderer.render(guiGraphics, x + 107, y + 23, menu.getTank2());
        outputRenderer.render(guiGraphics, x + 125, y + 23, menu.getTank3());

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
        pPoseStack.drawString(this.font, Component.literal("Chemical Plant"), 51, 7, -12829636, false);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(pPoseStack, pMouseX, pMouseY, x, y);
        renderFluidAreaTooltips(pPoseStack, pMouseX, pMouseY, x, y);

        renderOutputTooltip(pPoseStack, pMouseX, pMouseY, x, y, 11, 23, menu.getFluidStack());
        renderOutputTooltip(pPoseStack, pMouseX, pMouseY, x, y, 29, 23, menu.getInputTank2());
        renderOutputTooltip(pPoseStack, pMouseX, pMouseY, x, y, 47, 23, menu.getInputTank3());

        renderOutputTooltip(pPoseStack, pMouseX, pMouseY, x, y, 89, 23, menu.getTank1());
        renderOutputTooltip(pPoseStack, pMouseX, pMouseY, x, y, 107, 23, menu.getTank2());
        renderOutputTooltip(pPoseStack, pMouseX, pMouseY, x, y, 125, 23, menu.getTank3());
    }

    private void renderOutputTooltip(GuiGraphics graphics, int mx, int my, int x, int y, int offsetX, int offsetY, FluidStack stack) {
        if(isMouseAboveArea(mx, my, x, y, offsetX, offsetY, 16, 64)) {
            graphics.renderComponentTooltip(font, outputRenderer.getTooltip(stack, TooltipFlag.Default.NORMAL), mx - x, my - y);
        }
    }

    @Override
    public void init() {
        super.init();
        assignEnergyInfoArea();
        assignFluidRenderer();
        outputRenderer = new FluidTankRenderer(40000, true, 14, 48);
    }

    private void assignFluidRenderer() {
        renderer = new FluidTankRenderer(640000, true, 16, 61);
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x + 153, y + 14, menu.blockEntity.getEnergyStorage(), 16, 64);
    }

    private void renderFluidAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 55, 15, 36, 61)) {

            pGuiGraphics.renderComponentTooltip(font,
                    renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    pMouseX - x, pMouseY - y);
        }
    }

    private void renderEnergyAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 153, 14, 16, 64)) {
            pGuiGraphics.renderComponentTooltip(font, energyInfoArea.getTooltips(), pMouseX - x, pMouseY - y);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
