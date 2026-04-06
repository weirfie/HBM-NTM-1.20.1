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
import net.StrayBead.hbm_ntm.recipe.AnvilBlockRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class AnvilButtonClickRecipeCategory implements IRecipeCategory<AnvilBlockRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(HBMNTM.MOD_ID, "anvil_crafting");
    public static final RecipeType<AnvilBlockRecipe> TYPE = new RecipeType<>(UID, AnvilBlockRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slotDrawable;

    public AnvilButtonClickRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(120, 45);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.LEAD_ANVIL.get()));
        this.slotDrawable = helper.getSlotDrawable();
    }

    @Override
    public RecipeType<AnvilBlockRecipe> getRecipeType() { return TYPE; }

    @Override
    public Component getTitle() { return Component.literal("Lead Anvil Smithing"); }

    @Override
    public IDrawable getBackground() { return this.background; }

    @Override
    public IDrawable getIcon() { return this.icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AnvilBlockRecipe recipe, IFocusGroup focuses) {
        java.util.Map<Ingredient, Integer> groupedIngredients = new java.util.LinkedHashMap<>();

        for (Ingredient ingredient : recipe.getIngredients()) {
            groupedIngredients.put(ingredient, groupedIngredients.getOrDefault(ingredient, 0) + 1);
        }

        int x = 10;
        int y = 15;

        for (java.util.Map.Entry<Ingredient, Integer> entry : groupedIngredients.entrySet()) {
            Ingredient ing = entry.getKey();
            int count = entry.getValue();

            List<ItemStack> stacksWithCount = java.util.Arrays.stream(ing.getItems())
                    .map(stack -> {
                        ItemStack copy = stack.copy();
                        copy.setCount(count);
                        return copy;
                    }).toList();

            builder.addSlot(RecipeIngredientRole.INPUT, x, y)
                    .addItemStacks(stacksWithCount)
                    .setBackground(slotDrawable, -1, -1);

            x += 18;
            if (x > 70) {
                x = 10;
                y += 18;
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 90, 15)
                .addItemStack(recipe.getResultItem(RegistryAccess.EMPTY))
                .setBackground(slotDrawable, -1, -1);
    }

    @Override
    public void draw(AnvilBlockRecipe recipe, IRecipeSlotsView slotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.drawString(Minecraft.getInstance().font, "➔", 55, 20, 0x404040, false);
        graphics.drawString(Minecraft.getInstance().font, "1 XP Level", 35, 5, 0x38FF20, false);
    }
}
