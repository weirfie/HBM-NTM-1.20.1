package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.ToggleOutputPacket;
import net.StrayBead.hbm_ntm.screen.renderer.EnergyInfoArea;
import net.StrayBead.hbm_ntm.screen.renderer.FluidTankRenderer;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;

public class SparkEnergyBatteryScreen  extends AbstractContainerScreen<SparkEnergyBatteryMenu> {
    private final static HashMap<String, Object> guistate = SparkEnergyBatteryMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    EditBox energy;
    private EnergyInfoArea energyInfoArea;
    ImageButton imagebutton_insertbattery;
    ImageButton imagebutton_output;
    private static final ResourceLocation INPUT_BATTERY_TEX = new ResourceLocation("hbm_ntm:textures/screens/insertbattery.png");
    private static final ResourceLocation OUTPUT_BATTERY_TEX = new ResourceLocation("hbm_ntm:textures/screens/output.png");

    public SparkEnergyBatteryScreen(SparkEnergyBatteryMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 177;
        this.imageHeight = 188;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/spark_energy_battery_gui.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        energy.render(guiGraphics, mouseX, mouseY, partialTicks);
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

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow.png"), this.leftPos + 43, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrowpointer.png"), this.leftPos + 44, this.topPos + 77, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/torchinfo.png"), this.leftPos + 147, this.topPos + 78, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/info.png"), this.leftPos + 147, this.topPos + 42, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/energy_information_area.png"), this.leftPos + 61, this.topPos + 38, 0, 0, 65, 63, 65, 63);

        energyInfoArea.draw(guiGraphics);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        if (energy.isFocused())
            return energy.keyPressed(key, b, c);
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        energy.tick();
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        String energyValue = energy.getValue();
        super.resize(minecraft, width, height);
        energy.setValue(energyValue);
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyInfoArea(
                this.leftPos + 63,
                this.topPos + 40,
                menu.blockEntity.getEnergyStorage(),
                61,
                60
        );
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(pGuiGraphics, pMouseX, pMouseY, this.leftPos, this.topPos);
    }

    private void renderEnergyAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 61, 38, 65, 63)) {
            pGuiGraphics.renderComponentTooltip(font, energyInfoArea.getTooltips(), pMouseX - x, pMouseY - y);
        }
    }

    @Override
    public void init() {
        super.init();
        assignEnergyInfoArea();
        energy = new EditBox(this.font, this.leftPos + 29, this.topPos + 17, 118, 18, Component.translatable("gui.hbm_ntm.spark_energy_battery_gui.energy")) {
            @Override
            public void insertText(String text) {
                super.insertText(text);
                if (getValue().isEmpty())
                    setSuggestion(Component.literal("Energy").getString());
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion(Component.literal("Energy").getString());
                else
                    setSuggestion(null);
            }
        };
        energy.setSuggestion(Component.literal("Energy").getString());
        energy.setMaxLength(32767);
        guistate.put("text:energy", energy);
        this.addWidget(this.energy);
        imagebutton_insertbattery = new ImageButton(this.leftPos + 130, this.topPos + 41, 16, 16, 0, 0, 16,
                INPUT_BATTERY_TEX, 16, 16, e -> {
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
                new ResourceLocation("hbm_ntm:textures/screens/output.png"), 16, 16, e -> {
        });
        this.addRenderableWidget(imagebutton_output);
        guistate.put("button:imagebutton_output", imagebutton_output);
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
