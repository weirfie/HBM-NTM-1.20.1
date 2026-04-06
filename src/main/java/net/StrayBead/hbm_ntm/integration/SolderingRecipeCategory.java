package net.StrayBead.hbm_ntm.integration;

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
    public static final ResourceLocation TEXTURE = new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/soldering_station.png");

    public static final RecipeType<SolderingStationRecipe> SOLDERING_TYPE =
            new RecipeType<>(UID, SolderingStationRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public SolderingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SOLDERING_STATION.get()));
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
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 21).addIngredients(recipe.getIngredients().get(0));
        builder.addSlot(RecipeIngredientRole.INPUT, 45, 21).addIngredients(recipe.getIngredients().get(1));
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 21).addIngredients(recipe.getIngredients().get(2));
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 39).addIngredients(recipe.getIngredients().get(3));
        builder.addSlot(RecipeIngredientRole.INPUT, 45, 39).addIngredients(recipe.getIngredients().get(4));
        builder.addSlot(RecipeIngredientRole.INPUT, 63, 39).addIngredients(recipe.getIngredients().get(5));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 126, 31).addItemStack(recipe.getResultItem(null));
    }
}
