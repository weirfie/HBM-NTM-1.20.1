package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.StrayBead.hbm_ntm.HBMNTM;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.HashMap;

public class ControlPanelGuiScreen extends AbstractContainerScreen<ControlPanelGuiMenu> {
    private final static HashMap<String, Object> guistate = ControlPanelGuiMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;
    EditBox temperature;
    EditBox watertemp;
    EditBox rodtemp;
    EditBox thermgen;
    EditBox waterlev;
    EditBox status;
    EditBox OPSM;
    EditBox xenon;
    Checkbox Extract;
    Button button_50;
    Button button_100;
    Button button_sg1;
    Button button_sg2;
    Button button_sg3;
    Button button_sg4;
    ImageButton imagebutton_fuel;
    ImageButton imagebutton_controlrod;
    ImageButton imagebutton_controlrod1;
    ImageButton imagebutton_controlrod2;
    ImageButton imagebutton_controlrod3;
    ImageButton imagebutton_steam;
    ImageButton imagebutton_steam1;
    ImageButton imagebutton_steam2;
    ImageButton imagebutton_steam3;
    ImageButton imagebutton_moderator;
    ImageButton imagebutton_moderator1;
    ImageButton imagebutton_moderator2;
    ImageButton imagebutton_moderator3;
    ImageButton imagebutton_fuel1;
    ImageButton imagebutton_fuel2;
    ImageButton imagebutton_fuel3;
    ImageButton imagebutton_fuel4;
    ImageButton imagebutton_moderator4;
    ImageButton imagebutton_moderator5;
    ImageButton imagebutton_moderator6;
    ImageButton imagebutton_moderator7;
    ImageButton imagebutton_moderator8;
    ImageButton imagebutton_moderator9;
    ImageButton imagebutton_moderator10;
    ImageButton imagebutton_moderator11;
    ImageButton imagebutton_az5;
    ImageButton imagebutton_rise;
    ImageButton imagebutton_down;
    ImageButton imagebutton_redbutton;
    ImageButton imagebutton_redbutton1;

    public ControlPanelGuiScreen(ControlPanelGuiMenu container, Inventory inventory, Component text) {
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 418;
        this.imageHeight = 229;
    }

