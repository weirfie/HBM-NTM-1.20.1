package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class PWRControllerGuiScreen extends AbstractContainerScreen<PWRControllerGuiMenu> {
    private final static HashMap<String, Object> guistate = PWRControllerGuiMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    EditBox control_rod_level;
    Button button_save;

    public PWRControllerGuiScreen(PWRControllerGuiMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/pwr_controller_gui.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        control_rod_level.render(guiGraphics, mouseX, mouseY, partialTicks);

        if (mouseX >= this.leftPos + 150 && mouseX <= this.leftPos + 150 + 16 &&
                mouseY >= this.topPos + 24 && mouseY <= this.topPos + 24 + 16) {

            guiGraphics.renderTooltip(this.font, Component.literal(getAverageFuelRodEnergy()), mouseX, mouseY);
        } else {
            this.renderTooltip(guiGraphics, mouseX, mouseY);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 7, this.topPos + 44, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 7, this.topPos + 28, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 7, this.topPos + 12, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/template.png"), this.leftPos + 8, this.topPos + 63, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 25, this.topPos + 44, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 25, this.topPos + 28, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 25, this.topPos + 12, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/arrow.png"), this.leftPos + 73, this.topPos + 13, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/torchinfo.png"), this.leftPos + 121, this.topPos + 24, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/fuel.png"), this.leftPos + 150, this.topPos + 24, 0, 0, 16, 16, 16, 16);

        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        if (control_rod_level.isFocused())
            return control_rod_level.keyPressed(key, b, c);
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        control_rod_level.tick();
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        String control_rod_levelValue = control_rod_level.getValue();
        super.resize(minecraft, width, height);
        control_rod_level.setValue(control_rod_levelValue);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.pwr_controller_gui.label_temperature"), 113, 9, -12829636, false);
    }

    @Override
    public void init() {
        super.init();
        control_rod_level = new EditBox(this.font, this.leftPos + 46, this.topPos + 63, 118, 18, Component.translatable("gui.hbm_ntm.pwr_controller_gui.control_rod_level")) {
            @Override
            public void insertText(String text) {
                super.insertText(text);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.pwr_controller_gui.control_rod_level").getString());
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.pwr_controller_gui.control_rod_level").getString());
                else
                    setSuggestion(null);
            }
        };
        control_rod_level.setSuggestion(Component.translatable("gui.hbm_ntm.pwr_controller_gui.control_rod_level").getString());
        control_rod_level.setMaxLength(32767);
        guistate.put("text:control_rod_level", control_rod_level);
        this.addWidget(this.control_rod_level);
        button_save = Button.builder(Component.translatable("gui.hbm_ntm.pwr_controller_gui.button_save"), e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new PWRControllerGuiButtonMessage(0, x, y, z));
                PWRControllerGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
            }
        }).bounds(this.leftPos + 82, this.topPos + 41, 46, 20).build();
        guistate.put("button:button_save", button_save);
        this.addRenderableWidget(button_save);
    }

    private String getAverageFuelRodEnergy() {
        long totalEnergy = 0;
        int rodCount = 0;
        int range = 15;

        for (int sx = -range; sx <= range; sx++) {
            for (int sy = -range; sy <= range; sy++) {
                for (int sz = -range; sz <= range; sz++) {
                    BlockPos targetPos = new BlockPos(this.x + sx, this.y + sy, this.z + sz);
                    if (world.getBlockState(targetPos).getBlock() == net.StrayBead.hbm_ntm.block.ModBlocks.PWR_FUEL_ROD.get()) {
                        net.minecraft.world.level.block.entity.BlockEntity be = world.getBlockEntity(targetPos);
                        if (be != null) {
                            java.util.concurrent.atomic.AtomicLong energyInRod = new java.util.concurrent.atomic.AtomicLong(0);
                            be.getCapability(net.minecraftforge.common.capabilities.ForgeCapabilities.ENERGY).ifPresent(cap -> {
                                energyInRod.set(cap.getEnergyStored());
                            });
                            totalEnergy += energyInRod.get();
                            rodCount++;
                        }
                    }
                }
            }
        }

        if (rodCount == 0) return "0 FE";
        return (totalEnergy / rodCount) + " FE (Avg)";
    }
}