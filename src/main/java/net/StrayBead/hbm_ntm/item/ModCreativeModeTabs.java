package net.StrayBead.hbm_ntm.item;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.event.FluidColorRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HBMNTM.MOD_ID);

    public static final RegistryObject<CreativeModeTab> hbm_ntm_ENGINEERING = CREATIVE_MODE_TABS.register("hbm_ntm",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.URANIUM_DUST.get()))
                    .title(Component.translatable("creativetab.hbm_ntm"))
                    .displayItems((itemDisplayParameters, pOutput) -> {
                        pOutput.accept(ModItems.URANIUM_DUST.get());
                        pOutput.accept(ModItems.THORIUM_INGOT.get());
                        pOutput.accept(ModItems.REDCOPPER.get());
                        pOutput.accept(ModItems.TITANIUM_INGOT.get());
                        pOutput.accept(ModItems.TUNGSTEN_INGOT.get());
                        pOutput.accept(ModItems.STEEL_BOLT.get());
                        pOutput.accept(ModItems.REDSTONE_INGOT.get());
                        pOutput.accept(ModItems.TUNGSTEN_BOLT.get());
                        pOutput.accept(ModItems.STEEL.get());
                        pOutput.accept(ModItems.METAL_DETECTOR.get());
                        pOutput.accept(ModBlocks.URANIUM_ORE_BLOCK.get());
                        pOutput.accept(ModBlocks.URANIUM_ORE.get());
                        pOutput.accept(ModBlocks.TITANIUM_ORE_BLOCK.get());
                        pOutput.accept(ModBlocks.SULFUR_ORE.get());
                        pOutput.accept(ModBlocks.ALUMINUM_ORE.get());
                        pOutput.accept(ModItems.CRYOLITE_CHUNK.get());
                        pOutput.accept(ModBlocks.SCHRABIDIUM_ORE.get());
                        pOutput.accept(ModBlocks.FLOURITE_ORE.get());
                        pOutput.accept(ModBlocks.LIGNITE_ORE.get());
                        pOutput.accept(ModBlocks.RARE_EARTH_ORE.get());
                        pOutput.accept(ModBlocks.TITANIUM_ORE.get());
                        pOutput.accept(ModBlocks.TUNGSTEN_ORE.get());
                        pOutput.accept(ModBlocks.STEEL_BLOCK.get());
                        pOutput.accept(ModBlocks.RED_COPPER_BLOCK.get());
                        pOutput.accept(ModBlocks.POWER_SWITCH.get());
                        pOutput.accept(ModItems.INDUSTRIAL_GRADE_COPPER.get());
                        pOutput.accept(ModItems.ADVANCED_ALLOY_INGOT.get());
                        pOutput.accept(ModItems.MINECRAFT_GRADE_COPPER.get());
                        pOutput.accept(ModBlocks.TUNGSTEN_ORE_BLOCK.get());
                        pOutput.accept(ModBlocks.THORIUM_ORE.get());
                        pOutput.accept(ModBlocks.LEAD_ORE.get());
                        pOutput.accept(ModBlocks.BERYLLIUM_ORE.get());
                        pOutput.accept(ModItems.RAW_BERYLLIUM.get());
                        pOutput.accept(ModItems.BERYLLIUM_INGOT.get());
                        pOutput.accept(ModItems.FLUORITE.get());
                        pOutput.accept(ModItems.LEAD_DUST.get());
                        pOutput.accept(ModItems.ANALOG_CIRCUIT_BOARD.get());
                        pOutput.accept(ModItems.INSULATOR.get());
                        pOutput.accept(ModItems.STEEL_TANK.get());
                        pOutput.accept(ModItems.COPPER_PLATE.get());
                        pOutput.accept(ModItems.IRON_PLATE.get());
                        pOutput.accept(ModItems.MINECRAFT_GRADE_COPPER_WIRE.get());
                        pOutput.accept(ModItems.COPPER_COIL.get());
                        pOutput.accept(ModItems.RING_COIL.get());
                        pOutput.accept(ModBlocks.LEAD_BLOCK.get());
                        pOutput.accept(ModItems.STEEL_BEAM.get());
                        pOutput.accept(ModItems.STEEL_SHREDDER_BLADES.get());
                        pOutput.accept(ModItems.CAST_STEEL_PLATE.get());
                        pOutput.accept(ModItems.CAST_IRON_PLATE.get());
                        pOutput.accept(ModBlocks.BLOCK_ON.get());
                        pOutput.accept(ModBlocks.BLOCK_OFF.get());
                        pOutput.accept(ModBlocks.SIGNAL_BLOCK.get());
                        pOutput.accept(ModBlocks.BLOCK_OF_GRAPHITE.get());
                        pOutput.accept(ModItems.FLAT_STAMP.get());
                        pOutput.accept(ModItems.WIRE_STAMP.get());
                        pOutput.accept(ModItems.STEEL_PLATE.get());
                        pOutput.accept(ModItems.MOTOR.get());
                        pOutput.accept(ModItems.OIL_RESERVOIR_DETECTOR.get());
                        pOutput.accept(ModItems.THERMOELECTRIC_ELEMENT.get());
                        pOutput.accept(ModItems.DESH_HAND_DRILL.get());
                        pOutput.accept(ModItems.URANIUM_ROD.get());
                        pOutput.accept(ModItems.URANIUM_BEDROCK_ORE.get());
                        pOutput.accept(ModItems.URANIUM_ROD_NEUTRON_SOURCE.get());
                        pOutput.accept(ModItems.MACHINE_TEMPLATE_FOLDER.get());
                        pOutput.accept(ModBlocks.FUEL_ROD_NORMAL.get());
                        pOutput.accept(ModBlocks.FUEL_ROD_EMPTY.get());
                        pOutput.accept(ModBlocks.CONTROL_ROD.get());
                        pOutput.accept(ModBlocks.GRAPHITE_MODERATOR.get());
                        pOutput.accept(ModBlocks.STEAM_CHANNEL.get());
                        pOutput.accept(ModBlocks.STRUCTURAL_COLUMN.get());
                        pOutput.accept(ModBlocks.NEUTRON_REFLECTOR.get());
                        pOutput.accept(ModBlocks.NEUTRON_ABSORBER.get());
                        pOutput.accept(ModItems.ENERGY_INJECTOR.get());
                        pOutput.accept(ModItems.RADIATION_MEASURER.get());
                        pOutput.accept(ModItems.FLUID_MEASURER.get());
                        pOutput.accept(ModItems.ELECTRIC_MOTOR.get());
                        pOutput.accept(ModBlocks.ULTRA_DENSE_STEAM_PIPE.get());
                        pOutput.accept(ModBlocks.SUPER_DENSE_STEAM_PIPE.get());
                        pOutput.accept(ModBlocks.DENSE_STEAM_PIPE.get());
                        pOutput.accept(ModBlocks.BURNER_PRESS.get());
                        pOutput.accept(ModBlocks.STEAM_PIPE.get());
                        pOutput.accept(ModBlocks.WATER_DUCT.get());
                        pOutput.accept(ModBlocks.STEAM_SEPARATOR.get());
                        pOutput.accept(ModBlocks.CONCRETE_BRICKS.get());
                        pOutput.accept(ModBlocks.CONTROL_SYSTEM.get());
                        pOutput.accept(ModItems.GEIGER_COUNTER.get());
                        pOutput.accept(ModItems.INTEGRATED_CIRCUIT_BOARD.get());
                        pOutput.accept(ModBlocks.DEAD_GRASS.get());
                        pOutput.accept(ModBlocks.REINFORCED_GLASS.get());
                        pOutput.accept(ModBlocks.WARNING_BLOCK.get());
                        pOutput.accept(ModBlocks.WOOD_BRICKS.get());
                        pOutput.accept(ModBlocks.PWR_HEATSINK.get());
                        pOutput.accept(ModBlocks.PWR_HEAT_EXCHANGER.get());
                        pOutput.accept(ModBlocks.PWR_CONTROL_ROD.get());
                        pOutput.accept(ModBlocks.PWR_FUEL_ROD.get());
                        pOutput.accept(ModBlocks.PWR_NEUTRON_SOURCE.get());
                        pOutput.accept(ModBlocks.PWR_NEUTRON_REFLECTOR.get());
                        pOutput.accept(ModBlocks.PWR_COOLANT_CHANNEL.get());
                        pOutput.accept(ModBlocks.PWR_CONTROLLER.get());
                        pOutput.accept(ModBlocks.SOLAR_PANEL.get());
                        pOutput.accept(ModItems.CENTRIFUGED_URANIUM_BEDROCK_ORE.get());
                        pOutput.accept(ModBlocks.FAN.get());
                        pOutput.accept(ModBlocks.THE_GADGET.get());
                        pOutput.accept(ModBlocks.LITTLE_BOY.get());
                        pOutput.accept(ModBlocks.FAT_MAN.get());
                        pOutput.accept(ModBlocks.IVY_MIKE.get());
                        pOutput.accept(ModBlocks.CASTLE_BRAVO.get());
                        pOutput.accept(ModBlocks.TSAR_BOMBA.get());
                        pOutput.accept(ModBlocks.LEVIATHAN_STEAM_TURBINE.get());
                        pOutput.accept(ModBlocks.COOLING_TOWER.get());
                        pOutput.accept(ModItems.NUKE_DETONATOR.get());
                        pOutput.accept(ModBlocks.WATER_TANK.get());
                        pOutput.accept(ModBlocks.SPARK_ENERGY_BATTERY.get());
                        pOutput.accept(ModBlocks.PARTICLE_ACCELERATOR_PLATING.get());
                        pOutput.accept(ModBlocks.SCHRABIDIUM_BLOCK.get());
                        pOutput.accept(ModBlocks.DET_CORD.get());
                        pOutput.accept(ModBlocks.SHREDDER.get());
                        pOutput.accept(ModItems.DETONATOR.get());
                        pOutput.accept(ModBlocks.RADIOACTIVE_BARREL.get());
                        pOutput.accept(ModBlocks.LEAD_ANVIL.get());
                        pOutput.accept(ModBlocks.BLAST_FURNACE.get());
                        pOutput.accept(ModBlocks.COMBUSTION_GENERATOR.get());
                        pOutput.accept(ModBlocks.RADIATION_ABSORBER.get());
                        pOutput.accept(ModBlocks.ENHANCED_RADIATION_ABSORBER.get());
                        pOutput.accept(ModBlocks.ADVANCED_RADIATION_ABSORBER.get());
                        pOutput.accept(ModBlocks.ELITE_RADIATION_ABSORBER.get());
                        pOutput.accept(ModBlocks.PLAYER_DECONTAMINATOR.get());
                        pOutput.accept(ModBlocks.CHLORINE_VENT.get());
                        pOutput.accept(ModBlocks.CHLORINE_SEAL.get());
                        pOutput.accept(ModBlocks.MOLDY_DEBRIS.get());
                        pOutput.accept(ModBlocks.ASPHALT.get());
                        pOutput.accept(ModBlocks.REINFORCED_GLOWSTONE.get());
                        pOutput.accept(ModBlocks.GLOWING_ASPHALT.get());
                        pOutput.accept(ModBlocks.LIGHT_BRICKS.get());
                        pOutput.accept(ModBlocks.MARKED_CONCRETE_BRICKS.get());
                        pOutput.accept(ModBlocks.QUARTZ_GLASS.get());
                        pOutput.accept(ModBlocks.QUARTZ_SAND.get());
                        pOutput.accept(ModItems.QUARTZ_POWDER.get());
                        pOutput.accept(ModItems.TINY_PILE_OF_LITHIUM_POWDER.get());
                        pOutput.accept(ModItems.RUBBER_BAR.get());
                        pOutput.accept(ModBlocks.FIREBRICKS.get());
                        pOutput.accept(ModBlocks.REINFORCED_LAMINATE.get());
                        pOutput.accept(ModBlocks.LARGE_VINYL_TILE.get());
                        pOutput.accept(ModBlocks.SMALL_VINYL_TILES.get());
                        pOutput.accept(ModBlocks.REINFORCED_LAMP.get());
                        pOutput.accept(ModBlocks.REINFORCED_SANDSTONE.get());
                        pOutput.accept(ModBlocks.STEEL_GRATE.get());
                        pOutput.accept(ModBlocks.STEEL_SCAFFOLD.get());
                        pOutput.accept(ModBlocks.REINFORCED_STEEL_SCAFFOLD.get());
                        pOutput.accept(ModBlocks.CRUSHED_OBSIDIAN.get());
                        pOutput.accept(ModBlocks.BROKEN_CONCRETE_BRICKS.get());
                        pOutput.accept(ModBlocks.CRACKED_CONCRETE_BRICKS.get());
                        pOutput.accept(ModBlocks.DENSE_STONE.get());
                        pOutput.accept(ModBlocks.CONCRETE_TILE.get());
                        pOutput.accept(ModBlocks.ASBESTOS_CONCRETE.get());
                        pOutput.accept(ModBlocks.REBAR_REINFORCED_CONCRETE_PILLAR.get());
                        pOutput.accept(ModBlocks.MOSSY_CONCRETE_BRICKS.get());
                        pOutput.accept(ModBlocks.CMB_STEEL_TILE.get());
                        pOutput.accept(ModBlocks.DUCRETE.get());
                        pOutput.accept(ModBlocks.DUCRETE_TILE.get());
                        pOutput.accept(ModBlocks.OBSIDIAN_BRICKS.get());
                        pOutput.accept(ModBlocks.REINFORCED_STONE.get());
                        pOutput.accept(ModBlocks.UBER_CONCRETE.get());
                        pOutput.accept(ModBlocks.COMPOUND_MESH.get());
                        pOutput.accept(ModBlocks.DUCRETE_BRICKS.get());
                        pOutput.accept(ModBlocks.REINFORCED_DUCRETE.get());
                        pOutput.accept(ModBlocks.REINFORCED_CMB_BRICKS.get());
                        pOutput.accept(ModBlocks.CONVEYOR_BELT.get());
                        pOutput.accept(ModItems.SCREWDRIVER.get());
                        pOutput.accept(ModBlocks.INDUSTRIAL_COOLING_TOWER.get());
                        pOutput.accept(ModBlocks.CENTRIFUGE.get());
                        pOutput.accept(ModBlocks.CONVEYOR_INSERTER.get());
                        pOutput.accept(ModBlocks.CONVEYOR_EJECTOR.get());
                        pOutput.accept(ModBlocks.ORE_ACIDIZER.get());
                        pOutput.accept(ModBlocks.CONVEYOR_SPLITTER.get());
                        pOutput.accept(ModItems.STEEL_SPHERE.get());
                        pOutput.accept(ModItems.FLAT_STEEL_CASING.get());
                        pOutput.accept(ModItems.STEEL_PEDESTAL.get());
                        pOutput.accept(ModItems.MILITARY_GRADE_CIRCUIT_BOARD.get());
                        pOutput.accept(ModItems.GOLD_WIRE.get());
                        pOutput.accept(ModItems.SHORT_RANGE_TARGET_DESIGNATOR.get());
                        pOutput.accept(ModItems.LONG_RANGE_TARGET_DESIGNATOR.get());
                        pOutput.accept(ModItems.CABLE_DRUM.get());
                        pOutput.accept(ModBlocks.BOILER.get());
                        pOutput.accept(ModItems.INFINITE_BATTERY.get());
                        pOutput.accept(ModItems.SIM_CARD.get());
                        pOutput.accept(ModItems.SIM_CARD_ACTIVATOR.get());
                        pOutput.accept(ModBlocks.SIM_CARD_SIGNAL_BROADCASTING_DEVICE.get());
                        pOutput.accept(ModBlocks.SHORT_RANGE_SIM_CARD_DATA_SENDER.get());
                        pOutput.accept(ModBlocks.COPPER_CABLE.get());
                        pOutput.accept(ModBlocks.TANK.get());
                        pOutput.accept(ModBlocks.STEAM_CONDENSER.get());
                        pOutput.accept(ModBlocks.SMOKESTACK.get());
                        pOutput.accept(ModBlocks.HYDRAULIC_FRACKING_TOWER.get());
                        pOutput.accept(ModBlocks.HYDROTREATER.get());
                        pOutput.accept(ModBlocks.COMBINATION_OVEN.get());
                        pOutput.accept(ModBlocks.CATALYTIC_CRACKING_TOWER.get());
                        pOutput.accept(ModBlocks.FEL.get());
                        pOutput.accept(ModBlocks.SILEX.get());
                        pOutput.accept(ModBlocks.BREEDING_REACTOR.get());
                        pOutput.accept(ModBlocks.ELECTROLYSIS_MACHINE.get());
                        pOutput.accept(ModBlocks.CONVEYOR_CHAIN_LIFT.get());
                        pOutput.accept(ModBlocks.OIL_REFINERY.get());
                        pOutput.accept(ModBlocks.OIL_DERRICK.get());
                        pOutput.accept(ModBlocks.NUCLEAR_SIREN.get());
                        pOutput.accept(ModBlocks.ZIRNOX_NUCLEAR_REACTOR.get());
                        pOutput.accept(ModBlocks.UNIVERSAL_FLUID_DUCT.get());
                        pOutput.accept(ModItems.INFINITE_WATER_TANK.get());
                        pOutput.accept(ModItems.CORIUM_BUCKET.get());
                        pOutput.accept(ModItems.PWR_CONTROLLER_LINKING_DEVICE.get());
                        pOutput.accept(ModItems.FAT_MAN_DROP_LOCATION_DESIGNATOR.get());
                        pOutput.accept(ModItems.INFINITE_FLUID_BARREL.get());
                        pOutput.accept(ModBlocks.CHEMICAL_PLANT.get());
                        pOutput.accept(ModBlocks.MATTER_GIGAFACTORY.get());
                        pOutput.accept(ModBlocks.BATTERY_SOCKET.get());
                        pOutput.accept(ModBlocks.REDSTONE_BATTERY.get());
                        pOutput.accept(ModBlocks.INDUSTRIAL_STEAM_TURBINE.get());
                        pOutput.accept(ModBlocks.FIREBOX.get());
                        pOutput.accept(ModBlocks.HEATING_OVEN.get());
                        pOutput.accept(ModBlocks.FRACTIONATING_TOWER.get());
                        pOutput.accept(ModBlocks.FRACTIONATING_TOWER_SEPARATOR.get());
                        pOutput.accept(ModBlocks.DRAINAGE_PIPE.get());
                        pOutput.accept(ModBlocks.CHEMICAL_FACTORY.get());
                        pOutput.accept(ModBlocks.ASSEMBLY_MACHINE.get());
                        pOutput.accept(ModBlocks.SILO_LAUNCH_PAD.get());
                        pOutput.accept(ModItems.HIGH_EXPLOSIVE_MISSILE.get());
                        pOutput.accept(ModItems.INCENDIARY_MISSILE.get());
                        pOutput.accept(ModItems.ANTI_BALLISTIC_MISSILE.get());
                        pOutput.accept(ModItems.NUCLEAR_MISSILE.get());
                        pOutput.accept(ModBlocks.WOOD_BURNING_GENERATOR.get());
                        pOutput.accept(ModBlocks.INDUSTRIAL_BOILER.get());
                        pOutput.accept(ModBlocks.INDUSTRIAL_COMBUSTION_GENERATOR.get());
                        pOutput.accept(ModBlocks.CRUCIBLE.get());
                        pOutput.accept(ModBlocks.SOLDERING_STATION.get());
                        pOutput.accept(ModBlocks.LARGE_ELECTRICITY_PYLON.get());
                        pOutput.accept(ModBlocks.DEUTERIUM_EXTRACTION_TOWER.get());
                        pOutput.accept(ModBlocks.ARC_WELDER.get());
                        pOutput.accept(ModBlocks.FLARE_STACK.get());
                        pOutput.accept(ModBlocks.COMPRESSOR.get());
                        pOutput.accept(ModBlocks.LARGE_MINING_DRILL.get());
                        pOutput.accept(ModBlocks.EXPOSURE_CHAMBER.get());
                        pOutput.accept(ModBlocks.ELECTRIC_ARC_FURNACE.get());
                        pOutput.accept(ModBlocks.VACUUM_REFINERY.get());
                        pOutput.accept(ModBlocks.PUMPJACK.get());
                        pOutput.accept(ModBlocks.CATALYTIC_REFORMER.get());
                        pOutput.accept(ModBlocks.SHALLOW_FOUNDRY_BASIN.get());
                        pOutput.accept(ModBlocks.TURBOFAN.get());
                        pOutput.accept(ModBlocks.STEEL_DECO_BLOCK.get());
                        pOutput.accept(ModBlocks.STEEL_POLE.get());
                        pOutput.accept(ModBlocks.ANTENNA_TOP.get());
                        pOutput.accept(ModBlocks.TAPE_RECORDER.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> hbm_ntm_RESOURCES = CREATIVE_MODE_TABS.register("hbm_ntm_resources",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.MINECRAFT_GRADE_COPPER.get()))
                    .title(Component.translatable("creativetab.hbm_ntm_resources"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.THORIUM_INGOT.get());
                        output.accept(ModItems.STEEL.get());
                        output.accept(ModItems.INDUSTRIAL_GRADE_COPPER.get());
                        output.accept(ModItems.MAGNETIZED_TUNGSTEN_INGOT.get());
                        output.accept(ModItems.LEAD_INGOT.get());
                        output.accept(ModItems.CMB_STEEL_INGOT.get());
                        output.accept(ModItems.HIGH_SPEED_STEEL_INGOT.get());
                        output.accept(ModItems.TECHNETIUM_99_INGOT.get());
                        output.accept(ModItems.TECHNETIUM_STEEL_INGOT.get());
                        output.accept(ModItems.POLYMER_BAR.get());
                        output.accept(ModItems.BAKELITE_BAR.get());
                        output.accept(ModItems.DESH_INGOT.get());
                        output.accept(ModItems.STEEL_SHELL.get());
                        output.accept(ModItems.STEEL_PIPE.get());
                        output.accept(ModItems.IRON_POWDER.get());
                        output.accept(ModItems.CLAY_CATALYST.get());
                        output.accept(ModItems.SATURNITE_INGOT.get());
                        output.accept(ModItems.STARMETAL_INGOT.get());
                        output.accept(ModItems.EUPHEMIUM_INGOT.get());
                        output.accept(ModItems.DINEUTRONIUM_INGOT.get());
                        output.accept(ModItems.BISMUTH_INGOT.get());
                        output.accept(ModItems.AIRSTRIKE_DESIGNATOR.get());
                        output.accept(ModItems.ZIRCONIUM_CUBE.get());
                        output.accept(ModItems.URANIUM_233_INGOT.get());
                        output.accept(ModItems.URANIUM_235_INGOT.get());
                        output.accept(ModItems.DESHREADY_BLEND.get());
                        output.accept(ModItems.PLUTONIUM_INGOT.get());
                        output.accept(ModItems.REACTOR_GRADE_PLUTONIUM_INGOT.get());
                        output.accept(ModItems.AMERICIUM_242_INGOT.get());
                        output.accept(ModItems.REACTOR_GRADE_AMERICIUM_INGOT.get());
                        output.accept(ModItems.INGOT_OF_URANIUM_FUEL.get());
                        output.accept(ModItems.INGOT_OF_PLUTONIUM_FUEL.get());
                        output.accept(ModItems.NEPTUNIUM_FUEL_INGOT.get());
                        output.accept(ModItems.INGOT_OF_MOX_FUEL.get());
                        output.accept(ModItems.INGOT_OF_AMERICIUM_FUEL.get());
                        output.accept(ModItems.INGOT_OF_THORIUM_FUEL.get());
                        output.accept(ModItems.SCHRABIDIUM_INGOT.get());
                        output.accept(ModItems.ALUMINUM_INGOT.get());
                        output.accept(ModItems.BORON_INGOT.get());
                        output.accept(ModItems.SMORE_INGOT.get());
                        output.accept(ModItems.ALUMINUM_PLATE.get());
                        output.accept(ModItems.NIOBIUM_INGOT.get());
                        output.accept(ModItems.NEODYMIUM_INGOT.get());
                        output.accept(ModItems.BROMINE_INGOT.get());
                        output.accept(ModItems.REDSTONE_CRYSTALS.get());
                        output.accept(ModItems.COAL_POWDER.get());
                        output.accept(ModItems.RARE_EARTH_CRYSTALS.get());
                        output.accept(ModItems.DESH_BLEND.get());
                        output.accept(ModItems.COAL_TAR.get());
                        output.accept(ModItems.FIREBRICK.get());
                        output.accept(ModItems.FIRECLAY.get());
                        output.accept(ModItems.CARBON_WIRE.get());
                        output.accept(ModItems.SCHRABIDIUM_WIRE.get());
                        output.accept(ModItems.COPPER_WIRE.get());
                        output.accept(ModItems.TUNGSTEN_WIRE.get());
                        output.accept(ModItems.ALUMINUM_WIRE.get());
                        output.accept(ModItems.LEAD_WIRE.get());
                        output.accept(ModItems.SILICON_WAFER.get());
                        output.accept(ModItems.SILICON_NUGGET.get());
                        output.accept(ModItems.SILICON_BOULE.get());
                        output.accept(ModItems.ZIRCONIUM_WIRE.get());
                        output.accept(ModItems.STEEL_WIRE.get());
                        output.accept(ModItems.ADVANCED_ALLOY_WIRE.get());
                        output.accept(ModItems.STEEL_PICKAXE.get());
                        output.accept(ModItems.TITANIUM_PICKAXE.get());
                        output.accept(ModItems.COBALT_PICKAXE.get());
                        output.accept(ModItems.ADVANCED_ALLOY_PICKAXE.get());
                        output.accept(ModItems.WORKER_ALLOY_PICKAXE.get());
                        output.accept(ModItems.CMB_STEEL_PICKAXE.get());
                        output.accept(ModItems.SCHRABIDIUM_PICKAXE.get());
                        output.accept(ModItems.STARMETAL_PICKAXE.get());
                        output.accept(ModBlocks.MALACHITE.get());
                        output.accept(ModBlocks.HEMATITE.get());
                        output.accept(ModItems.SULFUR.get());
                        output.accept(ModItems.MICROCHIP.get());
                        output.accept(ModItems.PRINTED_SILICON_WAFER.get());
                        output.accept(ModItems.CIRCUIT_STAMP.get());
                        output.accept(ModBlocks.TEST.get());
                        for (RegistryObject<Item> item : ModItems.BEDROCK_ORE_NAMES.values()) {
                            output.accept(item.get());
                        }
                    }).build());

    public static final RegistryObject<CreativeModeTab> NTM_TEMPLATES = CREATIVE_MODE_TABS.register("hbm_ntm_templates",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.ASSEMBLY_TEMPLATES.get("iron_plate").get()))
                    .title(Component.translatable("creativetab.hbm_ntm_templates"))
                    .displayItems((parameters, output) -> {
                        for (RegistryObject<Item> item : ModItems.FLUID_IDENTIFIERS.values()) {
                            output.accept(item.get());
                        }
                        for (RegistryObject<Item> item : ModItems.ASSEMBLY_TEMPLATES.values()) {
                            output.accept(item.get());
                        }

                        for (String fluidName : FluidColorRegistry.getRegisteredFluids()) {
                            ItemStack ductStack = new ItemStack(ModBlocks.UNIVERSAL_FLUID_DUCT.get());
                            int color = FluidColorRegistry.getColor(fluidName);

                            var tag = ductStack.getOrCreateTagElement("BlockEntityTag");
                            tag.putString("filterFluid", fluidName);
                            tag.putInt("filterColor", color);

                            ductStack.getOrCreateTag().putString("FluidName", fluidName);

                            String prettyName = java.util.Arrays.stream(fluidName.split("_"))
                                    .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                                    .collect(java.util.stream.Collectors.joining(" "));

                            ductStack.setHoverName(Component.literal(prettyName + " Universal Fluid Duct")
                                    .withStyle(net.minecraft.ChatFormatting.WHITE));

                            output.accept(ductStack);
                        }

                        for (RegistryObject<Item> item : ModItems.CHEMISTRY_TEMPLATES.values()) {
                            output.accept(item.get());
                        }
                        for (RegistryObject<Item> item : ModItems.CRUCIBLE_TEMPLATES.values()) {
                            output.accept(item.get());
                        }
                    }).build());

    public static final RegistryObject<CreativeModeTab> NTM_MACHINE_ITEMS_AND_FUEL = CREATIVE_MODE_TABS.register("hbm_ntm_machine_items_and_fuel",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.ZIRCONIUM_CUBE.get()))
                    .title(Component.translatable("creativetab.hbm_ntm_machine_items_and_fuel"))
                    .displayItems((parameters, output) -> {
                        for (RegistryObject<Item> item : ModItems.RTG_PELLETS.values()) {
                            output.accept(item.get());
                        }
                        output.accept(ModItems.COMBUSTION_ENGINE_PISTON.get());
                        output.accept(ModItems.STEEL_PISTON_SET.get());
                        output.accept(ModItems.HIGH_SPEED_STEEL_PISTON_SET.get());
                        output.accept(ModItems.DESH_PISTON_SET.get());
                        output.accept(ModItems.STARMETAL_PISTON_SET.get());
                        output.accept(ModItems.STEEL_DRILLBIT.get());
                        output.accept(ModItems.HIGH_SPEED_STEEL_DRILLBIT.get());
                        output.accept(ModItems.DESH_DRILLBIT.get());
                        output.accept(ModItems.TECHNETIUM_STEEL_DRILLBIT.get());
                        output.accept(ModItems.FERROURANIUM_DRILLBIT.get());
                        output.accept(ModItems.EMPTY_CELL.get());
                        output.accept(ModItems.URANIUM_HEXAFLUORIDE_CELL.get());
                        output.accept(ModItems.PLUTONIUM_HEXAFLUORIDE_CELL.get());
                        output.accept(ModItems.DEUTERIUM_CELL.get());
                        output.accept(ModItems.TRITIUM_CELL.get());
                        output.accept(ModItems.SCHRABIDIUM_TRISULFIDE_CELL.get());
                        output.accept(ModItems.ANTIMATTER_CELL.get());
                        output.accept(ModItems.ANTISCHRABIDIUM_CELL.get());
                        output.accept(ModItems.GASEOUS_BALEFIRE_CELL.get());
                        for (RegistryObject<Item> item : ModItems.CANISTERS.values()) {
                            output.accept(item.get());
                        }
                        output.accept(ModItems.VACUUM_TUBE.get());
                        output.accept(ModItems.PRINTED_CIRCUIT_BOARD.get());
                        output.accept(ModItems.CAPACITOR.get());
                        for (RegistryObject<Item> item : ModItems.FLUID_TANK_NAMES.values()) {
                            output.accept(item.get());
                        }
                        for (RegistryObject<Item> item : ModItems.HAZARDOUS_MATERIAL_TANKS.values()) {
                            output.accept(item.get());
                        }
                        for (RegistryObject<Item> item : ModItems.FLUID_BARRELS.values()) {
                            output.accept(item.get());
                        }
                        for (RegistryObject<Item> item : ModItems.PACKAGED_FLUIDS.values()) {
                            output.accept(item.get());
                        }
                        output.accept(ModItems.BLANK_FOUNDRY_MOLD.get());
                        output.accept(ModItems.NUGGET_FOUNDRY_MOLD.get());
                        output.accept(ModItems.BILLET_FOUNDRY_MOLD.get());
                        output.accept(ModItems.INGOT_FOUNDRY_MOLD.get());
                        output.accept(ModItems.PLATE_FOUNDRY_MOLD.get());
                        output.accept(ModItems.WIRE_FOUNDRY_MOLD.get());
                        output.accept(ModItems.CAST_PLATE_FOUNDRY_MOLD.get());
                        output.accept(ModItems.DENSE_WIRE_FOUNDRY_MOLD.get());
                        output.accept(ModItems.BLADE_FOUNDRY_MOLD.get());
                        output.accept(ModItems.SHREDDER_BLADE_FOUNDRY_MOLD.get());
                        output.accept(ModItems.SHELL_FOUNDRY_MOLD.get());
                        output.accept(ModItems.PIPE_FOUNDRY_MOLD.get());
                        output.accept(ModItems.DYATLOV_INSTANT_MELTDOWN_APPLICATOR.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
