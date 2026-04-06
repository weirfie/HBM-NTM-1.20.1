package net.StrayBead.hbm_ntm.item;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.item.custom.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

public class ModItems {
    private static final String[] FLUID_NAMES = {
            "crude_oil", "deuterium", "tritium", "water", "steam", "heavy_water", "sulfuric_acid", "hot_steam", "liquid_oxygen",
            "liquid_hydrogen", "liquid_sodium", "mercury", "naphtha", "diesel", "kerosene", "petroil", "heavy_oil",
            "poisonous_mud", "cracked_oil", "desulfurized_cracked_oil", "coal_oil", "coker_oil", "hot_crude_oil", "desulfurized_hot_crude_oil",
            "vacuum_heavy_oil", "desulfurized_naphtha", "cracked_naphtha", "coker_naphtha", "reformate", "light_oil", "desulfurized_light_oil",
            "cracked_light_oil", "vacuum_light_oil", "bitumen", "industrial_oil", "heating_oil", "heavy_heating_oil", "reclaimed_industrial_oil",
            "natural_gas", "petroleum_gas", "sour_gas", "lpg", "syngas", "oxyhydrogen", "aromatic_hydrocarbons", "reformate_gas",
            "high_cetane_diesel", "cracked_diesel", "high_cetane_cracked_diesel", "jet_fuel", "gasoline", "leaded_gasoline",
            "coal_gasoline", "leaded_coal_gasoline", "coal_tar_creosote", "wood_oil", "biogas", "ethanol", "fish_oil", "sunflower_seed_oil",
            "salient_green", "seeding_slurry", "colloid", "ionic_gel", "hydrogen_peroxide", "nitric_acid", "solvent", "high_performance_solvent",
            "schrabidic_acid", "plutonium_hexaflouride", "schrabidium_trisulfide", "osmiridic_solution", "red_mud", "fullerene_solution",
            "dissolved_egg", "chlorocalcite_solution", "mixed_chlorocalcite_solution", "cleaned_chlorocalcite_solution", "potassiumchloride_solution",
            "calciumchloride_solution", "calcium_solution", "sodium_aluminate", "bauxite_solution", "lye", "fracking_solution",
            "phosgene", "mustard_gas", "estradiol_solution", "nitroglycerin", "antimatter", "antischrabidium", "liquid_nuclear_waste",
            "gaseous_nuclear_waste", "experience_juice", "ender_juice", "stellar_flux", "booster_pheromone", "modified_booster_pheromone", "custom_fluid_demo",
            "multi_fluid_identifier", "carbon_dioxide", "unsaturated_hydrocarbons"
    };

    private static final String[] ASSEMBLY_TEMPLATE_NAMES = {
            "iron_plate", "gold_plate", "titanium_plate", "aluminum_plate", "steel_plate", "lead_plate", "copper_plate", "advanced_alloy_plate",
            "schrabidium_plate", "cmb_steel_plate", "saturnite_plate", "mixed_plate", "hazmat_cloth", "fire_proximity_cloth",
            "activated_carbon_filter", "centrifuge_element", "breeding_reactor_core", "rtg_unit", "titanium_drill", "entanglement_kit",
            "dysfunctional_nuclear_reactor", "crucible", "firebox", "heating_oven", "small_missile_assembly", "small_warhead", "medium_warhead", "large_warhead", "small_incendiary_warhead",
            "medium_incendiary_warhead", "radioactive_barrel", "large_incendiary_warhead", "small_cluster_warhead", "medium_cluster_warhead",
            "large_cluster_warhead", "small_bunker_buster_warhead", "medium_bunker_buster_warhead", "large_bunker_buster_warhead",
            "nuclear_warhead", "soldering_station", "thermonuclear_warhead", "tectonic_warhead", "stealth_missile", "explosive_pellets", "lead_pellets",
            "magnetron", "redcoil_capacitor", "box_of_lithium_dust", "box_of_beryllium_dust", "box_of_carbon_dust", "box_of_copper_dust",
            "box_of_plutonium_dust", "thermoelectric_element", "angry_metal", "meteorite_block", "cmb_steel_tile", "reinforced_cmb_bricks",
            "silo_hatch_frame", "silo_hatch_opener", "centrifuge", "gas_centrifuge", "diesel_generator", "rt_generator", "energy_storage_block",
            "spark_energy_storage_block", "shredder", "oil_derrick", "electric_arc_furnace", "pumpjack", "flare_stock", "coker_unit", "oil_refinery", "electric_press",
            "chemical_plant", "ore_acidizer", "tank", "catalytic_cracking_tower", "silex", "heavy_magnetic_storage_tank", "mining_laser", "turbofan", "combined_cycle_gas_turbine",
            "teleporter", "schrabidium_transmutation_device", "big_ass_tank", "superconducting_magnet", "central_magnet_piece", "magnet_motor_piece",
            "plasma_heater_component", "watz_reaction_chamber", "watz_reactor_supercooler", "watz_reactor_stability_element", "naval_mine",
            "the_gadget", "ivy_mike", "tsar_bomba", "the_prototype", "the_blue_rinse", "rbmk_structural_column", "custom_nuke", "levitation_bomb", "endothermic_bomb",
            "exothermic_bomb", "flame_frag_grenade", "shrapnel_grenade", "cluster_bomb", "signal_flare", "lightning_bomb", "impulse_grenade",
            "plasma_grenade", "tau_grenade", "schrabidium_grenade", "boiler", "nuka_grenade", "negative_energy_pair_annihilation_grenade"
    };

