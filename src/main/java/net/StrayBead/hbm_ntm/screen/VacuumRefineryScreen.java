package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.screen.renderer.EnergyInfoArea;
import net.StrayBead.hbm_ntm.screen.renderer.FluidTankRenderer;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.fluids.FluidStack;

public class VacuumRefineryScreen extends AbstractContainerScreen<VacuumRefineryMenu> {
    private final static HashMap<String, Object> guistate = VacuumRefineryMenu.guistate;
    private EnergyInfoArea energyInfoArea;
    private FluidTankRenderer renderer;
    private FluidTankRenderer outputRenderer;

    public VacuumRefineryScreen(VacuumRefineryMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 174;
        this.imageHeight = 207;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/vacuum_refinery.png");

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

        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 25, this.topPos + 52, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 25, this.topPos + 36, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 25, this.topPos + 20, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meterstickup.png"), this.leftPos + 25, this.topPos + 67, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/infinite_battery.png"), this.leftPos + 25, this.topPos + 83, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/template_slot.png"), this.leftPos + 24, this.topPos + 101, 0, 0, 18, 18, 18, 18);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 41, this.topPos + 52, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 41, this.topPos + 36, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 41, this.topPos + 20, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 80, this.topPos + 52, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 80, this.topPos + 36, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 80, this.topPos + 20, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 98, this.topPos + 20, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 98, this.topPos + 36, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 98, this.topPos + 52, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 116, this.topPos + 36, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 116, this.topPos + 52, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 116, this.topPos + 20, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 134, this.topPos + 52, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 134, this.topPos + 36, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 134, this.topPos + 20, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_down.png"), this.leftPos + 80, this.topPos + 70, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_down.png"), this.leftPos + 98, this.topPos + 70, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_down.png"), this.leftPos + 116, this.topPos + 70, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_down.png"), this.leftPos + 134, this.topPos + 70, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/full_bucket_gui.png"), this.leftPos + 80, this.topPos + 88, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/full_bucket_gui.png"), this.leftPos + 98, this.topPos + 88, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/full_bucket_gui.png"), this.leftPos + 116, this.topPos + 88, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/full_bucket_gui.png"), this.leftPos + 134, this.topPos + 88, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bucket_gui.png"), this.leftPos + 80, this.topPos + 106, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bucket_gui.png"), this.leftPos + 98, this.topPos + 106, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bucket_gui.png"), this.leftPos + 116, this.topPos + 106, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bucket_gui.png"), this.leftPos + 134, this.topPos + 106, 0, 0, 16, 16, 16, 16);

        energyInfoArea.draw(guiGraphics);
        renderer.render(guiGraphics, x + 41, y + 6, menu.getFluidStack());

        outputRenderer.render(guiGraphics, x + 80, y + 20, menu.getVacuumHeavyOilStack());
        outputRenderer.render(guiGraphics, x + 98, y + 20, menu.getReformateStack());
        outputRenderer.render(guiGraphics, x + 116, y + 20, menu.getVacuumLightOilStack());
        outputRenderer.render(guiGraphics, x + 134, y + 20, menu.getSourGasStack());

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
        guiGraphics.drawString(this.font, Component.literal("Vacuum Refinery"), 47, 5, -12829636, false);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(guiGraphics, mouseX, mouseY, x, y);
        renderSpecialOutputTooltip(guiGraphics, mouseX, mouseY, 41, 6, menu.getFluidStack());

        renderOutputTooltip(guiGraphics, mouseX, mouseY, x, y, 80, 20, menu.getVacuumHeavyOilStack());
        renderOutputTooltip(guiGraphics, mouseX, mouseY, x, y, 98, 20, menu.getReformateStack());
        renderOutputTooltip(guiGraphics, mouseX, mouseY, x, y, 116, 20, menu.getVacuumLightOilStack());
        renderOutputTooltip(guiGraphics, mouseX, mouseY, x, y, 134, 20, menu.getSourGasStack());
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

        energyInfoArea = new EnergyInfoArea(x + 26, y + 12, menu.blockEntity.getEnergyStorage(), 14, 55);
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

            tooltip.add(Component.literal("Pressure: 2 PU").withStyle(net.minecraft.ChatFormatting.RED));

            int flashingColor = (System.currentTimeMillis() / 500) % 2 == 0 ? 0xFF5555 : 0xAA0000;
            tooltip.add(Component.literal("Pressurized, use compressor!").withStyle(net.minecraft.network.chat.Style.EMPTY.withColor(flashingColor)));

            tooltip.add(Component.literal("[Flammable]").withStyle(net.minecraft.ChatFormatting.YELLOW));

            net.minecraft.network.chat.MutableComponent fuelLine = Component.literal("Provides ").withStyle(net.minecraft.ChatFormatting.YELLOW)
                    .append(Component.literal("25.0kTU ").withStyle(net.minecraft.ChatFormatting.RED))
                    .append(Component.literal("per bucket").withStyle(net.minecraft.ChatFormatting.YELLOW));
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
