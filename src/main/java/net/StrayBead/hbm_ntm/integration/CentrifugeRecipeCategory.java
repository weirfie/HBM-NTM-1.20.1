package net.StrayBead.hbm_ntm.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.recipe.CentrifugeRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CentrifugeRecipeCategory implements IRecipeCategory<CentrifugeRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(HBMNTM.MOD_ID, "centrifuge");
    public static final RecipeType<CentrifugeRecipe> TYPE = new RecipeType<>(UID, CentrifugeRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slotDrawable;

    public CentrifugeRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(140, 50);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CENTRIFUGE.get()));
        this.slotDrawable = helper.getSlotDrawable();
    }

    @Override
    public RecipeType<CentrifugeRecipe> getRecipeType() { return TYPE; }

    @Override
    public Component getTitle() { return Component.literal("Centrifuge"); }

    @Override
    public IDrawable getBackground() { return this.background; }

    @Override
    public IDrawable getIcon() { return this.icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CentrifugeRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 18)
                .addIngredients(recipe.getIngredients().get(0))
                .setBackground(slotDrawable, -1, -1);

        var outputs = recipe.getOutputs();

        if (outputs.size() > 0) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 9)
                    .addItemStack(outputs.get(0))
                    .setBackground(slotDrawable, -1, -1);
        }
        if (outputs.size() > 1) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 98, 9)
                    .addItemStack(outputs.get(1))
                    .setBackground(slotDrawable, -1, -1);
        }
        if (outputs.size() > 2) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 27)
                    .addItemStack(outputs.get(2))
                    .setBackground(slotDrawable, -1, -1);
        }
        if (outputs.size() > 3) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 98, 27)
                    .addItemStack(outputs.get(3))
                    .setBackground(slotDrawable, -1, -1);
        }
    }

    @Override
    public void draw(CentrifugeRecipe recipe, IRecipeSlotsView slotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.drawString(Minecraft.getInstance().font, "➔", 45, 22, 0x404040, false);

        graphics.drawString(Minecraft.getInstance().font, "32 HE/t", 10, 5, 0x404040, false);
    }
}
