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

public class CatalyticReformerScreen extends AbstractContainerScreen<CatalyticReformerMenu> {
    private final static HashMap<String, Object> guistate = CatalyticReformerMenu.guistate;
    private EnergyInfoArea energyInfoArea;
    private FluidTankRenderer renderer;
    private FluidTankRenderer outputRenderer;

    public CatalyticReformerScreen(CatalyticReformerMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 176;
        this.imageHeight = 214;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/catalytic_reformer.png");

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

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 26, this.topPos + 59, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 26, this.topPos + 43, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 26, this.topPos + 27, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meterstickup.png"), this.leftPos + 26, this.topPos + 74, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/infinite_battery.png"), this.leftPos + 26, this.topPos + 90, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/template_slot.png"), this.leftPos + 25, this.topPos + 107, 0, 0, 18, 18, 18, 18);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 44, this.topPos + 59, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 44, this.topPos + 43, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 44, this.topPos + 27, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_up.png"), this.leftPos + 44, this.topPos + 74, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/full_bucket_gui.png"), this.leftPos + 44, this.topPos + 108, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/bucket_gui.png"), this.leftPos + 44, this.topPos + 90, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 108, this.topPos + 59, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 108, this.topPos + 43, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 108, this.topPos + 27, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_down.png"), this.leftPos + 108, this.topPos + 75, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_down.png"), this.leftPos + 125, this.topPos + 75, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_down.png"), this.leftPos + 144, this.topPos + 75, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 126, this.topPos + 59, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 126, this.topPos + 43, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 126, this.topPos + 27, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 144, this.topPos + 59, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 144, this.topPos + 43, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 144, this.topPos + 27, 0, 0, 16, 16, 16, 16);

        energyInfoArea.draw(guiGraphics);
        renderer.render(guiGraphics, x + 44, y + 13, menu.getFluidStack());

        outputRenderer.render(guiGraphics, x + 109, y + 26, menu.getTank1());
        outputRenderer.render(guiGraphics, x + 127, y + 26, menu.getTank2());
        outputRenderer.render(guiGraphics, x + 145, y + 26, menu.getTank3());

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
        guiGraphics.drawString(this.font, Component.literal("Catalytic Reformer"), 42, 9, -12829636, false);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltips(guiGraphics, mouseX, mouseY, x, y);
        renderSpecialOutputTooltip(guiGraphics, mouseX, mouseY, 44, 13, menu.getFluidStack());

        renderOutputTooltip(guiGraphics, mouseX, mouseY, x, y, 109, 26, menu.getTank1());
        renderOutputTooltip(guiGraphics, mouseX, mouseY, x, y, 127, 26, menu.getTank2());
        renderOutputTooltip(guiGraphics, mouseX, mouseY, x, y, 145, 26, menu.getTank3());
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

        energyInfoArea = new EnergyInfoArea(x + 27, y + 28, menu.blockEntity.getEnergyStorage(), 14, 46);
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

            tooltip.add(Component.literal("[Flammable]").withStyle(net.minecraft.ChatFormatting.YELLOW));

            net.minecraft.network.chat.MutableComponent bucketLine = Component.literal("Provides ").withStyle(net.minecraft.ChatFormatting.YELLOW)
                    .append(Component.literal("110.0kTU ").withStyle(net.minecraft.ChatFormatting.RED))
                    .append(Component.literal("per bucket").withStyle(net.minecraft.ChatFormatting.YELLOW));
            tooltip.add(bucketLine);

            tooltip.add(Component.literal("[Combustible]").withStyle(ChatFormatting.GOLD));

            net.minecraft.network.chat.MutableComponent fuelLine = Component.literal("Provides ").withStyle(net.minecraft.ChatFormatting.YELLOW)
                    .append(Component.literal("165.0kHE ").withStyle(net.minecraft.ChatFormatting.RED))
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
