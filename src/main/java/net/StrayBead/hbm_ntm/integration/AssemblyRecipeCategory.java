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
import net.StrayBead.hbm_ntm.recipe.AssemblyRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class AssemblyRecipeCategory implements IRecipeCategory<AssemblyRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(HBMNTM.MOD_ID, "assembly_machine");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot; // The JEI slot drawable

    private final IDrawable cog;
    private final IDrawable arrowLine;
    private final IDrawable arrowHead;
    private final IDrawable templateBase;

    public AssemblyRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(160, 115);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ASSEMBLY_MACHINE.get()));

        this.slot = helper.getSlotDrawable();

        this.cog = helper.drawableBuilder(new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/cogwheel.png"), 0, 0, 16, 16).setTextureSize(16, 16).build();
        this.arrowLine = helper.drawableBuilder(new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/arrow_line.png"), 0, 0, 16, 16).setTextureSize(16, 16).build();
        this.arrowHead = helper.drawableBuilder(new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/arrow_head.png"), 0, 0, 16, 16).setTextureSize(16, 16).build();
        this.templateBase = helper.drawableBuilder(new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/assembly_template_base.png"), 0, 0, 16, 16).setTextureSize(16, 16).build();
    }

    @Override
    public void draw(AssemblyRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        templateBase.draw(guiGraphics, 76, 53);

        cog.draw(guiGraphics, 47, 75);
        cog.draw(guiGraphics, 62, 91);
        cog.draw(guiGraphics, 78, 74);
        cog.draw(guiGraphics, 94, 91);

        arrowLine.draw(guiGraphics, 41, 83);
        arrowLine.draw(guiGraphics, 57, 83);
        arrowLine.draw(guiGraphics, 73, 83);
        arrowLine.draw(guiGraphics, 89, 83);
        arrowHead.draw(guiGraphics, 105, 83);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AssemblyRecipe recipe, IFocusGroup focuses) {
        int[][] ingredientCoords = {
                {4, 6}, {4, 24}, {4, 42}, {4, 60}, {4, 78}, {4, 96},
                {22, 6}, {22, 24}, {22, 42}, {22, 60}, {22, 78}, {22, 96}
        };

        List<Ingredient> ingredients = recipe.getIngredients();
        for (int i = 0; i < Math.min(ingredients.size(), 12); i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, ingredientCoords[i][0], ingredientCoords[i][1])
                    .addIngredients(ingredients.get(i))
                    .setBackground(this.slot, -1, -1);
        }

        builder.addSlot(RecipeIngredientRole.CATALYST, 76, 53)
                .addIngredients(recipe.getTemplate())
                .setBackground(this.slot, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 132, 83)
                .addItemStack(recipe.getResultItem(RegistryAccess.EMPTY))
                .setBackground(this.slot, -1, -1);
    }

    @Override public RecipeType<AssemblyRecipe> getRecipeType() { return JEINTMPlugin.ASSEMBLY_TYPE; }
    @Override public Component getTitle() { return Component.literal("Assembly Machine"); }
    @Override public IDrawable getBackground() { return this.background; }
    @Override public IDrawable getIcon() { return this.icon; }
}
