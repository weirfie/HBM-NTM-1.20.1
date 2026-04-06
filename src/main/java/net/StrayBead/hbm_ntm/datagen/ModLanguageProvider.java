package net.StrayBead.hbm_ntm.datagen;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, HBMNTM.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        // --- 1. DYNAMIC FLUID IDENTIFIERS ---
        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.FLUID_IDENTIFIERS.entrySet()) {
            String rawName = entry.getKey();
            String prettyName = Arrays.stream(rawName.split("_"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "));
            safeAdd(entry.getValue().get(), prettyName + " Fluid Identifier");
        }

        // --- 1. DYNAMIC FLUID CANISTERS ---
        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.CANISTERS.entrySet()) {
            String rawName = entry.getKey();
            String prettyName = Arrays.stream(rawName.split("_"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "));
            safeAdd(entry.getValue().get(), "Canister: " + prettyName);
        }

        // --- 1. DYNAMIC PACKAGED FLUIDS ---
        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.PACKAGED_FLUIDS.entrySet()) {
            String rawName = entry.getKey();
            String prettyName = Arrays.stream(rawName.split("_"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "));
            safeAdd(entry.getValue().get(), "Packaged " + prettyName);
        }

        // --- 1. DYNAMIC FLUID BARRELS ---
        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.FLUID_BARRELS.entrySet()) {
            String rawName = entry.getKey();
            String prettyName = Arrays.stream(rawName.split("_"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "));
            safeAdd(entry.getValue().get(), "Fluid Barrel: " + prettyName);
        }

        // --- 1. DYNAMIC HAZARDOUS MATERIAL TANKS ---
        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.HAZARDOUS_MATERIAL_TANKS.entrySet()) {
            String rawName = entry.getKey();
            String prettyName = Arrays.stream(rawName.split("_"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "));
            safeAdd(entry.getValue().get(), "Hazardous Material Tank: " + prettyName);
        }

        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.RTG_PELLETS.entrySet()) {
            String rawName = entry.getKey();
            String prettyName = Arrays.stream(rawName.split("_"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "));
            safeAdd(entry.getValue().get(), prettyName + " RTG Pellet");
        }

        // --- 2. DYNAMIC ASSEMBLY TEMPLATES ---
        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.ASSEMBLY_TEMPLATES.entrySet()) {
            String rawName = entry.getKey();
            String prettyName = Arrays.stream(rawName.split("_"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "));
            safeAdd(entry.getValue().get(), "Assembly Template: " + prettyName);
        }

        // --- 2. DYNAMIC CRUCIBLE TEMPLATES ---
        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.CRUCIBLE_TEMPLATES.entrySet()) {
            String rawName = entry.getKey();
            String prettyName = Arrays.stream(rawName.split("_"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "));
            safeAdd(entry.getValue().get(), "Crucible Template: " + prettyName);
        }

        // --- 2. DYNAMIC CHEMISTRY TEMPLATES ---
        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.CHEMISTRY_TEMPLATES.entrySet()) {
            String rawName = entry.getKey();
            String prettyName = Arrays.stream(rawName.split("_"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "));
            safeAdd(entry.getValue().get(), "Chemistry Template: " + prettyName);
        }

        // --- 2. DYNAMIC TANK TEMPLATES ---
        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.FLUID_TANK_NAMES.entrySet()) {
            String rawName = entry.getKey();
            String prettyName = Arrays.stream(rawName.split("_"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "));
            safeAdd(entry.getValue().get(), "Universal Fluid Tank: " + prettyName);
        }

        // --- 2. BEDROCK ORES ---
        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.BEDROCK_ORE_NAMES.entrySet()) {
            String rawName = entry.getKey();
            String prettyName = Arrays.stream(rawName.split("_"))
                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                    .collect(Collectors.joining(" "));
            safeAdd(entry.getValue().get(), prettyName + " Bedrock Ore");
        }

        // --- 3. ITEMS ---
        safeAdd(ModItems.URANIUM_DUST.get(), "Uranium Dust");
        safeAdd(ModItems.THORIUM_INGOT.get(), "Thorium Ingot");
        safeAdd(ModItems.TUNGSTEN_INGOT.get(), "Tungsten Ingot");
        safeAdd(ModItems.TITANIUM_INGOT.get(), "Titanium Ingot");
        safeAdd(ModItems.STEEL.get(), "Steel");
        safeAdd(ModItems.REDCOPPER.get(), "Red Copper");
        safeAdd(ModItems.COAL_TAR.get(), "Coal Tar");
        safeAdd(ModItems.METAL_DETECTOR.get(), "Metal Detector");
        safeAdd(ModItems.DESH_HAND_DRILL.get(), "Desh Hand Drill");
        safeAdd(ModItems.URANIUM_ROD.get(), "Uranium Rod");
        safeAdd(ModItems.URANIUM_ROD_NEUTRON_SOURCE.get(), "Uranium Rod Neutron Source");
        safeAdd(ModItems.MACHINE_TEMPLATE_FOLDER.get(), "Machine Template Folder");
        safeAdd(ModItems.RADIATION_MEASURER.get(), "Radiation Measurer");
        safeAdd(ModItems.ENERGY_INJECTOR.get(), "Energy Injector");
        safeAdd(ModItems.FLUID_MEASURER.get(), "Fluid Measurer");
        safeAdd(ModItems.FLUID_INJECTOR.get(), "Fluid Injector");
        safeAdd(ModItems.GEIGER_COUNTER.get(), "Geiger Counter");
        safeAdd(ModItems.STEEL_SHELL.get(), "Steel Shell");
        safeAdd(ModItems.FIREBRICK.get(), "Firebrick");
        safeAdd(ModItems.FIRECLAY.get(), "Fireclay");
        safeAdd(ModItems.SULFUR.get(), "Sulfur");
        safeAdd(ModItems.INTEGRATED_CIRCUIT_BOARD.get(), "Integrated Circuit Board");
        safeAdd(ModItems.INFINITE_FLUID_BARREL.get(), "Infinite Fluid Barrel");
        safeAdd(ModItems.NUKE_DETONATOR.get(), "Nuke Detonator");
        safeAdd(ModItems.FLUORITE.get(), "Fluorite");
        safeAdd(ModItems.LEAD_DUST.get(), "Lead Dust");
        safeAdd(ModItems.DETONATOR.get(), "Detonator");
        safeAdd(ModItems.EMPTY_CELL.get(), "Empty Cell");
        safeAdd(ModItems.URANIUM_HEXAFLUORIDE_CELL.get(), "Uranium Hexafluoride Cell");
        safeAdd(ModItems.PLUTONIUM_HEXAFLUORIDE_CELL.get(), "Plutonium Hexafluoride Cell");
        safeAdd(ModItems.DEUTERIUM_CELL.get(), "Deuterium Cell");
        safeAdd(ModItems.TRITIUM_CELL.get(), "Tritium Cell");
        safeAdd(ModItems.SCHRABIDIUM_TRISULFIDE_CELL.get(), "Schrabidium Trisulfide Cell");
        safeAdd(ModItems.ANTIMATTER_CELL.get(), "Antimatter Cell");
        safeAdd(ModItems.ANTISCHRABIDIUM_CELL.get(), "Antischrabidium Cell");
        safeAdd(ModItems.GASEOUS_BALEFIRE_CELL.get(), "Gaseous Balefire Cell");
        safeAdd(ModItems.NUCLEAR_MISSILE.get(), "Nuclear Missile");
        safeAdd(ModItems.MICROCHIP.get(), "Microchip");
        safeAdd(ModItems.PRINTED_SILICON_WAFER.get(), "Printed Silicon Wafer");
        safeAdd(ModItems.CIRCUIT_STAMP.get(), "Circuit Stamp");
        safeAdd(ModItems.SILICON_BOULE.get(), "Silicon Boule");
        safeAdd(ModItems.SILICON_WAFER.get(), "Silicon Wafer");
        safeAdd(ModItems.SILICON_NUGGET.get(), "Silicon Nugget");
        safeAdd(ModItems.BLANK_FOUNDRY_MOLD.get(), "Blank Foundry Mold");
        safeAdd(ModItems.NUGGET_FOUNDRY_MOLD.get(), "Nugget Foundry Mold");
        safeAdd(ModItems.BILLET_FOUNDRY_MOLD.get(), "Billet Foundry Mold");
        safeAdd(ModItems.INGOT_FOUNDRY_MOLD.get(), "Ingot Foundry Mold");
        safeAdd(ModItems.PLATE_FOUNDRY_MOLD.get(), "Plate Foundry Mold");
        safeAdd(ModItems.WIRE_FOUNDRY_MOLD.get(), "Wire Foundry Mold");
        safeAdd(ModItems.CAST_PLATE_FOUNDRY_MOLD.get(), "Cast Plate Foundry Mold");
        safeAdd(ModItems.DENSE_WIRE_FOUNDRY_MOLD.get(), "Dense Wire Foundry Mold");
        safeAdd(ModItems.BLADE_FOUNDRY_MOLD.get(), "Blade Foundry Mold");
        safeAdd(ModItems.SHREDDER_BLADE_FOUNDRY_MOLD.get(), "Shredder Blade Foundry Mold");
        safeAdd(ModItems.SHELL_FOUNDRY_MOLD.get(), "Shell Foundry Mold");
        safeAdd(ModItems.PIPE_FOUNDRY_MOLD.get(), "Pipe Foundry Mold");
        safeAdd(ModItems.COMBUSTION_ENGINE_PISTON.get(), "Combustion Engine Piston");
        safeAdd(ModItems.STEEL_PISTON_SET.get(), "Steel Piston Set");
        safeAdd(ModItems.HIGH_SPEED_STEEL_PISTON_SET.get(), "High-Speed Steel Piston Set");
        safeAdd(ModItems.DESH_PISTON_SET.get(), "Desh Piston Set");
        safeAdd(ModItems.STARMETAL_PISTON_SET.get(), "Starmetal Piston Set");
        safeAdd(ModItems.STEEL_DRILLBIT.get(), "Steel Drillbit");
        safeAdd(ModItems.HIGH_SPEED_STEEL_DRILLBIT.get(), "High-Speed Steel Drillbit");
        safeAdd(ModItems.DESH_DRILLBIT.get(), "Desh Drillbit");
        safeAdd(ModItems.TECHNETIUM_STEEL_DRILLBIT.get(), "Technetium Steel Drillbit");
        safeAdd(ModItems.FERROURANIUM_DRILLBIT.get(), "Ferrouranium Drillbit");
        safeAdd(ModItems.DYATLOV_INSTANT_MELTDOWN_APPLICATOR.get(), "Dyatlov Instant Meltdown Applicator");
        safeAdd(ModItems.SCREWDRIVER.get(), "Screwdriver");
        safeAdd(ModItems.URANIUM_BEDROCK_ORE.get(), "Uranium Bedrock Ore");
        safeAdd(ModItems.CENTRIFUGED_URANIUM_BEDROCK_ORE.get(), "Centrifuged Uranium Bedrock Ore");
        safeAdd(ModItems.CLEANED_URANIUM_BEDROCK_ORE.get(), "Cleaned Uranium Bedrock Ore");
        safeAdd(ModItems.SEPARATED_URANIUM_BEDROCK_ORE.get(), "Separated Uranium Bedrock Ore");
        safeAdd(ModItems.PURIFIED_URANIUM_BEDROCK_ORE.get(), "Purified Uranium Bedrock Ore");
        safeAdd(ModItems.STEEL_SPHERE.get(), "Steel Sphere");
        safeAdd(ModItems.IRON_PLATE.get(), "Iron Plate");
        safeAdd(ModItems.STEEL_BOLT.get(), "Steel Bolt");
        safeAdd(ModItems.REDSTONE_INGOT.get(), "Redstone Ingot");
        safeAdd(ModItems.FLAT_STEEL_CASING.get(), "Flat Steel Casing");
        safeAdd(ModItems.CAST_IRON_PLATE.get(), "Cast Iron Plate");
        safeAdd(ModItems.STEEL_PEDESTAL.get(), "Steel Pedestal");
        safeAdd(ModItems.MILITARY_GRADE_CIRCUIT_BOARD.get(), "Military Grade Circuit Board");
        safeAdd(ModItems.GOLD_WIRE.get(), "Gold Wire");
        safeAdd(ModItems.NITRATED_URANIUM_BEDROCK_ORE.get(), "Nitrated Uranium Bedrock Ore");
        safeAdd(ModItems.MINECRAFT_GRADE_COPPER.get(), "Minecraft Grade Copper");
        safeAdd(ModItems.INDUSTRIAL_GRADE_COPPER.get(), "Industrial Grade Copper");
        safeAdd(ModItems.ADVANCED_ALLOY_INGOT.get(), "Advanced Alloy Ingot");
        safeAdd(ModItems.MAGNETIZED_TUNGSTEN_INGOT.get(), "Magnetized Tungsten Ingot");
        safeAdd(ModItems.CMB_STEEL_INGOT.get(), "CMB Steel Ingot");
        safeAdd(ModItems.HIGH_SPEED_STEEL_INGOT.get(), "High-Speed Steel Ingot");
        safeAdd(ModItems.TECHNETIUM_99_INGOT.get(), "Technetium-99 Ingot");
        safeAdd(ModItems.TECHNETIUM_STEEL_INGOT.get(), "Technetium Steel Ingot");
        safeAdd(ModItems.POLYMER_BAR.get(), "Polymer Bar");
        safeAdd(ModItems.DESH_INGOT.get(), "Desh Ingot");
        safeAdd(ModItems.SATURNITE_INGOT.get(), "Saturnite Ingot");
        safeAdd(ModItems.STARMETAL_INGOT.get(), "Starmetal Ingot");
        safeAdd(ModItems.EUPHEMIUM_INGOT.get(), "Euphemium Ingot");
        safeAdd(ModItems.DINEUTRONIUM_INGOT.get(), "Dineutronium Ingot");
        safeAdd(ModItems.BISMUTH_INGOT.get(), "Bismuth Ingot");
        safeAdd(ModItems.ZIRCONIUM_CUBE.get(), "Zirconium Cube");
        safeAdd(ModItems.URANIUM_233_INGOT.get(), "Uranium-233 Ingot");
        safeAdd(ModItems.URANIUM_235_INGOT.get(), "Uranium-235 Ingot");
        safeAdd(ModItems.STEEL_TANK.get(), "Steel Tank");
        safeAdd(ModItems.MOTOR.get(), "Motor");
        safeAdd(ModItems.STEEL_BEAM.get(), "Steel Beam");
        safeAdd(ModItems.STEEL_PLATE.get(), "Steel Plate");
        safeAdd(ModItems.ANALOG_CIRCUIT_BOARD.get(), "Analog Circuit Board");
        safeAdd(ModItems.COPPER_PLATE.get(), "Copper Plate");
        safeAdd(ModItems.INSULATOR.get(), "Insulator");
        safeAdd(ModItems.PLUTONIUM_INGOT.get(), "Plutonium Ingot");
        safeAdd(ModItems.REACTOR_GRADE_PLUTONIUM_INGOT.get(), "Reactor Grade Plutonium Ingot");
        safeAdd(ModItems.AMERICIUM_242_INGOT.get(), "Americium-242 Ingot");
        safeAdd(ModItems.REACTOR_GRADE_AMERICIUM_INGOT.get(), "Reactor Grade Americium Ingot");
        safeAdd(ModItems.INGOT_OF_URANIUM_FUEL.get(), "Ingot of Uranium Fuel");
        safeAdd(ModItems.ELECTRIC_MOTOR.get(), "Electric Motor");
        safeAdd(ModItems.INGOT_OF_PLUTONIUM_FUEL.get(), "Ingot of Plutonium Fuel");
        safeAdd(ModItems.NEPTUNIUM_FUEL_INGOT.get(), "Neptunium Fuel Ingot");
        safeAdd(ModItems.FLAT_STAMP.get(), "Flat Stamp");
        safeAdd(ModItems.INGOT_OF_MOX_FUEL.get(), "Ingot of Mox Fuel");
        safeAdd(ModItems.INGOT_OF_AMERICIUM_FUEL.get(), "Ingot of Americium Fuel");
        safeAdd(ModItems.INGOT_OF_THORIUM_FUEL.get(), "Ingot of Thorium Fuel");
        safeAdd(ModItems.BORON_INGOT.get(), "Boron Ingot");
        safeAdd(ModItems.SMORE_INGOT.get(), "Smore Ingot");
        safeAdd(ModItems.NIOBIUM_INGOT.get(), "Niobium Ingot");
        safeAdd(ModItems.NEODYMIUM_INGOT.get(), "Neodymium Ingot");
        safeAdd(ModItems.PWR_CONTROLLER_LINKING_DEVICE.get(), "PWR Controller Linking Device");
        safeAdd(ModItems.BROMINE_INGOT.get(), "Bromine Ingot");
        safeAdd(ModItems.REDSTONE_CRYSTALS.get(), "Redstone Crystals");
        safeAdd(ModItems.DROP_OF_MERCURY.get(), "Drop of Mercury");
        safeAdd(ModItems.COAL_POWDER.get(), "Coal Powder");
        safeAdd(ModItems.RARE_EARTH_CRYSTALS.get(), "Rare Earth Crystals");
        safeAdd(ModItems.DESHREADY_BLEND.get(), "DeshReady Blend");
        safeAdd(ModItems.AIRSTRIKE_DESIGNATOR.get(), "Airstrike Designator");
        safeAdd(ModItems.ALUMINUM_INGOT.get(), "Aluminum Ingot");
        safeAdd(ModItems.ALUMINUM_PLATE.get(), "Aluminum Plate");
        safeAdd(ModItems.WIRE_STAMP.get(), "Wire Stamp");
        safeAdd(ModItems.MINECRAFT_GRADE_COPPER_WIRE.get(), "Minecraft Grade Copper Wire");
        safeAdd(ModItems.COPPER_COIL.get(), "Copper Coil");
        safeAdd(ModItems.SHORT_RANGE_TARGET_DESIGNATOR.get(), "Short Range Target Designator");
        safeAdd(ModItems.LONG_RANGE_TARGET_DESIGNATOR.get(), "Long Range Target Designator");
        safeAdd(ModItems.DESH_BLEND.get(), "Desh Blend");
        safeAdd(ModItems.HIGH_EXPLOSIVE_MISSILE.get(), "High Explosive Missile");
        safeAdd(ModItems.RING_COIL.get(), "Ring Coil");
        safeAdd(ModItems.INCENDIARY_MISSILE.get(), "Incendiary Missile");
        safeAdd(ModItems.ANTI_BALLISTIC_MISSILE.get(), "Anti-Ballistic Missile");
        safeAdd(ModItems.SIM_CARD.get(), "SIM Card");
        safeAdd(ModItems.SIM_CARD_ACTIVATOR.get(), "SIM Card Activator");
        safeAdd(ModItems.CAST_STEEL_PLATE.get(), "Cast Steel Plate");
        safeAdd(ModItems.CABLE_DRUM.get(), "Cable Drum");
        safeAdd(ModItems.RUBBER_BAR.get(), "Rubber Bar");
        safeAdd(ModItems.TINY_PILE_OF_LITHIUM_POWDER.get(), "Tiny Pile of Lithium Powder");
        safeAdd(ModItems.POLYMER_BAR.get(), "Polymer Bar");
        safeAdd(ModItems.BAKELITE_BAR.get(), "Bakelite Bar");
        safeAdd(ModItems.QUARTZ_POWDER.get(), "Quartz Powder");
        safeAdd(ModItems.CRYOLITE_CHUNK.get(), "Cryolite Chunk");
        safeAdd(ModItems.LEAD_INGOT.get(), "Lead Ingot");
        safeAdd(ModItems.IRON_POWDER.get(), "Iron Powder");
        safeAdd(ModItems.CLAY_CATALYST.get(), "Clay Catalyst");
        safeAdd(ModItems.VACUUM_TUBE.get(), "Vacuum Tube");
        safeAdd(ModItems.TUNGSTEN_BOLT.get(), "Tungsten Bolt");
        safeAdd(ModItems.CARBON_WIRE.get(), "Carbon Wire");
        safeAdd(ModItems.SCHRABIDIUM_WIRE.get(), "Schrabidium Wire");
        safeAdd(ModItems.COPPER_WIRE.get(), "Copper Wire");
        safeAdd(ModItems.TUNGSTEN_WIRE.get(), "Tungsten Wire");
        safeAdd(ModItems.ALUMINUM_WIRE.get(), "Aluminum Wire");
        safeAdd(ModItems.LEAD_WIRE.get(), "Lead Wire");
        safeAdd(ModItems.ZIRCONIUM_WIRE.get(), "Zirconium Wire");
        safeAdd(ModItems.STEEL_WIRE.get(), "Steel Wire");
        safeAdd(ModItems.THERMOELECTRIC_ELEMENT.get(), "Thermoelectric Element");
        safeAdd(ModItems.ADVANCED_ALLOY_WIRE.get(), "Advanced Alloy Wire");
        safeAdd(ModItems.PRINTED_CIRCUIT_BOARD.get(), "Printed Circuit Board");
        safeAdd(ModItems.MAGNETIZED_TUNGSTEN_WIRE.get(), "Magnetized Tungsten Wire");
        safeAdd(ModItems.CAPACITOR.get(), "Capacitor");
        safeAdd(ModItems.STEEL_PICKAXE.get(), "Steel Pickaxe");
        safeAdd(ModItems.TITANIUM_PICKAXE.get(), "Titanium Pickaxe");
        safeAdd(ModItems.COBALT_PICKAXE.get(), "Cobalt Pickaxe");
        safeAdd(ModItems.SCHRABIDIUM_INGOT.get(), "Schrabidium Ingot");
        safeAdd(ModItems.ADVANCED_ALLOY_PICKAXE.get(), "Advanced Alloy Pickaxe");
        safeAdd(ModItems.WORKER_ALLOY_PICKAXE.get(), "Worker's Alloy Pickaxe");
        safeAdd(ModItems.CMB_STEEL_PICKAXE.get(), "CMB Steel Pickaxe");
        safeAdd(ModItems.SCHRABIDIUM_PICKAXE.get(), "Schrabidium Pickaxe");
        safeAdd(ModItems.STARMETAL_PICKAXE.get(), "Starmetal Pickaxe");
        safeAdd(ModItems.STEEL_SHREDDER_BLADES.get(), "Steel Shredder Blades");
        safeAdd(ModItems.OIL_RESERVOIR_DETECTOR.get(), "Oil Reservoir Detector");
        safeAdd(ModItems.FAT_MAN_DROP_LOCATION_DESIGNATOR.get(), "Fat Man Drop Location Designator");

        // --- 4. BLOCKS ---
        safeAdd(ModBlocks.COPPER_CABLE.get(), "Copper Cable");
        safeAdd(ModBlocks.HYDROTREATER.get(), "Hydrotreater");
        safeAdd(ModBlocks.QUARTZ_GLASS.get(), "Quartz Glass");
        safeAdd(ModBlocks.SHALLOW_FOUNDRY_BASIN.get(), "Shallow Foundry Basin");
        safeAdd(ModBlocks.QUARTZ_SAND.get(), "Quartz Sand");
        safeAdd(ModBlocks.SOLDERING_STATION.get(), "Soldering Station");
        safeAdd(ModBlocks.SIM_CARD_SIGNAL_BROADCASTING_DEVICE.get(), "SIM Card Signal Broadcasting Device");
        safeAdd(ModBlocks.ELECTROLYSIS_MACHINE.get(), "Electrolysis Machine");
        safeAdd(ModBlocks.FEL.get(), "FEL");
        safeAdd(ModBlocks.LIGNITE_ORE.get(), "Lignite Ore");
        safeAdd(ModBlocks.WOOD_BURNING_GENERATOR.get(), "Wood Burning Generator");
        safeAdd(ModBlocks.INDUSTRIAL_BOILER.get(), "Industrial Boiler");
        safeAdd(ModBlocks.LARGE_ELECTRICITY_PYLON.get(), "Large Electricity Pylon");
        safeAdd(ModBlocks.DEUTERIUM_EXTRACTION_TOWER.get(), "Deuterium Extraction Tower");
        safeAdd(ModBlocks.CRUCIBLE.get(), "Crucible");
        safeAdd(ModBlocks.OIL_RESERVOIR.get(), "Oil Reservoir");
        safeAdd(ModBlocks.BUNKER_DOOR.get(), "Bunker Door");
        safeAdd(ModBlocks.SULFUR_ORE.get(), "Sulfur Ore");
        safeAdd(ModBlocks.DOOR_METAL.get(), "Metal Door");
        safeAdd(ModBlocks.PAINTABLE_COATED_UNIVERSAL_FLUID_DUCT.get(), "Paintable Coated Universal Fluid Duct");
        safeAdd(ModBlocks.STEEL_DECO_BLOCK.get(), "Steel Deco Block");
        safeAdd(ModBlocks.STEEL_POLE.get(), "Steel Pole");
        safeAdd(ModBlocks.ANTENNA_TOP.get(), "Antenna Top");
        safeAdd(ModBlocks.TAPE_RECORDER.get(), "Tape Recorder");
        safeAdd(ModBlocks.INDUSTRIAL_COMBUSTION_GENERATOR.get(), "Industrial Combustion Generator");
        safeAdd(ModBlocks.SILEX.get(), "Laser Isotope Separation Chamber (SILEX)");
        safeAdd(ModBlocks.CATALYTIC_CRACKING_TOWER.get(), "Catalytic Cracking Tower");
        safeAdd(ModBlocks.ARC_WELDER.get(), "Arc Welder");
        safeAdd(ModBlocks.STEEL_BLOCK.get(), "Block of Steel");
        safeAdd(ModBlocks.ALUMINUM_ORE.get(), "Aluminum-Bearing Ore");
        safeAdd(ModBlocks.BREEDING_REACTOR.get(), "Breeding Reactor");
        safeAdd(ModBlocks.RARE_EARTH_ORE.get(), "Rare Earth Ore");
        safeAdd(ModBlocks.COMBINATION_OVEN.get(), "Combination Oven");
        safeAdd(ModBlocks.CHEMICAL_PLANT.get(), "Chemical Plant");
        safeAdd(ModBlocks.MATTER_GIGAFACTORY.get(), "UU Matter Gigafactory");
        safeAdd(ModBlocks.BATTERY_SOCKET.get(), "Battery Socket");
        safeAdd(ModBlocks.REDSTONE_BATTERY.get(), "Redstone Battery");
        safeAdd(ModBlocks.OIL_REFINERY.get(), "Oil Refinery");
        safeAdd(ModBlocks.OIL_DERRICK.get(), "Oil Derrick");
        safeAdd(ModBlocks.TURBOFAN.get(), "Turbofan");
        safeAdd(ModBlocks.RED_COPPER_BLOCK.get(), "Block of Red Copper");
        safeAdd(ModBlocks.POWER_SWITCH.get(), "Power Switch");
        safeAdd(ModBlocks.CHEMICAL_FACTORY.get(), "Chemical Factory");
        safeAdd(ModBlocks.MALACHITE.get(), "Malachite");
        safeAdd(ModBlocks.HEMATITE.get(), "Hematite");
        safeAdd(ModBlocks.FRACTIONATING_TOWER_SEPARATOR.get(), "Fractionating Tower Separator");
        safeAdd(ModBlocks.ASSEMBLY_MACHINE.get(), "Assembly Machine");
        safeAdd(ModBlocks.STEEL_GRATE.get(), "Steel Grate");
        safeAdd(ModBlocks.STEEL_SCAFFOLD.get(), "Steel Scaffold");
        safeAdd(ModBlocks.CONVEYOR_LIFT.get(), "Conveyor Lift");
        safeAdd(ModBlocks.CRUDE_OIL_PIPE.get(), "Crude Oil Pipe");
        safeAdd(ModBlocks.URANIUM_ORE_BLOCK.get(), "Block of Uranium");
        safeAdd(ModBlocks.ORE_ACIDIZER.get(), "Ore Acidizer");
        safeAdd(ModBlocks.CONVEYOR_SPLITTER.get(), "Conveyor Splitter");
        safeAdd(ModBlocks.URANIUM_ORE.get(), "Uranium Ore");
        safeAdd(ModBlocks.ZIRNOX_NUCLEAR_REACTOR.get(), "Zirnox Nuclear Reactor");
        safeAdd(ModBlocks.TUNGSTEN_ORE_BLOCK.get(), "Block of Tungsten");
        safeAdd(ModBlocks.HYDRAULIC_FRACKING_TOWER.get(), "Hydraulic Fracking Tower");
        safeAdd(ModBlocks.BOILER.get(), "Boiler");
        safeAdd(ModBlocks.CONVEYOR_INSERTER.get(), "Conveyor Inserter");
        safeAdd(ModBlocks.CONVEYOR_EJECTOR.get(), "Conveyor Ejector");
        safeAdd(ModBlocks.TUNGSTEN_ORE.get(), "Tungsten Ore");
        safeAdd(ModBlocks.SMOKESTACK.get(), "Smokestack");
        safeAdd(ModBlocks.TANK.get(), "Tank");
        safeAdd(ModBlocks.TITANIUM_ORE_BLOCK.get(), "Block of Titanium");
        safeAdd(ModBlocks.TITANIUM_ORE.get(), "Titanium Ore");
        safeAdd(ModBlocks.BLOCK_ON.get(), "Block On");
        safeAdd(ModBlocks.BLOCK_OFF.get(), "Block Off");
        safeAdd(ModBlocks.SIGNAL_BLOCK.get(), "Signal Block");
        safeAdd(ModBlocks.BLOCK_OF_GRAPHITE.get(), "Block of Graphite");
        safeAdd(ModBlocks.CENTRIFUGE.get(), "Centrifuge");
        safeAdd(ModBlocks.INDUSTRIAL_COOLING_TOWER.get(), "Industrial Cooling Tower");
        safeAdd(ModBlocks.THE_GADGET.get(), "The Gadget");
        safeAdd(ModBlocks.GRAPHITE.get(), "Empty Graphite");
        safeAdd(ModBlocks.TSAR_BOMBA.get(), "Tsar Bomba");
        safeAdd(ModBlocks.LEAD_ANVIL.get(), "Lead Anvil");
        safeAdd(ModBlocks.BLAST_FURNACE.get(), "Blast Furnace");
        safeAdd(ModBlocks.DRILLED_GRAPHITE.get(), "Drilled Graphite");
        safeAdd(ModBlocks.FUEL_ROD.get(), "RBMK Fuel Rod Neutron Source");
        safeAdd(ModBlocks.STEAM_CHANNEL.get(), "RBMK Steam Channel");
        safeAdd(ModBlocks.GRAPHITE_MODERATOR.get(), "RBMK Graphite Moderator");
        safeAdd(ModBlocks.CONTROL_ROD.get(), "RBMK Control Rod");
        safeAdd(ModBlocks.STRUCTURAL_COLUMN.get(), "RBMK Structural Column");
        safeAdd(ModBlocks.FUEL_ROD_EMPTY.get(), "Empty RBMK Fuel Channel");
        safeAdd(ModBlocks.FUEL_ROD_NORMAL.get(), "RBMK Fuel Rod");
        safeAdd(ModBlocks.NEUTRON_REFLECTOR.get(), "RBMK Neutron Reflector");
        safeAdd(ModBlocks.RADIATED_GRAPHITE.get(), "Radiated Graphite");
        safeAdd(ModBlocks.ULTRA_DENSE_STEAM_PIPE.get(), "Ultra Dense Steam Pipe");
        safeAdd(ModBlocks.SUPER_DENSE_STEAM_PIPE.get(), "Super Dense Steam Pipe");
        safeAdd(ModBlocks.DENSE_STEAM_PIPE.get(), "Dense Steam Pipe");
        safeAdd(ModBlocks.STEAM_PIPE.get(), "Steam Pipe");
        safeAdd(ModBlocks.SOLAR_PANEL.get(), "Solar Panel");
        safeAdd(ModBlocks.CONCRETE_BRICKS.get(), "Concrete Bricks");
        safeAdd(ModBlocks.WATER_DUCT.get(), "Water Duct");
        safeAdd(ModBlocks.STEAM_SEPARATOR.get(), "Steam Separator");
        safeAdd(ModBlocks.CONTROL_SYSTEM.get(), "RBMK Control System");
        safeAdd(ModBlocks.DEAD_GRASS.get(), "Dead Grass");
        safeAdd(ModBlocks.WOOD_BRICKS.get(), "Wooden Bricks");
        safeAdd(ModBlocks.REINFORCED_GLASS.get(), "Reinforced Glass");
        safeAdd(ModBlocks.WARNING_BLOCK.get(), "Builder's Choice Concrete - Hazard Stripe");
        safeAdd(ModBlocks.PWR_CONTROL_ROD.get(), "PWR Control Rod");
        safeAdd(ModBlocks.PWR_FUEL_ROD.get(), "PWR Fuel Rod");
        safeAdd(ModBlocks.PWR_NEUTRON_REFLECTOR.get(), "PWR Neutron Reflector");
        safeAdd(ModBlocks.PWR_COOLANT_CHANNEL.get(), "PWR Coolant Channel");
        safeAdd(ModBlocks.PWR_HEAT_EXCHANGER.get(), "PWR Heat Exchanger");
        safeAdd(ModBlocks.PWR_NEUTRON_SOURCE.get(), "PWR Neutron Source");
        safeAdd(ModBlocks.PWR_CONTROLLER.get(), "PWR Controller");
        safeAdd(ModItems.CORIUM_BUCKET.get(), "Bucket of Corium");
        safeAdd(ModBlocks.FAN.get(), "Fan");
        safeAdd(ModBlocks.PWR_HEATSINK.get(), "PWR Heat Sink");
        safeAdd(ModBlocks.FAT_MAN.get(), "Fat Man");
        safeAdd(ModBlocks.LEVIATHAN_STEAM_TURBINE.get(), "Leviathan Steam Turbine");
        safeAdd(ModBlocks.COOLING_TOWER.get(), "Cooling Tower");
        safeAdd(ModBlocks.COOLING_TOWER_1.get(), "Cooling Tower");
        safeAdd(ModBlocks.WATER_TANK.get(), "Water Tank");
        safeAdd(ModBlocks.WATER_TANK_TOP.get(), "Water Tank");
        safeAdd(ModBlocks.SHREDDER.get(), "Shredder");
        safeAdd(ModBlocks.SPARK_ENERGY_BATTERY.get(), "Spark Energy Battery");
        safeAdd(ModBlocks.SCHRABIDIUM_BLOCK.get(), "Schrabidium Block");
        safeAdd(ModBlocks.SCHRABIDIUM_ORE.get(), "Schrabidium Ore");
        safeAdd(ModBlocks.SILO_LAUNCH_PAD.get(), "Silo Launch Pad");
        safeAdd(ModBlocks.CASTLE_BRAVO.get(), "Castle Bravo");
        safeAdd(ModBlocks.DET_CORD.get(), "Det Cord");
        safeAdd(ModBlocks.PARTICLE_ACCELERATOR_PLATING.get(), "Particle Accelerator Plating");
        safeAdd(ModBlocks.LEAD_ORE.get(), "Lead Ore");
        safeAdd(ModBlocks.LEAD_BLOCK.get(), "Block Of Lead");
        safeAdd(ModBlocks.THORIUM_ORE.get(), "Thorium Ore");
        safeAdd(ModBlocks.RADIOACTIVE_BARREL.get(), "Radioactive Barrel");
        safeAdd(ModBlocks.NEUTRON_ABSORBER.get(), "Neutron Absorber");
        safeAdd(ModBlocks.BURNER_PRESS.get(), "Burner Press");
        safeAdd(ModBlocks.COMBUSTION_GENERATOR.get(), "Combustion Generator");
        safeAdd(ModBlocks.CONTROL_ROD_TOP.get(), "Control Rod");
        safeAdd(ModBlocks.STEAM_CHANNEL_TOP.get(), "Steam Channel Rod");
        safeAdd(ModBlocks.RADIATION_ABSORBER.get(), "Radiation Absorber");
        safeAdd(ModBlocks.ENHANCED_RADIATION_ABSORBER.get(), "Enhanced Radiation Absorber");
        safeAdd(ModBlocks.ADVANCED_RADIATION_ABSORBER.get(), "Advanced Radiation Absorber");
        safeAdd(ModBlocks.ELITE_RADIATION_ABSORBER.get(), "Elite Radiation Absorber");
        safeAdd(ModBlocks.PLAYER_DECONTAMINATOR.get(), "Player Decontaminator");
        safeAdd(ModBlocks.BERYLLIUM_ORE.get(), "Beryllium Ore");
        safeAdd(ModBlocks.CHLORINE_VENT.get(), "Chlorine Vent");
        safeAdd(ModBlocks.MOLDY_DEBRIS.get(), "Moldy Debris");
        safeAdd(ModBlocks.ASPHALT.get(), "Asphalt");
        safeAdd(ModBlocks.GLOWING_ASPHALT.get(), "Glowing Asphalt");
        safeAdd(ModBlocks.LARGE_VINYL_TILE.get(), "Large Vinyl Tile");
        safeAdd(ModBlocks.SMALL_VINYL_TILES.get(), "Small Vinyl Tiles");
        safeAdd(ModBlocks.FIREBRICKS.get(), "Firebricks");
        safeAdd(ModBlocks.REINFORCED_GLOWSTONE.get(), "Reinforced Glowstone");
        safeAdd(ModBlocks.REINFORCED_LAMP.get(), "Reinforced Lamp");
        safeAdd(ModBlocks.REINFORCED_SANDSTONE.get(), "Reinforced Sandstone");
        safeAdd(ModBlocks.CRUSHED_OBSIDIAN.get(), "Crushed Obsidian");
        safeAdd(ModBlocks.LIGHT_BRICKS.get(), "Light Bricks");
        safeAdd(ModBlocks.NUCLEAR_SIREN.get(), "Nuclear Siren");
        safeAdd(ModBlocks.REINFORCED_LAMINATE.get(), "Reinforced Laminate");
        safeAdd(ModBlocks.MARKED_CONCRETE_BRICKS.get(), "Marked Concrete Bricks");
        safeAdd(ModBlocks.BROKEN_CONCRETE_BRICKS.get(), "Broken Concrete Bricks");
        safeAdd(ModBlocks.CRACKED_CONCRETE_BRICKS.get(), "Cracked Concrete Bricks");
        safeAdd(ModBlocks.DENSE_STONE.get(), "Dense Stone");
        safeAdd(ModBlocks.REINFORCED_STEEL_SCAFFOLD.get(), "Reinforced Steel Scaffold");
        safeAdd(ModBlocks.CONCRETE_TILE.get(), "Concrete Tile");
        safeAdd(ModBlocks.ASBESTOS_CONCRETE.get(), "Asbestos Concrete");
        safeAdd(ModBlocks.REBAR_REINFORCED_CONCRETE_PILLAR.get(), "Rebar Reinforced Concrete Pillar");
        safeAdd(ModBlocks.MOSSY_CONCRETE_BRICKS.get(), "Mossy Concrete Bricks");
        safeAdd(ModBlocks.CMB_STEEL_TILE.get(), "CMB Steel Tile");
        safeAdd(ModBlocks.DUCRETE.get(), "Ducrete");
        safeAdd(ModBlocks.FRACTIONATING_TOWER.get(), "Fractionating Tower");
        safeAdd(ModBlocks.DUCRETE_TILE.get(), "Ducrete Tile");
        safeAdd(ModBlocks.OBSIDIAN_BRICKS.get(), "Obsidian Bricks");
        safeAdd(ModBlocks.REINFORCED_STONE.get(), "Reinforced Stone");
        safeAdd(ModBlocks.DRAINAGE_PIPE.get(), "Drainage Pipe");
        safeAdd(ModBlocks.UBER_CONCRETE.get(), "Uber Concrete");
        safeAdd(ModBlocks.COMPOUND_MESH.get(), "Compound Mesh");
        safeAdd(ModBlocks.DUCRETE_BRICKS.get(), "Ducrete Bricks");
        safeAdd(ModBlocks.INDUSTRIAL_STEAM_TURBINE.get(), "Industrial Steam Turbine");
        safeAdd(ModBlocks.FIREBOX.get(), "Firebox");
        safeAdd(ModBlocks.REINFORCED_DUCRETE.get(), "Reinforced Ducrete");
        safeAdd(ModBlocks.REINFORCED_CMB_BRICKS.get(), "Reinforced CMB Bricks");
        safeAdd(ModBlocks.IVY_MIKE.get(), "Ivy Mike");
        safeAdd(ModBlocks.LITTLE_BOY.get(), "Little Boy");
        safeAdd(ModBlocks.CONVEYOR_BELT.get(), "Conveyor Belt");
        safeAdd(ModBlocks.UNIVERSAL_FLUID_DUCT.get(), "Universal Fluid Duct");
        safeAdd(ModBlocks.CONVEYOR_BELT_FACING_Z.get(), "Conveyor Belt");
        safeAdd(ModBlocks.VACUUM_REFINERY.get(), "Vacuum Refinery");
        safeAdd(ModBlocks.CATALYTIC_REFORMER.get(), "Catalytic Reformer");
        safeAdd(ModBlocks.PUMPJACK.get(), "Pumpjack");
        safeAdd(ModBlocks.ELECTRIC_ARC_FURNACE.get(), "Electric Arc Furnace");
        safeAdd(ModBlocks.LARGE_MINING_DRILL.get(), "Large Mining Drill");
        safeAdd(ModBlocks.EXPOSURE_CHAMBER.get(), "Exposure Chamber");
        safeAdd(ModBlocks.COMPRESSOR.get(), "Compressor");
        safeAdd(ModBlocks.FLARE_STACK.get(), "Flare Stack");

        // --- 5. GUIs & STRINGS ---
        add("death.attack.vaporization", "%1$s was vaporized");
        add("gui.hbm_ntm.steam_channel_gui.label_rbmk_steam_channel", "RBMK Steam Channel");
        add("gui.hbm_ntm.shredder_gui.label_shredder", "Shredder");
        add("gui.hbm_ntm.burner_press_gui.label_burner_press", "Burner Press");
        add("gui.hbm_ntm.burner_press_gui.button_press", "Press");
        add("gui.hbm_ntm.combustian_generator_gui.label_combustian_generator", "Combustion Generator");
        add("gui.hbm_ntm.ore_acidizer_gui.label_ore_acidizer", "Ore Acidizer");
        add("gui.hbm_ntm.big_ass_tank_gui.label_bigass_tank_9000:", "Big-Ass Tank 9000");
        add("hbm_ntm.tooltip.liquid.amount.with.capacity", "%s / %s mB");
        add("hbm_ntm.tooltip.liquid.amount", "%s mB");
        add("gui.hbm_ntm.silo_launch_pad.tooltip_short_range_target_designator", "Short Range Target Designator");
        add("gui.hbm_ntm.wood_burning_generator_gui.tooltip_burn_time_bonuses_logs_300_wo", "Burn Time Bonuses: Logs: +300% Wood: +100%");
        add("gui.hbm_ntm.control_panel_gui.button_sg1", "SG1");
        add("gui.hbm_ntm.control_panel_gui.button_sg2", "SG2");
        add("gui.hbm_ntm.control_panel_gui.button_sg3", "SG3");
        add("gui.hbm_ntm.control_panel_gui.button_sg4", "SG4");
        add("gui.hbm_ntm.control_panel_gui.xenon", "Xenon");
        add("gui.hbm_ntm.designator_gui.label_power", "POWER");
        add("gui.hbm_ntm.designator_gui.label_num_particles", "NUM PARTICLES");
        add("gui.hbm_ntm.control_panel_gui.OPSM", "OPSM");
        add("gui.hbm_ntm.control_panel_gui.status", "Status");
        add("gui.hbm_ntm.control_panel_gui.temperature", "Temperature");
        add("gui.hbm_ntm.control_panel_gui.label_safety_system_override", "Safety System Override");
        add("gui.hbm_ntm.control_panel_gui.label_reactor_info", "Reactor Info");
        add("gui.hbm_ntm.zirnox_nuclear_reactor_gui.label_zirnox_nuclear_reactor", "ZIRNOX Nuclear Reactor");
        add("gui.hbm_ntm.control_panel_gui.label_power_level", "Power Level");
        add("gui.hbm_ntm.control_panel_gui.label_control_panel", "Control Panel");
        add("gui.hbm_ntm.control_panel_gui.label_coolant_injection", "Coolant Injection");
        add("gui.hbm_ntm.control_panel_gui.label_none", "NONE");
        add("gui.hbm_ntm.control_panel_gui.label_heat_exchangers", "Heat Exchangers");

        // --- 6. MISC ---
        add("creativetab.hbm_ntm", "NTM Engineering");
        add("creativetab.hbm_ntm_resources", "NTM Resources and Parts");
        add("effect.hbm_ntm.radiation_poisoning", "Radiation Poisoning");
        add("sounds.hbm_ntm.geiger_click", "Geiger Clicks");
        add("sounds.hbm_ntm.nuclear_explosion", "Nuclear Explosion");

        add("creativetab.hbm_ntm_machine_items_and_fuel", "hbm_ntm Machine Items and Fuel");

        // FLUIDS
        add("fluid_type.hbm_ntm.steam", "Steam");
        add("fluid_type.hbm_ntm.crude_oil", "Crude Oil");
        add("fluid_type.hbm_ntm.corium", "Corium");
        add("fluid_type.hbm_ntm.carbon_dioxide", "Carbon Dioxide");
        add("fluid_type.hbm_ntm.hot_crude_oil", "Hot Crude Oil");
        add("fluid_type.hbm_ntm.heavy_oil", "Heavy Oil");
        add("fluid_type.hbm_ntm.naphtha", "Naphtha");
        add("fluid_type.hbm_ntm.light_oil", "Light Oil");
        add("fluid_type.hbm_ntm.petroleum_gas", "Petroleum Gas");
        add("fluid_type.hbm_ntm.cracked_oil", "Cracked Oil");
        add("fluid_type.hbm_ntm.aromatic_hydrocarbons", "Aromatic Hydrocarbons");
        add("fluid_type.hbm_ntm.unsaturated_hydrocarbons", "Unsaturated Hydrocarbons");
        add("fluid_type.hbm_ntm.natural_gas", "Natural Gas");
        add("fluid_type.hbm_ntm.reformate_gas", "Reformate Gas");
        add("fluid_type.hbm_ntm.heavy_heating_oil", "Heavy Heating Oil");
        add("fluid_type.hbm_ntm.vacuum_light_oil", "Vacuum Light Oil");
        add("fluid_type.hbm_ntm.heavy_water", "Heavy Water");
        add("fluid_type.hbm_ntm.molten_fuel", "Molten Fuel");

        // ADVANCEMENTS
        add("advancements.coal_and_iron.title", "Coal and Iron");
        add("advancements.coal_and_iron.descr", "They salvaged a sunken dreadnought for Explorer 1.");
    }

    private void safeAdd(Item item, String name) {
        try {
            add(item, name);
        } catch (IllegalStateException e) {
            // Log if needed, or silently skip duplicates
        }
    }

    private void safeAdd(Block block, String name) {
        try {
            add(block, name);
        } catch (IllegalStateException e) {
            // Log if needed, or silently skip duplicates
        }
    }
}