package net.StrayBead.hbm_ntm.integration;

import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.recipe.SolderingStationRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class SolderingRecipeCategory implements IRecipeCategory<SolderingStationRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(HBMNTM.MOD_ID, "soldering");

    public static final RecipeType<SolderingStationRecipe> SOLDERING_TYPE =
            new RecipeType<>(UID, SolderingStationRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;

    public SolderingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(150, 65);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SOLDERING_STATION.get()));

        this.slot = helper.getSlotDrawable();
    }

    @Override
    public RecipeType<SolderingStationRecipe> getRecipeType() { return SOLDERING_TYPE; }

    @Override
    public Component getTitle() { return Component.literal("Soldering Station"); }

    @Override
    public IDrawable getBackground() { return this.background; }

    @Override
    public IDrawable getIcon() { return this.icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SolderingStationRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 15)
                .addIngredients(recipe.getIngredients().get(0))
                .setBackground(this.slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.INPUT, 45, 15)
                .addIngredients(recipe.getIngredients().get(1))
                .setBackground(this.slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.INPUT, 63, 15)
                .addIngredients(recipe.getIngredients().get(2))
                .setBackground(this.slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.INPUT, 27, 33)
                .addIngredients(recipe.getIngredients().get(3))
                .setBackground(this.slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.INPUT, 45, 33)
                .addIngredients(recipe.getIngredients().get(4))
                .setBackground(this.slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.INPUT, 63, 33)
                .addIngredients(recipe.getIngredients().get(5))
                .setBackground(this.slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 120, 24)
                .addItemStack(recipe.getResultItem(null))
                .setBackground(this.slot, -1, -1);
    }

    @Override
    public void draw(SolderingStationRecipe recipe, IRecipeSlotsView recipeSlotsView, net.minecraft.client.gui.GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.drawString(net.minecraft.client.Minecraft.getInstance().font, "➔", 95, 28, 0x404040, false);
    }
}
