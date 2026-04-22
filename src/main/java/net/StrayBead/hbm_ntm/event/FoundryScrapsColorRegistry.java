package net.StrayBead.hbm_ntm.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FoundryScrapsColorRegistry {
    private static final Map<String, Integer> FOUNDRY_SCRAPS_COLORS = new HashMap<>();

    static {
        // --- BASICS & MINERALS ---
        register("stone", 0x808080);
        register("carbon", 0x202020);
        register("iron", 0xC0C0C0);
        register("wrought_iron", 0xC0C0C0);
        register("pig_iron", 0x91614C);
        register("meteoric_iron", 0x545454);
        register("gold", 0xFFD700);
        register("redstone", 0xFF0000);
        register("obsidian", 0x1A0033);
        register("hematite", 0x7A2F2F);
        register("malachite", 0x0BDA51);

        // --- NUCLEAR (All isotopes matched to base color) ---
        // Uranium: 0x4B6E1C
        register("uranium", 0x4B6E1C);
        register("uranium_233", 0x4B6E1C);
        register("uranium_235", 0x4B6E1C);
        register("uranium_238", 0x4B6E1C);

        // Thorium: 0x7A5901
        register("thorium_232", 0x7A5901);

        // Plutonium: 0x0055AA
        register("plutonium", 0x0055AA);
        register("reactor_grade_plutonium", 0x0055AA);
        register("plutonium_238", 0x0055AA);
        register("plutonium_239", 0x0055AA);
        register("plutonium_240", 0x0055AA);
        register("plutonium_241", 0x0055AA);

        // Americium: 0x8A2BE2
        register("reactor_grade_americium", 0x8A2BE2);
        register("americium_241", 0x8A2BE2);
        register("americium_242", 0x8A2BE2);

        // Others
        register("neptunium_237", 0x008080);
        register("polonium_210", 0xFF6347);
        register("technetium_99", 0x989898);
        register("radium_226", 0xFFFFFF);
        register("actinium_227", 0xE0FFFF);
        register("cobalt_60", 0x0047AB);
        register("gold_198", 0xFFD700);
        register("lead_209", 0x505050);

        // --- EXOTICS ---
        register("schrabidium", 0x00FFFF);
        register("ferric_schrabidate", 0x00FFFF);
        register("schraranium", 0x00FFFF);
        register("solinium", 0xFF4500);
        register("ghiorsium_336", 0x2F4F4F);
        register("saturnite", 0x4B0082);
        register("dineutronium", 0x0000FF);

        // --- METALS ---
        register("titanium", 0xD3D3D3);
        register("copper", 0xB87333);
        register("minecraft_grade_copper", 0xB87333);
        register("tungsten", 0x4D4D4D);
        register("magnetized_tungsten", 0x4D4D4D);
        register("aluminum", 0xD9D9D9);
        register("lead", 0x505050);
        register("bismuth", 0xE0C0C0);
        register("arsenic", 0x505050);
        register("tantalum", 0x404050);
        register("neodymium", 0xB0B0B0);
        register("niobium", 0x707070);
        register("beryllium", 0xD1D1D1);
        register("cobalt", 0x0047AB);
        register("boron", 0x606060);
        register("borax", 0x606060);
        register("lanthanium", 0xE0E0E0);
        register("zirconium", 0xA0A0A0);
        register("sodium", 0xD1D1D1);
        register("strontium", 0xE6E6FA);
        register("calcium", 0xFFF5EE);
        register("lithium", 0xD3D3D3);
        register("cadmium", 0xB0C4DE);
        register("silicon", 0x3C3C3C);
        register("asbestos", 0xE8E8E8);
        register("osmiridium", 0x666699);

        // --- ALLOYS ---
        register("steel", 0x708090);
        register("high_speed_steel", 0x708090);
        register("technetium_steel", 0x708090);
        register("cadmium_steel", 0x708090);
        register("combine_steel", 0x708090);
        register("weapon_steel", 0x1C1C1C);
        register("advanced_alloy", 0x98FB98);
        register("desh", 0x804000);
        register("starmetal", 0x4682B4);
        register("ferrouranium", 0x4F4F2F);
        register("bismuth_bronze", 0xCD7F32);
        register("arsenic_bronze", 0x8B4513);
        register("bscco", 0x00FF7F);
        register("gunmetal", 0x2A3439);

        // --- MISC ---
        register("flux", 0xFFFFFF);
        register("slag", 0x303030);
        register("poisonous_mud", 0x4B5320);
    }

    private static void register(String type, int color) {
        FOUNDRY_SCRAPS_COLORS.put(type, color);
    }

    public static int getColor(String type) {
        return FOUNDRY_SCRAPS_COLORS.getOrDefault(type, 0xFFFFFF);
    }

    public static Set<String> getRegisteredFluids() {
        return FOUNDRY_SCRAPS_COLORS.keySet();
    }
}
