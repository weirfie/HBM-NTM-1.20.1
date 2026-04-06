package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.SetPressureC2SPacket;
import net.StrayBead.hbm_ntm.screen.renderer.EnergyInfoArea;
import net.StrayBead.hbm_ntm.screen.renderer.FluidTankRenderer;
import net.StrayBead.hbm_ntm.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;

public class CompressorScreen extends AbstractContainerScreen<CompressorMenu> {
    private final static HashMap<String, Object> guistate = CompressorMenu.guistate;
    private EnergyInfoArea energyInfoArea;
    private FluidTankRenderer renderer;
    private FluidTankRenderer outputRenderer;
    ImageButton imagebutton_button_filled;
    ImageButton imagebutton_button_unfilled;
    private ImageButton[] pressureButtons = new ImageButton[5];
    private int selectedIndex;

    public CompressorScreen(CompressorMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.imageWidth = 176;
        this.imageHeight = 177;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/compressor.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        if (this.selectedIndex != menu.getSelectedPressure()) {
            this.selectedIndex = menu.getSelectedPressure();
            updateButtons();
        }

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

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/template_slot.png"), this.leftPos + 25, this.topPos + 67, 0, 0, 18, 18, 18, 18);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 25, this.topPos + 48, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 25, this.topPos + 32, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 25, this.topPos + 16, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_line.png"), this.leftPos + 46, this.topPos + 24, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_line.png"), this.leftPos + 61, this.topPos + 24, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_line.png"), this.leftPos + 77, this.topPos + 24, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow_head.png"), this.leftPos + 93, this.topPos + 24, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/infinite_battery.png"), this.leftPos + 152, this.topPos + 69, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 152, this.topPos + 48, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 152, this.topPos + 32, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 152, this.topPos + 16, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_bottom.png"), this.leftPos + 119, this.topPos + 48, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 119, this.topPos + 32, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter_top.png"), this.leftPos + 119, this.topPos + 16, 0, 0, 16, 16, 16, 16);

        energyInfoArea.draw(guiGraphics);
        renderer.render(guiGraphics, x + 41, y + 6, menu.getFluidStack());

        outputRenderer.render(guiGraphics, x + 116, y + 20, menu.getOutputTank());

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
        guiGraphics.drawString(this.font, Component.literal("Compressor"), 61, 5, -12829636, false);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        renderSpecialOutputTooltip(guiGraphics, mouseX, mouseY, 41, 6, menu.getFluidStack());

        renderOutputTooltip(guiGraphics, mouseX, mouseY, x, y, 116, 20, menu.getOutputTank());
        renderButtonTooltips(guiGraphics, mouseX, mouseY);
    }

    @Override
    public void init() {
        super.init();

        assignEnergyInfoArea();
        assignFluidRenderer();
        outputRenderer = new FluidTankRenderer(40000, true, 14, 48);

        updateButtons();
    }

    private void updateButtons() {
        this.clearWidgets();
        this.selectedIndex = menu.getSelectedPressure();

        int baseX = this.leftPos + 58;
        int baseY = this.topPos + 41;

        for (int i = 0; i < 5; i++) {
            final int index = i;

            ResourceLocation buttonTexture = (i == selectedIndex)
                    ? new ResourceLocation("hbm_ntm:textures/screens/button_filled.png")
                    : new ResourceLocation("hbm_ntm:textures/screens/button_unfilled.png");

            ImageButton button = new ImageButton(
                    baseX + (i * 12),
                    baseY,
                    10,
                    18,
                    0, 0,
                    18,
                    buttonTexture,
                    18, 18,
                    btn -> {
                        this.selectedIndex = index;
                        ModMessages.sendToServer(new SetPressureC2SPacket(menu.getBlockEntity().getBlockPos(), index));
                        updateButtons();
                    }
            );

            this.addRenderableWidget(button);
        }
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

    private void renderButtonTooltips(GuiGraphics graphics, int mouseX, int mouseY) {
        int baseX = this.leftPos + 58;
        int baseY = this.topPos + 41;

        for (int i = 0; i < 5; i++) {
            int x = baseX + (i * 12);
            int y = baseY;

            if (MouseUtil.isMouseOver(mouseX, mouseY, x, y, 10, 18)) {
                graphics.renderTooltip(
                        this.font,
                        Component.literal(i + " PU → " + (i + 1) + " PU"),
                        mouseX,
                        mouseY
                );
            }
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

            int pressureValue = 0;

            if (!stack.isEmpty()) {
                if (stack.hasTag() && stack.getTag().contains("Pressure")) {
                    pressureValue = stack.getTag().getInt("Pressure");
                }
                else if (stack.getFluid().getFluidType() instanceof net.StrayBead.hbm_ntm.fluid.GenericFluidType customType) {
                    pressureValue = customType.getDefaultPressure();
                }
            }

            tooltip.add(Component.literal("Pressure: " + pressureValue + " PU")
                    .withStyle(net.minecraft.ChatFormatting.GOLD));

            if (pressureValue > 0) {
                int flashingColor = (System.currentTimeMillis() / 500) % 2 == 0 ? 0xFF5555 : 0xAA0000;
                tooltip.add(Component.literal("Pressurized, use compressor!")
                        .withStyle(net.minecraft.network.chat.Style.EMPTY.withColor(flashingColor)));
            }

            tooltip.add(Component.literal("[Flammable]").withStyle(net.minecraft.ChatFormatting.YELLOW));
            tooltip.add(Component.literal("Provides 25.0kTU per bucket").withStyle(net.minecraft.ChatFormatting.RED));
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
