package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;

public class SteamChannelScreen extends AbstractContainerScreen<SteamChannelMenu> {
    private final static HashMap<String, Object> guistate = SteamChannelMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    ImageButton imagebutton_steam_1;

    public SteamChannelScreen(SteamChannelMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 183;
        this.imageHeight = 192;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/steam_channel_gui.png");

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

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/rod.png"), this.leftPos + 66, this.topPos + 31, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/rod.png"), this.leftPos + 66, this.topPos + 47, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/rod.png"), this.leftPos + 66, this.topPos + 63, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/rod.png"), this.leftPos + 82, this.topPos + 31, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/rod.png"), this.leftPos + 82, this.topPos + 47, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/rod.png"), this.leftPos + 82, this.topPos + 63, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/rod.png"), this.leftPos + 98, this.topPos + 31, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/rod.png"), this.leftPos + 98, this.topPos + 47, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/rod.png"), this.leftPos + 98, this.topPos + 63, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/boiler.png"), this.leftPos + 61, this.topPos + 17, 0, 0, 63, 83, 63, 83);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/fluid.png"), this.leftPos + 130, this.topPos + 17, 0, 0, 30, 83, 30, 83);

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
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.steam_channel_gui.label_rbmk_steam_channel"), 49, 4, -12829636, false);
    }

    @Override
    public void init() {
        super.init();
        imagebutton_steam_1 = new ImageButton(this.leftPos + 23, this.topPos + 17, 32, 82, 0, 0, 82, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_steam_1.png"), 32, 164, e -> {
        });
        guistate.put("button:imagebutton_steam_1", imagebutton_steam_1);
        this.addRenderableWidget(imagebutton_steam_1);
    }
}
