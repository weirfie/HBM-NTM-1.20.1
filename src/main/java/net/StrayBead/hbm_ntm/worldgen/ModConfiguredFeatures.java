package net.StrayBead.hbm_ntm.worldgen;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_URANIUM_ORE_KEY = registerKey("uranium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_THORIUM_ORE_KEY = registerKey("thorium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_BERYLLIUM_ORE_KEY = registerKey("beryllium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_TITANIUM_ORE_KEY = registerKey("titanium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_LEAD_ORE_KEY = registerKey("lead_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_SULFUR_ORE_KEY = registerKey("sulfur_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_TUNGSTEN_ORE_KEY = registerKey("tungsten_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_FLOURITE_ORE_KEY = registerKey("fluorite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_RARE_EARTH_ORE_KEY = registerKey("rare_earth_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_MALACHITE_KEY = registerKey("malachite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_HEMATITE_KEY = registerKey("hematite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_ALUMINUM_ORE_KEY = registerKey("aluminum_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_LIGNITE_ORE_KEY = registerKey("lignite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_OIL_RESERVOIR_KEY = registerKey("oil_reservoir");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> uraniumTarget = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.URANIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.URANIUM_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> oilTarget = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.OIL_RESERVOIR.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.OIL_RESERVOIR.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> ligniteTarget = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.LIGNITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.LIGNITE_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> thoriumTarget = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.THORIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.THORIUM_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> aluminumTarget = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.ALUMINUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.ALUMINUM_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> berylliumTarget = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.BERYLLIUM_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> titaniumTarget = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.TITANIUM_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.TITANIUM_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> rareEarthTarget = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.RARE_EARTH_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.RARE_EARTH_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> malachiteTarget = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.MALACHITE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.MALACHITE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> hematiteTarget = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.HEMATITE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.HEMATITE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> leadTarget = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.LEAD_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.LEAD_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> sulfurTarget = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.SULFUR_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, ModBlocks.SULFUR_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> tungstenTarget = List.of(
                OreConfiguration.target(deepslateReplaceables, ModBlocks.TUNGSTEN_ORE.get().defaultBlockState()));

        List<OreConfiguration.TargetBlockState> fluoriteTarget = List.of(
                OreConfiguration.target(stoneReplaceable, ModBlocks.FLOURITE_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_URANIUM_ORE_KEY, Feature.ORE, new OreConfiguration(uraniumTarget, 8));
        register(context, OVERWORLD_THORIUM_ORE_KEY, Feature.ORE, new OreConfiguration(thoriumTarget, 9));
        register(context, OVERWORLD_BERYLLIUM_ORE_KEY, Feature.ORE, new OreConfiguration(berylliumTarget, 7));
        register(context, OVERWORLD_SULFUR_ORE_KEY, Feature.ORE, new OreConfiguration(sulfurTarget, 7));
        register(context, OVERWORLD_TITANIUM_ORE_KEY, Feature.ORE, new OreConfiguration(titaniumTarget, 10));
        register(context, OVERWORLD_LEAD_ORE_KEY, Feature.ORE, new OreConfiguration(leadTarget, 10));
        register(context, OVERWORLD_TUNGSTEN_ORE_KEY, Feature.ORE, new OreConfiguration(tungstenTarget, 9));
        register(context, OVERWORLD_FLOURITE_ORE_KEY, Feature.ORE, new OreConfiguration(fluoriteTarget, 10));
        register(context, OVERWORLD_MALACHITE_KEY, Feature.ORE, new OreConfiguration(malachiteTarget, 30));
        register(context, OVERWORLD_HEMATITE_KEY, Feature.ORE, new OreConfiguration(hematiteTarget, 25));
        register(context, OVERWORLD_LIGNITE_ORE_KEY, Feature.ORE, new OreConfiguration(ligniteTarget, 24));
        register(context, OVERWORLD_ALUMINUM_ORE_KEY, Feature.ORE, new OreConfiguration(aluminumTarget, 9));
        register(context, OVERWORLD_RARE_EARTH_ORE_KEY, Feature.ORE, new OreConfiguration(rareEarthTarget, 8));
        register(context, OVERWORLD_OIL_RESERVOIR_KEY, Feature.ORE, new OreConfiguration(oilTarget, 4));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(HBMNTM.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
