package net.StrayBead.hbm_ntm.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FluidColorRegistry {
    private static final Map<String, Integer> FLUID_COLORS = new HashMap<>();

    static {
        // --- OILS & FUELS ---
        FLUID_COLORS.put("crude_oil", 0x0A0A0A);
        FLUID_COLORS.put("hot_crude_oil", 0x1A0A00);
        FLUID_COLORS.put("desulfurized_hot_crude_oil", 0x241400);
        FLUID_COLORS.put("vacuum_heavy_oil", 0x241400);
        FLUID_COLORS.put("vacuum_light_oil", 0x3D3D3D);
        FLUID_COLORS.put("industrial_oil", 0x241400);
        FLUID_COLORS.put("heavy_oil", 0x141414);
        FLUID_COLORS.put("heavy_heating_oil", 0xFFB800);
        FLUID_COLORS.put("light_oil", 0x3D3D3D);
        FLUID_COLORS.put("diesel", 0xD99D26);
        FLUID_COLORS.put("gasoline", 0xE6C35C);
        FLUID_COLORS.put("kerosene", 0xFFEA00);
        FLUID_COLORS.put("naphtha", 0xFFD54F);
        FLUID_COLORS.put("bitumen", 0x050505);
        FLUID_COLORS.put("jet_fuel", 0x64B5F6);
        FLUID_COLORS.put("water", 0x0000FF);
        FLUID_COLORS.put("unsaturated_hydrocarbons", 0x708090);
        FLUID_COLORS.put("petroleum_gas", 0xB0C4DE);
        FLUID_COLORS.put("reformate_gas", 0x58537D);
        FLUID_COLORS.put("vitroil", 0x575037);

        // --- NUCLEAR & RADIOACTIVE ---
        FLUID_COLORS.put("liquid_nuclear_waste", 0x4DFF00);
        FLUID_COLORS.put("gaseous_nuclear_waste", 0x99FF66);
        FLUID_COLORS.put("deuterium", 0xE3F2FD);
        FLUID_COLORS.put("tritium", 0xBBDEFB);
        FLUID_COLORS.put("heavy_water", 0x2196F3);
        FLUID_COLORS.put("plutonium_hexaflouride", 0xFFCC00);
        FLUID_COLORS.put("antimatter", 0x6200EA);
        FLUID_COLORS.put("antischrabidium", 0x311B92);
        FLUID_COLORS.put("salient_green", 0x00FF41);

        // --- CHEMICALS & ACIDS ---
        FLUID_COLORS.put("sulfuric_acid", 0xFFEB3B);
        FLUID_COLORS.put("nitric_acid", 0xFF8A65);
        FLUID_COLORS.put("schrabidic_acid", 0x7E57C2);
        FLUID_COLORS.put("mercury", 0xBDBDBD);
        FLUID_COLORS.put("liquid_sodium", 0xF5F5F5);
        FLUID_COLORS.put("liquid_oxygen", 0x90CAF9);
        FLUID_COLORS.put("liquid_hydrogen", 0xE1F5FE);
        FLUID_COLORS.put("chlorocalcite_solution", 0x8BC34A);
        FLUID_COLORS.put("lye", 0xFFFFFF);
        FLUID_COLORS.put("ore_slop", 0x4C5737);

        // --- GASES & STEAM ---
        FLUID_COLORS.put("steam", 0xE0E0E0);
        FLUID_COLORS.put("hot_steam", 0xFF9800);
        FLUID_COLORS.put("natural_gas", 0xFFE082);
        FLUID_COLORS.put("syngas", 0x9E9E9E);
        FLUID_COLORS.put("mustard_gas", 0x6D4C41);
        FLUID_COLORS.put("phosgene", 0xAED581);
        FLUID_COLORS.put("carbon_dioxide", 0x212121);

        // --- BIO & OTHERS ---
        FLUID_COLORS.put("experience_juice", 0x80FF00);
        FLUID_COLORS.put("ender_juice", 0x0D47A1);
        FLUID_COLORS.put("fish_oil", 0xFFCC80);
        FLUID_COLORS.put("ethanol", 0x4FC3F7);
        FLUID_COLORS.put("stellar_flux", 0xFFD700);
    }

    public static int getColor(String type) {
        if ("multi_fluid_identifier".equals(type)) {
            return java.awt.Color.HSBtoRGB((System.currentTimeMillis() % 2000) / 2000f, 0.8f, 1.0f);
        }
        return FLUID_COLORS.getOrDefault(type, 0xFFFFFF);
    }

    public static Set<String> getRegisteredFluids() {
        return FLUID_COLORS.keySet();
    }
}
