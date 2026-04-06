package net.StrayBead.hbm_ntm.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.recipe.ArcWelderRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class WeldingRecipeCategory implements IRecipeCategory<ArcWelderRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(HBMNTM.MOD_ID, "welding");
    public static final ResourceLocation TEXTURE = new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/arc_welder_gui.png");

    public static final RecipeType<ArcWelderRecipe> WELDING_TYPE =
            new RecipeType<>(UID, ArcWelderRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public WeldingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ARC_WELDER.get()));
    }

    @Override
    public RecipeType<ArcWelderRecipe> getRecipeType() { return WELDING_TYPE; }

    @Override
    public Component getTitle() { return Component.literal("Arc Welder"); }

    @Override
    public IDrawable getBackground() { return this.background; }

    @Override
    public IDrawable getIcon() { return this.icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ArcWelderRecipe recipe, IFocusGroup focuses) {
        var ingredients = recipe.getIngredients();

        builder.addSlot(RecipeIngredientRole.INPUT, 17, 16)
                .addIngredients(ingredients.get(0));

        if (ingredients.size() > 1) {
            builder.addSlot(RecipeIngredientRole.INPUT, 35, 16)
                    .addIngredients(ingredients.get(1));
        }

        if (ingredients.size() > 2) {
            builder.addSlot(RecipeIngredientRole.INPUT, 53, 16)
                    .addIngredients(ingredients.get(2));
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 107, 16)
                .addItemStack(recipe.getResultItem(null));
    }
}
