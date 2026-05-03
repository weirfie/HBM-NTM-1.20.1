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
import net.StrayBead.hbm_ntm.recipe.ArcFurnaceRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ArcFurnaceRecipeCategory implements IRecipeCategory<ArcFurnaceRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(HBMNTM.MOD_ID, "arc_furnace");
    public static final RecipeType<ArcFurnaceRecipe> TYPE = new RecipeType<>(UID, ArcFurnaceRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slotDrawable;

    public ArcFurnaceRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(120, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ELECTRIC_ARC_FURNACE.get()));
        this.slotDrawable = helper.getSlotDrawable();
    }

    @Override
    public RecipeType<ArcFurnaceRecipe> getRecipeType() { return TYPE; }

    @Override
    public Component getTitle() { return Component.literal("Electric Arc Furnace"); }

    @Override
    public IDrawable getBackground() { return this.background; }

    @Override
    public IDrawable getIcon() { return this.icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ArcFurnaceRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 10)
                .addIngredients(recipe.getIngredients().get(0))
                .setBackground(slotDrawable, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 85, 10)
                .addItemStack(recipe.getResultItem(RegistryAccess.EMPTY))
                .setBackground(slotDrawable, -1, -1);

        List<ItemStack> electrodes = List.of(
                new ItemStack(ModItems.GRAPHITE_ELECTRODE.get()),
                new ItemStack(ModItems.LANTHANIUM_ELECTRODE.get()),
                new ItemStack(ModItems.DESH_ELECTRODE.get()),
                new ItemStack(ModItems.SATURNITE_ELECTRODE.get())
        );

        builder.addSlot(RecipeIngredientRole.CATALYST, 35, 38).addItemStacks(electrodes).setBackground(slotDrawable, -1, -1);
        builder.addSlot(RecipeIngredientRole.CATALYST, 53, 38).addItemStacks(electrodes).setBackground(slotDrawable, -1, -1);
        builder.addSlot(RecipeIngredientRole.CATALYST, 71, 38).addItemStacks(electrodes).setBackground(slotDrawable, -1, -1);
    }

    @Override
    public void draw(ArcFurnaceRecipe recipe, IRecipeSlotsView slotsView, GuiGraphics graphics, double mouseX, double mouseY) {
        graphics.drawString(Minecraft.getInstance().font, "➔", 53, 15, 0x404040, false);
        graphics.drawString(Minecraft.getInstance().font, "Required Electrodes:", 25, 28, 0x555555, false);
    }
}