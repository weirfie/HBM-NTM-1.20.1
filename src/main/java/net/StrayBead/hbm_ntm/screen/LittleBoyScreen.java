package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;

public class LittleBoyScreen extends AbstractContainerScreen<LittleBoyMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/little_boy_gui.png");

    protected int imageWidth = 176;
    protected int imageHeight = 219;

    public LittleBoyScreen(LittleBoyMenu menu, Inventory inventory, Component component) {
        super(menu, inventory, component);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, Component.literal("Little Boy"), 63, 1, -12829636, false);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int x = this.leftPos;
        int y = this.topPos;

        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos + -3, 0, 0, imageWidth, imageHeight, 256, 256);

        renderExplosiveLenses(pGuiGraphics, x, y);
        renderPlutoniumCoreButton(pGuiGraphics, x, y);
    }

    private void renderExplosiveLenses(GuiGraphics pGuiGraphics, int x, int y) {
        // Source coordinates for the red circle on your texture sheet
        int circleU = 177;
        int circleV = 16;
        int qSize = 22;

        int destX = x + 29;
        int destY = y + 84;

        // Slot 0: Top Left Quadrant
        if (hasItemInMachineSlot(0, ModItems.NEUTRON_SHIELDING.get())) {
            pGuiGraphics.blit(TEXTURE, destX, destY, circleU, circleV, qSize, qSize, 256, 256);
        }

        // Slot 1: Top Right Quadrant
        if (hasItemInMachineSlot(1, ModItems.SUBCRITICAL_TARGET.get())) {
            pGuiGraphics.blit(TEXTURE, destX - 2, destY + 2, circleU - 1, circleV + 22, qSize + 1, qSize - 4, 256, 256);
        }

        // Slot 3: Bottom Left Quadrant
        if (hasItemInMachineSlot(2, ModItems.U235_PROJECTILE.get())) {
            pGuiGraphics.blit(TEXTURE, destX + 45, destY + 6, circleU - 1, circleV + 40, qSize, qSize - 12, 256, 256);
        }

        // Slot 4: Bottom Right Quadrant
        if (hasItemInMachineSlot(3, ModItems.PROPELLANT.get())) {
            pGuiGraphics.blit(TEXTURE, destX + 63, destY + 8, circleU - 1, circleV + 50, qSize, qSize - 15, 256, 256);
        }
    }

    private void renderPlutoniumCoreButton(GuiGraphics pGuiGraphics, int x, int y) {
        if (hasItemInMachineSlot(4, ModItems.BOMB_IGNITER.get())) {
            int buttonU = 176;
            int buttonV = 75;

            pGuiGraphics.blit(TEXTURE, x + 107, y + 88, buttonU, buttonV, 16, 16, 256, 256);
            pGuiGraphics.blit(TEXTURE, x + 142, y + 87, buttonU, 0, 16, 16, 256, 256);
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
