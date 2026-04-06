package net.StrayBead.hbm_ntm.integration;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.recipe.BasinCastingRecipe;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class BasinCastingRecipeCategory implements IRecipeCategory<BasinCastingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(HBMNTM.MOD_ID, "basin_casting");
    public static final RecipeType<BasinCastingRecipe> TYPE =
            new RecipeType<>(UID, BasinCastingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public BasinCastingRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(120, 30);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SHALLOW_FOUNDRY_BASIN.get()));
    }

    @Override
    public RecipeType<BasinCastingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.hbm_ntm.shallow_foundry_basin");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BasinCastingRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 5, 7)
                .addIngredients(ForgeTypes.FLUID_STACK, List.of(recipe.getFluidIngredient()))
                .setFluidRenderer(recipe.getFluidAmount(), false, 16, 16);

        builder.addSlot(RecipeIngredientRole.INPUT, 45, 7)
                .addIngredients(recipe.getMoldIngredient());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 7)
                .addItemStack(recipe.getResultItem(null));
    }
}
