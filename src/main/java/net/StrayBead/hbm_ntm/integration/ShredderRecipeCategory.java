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
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.recipe.ShredderRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ShredderRecipeCategory implements IRecipeCategory<ShredderRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(HBMNTM.MOD_ID, "shredding");
    public static final RecipeType<ShredderRecipe> TYPE = new RecipeType<>(UID, ShredderRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slotDrawable;

    public ShredderRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(120, 45);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SHREDDER.get()));
        this.slotDrawable = helper.getSlotDrawable();
    }

    @Override
    public RecipeType<ShredderRecipe> getRecipeType() { return TYPE; }

    @Override
    public Component getTitle() { return Component.literal("Shredder"); }

    @Override
    public IDrawable getBackground() { return this.background; }

    @Override
    public IDrawable getIcon() { return this.icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ShredderRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 15)
                .addIngredients(recipe.getIngredients().get(0))
                .setBackground(slotDrawable, -1, -1);

        builder.addSlot(RecipeIngredientRole.CATALYST, 50, 25)
                .addItemStack(new ItemStack(ModItems.STEEL_SHREDDER_BLADES.get()))
                .setBackground(slotDrawable, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 90, 15)
                .addItemStack(recipe.getResultItem(RegistryAccess.EMPTY))
                .setBackground(slotDrawable, -1, -1);
    }

    @Override
    public void draw(ShredderRecipe recipe, IRecipeSlotsView slotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.drawString(Minecraft.getInstance().font, "➔", 55, 10, 0x404040, false);
        graphics.drawString(Minecraft.getInstance().font, "Requires:", 35, 30, 0x555555, false);
    }
}