package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;

public class BurnerPressGuiScreen extends AbstractContainerScreen<BurnerPressGuiMenu> {
    private final static HashMap<String, Object> guistate = BurnerPressGuiMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    Button button_press;

    public BurnerPressGuiScreen(BurnerPressGuiMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/burner_press_gui.png");

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

        ResourceLocation emptyPress = new ResourceLocation("hbm_ntm:textures/screens/press.png");
        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/press.png"),
                this.leftPos + 80, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        int progressHeight = this.menu.getScaledProgress();
        if (progressHeight > 0) {
            guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/filled_press.png"),
                    this.leftPos + 80, this.topPos + 41,
                    0, 0,
                    16, progressHeight,
                    16, 16
            );
        }

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow.png"), this.leftPos + 111, this.topPos + 43, 0, 0, 16, 16, 16, 16);

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
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.burner_press_gui.label_burner_press"), 60, 5, -12829636, false);
    }

    @Override
    public void init() {
        super.init();
        button_press = Button.builder(Component.translatable("gui.hbm_ntm.burner_press_gui.button_press"), e -> {
        }).bounds(this.leftPos + 9, this.topPos + 35, 51, 20).build();
        guistate.put("button:button_press", button_press);
        this.addRenderableWidget(button_press);
    }
}
