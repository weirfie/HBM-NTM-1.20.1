package net.StrayBead.hbm_ntm.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BilletColorRegistry {
    private static final Map<String, Integer> BILLET_COLORS = new HashMap<>();

    static {
        // URANIUM (Greenish Tints)
        register("uranium", 0x4B6E1C);
        register("uranium_233", 0x5D8A22);
        register("uranium_235", 0x6E9B28);
        register("uranium_238", 0x3A5416);
        register("uranium_fuel", 0x7FFF00);

        // THORIUM (Brownish/Amber Tints)
        register("thorium_232", 0x7A5901);
        register("thorium_fuel", 0xA67A01);

        // PLUTONIUM (Blue/Cyan Tints)
        register("plutonium", 0x0055AA);
        register("plutonium_238", 0x0066CC);
        register("plutonium_239", 0x0077EE);
        register("plutonium_240", 0x004488);
        register("plutonium_241", 0x3399FF);
        register("pu_241", 0x3399FF);
        register("plutonium_fuel", 0x00FFFF);
        register("reactor_grade_plutonium", 0x003366);

        // AMERICIUM (Purple/Pink Tints)
        register("americium_241", 0x8A2BE2);
        register("americium_242", 0x9400D3);
        register("americium_fuel", 0xBA55D3);
        register("reactor_grade_americium", 0x4B0082);
        register("reactor_grade_americium_zfb", 0x5D3A8F);

        // NEPTUNIUM (Teal/Dark Green Tints)
        register("neptunium", 0x008080);
        register("neptunium_fuel", 0x20B2AA);

        // SCHRABIDIUM (Magenta/Bright Purple Tints)
        register("schrabidium", 0x00FFFF);
        register("schrabidium_fuel", 0xD600D6);
        register("low_enriched_schrabidium_fuel", 0xA300A3);
        register("highly_enriched_schrabidium_fuel", 0xFF55FF);
        register("euphemium", 0xFFB6C1);

        // AUSTRALIUM / ASTRALIUM (Gold/Bright Yellow Tints)
        register("australium", 0xFFD700);
        register("lesser_australium", 0xE6C300);
        register("greater_astralium", 0xFFF700);

        // COBALT (Deep Blue)
        register("cobalt", 0x0047AB);
        register("cobalt_60", 0x002E6E);

        // METALS & BASICS
        register("beryllium", 0xD1D1D1);
        register("bismuth", 0xE0C0C0);
        register("bismuth_zfb", 0xC0A0A0);
        register("zirconium", 0xA0A0A0);
        register("technetium_99", 0x989898);
        register("strontium_90", 0xE6E6FA);
        register("polonium_210", 0xFF6347);
        register("po210be", 0xFF7F50);
        register("ra226be", 0xF5F5DC);
        register("pu238be", 0xADD8E6);

        // RARE & EXOTIC
        register("solinium", 0xFF4500);
        register("ghiorsium_336", 0x2F4F4F);
        register("yharonite", 0xFF8C00);
        register("radium_226", 0xFFFFFF);
        register("actinium_227", 0xE0FFFF);
        register("lead_209", 0x505050);
        register("gold_198", 0xCCAC00);
        register("flashgold", 0xFFE4B5);
        register("flashlead", 0x778899);
        register("mox_fuel", 0x556B2F);
    }

    private static void register(String type, int color) {
        BILLET_COLORS.put(type, color);
    }

    public static int getColor(String type) {
        return BILLET_COLORS.getOrDefault(type, 0xFFFFFF);
    }

    public static Set<String> getRegisteredFluids() {
        return BILLET_COLORS.keySet();
    }
}
