package net.StrayBead.hbm_ntm.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
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
import net.StrayBead.hbm_ntm.recipe.ChemicalPlantRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class ChemicalPlantRecipeCategory implements IRecipeCategory<ChemicalPlantRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(HBMNTM.MOD_ID, "chemical_plant");
    public static final RecipeType<ChemicalPlantRecipe> TYPE = new RecipeType<>(UID, ChemicalPlantRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;

    public ChemicalPlantRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(160, 100);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CHEMICAL_PLANT.get()));
        this.slot = helper.getSlotDrawable();
    }

    @Override public RecipeType<ChemicalPlantRecipe> getRecipeType() { return TYPE; }
    @Override public Component getTitle() { return Component.literal("Chemical Plant"); }
    @Override public IDrawable getBackground() { return this.background; }
    @Override public IDrawable getIcon() { return this.icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ChemicalPlantRecipe recipe, IFocusGroup focuses) {
        for (int i = 0; i < recipe.getFluidIngredients().size(); i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, 10, 10 + (i * 20))
                    .addIngredient(ForgeTypes.FLUID_STACK, recipe.getFluidIngredients().get(i))
                    .setBackground(slot, -1, -1);
        }

        for (int i = 0; i < recipe.getItemIngredients().size(); i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, 40, 20 + (i * 20))
                    .addIngredients(recipe.getItemIngredients().get(i))
                    .setBackground(slot, -1, -1);
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 130, 40)
                .addItemStack(recipe.getResultItem(RegistryAccess.EMPTY))
                .setBackground(slot, -1, -1);

        for (int i = 0; i < recipe.getFluidOutputs().size(); i++) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 100, 10 + (i * 20))
                    .addIngredient(ForgeTypes.FLUID_STACK, recipe.getFluidOutputs().get(i))
                    .setBackground(slot, -1, -1);
        }
    }

    @Override
    public void draw(ChemicalPlantRecipe recipe, IRecipeSlotsView slotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.drawString(Minecraft.getInstance().font, "➔", 75, 42, 0x404040, false);
        graphics.drawString(Minecraft.getInstance().font, recipe.getEnergyPerTick() + " FE/t", 65, 80, 0xAA0000, false);
    }
}