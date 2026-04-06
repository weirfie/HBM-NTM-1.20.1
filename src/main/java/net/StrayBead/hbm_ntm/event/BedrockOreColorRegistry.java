package net.StrayBead.hbm_ntm.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BedrockOreColorRegistry {
    private static final Map<String, Integer> BEDROCK_ORE_COLORS = new HashMap<>();

    static {
        BEDROCK_ORE_COLORS.put("iron", 0xBCBCBC);
        BEDROCK_ORE_COLORS.put("centrifuged_iron", 0xC7C7C7);
        BEDROCK_ORE_COLORS.put("cleaned_iron", 0xD2D2D2);
        BEDROCK_ORE_COLORS.put("separated_iron", 0xDDDDDD);
        BEDROCK_ORE_COLORS.put("purified_iron", 0xE8E8E8);
        BEDROCK_ORE_COLORS.put("nitrated_iron", 0xF3F3F3);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_iron", 0xF9F9F9);
        BEDROCK_ORE_COLORS.put("deep_cleaned_iron", 0xFFFFFF);
        BEDROCK_ORE_COLORS.put("seared_iron", 0xFFFFFF);
        BEDROCK_ORE_COLORS.put("enriched_iron", 0xFFFFFF);

        BEDROCK_ORE_COLORS.put("copper", 0xBD664E);
        BEDROCK_ORE_COLORS.put("centrifuged_copper", 0xCE7359);
        BEDROCK_ORE_COLORS.put("cleaned_copper", 0xDE8064);
        BEDROCK_ORE_COLORS.put("separated_copper", 0xEB8D70);
        BEDROCK_ORE_COLORS.put("purified_copper", 0xF59B7E);
        BEDROCK_ORE_COLORS.put("nitrated_copper", 0xFFA98C);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_copper", 0xFFB99D);
        BEDROCK_ORE_COLORS.put("deep_cleaned_copper", 0xFFC9AF);
        BEDROCK_ORE_COLORS.put("seared_copper", 0xFFDBC2);
        BEDROCK_ORE_COLORS.put("enriched_copper", 0xFFEBD6);

        BEDROCK_ORE_COLORS.put("borax", 0xBEC9C9);
        BEDROCK_ORE_COLORS.put("centrifuged_borax", 0xC9D5D5);
        BEDROCK_ORE_COLORS.put("cleaned_borax", 0xD4E1E1);
        BEDROCK_ORE_COLORS.put("separated_borax", 0xDFECEC);
        BEDROCK_ORE_COLORS.put("purified_borax", 0xEAF8F8);
        BEDROCK_ORE_COLORS.put("nitrated_borax", 0xF5FFFF);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_borax", 0xFBFFFF);
        BEDROCK_ORE_COLORS.put("deep_cleaned_borax", 0xFFFFFF);
        BEDROCK_ORE_COLORS.put("seared_borax", 0xFFFFFF);
        BEDROCK_ORE_COLORS.put("enriched_borax", 0xFFFFFF);

        BEDROCK_ORE_COLORS.put("asbestos", 0xA7A7A7);
        BEDROCK_ORE_COLORS.put("centrifuged_asbestos", 0xB4B4B4);
        BEDROCK_ORE_COLORS.put("cleaned_asbestos", 0xC1C1C1);
        BEDROCK_ORE_COLORS.put("separated_asbestos", 0xCECECE);
        BEDROCK_ORE_COLORS.put("purified_asbestos", 0xDBDBDB);
        BEDROCK_ORE_COLORS.put("nitrated_asbestos", 0xE8E8E8);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_asbestos", 0xF5F5F5);
        BEDROCK_ORE_COLORS.put("deep_cleaned_asbestos", 0xFFFFFF);
        BEDROCK_ORE_COLORS.put("seared_asbestos", 0xFFFFFF);
        BEDROCK_ORE_COLORS.put("enriched_asbestos", 0xFFFFFF);

        BEDROCK_ORE_COLORS.put("niobium", 0x6E8C9B);
        BEDROCK_ORE_COLORS.put("centrifuged_niobium", 0x7A9BAA);
        BEDROCK_ORE_COLORS.put("cleaned_niobium", 0x86ABB9);
        BEDROCK_ORE_COLORS.put("separated_niobium", 0x92BCC8);
        BEDROCK_ORE_COLORS.put("purified_niobium", 0x9ECDD7);
        BEDROCK_ORE_COLORS.put("nitrated_niobium", 0xAADEE6);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_niobium", 0xB6EFF5);
        BEDROCK_ORE_COLORS.put("deep_cleaned_niobium", 0xC2FFFF);
        BEDROCK_ORE_COLORS.put("seared_niobium", 0xD1FFFF);
        BEDROCK_ORE_COLORS.put("enriched_niobium", 0xE0FFFF);

        BEDROCK_ORE_COLORS.put("titanium", 0x909090);
        BEDROCK_ORE_COLORS.put("centrifuged_titanium", 0x9F9F9F);
        BEDROCK_ORE_COLORS.put("cleaned_titanium", 0xAEAEAE);
        BEDROCK_ORE_COLORS.put("separated_titanium", 0xBDBDBD);
        BEDROCK_ORE_COLORS.put("purified_titanium", 0xCCCCCC);
        BEDROCK_ORE_COLORS.put("nitrated_titanium", 0xDBDBDB);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_titanium", 0xEAEAEA);
        BEDROCK_ORE_COLORS.put("deep_cleaned_titanium", 0xF9F9F9);
        BEDROCK_ORE_COLORS.put("seared_titanium", 0xFFFFFF);
        BEDROCK_ORE_COLORS.put("enriched_titanium", 0xFFFFFF);

        BEDROCK_ORE_COLORS.put("tungsten", 0x3D3D3D);
        BEDROCK_ORE_COLORS.put("centrifuged_tungsten", 0x484848);
        BEDROCK_ORE_COLORS.put("cleaned_tungsten", 0x535353);
        BEDROCK_ORE_COLORS.put("separated_tungsten", 0x5E5E5E);
        BEDROCK_ORE_COLORS.put("purified_tungsten", 0x696969);
        BEDROCK_ORE_COLORS.put("nitrated_tungsten", 0x747474);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_tungsten", 0x7F7F7F);
        BEDROCK_ORE_COLORS.put("deep_cleaned_tungsten", 0x8A8A8A);
        BEDROCK_ORE_COLORS.put("seared_tungsten", 0x959595);
        BEDROCK_ORE_COLORS.put("enriched_tungsten", 0xA0A0A0);

        BEDROCK_ORE_COLORS.put("gold", 0xDDBB00);
        BEDROCK_ORE_COLORS.put("centrifuged_gold", 0xEBC910);
        BEDROCK_ORE_COLORS.put("cleaned_gold", 0xF9D720);
        BEDROCK_ORE_COLORS.put("separated_gold", 0xFFE530);
        BEDROCK_ORE_COLORS.put("purified_gold", 0xFFF340);
        BEDROCK_ORE_COLORS.put("nitrated_gold", 0xFFFF50);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_gold", 0xFFFF70);
        BEDROCK_ORE_COLORS.put("deep_cleaned_gold", 0xFFFF90);
        BEDROCK_ORE_COLORS.put("seared_gold", 0xFFFFB0);
        BEDROCK_ORE_COLORS.put("enriched_gold", 0xFFFFD0);

        BEDROCK_ORE_COLORS.put("thorium", 0x2A3D2A);
        BEDROCK_ORE_COLORS.put("centrifuged_thorium", 0x344D34);
        BEDROCK_ORE_COLORS.put("cleaned_thorium", 0x3E5D3E);
        BEDROCK_ORE_COLORS.put("separated_thorium", 0x486D48);
        BEDROCK_ORE_COLORS.put("purified_thorium", 0x527D52);
        BEDROCK_ORE_COLORS.put("nitrated_thorium", 0x5C8D5C);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_thorium", 0x669D66);
        BEDROCK_ORE_COLORS.put("deep_cleaned_thorium", 0x70AD70);
        BEDROCK_ORE_COLORS.put("seared_thorium", 0x8ABD8A);
        BEDROCK_ORE_COLORS.put("enriched_thorium", 0xA4CDA4);

        BEDROCK_ORE_COLORS.put("chlorocalcite", 0xBD6B1E);
        BEDROCK_ORE_COLORS.put("centrifuged_chlorocalcite", 0xCE7A2A);
        BEDROCK_ORE_COLORS.put("cleaned_chlorocalcite", 0xDF8936);
        BEDROCK_ORE_COLORS.put("separated_chlorocalcite", 0xF09842);
        BEDROCK_ORE_COLORS.put("purified_chlorocalcite", 0xFFA74E);
        BEDROCK_ORE_COLORS.put("nitrated_chlorocalcite", 0xFFB65A);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_chlorocalcite", 0xFFC566);
        BEDROCK_ORE_COLORS.put("deep_cleaned_chlorocalcite", 0xFFD472);
        BEDROCK_ORE_COLORS.put("seared_chlorocalcite", 0xFFE37E);
        BEDROCK_ORE_COLORS.put("enriched_chlorocalcite", 0xFFF28A);

        BEDROCK_ORE_COLORS.put("flourite", 0x30A58F);
        BEDROCK_ORE_COLORS.put("centrifuged_flourite", 0x3EB8A0);
        BEDROCK_ORE_COLORS.put("cleaned_flourite", 0x4CCBB1);
        BEDROCK_ORE_COLORS.put("separated_flourite", 0x5ADEC2);
        BEDROCK_ORE_COLORS.put("purified_flourite", 0x68F1D3);
        BEDROCK_ORE_COLORS.put("nitrated_flourite", 0x76FFE4);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_flourite", 0x84FFF0);
        BEDROCK_ORE_COLORS.put("deep_cleaned_flourite", 0x92FFFF);
        BEDROCK_ORE_COLORS.put("seared_flourite", 0xA1FFFF);
        BEDROCK_ORE_COLORS.put("enriched_flourite", 0xB0FFFF);

        BEDROCK_ORE_COLORS.put("hematite", 0x6D1A1A);
        BEDROCK_ORE_COLORS.put("centrifuged_hematite", 0x7F2222);
        BEDROCK_ORE_COLORS.put("cleaned_hematite", 0x912A2A);
        BEDROCK_ORE_COLORS.put("separated_hematite", 0xA33232);
        BEDROCK_ORE_COLORS.put("purified_hematite", 0xB53A3A);
        BEDROCK_ORE_COLORS.put("nitrated_hematite", 0xC74242);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_hematite", 0xD94A4A);
        BEDROCK_ORE_COLORS.put("deep_cleaned_hematite", 0xEB5252);
        BEDROCK_ORE_COLORS.put("seared_hematite", 0xFD5A5A);
        BEDROCK_ORE_COLORS.put("enriched_hematite", 0xFF6C6C);

        BEDROCK_ORE_COLORS.put("malachite", 0x008040);
        BEDROCK_ORE_COLORS.put("centrifuged_malachite", 0x00964B);
        BEDROCK_ORE_COLORS.put("cleaned_malachite", 0x00AC56);
        BEDROCK_ORE_COLORS.put("separated_malachite", 0x00C261);
        BEDROCK_ORE_COLORS.put("purified_malachite", 0x00D86C);
        BEDROCK_ORE_COLORS.put("nitrated_malachite", 0x00EE77);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_malachite", 0x00FF82);
        BEDROCK_ORE_COLORS.put("deep_cleaned_malachite", 0x2DFF92);
        BEDROCK_ORE_COLORS.put("seared_malachite", 0x5AFFB2);
        BEDROCK_ORE_COLORS.put("enriched_malachite", 0x87FFD2);

        BEDROCK_ORE_COLORS.put("neodymium", 0x5C4D5C);
        BEDROCK_ORE_COLORS.put("centrifuged_neodymium", 0x6B5A6B);
        BEDROCK_ORE_COLORS.put("cleaned_neodymium", 0x7A677A);
        BEDROCK_ORE_COLORS.put("separated_neodymium", 0x897489);
        BEDROCK_ORE_COLORS.put("purified_neodymium", 0x988198);
        BEDROCK_ORE_COLORS.put("nitrated_neodymium", 0xA78EA7);
        BEDROCK_ORE_COLORS.put("nitrocrystalline_neodymium", 0xB69BB6);
        BEDROCK_ORE_COLORS.put("deep_cleaned_neodymium", 0xC5A8C5);
        BEDROCK_ORE_COLORS.put("seared_neodymium", 0xD4B5D4);
        BEDROCK_ORE_COLORS.put("enriched_neodymium", 0xE3C2E3);
    }

    public static int getColor(String type) {
        return BEDROCK_ORE_COLORS.getOrDefault(type, 0xFFFFFF);
    }

    public static Set<String> getRegisteredFluids() {
        return BEDROCK_ORE_COLORS.keySet();
    }
}
