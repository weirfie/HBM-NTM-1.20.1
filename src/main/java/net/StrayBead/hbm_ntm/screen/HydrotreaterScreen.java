package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.StrayBead.hbm_ntm.screen.renderer.EnergyInfoArea;
import net.StrayBead.hbm_ntm.screen.renderer.FluidTankRenderer;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;

public class HydrotreaterScreen extends AbstractContainerScreen<HydrotreaterMenu> {
    private final static HashMap<String, Object> guistate = HydrotreaterMenu.guistate;
    private EnergyInfoArea energyInfoArea;
    private FluidTankRenderer renderer;
    private FluidTankRenderer outputRenderer;

    public HydrotreaterScreen(HydrotreaterMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 176;
        this.imageHeight = 214;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/gui_hydrotreater.png");

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
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(texture, this.leftPos, this.topPos - 1, 0, 0, 256, 256, 256, 256);
        energyInfoArea.draw(guiGraphics);
        renderer.render(guiGraphics, x + 35, y + 8, menu.getFluidStack());
        renderer.render(guiGraphics, x + 53, y + 8, menu.getInputStack());

        outputRenderer.render(guiGraphics, x + 143, y + 20, menu.getTank1());
        outputRenderer.render(guiGraphics, x + 2000, y + 15, menu.getTank2());

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
        guiGraphics.drawString(this.font, Component.literal("Hydrotreater"), 57, 2, -1, false);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(guiGraphics, mouseX, mouseY, x, y);
        renderSpecialOutputTooltip(guiGraphics, mouseX, mouseY, 35, 8, menu.getFluidStack());
        renderSpecialOutputTooltip(guiGraphics, mouseX, mouseY, 53, 8, menu.getInputStack());

        renderOutputTooltip(guiGraphics, mouseX, mouseY, x, y, 143, 20, menu.getTank1());
        renderOutputTooltip(guiGraphics, mouseX, mouseY, x, y, 127, 26, menu.getTank2());
    }

    @Override
    public void init() {
        super.init();
        assignEnergyInfoArea();
        assignFluidRenderer();
        outputRenderer = new FluidTankRenderer(40000, true, 14, 48);
    }

    private void assignFluidRenderer() {
        renderer = new FluidTankRenderer(640000, true, 16, 61);
    }

    private void assignEnergyInfoArea() {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        energyInfoArea = new EnergyInfoArea(x + 17, y + 23, menu.blockEntity.getEnergyStorage(), 16, 46);
    }

    private void renderOutputTooltip(GuiGraphics graphics, int mx, int my, int x, int y, int offsetX, int offsetY, FluidStack stack) {
        if(isMouseAboveArea(mx, my, x, y, offsetX, offsetY, 16, 64)) {
            graphics.renderComponentTooltip(font, outputRenderer.getTooltip(stack, TooltipFlag.Default.NORMAL), mx - x, my - y);
        }
    }

    private void renderFluidAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 55, 15, 36, 61)) {

            pGuiGraphics.renderComponentTooltip(font,
                    renderer.getTooltip(menu.getFluidStack(), TooltipFlag.Default.NORMAL),
                    pMouseX - x, pMouseY - y);
        }
    }

    private void renderSpecialOutputTooltip(GuiGraphics graphics, int mx, int my, int offsetX, int offsetY, FluidStack stack) {
        if(isMouseAboveArea(mx, my, this.leftPos, this.topPos, offsetX, offsetY, 16, 48)) {
            java.util.List<Component> tooltip = new java.util.ArrayList<>(outputRenderer.getTooltip(stack, TooltipFlag.Default.NORMAL));

            tooltip.add(Component.literal("[Flammable]").withStyle(ChatFormatting.YELLOW));

            net.minecraft.network.chat.MutableComponent bucketLine = Component.literal("Provides ").withStyle(ChatFormatting.YELLOW)
                    .append(Component.literal("110.0kTU ").withStyle(ChatFormatting.RED))
                    .append(Component.literal("per bucket").withStyle(ChatFormatting.YELLOW));
            tooltip.add(bucketLine);

            net.minecraft.network.chat.MutableComponent fuelLine = Component.literal("Provides ").withStyle(ChatFormatting.YELLOW)
                    .append(Component.literal("165.0kHE ").withStyle(ChatFormatting.RED))
                    .append(Component.literal("per bucket").withStyle(ChatFormatting.YELLOW));
            tooltip.add(fuelLine);

            tooltip.add(Component.literal("[Polluting]").withStyle(net.minecraft.network.chat.Style.EMPTY.withColor(0xFFAA00)));

            graphics.renderComponentTooltip(font, tooltip, mx - this.leftPos, my - this.topPos);
        }
    }

    private void renderEnergyAreaTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 26, 12, 14, 55)) {
            pGuiGraphics.renderComponentTooltip(font, energyInfoArea.getTooltips(), pMouseX - x, pMouseY - y);
        }
    }

    private boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
