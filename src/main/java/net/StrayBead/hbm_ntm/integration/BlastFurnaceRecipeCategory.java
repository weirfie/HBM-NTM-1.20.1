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
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.recipe.BlastFurnaceRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;

public class BlastFurnaceRecipeCategory implements IRecipeCategory<BlastFurnaceRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(HBMNTM.MOD_ID, "blast_furnace");
    public static final RecipeType<BlastFurnaceRecipe> TYPE = new RecipeType<>(UID, BlastFurnaceRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slotDrawable;

    private static final ResourceLocation METER_BOTTOM = new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/meter_bottom.png");
    private static final ResourceLocation METER = new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/meter.png");
    private static final ResourceLocation METER_TOP = new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/meter_top.png");
    private static final ResourceLocation ARROW = new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/arrow.png");
    private static final ResourceLocation SMELT = new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/smelt.png");

    public BlastFurnaceRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(160, 75);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.BLAST_FURNACE.get()));
        this.slotDrawable = helper.getSlotDrawable();
    }

    @Override
    public RecipeType<BlastFurnaceRecipe> getRecipeType() { return TYPE; }

    @Override
    public Component getTitle() { return Component.literal("Blast Furnace"); }

    @Override
    public IDrawable getBackground() { return background; }

    @Override
    public IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BlastFurnaceRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 8, 37)
                .addItemStack(new ItemStack(Items.COAL))
                .setBackground(slotDrawable, -1, -1);

        builder.addSlot(RecipeIngredientRole.INPUT, 80, 24)
                .addIngredients(recipe.getInput1())
                .setBackground(slotDrawable, -1, -1);

        builder.addSlot(RecipeIngredientRole.INPUT, 80, 56)
                .addIngredients(recipe.getInput2())
                .setBackground(slotDrawable, -1, -1);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 134, 39)
                .addItemStack(recipe.getResultItem(Minecraft.getInstance().level.registryAccess()))
                .setBackground(slotDrawable, -1, -1);

        builder.addSlot(RecipeIngredientRole.INPUT, 44, 24)
                .addIngredient(ForgeTypes.FLUID_STACK, new FluidStack(ModFluids.MOLTEN_FUEL.get(), recipe.getFluidAmount()))
                .setFluidRenderer(6400, true, 14, 46);
    }

    @Override
    public void draw(BlastFurnaceRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(METER_BOTTOM, 43, 55, 0, 0, 16, 16, 16, 16);
        guiGraphics.blit(METER, 43, 39, 0, 0, 16, 16, 16, 16);
        guiGraphics.blit(METER_TOP, 43, 23, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/meterstick.png"), 24, 37, 0, 0, 16, 16, 16, 16);
        guiGraphics.blit(new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/meterstickup.png"), 80, 40, 0, 0, 16, 16, 16, 16);

        guiGraphics.blit(ARROW, 107, 39, 0, 0, 16, 16, 16, 16);
        guiGraphics.blit(SMELT, 61, 38, 0, 0, 16, 16, 16, 16);
    }
}
