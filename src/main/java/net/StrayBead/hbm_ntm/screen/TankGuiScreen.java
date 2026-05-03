package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.ToggleOutputPacket;
import net.StrayBead.hbm_ntm.screen.renderer.FluidTankRenderer;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.HashMap;

public class TankGuiScreen extends AbstractContainerScreen<TankGuiMenu> {
    private final static HashMap<String, Object> guistate = TankGuiMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    ImageButton imagebutton_insertbattery;
    private FluidTankRenderer renderer;

    public TankGuiScreen(TankGuiMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/steel_barrel_gui.png");

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
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, 256, 256, 256, 256);

        renderer.render(guiGraphics, this.leftPos + 71, this.topPos + 17, menu.getFluidStack());

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
        guiGraphics.drawString(this.font, Component.literal("Tank"), 73, 4, -12829636, false);
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
        renderer = new FluidTankRenderer(256000, true, 34, 52);
    }

    private void renderFluidAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 71, 17, 34, 52)) {
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
