package net.StrayBead.hbm_ntm.block.custom.entity;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntites {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, HBMNTM.MOD_ID);

    public static final RegistryObject<BlockEntityType<FuelRodEntity>> FUEL_ROD_ENTITY =
            BLOCK_ENTITIES.register("fuel_rod_entity", () ->
                    BlockEntityType.Builder.of(FuelRodEntity::new,
                            ModBlocks.FUEL_ROD.get()).build(null));
    public static final RegistryObject<BlockEntityType<ZirnoxNuclearReactorBlockEntity>> ZIRNOX_NUCLEAR_REACTOR =
            BLOCK_ENTITIES.register("zirnox_nuclear_reactor_entity", () ->
                    BlockEntityType.Builder.of(ZirnoxNuclearReactorBlockEntity::new,
                            ModBlocks.ZIRNOX_NUCLEAR_REACTOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<MeltdownBlastFurnaceBlockEntity>> BLAST_FURNACE =
            BLOCK_ENTITIES.register("blast_furnace_entity", () ->
                    BlockEntityType.Builder.of(MeltdownBlastFurnaceBlockEntity::new,
                            ModBlocks.BLAST_FURNACE.get()).build(null));
    public static final RegistryObject<BlockEntityType<FlareStackBlockEntity>> FLARE_STACK =
            BLOCK_ENTITIES.register("flare_stack_entity", () ->
                    BlockEntityType.Builder.of(FlareStackBlockEntity::new,
                            ModBlocks.FLARE_STACK.get()).build(null));
    public static final RegistryObject<BlockEntityType<PaintableCoatedUniversalFluidDuctBlockEntity>> COATED_FLUID_DUCT =
            BLOCK_ENTITIES.register("paintable_coated_universal_fluid_duct_entity", () ->
                    BlockEntityType.Builder.of(PaintableCoatedUniversalFluidDuctBlockEntity::new,
                            ModBlocks.PAINTABLE_COATED_UNIVERSAL_FLUID_DUCT.get()).build(null));
    public static final RegistryObject<BlockEntityType<CatalyticCrackingTowerBlockEntity>> CATALYTIC_CRACKING_TOWER =
            BLOCK_ENTITIES.register("catalytic_cracking_tower_entity", () ->
                    BlockEntityType.Builder.of(CatalyticCrackingTowerBlockEntity::new,
                            ModBlocks.CATALYTIC_CRACKING_TOWER.get()).build(null));
    public static final RegistryObject<BlockEntityType<ArcWelderBlockEntity>> ARC_WELDER =
            BLOCK_ENTITIES.register("arc_welder_entity", () ->
                    BlockEntityType.Builder.of(ArcWelderBlockEntity::new,
                            ModBlocks.ARC_WELDER.get()).build(null));
    public static final RegistryObject<BlockEntityType<ChemicalPlantBlockEntity>> CHEMICAL_PLANT =
            BLOCK_ENTITIES.register("chemical_plant_entity", () ->
                    BlockEntityType.Builder.of(ChemicalPlantBlockEntity::new,
                            ModBlocks.CHEMICAL_PLANT.get()).build(null));
    public static final RegistryObject<BlockEntityType<DeuteriumExtractionTowerBlockEntity>> DEUTERIUM_EXTRACTION_TOWER =
            BLOCK_ENTITIES.register("deuterium_extraction_tower_entity", () ->
                    BlockEntityType.Builder.of(DeuteriumExtractionTowerBlockEntity::new,
                            ModBlocks.DEUTERIUM_EXTRACTION_TOWER.get()).build(null));
    public static final RegistryObject<BlockEntityType<CatalyticReformerBlockEntity>> CATALYTIC_REFORMER =
            BLOCK_ENTITIES.register("catalytic_reformer_entity", () ->
                    BlockEntityType.Builder.of(CatalyticReformerBlockEntity::new,
                            ModBlocks.CATALYTIC_REFORMER.get()).build(null));
    public static final RegistryObject<BlockEntityType<SolderingStationBlockEntity>> SOLDERING_STATION =
            BLOCK_ENTITIES.register("soldering_station_entity", () ->
                    BlockEntityType.Builder.of(SolderingStationBlockEntity::new,
                            ModBlocks.SOLDERING_STATION.get()).build(null));
    public static final RegistryObject<BlockEntityType<VacuumRefineryBlockEntity>> VACUUM_REFINERY =
            BLOCK_ENTITIES.register("vacuum_refinery_entity", () ->
                    BlockEntityType.Builder.of(VacuumRefineryBlockEntity::new,
                            ModBlocks.VACUUM_REFINERY.get()).build(null));
    public static final RegistryObject<BlockEntityType<CompressorBlockEntity>> COMPRESSOR =
            BLOCK_ENTITIES.register("compressor_entity", () ->
                    BlockEntityType.Builder.of(CompressorBlockEntity::new,
                            ModBlocks.COMPRESSOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<PylonBlockEntity>> PYLON =
            BLOCK_ENTITIES.register("pylon_entity", () ->
                    BlockEntityType.Builder.of(PylonBlockEntity::new,
                            ModBlocks.LARGE_ELECTRICITY_PYLON.get()).build(null));
    public static final RegistryObject<BlockEntityType<CrucibleBlockEntity>> CRUCIBLE =
            BLOCK_ENTITIES.register("crucible", () ->
                    BlockEntityType.Builder.of(CrucibleBlockEntity::new,
                            ModBlocks.CRUCIBLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<LeadAnvilBlockEntity>> LEAD_ANVIL =
            BLOCK_ENTITIES.register("lead_anvil_entity", () ->
                    BlockEntityType.Builder.of(LeadAnvilBlockEntity::new,
                            ModBlocks.LEAD_ANVIL.get()).build(null));
    public static final RegistryObject<BlockEntityType<SiloLaunchPadBlockEntity>> SILO_LAUNCH_PAD =
            BLOCK_ENTITIES.register("silo_launch_pad_entity", () ->
                    BlockEntityType.Builder.of(SiloLaunchPadBlockEntity::new,
                            ModBlocks.SILO_LAUNCH_PAD.get()).build(null));
    public static final RegistryObject<BlockEntityType<WoodBurningGeneratorBlockEntity>> WOOD_BURNING_GENERATOR =
            BLOCK_ENTITIES.register("wood_burning_generator_entity", () ->
                    BlockEntityType.Builder.of(WoodBurningGeneratorBlockEntity::new,
                            ModBlocks.WOOD_BURNING_GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<AssemblyMachineBlockEntity>> ASSEMBLY_MACHINE =
            BLOCK_ENTITIES.register("assembly_machine_entity", () ->
                    BlockEntityType.Builder.of(AssemblyMachineBlockEntity::new,
                            ModBlocks.ASSEMBLY_MACHINE.get()).build(null));
    public static final RegistryObject<BlockEntityType<BurnerPressBlockEntity>> BURNER_PRESS =
            BLOCK_ENTITIES.register("burner_press_entity", () ->
                    BlockEntityType.Builder.of(BurnerPressBlockEntity::new,
                            ModBlocks.BURNER_PRESS.get()).build(null));
    public static final RegistryObject<BlockEntityType<GenericBoundingBoxBE>> GENERIC_BOUNDING_BOX =
            BLOCK_ENTITIES.register("generic_bounding_box_entity", () ->
                    BlockEntityType.Builder.of(GenericBoundingBoxBE::new,
                            ModBlocks.GENERIC_BOUNDING_BOX.get()).build(null));
    public static final RegistryObject<BlockEntityType<FractionatingTowerBlockEntity>> FRACTIONATING_TOWER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fractionating_tower_entity", () ->
                    BlockEntityType.Builder.of(FractionatingTowerBlockEntity::new,
                            ModBlocks.FRACTIONATING_TOWER.get()).build(null));
    public static final RegistryObject<BlockEntityType<FluidBlockEntity>> FLUID_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fluid_block_entity", () ->
                    BlockEntityType.Builder.of(FluidBlockEntity::new,
                            ModBlocks.FUEL_ROD.get()).build(null));
    public static final RegistryObject<BlockEntityType<DrainagePipeBlockEntity>> DRAINAGE_PIPE =
            BLOCK_ENTITIES.register("drainage_pipe_entity", () ->
                    BlockEntityType.Builder.of(DrainagePipeBlockEntity::new,
                            ModBlocks.DRAINAGE_PIPE.get()).build(null));
    public static final RegistryObject<BlockEntityType<PWRFuelRodBlockEntity>> PWR_FUEL_ROD_ENTITY =
            BLOCK_ENTITIES.register("pwr_fuel_rod_entity", () ->
                    BlockEntityType.Builder.of(PWRFuelRodBlockEntity::new,
                            ModBlocks.PWR_FUEL_ROD.get()).build(null));
    public static final RegistryObject<BlockEntityType<HydraulicFrackingTowerBlockEntity>> HYDRAULIC_FRACKING_TOWER_ENTITY =
            BLOCK_ENTITIES.register("hydraulic_fracking_tower_entity", () ->
                    BlockEntityType.Builder.of(HydraulicFrackingTowerBlockEntity::new,
                            ModBlocks.FUEL_ROD.get()).build(null));
    public static final RegistryObject<BlockEntityType<CopperCableBlockEntity>> COPPER_CABLE_ENTITY =
            BLOCK_ENTITIES.register("copper_cable_entity", () ->
                    BlockEntityType.Builder.of(CopperCableBlockEntity::new,
                            ModBlocks.COPPER_CABLE.get()).build(null));
    public static final RegistryObject<BlockEntityType<NeutronSourceBlockEntity>> NEUTRON_SOURCE_ENTITY =
            BLOCK_ENTITIES.register("neutron_source_entity", () ->
                    BlockEntityType.Builder.of(NeutronSourceBlockEntity::new,
                            ModBlocks.PWR_NEUTRON_SOURCE.get()).build(null));
    public static final RegistryObject<BlockEntityType<BoilerBlockEntity>> BOILER =
            BLOCK_ENTITIES.register("boiler_entity", () ->
                    BlockEntityType.Builder.of(BoilerBlockEntity::new,
                            ModBlocks.BOILER.get()).build(null));
    public static final RegistryObject<BlockEntityType<OilDerrickBlockEntity>> OIL_DERRICK =
            BLOCK_ENTITIES.register("oil_derrick_entity", () ->
                    BlockEntityType.Builder.of(OilDerrickBlockEntity::new,
                            ModBlocks.OIL_DERRICK.get()).build(null));
    public static final RegistryObject<BlockEntityType<OilRefineryBlockEntity>> OIL_REFINERY =
            BLOCK_ENTITIES.register("oil_refinery_entity", () ->
                    BlockEntityType.Builder.of(OilRefineryBlockEntity::new,
                            ModBlocks.OIL_REFINERY.get()).build(null));
    public static final RegistryObject<BlockEntityType<BatterySocketBlockEntity>> BATTERY_SOCKET =
            BLOCK_ENTITIES.register("battery_socket_entity", () ->
                    BlockEntityType.Builder.of(BatterySocketBlockEntity::new,
                            ModBlocks.BATTERY_SOCKET.get()).build(null));
    public static final RegistryObject<BlockEntityType<SilexBlockEntity>> SILEX =
            BLOCK_ENTITIES.register("silex", () ->
                    BlockEntityType.Builder.of(SilexBlockEntity::new,
                            ModBlocks.SILEX.get()).build(null));
    public static final RegistryObject<BlockEntityType<TurbineBlockEntity>> TURBINE =
            BLOCK_ENTITIES.register("turbine", () ->
                    BlockEntityType.Builder.of(TurbineBlockEntity::new,
                            ModBlocks.LEVIATHAN_STEAM_TURBINE.get()).build(null));
    public static final RegistryObject<BlockEntityType<SparkEnergyBatteryBlockEntity>> SPARK_ENERGY_BATTERY =
            BLOCK_ENTITIES.register("spark_energy_battery_entity", () ->
                    BlockEntityType.Builder.of(SparkEnergyBatteryBlockEntity::new,
                            ModBlocks.SPARK_ENERGY_BATTERY.get()).build(null));
    public static final RegistryObject<BlockEntityType<FanBlockEntity>> FAN =
            BLOCK_ENTITIES.register("fan", () ->
                    BlockEntityType.Builder.of(FanBlockEntity::new,
                            ModBlocks.FAN.get()).build(null));

    public static final RegistryObject<BlockEntityType<SteamChannelBlockEntity>> STEAM_CHANNEL_ENTITY =
            BLOCK_ENTITIES.register("steam_channel_entity", () ->
                    BlockEntityType.Builder.of(SteamChannelBlockEntity::new,
                            ModBlocks.STEAM_CHANNEL.get()).build(null));

    public static final RegistryObject<BlockEntityType<UltradenseSteamPipeBlockEntity>> ULTRA_DENSE_STEAM_ENTITY =
            BLOCK_ENTITIES.register("ultra_dense_steam_entity", () ->
                    BlockEntityType.Builder.of(UltradenseSteamPipeBlockEntity::new,
                            ModBlocks.FUEL_ROD.get()).build(null));

    public static final RegistryObject<BlockEntityType<ShallowFoundryBasinBlockEntity>> SHALLOW_FOUNDRY_BASIN =
            BLOCK_ENTITIES.register("shallow_foundry_basin_entity", () ->
                    BlockEntityType.Builder.of(ShallowFoundryBasinBlockEntity::new,
                            ModBlocks.SHALLOW_FOUNDRY_BASIN.get()).build(null));

    public static final RegistryObject<BlockEntityType<ControlSystemBlockEntity>> CONTROL_SYSTEM =
            BLOCK_ENTITIES.register("control_system_entity", () ->
                    BlockEntityType.Builder.of(ControlSystemBlockEntity::new,
                            ModBlocks.FUEL_ROD.get()).build(null));

    public static final RegistryObject<BlockEntityType<PWRControllerBlockEntity>> PWR_CONTROLLER =
            BLOCK_ENTITIES.register("pwr_controller_entity", () ->
                    BlockEntityType.Builder.of(PWRControllerBlockEntity::new,
                            ModBlocks.FUEL_ROD.get()).build(null));

    public static final RegistryObject<BlockEntityType<CoolingTowerBlockEntity>> COOLING_TOWER = registerBlock("cooling_tower", ModBlocks.COOLING_TOWER, CoolingTowerBlockEntity::new);
    public static final RegistryObject<BlockEntityType<?>> STEAM_SEPARATOR = register("steam_separator", ModBlocks.STEAM_SEPARATOR, SteamSeparatorBlockEntity::new);
    public static final RegistryObject<BlockEntityType<?>> WATER_TANK = register("water_tank", ModBlocks.WATER_TANK, WaterTankBlockEntity::new);
    public static final RegistryObject<BlockEntityType<?>> SHREDDER = register("shredder", ModBlocks.SHREDDER, ShredderBlockEntity::new);
    public static final RegistryObject<BlockEntityType<?>> COMBUSTIAN_GENERATOR = register("combustian_generator", ModBlocks.COMBUSTION_GENERATOR, CombustianGeneratorBlockEntity::new);
    public static final RegistryObject<BlockEntityType<?>> CENTRIFUGE = register("centrifuge", ModBlocks.CENTRIFUGE, CentrifugeBlockEntity::new);
    public static final RegistryObject<BlockEntityType<?>> ORE_ACIDIZER = register("ore_acidizer", ModBlocks.ORE_ACIDIZER, OreAcidizerBlockEntity::new);

    public static final RegistryObject<BlockEntityType<?>> WATER_DUCT = register("water_duct", ModBlocks.WATER_DUCT, WaterDuctBlockEntity::new);
    public static final RegistryObject<BlockEntityType<?>> CRUDE_OIL_PIPE = register("crude_oil_pipe", ModBlocks.CRUDE_OIL_PIPE, CrudeOilPipeBlockEntity::new);

    public static final RegistryObject<BlockEntityType<ControlRodTopBlockEntity>> CONTROL_ROD_TOP =
            registerBlock("control_rod_top", ModBlocks.CONTROL_ROD_TOP, ControlRodTopBlockEntity::new);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> registerBlock(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<T> supplier) {
        return BLOCK_ENTITIES.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    private static RegistryObject<BlockEntityType<?>> register(String registryname, RegistryObject<Block> block, BlockEntityType.BlockEntitySupplier<?> supplier) {
        return BLOCK_ENTITIES.register(registryname, () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
    }
}
