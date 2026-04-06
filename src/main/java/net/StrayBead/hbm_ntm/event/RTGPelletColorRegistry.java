package net.StrayBead.hbm_ntm.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RTGPelletColorRegistry {
    private static final Map<String, Integer> PELLET_COLORS = new HashMap<>();

    static {
        PELLET_COLORS.put("radium_226", 0xD6EAF8);

        PELLET_COLORS.put("weak_uranium", 0x4D4D4D);

        PELLET_COLORS.put("plutonium_238", 0x6E5A6E);

        PELLET_COLORS.put("strontium_90", 0xE0E0E0);

        PELLET_COLORS.put("cobalt_60", 0x283593);

        PELLET_COLORS.put("polonium_210", 0x81D4FA);

        PELLET_COLORS.put("actinium_227", 0xB2EBF2);

        PELLET_COLORS.put("americium_241", 0xD7BDE2);

        PELLET_COLORS.put("gold_198", 0xC5B358);

        PELLET_COLORS.put("lead_209", 0x37474F);

        PELLET_COLORS.put("tritium_deuterium_cake", 0xE1F5FE);

        PELLET_COLORS.put("decayed_mercury", 0x5F5F5F);

        PELLET_COLORS.put("decayed_neptunium", 0x2E3B2E);

        PELLET_COLORS.put("decayed_lead", 0x212121);

        PELLET_COLORS.put("decayed_zirconium", 0x9E9E9E);

        PELLET_COLORS.put("decayed_nickel", 0x7A8070);
    }

    public static int getColor(String type) {
        return PELLET_COLORS.getOrDefault(type, 0xFFFFFF);
    }

    public static Set<String> getRegisteredFluids() {
        return PELLET_COLORS.keySet();
    }
}
