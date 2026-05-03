package net.StrayBead.hbm_ntm.recipe;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, HBMNTM.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, HBMNTM.MOD_ID);

    public static final RegistryObject<RecipeSerializer<BlastFurnaceRecipe>> BLAST_FURNACE_SERIALIZER =
            SERIALIZERS.register("blast_furnace", () -> BlastFurnaceRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<BlastFurnaceRecipe>> BLAST_FURNACE_TYPE =
            TYPES.register("blast_furnace", () -> new RecipeType<BlastFurnaceRecipe>() {
                @Override
                public String toString() {
                    return HBMNTM.MOD_ID + ":blast_furnace";
                }
            });

    public static final RegistryObject<RecipeSerializer<ChemicalPlantRecipe>> CHEMICAL_PLANT_SERIALIZER =
            SERIALIZERS.register("chemical_plant", () -> ChemicalPlantRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<ChemicalPlantRecipe>> CHEMICAL_PLANT_TYPE =
            TYPES.register("chemical_plant", () -> new RecipeType<ChemicalPlantRecipe>() {
                @Override public String toString() { return HBMNTM.MOD_ID + ":chemical_plant"; }
            });

    public static final RegistryObject<RecipeSerializer<ArcFurnaceRecipe>> ARC_FURNACE_SERIALIZER =
            SERIALIZERS.register("arc_furnace", () -> ArcFurnaceRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<ArcFurnaceRecipe>> ARC_FURNACE_TYPE =
            TYPES.register("arc_furnace", () -> new RecipeType<ArcFurnaceRecipe>() {
                @Override
                public String toString() {
                    return HBMNTM.MOD_ID + ":arc_furnace";
                }
            });

    public static final RegistryObject<RecipeSerializer<ShredderRecipe>> SHREDDER_SERIALIZER =
            SERIALIZERS.register("shredding", () -> ShredderRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<ShredderRecipe>> SHREDDER_TYPE =
            TYPES.register("shredding", () -> new RecipeType<ShredderRecipe>() {
                @Override
                public String toString() {
                    return HBMNTM.MOD_ID + ":shredding";
                }
            });

    public static final RegistryObject<RecipeSerializer<BurnerPressRecipe>> BURNER_PRESS_SERIALIZER =
            SERIALIZERS.register("burner_press", () -> BurnerPressRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<BurnerPressRecipe>> BURNER_PRESS_TYPE =
            TYPES.register("burner_press", () -> new RecipeType<BurnerPressRecipe>() {
                @Override
                public String toString() {
                    return HBMNTM.MOD_ID + ":burner_press";
                }
            });

    public static final RegistryObject<RecipeSerializer<BasinCastingRecipe>> BASIN_CASTING_SERIALIZER =
            SERIALIZERS.register("basin_casting", () -> BasinCastingRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<BasinCastingRecipe>> BASIN_CASTING_TYPE =
            TYPES.register("basin_casting", () -> new RecipeType<BasinCastingRecipe>() {
                @Override
                public String toString() {
                    return HBMNTM.MOD_ID + ":basin_casting";
                }
            });

    public static final RegistryObject<RecipeSerializer<AnvilBlockRecipe>> ANVIL_SERIALIZER =
            SERIALIZERS.register("anvil_crafting", () -> AnvilBlockRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<AnvilBlockRecipe>> ANVIL_TYPE =
            TYPES.register("anvil_crafting", () -> new RecipeType<AnvilBlockRecipe>() {
                @Override
                public String toString() {
                    return HBMNTM.MOD_ID + ":anvil_crafting";
                }
            });

    public static final RegistryObject<RecipeSerializer<SolderingStationRecipe>> SOLDERING_STATION_SERIALIZER =
            SERIALIZERS.register("soldering", () -> SolderingStationRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<CentrifugeRecipe>> CENTRIFUGE_SERIALIZER =
            SERIALIZERS.register("centrifuge", () -> CentrifugeRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<SolderingStationRecipe>> SOLDERING_STATION_TYPE =
            TYPES.register("soldering", () -> new RecipeType<SolderingStationRecipe>() {
                @Override
                public String toString() {
                    return HBMNTM.MOD_ID + ":soldering";
                }
            });

    public static final RegistryObject<RecipeSerializer<ArcWelderRecipe>> ARC_WELDER_SERIALIZER =
            SERIALIZERS.register("welding", () -> ArcWelderRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<ArcWelderRecipe>> ARC_WELDER_TYPE =
            TYPES.register("welding", () -> new RecipeType<ArcWelderRecipe>() {
                @Override
                public String toString() {
                    return HBMNTM.MOD_ID + ":welding";
                }
            });

    public static final RegistryObject<RecipeType<CentrifugeRecipe>> CENTRIFUGE_TYPE =
            TYPES.register("centrifuge", () -> new RecipeType<CentrifugeRecipe>() {
                @Override
                public String toString() { return HBMNTM.MOD_ID + ":centrifuge"; }
            });

    public static final RegistryObject<RecipeSerializer<AssemblyRecipe>> ASSEMBLY_SERIALIZER =
            SERIALIZERS.register("assembly_machine", () -> AssemblyRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeType<AssemblyRecipe>> ASSEMBLY_TYPE =
            TYPES.register("assembly_machine", () -> new RecipeType<AssemblyRecipe>() {
                @Override
                public String toString() {
                    return "hbm_ntm:assembly_machine";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
