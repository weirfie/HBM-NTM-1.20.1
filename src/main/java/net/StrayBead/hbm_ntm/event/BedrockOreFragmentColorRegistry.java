package net.StrayBead.hbm_ntm.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BedrockOreFragmentColorRegistry {
    private static final Map<String, Integer> ORE_FRAGMENT_COLORS = new HashMap<>();

    static {
        // --- VANILLA & GEMS ---
        register("coal", 0x262626);
        register("lignite", 0x3B270C);
        register("diamond", 0x33EBCB);
        register("iron", 0xC6C6C6);
        register("gold", 0xFFD700);
        register("redstone", 0xFF0000);
        register("emerald", 0x17DD62);

        // --- NUCLEAR & RADIOACTIVES ---
        register("uranium", 0x4B6E1C);
        register("uranium_238", 0x3E5916);
        register("thorium_232", 0x7A5901);
        register("polonium_210", 0xFF6347);
        register("technetium_99", 0x778899);
        register("radium_226", 0xFFFFFF);

        // --- METALS & EARTHS ---
        register("titanium", 0xDCDCDC);
        register("copper", 0xFF7F50);
        register("tungsten", 0x2B2B2B);
        register("aluminum", 0xD6D6D6);
        register("lead", 0x505050);
        register("bismuth", 0xE0C0C0);
        register("tantalum", 0x4F4F4F);
        register("neodymium", 0xBDB76B);
        register("niobium", 0x708090);
        register("beryllium", 0xD1D1D1);
        register("cobalt", 0x002E6E);
        register("zirconium", 0xA0A0A0);
        register("lanthanium", 0xE5E4E2);

        // --- SALTS, CHEMICALS & OCHRES ---
        register("bauxite", 0x9B4F1A);
        register("cryolite", 0x00FFFF);
        register("boron", 0x808000);
        register("borax", 0xFFFAFA);
        register("sodium", 0xF5F5F5);
        register("sodalite", 0x1E3B5A);
        register("strontium", 0xD3D3D3);
        register("lithium", 0xFFFAFA);
        register("sulfur", 0xEEEE00);
        register("niter", 0xE6E6FA);
        register("fluorite", 0xA200FF);
        register("red_phosphorus", 0x8B0000);
        register("chlorocalcite", 0xF5F5F5);
        register("molysite", 0xFFD300);
        register("cinnabar", 0x9E1A1A);
        register("silicon", 0x4F4F4F);
        register("asbestos", 0xDCDCDC);
        register("rare_earth", 0x708090);
    }

    private static void register(String type, int color) {
        ORE_FRAGMENT_COLORS.put(type, color);
    }

    public static int getColor(String type) {
        return ORE_FRAGMENT_COLORS.getOrDefault(type, 0xFFFFFF);
    }

    public static Set<String> getRegisteredFluids() {
        return ORE_FRAGMENT_COLORS.keySet();
    }
}
