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
    public static final ResourceLocation TEXTURE = new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/assembly_machine_gui.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;

    private final IDrawable cog;
    private final IDrawable arrowLine;
    private final IDrawable arrowHead;
    private final IDrawable templateBase;

    public AssemblyRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 5, 15, 165, 120);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ASSEMBLY_MACHINE.get()));
        this.slot = helper.getSlotDrawable();

        this.cog = helper.drawableBuilder(new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/cogwheel.png"), 0, 0, 16, 16).setTextureSize(16, 16).build();
        this.arrowLine = helper.drawableBuilder(new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/arrow_line.png"), 0, 0, 16, 16).setTextureSize(16, 16).build();
        this.arrowHead = helper.drawableBuilder(new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/arrow_head.png"), 0, 0, 16, 16).setTextureSize(16, 16).build();
        this.templateBase = helper.drawableBuilder(new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/assembly_template_base.png"), 0, 0, 16, 16).setTextureSize(16, 16).build();
    }

    @Override
    public void draw(AssemblyRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        templateBase.draw(guiGraphics, 81 - 5, 68 - 15);

        cog.draw(guiGraphics, 52 - 5, 90 - 15);
        cog.draw(guiGraphics, 67 - 5, 106 - 15);
        cog.draw(guiGraphics, 83 - 5, 89 - 15);
        cog.draw(guiGraphics, 99 - 5, 106 - 15);

        arrowLine.draw(guiGraphics, 46 - 5, 98 - 15);
        arrowLine.draw(guiGraphics, 62 - 5, 98 - 15);
        arrowLine.draw(guiGraphics, 78 - 5, 98 - 15);
        arrowLine.draw(guiGraphics, 94 - 5, 98 - 15);
        arrowHead.draw(guiGraphics, 110 - 5, 98 - 15);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AssemblyRecipe recipe, IFocusGroup focuses) {
        int[][] ingredientCoords = {
                {9, 21}, {9, 39}, {9, 57}, {9, 75}, {9, 93}, {9, 111},
                {27, 21}, {27, 39}, {27, 57}, {27, 75}, {27, 93}, {27, 111}
        };

        List<Ingredient> ingredients = recipe.getIngredients();
        for (int i = 0; i < Math.min(ingredients.size(), 12); i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, ingredientCoords[i][0] - 5, ingredientCoords[i][1] - 15)
                    .addIngredients(ingredients.get(i));
        }

        builder.addSlot(RecipeIngredientRole.CATALYST, 81 - 5, 68 - 15)
                .addIngredients(recipe.getTemplate());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 137 - 5, 98 - 15)
                .addItemStack(recipe.getResultItem(RegistryAccess.EMPTY))
                .setBackground(this.slot, -1, -1);
    }

    @Override public RecipeType<AssemblyRecipe> getRecipeType() { return JEINTMPlugin.ASSEMBLY_TYPE; }
    @Override public Component getTitle() { return Component.literal("Assembly Machine"); }
    @Override public IDrawable getBackground() { return this.background; }
    @Override public IDrawable getIcon() { return this.icon; }
}
