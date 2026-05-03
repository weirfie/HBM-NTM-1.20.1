package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NTMModMenus {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, HBMNTM.MOD_ID);

    public static final RegistryObject<MenuType<ControlPanelGuiMenu>> CONTROL_PANEL_GUI = REGISTRY.register("control_panel_gui", () -> IForgeMenuType.create(ControlPanelGuiMenu::new));
    public static final RegistryObject<MenuType<SteamChannelMenu>> STEAM_CHANNEL_GUI = REGISTRY.register("steam_channel_gui", () -> IForgeMenuType.create(SteamChannelMenu::new));
    public static final RegistryObject<MenuType<ShredderGuiMenu>> SHREDDER_GUI = REGISTRY.register("shredder_gui", () -> IForgeMenuType.create(ShredderGuiMenu::new));
    public static final RegistryObject<MenuType<BurnerPressGuiMenu>> BURNER_PRESS_GUI = REGISTRY.register("burner_press_gui", () -> IForgeMenuType.create(BurnerPressGuiMenu::new));
    public static final RegistryObject<MenuType<CombustianGeneratorGuiMenu>> COMBUSTIAN_GENERATOR_GUI = REGISTRY.register("combustian_generator_gui", () -> IForgeMenuType.create(CombustianGeneratorGuiMenu::new));
    public static final RegistryObject<MenuType<BigAssTankGuiMenu>> BIG_ASS_TANK_GUI =
            REGISTRY.register("big_ass_tank_gui", () -> IForgeMenuType.create((id, inv, data) -> new BigAssTankGuiMenu(id, inv, data)));
    public static final RegistryObject<MenuType<CentrifugeGuiMenu>> CENTRIFUGE_GUI =
            REGISTRY.register("centrifuge_gui", () -> IForgeMenuType.create((id, inv, extraData) ->
                    new CentrifugeGuiMenu(id, inv, extraData, new net.minecraft.world.inventory.SimpleContainerData(2))
            ));
    public static final RegistryObject<MenuType<OreAcidizerGuiMenu>> ORE_ACIDIZER_GUI = REGISTRY.register("ore_acidizer_gui", () -> IForgeMenuType.create(OreAcidizerGuiMenu::new));
    public static final RegistryObject<MenuType<SparkEnergyBatteryMenu>> SPARK_ENERGY_BATTERY_GUI = REGISTRY.register("spark_energy_battery_gui", () -> IForgeMenuType.create(SparkEnergyBatteryMenu::new));
    public static final RegistryObject<MenuType<DesignatorGuiMenu>> DESIGNATOR_GUI = REGISTRY.register("designator_gui", () -> IForgeMenuType.create(DesignatorGuiMenu::new));
    public static final RegistryObject<MenuType<SilexGuiMenu>> SILEX_GUI = REGISTRY.register("silex_gui", () -> IForgeMenuType.create(SilexGuiMenu::new));
    public static final RegistryObject<MenuType<BoilerMenu>> BOILER_MENU = registerMenuType(BoilerMenu::new, "boiler_menu");
    public static final RegistryObject<MenuType<ChemicalPlantMenu>> CHEMICAL_PLANT_MENU = registerMenuType(ChemicalPlantMenu::new, "chemical_plant_menu");
    public static final RegistryObject<MenuType<CompressorMenu>> COMPRESSOR_MENU = registerMenuType(CompressorMenu::new, "compressor_menu");
    public static final RegistryObject<MenuType<CatalyticReformerMenu>> CATALYTIC_REFORMER_MENU = registerMenuType(CatalyticReformerMenu::new, "catalytic_reformer_menu");
    public static final RegistryObject<MenuType<VacuumRefineryMenu>> VACUUM_REFINERY_MENU = registerMenuType(VacuumRefineryMenu::new, "vacuum_refinery_menu");
    public static final RegistryObject<MenuType<SolderingStationMenu>> SOLDERING_STATION_MENU = registerMenuType(SolderingStationMenu::new, "soldering_station_menu");
    public static final RegistryObject<MenuType<LeadAnvilMenu>> LEAD_ANVIL_MENU = registerMenuType(LeadAnvilMenu::new, "lead_anvil_menu");
    public static final RegistryObject<MenuType<FatManMenu>> FAT_MAN_MENU = registerMenuType(FatManMenu::new, "fat_man_menu");
    public static final RegistryObject<MenuType<HydraulicFrackingTowerMenu>> HYDRAULIC_FRACKING_TOWER = registerMenuType(HydraulicFrackingTowerMenu::new, "hydraulic_fracking_tower");
    public static final RegistryObject<MenuType<PyrolysisOvenMenu>> PYROLYSIS_OVEN_ENTITY = registerMenuType(PyrolysisOvenMenu::new, "pyrolysis_oven");
    public static final RegistryObject<MenuType<SiloLaunchPadMenu>> SILO_LAUNCH_PAD_MENU = registerMenuType(SiloLaunchPadMenu::new, "silo_launch_pad_menu");
    public static final RegistryObject<MenuType<WoodBurningGeneratorMenu>> WOOD_BURNING_GENERATOR_MENU = registerMenuType(WoodBurningGeneratorMenu::new, "wood_burning_generator_menu");
    public static final RegistryObject<MenuType<AssemblyMachineMenu>> ASSEMBLY_MACHINE_MENU = registerMenuType(AssemblyMachineMenu::new, "assembly_machine_menu");
    public static final RegistryObject<MenuType<OilDerrickMenu>> OIL_DERRICK_MENU = registerMenuType(OilDerrickMenu::new, "oil_derrick_menu");
    public static final RegistryObject<MenuType<CrucibleMenu>> CRUCIBLE_MENU = registerMenuType(CrucibleMenu::new, "crucible_menu");
    public static final RegistryObject<MenuType<ThePrototypeMenu>> THE_PROTOTYPE_MENU = registerMenuType(ThePrototypeMenu::new, "the_prototype_menu");
    public static final RegistryObject<MenuType<ElectrolysisMachineMenu>> ELECTROLYSIS_MACHINE_MENU = registerMenuType(ElectrolysisMachineMenu::new, "electrolysis_machine_menu");
    public static final RegistryObject<MenuType<CokerUnitMenu>> COKER_UNIT_MENU = registerMenuType(CokerUnitMenu::new, "coker_unit_menu");
    public static final RegistryObject<MenuType<IronCrateMenu>> IRON_CRATE_MENU = registerMenuType(IronCrateMenu::new, "iron_crate_menu");
    public static final RegistryObject<MenuType<TankGuiMenu>> TANK_GUI = registerMenuType(TankGuiMenu::new, "tank_menu");
    public static final RegistryObject<MenuType<FelMenu>> FEL_MENU = registerMenuType(FelMenu::new, "fel_menu");
    public static final RegistryObject<MenuType<ElectricArcFurnaceMenu>> ELECTRIC_ARC_FURNACE_MENU = registerMenuType(ElectricArcFurnaceMenu::new, "electric_arc_furnace_menu");
    public static final RegistryObject<MenuType<RadiolysisChamberMenu>> RADIOLYSIS_CHAMBER_MENU = registerMenuType(RadiolysisChamberMenu::new, "radiolysis_chamber_menu");
    public static final RegistryObject<MenuType<HydrotreaterMenu>> HYDROTREATER_MENU = registerMenuType(HydrotreaterMenu::new, "hydrotreater_menu");
    public static final RegistryObject<MenuType<BedrockOreProcessorMenu>> BEDROCK_ORE_PROCESSOR_MENU = registerMenuType(BedrockOreProcessorMenu::new, "bedrock_ore_processor_menu");
    public static final RegistryObject<MenuType<SteelBarrelGuiMenu>> STEEL_BARREL_GUI = registerMenuType(SteelBarrelGuiMenu::new, "steel_barrel_menu");
    public static final RegistryObject<MenuType<MeltdownBlastFurnaceMenu>> BLAST_FURNACE_MENU = registerMenuType(MeltdownBlastFurnaceMenu::new, "blast_furnace_menu");
    public static final RegistryObject<MenuType<FlareStackMenu>> FLARE_STACK_MENU = registerMenuType(FlareStackMenu::new, "flare_stack_menu");
    public static final RegistryObject<MenuType<ArcWelderMenu>> ARC_WELDER_MENU = registerMenuType(ArcWelderMenu::new, "arc_welder_menu");
    public static final RegistryObject<MenuType<LittleBoyMenu>> LITTLE_BOY_MENU = registerMenuType(LittleBoyMenu::new, "little_boy_menu");
    public static final RegistryObject<MenuType<OilRefineryMenu>> OIL_REFINERY_MENU = registerMenuType(OilRefineryMenu::new, "oil_refinery_menu");
    public static final RegistryObject<MenuType<BatterySocketMenu>> BATTERY_SOCKET_MENU = registerMenuType(BatterySocketMenu::new, "battery_socket_menu");
    public static final RegistryObject<MenuType<PWRControllerGuiMenu>> PWR_CONTROLLER_GUI = registerMenuType(PWRControllerGuiMenu::new, "pwr_controller_gui");
    public static final RegistryObject<MenuType<ZirnoxNuclearReactorGuiMenu>> ZIRNOX_NUCLEAR_REACTOR_GUI = registerMenuType(ZirnoxNuclearReactorGuiMenu::new, "zirnox_reactor_gui");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return REGISTRY.register(name, () -> IForgeMenuType.create(factory));
    }
}
