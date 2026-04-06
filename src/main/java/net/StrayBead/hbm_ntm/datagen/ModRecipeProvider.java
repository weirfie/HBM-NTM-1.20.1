package net.StrayBead.hbm_ntm.datagen;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> BERYLLIUM_SMELTABLES = List.of(ModItems.RAW_BERYLLIUM.get());
    private static final List<ItemLike> URANIUM_SMELTABLES = List.of(ModBlocks.URANIUM_ORE.get());
    private static final List<ItemLike> TITANIUM_SMELTABLES = List.of(ModBlocks.TITANIUM_ORE.get());
    private static final List<ItemLike> RED_COPPER_SMELTABLES = List.of(Items.COPPER_INGOT);
    private static final List<ItemLike> RED_COOPER_SMELTABLES = List.of(ModItems.REDCOPPER.get());
    private static final List<ItemLike> TUNGSTEN_ORE_SMELTABLES = List.of(ModBlocks.TUNGSTEN_ORE.get());
    private static final List<ItemLike> LEAD_ORE_SMELTABLES = List.of(ModBlocks.LEAD_ORE.get());
    private static final List<ItemLike> FIRECLAY_SMELTABLES = List.of(ModItems.FIRECLAY.get());
    private static final List<ItemLike> SCHRABIDIUM_ORE_SMELTABLES = List.of(ModBlocks.SCHRABIDIUM_ORE.get());
    private static final List<ItemLike> ALUMINUM_ORE_SMELTABLES = List.of(ModBlocks.ALUMINUM_ORE.get());
    private static final List<ItemLike> FLINT_SMELTABLES = List.of(Items.FLINT);

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        oreSmelting(pWriter, BERYLLIUM_SMELTABLES, RecipeCategory.MISC, ModItems.BERYLLIUM_INGOT.get(), 0.25f, 100, "beryllium");
        oreBlasting(pWriter, BERYLLIUM_SMELTABLES, RecipeCategory.MISC, ModItems.BERYLLIUM_INGOT.get(), 0.25f, 100, "beryllium");
        oreSmelting(pWriter, URANIUM_SMELTABLES, RecipeCategory.MISC, ModItems.URANIUM_BEDROCK_ORE.get(), 0.25f, 100, "uranium");
        oreBlasting(pWriter, URANIUM_SMELTABLES, RecipeCategory.MISC, ModItems.URANIUM_BEDROCK_ORE.get(), 0.25f, 100, "uranium");
        oreSmelting(pWriter, TITANIUM_SMELTABLES, RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 0.25f, 100, "titanium");
        oreBlasting(pWriter, TITANIUM_SMELTABLES, RecipeCategory.MISC, ModItems.TITANIUM_INGOT.get(), 0.25f, 100, "titanium");
        oreSmelting(pWriter, RED_COPPER_SMELTABLES, RecipeCategory.MISC, ModItems.REDCOPPER.get(), 0.25f, 100, "red_copper");
        oreSmelting(pWriter, RED_COOPER_SMELTABLES, RecipeCategory.MISC, ModItems.INDUSTRIAL_GRADE_COPPER.get(), 0.25f, 100, "copper");
        oreBlasting(pWriter, FLINT_SMELTABLES, RecipeCategory.MISC, ModItems.SILICON_NUGGET.get(), 0.25f, 400, "silicon_nugget");
        oreSmelting(pWriter, TUNGSTEN_ORE_SMELTABLES, RecipeCategory.MISC, ModItems.TUNGSTEN_INGOT.get(), 0.25f, 100, "tungsten");
        oreSmelting(pWriter, LEAD_ORE_SMELTABLES, RecipeCategory.MISC, ModItems.LEAD_INGOT.get(), 0.25f, 100, "lead");
        oreSmelting(pWriter, FIRECLAY_SMELTABLES, RecipeCategory.MISC, ModItems.FIREBRICK.get(), 0.25f, 100, "fireclay");
        oreSmelting(pWriter, SCHRABIDIUM_ORE_SMELTABLES, RecipeCategory.MISC, ModItems.SCHRABIDIUM_INGOT.get(), 0.25f, 150, "schrabidium_ingot");
        oreSmelting(pWriter, ALUMINUM_ORE_SMELTABLES, RecipeCategory.MISC, ModItems.CRYOLITE_CHUNK.get(), 0.25f, 150, "cryolite_chunk");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.REINFORCED_STEEL_SCAFFOLD.get())
                .pattern("SSS")
                .pattern(" S ")
                .pattern("SSS")
                .define('S', ModItems.STEEL.get())
                .unlockedBy(getHasName(ModItems.STEEL.get()), has(ModItems.STEEL.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.STEEL_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.STEEL.get())
                .unlockedBy(getHasName(ModItems.STEEL.get()), has(ModItems.STEEL.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RED_COPPER_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.MINECRAFT_GRADE_COPPER.get())
                .unlockedBy(getHasName(ModItems.MINECRAFT_GRADE_COPPER.get()), has(ModItems.MINECRAFT_GRADE_COPPER.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ADVANCED_ALLOY_INGOT.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.MINECRAFT_GRADE_COPPER_WIRE.get())
                .unlockedBy(getHasName(ModItems.MINECRAFT_GRADE_COPPER_WIRE.get()), has(ModItems.MINECRAFT_GRADE_COPPER_WIRE.get()))
                .save(pWriter, new ResourceLocation(HBMNTM.MOD_ID, "advanced_alloy_from_grade_copper_wire"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ADVANCED_ALLOY_INGOT.get(), 9)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModBlocks.RED_COPPER_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.RED_COPPER_BLOCK.get()), has(ModBlocks.RED_COPPER_BLOCK.get()))
                .save(pWriter, new ResourceLocation(HBMNTM.MOD_ID, "advanced_alloy_from_red_copper_block"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STEEL.get(), 9)
                .pattern("S  ")
                .pattern("   ")
                .pattern("   ")
                .define('S', ModBlocks.STEEL_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.STEEL_BLOCK.get()), has(ModBlocks.STEEL_BLOCK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MACHINE_TEMPLATE_FOLDER.get())
                .pattern("SPS")
                .pattern("SPS")
                .pattern("SPS")
                .define('S', Tags.Items.DYES)
                .define('P', Items.PAPER)
                .unlockedBy(getHasName(Items.PAPER), has(Items.PAPER))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CLAY_CATALYST.get())
                .pattern("IC ")
                .pattern("   ")
                .pattern("   ")
                .define('I', ModItems.IRON_POWDER.get())
                .define('C', Items.CLAY)
                .unlockedBy(getHasName(ModItems.IRON_POWDER.get()), has(ModItems.IRON_POWDER.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STEEL_SHREDDER_BLADES.get())
                .pattern(" P ")
                .pattern("PSP")
                .pattern(" P ")
                .define('P', ModItems.STEEL_PLATE.get())
                .define('S', ModItems.STEEL.get())
                .unlockedBy(getHasName(ModItems.STEEL_PLATE.get()), has(ModItems.STEEL_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MICROCHIP.get())
                .pattern("I  ")
                .pattern("W  ")
                .pattern("C  ")
                .define('I', ModItems.INSULATOR.get())
                .define('W', ModItems.PRINTED_SILICON_WAFER.get())
                .define('C', ModItems.COPPER_WIRE.get())
                .unlockedBy(getHasName(ModItems.PRINTED_SILICON_WAFER.get()), has(ModItems.PRINTED_SILICON_WAFER.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.OIL_RESERVOIR_DETECTOR.get())
                .pattern("G I")
                .pattern("GAI")
                .pattern("SSS")
                .define('A', ModItems.ANALOG_CIRCUIT_BOARD.get())
                .define('S', ModItems.STEEL_PLATE.get())
                .define('G', ModItems.GOLD_WIRE.get())
                .define('I', ModItems.INDUSTRIAL_GRADE_COPPER.get())
                .unlockedBy(getHasName(ModItems.INDUSTRIAL_GRADE_COPPER.get()), has(ModItems.INDUSTRIAL_GRADE_COPPER.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COPPER_CABLE.get(), 16)
                .pattern(" I ")
                .pattern("WWW")
                .pattern(" I ")
                .define('I', ModItems.INSULATOR.get())
                .define('W', ModItems.MINECRAFT_GRADE_COPPER_WIRE.get())
                .unlockedBy(getHasName(ModItems.INSULATOR.get()), has(ModItems.INSULATOR.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TUNGSTEN_BOLT.get(), 16)
                .pattern("T  ")
                .pattern("T  ")
                .pattern("   ")
                .define('T', ModItems.TUNGSTEN_INGOT.get())
                .unlockedBy(getHasName(ModItems.TUNGSTEN_INGOT.get()), has(ModItems.TUNGSTEN_INGOT.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STEEL_BOLT.get(), 16)
                .pattern("T  ")
                .pattern("T  ")
                .pattern("   ")
                .define('T', ModItems.STEEL.get())
                .unlockedBy(getHasName(ModItems.STEEL.get()), has(ModItems.STEEL.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BLANK_FOUNDRY_MOLD.get())
                .pattern(" B ")
                .pattern("BIB")
                .pattern(" B ")
                .define('B', ModItems.FIREBRICK.get())
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.FIREBRICK.get()), has(ModItems.FIREBRICK.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CAST_PLATE_FOUNDRY_MOLD.get())
                .pattern("CB ")
                .pattern("   ")
                .pattern("   ")
                .define('C', ModItems.CAST_IRON_PLATE.get())
                .define('B', ModItems.BLANK_FOUNDRY_MOLD.get())
                .unlockedBy(getHasName(ModItems.CAST_IRON_PLATE.get()), has(ModItems.CAST_IRON_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SHELL_FOUNDRY_MOLD.get())
                .pattern("SB ")
                .pattern("   ")
                .pattern("   ")
                .define('S', ModItems.STEEL_SHELL.get())
                .define('B', ModItems.BLANK_FOUNDRY_MOLD.get())
                .unlockedBy(getHasName(ModItems.STEEL_SHELL.get()), has(ModItems.STEEL_SHELL.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PLATE_FOUNDRY_MOLD.get())
                .pattern("IB ")
                .pattern("   ")
                .pattern("   ")
                .define('I', ModItems.IRON_PLATE.get())
                .define('B', ModItems.BLANK_FOUNDRY_MOLD.get())
                .unlockedBy(getHasName(ModItems.IRON_PLATE.get()), has(ModItems.IRON_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WIRE_FOUNDRY_MOLD.get())
                .pattern("IB ")
                .pattern("   ")
                .pattern("   ")
                .define('I', ModItems.COPPER_WIRE.get())
                .define('B', ModItems.BLANK_FOUNDRY_MOLD.get())
                .unlockedBy(getHasName(ModItems.COPPER_WIRE.get()), has(ModItems.COPPER_WIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PIPE_FOUNDRY_MOLD.get())
                .pattern("IB ")
                .pattern("   ")
                .pattern("   ")
                .define('I', ModItems.STEEL_PIPE.get())
                .define('B', ModItems.BLANK_FOUNDRY_MOLD.get())
                .unlockedBy(getHasName(ModItems.STEEL_PIPE.get()), has(ModItems.STEEL_PIPE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NUGGET_FOUNDRY_MOLD.get())
                .pattern("IB ")
                .pattern("   ")
                .pattern("   ")
                .define('I', Items.GOLD_NUGGET)
                .define('B', ModItems.BLANK_FOUNDRY_MOLD.get())
                .unlockedBy(getHasName(Items.GOLD_NUGGET), has(Items.GOLD_NUGGET))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INGOT_FOUNDRY_MOLD.get())
                .pattern("IB ")
                .pattern("   ")
                .pattern("   ")
                .define('I', Items.IRON_INGOT)
                .define('B', ModItems.BLANK_FOUNDRY_MOLD.get())
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CAST_IRON_PLATE.get())
                .pattern("SBS")
                .pattern("SBS")
                .pattern("SBS")
                .define('B', ModItems.IRON_PLATE.get())
                .define('S', ModItems.STEEL_BOLT.get())
                .unlockedBy(getHasName(ModItems.IRON_PLATE.get()), has(ModItems.IRON_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SILICON_WAFER.get())
                .pattern("NNN")
                .pattern("NNN")
                .pattern("   ")
                .define('N', ModItems.SILICON_NUGGET.get())
                .unlockedBy(getHasName(ModItems.SILICON_NUGGET.get()), has(ModItems.SILICON_NUGGET.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SILICON_NUGGET.get(), 9)
                .pattern("B  ")
                .pattern("   ")
                .pattern("   ")
                .define('B', ModItems.SILICON_BOULE.get())
                .unlockedBy(getHasName(ModItems.SILICON_BOULE.get()), has(ModItems.SILICON_BOULE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RING_COIL.get())
                .pattern("SSS")
                .pattern("SPS")
                .pattern("SSS")
                .define('S', ModItems.COPPER_COIL.get())
                .define('P', ModItems.STEEL_PLATE.get())
                .unlockedBy(getHasName(ModItems.COPPER_COIL.get()), has(ModItems.COPPER_COIL.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CABLE_DRUM.get())
                .pattern("SSS")
                .pattern("SPS")
                .pattern("SSS")
                .define('S', ModItems.STEEL_PLATE.get())
                .define('P', ModItems.STEEL.get())
                .unlockedBy(getHasName(ModItems.STEEL_PLATE.get()), has(ModItems.STEEL_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.QUARTZ_SAND.get())
                .pattern("SS ")
                .pattern("PP ")
                .pattern("   ")
                .define('S', Blocks.SAND)
                .define('P', ModItems.QUARTZ_POWDER.get())
                .unlockedBy(getHasName(ModItems.QUARTZ_POWDER.get()), has(ModItems.QUARTZ_POWDER.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FIREBRICKS.get())
                .pattern("CC ")
                .pattern("CA ")
                .pattern("   ")
                .define('C', Items.CLAY)
                .define('A', ModBlocks.TUNGSTEN_ORE.get())
                .unlockedBy(getHasName(Items.CLAY), has(Items.CLAY))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ADVANCED_ALLOY_PICKAXE.get())
                .pattern("AAA")
                .pattern(" S ")
                .pattern(" S ")
                .define('A', ModItems.ADVANCED_ALLOY_INGOT.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.ADVANCED_ALLOY_INGOT.get()), has(ModItems.ADVANCED_ALLOY_INGOT.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TITANIUM_PICKAXE.get())
                .pattern("AAA")
                .pattern(" S ")
                .pattern(" S ")
                .define('A', ModItems.TITANIUM_INGOT.get())
                .define('S', Items.STICK)
                .unlockedBy(getHasName(ModItems.TITANIUM_INGOT.get()), has(ModItems.TITANIUM_INGOT.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STEEL_PICKAXE.get())
                .pattern("SSS")
                .pattern(" T ")
                .pattern(" T ")
                .define('S', ModItems.STEEL.get())
                .define('T', Items.STICK)
                .unlockedBy(getHasName(ModItems.STEEL.get()), has(ModItems.STEEL.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SCHRABIDIUM_BLOCK.get())
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .define('S', ModItems.SCHRABIDIUM_INGOT.get())
                .unlockedBy(getHasName(ModItems.SCHRABIDIUM_INGOT.get()), has(ModItems.SCHRABIDIUM_INGOT.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_POWDER.get())
                .pattern("I  ")
                .pattern("   ")
                .pattern("   ")
                .define('I', Items.IRON_NUGGET)
                .unlockedBy(getHasName(Items.IRON_NUGGET), has(Items.IRON_NUGGET))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GRAPHITE_MODERATOR.get())
                .pattern(" G ")
                .pattern("GPG")
                .pattern(" G ")
                .define('G', ModBlocks.BLOCK_OF_GRAPHITE.get())
                .define('P', ModBlocks.STRUCTURAL_COLUMN.get())
                .unlockedBy(getHasName(ModBlocks.STRUCTURAL_COLUMN.get()), has(ModBlocks.STRUCTURAL_COLUMN.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FUEL_ROD.get())
                .pattern("G  ")
                .pattern("F  ")
                .pattern("G  ")
                .define('G', ModItems.STEEL_SHELL.get())
                .define('F', ModBlocks.STRUCTURAL_COLUMN.get())
                .unlockedBy(getHasName(ModItems.STEEL_SHELL.get()), has(ModItems.STEEL_SHELL.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NUKE_DETONATOR.get())
                .pattern("G  ")
                .pattern("F  ")
                .pattern("   ")
                .define('G', ModItems.ANALOG_CIRCUIT_BOARD.get())
                .define('F', ModItems.STEEL_PLATE.get())
                .unlockedBy(getHasName(ModItems.STEEL_PLATE.get()), has(ModItems.STEEL_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.COPPER_COIL.get())
                .pattern("SSS")
                .pattern("SPS")
                .pattern("SSS")
                .define('S', ModItems.MINECRAFT_GRADE_COPPER_WIRE.get())
                .define('P', Items.IRON_INGOT)
                .unlockedBy(getHasName(ModItems.MINECRAFT_GRADE_COPPER_WIRE.get()), has(ModItems.MINECRAFT_GRADE_COPPER_WIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STEEL_SPHERE.get())
                .pattern("SPS")
                .pattern("P P")
                .pattern("SPS")
                .define('S', ModItems.STEEL_PLATE.get())
                .define('P', ModItems.STEEL.get())
                .unlockedBy(getHasName(ModItems.STEEL_PLATE.get()), has(ModItems.STEEL_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLAT_STEEL_CASING.get())
                .pattern("PS ")
                .pattern("SS ")
                .pattern("PS ")
                .define('S', ModItems.STEEL_PLATE.get())
                .define('P', ModItems.STEEL.get())
                .unlockedBy(getHasName(ModItems.STEEL_PLATE.get()), has(ModItems.STEEL_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STEEL_PEDESTAL.get())
                .pattern("S S")
                .pattern("S S")
                .pattern("PPP")
                .define('S', ModItems.STEEL_PLATE.get())
                .define('P', ModItems.STEEL.get())
                .unlockedBy(getHasName(ModItems.STEEL_PLATE.get()), has(ModItems.STEEL_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INFINITE_WATER_TANK.get())
                .pattern("AAA")
                .pattern("WDW")
                .pattern("AAA")
                .define('W', Items.WATER_BUCKET)
                .define('D', Items.DIAMOND)
                .define('A', ModItems.ALUMINUM_PLATE.get())
                .unlockedBy(getHasName(ModItems.ALUMINUM_PLATE.get()), has(ModItems.ALUMINUM_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CONCRETE_BRICKS.get())
                .pattern(" C ")
                .pattern("CBC")
                .pattern(" C ")
                .define('C', Blocks.LIGHT_GRAY_CONCRETE)
                .define('B', Items.CLAY)
                .unlockedBy(getHasName(Items.CLAY), has(Items.CLAY))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STEEL_PIPE.get())
                .pattern(" P ")
                .pattern(" P ")
                .pattern(" P ")
                .define('P', ModItems.STEEL_PLATE.get())
                .unlockedBy(getHasName(ModItems.STEEL_PLATE.get()), has(ModItems.STEEL_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FLAT_STAMP.get())
                .pattern("BBB")
                .pattern("SSS")
                .pattern("   ")
                .define('B', Items.BRICK)
                .define('S', Items.STONE)
                .unlockedBy(getHasName(Items.BRICK), has(Items.BRICK))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.INSULATOR.get(), 4)
                .pattern("SWS")
                .pattern("   ")
                .pattern("   ")
                .define('S', Items.STRING)
                .define('W', ItemTags.WOOL)
                .unlockedBy(getHasName(Items.STRING), has(Items.STRING))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CONVEYOR_BELT.get(), 16)
                .pattern("LLL")
                .pattern("I I")
                .pattern("LLL")
                .define('L', Items.LEATHER)
                .define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CONVEYOR_INSERTER.get())
                .pattern("LLL")
                .pattern("L L")
                .pattern("LIL")
                .define('L', ItemTags.STONE_BRICKS)
                .define('I', ModBlocks.CONVEYOR_BELT.get())
                .unlockedBy(getHasName(ModBlocks.CONVEYOR_BELT.get()), has(ModBlocks.CONVEYOR_BELT.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.UNIVERSAL_FLUID_DUCT.get(), 8)
                .pattern("ASA")
                .pattern("   ")
                .pattern("ASA")
                .define('A', ModItems.ALUMINUM_PLATE.get())
                .define('S', ModItems.STEEL_PLATE.get())
                .unlockedBy(getHasName(ModItems.ALUMINUM_PLATE.get()), has(ModItems.ALUMINUM_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DRAINAGE_PIPE.get())
                .pattern("CCC")
                .pattern("T  ")
                .pattern("CCC")
                .define('C', ModItems.CAST_STEEL_PLATE.get())
                .define('T', ModItems.STEEL_TANK.get())
                .unlockedBy(getHasName(ModItems.CAST_STEEL_PLATE.get()), has(ModItems.CAST_STEEL_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.WOOD_BURNING_GENERATOR.get())
                .pattern("SSS")
                .pattern("CFC")
                .pattern("I I")
                .define('I', Items.IRON_INGOT)
                .define('F', Blocks.FURNACE)
                .define('C', ModItems.COPPER_COIL.get())
                .define('S', ModItems.STEEL_PLATE.get())
                .unlockedBy(getHasName(ModItems.COPPER_COIL.get()), has(ModItems.COPPER_COIL.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DESHREADY_BLEND.get())
                .pattern("DM ")
                .pattern("MC ")
                .pattern("   ")
                .define('D', ModItems.DESH_BLEND.get())
                .define('M', ModItems.DROP_OF_MERCURY.get())
                .define('C', ModItems.COAL_POWDER.get())
                .unlockedBy(getHasName(ModItems.DESH_BLEND.get()), has(ModItems.DESH_BLEND.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BURNER_PRESS.get())
                .pattern("SFS")
                .pattern("SPS")
                .pattern("SIS")
                .define('S', Items.IRON_INGOT)
                .define('P', Items.PISTON)
                .define('F', Items.FURNACE)
                .define('I', Items.IRON_BLOCK)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.STEAM_CONDENSER.get())
                .pattern("SIS")
                .pattern("IPI")
                .pattern("SIS")
                .define('S', ModItems.STEEL.get())
                .define('P', ModItems.CAST_STEEL_PLATE.get())
                .define('I', ModItems.STEEL_PLATE.get())
                .unlockedBy(getHasName(ModItems.STEEL_PLATE.get()), has(ModItems.STEEL_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.STEAM_SEPARATOR.get())
                .pattern("SIS")
                .pattern("IPI")
                .pattern("SIS")
                .define('S', ModItems.STEEL_PLATE.get())
                .define('P', ModItems.STEEL_TANK.get())
                .define('I', ModItems.INDUSTRIAL_GRADE_COPPER.get())
                .unlockedBy(getHasName(ModItems.STEEL_PLATE.get()), has(ModItems.STEEL_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STEEL_TANK.get(), 2)
                .pattern("SIS")
                .pattern("S S")
                .pattern("SIS")
                .define('S', ModItems.STEEL_PLATE.get())
                .define('I', ModItems.TITANIUM_INGOT.get())
                .unlockedBy(getHasName(ModItems.STEEL_PLATE.get()), has(ModItems.STEEL_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.THE_GADGET.get())
                .pattern("SPO")
                .pattern("SGS")
                .pattern("LPN")
                .define('G', Items.GRAY_DYE)
                .define('P', ModItems.STEEL_SPHERE.get())
                .define('L', ModItems.MILITARY_GRADE_CIRCUIT_BOARD.get())
                .define('S', ModItems.FLAT_STEEL_CASING.get())
                .define('N', ModItems.GOLD_WIRE.get())
                .define('O', ModItems.STEEL_PEDESTAL.get())
                .unlockedBy(getHasName(Items.GRAY_DYE), has(Items.GRAY_DYE))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GEIGER_COUNTER.get())
                .pattern("OPR")
                .pattern("GIS")
                .pattern("GBB")
                .define('G', ModItems.GOLD_WIRE.get())
                .define('B', ModItems.BERYLLIUM_INGOT.get())
                .define('I', ModItems.INTEGRATED_CIRCUIT_BOARD.get())
                .define('R', ModItems.RUBBER_BAR.get())
                .define('P', ModItems.GOLD_WIRE.get())
                .define('O', Items.GOLD_INGOT)
                .define('S', Items.GOLD_INGOT)
                .unlockedBy(getHasName(Items.GRAY_DYE), has(Items.GRAY_DYE))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LEAD_ANVIL.get())
                .pattern("SSS")
                .pattern(" R ")
                .pattern("SSS")
                .define('S', Items.IRON_INGOT)
                .define('R', Blocks.IRON_BLOCK)
                .unlockedBy(getHasName(Items.IRON_INGOT), has(Items.IRON_INGOT))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SHALLOW_FOUNDRY_BASIN.get())
                .pattern("B B")
                .pattern("BSB")
                .pattern("   ")
                .define('S', Blocks.COBBLESTONE_SLAB)
                .define('B', ModItems.FIREBRICK.get())
                .unlockedBy(getHasName(Blocks.COBBLESTONE_SLAB), has(Blocks.COBBLESTONE_SLAB))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRECLAY.get())
                .pattern("CC ")
                .pattern("CP ")
                .pattern("   ")
                .define('C', Items.CLAY)
                .define('P', ModItems.STEEL.get())
                .unlockedBy(getHasName(ModItems.STEEL.get()), has(ModItems.STEEL.get()))
                .save(pWriter, new ResourceLocation(HBMNTM.MOD_ID, "fireclay_from_steel"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.FIRECLAY.get())
                .pattern("CC ")
                .pattern("CP ")
                .pattern("   ")
                .define('C', Items.CLAY)
                .define('P', ModBlocks.ALUMINUM_ORE.get())
                .unlockedBy(getHasName(ModBlocks.ALUMINUM_ORE.get()), has(ModBlocks.ALUMINUM_ORE.get()))
                .save(pWriter, new ResourceLocation(HBMNTM.MOD_ID, "fireclay_from_aluminum_ore"));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.VACUUM_TUBE.get())
                .pattern("S  ")
                .pattern("R  ")
                .pattern("B  ")
                .define('S', Blocks.GLASS_PANE)
                .define('R', ModItems.TUNGSTEN_WIRE.get())
                .define('B', ModItems.INSULATOR.get())
                .unlockedBy(getHasName(ModItems.TUNGSTEN_WIRE.get()), has(ModItems.TUNGSTEN_WIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WIRE_STAMP.get())
                .pattern("SBS")
                .pattern("RRR")
                .pattern("   ")
                .define('S', Items.IRON_INGOT)
                .define('R', Items.BRICK)
                .define('B', ModItems.FLAT_STAMP.get())
                .unlockedBy(getHasName(ModItems.FLAT_STAMP.get()), has(ModItems.FLAT_STAMP.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MOTOR.get())
                .pattern(" R ")
                .pattern("PRP")
                .pattern("PTP")
                .define('P', ModItems.STEEL_PLATE.get())
                .define('R', Items.REDSTONE)
                .define('T', Items.REDSTONE_TORCH)
                .unlockedBy(getHasName(ModItems.STEEL_PLATE.get()), has(ModItems.STEEL_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CAPACITOR.get())
                .pattern("IRI")
                .pattern("C C")
                .pattern("   ")
                .define('R', ModItems.IRON_POWDER.get())
                .define('I', ModItems.INSULATOR.get())
                .define('C', ModItems.COPPER_WIRE.get())
                .unlockedBy(getHasName(ModItems.COPPER_WIRE.get()), has(ModItems.COPPER_WIRE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.LEAD_BLOCK.get())
                .pattern("LLL")
                .pattern("LLL")
                .pattern("LLL")
                .define('L', ModItems.LEAD_INGOT.get())
                .unlockedBy(getHasName(ModItems.LEAD_INGOT.get()), has(ModItems.LEAD_INGOT.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PRINTED_CIRCUIT_BOARD.get())
                .pattern(" I ")
                .pattern(" C ")
                .pattern("   ")
                .define('I', ModItems.INSULATOR.get())
                .define('C', ModItems.COPPER_PLATE.get())
                .unlockedBy(getHasName(ModItems.COPPER_PLATE.get()), has(ModItems.COPPER_PLATE.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.COMBUSTION_GENERATOR.get())
                .pattern("STS")
                .pattern("SCS")
                .pattern("SFS")
                .define('S', ModItems.STEEL.get())
                .define('T', Items.IRON_INGOT)
                .define('C', Items.COPPER_INGOT)
                .define('F', Items.FURNACE)
                .unlockedBy(getHasName(ModItems.STEEL.get()), has(ModItems.STEEL.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.RADIATION_ABSORBER.get())
                .pattern("COC")
                .pattern("OLO")
                .pattern("COC")
                .define('L', ModItems.LEAD_DUST.get())
                .define('C', Items.COPPER_INGOT)
                .define('O', Items.COAL)
                .unlockedBy(getHasName(ModItems.LEAD_DUST.get()), has(ModItems.LEAD_DUST.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ENHANCED_RADIATION_ABSORBER.get())
                .pattern("COC")
                .pattern("ORO")
                .pattern("COC")
                .define('R', ModBlocks.RADIATION_ABSORBER.get())
                .define('C', ModItems.TITANIUM_INGOT.get())
                .define('O', Items.COAL)
                .unlockedBy(getHasName(ModBlocks.RADIATION_ABSORBER.get()), has(ModBlocks.RADIATION_ABSORBER.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ADVANCED_RADIATION_ABSORBER.get())
                .pattern("COC")
                .pattern("ORO")
                .pattern("COC")
                .define('R', ModBlocks.ENHANCED_RADIATION_ABSORBER.get())
                .define('C', ModItems.STEEL.get())
                .define('O', Items.REDSTONE)
                .unlockedBy(getHasName(ModBlocks.ENHANCED_RADIATION_ABSORBER.get()), has(ModBlocks.ENHANCED_RADIATION_ABSORBER.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.PLAYER_DECONTAMINATOR.get())
                .pattern("COC")
                .pattern("CRC")
                .pattern("CCC")
                .define('R', ModBlocks.RADIATION_ABSORBER.get())
                .define('C', ModItems.BERYLLIUM_INGOT.get())
                .define('O', Items.IRON_BARS)
                .unlockedBy(getHasName(ModBlocks.RADIATION_ABSORBER.get()), has(ModBlocks.RADIATION_ABSORBER.get()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ELITE_RADIATION_ABSORBER.get())
                .pattern("COC")
                .pattern("ORO")
                .pattern("COC")
                .define('R', ModBlocks.ADVANCED_RADIATION_ABSORBER.get())
                .define('C', ModItems.REDCOPPER.get())
                .define('O', Items.GLOWSTONE_DUST)
                .unlockedBy(getHasName(ModBlocks.ADVANCED_RADIATION_ABSORBER.get()), has(ModBlocks.ADVANCED_RADIATION_ABSORBER.get()))
                .save(pWriter);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> pFinishedRecipeConsumer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(pFinishedRecipeConsumer, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> pFinishedRecipeConsumer, RecipeSerializer<? extends AbstractCookingRecipe> pCookingSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult,
                            pExperience, pCookingTime, pCookingSerializer)
                    .group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(pFinishedRecipeConsumer,  HBMNTM.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