    private static final ResourceLocation texture = new ResourceLocation("hbm_ntm:textures/screens/control_panel_gui.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        temperature.render(guiGraphics, mouseX, mouseY, partialTicks);
        watertemp.render(guiGraphics, mouseX, mouseY, partialTicks);
        rodtemp.render(guiGraphics, mouseX, mouseY, partialTicks);
        thermgen.render(guiGraphics, mouseX, mouseY, partialTicks);
        waterlev.render(guiGraphics, mouseX, mouseY, partialTicks);
        status.render(guiGraphics, mouseX, mouseY, partialTicks);
        OPSM.render(guiGraphics, mouseX, mouseY, partialTicks);
        xenon.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
        if (mouseX > leftPos + 198 && mouseX < leftPos + 222 && mouseY > topPos + 71 && mouseY < topPos + 95)
            guiGraphics.renderTooltip(font, Component.translatable("gui.hbm_ntm.control_panel_gui.tooltip_select_yellow_group"), mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.blit(texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/structure.png"), this.leftPos + 186, this.topPos + 47, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/structure.png"), this.leftPos + 172, this.topPos + 47, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/structure.png"), this.leftPos + 172, this.topPos + 61, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/structure.png"), this.leftPos + 172, this.topPos + 91, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/structure.png"), this.leftPos + 172, this.topPos + 106, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/structure.png"), this.leftPos + 186, this.topPos + 106, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/structure.png"), this.leftPos + 215, this.topPos + 105, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/structure.png"), this.leftPos + 229, this.topPos + 106, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/structure.png"), this.leftPos + 229, this.topPos + 91, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/structure.png"), this.leftPos + 215, this.topPos + 47, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/structure.png"), this.leftPos + 229, this.topPos + 61, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/structure.png"), this.leftPos + 229, this.topPos + 47, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 126, this.topPos + 121, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 126, this.topPos + 105, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 126, this.topPos + 89, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 126, this.topPos + 73, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 126, this.topPos + 57, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 126, this.topPos + 41, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/meter.png"), this.leftPos + 126, this.topPos + 25, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/question.png"), this.leftPos + 276, this.topPos + 16, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/leftarrow.png"), this.leftPos + 320, this.topPos + 125, 0, 0, 30, 20, 30, 20);

        guiGraphics.blit(new ResourceLocation("hbm_ntm:textures/screens/rightarrow.png"), this.leftPos + 364, this.topPos + 124, 0, 0, 30, 20, 30, 20);

        RenderSystem.disableBlend();
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        if (temperature.isFocused())
            return temperature.keyPressed(key, b, c);
        if (watertemp.isFocused())
            return watertemp.keyPressed(key, b, c);
        if (rodtemp.isFocused())
            return rodtemp.keyPressed(key, b, c);
        if (thermgen.isFocused())
            return thermgen.keyPressed(key, b, c);
        if (waterlev.isFocused())
            return waterlev.keyPressed(key, b, c);
        if (status.isFocused())
            return status.keyPressed(key, b, c);
        if (OPSM.isFocused())
            return OPSM.keyPressed(key, b, c);
        if (xenon.isFocused())
            return xenon.keyPressed(key, b, c);
        return super.keyPressed(key, b, c);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        temperature.tick();
        watertemp.tick();
        rodtemp.tick();
        thermgen.tick();
        waterlev.tick();
        status.tick();
        OPSM.tick();
        xenon.tick();

        if (ControlPanelGuiMenu.guistate.containsKey("xenon_result")) {
            String value = ControlPanelGuiMenu.guistate.get("xenon_result").toString();
            this.xenon.setValue(value);
            ControlPanelGuiMenu.guistate.remove("xenon_result");
        }
    }

    @Override
    public void resize(Minecraft minecraft, int width, int height) {
        String temperatureValue = temperature.getValue();
        String watertempValue = watertemp.getValue();
        String rodtempValue = rodtemp.getValue();
        String thermgenValue = thermgen.getValue();
        String waterlevValue = waterlev.getValue();
        String statusValue = status.getValue();
        String OPSMValue = OPSM.getValue();
        String xenonValue = xenon.getValue();
        super.resize(minecraft, width, height);
        temperature.setValue(temperatureValue);
        watertemp.setValue(watertempValue);
        rodtemp.setValue(rodtempValue);
        thermgen.setValue(thermgenValue);
        waterlev.setValue(waterlevValue);
        status.setValue(statusValue);
        OPSM.setValue(OPSMValue);
        xenon.setValue(xenonValue);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.control_panel_gui.label_control_panel"), 174, 4, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.control_panel_gui.label_power_level"), 232, 125, -12829636, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.control_panel_gui.label_reactor_info"), 295, 16, -16777216, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.control_panel_gui.label_status"), 7, 11, -16777216, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.control_panel_gui.label_safety_system_override"), 9, 85, -16777216, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.control_panel_gui.label_coolant_injection"), 20, 139, -16777216, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.control_panel_gui.label_oppm"), 347, 112, -15870, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.control_panel_gui.label_none"), 346, 171, -16777216, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.control_panel_gui.label_xenon_concentration"), 305, 183, -16777216, false);
        guiGraphics.drawString(this.font, Component.translatable("gui.hbm_ntm.control_panel_gui.label_heat_exchangers"), 25, 157, -16777216, false);
    }

    @Override
    public void init() {
        super.init();
        temperature = new EditBox(this.font, this.leftPos + 151, this.topPos + 15, 118, 18, Component.translatable("gui.hbm_ntm.control_panel_gui.temperature")) {
            @Override
            public void insertText(String text) {
                super.insertText(text);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.temperature").getString());
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.temperature").getString());
                else
                    setSuggestion(null);
            }
        };
        temperature.setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.temperature").getString());
        temperature.setMaxLength(32767);
        guistate.put("text:temperature", temperature);
        this.addWidget(this.temperature);
        watertemp = new EditBox(this.font, this.leftPos + 295, this.topPos + 29, 118, 18, Component.translatable("gui.hbm_ntm.control_panel_gui.watertemp")) {
            @Override
            public void insertText(String text) {
                super.insertText(text);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.watertemp").getString());
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.watertemp").getString());
                else
                    setSuggestion(null);
            }
        };
        watertemp.setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.watertemp").getString());
        watertemp.setMaxLength(32767);
        guistate.put("text:watertemp", watertemp);
        this.addWidget(this.watertemp);
        rodtemp = new EditBox(this.font, this.leftPos + 295, this.topPos + 49, 118, 18, Component.translatable("gui.hbm_ntm.control_panel_gui.rodtemp")) {
            @Override
            public void insertText(String text) {
                super.insertText(text);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.rodtemp").getString());
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.rodtemp").getString());
                else
                    setSuggestion(null);
            }
        };
        rodtemp.setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.rodtemp").getString());
        rodtemp.setMaxLength(32767);
        guistate.put("text:rodtemp", rodtemp);
        this.addWidget(this.rodtemp);
        thermgen = new EditBox(this.font, this.leftPos + 295, this.topPos + 69, 118, 18, Component.translatable("gui.hbm_ntm.control_panel_gui.thermgen")) {
            @Override
            public void insertText(String text) {
                super.insertText(text);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.thermgen").getString());
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.thermgen").getString());
                else
                    setSuggestion(null);
            }
        };
        thermgen.setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.thermgen").getString());
        thermgen.setMaxLength(32767);
        guistate.put("text:thermgen", thermgen);
        this.addWidget(this.thermgen);
        waterlev = new EditBox(this.font, this.leftPos + 295, this.topPos + 89, 118, 18, Component.translatable("gui.hbm_ntm.control_panel_gui.waterlev")) {
            @Override
            public void insertText(String text) {
                super.insertText(text);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.waterlev").getString());
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.waterlev").getString());
                else
                    setSuggestion(null);
            }
        };
        waterlev.setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.waterlev").getString());
        waterlev.setMaxLength(32767);
        guistate.put("text:waterlev", waterlev);
        this.addWidget(this.waterlev);
        status = new EditBox(this.font, this.leftPos + 5, this.topPos + 24, 118, 18, Component.translatable("gui.hbm_ntm.control_panel_gui.status")) {
            @Override
            public void insertText(String text) {
                super.insertText(text);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.status").getString());
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.status").getString());
                else
                    setSuggestion(null);
            }
        };
        status.setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.status").getString());
        status.setMaxLength(32767);
        guistate.put("text:status", status);
        this.addWidget(this.status);
        OPSM = new EditBox(this.font, this.leftPos + 295, this.topPos + 147, 118, 18, Component.translatable("gui.hbm_ntm.control_panel_gui.OPSM")) {
            @Override
            public void insertText(String text) {
                super.insertText(text);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.OPSM").getString());
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.OPSM").getString());
                else
                    setSuggestion(null);
            }
        };
        OPSM.setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.OPSM").getString());
        OPSM.setMaxLength(32767);
        guistate.put("text:OPSM", OPSM);
        this.addWidget(this.OPSM);
        xenon = new EditBox(this.font, this.leftPos + 295, this.topPos + 194, 118, 18, Component.translatable("gui.hbm_ntm.control_panel_gui.xenon")) {
            @Override
            public void insertText(String text) {
                super.insertText(text);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.xenon").getString());
                else
                    setSuggestion(null);
            }

            @Override
            public void moveCursorTo(int pos) {
                super.moveCursorTo(pos);
                if (getValue().isEmpty())
                    setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.xenon").getString());
                else
                    setSuggestion(null);
            }
        };
        xenon.setSuggestion(Component.translatable("gui.hbm_ntm.control_panel_gui.xenon").getString());
        xenon.setMaxLength(32767);
        guistate.put("text:xenon", xenon);
        this.addWidget(this.xenon);
        button_50 = Button.builder(Component.translatable("gui.hbm_ntm.control_panel_gui.button_50"), e -> {
            if (GetCheckboxProcedure.execute(guistate)) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(0, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 0, x, y, z);
            }
        }).bounds(this.leftPos + 136, this.topPos + 173, 40, 20).build(builder -> new Button(builder) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
                this.visible = GetCheckboxProcedure.execute(guistate);
                super.renderWidget(guiGraphics, gx, gy, ticks);
            }
        });
        guistate.put("button:button_50", button_50);
        this.addRenderableWidget(button_50);
        button_100 = Button.builder(Component.translatable("gui.hbm_ntm.control_panel_gui.button_100"), e -> {
            if (GetCheckboxProcedure.execute(guistate)) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(1, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 1, x, y, z);
            }
        }).bounds(this.leftPos + 136, this.topPos + 146, 46, 20).build(builder -> new Button(builder) {
            @Override
            public void renderWidget(GuiGraphics guiGraphics, int gx, int gy, float ticks) {
                this.visible = GetCheckboxProcedure.execute(guistate);
                super.renderWidget(guiGraphics, gx, gy, ticks);
            }
        });
        guistate.put("button:button_100", button_100);
        this.addRenderableWidget(button_100);
        button_sg1 = Button.builder(Component.translatable("gui.hbm_ntm.control_panel_gui.button_sg1"), e -> {
            HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(34, x, y, z));
            ControlPanelGuiButtonMessage.handleButtonAction(entity, 34, x, y, z);
        }).bounds(this.leftPos + 19, this.topPos + 169, 40, 20).build();
        guistate.put("button:button_sg1", button_sg1);
        this.addRenderableWidget(button_sg1);
        button_sg2 = Button.builder(Component.translatable("gui.hbm_ntm.control_panel_gui.button_sg2"), e -> {
        }).bounds(this.leftPos + 68, this.topPos + 169, 40, 20).build();
        guistate.put("button:button_sg2", button_sg2);
        this.addRenderableWidget(button_sg2);
        button_sg3 = Button.builder(Component.translatable("gui.hbm_ntm.control_panel_gui.button_sg3"), e -> {
        }).bounds(this.leftPos + 19, this.topPos + 190, 40, 20).build();
        guistate.put("button:button_sg3", button_sg3);
        this.addRenderableWidget(button_sg3);
        button_sg4 = Button.builder(Component.translatable("gui.hbm_ntm.control_panel_gui.button_sg4"), e -> {
        }).bounds(this.leftPos + 68, this.topPos + 190, 40, 20).build();
        guistate.put("button:button_sg4", button_sg4);
        this.addRenderableWidget(button_sg4);
        imagebutton_fuel = new ImageButton(this.leftPos + 201, this.topPos + 76, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_fuel.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(6, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 6, x, y, z);
            }
        });
        guistate.put("button:imagebutton_fuel", imagebutton_fuel);
        this.addRenderableWidget(imagebutton_fuel);
        imagebutton_controlrod = new ImageButton(this.leftPos + 201, this.topPos + 62, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_controlrod.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(7, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 7, x, y, z);
            }
        });
        guistate.put("button:imagebutton_controlrod", imagebutton_controlrod);
        this.addRenderableWidget(imagebutton_controlrod);
        imagebutton_controlrod1 = new ImageButton(this.leftPos + 215, this.topPos + 76, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_controlrod1.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(8, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 8, x, y, z);
            }
        });
        guistate.put("button:imagebutton_controlrod1", imagebutton_controlrod1);
        this.addRenderableWidget(imagebutton_controlrod1);
        imagebutton_controlrod2 = new ImageButton(this.leftPos + 186, this.topPos + 76, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_controlrod2.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(9, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 9, x, y, z);
            }
        });
        guistate.put("button:imagebutton_controlrod2", imagebutton_controlrod2);
        this.addRenderableWidget(imagebutton_controlrod2);
        imagebutton_controlrod3 = new ImageButton(this.leftPos + 200, this.topPos + 90, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_controlrod3.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(10, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 10, x, y, z);
            }
        });
        guistate.put("button:imagebutton_controlrod3", imagebutton_controlrod3);
        this.addRenderableWidget(imagebutton_controlrod3);
        imagebutton_steam = new ImageButton(this.leftPos + 215, this.topPos + 91, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_steam.png"), 16, 32, e -> {
        });
        guistate.put("button:imagebutton_steam", imagebutton_steam);
        this.addRenderableWidget(imagebutton_steam);
        imagebutton_steam1 = new ImageButton(this.leftPos + 215, this.topPos + 62, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_steam1.png"), 16, 32, e -> {
        });
        guistate.put("button:imagebutton_steam1", imagebutton_steam1);
        this.addRenderableWidget(imagebutton_steam1);
        imagebutton_steam2 = new ImageButton(this.leftPos + 186, this.topPos + 62, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_steam2.png"), 16, 32, e -> {
        });
        guistate.put("button:imagebutton_steam2", imagebutton_steam2);
        this.addRenderableWidget(imagebutton_steam2);
        imagebutton_steam3 = new ImageButton(this.leftPos + 186, this.topPos + 91, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_steam3.png"), 16, 32, e -> {
        });
        guistate.put("button:imagebutton_steam3", imagebutton_steam3);
        this.addRenderableWidget(imagebutton_steam3);
        imagebutton_moderator = new ImageButton(this.leftPos + 200, this.topPos + 105, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_moderator.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(15, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 15, x, y, z);
            }
        });
        guistate.put("button:imagebutton_moderator", imagebutton_moderator);
        this.addRenderableWidget(imagebutton_moderator);
        imagebutton_moderator1 = new ImageButton(this.leftPos + 229, this.topPos + 76, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_moderator1.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(16, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 16, x, y, z);
            }
        });
        guistate.put("button:imagebutton_moderator1", imagebutton_moderator1);
        this.addRenderableWidget(imagebutton_moderator1);
        imagebutton_moderator2 = new ImageButton(this.leftPos + 201, this.topPos + 47, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_moderator2.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(17, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 17, x, y, z);
            }
        });
        guistate.put("button:imagebutton_moderator2", imagebutton_moderator2);
        this.addRenderableWidget(imagebutton_moderator2);
        imagebutton_moderator3 = new ImageButton(this.leftPos + 172, this.topPos + 77, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_moderator3.png"), 16, 32, e -> {
        });
        guistate.put("button:imagebutton_moderator3", imagebutton_moderator3);
        this.addRenderableWidget(imagebutton_moderator3);
        imagebutton_fuel1 = new ImageButton(this.leftPos + 158, this.topPos + 76, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_fuel1.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(19, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 19, x, y, z);
            }
        });
        guistate.put("button:imagebutton_fuel1", imagebutton_fuel1);
        this.addRenderableWidget(imagebutton_fuel1);
        imagebutton_fuel2 = new ImageButton(this.leftPos + 200, this.topPos + 119, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_fuel2.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(20, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 20, x, y, z);
            }
        });
        guistate.put("button:imagebutton_fuel2", imagebutton_fuel2);
        this.addRenderableWidget(imagebutton_fuel2);
        imagebutton_fuel3 = new ImageButton(this.leftPos + 243, this.topPos + 76, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_fuel3.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(21, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 21, x, y, z);
            }
        });
        guistate.put("button:imagebutton_fuel3", imagebutton_fuel3);
        this.addRenderableWidget(imagebutton_fuel3);
        imagebutton_fuel4 = new ImageButton(this.leftPos + 200, this.topPos + 33, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_fuel4.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(22, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 22, x, y, z);
            }
        });
        guistate.put("button:imagebutton_fuel4", imagebutton_fuel4);
        this.addRenderableWidget(imagebutton_fuel4);
        imagebutton_moderator4 = new ImageButton(this.leftPos + 158, this.topPos + 91, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_moderator4.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(23, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 23, x, y, z);
            }
        });
        guistate.put("button:imagebutton_moderator4", imagebutton_moderator4);
        this.addRenderableWidget(imagebutton_moderator4);
        imagebutton_moderator5 = new ImageButton(this.leftPos + 158, this.topPos + 62, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_moderator5.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(24, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 24, x, y, z);
            }
        });
        guistate.put("button:imagebutton_moderator5", imagebutton_moderator5);
        this.addRenderableWidget(imagebutton_moderator5);
        imagebutton_moderator6 = new ImageButton(this.leftPos + 186, this.topPos + 119, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_moderator6.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(25, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 25, x, y, z);
            }
        });
        guistate.put("button:imagebutton_moderator6", imagebutton_moderator6);
        this.addRenderableWidget(imagebutton_moderator6);
        imagebutton_moderator7 = new ImageButton(this.leftPos + 214, this.topPos + 119, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_moderator7.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(26, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 26, x, y, z);
            }
        });
        guistate.put("button:imagebutton_moderator7", imagebutton_moderator7);
        this.addRenderableWidget(imagebutton_moderator7);
        imagebutton_moderator8 = new ImageButton(this.leftPos + 243, this.topPos + 90, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_moderator8.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(27, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 27, x, y, z);
            }
        });
        guistate.put("button:imagebutton_moderator8", imagebutton_moderator8);
        this.addRenderableWidget(imagebutton_moderator8);
        imagebutton_moderator9 = new ImageButton(this.leftPos + 243, this.topPos + 61, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_moderator9.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(28, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 28, x, y, z);
            }
        });
        guistate.put("button:imagebutton_moderator9", imagebutton_moderator9);
        this.addRenderableWidget(imagebutton_moderator9);
        imagebutton_moderator10 = new ImageButton(this.leftPos + 214, this.topPos + 33, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_moderator10.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(29, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 29, x, y, z);
            }
        });
        guistate.put("button:imagebutton_moderator10", imagebutton_moderator10);
        this.addRenderableWidget(imagebutton_moderator10);
        imagebutton_moderator11 = new ImageButton(this.leftPos + 187, this.topPos + 34, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_moderator11.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(30, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 30, x, y, z);
            }
        });
        guistate.put("button:imagebutton_moderator11", imagebutton_moderator11);
        this.addRenderableWidget(imagebutton_moderator11);
        imagebutton_az5 = new ImageButton(this.leftPos + 192, this.topPos + 152, 32, 32, 0, 0, 32, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_az5.png"), 32, 64, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(31, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 31, x, y, z);
            }
        });
        guistate.put("button:imagebutton_az5", imagebutton_az5);
        this.addRenderableWidget(imagebutton_az5);
        imagebutton_rise = new ImageButton(this.leftPos + 253, this.topPos + 136, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_rise.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(32, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 32, x, y, z);
            }
        });
        guistate.put("button:imagebutton_rise", imagebutton_rise);
        this.addRenderableWidget(imagebutton_rise);
        imagebutton_down = new ImageButton(this.leftPos + 253, this.topPos + 149, 16, 16, 0, 0, 16, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_down.png"), 16, 32, e -> {
            if (true) {
                HBMNTM.PACKET_HANDLER.sendToServer(new ControlPanelGuiButtonMessage(33, x, y, z));
                ControlPanelGuiButtonMessage.handleButtonAction(entity, 33, x, y, z);
            }
        });
        guistate.put("button:imagebutton_down", imagebutton_down);
        this.addRenderableWidget(imagebutton_down);
        imagebutton_redbutton = new ImageButton(this.leftPos + 49, this.topPos + 48, 32, 32, 0, 0, 32, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_redbutton.png"), 32, 64, e -> {
        });
        guistate.put("button:imagebutton_redbutton", imagebutton_redbutton);
        this.addRenderableWidget(imagebutton_redbutton);
        imagebutton_redbutton1 = new ImageButton(this.leftPos + 48, this.topPos + 101, 32, 32, 0, 0, 32, new ResourceLocation("hbm_ntm:textures/screens/atlas/imagebutton_redbutton1.png"), 32, 64, e -> {
        });
        guistate.put("button:imagebutton_redbutton1", imagebutton_redbutton1);
        this.addRenderableWidget(imagebutton_redbutton1);
        Extract = new Checkbox(this.leftPos + 137, this.topPos + 197, 20, 20, Component.translatable("gui.hbm_ntm.control_panel_gui.Extract"), false);
        guistate.put("checkbox:Extract", Extract);
        this.addRenderableWidget(Extract);
    }
}