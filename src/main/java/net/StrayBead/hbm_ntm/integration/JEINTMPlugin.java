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
    public static RecipeType<ShredderRecipe> SHREDDER_TYPE = ShredderRecipeCategory.TYPE;
    public static RecipeType<ArcFurnaceRecipe> ARC_FURNACE_TYPE = ArcFurnaceRecipeCategory.TYPE;
    public static RecipeType<ChemicalPlantRecipe> CHEMICAL_PLANT_TYPE = ChemicalPlantRecipeCategory.TYPE;

    public static RecipeType<CentrifugeRecipe> CENTRIFUGE_TYPE = CentrifugeRecipeCategory.TYPE;

    public static RecipeType<AssemblyRecipe> ASSEMBLY_TYPE =
            new RecipeType<>(new ResourceLocation(HBMNTM.MOD_ID, "assembly_machine"), AssemblyRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(HBMNTM.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new CentrifugeRecipeCategory(helper),
                new ChemicalPlantRecipeCategory(helper),
                new SolderingRecipeCategory(helper),
                new WeldingRecipeCategory(helper),
                new BasinCastingRecipeCategory(helper),
                new BlastFurnaceRecipeCategory(helper),
                new BurnerPressRecipeCategory(helper),
                new AssemblyRecipeCategory(helper),
                new ShredderRecipeCategory(helper),
                new ArcFurnaceRecipeCategory(helper),
                new AnvilButtonClickRecipeCategory(helper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        registration.addRecipes(CENTRIFUGE_TYPE, rm.getAllRecipesFor(CentrifugeRecipe.Type.INSTANCE));
        registration.addRecipes(ARC_FURNACE_TYPE, rm.getAllRecipesFor(ModRecipes.ARC_FURNACE_TYPE.get()));
        registration.addRecipes(CHEMICAL_PLANT_TYPE, rm.getAllRecipesFor(ModRecipes.CHEMICAL_PLANT_TYPE.get()));

        registration.addRecipes(PRESS_TYPE, rm.getAllRecipesFor(BurnerPressRecipe.Type.INSTANCE));
        registration.addRecipes(ASSEMBLY_TYPE, rm.getAllRecipesFor(ModRecipes.ASSEMBLY_TYPE.get()));
        registration.addRecipes(SHREDDER_TYPE, rm.getAllRecipesFor(ModRecipes.SHREDDER_TYPE.get()));
        registration.addRecipes(ANVIL_TYPE, rm.getAllRecipesFor(ModRecipes.ANVIL_TYPE.get()));
        registration.addRecipes(SolderingRecipeCategory.SOLDERING_TYPE, rm.getAllRecipesFor(SolderingStationRecipe.Type.INSTANCE));
        registration.addRecipes(WeldingRecipeCategory.WELDING_TYPE, rm.getAllRecipesFor(ArcWelderRecipe.Type.INSTANCE));
        registration.addRecipes(BASIN_CASTING_TYPE, rm.getAllRecipesFor(BasinCastingRecipe.Type.INSTANCE));
        registration.addRecipes(BLAST_FURNACE_TYPE, rm.getAllRecipesFor(ModRecipes.BLAST_FURNACE_TYPE.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CENTRIFUGE.get()), CENTRIFUGE_TYPE);

        registration.addRecipeCatalyst(new ItemStack(ModBlocks.CHEMICAL_PLANT.get()), CHEMICAL_PLANT_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ELECTRIC_ARC_FURNACE.get()), ARC_FURNACE_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.BURNER_PRESS.get()), PRESS_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ASSEMBLY_MACHINE.get()), ASSEMBLY_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SHREDDER.get()), SHREDDER_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.LEAD_ANVIL.get()), ANVIL_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SOLDERING_STATION.get()), SOLDERING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.ARC_WELDER.get()), WELDING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.SHALLOW_FOUNDRY_BASIN.get()), BASIN_CASTING_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.BLAST_FURNACE.get()), BLAST_FURNACE_TYPE);
    }
}
