package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class GuideBookScreen extends Screen {
    private int currentPage = 0;
    private int maxPages = 8;

    private final int bgWidth = 256;
    private final int bgHeight = 180;

    private final int imgWidth = 1023;
    private final int imgHeight = 658;

    private final int displayWidth = 250;
    private final int displayHeight = (imgHeight * displayWidth) / imgWidth;

    private static final ResourceLocation BOOK_PAGE_1 = new ResourceLocation("hbm_ntm", "textures/screens/guide_book_page_1.png");
    private static final ResourceLocation BOOK_PAGE_2 = new ResourceLocation("hbm_ntm", "textures/screens/guide_book_page_2.png");
    private static final ResourceLocation BOOK_PAGE_3 = new ResourceLocation("hbm_ntm", "textures/screens/guide_book_page_3.png");
    private static final ResourceLocation BOOK_PAGE_4 = new ResourceLocation("hbm_ntm", "textures/screens/guide_book_page_4.png");
    private static final ResourceLocation BOOK_PAGE_5 = new ResourceLocation("hbm_ntm", "textures/screens/guide_book_page_5.png");
    private static final ResourceLocation BOOK_PAGE_6 = new ResourceLocation("hbm_ntm", "textures/screens/guide_book_page_6.png");
    private static final ResourceLocation BOOK_PAGE_7 = new ResourceLocation("hbm_ntm", "textures/screens/guide_book_page_7.png");
    private static final ResourceLocation BOOK_PAGE_8 = new ResourceLocation("hbm_ntm", "textures/screens/guide_book_page_8.png");

    public GuideBookScreen() {
        super(Component.literal("Guide Book"));
    }

    @Override
    protected void init() {
        super.init();
        int x = (this.width - bgWidth) / 2;
        int y = (this.height - bgHeight) / 2;

        this.addRenderableWidget(Button.builder(Component.literal("<"), b -> {
            if (currentPage > 0) {
                currentPage--;
            }
        }).bounds(x + 15, y + 146, 12, 12).build());

        this.addRenderableWidget(Button.builder(Component.literal(">"), b -> {
            if (currentPage < maxPages - 1) {
                currentPage++;
            }
        }).bounds(x + bgWidth - 35, y + 146, 12, 12).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);

        int x = (this.width - bgWidth) / 2;
        int y = (this.height - bgHeight) / 2;

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        guiGraphics.fill(x, y, x + bgWidth, (y + bgHeight) - 10, 0xFF4F4F4F);

        guiGraphics.drawString(this.font, "An Industrialist's Guide To Rebuilding Society", x + 17, y - 10, 0xFFD000);

        int imgX = x + (bgWidth - displayWidth) / 2;
        int imgY = y + 5;

        ResourceLocation textureToDraw = switch (currentPage) {
            case 0 -> BOOK_PAGE_1;
            case 1 -> BOOK_PAGE_2;
            case 2 -> BOOK_PAGE_3;
            case 3 -> BOOK_PAGE_4;
            case 4 -> BOOK_PAGE_5;
            case 5 -> BOOK_PAGE_6;
            case 6 -> BOOK_PAGE_7;
            case 7 -> BOOK_PAGE_8;
            default -> BOOK_PAGE_1;
        };

        if (textureToDraw != null) {
            guiGraphics.blit(textureToDraw, imgX, imgY, displayWidth, displayHeight, 0, 0, imgWidth, imgHeight, imgWidth, imgHeight);
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
