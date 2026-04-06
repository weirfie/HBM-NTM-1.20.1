package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.ButtonPressedPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class LeadAnvilScreen extends AbstractContainerScreen<LeadAnvilMenu> {
    private final static HashMap<String, Object> guistate = LeadAnvilMenu.guistate;
    EditBox search;
    ImageButton imagebutton_anvil;

    public LeadAnvilScreen(LeadAnvilMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 187;
        this.imageHeight = 221;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/lead_anvil.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        search.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/blue.png"), this.leftPos + 120, this.topPos + 87, 0, 0, 32, 32, 32, 32);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/blue.png"), this.leftPos + 152, this.topPos + 87, 0, 0, 32, 32, 32, 32);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/blue.png"), this.leftPos + 120, this.topPos + 55, 0, 0, 32, 32, 32, 32);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/blue.png"), this.leftPos + 152, this.topPos + 55, 0, 0, 32, 32, 32, 32);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/blue.png"), this.leftPos + 120, this.topPos + 23, 0, 0, 32, 32, 32, 32);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/blue.png"), this.leftPos + 152, this.topPos + 23, 0, 0, 32, 32, 32, 32);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/plus.png"), this.leftPos + 41, this.topPos + 28, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow.png"), this.leftPos + 76, this.topPos + 28, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/energy_area.png"), this.leftPos + 26, this.topPos + 74, 0, 0, 20, 20, 20, 20);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/energy_area.png"), this.leftPos + 46, this.topPos + 74, 0, 0, 20, 20, 20, 20);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/energy_area.png"), this.leftPos + 66, this.topPos + 74, 0, 0, 20, 20, 20, 20);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/energy_area.png"), this.leftPos + 86, this.topPos + 74, 0, 0, 20, 20, 20, 20);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/energy_area.png"), this.leftPos + 26, this.topPos + 94, 0, 0, 20, 20, 20, 20);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/energy_area.png"), this.leftPos + 46, this.topPos + 94, 0, 0, 20, 20, 20, 20);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/energy_area.png"), this.leftPos + 66, this.topPos + 94, 0, 0, 20, 20, 20, 20);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/energy_area.png"), this.leftPos + 86, this.topPos + 94, 0, 0, 20, 20, 20, 20);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrowleft.png"), this.leftPos + 10, this.topPos + 88, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrowright.png"), this.leftPos + 105, this.topPos + 88, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/search2.png"), this.leftPos + 6, this.topPos + 117, 0, 0, 16, 16, 16, 16);

        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        if (search.isFocused())
            return search.keyPressed(key, b, c);
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        search.tick();
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        String searchValue = search.getValue();
        super.resize(minecraft, width, height);
        search.setValue(searchValue);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.literal("Tier 1 Anvil"), 36, 9, -12829636, false);
    }

    @Override
    public void init() {
        super.init();
        search = new EditBox(this.font, this.leftPos + 25, this.topPos + 117, 118, 18, Component.translatable("gui.hbm_ntm.lead_anvil.search"));
        search.setMaxLength(32767);
        guistate.put("text:search", search);
        this.addWidget(this.search);
        imagebutton_anvil = new ImageButton(this.leftPos + 62, this.topPos + 54, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_anvil.png"), 16, 32, e -> {
            ModMessages.sendToServer(new ButtonPressedPacket(menu.blockEntity.getBlockPos()));
        });
        guistate.put("button:imagebutton_anvil", imagebutton_anvil);
        this.addRenderableWidget(imagebutton_anvil);
    }
}
