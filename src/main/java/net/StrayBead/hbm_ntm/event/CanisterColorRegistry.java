package net.StrayBead.hbm_ntm.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CanisterColorRegistry {
    private static final Map<String, Integer> CANISTER_COLORS = new HashMap<>();

    static {
        CANISTER_COLORS.put("crude_oil", 0x0A0A0A);
        CANISTER_COLORS.put("desulfurized_crude_oil", 0x1A1A1A);
        CANISTER_COLORS.put("cracked_oil", 0x212121);
        CANISTER_COLORS.put("desulfurized_cracked_oil", 0x263238);
        CANISTER_COLORS.put("coal_oil", 0x212121);
        CANISTER_COLORS.put("heavy_oil", 0x141414);
        CANISTER_COLORS.put("vacuum_heavy_oil", 0x241400);

        // --- NAPHTHA & LIGHT FRACTIONS ---
        CANISTER_COLORS.put("naphtha", 0xFFD54F);
        CANISTER_COLORS.put("desulfurized_naphtha", 0xFFE082);
        CANISTER_COLORS.put("cracked_naphtha", 0xFFB74D);
        CANISTER_COLORS.put("reformate", 0x80CBC4);
        CANISTER_COLORS.put("light_oil", 0x3D3D3D);
        CANISTER_COLORS.put("desulfurized_light_oil", 0x546E7A);
        CANISTER_COLORS.put("cracked_light_oil", 0x78909C);
        CANISTER_COLORS.put("vacuum_light_oil", 0x3D3D3D);
        CANISTER_COLORS.put("bitumen", 0x050505);
        CANISTER_COLORS.put("industrial_oil", 0x241400);
        CANISTER_COLORS.put("heating_oil", 0xFBC02D);
        CANISTER_COLORS.put("heavy_heating_oil", 0xFFB800);
        CANISTER_COLORS.put("reclaimed_industrial_oil", 0x8D6E63);
        CANISTER_COLORS.put("engine_lubricant", 0x4E342E);

        // --- DIESEL, KEROSENE & SPECIALTY ---
        CANISTER_COLORS.put("btx", 0xB39DDB); // Aromatic mix
        CANISTER_COLORS.put("diesel", 0xD99D26);
        CANISTER_COLORS.put("high_cetane_diesel", 0xFFA000);
        CANISTER_COLORS.put("cracked_diesel", 0x795548);
        CANISTER_COLORS.put("high_cetane_cracked_diesel", 0x8D6E63);
        CANISTER_COLORS.put("kerosene", 0xFFEA00);
        CANISTER_COLORS.put("jet_fuel", 0x64B5F6);
        CANISTER_COLORS.put("petroil", 0x4DB6AC);
        CANISTER_COLORS.put("leaded_petroil", 0x009688);

        // --- GASOLINE & BIO-FUELS ---
        CANISTER_COLORS.put("gasoline", 0xE6C35C);
        CANISTER_COLORS.put("leaded_gasoline", 0xC0CA33);
        CANISTER_COLORS.put("coal_gasoline", 0x546E7A);
        CANISTER_COLORS.put("leaded_coal_gasoline", 0x37474F);
        CANISTER_COLORS.put("coal_tar_creosote", 0x212121);
        CANISTER_COLORS.put("wood_oil", 0x6D4C41);
        CANISTER_COLORS.put("biofuel", 0x8BC34A);
        CANISTER_COLORS.put("ethanol", 0x4FC3F7);
        CANISTER_COLORS.put("nitan_octane_super_fuel", 0xD32F2F);

        // --- SOLUTIONS & SLURRIES ---
        CANISTER_COLORS.put("seeding_slurry", 0xA5D6A7);
        CANISTER_COLORS.put("solvent", 0x00BCD4);
        CANISTER_COLORS.put("fracking_solution", 0x4527A0);
        CANISTER_COLORS.put("Napalm_b_4711", 0xFF5722);
    }

    public static int getColor(String type) {
        return CANISTER_COLORS.getOrDefault(type, 0xFFFFFF);
    }

    public static Set<String> getRegisteredFluids() {
        return CANISTER_COLORS.keySet();
    }
}
