package net.StrayBead.hbm_ntm.integration;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.recipe.*;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;
import java.util.Objects;

@JeiPlugin
public class JEINTMPlugin implements IModPlugin {
    public static RecipeType<BurnerPressRecipe> PRESS_TYPE =
            new RecipeType<>(BurnerPressRecipeCategory.UID, BurnerPressRecipe.class);
    public static RecipeType<AnvilBlockRecipe> ANVIL_TYPE = AnvilButtonClickRecipeCategory.TYPE;
    public static RecipeType<SolderingStationRecipe> SOLDERING_TYPE = SolderingRecipeCategory.SOLDERING_TYPE;
    public static RecipeType<ArcWelderRecipe> WELDING_TYPE = WeldingRecipeCategory.WELDING_TYPE;
    public static RecipeType<BasinCastingRecipe> BASIN_CASTING_TYPE = BasinCastingRecipeCategory.TYPE;
    public static RecipeType<BlastFurnaceRecipe> BLAST_FURNACE_TYPE = BlastFurnaceRecipeCategory.TYPE;

    public static RecipeType<AssemblyRecipe> ASSEMBLY_TYPE =
            new RecipeType<>(new ResourceLocation(HBMNTM.MOD_ID, "assembly_machine"), AssemblyRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(HBMNTM.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(new SolderingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new WeldingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new BasinCastingRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new BlastFurnaceRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(
                new BurnerPressRecipeCategory(helper),
                new AssemblyRecipeCategory(helper),
                new AnvilButtonClickRecipeCategory(helper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        List<BurnerPressRecipe> recipesPress = rm.getAllRecipesFor(BurnerPressRecipe.Type.INSTANCE);
        registration.addRecipes(PRESS_TYPE, recipesPress);
        registration.addRecipes(ASSEMBLY_TYPE, rm.getAllRecipesFor(ModRecipes.ASSEMBLY_TYPE.get()));
        registration.addRecipes(ANVIL_TYPE, rm.getAllRecipesFor(ModRecipes.ANVIL_TYPE.get()));
        List<SolderingStationRecipe> recipes = rm.getAllRecipesFor(SolderingStationRecipe.Type.INSTANCE);
        registration.addRecipes(SolderingRecipeCategory.SOLDERING_TYPE, recipes);
        List<ArcWelderRecipe> weldingRecipes = rm.getAllRecipesFor(ArcWelderRecipe.Type.INSTANCE);
        registration.addRecipes(WeldingRecipeCategory.WELDING_TYPE, weldingRecipes);
        List<BasinCastingRecipe> basinRecipes = rm.getAllRecipesFor(BasinCastingRecipe.Type.INSTANCE);
        registration.addRecipes(BASIN_CASTING_TYPE, basinRecipes);
        List<BlastFurnaceRecipe> blastFurnaceRecipes = rm.getAllRecipesFor(ModRecipes.BLAST_FURNACE_TYPE.get());
        registration.addRecipes(BLAST_FURNACE_TYPE, blastFurnaceRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.BURNER_PRESS.get()), PRESS_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ASSEMBLY_MACHINE.get()), ASSEMBLY_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.LEAD_ANVIL.get()), ANVIL_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SOLDERING_STATION.get()), SOLDERING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ARC_WELDER.get()), WELDING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SHALLOW_FOUNDRY_BASIN.get()), BASIN_CASTING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.BLAST_FURNACE.get()), BLAST_FURNACE_TYPE);
    }
}
