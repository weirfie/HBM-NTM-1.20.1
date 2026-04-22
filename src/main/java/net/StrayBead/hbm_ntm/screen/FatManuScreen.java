package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.screen.renderer.EnergyInfoArea;
import net.StrayBead.hbm_ntm.screen.renderer.FluidTankRenderer;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;

public class FatManuScreen extends AbstractContainerScreen<FatManMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/fat_man_gui.png");

    protected int imageWidth = 176;
    protected int imageHeight = 166;

    public FatManuScreen(FatManMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, Component.literal("Fat Man"), 70, 3, -12829636, false);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = this.leftPos;
        int y = this.topPos;

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        renderExplosiveLenses(pGuiGraphics, x, y);
        renderPlutoniumCoreButton(pGuiGraphics, x, y);
    }

    private void renderExplosiveLenses(GuiGraphics pGuiGraphics, int x, int y) {
        // Source coordinates for the red circle on your texture sheet
        int circleU = 177;
        int circleV = -3;
        int qSize = 25;

        int destX = x + 83;
        int destY = y + 16;

        // Slot 0: Top Left Quadrant
        if (hasItemInMachineSlot(0, ModItems.HIGH_EXPLOSIVE_LENSES.get())) {
            pGuiGraphics.blit(TEXTURE, destX, destY, circleU, circleV, qSize, qSize, 256, 256);
        }

        // Slot 1: Top Right Quadrant
        if (hasItemInMachineSlot(1, ModItems.HIGH_EXPLOSIVE_LENSES.get())) {
            pGuiGraphics.blit(TEXTURE, destX + qSize, destY, circleU + qSize, circleV, qSize, qSize, 256, 256);
        }

        // Slot 3: Bottom Left Quadrant
        if (hasItemInMachineSlot(3, ModItems.HIGH_EXPLOSIVE_LENSES.get())) {
            pGuiGraphics.blit(TEXTURE, destX, destY + qSize, circleU, circleV + qSize, qSize, qSize, 256, 256);
        }

        // Slot 4: Bottom Right Quadrant
        if (hasItemInMachineSlot(4, ModItems.HIGH_EXPLOSIVE_LENSES.get())) {
            pGuiGraphics.blit(TEXTURE, destX + qSize, destY + qSize, circleU + qSize, circleV + qSize, qSize, qSize, 256, 256);
        }
    }

    private void renderPlutoniumCoreButton(GuiGraphics pGuiGraphics, int x, int y) {
        if (hasItemInMachineSlot(5, ModItems.PLUTONIUM_CORE.get())) {
            int buttonU = 177;
            int buttonV = 48;

            pGuiGraphics.blit(TEXTURE, x + 135, y + 35, buttonU, buttonV, 16, 16, 256, 256);
        }
    }

    private boolean hasItemInMachineSlot(int localSlotIndex, Item itemToCheck) {
        int menuIndex = localSlotIndex + 36;
        if (menuIndex >= this.menu.slots.size()) return false;

        return this.menu.getSlot(menuIndex).getItem().getItem() == itemToCheck;
    }

    @Override
    public void render(GuiGraphics pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }
}
