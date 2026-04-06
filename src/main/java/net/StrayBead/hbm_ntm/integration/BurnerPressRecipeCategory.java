package net.StrayBead.hbm_ntm.integration;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.recipe.BurnerPressRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

public class BurnerPressRecipeCategory implements IRecipeCategory<BurnerPressRecipe> {
    public final static ResourceLocation UID = new ResourceLocation(HBMNTM.MOD_ID, "burner_press");
    public final static ResourceLocation TEXTURE = new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/burner_press_gui.png");

    private final ResourceLocation ARROW_LOC = new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/arrow.png");
    private final ResourceLocation PRESS_LOC = new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/press.png");
    private final ResourceLocation FILLED_PRESS_LOC = new ResourceLocation(HBMNTM.MOD_ID, "textures/screens/filled_press.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;
    private final IDrawable arrow;
    private final IDrawable press;
    protected final IDrawableAnimated animatedPress;

    public BurnerPressRecipeCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.BURNER_PRESS.get()));
        this.slot = helper.getSlotDrawable();

        this.arrow = helper.drawableBuilder(ARROW_LOC, 0, 0, 16, 16)
                .setTextureSize(16, 16)
                .build();

        this.press = helper.drawableBuilder(PRESS_LOC, 0, 0, 16, 16)
                .setTextureSize(16, 16)
                .build();

        this.animatedPress = helper.drawableBuilder(FILLED_PRESS_LOC, 0, 0, 16, 16)
                .setTextureSize(16, 16)
                .buildAnimated(200, IDrawableAnimated.StartDirection.TOP, false);
    }

    @Override
    public void draw(BurnerPressRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        // 1. Draw the main background
        this.background.draw(guiGraphics);

        // 2. Draw the slots (since they aren't in your 176x85 crop area)
        this.slot.draw(guiGraphics, 79, 22);  // Input
        this.slot.draw(guiGraphics, 25, 60);  // Coal
        this.slot.draw(guiGraphics, 138, 40); // Output

        // 3. Draw the Arrow
        this.arrow.draw(guiGraphics, 111, 43);

        // 4. Draw the Press (Empty static one first)
        this.press.draw(guiGraphics, 80, 41);

        // 5. Draw the Animated Press on top
        this.animatedPress.draw(guiGraphics, 80, 41);
    }

    @Override
    public RecipeType<BurnerPressRecipe> getRecipeType() {
        return JEINTMPlugin.PRESS_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Burner Press");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BurnerPressRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 23)
                .addIngredients(recipe.getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.INPUT, 26, 61)
                .addItemStack(new ItemStack(Items.COAL));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 139, 41)
                .addItemStack(recipe.getResultItem(RegistryAccess.EMPTY))
                .setBackground(this.slot, -1, -1);
    }
}
