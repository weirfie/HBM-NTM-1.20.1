package net.StrayBead.hbm_ntm.block;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.custom.*;
import net.StrayBead.hbm_ntm.block.custom.BlastFurnaceBlock;
import net.StrayBead.hbm_ntm.block.custom.rbmk.*;
import net.StrayBead.hbm_ntm.fluid.ModFluids;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, HBMNTM.MOD_ID);

    public static final Map<String, RegistryObject<Block>> GENERAL_SIMPLE_BLOCK = new LinkedHashMap<>();

    private static RegistryObject<Block> registerSimpleBlock(String name, Supplier<Block> block) {
        RegistryObject<Block> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        GENERAL_SIMPLE_BLOCK.put(name, toReturn);
        return toReturn;
    }

    public static final BlockSetType BUNKER_BLOCK_SET = BlockSetType.register(new BlockSetType(
            "bunker",
            true,
            SoundType.METAL,
            SoundEvent.createVariableRangeEvent(new ResourceLocation(HBMNTM.MOD_ID, "door_creak_open")),
            SoundEvent.createVariableRangeEvent(new ResourceLocation(HBMNTM.MOD_ID, "door_creak_open")),
            SoundEvents.IRON_TRAPDOOR_CLOSE,
            SoundEvents.IRON_TRAPDOOR_OPEN,
            SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
            SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
            SoundEvents.STONE_BUTTON_CLICK_OFF,
            SoundEvents.STONE_BUTTON_CLICK_ON
    ));

    public static final RegistryObject<Block> URANIUM_ORE_BLOCK = registerBlock("uranium_ore_block",
            () -> new UraniumOreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(-1, 3600000)));
    public static final RegistryObject<Block> URANIUM_ORE = registerBlock("uranium_ore",
            () -> new UraniumOre(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> TITANIUM_ORE_BLOCK = registerBlock("titanium_ore_block",
            () -> new TitaniumOreBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> TITANIUM_ORE = registerBlock("titanium_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> LIGNITE_ORE = registerBlock("lignite_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)), 1600);
    public static final RegistryObject<Block> ALUMINUM_ORE = registerBlock("aluminum_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> FLOURITE_ORE = registerBlock("flourite_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> RARE_EARTH_ORE = registerBlock("rare_earth_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> TUNGSTEN_ORE = registerBlock("tungsten_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> TUNGSTEN_ORE_BLOCK = registerBlock("tungsten_ore_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> LEAD_ORE = registerBlock("lead_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> REINFORCED_STEEL_SCAFFOLD = registerBlock("reinforced_steel_scaffold",
            () -> new ReinforcedSteelScaffoldBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> LEAD_BLOCK = registerBlock("lead_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> THORIUM_ORE = registerBlock("thorium_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> ASBESTOS_ORE = registerSimpleBlock("asbestos_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> AUSTRALIAN_ORE = registerSimpleBlock("australium_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> COBALT_ORE = registerSimpleBlock("cobalt_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> CINNABAR_ORE = registerSimpleBlock("cinnabar_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> COLTAN_ORE = registerSimpleBlock("coltan_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> IRON_ORE_CLUSTER = registerSimpleBlock("iron_ore_cluster",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> TITANIUM_ORE_CLUSTER = registerSimpleBlock("titanium_ore_cluster",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> ALUMINUM_ORE_CLUSTER = registerSimpleBlock("aluminum_ore_cluster",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> COPPER_ORE_CLUSTER = registerSimpleBlock("copper_ore_cluster",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE)));
    public static final RegistryObject<Block> OILY_COAL_ORE = registerSimpleBlock("oily_coal_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> BURNING_OILY_COAL_ORE = registerSimpleBlock("burning_oily_coal_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_ORE)));
    public static final RegistryObject<Block> SCHIST_IRON_ORE = registerSimpleBlock("schist_iron_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> SCHIST_GOLD_ORE = registerSimpleBlock("schist_gold_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GOLD_ORE)));
    public static final RegistryObject<Block> SCHIST_URANIUM_ORE = registerSimpleBlock("schist_uranium_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> SCORCHED_SCHIST_URANIUM_ORE = registerSimpleBlock("scorched_schist_uranium_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> SCHIST_COPPER_ORE = registerSimpleBlock("schist_copper_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COPPER_ORE)));
    public static final RegistryObject<Block> SCHIST_ASBESTOS_ORE = registerSimpleBlock("schist_asbestos_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> SCHIST_LITHIUM_ORE = registerSimpleBlock("schist_lithium_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> SCHIST_SCHRABIDIUM_ORE = registerSimpleBlock("schist_schrabidium_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> SCHIST_RARE_EARTH_ORE = registerSimpleBlock("schist_rare_earth_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> GAS_SHALE = registerSimpleBlock("gas_shale",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> DEPTH_CINNABAR_ORE = registerSimpleBlock("depth_cinnabar_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> DEPTH_ZIRCONIUM_ORE = registerSimpleBlock("depth_zirconium_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> DEPTH_BORAX_ORE = registerSimpleBlock("depth_borax_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> DEPTH_IRON_ORE_CLUSTER = registerSimpleBlock("depth_iron_ore_cluster",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> DEPTH_TITANIUM_ORE_CLUSTER = registerSimpleBlock("depth_titanium_ore_cluster",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> DEPTH_TUNGSTEN_ORE_CLUSTER = registerSimpleBlock("depth_tungsten_ore_cluster",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> ALEXANDRITE_ORE = registerSimpleBlock("alexandrite_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> NETHER_DEPTH_NEODYMIUM_ORE = registerSimpleBlock("nether_depth_neodymium_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE)));
    public static final RegistryObject<Block> STEEL_BLOCK = registerBlock("steel_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.REDSTONE_BLOCK)));
    public static final RegistryObject<Block> BLOCK_OF_RADIUM_226 = registerSimpleBlock("block_of_radium_226",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BLOCK_OF_ACTINIUM = registerSimpleBlock("block_of_actinium",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ASBESTOS_ROOF = registerSimpleBlock("asbestos_roof",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> DECO_LIGHT_EMITTER = registerSimpleBlock("deco_light_emitter",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> DECO_PARTICLE_EMITTER = registerSimpleBlock("deco_particle_emitter",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> RBMK_DECO_BLOCK = registerSimpleBlock("rbmk_deco_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SMOOTH_RBMK_DECO_BLOCK = registerSimpleBlock("smooth_rbmk_deco_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> DIAMOND_GRAVEL = registerSimpleBlock("diamond_gravel",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> DEAD_LEAVES = registerSimpleBlock("dead_leaves",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistryObject<Block> BLOCK_ON = registerBlock("block_on",
            () -> new BlockOn(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BLOCK_OFF = registerBlock("block_off",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SIGNAL_BLOCK = registerBlock("signal_block",
            () -> new SignalBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BLOCK_OF_GRAPHITE = registerBlock("block_of_graphite",
            () -> new SignalBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> GRAPHITE = registerBlock("graphite",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> RED_COPPER_BLOCK = registerBlock("red_copper_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> POWER_SWITCH = registerBlock("power_switch",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SHALLOW_FOUNDRY_BASIN = registerBlock("shallow_foundry_basin",
            () -> new ShallowFoundryBasinBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> DRILLED_GRAPHITE = registerBlock("drilled_graphite",
            () -> new DrilledGraphiteBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> DRILLED_GRAPHITE_NEUTRON_SOURCE = registerBlock("drilled_graphite_neutron_source",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> FUEL_ROD = registerBlock("fuel_rod",
            () -> new FuelRodBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> FUEL_ROD_NORMAL = registerBlock("fuel_rod_normal",
            () -> new NormalFuelRodBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> STEAM_CHANNEL = registerBlock("steam_channel",
            () -> new SteamChannelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> STEAM_CHANNEL_TOP = registerBlock("steam_channel_top",
            () -> new SteamChannelTopBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> GRAPHITE_MODERATOR = registerBlock("graphite_moderator",
            () -> new GraphiteModeratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> CONTROL_ROD = registerBlock("control_rod",
            () -> new ControlRodBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> CONTROL_ROD_TOP = registerBlock("control_rod_top",
            () -> new ControlRodTopBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().noCollission()));
    public static final RegistryObject<Block> STRUCTURAL_COLUMN = registerBlock("structural_column",
            () -> new StructuralColumnBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> FUEL_ROD_EMPTY = registerBlock("fuel_rod_empty",
            () -> new FuelRodEmptyBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> NEUTRON_REFLECTOR = registerBlock("neutron_reflector",
            () -> new NeutronReflectorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> NEUTRON_ABSORBER = registerBlock("neutron_absorber",
            () -> new NeutronAbsorberBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> RADIATED_GRAPHITE = registerBlock("radiated_graphite",
            () -> new RadiatedGraphiteBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(-1, 3600000)));
    public static final RegistryObject<Block> ULTRA_DENSE_STEAM_PIPE = registerBlock("ultra_dense_steam_pipe",
            () -> new UltraDenseSteamPipeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SUPER_DENSE_STEAM_PIPE = registerBlock("super_dense_steam_pipe",
            () -> new SuperDenseSteamPipeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> DENSE_STEAM_PIPE = registerBlock("dense_steam_pipe",
            () -> new DenseSteamPipeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> STEAM_PIPE = registerBlock("steam_pipe",
            () -> new SteamPipeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> CONCRETE_BRICKS = registerBlock("concrete_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> WATER_DUCT = registerBlock("water_duct",
            () -> new WaterDuctBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> STEAM_SEPARATOR = registerBlock("steam_separator",
            () -> new SteamSeparaterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> CONTROL_SYSTEM = registerBlock("control_system",
            () -> new ControlSystemBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> DEAD_GRASS = registerBlock("dead_grass",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT_PATH)));
    public static final RegistryObject<Block> WOOD_BRICKS = registerBlock("woodbricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Block> STEAM = BLOCKS.register("steam", () -> new SteamBlock());
    public static final RegistryObject<Block> CARBON_DIOXIDE = BLOCKS.register("carbon_dioxide", () -> new CarbonDioxideBlock());
    public static final RegistryObject<Block> OIL = BLOCKS.register("oil", () -> new OilBlock());
    public static final RegistryObject<Block> HOT_CRUDE_OIL = BLOCKS.register("hot_crude_oil", () -> new OilBlock());
    public static final RegistryObject<Block> LIGHT_OIL = BLOCKS.register("light_oil", () -> new LightOilBlock());
    public static final RegistryObject<Block> HEAVY_OIL = BLOCKS.register("heavy_oil", () -> new HeavyOilBlock());
    public static final RegistryObject<Block> NAPHTHA = BLOCKS.register("naphtha", () -> new NaphthaBlock());
    public static final RegistryObject<Block> PETROLEUM_GAS = BLOCKS.register("petroleum_gas", () -> new PetroleumGasBlock());
    public static final RegistryObject<Block> BITUMEN = BLOCKS.register("bitumen", () -> new BitumenBlock());
    public static final RegistryObject<Block> DIESEL = BLOCKS.register("diesel", () -> new DieselBlock());
    public static final RegistryObject<Block> HEATING_OIL = BLOCKS.register("heating_oil", () -> new HeatingOilBlock());
    public static final RegistryObject<Block> INDUSTRIAL_OIL = BLOCKS.register("industrial_oil", () -> new IndustrialOilBlock());
    public static final RegistryObject<Block> KEROSENE = BLOCKS.register("kerosene", () -> new KeroseneBlock());
    public static final RegistryObject<Block> NATURAL_GAS = BLOCKS.register("natural_gas", () -> new NaturalGasBlock());
    public static final RegistryObject<Block> UNSATURATED_HYDROCARBONS = BLOCKS.register("unsaturated_hydrocarbons", () -> new UnsaturatedHydrocarbonsBlock());
    public static final RegistryObject<Block> AROMATIC_HYDROCARBONS = BLOCKS.register("aromatic_hydrocarbons", () -> new AromaticHydrocarbonsBlock());
    public static final RegistryObject<Block> CRACKED_OIL = BLOCKS.register("cracked_oil", () -> new CrackedOilBlock());
    public static final RegistryObject<Block> VACUUM_LIGHT_OIL = BLOCKS.register("vacuum_light_oil",
            () -> new GenericLiquidBlock(ModFluids.VACUUM_LIGHT_OIL, MapColor.SNOW));
    public static final RegistryObject<Block> HEAVY_HEATING_OIL = BLOCKS.register("heavy_heating_oil",
            () -> new GenericLiquidBlock(ModFluids.HEAVY_HEATING_OIL, MapColor.SNOW));
    public static final RegistryObject<Block> REFORMATE_GAS = BLOCKS.register("reformate_gas",
            () -> new GenericLiquidBlock(ModFluids.REFORMATE_GAS, MapColor.SNOW));
    public static final RegistryObject<Block> VACUUM_HEAVY_OIL = BLOCKS.register("vacuum_heavy_oil",
            () -> new GenericLiquidBlock(ModFluids.VACUUM_HEAVY_OIL, MapColor.SNOW));
    public static final RegistryObject<Block> SOUR_GAS = BLOCKS.register("sour_gas",
            () -> new GenericLiquidBlock(ModFluids.SOUR_GAS, MapColor.SNOW));
    public static final RegistryObject<Block> LIQUID_HYDROGEN = BLOCKS.register("liquid_hydrogen",
            () -> new GenericLiquidBlock(ModFluids.LIQUID_HYDROGEN, MapColor.SNOW));
    public static final RegistryObject<Block> MERCURY = BLOCKS.register("mercury",
            () -> new GenericLiquidBlock(ModFluids.MERCURY, MapColor.SNOW));
    public static final RegistryObject<Block> REFORMATE = BLOCKS.register("reformate",
            () -> new GenericLiquidBlock(ModFluids.REFORMATE, MapColor.SNOW));
    public static final RegistryObject<Block> MOLTEN_STEEL = BLOCKS.register("molten_steel",
            () -> new GenericLiquidBlock(ModFluids.MOLTEN_STEEL, MapColor.SNOW));
    public static final RegistryObject<Block> MOLTEN_COPPER = BLOCKS.register("molten_copper",
            () -> new GenericLiquidBlock(ModFluids.MOLTEN_COPPER, MapColor.SNOW));
    public static final RegistryObject<Block> MOLTEN_IRON = BLOCKS.register("molten_iron",
            () -> new GenericLiquidBlock(ModFluids.MOLTEN_IRON, MapColor.SNOW));
    public static final RegistryObject<Block> MOLTEN_GOLD = BLOCKS.register("molten_gold",
            () -> new GenericLiquidBlock(ModFluids.MOLTEN_GOLD, MapColor.SNOW));
    public static final RegistryObject<Block> MOLTEN_ALUMINUM = BLOCKS.register("molten_aluminum",
            () -> new GenericLiquidBlock(ModFluids.MOLTEN_ALUMINUM, MapColor.SNOW));
    public static final RegistryObject<Block> MOLTEN_FUEL = BLOCKS.register("molten_fuel",
            () -> new GenericLiquidBlock(ModFluids.MOLTEN_FUEL, MapColor.SNOW));
    public static final RegistryObject<Block> MOLTEN_REDSTONE = BLOCKS.register("molten_redstone",
            () -> new GenericLiquidBlock(ModFluids.MOLTEN_REDSTONE, MapColor.SNOW));
    public static final RegistryObject<Block> HEAVY_WATER = BLOCKS.register("heavy_water",
            () -> new GenericLiquidBlock(ModFluids.HEAVY_WATER, MapColor.SNOW));
    public static final RegistryObject<Block> COKER_OIL = BLOCKS.register("coker_oil",
            () -> new GenericLiquidBlock(ModFluids.COKER_OIL, MapColor.SNOW));
    public static final RegistryObject<Block> ORE_SLOP = BLOCKS.register("ore_slop",
            () -> new GenericLiquidBlock(ModFluids.ORE_SLOP, MapColor.SNOW));
    public static final RegistryObject<Block> VITROIL = BLOCKS.register("vitroil",
            () -> new GenericLiquidBlock(ModFluids.VITROIL, MapColor.SNOW));
    public static final RegistryObject<Block> SULFURIC_ACID = BLOCKS.register("sulfuric_acid",
            () -> new GenericLiquidBlock(ModFluids.SULFURIC_ACID, MapColor.SNOW));
    public static final RegistryObject<Block> CHLORINE_GAS_LIQUID = BLOCKS.register("chlorine_gas_liquid",
            () -> new GenericLiquidBlock(ModFluids.CHLORINE_GAS, MapColor.SNOW));

    public static final RegistryObject<Block> REINFORCED_GLASS = registerBlock("reinforcedglass",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion().strength(1f, 10f)));
    public static final RegistryObject<Block> WARNING_BLOCK = registerBlock("warning",
            () -> new WarningBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PWR_CONTROL_ROD = registerBlock("pwrcontrolrod",
            () -> new PWRControlRodBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PWR_FUEL_ROD = registerBlock("pwrfuelrod",
            () -> new PWRFuelRodBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PWR_CONTROLLER = registerBlock("pwrcontroller",
            () -> new PWRControllerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PWR_NEUTRON_REFLECTOR = registerBlock("neutronreflector",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PWR_COOLANT_CHANNEL = registerBlock("coolantchannel",
            () -> new PWRCoolantChannelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PWR_HEAT_EXCHANGER = registerBlock("heatexchanger",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PWR_HEATSINK = registerBlock("heatsink",
            () -> new PWRHeatsinkBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PWR_NEUTRON_SOURCE = registerBlock("netronsource",
            () -> new PWRNeutronSourceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> LITTLE_BOY = registerBlock("little_boy",
            () -> new LittleBoyBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> FAT_MAN = registerBlock("fatman",
            () -> new FatManBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> IVY_MIKE = registerBlock("ivy_mike",
            () -> new IvyMikeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> CASTLE_BRAVO = registerBlock("castle_bravo",
            () -> new CastleBravoBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> OIL_RESERVOIR = registerBlock("oil_reservoir",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> TSAR_BOMBA = registerBlock("tsar_bomba",
            () -> new TsarBombaBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SOLAR_PANEL = registerBlock("solar_panel",
            () -> new SolarPanelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> FAN = registerBlock("fan",
            () -> new FanBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> LEVIATHAN_STEAM_TURBINE = registerBlock("leviathan_steam_turbine",
            () -> new SteamTurbineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> COOLING_TOWER = registerBlock("cooling_tower",
            () -> new CoolingTowerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> COOLING_TOWER_1 = registerBlock("cooling_tower1",
            () -> new CoolingTowerBlockOne(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> WATER_TANK = registerBlock("water_tank",
            () -> new WaterTankBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> WATER_TANK_TOP = registerBlock("water_tank_top",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SHREDDER = registerBlock("shredder",
            () -> new ShredderBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SPARK_ENERGY_BATTERY = registerBlock("spark_energy_battery",
            () -> new SparkEnergyBatteryBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SCHRABIDIUM_BLOCK = registerBlock("schrabidium_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SCHRABIDIUM_ORE = registerBlock("schrabidium_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BERYLLIUM_ORE = registerBlock("beryllium_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> DET_CORD = registerBlock("det_cord",
            () -> new DetCordBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PARTICLE_ACCELERATOR_PLATING = registerBlock("particle_accelerator_plating",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> RADIOACTIVE_BARREL = registerBlock("radioactive_barrel",
            () -> new RadioactiveBarrelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> BURNER_PRESS = registerBlock("burner_press",
            () -> new BurnerPressBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> LEAD_ANVIL = registerBlock("lead_anvil",
            () -> new LeadAnvilBlock(BlockBehaviour.Properties.copy(Blocks.ANVIL).noOcclusion()));
    public static final RegistryObject<Block> SIM_CARD_SIGNAL_BROADCASTING_DEVICE = registerBlock("sim_card_signal_broadcasting_device",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SHORT_RANGE_SIM_CARD_DATA_SENDER = registerBlock("short_range_sim_card_data_sender",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> BLAST_FURNACE = registerBlock("blast_furnace",
            () -> new BlastFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> COMBUSTION_GENERATOR = registerBlock("combustion_generator",
            () -> new CombustionGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> RADIATION_ABSORBER = registerBlock("radiation_absorber",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ENHANCED_RADIATION_ABSORBER = registerBlock("enhanced_radiation_absorber",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ADVANCED_RADIATION_ABSORBER = registerBlock("advanced_radiation_absorber",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ELITE_RADIATION_ABSORBER = registerBlock("elite_radiation_absorber",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PLAYER_DECONTAMINATOR = registerBlock("player_decontaminator",
            () -> new PlayerDecontaminatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> CHLORINE_VENT = registerBlock("chlorine_vent",
            () -> new ChlorineVentBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> CHLORINE_SEAL = registerBlock("chlorine_seal",
            () -> new ChlorineSealBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> CHLORINE_GAS = registerBlock("chlorine_gas",
            () -> new ChlorineGasBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().noCollission()));
    public static final RegistryObject<Block> MOLDY_DEBRIS = registerBlock("moldy_debris",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(12)));
    public static final RegistryObject<Block> ASPHALT = registerBlock("asphalt",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(60)));
    public static final RegistryObject<Block> GLOWING_ASPHALT = registerBlock("glowing_asphalt",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(60).lightLevel(state -> 10)));
    public static final RegistryObject<Block> LARGE_VINYL_TILE = registerBlock("large_vinyl_tile",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(72)));
    public static final RegistryObject<Block> SMALL_VINYL_TILES = registerBlock("small_vinyl_tiles",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(72)));
    public static final RegistryObject<Block> FIREBRICKS = registerBlock("firebricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(96)));
    public static final RegistryObject<Block> REINFORCED_GLOWSTONE = registerBlock("reinforced_glowstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(180).lightLevel(state -> 15)));
    public static final RegistryObject<Block> REINFORCED_LAMP = registerBlock("reinforced_lamp",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(180)));
    public static final RegistryObject<Block> REINFORCED_SANDSTONE = registerBlock("reinforced_sandstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(240)));
    public static final RegistryObject<Block> COPPER_CABLE = registerBlock("copper_cable",
            () -> new CopperCableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> UNIVERSAL_FLUID_DUCT = registerBlock("universal_fluid_duct",
            () -> new UniversalFluidDuctBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> CRUDE_OIL_PIPE = registerBlock("crude_oil_pipe",
            () -> new CrudeOilPipeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> COPPER_CABLE_TURN_RIGHT = registerBlock("copper_cable_turn_right",
            () -> new CopperCableTurnRightBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> COPPER_CABLE_TURN_LEFT = registerBlock("copper_cable_turn_left",
            () -> new CopperCableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> COPPER_CABLE_THREE_SIDES = registerBlock("copper_cable_three_sides",
            () -> new CopperCableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> COPPER_CABLE_FOUR_SIDES = registerBlock("copper_cable_four_sides",
            () -> new CopperCableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> COPPER_CABLE_FIVE_SIDES = registerBlock("copper_cable_five_sides",
            () -> new CopperCableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> COPPER_CABLE_ALL_SIDES = registerBlock("copper_cable_all_sides",
            () -> new CopperCableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> TANK = registerBlock("tank",
            () -> new TankBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> SMOKESTACK = registerBlock("smokestack",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> HYDRAULIC_FRACKING_TOWER = registerBlock("hydraulic_fracking_tower",
            () -> new HydraulicFrackingTowerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> HYDROTREATER = registerBlock("hydrotreater",
            () -> new HydrotreaterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> ELECTROLYSIS_MACHINE = registerBlock("electrolysis_machine",
            () -> new ElectrolysisMachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> FEL = registerBlock("fel",
            () -> new FelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> SILEX = registerBlock("silex",
            () -> new SilexBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> CATALYTIC_CRACKING_TOWER = registerBlock("catalytic_cracking_tower",
            () -> new CatalyticCrackingTowerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> BREEDING_REACTOR = registerBlock("breeding_reactor",
            () -> new HydrotreaterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> COMBINATION_OVEN = registerBlock("combination_oven",
            () -> new HydrotreaterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> STEEL_GRATE = registerBlock("steel_grate",
            () -> new SteelGrateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> STEEL_SCAFFOLD = registerBlock("steel_scaffold",
            () -> new HydrotreaterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> CHEMICAL_PLANT = registerBlock("chemical_plant",
            () -> new ChemicalPlantBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> MATTER_GIGAFACTORY = registerBlock("matter_gigafactory",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> BATTERY_SOCKET = registerBlock("battery_socket",
            () -> new BatterySocketBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> REDSTONE_BATTERY = registerBlock("redstone_battery",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> CONVEYOR_LIFT = registerBlock("conveyor_lift",
            () -> new ConveyorLiftBLock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape().noCollission()));
    public static final RegistryObject<Block> CONVEYOR_CHAIN_LIFT = registerBlock("conveyor_chain_lift",
            () -> new ConveyorChainLiftBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape().noCollission()));
    public static final RegistryObject<Block> OIL_REFINERY = registerBlock("oil_refinery",
            () -> new OilRefineryBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> OIL_DERRICK = registerBlock("oil_derrick",
            () -> new OilDerrickBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> FIREBOX = registerBlock("firebox",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> GENERIC_BOUNDING_BOX = registerBlock("generic_bounding_box",
            () -> new GenericBoundingBoxBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> INDUSTRIAL_STEAM_TURBINE = registerBlock("industrial_steam_turbine",
            () -> new IndustrialSteamTurbineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> ASSEMBLY_MACHINE = registerBlock("assembly_machine",
            () -> new AssemblyMachineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> FRACTIONATING_TOWER = registerBlock("fractioning_tower",
            () -> new FractionatingTowerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> FRACTIONATING_TOWER_SEPARATOR = registerBlock("fractioning_tower_separator",
            () -> new FractionatingTowerSeparator(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> CHEMICAL_FACTORY = registerBlock("chemical_factory",
            () -> new ChemicalFactoryBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> DRAINAGE_PIPE = registerBlock("drainage_pipe",
            () -> new DrainagePipeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> CRUSHED_OBSIDIAN = registerBlock("crushed_obsidian",
            () -> new FallingBlock(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN)
                    .strength(2.0f, 6.0f)
                    .explosionResistance(360)
            )
    );
    public static final RegistryObject<Block> LIGHT_BRICKS = registerBlock("light_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(600)));
    public static final RegistryObject<Block> REINFORCED_LAMINATE = registerBlock("reinforced_laminate",
            () -> new ReinforcedLaminatedBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)
                    .strength(5.0f, 600.0f)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
                    .isValidSpawn((state, getter, pos, type) -> false)
                    .isRedstoneConductor((state, getter, pos) -> false)
                    .isSuffocating((state, getter, pos) -> false)
                    .isViewBlocking((state, getter, pos) -> false).explosionResistance(600)
            )
    );
    public static final RegistryObject<Block> MARKED_CONCRETE_BRICKS = registerBlock("marked_concrete_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(900)));
    public static final RegistryObject<Block> BROKEN_CONCRETE_BRICKS = registerBlock("broken_concrete_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(900)));
    public static final RegistryObject<Block> CRACKED_CONCRETE_BRICKS = registerBlock("cracked_concrete_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(1200)));
    public static final RegistryObject<Block> DENSE_STONE = registerBlock("dense_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(2400)));
    public static final RegistryObject<Block> CONCRETE_TILE = registerBlock("concrete_tile",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(2400)));
    public static final RegistryObject<Block> ASBESTOS_CONCRETE = registerBlock("asbestos_concrete",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(2400)));
    public static final RegistryObject<Block> REBAR_REINFORCED_CONCRETE_PILLAR = registerBlock("rebar_reinforced_concrete_pillar",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(2400)));
    public static final RegistryObject<Block> MOSSY_CONCRETE_BRICKS = registerBlock("mossy_concrete_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(3600)));
    public static final RegistryObject<Block> CMB_STEEL_TILE = registerBlock("cmb_steel_tile",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(3600)));
    public static final RegistryObject<Block> DUCRETE = registerBlock("ducrete",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(4800)));
    public static final RegistryObject<Block> DUCRETE_TILE = registerBlock("ducrete_tile",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(4800)));
    public static final RegistryObject<Block> OBSIDIAN_BRICKS = registerBlock("obsidian_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(4800)));
    public static final RegistryObject<Block> REINFORCED_STONE = registerBlock("reinforced_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(4800)));
    public static final RegistryObject<Block> UBER_CONCRETE = registerBlock("uber_concrete",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(6000)));
    public static final RegistryObject<Block> COMPOUND_MESH = registerBlock("compound_mesh",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(6000)));
    public static final RegistryObject<Block> DUCRETE_BRICKS = registerBlock("ducrete_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(7200)));
    public static final RegistryObject<Block> REINFORCED_DUCRETE = registerBlock("reinforced_ducrete",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(14400)));
    public static final RegistryObject<Block> REINFORCED_CMB_BRICKS = registerBlock("reinforced_cmb_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).explosionResistance(36000)));
    public static final RegistryObject<Block> LIGHTSTONE_TILE = registerSimpleBlock("lightstone_tile",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
    public static final RegistryObject<Block> LIGHTSTONE_BRICKS = registerSimpleBlock("lightstone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
    public static final RegistryObject<Block> LIGHTSTONE_CHISELED_BRICKS = registerBlock("lightstone_chiseled_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
    public static final RegistryObject<Block> CHISELED_LIGHTSTONE = registerBlock("chiseled_lightstone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.BRICKS)));
    public static final RegistryObject<Block> LIGHTSTONE_TILE_STAIRS = registerBlock("lightstone_tile_stairs",
            () -> new StairBlock(ModBlocks.LIGHTSTONE_TILE.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.BRICKS)));
    public static final RegistryObject<Block> LIGHTSTONE_BRICK_STAIRS = registerBlock("lightstone_brick_stairs",
            () -> new StairBlock(ModBlocks.LIGHTSTONE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.BRICKS)));
    public static final RegistryObject<Block> LIGHTSTONE_TILE_SLAB = registerBlock("lightstone_tile_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.LIGHTSTONE_TILE.get())));
    public static final RegistryObject<Block> LIGHTSTONE_BRICK_SLAB = registerBlock("lightstone_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.LIGHTSTONE_BRICKS.get())));
    public static final RegistryObject<Block> CONVEYOR_BELT = registerBlock("conveyor_belt",
            () -> new ConveyorBeltBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> CONVEYOR_BELT_FACING_Z = registerBlock("conveyor_belt_facing_z",
            () -> new ConveyorBeltBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> INDUSTRIAL_COOLING_TOWER = registerBlock("industrial_cooling_tower",
            () -> new IndustrialCoolingTowerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> THE_GADGET = registerBlock("the_gadget",
            () -> new TheGadgetBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> THE_PROTOTYPE = registerBlock("the_prototype",
            () -> new ThePrototypeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> CENTRIFUGE = registerBlock("centrifuge",
            () -> new CentrifugeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> CORIUM = registerBlock("corium",
            CoriumBlock::new);
    public static final RegistryObject<Block> CONVEYOR_INSERTER = registerBlock("conveyor_inserter",
            () -> new ConveyorInserter(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> CONVEYOR_EJECTOR = registerBlock("conveyor_ejector",
            () -> new ConveyorEjector(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> ORE_ACIDIZER = registerBlock("ore_acidizer",
            () -> new OreAcidizerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> CONVEYOR_SPLITTER = registerBlock("conveyor_splitter",
            () -> new ConveyorSplitterBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> NUCLEAR_SIREN = registerBlock("nuclear_siren",
            () -> new NuclearSirenBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> STEAM_CONDENSER = registerBlock("steam_condenser",
            () -> new NuclearSirenBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> QUARTZ_GLASS = registerBlock("quartz_glass",
            () -> new QuartzGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()));
    public static final RegistryObject<Block> QUARTZ_SAND = registerBlock("quartz_sand",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> BOILER = registerBlock("boiler",
            () -> new BoilerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> WOOD_BURNING_GENERATOR = registerBlock("wood_burning_generator",
            () -> new WoodBurningGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> HEATING_OVEN = registerBlock("heating_oven",
            () -> new HeatingOvenBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> ZIRNOX_NUCLEAR_REACTOR = registerBlock("zirnox_nuclear_reactor",
            () -> new ZirnoxNuclearReactorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> SILO_LAUNCH_PAD = registerBlock("silo_launch_pad",
            () -> new SiloLaunchPadBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> CRUCIBLE = registerBlock("crucible",
            () -> new CrucibleBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> SOLDERING_STATION = registerBlock("soldering_station",
            () -> new SolderingStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> ARC_WELDER = registerBlock("arc_welder",
            () -> new ArcWelderBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> INDUSTRIAL_BOILER = registerBlock("industrial_boiler",
            () -> new IndustrialBoilerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> LARGE_ELECTRICITY_PYLON = registerBlock("large_electricity_pylon",
            () -> new LargeElectricityPylonBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> DEUTERIUM_EXTRACTION_TOWER = registerBlock("deuterium_extraction_tower",
            () -> new DeuteriumExtractionTowerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> INDUSTRIAL_COMBUSTION_GENERATOR = registerBlock("industrial_combustian_generator",
            () -> new IndustrialCombustionGeneratorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> VACUUM_REFINERY = registerBlock("vacuum_refinery",
            () -> new VacuumRefineryBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> PUMPJACK = registerBlock("pumpjack",
            () -> new PumpjackBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> ELECTRIC_ARC_FURNACE = registerBlock("electric_arc_furnace",
            () -> new ElectricArcFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> LARGE_MINING_DRILL = registerBlock("large_mining_drill",
            () -> new LargeMiningDrillBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> EXPOSURE_CHAMBER = registerBlock("exposure_chamber",
            () -> new ExposureChamberBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> COMPRESSOR = registerBlock("compressor",
            () -> new CompressorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> FLARE_STACK = registerBlock("flare_stack",
            () -> new FlareStackBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> TURBOFAN = registerBlock("turbofan",
            () -> new TurbofanBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> MALACHITE = registerBlock("malachite",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> HEMATITE = registerBlock("hematite",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SULFUR_ORE = registerBlock("sulfur_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final RegistryObject<Block> BROKEN_METEORITE_BLOCK = registerBlock("broken_meteorite_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> METEORITE_COBBLESTONE = registerBlock("meteorite_cobblestone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> METEOR_COBALT_ORE = registerBlock("meteor_cobalt_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> METEOR_IRON_ORE = registerBlock("meteor_iron_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> METEOR_COPPER_ORE = registerBlock("meteor_copper_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> METEOR_ALUMINUM_ORE = registerBlock("meteor_aluminum_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> METEOR_RARE_EARTH_ORE = registerBlock("meteor_rare_earth_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> HOT_METEORITE_COBBLESTONE = registerBlock("hot_meteorite_cobblestone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BUNKER_DOOR = BLOCKS.register("bunker_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL).noOcclusion(), BlockSetType.IRON));
    public static final RegistryObject<Block> DOOR_METAL = BLOCKS.register("door_metal",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL).noOcclusion(), BlockSetType.IRON));
    public static final RegistryObject<Block> CATALYTIC_REFORMER = registerBlock("catalytic_reformer",
            () -> new CatalyticReformerBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> TEST = registerBlock("test",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> STEEL_DECO_BLOCK = registerBlock("steel_deco_block",
            () -> new SteelDecoBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> STEEL_POLE = registerBlock("steel_pole",
            () -> new SteelPoleBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> ANTENNA_TOP = registerBlock("antenna_top",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> CONCRETE_SLAB = registerBlock("concrete_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GRAY_CONCRETE)));
    public static final RegistryObject<Block> CONCRETE_BRICK_STAIRS = registerBlock("concrete_brick_stairs",
            () -> new StairBlock(() -> ModBlocks.CONCRETE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.GRAY_CONCRETE)));
    public static final RegistryObject<Block> BROKEN_CONCRETE_BRICK_SLAB = registerBlock("broken_concrete_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GRAY_CONCRETE)));
    public static final RegistryObject<Block> CONCRETE_BRICK_SLAB = registerBlock("concrete_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.GRAY_CONCRETE)));
    public static final RegistryObject<Block> CONCRETE_STAIRS = registerBlock("concrete_stairs",
            () -> new StairBlock(Blocks.LIGHT_GRAY_CONCRETE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.GRAY_CONCRETE)));
    public static final RegistryObject<Block> CRACKED_CONCRETE_BRICK_STAIRS = registerBlock("cracked_concrete_brick_stairs",
            () -> new StairBlock(() -> ModBlocks.CRACKED_CONCRETE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.GRAY_CONCRETE)));
    public static final RegistryObject<Block> TAPE_RECORDER = registerBlock("tape_recorder",
            () -> new SteelPoleBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> PAINTABLE_COATED_UNIVERSAL_FLUID_DUCT = registerBlock("paintable_coated_universal_fluid_duct",
            () -> new PaintableCoatedUniversalFluidDuctBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final RegistryObject<Block> CHAINLINK_FENCE = registerBlock("chainlink_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> CHAINLINK_FENCE_POST = registerBlock("chainlink_fence_post",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> ASH = registerBlock("ash",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> BORON_SAND = registerBlock("boron_sand",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> LEAD_SAND = registerBlock("lead_sand",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> URANIUM_SAND = registerBlock("uranium_sand",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> POLONIUM_SAND = registerBlock("polonium_sand",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SAND)));
    public static final RegistryObject<Block> BORON_GLASS = registerBlock("boron_glass",
            () -> new QuartzGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()));
    public static final RegistryObject<Block> LEAD_GLASS = registerBlock("lead_glass",
            () -> new QuartzGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()));
    public static final RegistryObject<Block> URANIUM_GLASS = registerBlock("uranium_glass",
            () -> new QuartzGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()));
    public static final RegistryObject<Block> TRINITY_GLASS = registerBlock("trinity_glass",
            () -> new QuartzGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()));
    public static final RegistryObject<Block> POLONIUM_GLASS = registerBlock("polonium_glass",
            () -> new QuartzGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()));
    public static final RegistryObject<Block> ASH_GLASS = registerBlock("ash_glass",
            () -> new QuartzGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()));
    public static final RegistryObject<Block> POLARIZED_GLASS = registerBlock("polarized_glass",
            () -> new QuartzGlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion()));
    public static final RegistryObject<Block> SILO_HATCH_FRAME = registerBlock("silo_hatch_frame",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SILO_HATCH_OPENER = registerBlock("silo_hatch_opener",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> IRON_CRATE = registerBlock("iron_crate",
            () -> new IronCrateBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> STEEL_CRATE = registerBlock("steel_crate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> DESH_CRATE = registerBlock("desh_crate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> TUNGSTEN_CRATE = registerBlock("tungsten_crate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> TEMPLATE_CRATE = registerBlock("template_crate",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SAFE = registerBlock("safe",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> MASS_STORAGE_UNIT = registerBlock("mass_storage_unit",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> FIREWORK_BATTERY = registerBlock("firework_battery",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> DYNAMITE = registerBlock("dynamite",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.TNT)));
    public static final RegistryObject<Block> ACTUAL_TNT = registerBlock("actual_tnt",
            () -> new TntBlock(BlockBehaviour.Properties.copy(Blocks.TNT)));
    public static final RegistryObject<Block> SEMTEX = registerBlock("semtex",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.TNT)));
    public static final RegistryObject<Block> C_4 = registerBlock("c_4",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.TNT)));
    public static final RegistryObject<Block> FISSURE_BOMB = registerBlock("fissure_bomb",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.TNT)));
    public static final RegistryObject<Block> FLAME_WAR_IN_A_BOX = registerBlock("flame_war_in_a_box",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ASSEMBLY_FACTORY = registerBlock("assembly_factory",
            () -> new ChemicalFactoryBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> IRON_FURNACE = registerBlock("iron_furnace",
            () -> new IronFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> STEAM_PUMP = registerBlock("steam_pump",
            () -> new SteamPumpBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> STEEL_FURNACE = registerBlock("steel_furnace",
            () -> new IronFurnaceBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> LEVITATION_BOMB = registerBlock("levitation_bomb",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ENDOTHERMIC_BOMB = registerBlock("endothermic_bomb",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> EXOTHERMIC_BOMB = registerBlock("exothermic_bomb",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> EMP_DEVICE = registerBlock("emp_device",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> EXPLOSIVE_CHARGE = registerBlock("explosive_charge",
            () -> new ExplosiveChargeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> NUCLEAR_CHARGE = registerBlock("nuclear_charge",
            () -> new NuclearChargeBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> MINING_CHARGE = registerBlock("mining_charge",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> AUTOMATIC_CRAFTING_TABLE = registerBlock("automatic_crafting_table",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BURNER_PRESS_PREHEATER = registerBlock("burner_press_preheater",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> STEEL_BARREL = registerBlock("steel_barrel",
            () -> new SteelBarrelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> CYCLOTRON = registerBlock("cyclotron",
            () -> new CyclotronBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> ASHPIT = registerBlock("ashpit",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> VAULT_TECH_BLAST_DOOR = registerBlock("vault_tech_blast_door",
            () -> new VaultBlastDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> SLIDING_BLAST_DOOR = registerBlock("sliding_blast_door",
            () -> new SlidingBlastDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> FIRE_DOOR = registerBlock("fire_door",
            () -> new VaultBlastDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> SILO_HATCH = registerBlock("silo_hatch",
            () -> new SiloHatchBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> FLEIJA = registerBlock("fleija",
            () -> new VaultBlastDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> THE_BLUE_RINSE = registerBlock("the_blue_rinse",
            () -> new VaultBlastDoorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> PAINTABLE_COATED_EXHAUST_PIPE = registerSimpleBlock("paintable_coated_exhaust_pipe",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> FLUID_VALVE = registerSimpleBlock("fluid_valve",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> REDSTONE_FLUID_VALVE = registerSimpleBlock("redstone_fluid_valve",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BLOCK_OF_SCRAP = registerSimpleBlock("block_of_scrap",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT)));
    public static final RegistryObject<Block> FOAM = registerSimpleBlock("foam",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SNOW)));
    public static final RegistryObject<Block> BLOCK_OF_COAL_COKE = registerSimpleBlock("block_of_coal_coke",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COAL_BLOCK)));
    public static final RegistryObject<Block> BLOCK_OF_LIGNITE_COKE = registerSimpleBlock("block_of_lignite_coke",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BLOCK_OF_PETROLEUM_COKE = registerSimpleBlock("block_of_petroleum_coke",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BLOCK_OF_BORON = registerSimpleBlock("block_of_boron",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> ROLL_OF_INSULATION = registerSimpleBlock("roll_of_insulation",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL)));
    public static final RegistryObject<Block> BLOCK_OF_ASBESTOS = registerSimpleBlock("block_of_asbestos",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BLOCK_OF_TRINITITE = registerSimpleBlock("block_of_trinitite",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BLOCK_OF_NUCLEAR_WASTE = registerSimpleBlock("block_of_nuclear_waste",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> PAINTED_BLOCK_OF_NUCLEAR_WASTE = registerSimpleBlock("painted_block_of_nuclear_waste",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> BLOCK_OF_SCHRABIDIUM = registerSimpleBlock("block_of_schrabidium",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> COKER_UNIT = registerBlock("coker_unit",
            () -> new CokerUnitBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> BEDROCK_ORE_PROCESSOR = registerBlock("bedrock_ore_processor",
            () -> new BedrockOreProcessorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> PYROLYSIS_OVEN = registerBlock("pyrolysis_oven",
            () -> new PyrolysisOvenBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> ANTI_PERSONELL_MINE = registerBlock("anti_personell_mine",
            () -> new AntiPersonellMineBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> TIME_BOMB = registerBlock("time_bomb",
            () -> new TimeBomb(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> BURNING_GRASS = registerBlock("burning_grass",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK)));
    public static final RegistryObject<Block> SATELLITE_ID_MANAGER = registerSimpleBlock("satellite_id_manager",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> LAUNCH_PAD_COMPONENT_BLOCK = registerSimpleBlock("launch_pad_component_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> LAUNCH_PAD_SCAFFOLD_BLOCK = registerSimpleBlock("launch_pad_scaffold_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> COMPACT_LAUNCHER_CORE_COMPONENT = registerSimpleBlock("compact_launcher_core_component",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> LAUNCH_TABLE_CORE_COMPONENT = registerSimpleBlock("launch_table_core_component",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> SOYUZ_LAUNCHER_CORE_COMPONENT = registerSimpleBlock("soyuz_launcher_core_component",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));
    public static final RegistryObject<Block> COMPACT_LAUNCH_PAD = registerBlock("compact_launch_pad",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));
    public static final RegistryObject<Block> SOYUZ_LAUNCH_PLATFORM = registerBlock("soyuz_launch_platform",
            () -> new SoyuzLaunchPlatform(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion().dynamicShape()));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, int burnTime) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, burnTime);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()) {
            @Override
            public void appendHoverText(ItemStack pStack, @Nullable net.minecraft.world.level.Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
                addCustomTooltip(name, pTooltip);
            }
        });
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, int burnTime) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()) {
            @Override
            public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType) {
                return burnTime;
            }
        });
    }

    private static void addCustomTooltip(String name, List<Component> tooltip) {
        if (name.contains("cluster")) {
            tooltip.add(Component.literal("Drops only when broken by a player").withStyle(ChatFormatting.YELLOW));
        }
        if (name.contains("depth")) {
            tooltip.add(Component.literal("Can only be destroyed by explosions").withStyle(ChatFormatting.YELLOW));
        }
        if (name.contains("diamond_gravel")) {
            tooltip.add(Component.literal("This is some kind of joke here, ").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("but I can't quite tell what it is.").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("As it turns out, 'diamond gravel' was ").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("never really a thing, rendering what might ").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("have been a joke as totally nonsensical.").withStyle(ChatFormatting.GRAY));
        }
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);

    }
}
