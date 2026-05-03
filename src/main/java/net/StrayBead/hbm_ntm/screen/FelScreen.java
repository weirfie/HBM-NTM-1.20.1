package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.ToggleOutputPacket;
import net.StrayBead.hbm_ntm.screen.renderer.EnergyInfoArea;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;

import com.mojang.blaze3d.systems.RenderSystem;

import java.util.HashMap;

public class FelScreen extends AbstractContainerScreen<FelMenu> {
    private final static HashMap<String, Object> guistate = FelMenu.guistate;
    private EnergyInfoArea energyInfoArea;
    ImageButton imagebutton_toggle;
    private final int x, y, z;

    public FelScreen(FelMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 176;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.imageHeight = 166;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/gui_fel.png");

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
        guiGraphics.blit(texture, this.leftPos, this.topPos + 1, 0, 0, 256, 256, 256, 256);

        energyInfoArea.draw(guiGraphics);

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
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.literal("FEL"), 182, 6, -1, false);
        guiGraphics.drawString(this.font, Component.literal("Inventory"), 7, 70, -12829636, false);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(guiGraphics, mouseX, mouseY, x, y);
    }

    @Override
    public void init() {
        super.init();
        assignEnergyInfoArea();
        imagebutton_toggle = new ToggleButton(this.leftPos + 141, this.topPos + 42, e -> {
            ModMessages.sendToServer(new ToggleOutputPacket(new BlockPos(x, y, z)));
        });
        guistate.put("button:toggle", imagebutton_toggle);
        this.addRenderableWidget(imagebutton_toggle);
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        energyInfoArea = new EnergyInfoArea(x + 182, y + 28, menu.blockEntity.getEnergyStorage(), 16, 113);
    }

    private void renderEnergyAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 182, 28, 16, 113)) {
            pGuiGraphics.renderComponentTooltip(font, energyInfoArea.getTooltips(), pMouseX - x, pMouseY - y);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    private class ToggleButton extends ImageButton {
        private static final ResourceLocation OFF_TEX = new ResourceLocation("hbm_ntm:textures/screens/off.png");
        private static final ResourceLocation ON_TEX = new ResourceLocation("hbm_ntm:textures/screens/on.png");

        public ToggleButton(int x, int y, OnPress onPress) {
            super(x, y, 32, 18, 0, 0, 0, OFF_TEX, 32, 18, onPress);
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
            ResourceLocation currentTex = menu.isFelOn() ? ON_TEX : OFF_TEX;

            int vOffset = 0;
            if (!this.active) {
                vOffset += this.yDiffTex * 2;
            } else if (this.isHoveredOrFocused()) {
                vOffset += this.yDiffTex;
            }

            guiGraphics.blit(currentTex, this.getX(), this.getY(), (float)this.xTexStart, (float)vOffset, this.width, this.height, this.textureWidth, this.textureHeight);
        }
    }
}