    private static final String[] CHEMISTRY_TEMPLATE_NAMES = {
            "biogas_production", "biofuel_transesterification", "petroleum_gas_liquefaction", "tar_sand_extraction", "asphalt_production", "coolant_mixing",
            "cryogel_mixing", "desh_production", "nitan_super_fuel_mixing", "hydrogen_peroxide_production", "sulfuric_acid_production", "nitric_acid_production",
            "organic_solvent_mixing", "polymer_synthesis", "bakelite_production", "rubber_production", "laminate_production", "polycarbonate_synthesis",
            "polyvinylchloride_synthesis", "dynamite_synthesis", "tnt_synthesis", "tatb_synthesis", "c4_synthesis", "yellowcake_production", "uranium_hexaflouride_production",
            "plutonium_hexaflouride_production", "schrabidium_trisulfide_production", "cordite_production", "kelvar_compound_production", "concrete_production",
            "asbestos_concrete_production", "ducrete_production", "solid_rocket_fuel_production", "cryo-electrolysis", "linde_xenon_cycle", "boosted_linde_xenon_cycle",
            "bf_rocket_fuel_mixing", "schrabidic_acid_mixing", "ferric_schrabidate_production", "coltan_purifying", "pandemonium_tantalite_production", "tantalium_crystallizing",
            "liquid_nuclear_waste_vitrification", "lubricant_mixing", "tel_mixing", "oil_reprocessing", "petroil_mixing", "leaded_petroil_mixing", "gasoline_production",
            "fracking_solution_production", "osmiridic_solution_production", "ethanol_production", "methamphetamine_synthesis", "carbon_dioxide_production", "heavy_water_cryo_electrolysis",
            "ender_pearl_synthesis", "chlorine_gas_artillery_shell_production", "phosgene_artillery_shell_production", "chlorocalcite_production", "thorium_salt_enrichment",
            "glyphid_meat_mineral_extraction", "rusty_deco_steel_production"
    };

    private static final String[] CRUCIBLE_TEMPLATE_NAMES = {
            "steel_production", "iron_production_from_hematite", "copper_production_from_malachite", "red_copper_production", "advanced_alloy_production",
            "high_speed_steel_production", "ferrouranium_production", "aluminum_production", "technetium_steel_production", "cadmium_steel_production", "bismuth_bronze_production",
            "arsenic_bronze_production", "cmb_steel_production", "magnetized_tungsten_production", "bscco_production"
    };

    private static final String[] RTG_PELLET_NAMES = {
            "radium_226", "weak_uranium", "plutonium_238", "strontium_90", "cobalt_60", "actinium_227", "polonium_210", "americium_241",
            "gold_198", "lead_209", "decayed_mercury", "decayed_neptunium", "decayed_lead", "decayed_zirconium", "decayed_nickel", "tritium_deuterium_cake"
    };


    private static final String[] BEDROCK_ORES = {
            "iron", "copper", "borax", "asbestos", "niobium", "titanium", "tungsten", "gold", "thorium", "chlorocalcite",
            "flourite", "hematite", "malachite", "neodymium", "centrifuged_iron", "centrifuged_copper", "centrifuged_borax",
            "centrifuged_asbestos", "centrifuged_niobium", "centrifuged_titanium", "centrifuged_tungsten", "centrifuged_gold",
            "centrifuged_thorium", "centrifuged_chlorocalcite", "centrifuged_flourite", "centrifuged_hematite", "centrifuged_malachite",
            "centrifuged_neodymium", "cleaned_iron", "cleaned_copper", "cleaned_borax", "cleaned_asbestos", "cleaned_niobium",
            "cleaned_titanium", "cleaned_tungsten", "cleaned_gold", "cleaned_thorium", "cleaned_chlorocalcite", "cleaned_flourite",
            "cleaned_hematite", "cleaned_malachite", "cleaned_neodymium", "separated_iron", "separated_copper", "separated_borax",
            "separated_asbestos", "separated_niobium", "separated_titanium", "separated_tungsten", "separated_gold", "separated_thorium",
            "separated_chlorocalcite", "separated_flourite", "separated_hematite", "separated_malachite", "separated_neodymium",
            "purified_iron", "purified_copper", "purified_borax", "purified_asbestos", "purified_niobium", "purified_titanium", "purified_tungsten",
            "purified_gold", "purified_thorium", "purified_chlorocalcite", "purified_flourite", "purified_hematite", "purified_malachite",
            "purified_neodymium", "nitrated_iron", "nitrated_copper", "nitrated_borax", "nitrated_asbestos", "nitrated_niobium", "nitrated_titanium",
            "nitrated_tungsten", "nitrated_gold", "nitrated_thorium", "nitrated_chlorocalcite", "nitrated_flourite", "nitrated_hematite",
            "nitrated_malachite", "nitrated_neodymium", "nitrocrystalline_iron", "nitrocrystalline_copper", "nitrocrystalline_borax",
            "nitrocrystalline_asbestos", "nitrocrystalline_niobium", "nitrocrystalline_titanium", "nitrocrystalline_tungsten", "nitrocrystalline_gold",
            "nitrocrystalline_thorium", "nitrocrystalline_chlorocalcite", "nitrocrystalline_flourite", "nitrocrystalline_hematite",
            "nitrocrystalline_malachite", "nitrocrystalline_neodymium", "deep_cleaned_iron", "deep_cleaned_copper", "deep_cleaned_borax", "deep_cleaned_asbestos",
            "deep_cleaned_niobium", "deep_cleaned_titanium", "deep_cleaned_tungsten", "deep_cleaned_gold", "deep_cleaned_thorium", "deep_cleaned_chlorocalcite",
            "deep_cleaned_flourite", "deep_cleaned_hematite", "deep_cleaned_malachite", "deep_cleaned_neodymium", "seared_iron", "seared_copper",
            "seared_borax", "seared_asbestos", "seared_niobium", "seared_titanium", "seared_tungsten", "seared_gold", "seared_thorium",
            "seared_chlorocalcite", "seared_flourite", "seared_hematite", "seared_malachite", "seared_neodymium", "enriched_iron", "enriched_copper",
            "enriched_borax", "enriched_asbestos", "enriched_niobium", "enriched_titanium", "enriched_tungsten", "enriched_gold", "enriched_thorium",
            "enriched_chlorocalcite", "enriched_flourite", "enriched_hematite", "enriched_malachite", "enriched_neodymium"
    };

