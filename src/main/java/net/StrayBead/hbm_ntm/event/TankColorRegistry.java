package net.StrayBead.hbm_ntm.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TankColorRegistry {
    private static final Map<String, Integer> TANK_COLORS = new HashMap<>();

    static {
        TANK_COLORS.put("compressed_air", 0xDAE4E1);
        TANK_COLORS.put("water", 0x3F76E4);
        TANK_COLORS.put("heavy_water", 0x1E88E5);
        TANK_COLORS.put("hot_heavy_water", 0x1565C0);
        TANK_COLORS.put("lava", 0xFF5500);

        // --- STEAM VARIANTS ---
        TANK_COLORS.put("steam", 0xE0E0E0);
        TANK_COLORS.put("dense_steam", 0xCCCCCC);
        TANK_COLORS.put("super_dense_steam", 0xAAAAAA);
        TANK_COLORS.put("ultra_dense_steam", 0x888888);

        // --- COOLANTS & EXOTICS ---
        TANK_COLORS.put("carbon_dioxide", 0x212121);
        TANK_COLORS.put("coolant", 0x42A5F5);
        TANK_COLORS.put("hot_coolant", 0xEF5350);
        TANK_COLORS.put("perfluoromethyl", 0xCFD8DC);
        TANK_COLORS.put("cold_perfluoromethyl", 0x81D4FA);
        TANK_COLORS.put("hot_perfluoromethyl", 0xFF8A65);
        TANK_COLORS.put("cryogel", 0x00E5FF);

        // --- FOOD & BIOLOGICAL ---
        TANK_COLORS.put("mug_root_beer", 0x3E2723);
        TANK_COLORS.put("hot_mug_root_beer", 0x4E342E);
        TANK_COLORS.put("blood", 0xB71C1C);
        TANK_COLORS.put("hot_blood", 0x880E4F);

        // --- MOLTEN METALS & SALTS ---
        TANK_COLORS.put("liquid_sodium", 0xF5F5F5);
        TANK_COLORS.put("hot_liquid_sodium", 0xFFEB3B);
        TANK_COLORS.put("liquid_lead", 0x455A64);
        TANK_COLORS.put("liquid_thorium_salt", 0x4CAF50);
        TANK_COLORS.put("hot_liquid_thorium_salt", 0x388E3C);
        TANK_COLORS.put("depleted_liquid_thorium_salt", 0x1B5E20);

        // --- ELEMENTAL GASES ---
        TANK_COLORS.put("liquid_hydrogen", 0xE1F5FE);
        TANK_COLORS.put("deuterium", 0xE3F2FD);
        TANK_COLORS.put("tritium", 0xBBDEFB);
        TANK_COLORS.put("helium_3", 0xFFEBEE);
        TANK_COLORS.put("helium_4", 0xFFCDD2);
        TANK_COLORS.put("liquid_oxygen", 0x90CAF9);
        TANK_COLORS.put("xenon_gas", 0x9575CD);
        TANK_COLORS.put("chlorine_gas", 0xCCFF90);
        TANK_COLORS.put("mercury", 0xBDBDBD);

        // --- CRUDE & HEAVY OILS ---
        TANK_COLORS.put("crude_oil", 0x0A0A0A);
        TANK_COLORS.put("desulfurized_crude_oil", 0x1A1A1A);
        TANK_COLORS.put("cracked_oil", 0x212121);
        TANK_COLORS.put("desulfurized_cracked_oil", 0x263238);
        TANK_COLORS.put("coal_oil", 0x212121);
        TANK_COLORS.put("coker_oil", 0x3E2723);
        TANK_COLORS.put("hot_crude_oil", 0x1A0A00);
        TANK_COLORS.put("desulfurized_hot_crude_oil", 0x241400);
        TANK_COLORS.put("hot_cracked_oil", 0x331100);
        TANK_COLORS.put("desulfurized_hot_cracked_oil", 0x442200);
        TANK_COLORS.put("heavy_oil", 0x141414);
        TANK_COLORS.put("vacuum_heavy_oil", 0x241400);

        // --- NAPHTHA & LIGHT FRACTIONS ---
        TANK_COLORS.put("naphtha", 0xFFD54F);
        TANK_COLORS.put("desulfurized_naphtha", 0xFFE082);
        TANK_COLORS.put("cracked_naphtha", 0xFFB74D);
        TANK_COLORS.put("coker_naphtha", 0xFF8A65);
        TANK_COLORS.put("reformate", 0x80CBC4);
        TANK_COLORS.put("light_oil", 0x3D3D3D);
        TANK_COLORS.put("desulfurized_light_oil", 0x546E7A);
        TANK_COLORS.put("cracked_light_oil", 0x78909C);
        TANK_COLORS.put("vacuum_light_oil", 0x3D3D3D);
        TANK_COLORS.put("bitumen", 0x050505);
        TANK_COLORS.put("industrial_oil", 0x241400);
        TANK_COLORS.put("heating_oil", 0xFBC02D);
        TANK_COLORS.put("heavy_heating_oil", 0xFFB800);
        TANK_COLORS.put("reclaimed_industrial_oil", 0x8D6E63);
        TANK_COLORS.put("engine_lubricant", 0x4E342E);

        // --- REFINERY GASES ---
        TANK_COLORS.put("natural_gas", 0xFFE082);
        TANK_COLORS.put("coker_gas", 0x5D4037);
        TANK_COLORS.put("petroleum_gas", 0xB0C4DE);
        TANK_COLORS.put("sour_gas", 0xFFFF00);
        TANK_COLORS.put("lpg", 0xFFAB91);
        TANK_COLORS.put("syngas", 0x9E9E9E);
        TANK_COLORS.put("oxyhydrogen", 0xFFFFFF);
        TANK_COLORS.put("aromatic_hydrocarbons", 0xB39DDB);
        TANK_COLORS.put("unsaturated_hydrocarbons", 0x708090);
        TANK_COLORS.put("reformate_gas", 0x58537D);

        // --- DIESEL & KEROSENE VARIANTS ---
        TANK_COLORS.put("diesel", 0xD99D26);
        TANK_COLORS.put("high_cetane_diesel", 0xFFA000);
        TANK_COLORS.put("cracked_diesel", 0x795548);
        TANK_COLORS.put("high_cetane_cracked_diesel", 0x8D6E63);
        TANK_COLORS.put("kerosene", 0xFFEA00);
        TANK_COLORS.put("jet_fuel", 0x64B5F6);
        TANK_COLORS.put("petroil", 0x4DB6AC);
        TANK_COLORS.put("leaded_petroil", 0x009688);

        // --- GASOLINE & BIO-FUELS ---
        TANK_COLORS.put("gasoline", 0xE6C35C);
        TANK_COLORS.put("leaded_gasoline", 0xC0CA33);
        TANK_COLORS.put("coal_gasoline", 0x546E7A);
        TANK_COLORS.put("leaded_coal_gasoline", 0x37474F);
        TANK_COLORS.put("coal_tar_gasoline", 0x212121);
        TANK_COLORS.put("wood_oil", 0x6D4C41);
        TANK_COLORS.put("biogas", 0xDCEDC8);
        TANK_COLORS.put("biofuel", 0x8BC34A);
        TANK_COLORS.put("ethanol", 0x4FC3F7);
        TANK_COLORS.put("fish_oil", 0xFFCC80);
        TANK_COLORS.put("sunflower_seed_oil", 0xFFF176);
        TANK_COLORS.put("bf_rocket_fuel", 0xD32F2F);

        // --- SLURRIES & CHEMICALS ---
        TANK_COLORS.put("salient_green", 0x00FF41);
        TANK_COLORS.put("seeding_slurry", 0xA5D6A7);
        TANK_COLORS.put("colloid", 0xE0F2F1);
        TANK_COLORS.put("vitriol", 0x00ACC1);
        TANK_COLORS.put("ore_slop", 0x795548);
        TANK_COLORS.put("ionic_gel", 0x00B0FF);
        TANK_COLORS.put("hydrogen_peroxide", 0xE1F5FE);
        TANK_COLORS.put("sulfuric_acid", 0xFFEB3B);
        TANK_COLORS.put("nitric_acid", 0xFF8A65);
        TANK_COLORS.put("solvent", 0x00BCD4);
        TANK_COLORS.put("high_performance_solvent", 0x0097A7);
        TANK_COLORS.put("schrabidic_acid", 0x7E57C2);

        // --- NUCLEAR CHEMISTRY ---
        TANK_COLORS.put("uranium_hexafluoride", 0x76FF03);
        TANK_COLORS.put("plutonium_hexafluoride", 0xFFCC00);
        TANK_COLORS.put("schrabidium_trisulfide", 0x5E35B1);
        TANK_COLORS.put("poisonous_mud", 0x33691E);
        TANK_COLORS.put("fullerene_solution", 0x4A148C);

        // --- INDUSTRIAL SOLUTIONS ---
        TANK_COLORS.put("dissolved_egg", 0xFFF59D);
        TANK_COLORS.put("cholesterol_solution", 0xFFFDE7);
        TANK_COLORS.put("sodium_aluminate", 0xECEFF1);
        TANK_COLORS.put("bauxite_solution", 0xFF8A65);
        TANK_COLORS.put("alumina", 0xFFFFFF);
        TANK_COLORS.put("liquid_concrete", 0x9E9E9E);
        TANK_COLORS.put("fracking_solution", 0x4527A0);
        TANK_COLORS.put("lye", 0xFFFFFF);

        // --- TOXIC & COMBAT GASES ---
        TANK_COLORS.put("phosgene", 0xAED581);
        TANK_COLORS.put("mustard_gas", 0x6D4C41);
        TANK_COLORS.put("estradiol_solution", 0xF06292);
        TANK_COLORS.put("nitroglycerin", 0xFF5252);
        TANK_COLORS.put("antimatter", 0x6200EA);
        TANK_COLORS.put("antishrabidium", 0x311B92);

        // --- END-GAME & FLUX ---
        TANK_COLORS.put("experience_juice", 0x80FF00);
        TANK_COLORS.put("ender_juice", 0x0D47A1);
        TANK_COLORS.put("stellar_flux", 0xFFD700);
        TANK_COLORS.put("booster_pheromone", 0xF48FB1);
        TANK_COLORS.put("modified_booster_pheromone", 0xAD1457);
        TANK_COLORS.put("custom_fluid_demo", 0xFF00FF);
    }

    public static int getColor(String type) {
        return TANK_COLORS.getOrDefault(type, 0xFFFFFF);
    }

    public static Set<String> getRegisteredFluids() {
        return TANK_COLORS.keySet();
    }
}
