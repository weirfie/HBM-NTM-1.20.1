package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.ToggleOutputPacket;
import net.StrayBead.hbm_ntm.screen.renderer.EnergyInfoArea;
import net.StrayBead.hbm_ntm.screen.renderer.FluidTankRenderer;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.core.BlockPos;
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

public class BigAssTankGuiScreen extends AbstractContainerScreen<BigAssTankGuiMenu> {
    private final static HashMap<String, Object> guistate = BigAssTankGuiMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    ImageButton imagebutton_insertbattery;
    private FluidTankRenderer renderer;

    public BigAssTankGuiScreen(BigAssTankGuiMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/big_ass_tank_gui.png");

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
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/down.png"), this.leftPos + 8, this.topPos + 37, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/template.png"), this.leftPos + 8, this.topPos + 18, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/template.png"), this.leftPos + 8, this.topPos + 61, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/down.png"), this.leftPos + 37, this.topPos + 37, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/large_tank_meter_area.png"), this.leftPos + 72, this.topPos + 16, 0, 0, 42, 64, 42, 64);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow.png"), this.leftPos + 55, this.topPos + 18, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow.png"), this.leftPos + 115, this.topPos + 17, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/down.png"), this.leftPos + 133, this.topPos + 37, 0, 0, 16, 16, 16, 16);

        renderer.render(guiGraphics, this.leftPos + 72, this.topPos + 18, menu.getFluidStack());

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
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.drawString(this.font, Component.literal("Big-Ass Tank 9000"), 47, 4, -13421773, false);
        renderFluidAreaTooltips(guiGraphics, mouseX, mouseY, x, y);
    }

    @Override
    public void init() {
        super.init();
        imagebutton_insertbattery = new ToggleButton(this.leftPos + 155, this.topPos + 39, e -> {
            ModMessages.sendToServer(new ToggleOutputPacket(new BlockPos(x, y, z)));
        });
        guistate.put("button:insertbattery", imagebutton_insertbattery);
        this.addRenderableWidget(imagebutton_insertbattery);
        assignFluidRenderer();
    }

    private void assignFluidRenderer() {
        renderer = new FluidTankRenderer(100000, true, 40, 60);
    }

    private void renderFluidAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 72, 18, 40, 60)) {
            pGuiGraphics.renderComponentTooltip(font,
                    renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    pMouseX - x, pMouseY - y);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    private class ToggleButton extends ImageButton {
        private static final ResourceLocation INPUT_TEX = new ResourceLocation("hbm_ntm:textures/screens/insertbattery.png");
        private static final ResourceLocation OUTPUT_TEX = new ResourceLocation("hbm_ntm:textures/screens/output.png");

        public ToggleButton(int x, int y, OnPress onPress) {
            super(x, y, 16, 16, 0, 0, 16, INPUT_TEX, 16, 16, onPress);
        }

        @Override
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
            ResourceLocation currentTex = menu.getBlockEntity().isOutputting() ? OUTPUT_TEX : INPUT_TEX;

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
