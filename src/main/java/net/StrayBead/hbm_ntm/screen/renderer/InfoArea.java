package net.StrayBead.hbm_ntm.screen.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;

public abstract class InfoArea {
    protected final Rect2i area;

    protected InfoArea(Rect2i area) {
        this.area = area;
    }

    // Use GuiGraphics for 1.20.1
    public abstract void draw(GuiGraphics guiGraphics);
}
