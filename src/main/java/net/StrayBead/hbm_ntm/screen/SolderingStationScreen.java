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
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class SolderingStationScreen extends AbstractContainerScreen<SolderingStationMenu> {
    private EnergyInfoArea energyInfoArea;
    private final static HashMap<String, Object> guistate = SolderingStationMenu.guistate;
    ImageButton imagebutton_recipe_off;
    private FluidTankRenderer renderer;

    public SolderingStationScreen(SolderingStationMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/soldering_station.png");

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
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 151, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 151, this.topPos + 25, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 151, this.topPos + 9, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow.png"), this.leftPos + 93, this.topPos + 28, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/template.png"), this.leftPos + 8, this.topPos + 57, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 8, this.topPos + 40, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 8, this.topPos + 24, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 8, this.topPos + 8, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/infinite_battery.png"), this.leftPos + 151, this.topPos + 58, 0, 0, 16, 16, 16, 16);

        if(menu.isCrafting()) {
            int scaledProgress = menu.getScaledProgress();
            guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_filled.png"),
                    this.leftPos + 93, this.topPos + 28, 0, 0, scaledProgress, 15, 22, 15);
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
        energyInfoArea = new EnergyInfoArea(x + 152, y + 10, menu.blockEntity.getEnergyStorage(), 14, 46);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.literal("Soldering Station"), 45, 5, -12829636, false);
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
        imagebutton_recipe_off = new ImageButton(this.leftPos + 24, this.topPos + 56, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/recipe_off.png"), 16, 16, e -> {
        });
        guistate.put("button:imagebutton_recipe_off", imagebutton_recipe_off);
        this.addRenderableWidget(imagebutton_recipe_off);
        assignEnergyInfoArea();
        assignFluidRenderer();
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
