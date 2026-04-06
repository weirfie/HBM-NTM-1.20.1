package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class DesignatorGuiScreen extends AbstractContainerScreen<DesignatorGuiMenu> {
    private final static HashMap<String, Object> guistate = DesignatorGuiMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    EditBox power;
    EditBox particles;
    Checkbox is_incendiary;
    Button button_ok;
    Button button_enter;

    public DesignatorGuiScreen(DesignatorGuiMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/designator_gui.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        power.render(guiGraphics, mouseX, mouseY, partialTicks);
        particles.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        if (power.isFocused())
            return power.keyPressed(key, b, c);
        if (particles.isFocused())
            return particles.keyPressed(key, b, c);
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        power.tick();
        particles.tick();
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        String powerValue = power.getValue();
        String particlesValue = particles.getValue();
        super.resize(minecraft, width, height);
        power.setValue(powerValue);
        particles.setValue(particlesValue);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.designator_gui.label_power"), 71, 7, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.designator_gui.label_num_particles"), 53, 98, -12829636, false);
    }

    @Override
    public void init() {
        super.init();
        power = new EditBox(this.font, this.leftPos + 26, this.topPos + 21, 118, 18, Component.translatable("gui.hbm_ntm.designator_gui.power")) {
            @Override
            public void insertText(String text) {
                super.insertText(text);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.designator_gui.power").getString());
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.designator_gui.power").getString());
                else
                    setSuggestion(null);
            }
        };
        power.setSuggestion(Component.translatable("gui.hbm_ntm.designator_gui.power").getString());
        power.setMaxLength(32767);
        guistate.put("text:power", power);
        this.addWidget(this.power);
        particles = new EditBox(this.font, this.leftPos + 26, this.topPos + 112, 118, 18, Component.translatable("gui.hbm_ntm.designator_gui.particles")) {
            @Override
            public void insertText(String text) {
                super.insertText(text);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.designator_gui.particles").getString());
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.designator_gui.particles").getString());
                else
                    setSuggestion(null);
            }
        };
        particles.setSuggestion(Component.translatable("gui.hbm_ntm.designator_gui.particles").getString());
        particles.setMaxLength(32767);
        guistate.put("text:particles", particles);
        this.addWidget(this.particles);
        button_ok = Button.builder(Component.translatable("gui.hbm_ntm.designator_gui.button_ok"), e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new DesignatorGuiButtonMessage(0, x, y, z));
                DesignatorGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
            }
        }).bounds(this.leftPos + 67, this.topPos + 41, 35, 20).build();
        guistate.put("button:button_ok", button_ok);
        this.addRenderableWidget(button_ok);
        button_enter = Button.builder(Component.translatable("gui.hbm_ntm.designator_gui.button_enter"), e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new DesignatorGuiButtonMessage(1, x, y, z));
                DesignatorGuiButtonMessage.handleButtonAction(entity, 1, x, y, z);
            }
        }).bounds(this.leftPos + 58, this.topPos + 134, 51, 20).build();
        guistate.put("button:button_enter", button_enter);
        this.addRenderableWidget(button_enter);
        is_incendiary = new Checkbox(this.leftPos + 44, this.topPos + 66, 20, 20, Component.translatable("gui.hbm_ntm.designator_gui.is_incendiary"), false);
        guistate.put("checkbox:is_incendiary", is_incendiary);
        this.addRenderableWidget(is_incendiary);
    }
}