    private static final String[] FLUID_TANKS = {
            "compressed_air", "water", "heavy_water", "hot_heavy_water", "lava", "steam", "dense_steam", "super_dense_steam",
            "ultra_dense_steam", "carbon_dioxide", "coolant", "hot_coolant", "perfluoromethyl", "cold_perfluoromethyl", "hot_perfluoromethyl",
            "cryogel", "mug_root_beer", "hot_mug_root_beer", "blood", "hot_blood", "liquid_sodium", "hot_liquid_sodium", "liquid_lead",
            "liquid_thorium_salt", "hot_liquid_thorium_salt", "depleted_liquid_thorium_salt", "liquid_hydrogen", "deuterium",
            "tritium", "helium_3", "helium_4", "liquid_oxygen", "xenon_gas", "chlorine_gas", "mercury", "crude_oil", "desulfurized_crude_oil",
            "cracked_oil", "desulfurized_cracked_oil", "coal_oil", "coker_oil", "hot_crude_oil", "desulfurized_hot_crude_oil", "hot_cracked_oil",
            "desulfurized_hot_cracked_oil", "heavy_oil", "vacuum_heavy_oil", "naphtha", "desulfurized_naphtha", "cracked_naphtha", "coker_naphtha",
            "reformate", "light_oil", "desulfurized_light_oil", "cracked_light_oil", "vacuum_light_oil", "bitumen", "industrial_oil", "heating_oil",
            "heavy_heating_oil", "reclaimed_industrial_oil", "engine_lubricant", "natural_gas", "coker_gas", "petroleum_gas", "sour_gas",
            "lpg", "syngas", "oxyhydrogen", "aromatic_hydrocarbons", "unsaturated_hydrocarbons", "reformate_gas", "diesel", "high_cetane_diesel",
            "cracked_diesel", "high_cetane_cracked_diesel", "kerosene", "jet_fuel", "petroil", "leaded_petroil", "gasoline", "leaded_gasoline",
            "coal_gasoline", "leaded_coal_gasoline", "coal_tar_gasoline", "wood_oil", "biogas", "biofuel", "ethanol", "fish_oil", "sunflower_seed_oil",
            "bf_rocket_fuel", "salient_green", "seeding_slurry", "colloid", "vitriol", "ore_slop", "ionic_gel", "hydrogen_peroxide", "sulfuric_acid",
            "nitric_acid", "solvent", "high_performance_solvent", "schrabidic_acid", "uranium_hexafluoride", "plutonium_hexafluoride", "schrabidium_trisulfide",
            "poisonous_mud", "fullerene_solution", "dissolved_egg", "cholesterol_solution", "sodium_aluminate", "bauxite_solution", "alumina",
            "liquid_concrete", "fracking_solution", "lye", "phosgene", "mustard_gas", "estradiol_solution", "nitroglycerin", "antimatter", "antishrabidium",
            "experience_juice", "ender_juice", "stellar_flux", "booster_pheromone", "modified_booster_pheromone", "custom_fluid_demo"
    };

    private static final String[] HAZARDOUS_MATERIAL_TANK_NAMES = {
            "compressed_air", "water", "heavy_water", "hot_heavy_water", "lava", "steam", "dense_steam", "super_dense_steam",
            "ultra_dense_steam", "carbon_dioxide", "coolant", "hot_coolant", "perfluoromethyl", "cold_perfluoromethyl", "hot_perfluoromethyl",
            "cryogel", "mug_root_beer", "hot_mug_root_beer", "blood", "hot_blood", "liquid_sodium", "hot_liquid_sodium", "liquid_lead",
            "liquid_thorium_salt", "hot_liquid_thorium_salt", "depleted_liquid_thorium_salt", "liquid_hydrogen", "deuterium",
            "tritium", "helium_3", "helium_4", "liquid_oxygen", "xenon_gas", "chlorine_gas", "mercury", "crude_oil", "desulfurized_crude_oil",
            "cracked_oil", "desulfurized_cracked_oil", "coal_oil", "coker_oil", "hot_crude_oil", "desulfurized_hot_crude_oil", "hot_cracked_oil",
            "desulfurized_hot_cracked_oil", "heavy_oil", "vacuum_heavy_oil", "naphtha", "desulfurized_naphtha", "cracked_naphtha", "coker_naphtha",
            "reformate", "light_oil", "desulfurized_light_oil", "cracked_light_oil", "vacuum_light_oil", "bitumen", "industrial_oil", "heating_oil",
            "heavy_heating_oil", "reclaimed_industrial_oil", "engine_lubricant", "natural_gas", "coker_gas", "petroleum_gas", "sour_gas",
            "lpg", "syngas", "oxyhydrogen", "aromatic_hydrocarbons", "unsaturated_hydrocarbons", "reformate_gas", "diesel", "high_cetane_diesel",
            "cracked_diesel", "high_cetane_cracked_diesel", "kerosene", "jet_fuel", "petroil", "leaded_petroil", "gasoline", "leaded_gasoline",
            "coal_gasoline", "leaded_coal_gasoline", "coal_tar_gasoline", "wood_oil", "biogas", "biofuel", "ethanol", "fish_oil", "sunflower_seed_oil",
            "bf_rocket_fuel", "salient_green", "seeding_slurry", "colloid", "vitriol", "ore_slop", "ionic_gel", "hydrogen_peroxide", "sulfuric_acid",
            "nitric_acid", "solvent", "high_performance_solvent", "schrabidic_acid", "uranium_hexafluoride", "plutonium_hexafluoride", "schrabidium_trisulfide",
            "poisonous_mud", "fullerene_solution", "dissolved_egg", "cholesterol_solution", "sodium_aluminate", "bauxite_solution", "alumina",
            "liquid_concrete", "fracking_solution", "lye", "phosgene", "mustard_gas", "estradiol_solution", "nitroglycerin", "antimatter", "antishrabidium",
            "experience_juice", "ender_juice", "stellar_flux", "booster_pheromone", "modified_booster_pheromone", "custom_fluid_demo"
    };

