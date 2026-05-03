package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.ToggleOutputPacket;
import net.StrayBead.hbm_ntm.screen.renderer.EnergyInfoArea;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.HashMap;

public class BatterySocketScreen extends AbstractContainerScreen<BatterySocketMenu> {
    private final static HashMap<String, Object> guistate = SparkEnergyBatteryMenu.guistate;
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/battery_socket_gui.png");
    private EnergyInfoArea energyInfoArea;
    private final int x, y, z;
    ImageButton imagebutton_insertbattery;
    ImageButton imagebutton_output;
    private static final ResourceLocation INPUT_BATTERY_TEX = new ResourceLocation("hbm_ntm:textures/screens/insertbattery.png");
    private static final ResourceLocation OUTPUT_BATTERY_TEX = new ResourceLocation("hbm_ntm:textures/screens/output.png");

    public BatterySocketScreen(BatterySocketMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
        this.imageWidth = 175;
        this.imageHeight = 94;
        this.x = menu.x;
        this.y = menu.y;
        this.z = menu.z;
    }

    @Override
    protected void init() {
        super.init();
        assignEnergyInfoArea();

        imagebutton_insertbattery = new ImageButton(this.leftPos + 130, this.topPos + 41, 16, 16, 0, 0, 16,
                INPUT_BATTERY_TEX, 16, 32, e -> {
            ModMessages.sendToServer(new ToggleOutputPacket(new BlockPos(this.x, this.y, this.z)));
        }) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
                ResourceLocation currentTexture = (menu.getData().get(2) == 1) ? OUTPUT_BATTERY_TEX : INPUT_BATTERY_TEX;

                int vOffset = this.isHoveredOrFocused() ? 16 : 0;

                guiGraphics.blit(currentTexture, this.getX(), this.getY(), 0, vOffset, 16, 16, 16, 32);
            }
        };
        this.addRenderableWidget(imagebutton_insertbattery);
        guistate.put("button:imagebutton_insertbattery", imagebutton_insertbattery);

        imagebutton_output = new ImageButton(this.leftPos + 130, this.topPos + 78, 16, 16, 0, 0, 16,
                new ResourceLocation("hbm_ntm:textures/screens/output.png"), 16, 32, e -> {
        });
        this.addRenderableWidget(imagebutton_output);
        guistate.put("button:imagebutton_output", imagebutton_output);
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        energyInfoArea = new EnergyInfoArea(x + 56, y, menu.blockEntity.getEnergyStorage(), 37, 53);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(pGuiGraphics, pMouseX, pMouseY, x, y);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int pMouseX, int pMouseY) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(TEXTURE, this.leftPos, this.topPos + 70, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/battery_socket_interface.png"), this.leftPos + 10, this.topPos - 25, 0, 0, 150, 88, 150, 88);

        energyInfoArea.draw(guiGraphics);

        RenderSystem.disableBlend();
    }

    private void renderEnergyAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 56, 0, 37, 53)) {
            pGuiGraphics.renderComponentTooltip(font, energyInfoArea.getTooltips(), pMouseX - x, pMouseY - y);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
