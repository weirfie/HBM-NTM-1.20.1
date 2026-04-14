package net.StrayBead.hbm_ntm.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PowderColorRegistry {
    private static final Map<String, Integer> POWDER_COLORS = new HashMap<>();

    static {
        // VANILLA & GEMS
        register("gold", 0xFFD700);
        register("lapis_lazuli", 0x224BAF);
        register("diamond", 0x33EBCB);
        register("emerald", 0x17DD62);

        // NUCLEAR & RADIOACTIVES
        register("uranium", 0x4B6E1C);
        register("plutonium", 0x0055AA);
        register("neptunium", 0x008080);
        register("thorium", 0x7A5901);
        register("polonium_210", 0xFF6347);
        register("cobalt_60", 0x002E6E);
        register("strontium_90", 0xE6E6FA);
        register("radium_226", 0xFFFFFF);
        register("actinium", 0xE0FFFF);
        register("caesium_137", 0xE6C300);
        register("caesium", 0xFFD300);
        register("iodine_131", 0x8B008B);
        register("iodine", 0x4B0082);
        register("xenon_135", 0x4169E1);
        register("astatine_209", 0x191919);
        register("astatine", 0x2F4F4F);
        register("tennessine", 0x483D8B);

        // METALS & ALLOYS
        register("titanium", 0xDCDCDC);
        register("copper", 0xFF7F50);
        register("red_copper", 0xFF4500);
        register("lead", 0x505050);
        register("tungsten", 0x2B2B2B);
        register("magnetized_tungsten", 0x1A1A1A);
        register("technetium_steel", 0x778899);
        register("cmb_steel", 0x1E3B5A);
        register("high_speed_steel", 0xB0C4DE);
        register("advanced_alloy", 0xA9A9A9);
        register("bismuth", 0xE0C0C0);
        register("beryllium", 0xD1D1D1);
        register("zirconium", 0xA0A0A0);
        register("niobium", 0x708090);
        register("cadmium", 0xBFBFBF);
        register("tantalum", 0x4F4F4F);
        register("impure_osmiridium", 0x696969);

        // EXOTICS & FICTIONAL
        register("schrabidium", 0xFF00FF);
        register("ferric_schrabidate", 0x8B0000);
        register("australium", 0xFFD700);
        register("paleogenite", 0x4682B4);
        register("tektite", 0x556B2F);
        register("chlorophyte", 0x32CD32);
        register("euphemium", 0xFFB6C1);
        register("desh", 0xCC7722);
        register("meteorite", 0x3D2B1F);
        register("schrabidium", 0x00FFFF);
        register("dineutronium", 0x00008B);

        // CHEMICALS & SYNTHETICS
        register("lithium", 0xFFFAFA);
        register("calcium", 0xF5F5F5);
        register("strontium", 0xD3D3D3);
        register("boron", 0x808000);
        register("bromine", 0x8B4513);
        register("cerium", 0xC0C0C0);
        register("lanthanium", 0xE5E4E2);
        register("polymer", 0xF0F8FF);
        register("bakelite", 0x8B4513);
        register("asbestos", 0xDCDCDC);
        register("lignite", 0x3B270C);
        register("limestone", 0xD3D3D3);
        register("neodymium", 0xBDB76B);

        // MISC & SPECIAL
        register("gold_198", 0xCCAC00);
        register("cryo", 0x00FFFF);
        register("poison", 0xADFF2F);
        register("energy", 0xFF69B4);
    }

    private static void register(String type, int color) {
        POWDER_COLORS.put(type, color);
    }

    public static int getColor(String type) {
        return POWDER_COLORS.getOrDefault(type, 0xFFFFFF);
    }

    public static Set<String> getRegisteredFluids() {
        return POWDER_COLORS.keySet();
    }
}
