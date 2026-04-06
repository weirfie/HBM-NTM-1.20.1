package net.StrayBead.hbm_ntm.screen;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class CrucibleScreen extends AbstractContainerScreen<CrucibleMenu> {
    private final static HashMap<String, Object> guistate = CrucibleMenu.guistate;

    public CrucibleScreen(CrucibleMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 176;
        this.imageHeight = 185;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/crucible.png");

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

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 7, this.topPos + 9, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 23, this.topPos + 9, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 39, this.topPos + 9, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 55, this.topPos + 9, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 71, this.topPos + 9, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 87, this.topPos + 9, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 103, this.topPos + 9, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 119, this.topPos + 9, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 135, this.topPos + 9, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 151, this.topPos + 9, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 7, this.topPos + 25, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 23, this.topPos + 25, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 39, this.topPos + 25, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 55, this.topPos + 25, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 71, this.topPos + 25, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 87, this.topPos + 25, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 103, this.topPos + 25, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 119, this.topPos + 25, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 135, this.topPos + 25, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 151, this.topPos + 25, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 7, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 7, this.topPos + 57, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 7, this.topPos + 73, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 23, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 39, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 55, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 23, this.topPos + 57, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 71, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 87, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 103, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 119, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 135, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 151, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 39, this.topPos + 57, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 55, this.topPos + 57, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 23, this.topPos + 73, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 71, this.topPos + 57, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 87, this.topPos + 57, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 103, this.topPos + 57, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 119, this.topPos + 57, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 135, this.topPos + 57, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 151, this.topPos + 57, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 39, this.topPos + 73, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 55, this.topPos + 73, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 71, this.topPos + 73, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 87, this.topPos + 73, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 103, this.topPos + 73, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 119, this.topPos + 73, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 135, this.topPos + 73, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bricks.png"), this.leftPos + 151, this.topPos + 73, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/brick_info_area_top_left.png"), this.leftPos + 18, this.topPos + 21, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/brick_info_area_top_right.png"), this.leftPos + 34, this.topPos + 21, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/brick_info_area_middle_left.png"), this.leftPos + 18, this.topPos + 37, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/brick_info_area_middle_right.png"), this.leftPos + 34, this.topPos + 37, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/brick_info_area_bottom_right.png"), this.leftPos + 34, this.topPos + 69, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/brick_info_area_middle_right.png"), this.leftPos + 34, this.topPos + 53, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/brick_info_area_middle_left.png"), this.leftPos + 18, this.topPos + 53, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/brick_info_area_bottom_left.png"), this.leftPos + 18, this.topPos + 69, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/red_brick_area_top_left.png"), this.leftPos + 59, this.topPos + 21, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/red_brick_area_top_right.png"), this.leftPos + 75, this.topPos + 21, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/red_brick_area_middle_left.png"), this.leftPos + 59, this.topPos + 37, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/red_brick_area_middle_right.png"), this.leftPos + 75, this.topPos + 37, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/red_brick_area_middle_left.png"), this.leftPos + 59, this.topPos + 53, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/red_brick_area_middle_right.png"), this.leftPos + 75, this.topPos + 53, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/red_brick_area_bottom_left.png"), this.leftPos + 59, this.topPos + 69, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/red_brick_area_bottom_right.png"), this.leftPos + 75, this.topPos + 69, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/assembly_template_base.png"), this.leftPos + 107, this.topPos + 73, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrowleft.png"), this.leftPos + 91, this.topPos + 21, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrowright.png"), this.leftPos + 93, this.topPos + 39, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/crucible_slot.png"), this.leftPos + 110, this.topPos + 21, 0, 0, 18, 18, 18, 18);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/crucible_slot.png"), this.leftPos + 128, this.topPos + 21, 0, 0, 18, 18, 18, 18);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/crucible_slot.png"), this.leftPos + 146, this.topPos + 21, 0, 0, 18, 18, 18, 18);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/crucible_slot.png"), this.leftPos + 110, this.topPos + 39, 0, 0, 18, 18, 18, 18);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/crucible_slot.png"), this.leftPos + 128, this.topPos + 39, 0, 0, 18, 18, 18, 18);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/crucible_slot.png"), this.leftPos + 146, this.topPos + 39, 0, 0, 18, 18, 18, 18);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/crucible_slot.png"), this.leftPos + 110, this.topPos + 57, 0, 0, 18, 18, 18, 18);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/crucible_slot.png"), this.leftPos + 128, this.topPos + 57, 0, 0, 18, 18, 18, 18);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/crucible_slot.png"), this.leftPos + 146, this.topPos + 57, 0, 0, 18, 18, 18, 18);

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
        guiGraphics.drawString(this.font, Component.literal("Crucible"), 67, 9, -1, false);
    }

    @Override
    public void init() {
        super.init();
    }
}
