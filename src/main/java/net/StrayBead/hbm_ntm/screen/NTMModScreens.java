package net.StrayBead.hbm_ntm.screen;

import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NTMModScreens {
    @SubscribeEvent
    public static void clientLoad(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(NTMModMenus.CONTROL_PANEL_GUI.get(), ControlPanelGuiScreen::new);
            MenuScreens.register(NTMModMenus.STEAM_CHANNEL_GUI.get(), SteamChannelScreen::new);
            MenuScreens.register(NTMModMenus.SHREDDER_GUI.get(), ShredderGuiScreen::new);
            MenuScreens.register(NTMModMenus.COMBUSTIAN_GENERATOR_GUI.get(), CombustianGeneratorGuiScreen::new);
            MenuScreens.register(NTMModMenus.BURNER_PRESS_GUI.get(), BurnerPressGuiScreen::new);
            MenuScreens.register(NTMModMenus.CENTRIFUGE_GUI.get(), CentrifugeGuiScreen::new);
            MenuScreens.register(NTMModMenus.SILO_LAUNCH_PAD_MENU.get(), SiloLaunchPadScreen::new);
            MenuScreens.register(NTMModMenus.ORE_ACIDIZER_GUI.get(), OreAcidizerGuiScreen::new);
            MenuScreens.register(NTMModMenus.LEAD_ANVIL_MENU.get(), LeadAnvilScreen::new);
            MenuScreens.register(NTMModMenus.CRUCIBLE_MENU.get(), CrucibleScreen::new);
            MenuScreens.register(NTMModMenus.LITTLE_BOY_MENU.get(), LittleBoyScreen::new);
            MenuScreens.register(NTMModMenus.TANK_GUI.get(), TankGuiScreen::new);
            MenuScreens.register(NTMModMenus.HYDRAULIC_FRACKING_TOWER.get(), HydraulicFrackingTowerScreen::new);
            MenuScreens.register(NTMModMenus.HYDROTREATER_MENU.get(), HydrotreaterScreen::new);
            MenuScreens.register(NTMModMenus.PYROLYSIS_OVEN_ENTITY.get(), PyrolysisOvenScreen::new);
            MenuScreens.register(NTMModMenus.BEDROCK_ORE_PROCESSOR_MENU.get(), BedrockOreProcessorScreen::new);
            MenuScreens.register(NTMModMenus.ARC_WELDER_MENU.get(), ArcWelderScreen::new);
            MenuScreens.register(NTMModMenus.FLARE_STACK_MENU.get(), FlareStackScreen::new);
            MenuScreens.register(NTMModMenus.FAT_MAN_MENU.get(), FatManuScreen::new);
            MenuScreens.register(NTMModMenus.THE_PROTOTYPE_MENU.get(), ThePrototypeScreen::new);
            MenuScreens.register(NTMModMenus.COKER_UNIT_MENU.get(), CokerUnitScreen::new);
            MenuScreens.register(NTMModMenus.ELECTROLYSIS_MACHINE_MENU.get(), ElectrolysisMachineScreen::new);
            MenuScreens.register(NTMModMenus.STEEL_BARREL_GUI.get(), SteelBarrelGuiScreen::new);
            MenuScreens.register(NTMModMenus.IRON_CRATE_MENU.get(), IronCrateScreen::new);
            MenuScreens.register(NTMModMenus.BLAST_FURNACE_MENU.get(), MeltdownBlastFurnaceScreen::new);
            MenuScreens.register(NTMModMenus.SOLDERING_STATION_MENU.get(), SolderingStationScreen::new);
            MenuScreens.register(NTMModMenus.SPARK_ENERGY_BATTERY_GUI.get(), SparkEnergyBatteryScreen::new);
            MenuScreens.register(NTMModMenus.SILEX_GUI.get(), SilexGuiScreen::new);
            MenuScreens.register(NTMModMenus.ELECTRIC_ARC_FURNACE_MENU.get(), ElectricArcFurnaceScreen::new);
            MenuScreens.register(NTMModMenus.BIG_ASS_TANK_GUI.get(), BigAssTankGuiScreen::new);
            MenuScreens.register(NTMModMenus.PWR_CONTROLLER_GUI.get(), PWRControllerGuiScreen::new);
            MenuScreens.register(NTMModMenus.ZIRNOX_NUCLEAR_REACTOR_GUI.get(), ZirnoxReactorGuiScreen::new);
            MenuScreens.register(NTMModMenus.BATTERY_SOCKET_MENU.get(), BatterySocketScreen::new);
            MenuScreens.register(NTMModMenus.COMPRESSOR_MENU.get(), CompressorScreen::new);
            MenuScreens.register(NTMModMenus.OIL_DERRICK_MENU.get(), OilDerrickScreen::new);
            MenuScreens.register(NTMModMenus.FEL_MENU.get(), FelScreen::new);
            MenuScreens.register(NTMModMenus.OIL_REFINERY_MENU.get(), OilRefineryScreen::new);
            MenuScreens.register(NTMModMenus.RADIOLYSIS_CHAMBER_MENU.get(), RadiolysisChamberScreen::new);
            MenuScreens.register(NTMModMenus.ASSEMBLY_MACHINE_MENU.get(), AssemblyMachineScreen::new);
            MenuScreens.register(NTMModMenus.DESIGNATOR_GUI.get(), DesignatorGuiScreen::new);
            MenuScreens.register(NTMModMenus.CATALYTIC_REFORMER_MENU.get(), CatalyticReformerScreen::new);
            MenuScreens.register(NTMModMenus.CHEMICAL_PLANT_MENU.get(), ChemicalPlantScreen::new);
            MenuScreens.register(NTMModMenus.VACUUM_REFINERY_MENU.get(), VacuumRefineryScreen::new);
            MenuScreens.register(NTMModMenus.WOOD_BURNING_GENERATOR_MENU.get(), WoodBurningGeneratorScreen::new);
        });
    }
}