    private static final String[] PACKAGED_FLUID_NAMES = {
            "compressed_air", "water", "heavy_water", "hot_heavy_water", "lava", "steam", "dense_steam", "super_dense_steam",
            "ultra_dense_steam", "carbon_dioxide", "coolant", "hot_coolant", "perfluoromethyl", "cold_perfluoromethyl", "hot_perfluoromethyl",
            "cryogel", "mug_root_beer", "hot_mug_root_beer", "blood", "hot_blood", "liquid_sodium", "hot_liquid_sodium", "liquid_lead",
            "liquid_thorium_salt", "hot_liquid_thorium_salt", "depleted_liquid_thorium_salt", "liquid_hydrogen", "deuterium",
            "tritium", "helium_3", "helium_4", "liquid_oxygen", "xenon_gas", "chlorine_gas", "mercury", "crude_oil", "desulfurized_crude_oil",
            "cracked_oil", "desulfurized_cracked_oil", "coal_oil", "coker_oil", "hot_crude_oil", "desulfurized_hot_crude_oil", "hot_cracked_oil",
            "desulfurized_hot_cracked_oil", "heavy_oil", "vacuum_heavy_oil", "naphtha", "desulfurized_naphtha", "cracked_naphtha", "coker_naphtha",
            "reformate", "light_oil", "desulfurized_light_oil", "cracked_light_oil", "vacuum_light_oil", "bitumen", "industrial_oil", "heating_oil",
            "heavy_heating_oil", "reclaimed_industrial_oil", "engine_lubricant", "natural_gas", "coker_gas", "petroleum_gas", "sour_gas",
            "lpg", "syngas", "oxyhydrogen", "aromatic_hydrocarbons", "unsaturated_hydrocarbons", "reformate_gas", "diesel", "high_cetane_diesel",
            "cracked_diesel", "high_cetane_cracked_diesel", "kerosene", "jet_fuel", "petroil", "leaded_petroil", "gasoline", "leaded_gasoline",
            "coal_gasoline", "leaded_coal_gasoline", "coal_tar_gasoline", "wood_oil", "biogas", "biofuel", "ethanol", "fish_oil", "sunflower_seed_oil",
            "bf_rocket_fuel", "salient_green", "seeding_slurry", "colloid", "vitriol", "ore_slop", "ionic_gel", "hydrogen_peroxide", "sulfuric_acid",
            "nitric_acid", "solvent", "high_performance_solvent", "schrabidic_acid", "uranium_hexafluoride", "plutonium_hexafluoride", "schrabidium_trisulfide",
            "poisonous_mud", "fullerene_solution", "dissolved_egg", "cholesterol_solution", "sodium_aluminate", "bauxite_solution", "alumina",
            "liquid_concrete", "fracking_solution", "lye", "phosgene", "mustard_gas", "estradiol_solution", "nitroglycerin", "antimatter", "antishrabidium",
            "experience_juice", "ender_juice", "stellar_flux", "booster_pheromone", "modified_booster_pheromone", "custom_fluid_demo"
    };

    private static final String[] FLUID_BARREL_NAMES = {
            "compressed_air", "water", "heavy_water", "hot_heavy_water", "lava", "steam", "dense_steam", "super_dense_steam",
            "ultra_dense_steam", "carbon_dioxide", "coolant", "hot_coolant", "perfluoromethyl", "cold_perfluoromethyl", "hot_perfluoromethyl",
            "cryogel", "mug_root_beer", "hot_mug_root_beer", "blood", "hot_blood", "liquid_sodium", "hot_liquid_sodium", "liquid_lead",
            "liquid_thorium_salt", "hot_liquid_thorium_salt", "depleted_liquid_thorium_salt", "liquid_hydrogen", "deuterium",
            "tritium", "helium_3", "helium_4", "liquid_oxygen", "xenon_gas", "chlorine_gas", "mercury", "crude_oil", "desulfurized_crude_oil",
            "cracked_oil", "desulfurized_cracked_oil", "coal_oil", "coker_oil", "hot_crude_oil", "desulfurized_hot_crude_oil", "hot_cracked_oil",
            "desulfurized_hot_cracked_oil", "heavy_oil", "vacuum_heavy_oil", "naphtha", "desulfurized_naphtha", "cracked_naphtha", "coker_naphtha",
            "reformate", "light_oil", "desulfurized_light_oil", "cracked_light_oil", "vacuum_light_oil", "bitumen", "industrial_oil", "heating_oil",
            "heavy_heating_oil", "reclaimed_industrial_oil", "engine_lubricant", "natural_gas", "coker_gas", "petroleum_gas", "sour_gas",
            "lpg", "syngas", "oxyhydrogen", "aromatic_hydrocarbons", "unsaturated_hydrocarbons", "reformate_gas", "diesel", "high_cetane_diesel",
            "cracked_diesel", "high_cetane_cracked_diesel", "kerosene", "jet_fuel", "petroil", "leaded_petroil", "gasoline", "leaded_gasoline",
            "coal_gasoline", "leaded_coal_gasoline", "coal_tar_gasoline", "wood_oil", "biogas", "biofuel", "ethanol", "fish_oil", "sunflower_seed_oil",
            "bf_rocket_fuel", "salient_green", "seeding_slurry", "colloid", "vitriol", "ore_slop", "ionic_gel", "hydrogen_peroxide", "sulfuric_acid",
            "nitric_acid", "solvent", "high_performance_solvent", "schrabidic_acid", "uranium_hexafluoride", "plutonium_hexafluoride", "schrabidium_trisulfide",
            "poisonous_mud", "fullerene_solution", "dissolved_egg", "cholesterol_solution", "sodium_aluminate", "bauxite_solution", "alumina",
            "liquid_concrete", "fracking_solution", "lye", "phosgene", "mustard_gas", "estradiol_solution", "nitroglycerin", "antimatter", "antishrabidium",
            "experience_juice", "ender_juice", "stellar_flux", "booster_pheromone", "modified_booster_pheromone", "custom_fluid_demo"
    };

