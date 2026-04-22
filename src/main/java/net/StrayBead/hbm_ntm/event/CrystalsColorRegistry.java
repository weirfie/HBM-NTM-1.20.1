package net.StrayBead.hbm_ntm.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CrystalsColorRegistry {
    private static final Map<String, Integer> CRYSTALS_COLORS = new HashMap<>();

    static {
        // --- VANILLA / BASE ---
        register("iron", 0xC6C6C6);
        register("gold", 0xFFD700);
        register("lapis", 0x224BAF);
        register("diamond", 0x33EBCB);
        register("copper", 0xFF7F50);

        // --- NUCLEAR & RADIOACTIVES ---
        register("uranium", 0x4B6E1C);
        register("thorium", 0x7A5901);
        register("plutonium", 0x333333);
        register("schraranium", 0x3BB273);

        // --- HBM SPECIALS / HIGH-TIER ---
        register("shrabidium", 0x00FFFF);
        register("starmetal", 0x3BE3FF);
        register("trixite", 0xFFD300);
        register("osmiridium", 0x667788);

        // --- METALS & EARTHS ---
        register("titanium", 0xDCDCDC);
        register("tungsten", 0x2B2B2B);
        register("aluminum", 0xD6D6D6);
        register("beryllium", 0xD1D1D1);
        register("lead", 0x505050);
        register("cobalt", 0x002E6E);

        // --- CHEMICALS & GEMS ---
        register("sulfur", 0xEEEE00);
        register("niter", 0xE6E6FA);
        register("fluorite", 0xA200FF);
        register("phosphorus", 0x8B0000);
        register("lithium", 0xFFFAFA);
        register("cinnabar", 0x9E1A1A);
    }

    private static void register(String type, int color) {
        CRYSTALS_COLORS.put(type, color);
    }

    public static int getColor(String type) {
        return CRYSTALS_COLORS.getOrDefault(type, 0xFFFFFF);
    }

    public static Set<String> getRegisteredCrystals() {
        return CRYSTALS_COLORS.keySet();
    }
}