    private static String[] CANISTER_NAMES = {
            "crude_oil", "desulfurized_crude_oil", "cracked_oil", "desulfurized_cracked_oil", "coal_oil", "heavy_oil", "vacuum_heavy_oil",
            "naphtha", "desulfurized_naphtha", "cracked_naphtha", "reformate", "light_oil", "desulfurized_light_oil", "cracked_light_oil",
            "vacuum_light_oil", "bitumen", "industrial_oil", "heating_oil", "heavy_heating_oil", "reclaimed_industrial_oil", "engine_lubricant",
            "btx", "diesel", "high_cetane_diesel", "cracked_diesel", "high_cetane_cracked_diesel", "kerosene", "jet_fuel", "petroil", "leaded_petroil",
            "gasoline", "leaded_gasoline", "coal_gasoline", "leaded_coal_gasoline", "coal_tar_creosote", "wood_oil", "biofuel", "ethanol",
            "nitan_octane_super_fuel", "seeding_slurry", "solvent", "fracking_solution", "napalm_b_4711"
    };

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HBMNTM.MOD_ID);

    public static final Map<String, RegistryObject<Item>> FLUID_IDENTIFIERS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> ASSEMBLY_TEMPLATES = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> CHEMISTRY_TEMPLATES = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> CRUCIBLE_TEMPLATES = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> BEDROCK_ORE_NAMES = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> FLUID_TANK_NAMES = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> RTG_PELLETS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> CANISTERS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> PACKAGED_FLUIDS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> HAZARDOUS_MATERIAL_TANKS = new HashMap<>();
    public static final Map<String, RegistryObject<Item>> FLUID_BARRELS = new HashMap<>();

    static {
        for (String name : FLUID_NAMES) {
            FLUID_IDENTIFIERS.put(name, ITEMS.register(name + "_fluid_identifier",
                    () -> new FluidIdentifierItem(new Item.Properties().durability(200), name)));
        }
    }

    static {
        for (String name : RTG_PELLET_NAMES) {
            RTG_PELLETS.put(name, ITEMS.register(name + "_rtg_pellet",
                    () -> new Item(new Item.Properties().durability(200))));
        }
    }

    static {
        for (String name : PACKAGED_FLUID_NAMES) {
            PACKAGED_FLUIDS.put(name, ITEMS.register("packaged_" + name,
                    () -> new FluidTankItem(new Item.Properties().durability(200), name)));
        }
    }

    static {
        for (String name : CANISTER_NAMES) {
            CANISTERS.put(name, ITEMS.register("canister_" + name,
                    () -> new FluidTankItem(new Item.Properties().durability(200), name)));
        }
    }

    static {
        for (String name : HAZARDOUS_MATERIAL_TANK_NAMES) {
            HAZARDOUS_MATERIAL_TANKS.put(name, ITEMS.register("hazardous_material_tank_" + name,
                    () -> new FluidTankItem(new Item.Properties().durability(200), name)));
        }
    }

    static {
        for (String name : FLUID_BARREL_NAMES) {
            FLUID_BARRELS.put(name, ITEMS.register("fluid_barrel_" + name,
                    () -> new FluidTankItem(new Item.Properties().durability(200), name)));
        }
    }

    static {
        for (String name : ASSEMBLY_TEMPLATE_NAMES) {
            ASSEMBLY_TEMPLATES.put(name, ITEMS.register("assembly_template_" + name,
                    () -> new AssemblyTemplateItem(new Item.Properties().durability(200), name)));
        }
    }

    static {
        for (String name : CRUCIBLE_TEMPLATE_NAMES) {
            CRUCIBLE_TEMPLATES.put(name, ITEMS.register("crucible_template_" + name,
                    () -> new CrucibleTemplateItem(new Item.Properties().durability(200), name)));
        }
    }

    static {
        for (String name : BEDROCK_ORES) {
            BEDROCK_ORE_NAMES.put(name, ITEMS.register(name + "_bedrock_ore",
                    () -> new BedrockOreItem(new Item.Properties(), name)));
        }
    }

    static {
        for (String name : CHEMISTRY_TEMPLATE_NAMES) {
            CHEMISTRY_TEMPLATES.put(name, ITEMS.register("chemistry_template_" + name,
                    () -> new ChemistryTemplateItem(new Item.Properties().durability(200), name)));
        }
    }

    static {
        for (String name : FLUID_TANKS) {
            FLUID_TANK_NAMES.put(name, ITEMS.register("fluid_tank_" + name,
                    () -> new FluidTankItem(new Item.Properties().durability(200), name)));
        }
    }

    public static final RegistryObject<Item> URANIUM_DUST = ITEMS.register("uranium_dust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CAST_STEEL_PLATE = ITEMS.register("cast_steel_plate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_SHELL = ITEMS.register("steel_shell",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_PIPE = ITEMS.register("steel_pipe",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CRYOLITE_CHUNK = ITEMS.register("cryolite_chunk",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CLAY_CATALYST = ITEMS.register("clay_catalyst",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_POWDER = ITEMS.register("iron_powder",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEAD_INGOT = ITEMS.register("lead_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIRECLAY = ITEMS.register("fireclay",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CAPACITOR = ITEMS.register("capacitor",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_BOLT = ITEMS.register("tungsten_bolt",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VACUUM_TUBE = ITEMS.register("vacuum_tube",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MICROCHIP = ITEMS.register("microchip",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FIREBRICK = ITEMS.register("firebrick",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PRINTED_SILICON_WAFER = ITEMS.register("printed_silicon_wafer",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILICON_WAFER = ITEMS.register("silicon_wafer",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILICON_NUGGET = ITEMS.register("silicon_nugget",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SILICON_BOULE = ITEMS.register("silicon_boule",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CIRCUIT_STAMP = ITEMS.register("circuit_stamp",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LONG_RANGE_TARGET_DESIGNATOR = ITEMS.register("long_range_target_designator",
            () -> new LongRangeTargetDesignatorItem(new Item.Properties()));
    public static final RegistryObject<Item> SIM_CARD_ACTIVATOR = ITEMS.register("sim_card_activator",
            () -> new SimCardActivatorItem(new Item.Properties()));
    public static final RegistryObject<Item> RING_COIL = ITEMS.register("ring_coil",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PRINTED_CIRCUIT_BOARD = ITEMS.register("printed_circuit_board",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_INGOT = ITEMS.register("redstone_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CORIUM_BUCKET = ITEMS.register("corium_bucket",
            CoriumItem::new);
    public static final RegistryObject<Item> THORIUM_INGOT = ITEMS.register("thorium_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_INGOT = ITEMS.register("tungsten_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ALUMINUM_INGOT = ITEMS.register("aluminum_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_BERYLLIUM = ITEMS.register("raw_beryllium",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BERYLLIUM_INGOT = ITEMS.register("beryllium_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_PLATE = ITEMS.register("copper_plate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COAL_TAR = ITEMS.register("coal_tar",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ELECTRIC_MOTOR = ITEMS.register("electric_motor",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CABLE_DRUM = ITEMS.register("cable_drum",
            () -> new CableDrumItem(new Item.Properties()));
    public static final RegistryObject<Item> FLAT_STAMP = ITEMS.register("flat_stamp",
            () -> new Item(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> WIRE_STAMP = ITEMS.register("wire_stamp",
            () -> new Item(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> MINECRAFT_GRADE_COPPER_WIRE = ITEMS.register("minecraft_grade_copper_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BAKELITE_BAR = ITEMS.register("bakelite_bar",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> POLYMER_BAR = ITEMS.register("polymer_bar",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_COIL = ITEMS.register("copper_coil",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INSULATOR = ITEMS.register("insulator",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANALOG_CIRCUIT_BOARD = ITEMS.register("analog_circuit_board",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_TANK = ITEMS.register("steel_tank",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MOTOR = ITEMS.register("motor",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> AIRSTRIKE_DESIGNATOR = ITEMS.register("airstrike_designator",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_PLATE = ITEMS.register("steel_plate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ALUMINUM_PLATE = ITEMS.register("aluminum_plate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SCHRABIDIUM_INGOT = ITEMS.register("schrabidium_ingot",
            () -> new SchrabidiumIngotItem(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_PICKAXE = ITEMS.register("steel_pickaxe",
            () -> new CustomPickaxeItem(Tiers.IRON, 1, -2.8f, 7.0f, false,
                    false, false, false, false, false, false, false, new Item.Properties()));

    public static final RegistryObject<Item> TITANIUM_PICKAXE = ITEMS.register("titanium_pickaxe",
            () -> new CustomPickaxeItem(Tiers.DIAMOND, 1, -2.8f, 8.0f, false,
                    false, false, false, false, false, false, false, new Item.Properties()));

    public static final RegistryObject<Item> COBALT_PICKAXE = ITEMS.register("cobalt_pickaxe",
            () -> new CustomPickaxeItem(Tiers.DIAMOND, 1, -2.8f, 8.0f, false,
                    true, false, false, true, true, false, false, new Item.Properties()));

    public static final RegistryObject<Item> ADVANCED_ALLOY_PICKAXE = ITEMS.register("advanced_alloy_pickaxe",
            () -> new CustomPickaxeItem(Tiers.NETHERITE, 1, -2.8f, 8.3f, false,
                    true, false, false, false, false, false, false, new Item.Properties()));

    public static final RegistryObject<Item> WORKER_ALLOY_PICKAXE = ITEMS.register("worker_alloy_pickaxe",
            () -> new CustomPickaxeItem(Tiers.IRON, 1, -2.8f, 12.0f, false,
                    true, true, true, true, true, false, false, new Item.Properties()));

    public static final RegistryObject<Item> CMB_STEEL_PICKAXE = ITEMS.register("cmb_steel_pickaxe",
            () -> new CustomPickaxeItem(Tiers.NETHERITE, 2, -2.8f, 40.0f, false,
                    true, false, false, true, true, true, false, new Item.Properties()));

    public static final RegistryObject<Item> SCHRABIDIUM_PICKAXE = ITEMS.register("schrabidium_pickaxe",
            () -> new CustomPickaxeItem(Tiers.NETHERITE, 5, -2.8f, 100.0f, true,
                    true, true, true, true, true, true, true, new Item.Properties().rarity(Rarity.EPIC)));

    public static final RegistryObject<Item> STARMETAL_PICKAXE = ITEMS.register("starmetal_pickaxe",
            () -> new CustomPickaxeItem(Tiers.NETHERITE, 3, -2.8f, 15.0f, true,
                    true, true, false, true, true, false, false, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> STEEL_BEAM = ITEMS.register("steel_beam",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TITANIUM_INGOT = ITEMS.register("titanium_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ADVANCED_ALLOY_INGOT = ITEMS.register("advanced_alloy_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INDUSTRIAL_GRADE_COPPER = ITEMS.register("industrial_grade_copper",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MINECRAFT_GRADE_COPPER = ITEMS.register("minecraft_grade_copper",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HIGH_EXPLOSIVE_MISSILE = ITEMS.register("high_explosive_missile",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INCENDIARY_MISSILE = ITEMS.register("incendiary_missile",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ANTI_BALLISTIC_MISSILE = ITEMS.register("anti_ballistic_missile",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INTEGRATED_CIRCUIT_BOARD = ITEMS.register("integrated_circuit_board",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHORT_RANGE_TARGET_DESIGNATOR = ITEMS.register("short_range_target_designator",
            () -> new ShortRangeTargetDesignatorItem(new Item.Properties()));
    public static final RegistryObject<Item> FLUORITE = ITEMS.register("fluorite",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PWR_CONTROLLER_LINKING_DEVICE = ITEMS.register("pwr_controller_linking_device",
            () -> new PWRControllerLinkingDeviceItem(new Item.Properties()));
    public static final RegistryObject<Item> LEAD_DUST = ITEMS.register("lead_dust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL = ITEMS.register("steel",
            () -> new FuelItem(new Item.Properties(), 400));
    public static final RegistryObject<Item> REDCOPPER = ITEMS.register("redcopper",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> METAL_DETECTOR = ITEMS.register("metal_detector",
            () -> new MetalDetectorItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> DESH_HAND_DRILL = ITEMS.register("desh_hand_drill",
            () -> new DeshHandDrillItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> URANIUM_ROD = ITEMS.register("uranium_rod",
            () -> new UraniumRodItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> URANIUM_ROD_NEUTRON_SOURCE = ITEMS.register("uranium_rod_neutron_source",
            () -> new UraniumRodNeutronSourceItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> MACHINE_TEMPLATE_FOLDER = ITEMS.register("machine_template_folder",
            () -> new MachineTemplateFolderItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> RADIATION_MEASURER = ITEMS.register("radiation_measurer",
            () -> new RadiationMeasurerItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> ENERGY_INJECTOR = ITEMS.register("energy_injector",
            () -> new EnergyInjectorItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> FLUID_MEASURER = ITEMS.register("fluid_measurer",
            () -> new FluidMeasurerItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> FLUID_INJECTOR = ITEMS.register("fluid_injector",
            () -> new FluidInjectorItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> GEIGER_COUNTER = ITEMS.register("geiger_counter",
            () -> new GeigerCounterItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> NUKE_DETONATOR = ITEMS.register("nuke_detonator",
            () -> new NukeDetonatorItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> DETONATOR = ITEMS.register("detonator",
            () -> new DetonatorItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> SIM_CARD = ITEMS.register("sim_card",
            () -> new SimCardItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> INFINITE_WATER_TANK = ITEMS.register("infinite_water_tank",
            () -> new Item(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> URANIUM_BEDROCK_ORE = ITEMS.register("uranium_bedrock_ore",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> THERMOELECTRIC_ELEMENT = ITEMS.register("thermoelectric_element",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NUCLEAR_MISSILE = ITEMS.register("nuclear_missile",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> QUARTZ_POWDER = ITEMS.register("quartz_powder",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TINY_PILE_OF_LITHIUM_POWDER = ITEMS.register("tiny_pile_of_lithium_powder",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUBBER_BAR = ITEMS.register("rubber_bar",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CENTRIFUGED_URANIUM_BEDROCK_ORE = ITEMS.register("centrifuged_uranium_bedrock_ore",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CLEANED_URANIUM_BEDROCK_ORE = ITEMS.register("cleaned_uranium_bedrock_ore",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SEPARATED_URANIUM_BEDROCK_ORE = ITEMS.register("separated_uranium_bedrock_ore",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURIFIED_URANIUM_BEDROCK_ORE = ITEMS.register("purified_uranium_bedrock_ore",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NITRATED_URANIUM_BEDROCK_ORE = ITEMS.register("nitrated_uranium_bedrock_ore",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_SPHERE = ITEMS.register("steel_sphere",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FLAT_STEEL_CASING = ITEMS.register("flat_steel_casing",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_PEDESTAL = ITEMS.register("steel_pedestal",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MILITARY_GRADE_CIRCUIT_BOARD = ITEMS.register("military_grade_circuit_board",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_WIRE = ITEMS.register("gold_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SCREWDRIVER = ITEMS.register("screwdriver",
            () -> new ScrewdriverItem(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> INFINITE_BATTERY = ITEMS.register("infinite_battery",
            () -> new Item(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> INFINITE_FLUID_BARREL = ITEMS.register("infinite_fluid_barrel",
            () -> new Item(new Item.Properties().durability(200)));
    public static final RegistryObject<Item> STEAM_BUCKET = ITEMS.register("steam_bucket", () -> new SteamItem());
    public static final RegistryObject<Item> OIL_BUCKET = ITEMS.register("oil_bucket", () -> new OilItem());



    // Resources Tab
    public static final RegistryObject<Item> MAGNETIZED_TUNGSTEN_INGOT = ITEMS.register("magnetized_tungsten_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CMB_STEEL_INGOT = ITEMS.register("cmb_steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HIGH_SPEED_STEEL_INGOT = ITEMS.register("high_speed_steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TECHNETIUM_99_INGOT = ITEMS.register("technetium_99_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TECHNETIUM_STEEL_INGOT = ITEMS.register("technetium_steel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DESH_INGOT = ITEMS.register("desh_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SATURNITE_INGOT = ITEMS.register("saturnite_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STARMETAL_INGOT = ITEMS.register("starmetal_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EUPHEMIUM_INGOT = ITEMS.register("euphemium_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DINEUTRONIUM_INGOT = ITEMS.register("dineutronium_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BISMUTH_INGOT = ITEMS.register("bismuth_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZIRCONIUM_CUBE = ITEMS.register("zirconium_cube",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> URANIUM_233_INGOT = ITEMS.register("uranium_233_ingot",
            () -> new Uranium233Item(new Item.Properties(), "5.0"));
    public static final RegistryObject<Item> URANIUM_235_INGOT = ITEMS.register("uranium_235_ingot",
            () -> new Uranium233Item(new Item.Properties(), "1.0"));
    public static final RegistryObject<Item> REACTOR_GRADE_PLUTONIUM_INGOT = ITEMS.register("reactor_grade_plutonium_ingot",
            () -> new Uranium233Item(new Item.Properties(), "1.0"));
    public static final RegistryObject<Item> PLUTONIUM_INGOT = ITEMS.register("plutonium_ingot",
            () -> new Uranium233Item(new Item.Properties(), "7.5"));

    public static final RegistryObject<Item> AMERICIUM_242_INGOT = ITEMS.register("americium_242_ingot",
            () -> new Uranium233Item(new Item.Properties(), "9.5"));
    public static final RegistryObject<Item> REACTOR_GRADE_AMERICIUM_INGOT = ITEMS.register("reactor_grade_americium_ingot",
            () -> new Uranium233Item(new Item.Properties(), "9.0"));
    public static final RegistryObject<Item> INGOT_OF_URANIUM_FUEL = ITEMS.register("ingot_of_uranium_fuel",
            () -> new Uranium233Item(new Item.Properties(), "0.5"));
    public static final RegistryObject<Item> INGOT_OF_PLUTONIUM_FUEL = ITEMS.register("ingot_of_plutonium_fuel",
            () -> new Uranium233Item(new Item.Properties(), "4.25"));
    public static final RegistryObject<Item> NEPTUNIUM_FUEL_INGOT = ITEMS.register("neptunium_fuel_ingot",
            () -> new Uranium233Item(new Item.Properties(), "1.5"));
    public static final RegistryObject<Item> INGOT_OF_MOX_FUEL = ITEMS.register("ingot_of_mox_fuel",
            () -> new Uranium233Item(new Item.Properties(), "2.5"));
    public static final RegistryObject<Item> INGOT_OF_AMERICIUM_FUEL = ITEMS.register("ingot_of_americium_fuel",
            () -> new Uranium233Item(new Item.Properties(), "4.75"));
    public static final RegistryObject<Item> INGOT_OF_THORIUM_FUEL = ITEMS.register("ingot_of_thorium_fuel",
            () -> new Uranium233Item(new Item.Properties(), "1.75"));
    public static final RegistryObject<Item> BORON_INGOT = ITEMS.register("boron_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COAL_POWDER = ITEMS.register("coal_powder",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SMORE_INGOT = ITEMS.register("smore_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RARE_EARTH_CRYSTALS = ITEMS.register("rare_earth_crystals",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DESHREADY_BLEND = ITEMS.register("deshready_blend",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DESH_BLEND = ITEMS.register("desh_blend",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NIOBIUM_INGOT = ITEMS.register("niobium_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NEODYMIUM_INGOT = ITEMS.register("neodymium_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BROMINE_INGOT = ITEMS.register("bromine_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_CRYSTALS = ITEMS.register("redstone_crystals",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DROP_OF_MERCURY = ITEMS.register("drop_of_mercury",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CARBON_WIRE = ITEMS.register("carbon_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SCHRABIDIUM_WIRE = ITEMS.register("schrabidium_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COPPER_WIRE = ITEMS.register("copper_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TUNGSTEN_WIRE = ITEMS.register("tungsten_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ALUMINUM_WIRE = ITEMS.register("aluminum_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> LEAD_WIRE = ITEMS.register("lead_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ZIRCONIUM_WIRE = ITEMS.register("zirconium_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_WIRE = ITEMS.register("steel_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ADVANCED_ALLOY_WIRE = ITEMS.register("advanced_alloy_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAGNETIZED_TUNGSTEN_WIRE = ITEMS.register("magnetized_tungsten_wire",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> COMBUSTION_ENGINE_PISTON = ITEMS.register("combustion_engine_piston",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_PISTON_SET = ITEMS.register("steel_piston_set",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HIGH_SPEED_STEEL_PISTON_SET = ITEMS.register("high_speed_steel_piston_set",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CAST_IRON_PLATE = ITEMS.register("cast_iron_plate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_PLATE = ITEMS.register("iron_plate",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DESH_PISTON_SET = ITEMS.register("desh_piston_set",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STARMETAL_PISTON_SET = ITEMS.register("starmetal_piston_set",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_DRILLBIT = ITEMS.register("steel_drillbit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_BOLT = ITEMS.register("steel_bolt",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HIGH_SPEED_STEEL_DRILLBIT = ITEMS.register("high_speed_steel_drillbit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DESH_DRILLBIT = ITEMS.register("desh_drillbit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TECHNETIUM_STEEL_DRILLBIT = ITEMS.register("technetium_steel_drillbit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FERROURANIUM_DRILLBIT = ITEMS.register("ferrouranium_drillbit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> EMPTY_CELL = ITEMS.register("empty_cell",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> URANIUM_HEXAFLUORIDE_CELL = ITEMS.register("uranium_hexafluoride_cell",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLUTONIUM_HEXAFLUORIDE_CELL = ITEMS.register("plutonium_hexafluoride_cell",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEUTERIUM_CELL = ITEMS.register("deuterium_cell",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TRITIUM_CELL = ITEMS.register("tritium_cell",
            () -> new Uranium233Item(new Item.Properties(), "0.001"));
    public static final RegistryObject<Item> SCHRABIDIUM_TRISULFIDE_CELL = ITEMS.register("schrabidium_trisulfide_cell",
            () -> new SchrabidiumTrisulfideCellItem(new Item.Properties(), "5.0", true));
    public static final RegistryObject<Item> ANTIMATTER_CELL = ITEMS.register("antimatter_cell",
            () -> new AntimatterCellItem(new Item.Properties()));
    public static final RegistryObject<Item> ANTISCHRABIDIUM_CELL = ITEMS.register("antischrabidium_cell",
            () -> new AntiSchrabidiumCellItem(new Item.Properties()));
    public static final RegistryObject<Item> GASEOUS_BALEFIRE_CELL = ITEMS.register("gaseous_balefire_cell",
            () -> new Uranium233Item(new Item.Properties(), "50.0"));
    public static final RegistryObject<Item> DYATLOV_INSTANT_MELTDOWN_APPLICATOR = ITEMS.register("dyatlov_instant_meltdown_applicator",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FAT_MAN_DROP_LOCATION_DESIGNATOR = ITEMS.register("fat_man_drop_location_designator",
            () -> new FatManDropLocationDesignatorItem(new Item.Properties()));
    public static final RegistryObject<Item> BLANK_FOUNDRY_MOLD = ITEMS.register("blank_foundry_mold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NUGGET_FOUNDRY_MOLD = ITEMS.register("nugget_foundry_mold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BILLET_FOUNDRY_MOLD = ITEMS.register("billet_foundry_mold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INGOT_FOUNDRY_MOLD = ITEMS.register("ingot_foundry_mold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLATE_FOUNDRY_MOLD = ITEMS.register("plate_foundry_mold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STEEL_SHREDDER_BLADES = ITEMS.register("steel_shredder_blades",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WIRE_FOUNDRY_MOLD = ITEMS.register("wire_foundry_mold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CAST_PLATE_FOUNDRY_MOLD = ITEMS.register("cast_plate_foundry_mold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OIL_RESERVOIR_DETECTOR = ITEMS.register("oil_reservoir_detector",
            () -> new OilReservoirDetectorItem(new Item.Properties()));
    public static final RegistryObject<Item> DENSE_WIRE_FOUNDRY_MOLD = ITEMS.register("dense_wire_foundry_mold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BLADE_FOUNDRY_MOLD = ITEMS.register("blade_foundry_mold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHREDDER_BLADE_FOUNDRY_MOLD = ITEMS.register("shredder_blade_foundry_mold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SHELL_FOUNDRY_MOLD = ITEMS.register("shell_foundry_mold",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PIPE_FOUNDRY_MOLD = ITEMS.register("pipe_foundry_mold",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
