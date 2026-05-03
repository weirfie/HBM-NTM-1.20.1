package net.StrayBead.hbm_ntm.item;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.event.FluidColorRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HBMNTM.MOD_ID);

    public static final RegistryObject<CreativeModeTab> hbm_ntm_RESOURCES = CREATIVE_MODE_TABS.register("hbm_ntm_resources",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.URANIUM_INGOT.get()))
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
                        output.accept(ModItems.URANIUM_INGOT.get());
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
                        output.accept(ModItems.RADIUM_NUGGET.get());
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
                        output.accept(ModItems.SULFUR.get());
                        output.accept(ModItems.MICROCHIP.get());
                        output.accept(ModItems.PRINTED_SILICON_WAFER.get());
                        output.accept(ModItems.CIRCUIT_STAMP.get());
                        output.accept(ModBlocks.TEST.get());
                        for (RegistryObject<Item> item : ModItems.BILLETS.values()) {
                            output.accept(item.get());
                        }
                        output.accept(ModItems.COAL_COKE.get());
                        output.accept(ModItems.LIGNITE_COKE.get());
                        output.accept(ModItems.PETROLEUM_COKE.get());
                        output.accept(ModItems.LIGNITE.get());
                        output.accept(ModItems.INFERNAL_COAL.get());
                        for (RegistryObject<Item> item : ModItems.POWDERS.values()) {
                            output.accept(item.get());
                        }
                        output.accept(ModItems.CEMENT.get());
                        for (RegistryObject<Item> item : ModItems.BEDROCK_ORE_NAMES.values()) {
                            output.accept(item.get());
                        }
                        output.accept(ModItems.CRYSTALLINE_IRON_FRAGMENT.get());
                        output.accept(ModItems.CRYSTALLINE_COPPER_FRAGMENT.get());
                        output.accept(ModItems.CRYSTALLINE_LITHIUM_FRAGMENT.get());
                        output.accept(ModItems.CRYSTALLINE_SILICON_FRAGMENT.get());
                        output.accept(ModItems.CRYSTALLINE_LEAD_FRAGMENT.get());
                        output.accept(ModItems.CRYSTALLINE_TITANIUM_FRAGMENT.get());
                        output.accept(ModItems.CRYSTALLINE_ALUMINUM_FRAGMENT.get());
                        output.accept(ModItems.CRYSTALLINE_SULFUR_FRAGMENT.get());
                        output.accept(ModItems.CRYSTALLINE_CALCIUM_FRAGMENT.get());
                        output.accept(ModItems.CRYSTALLINE_BISMUTH_FRAGMENT.get());
                        output.accept(ModItems.CRYSTALLINE_RADIUM_FRAGMENT.get());
                        output.accept(ModItems.CRYSTALLINE_TECHNETIUM_FRAGMENT.get());
                        output.accept(ModItems.CRYSTALLINE_POLONIUM_FRAGMENT.get());
                        output.accept(ModItems.CRYSTALLINE_URANIUM_FRAGMENT.get());
                        output.accept(ModItems.RAW_BEDROCK_ORE.get());
                        for (RegistryObject<Item> item : ModItems.NUCLEAR_COMPONENTS.values()) {
                            if (item.getId().getPath().contains("_bedrock_ore")) {
                                output.accept(item.get());
                            }
                        }
                        for (RegistryObject<Item> item : ModItems.BEDROCK_ORE_FRAGMENTS.values()) {
                            output.accept(item.get());
                        }
                        for (RegistryObject<Item> item : ModItems.CRYSTALS.values()) {
                            output.accept(item.get());
                        }
                        output.accept(ModItems.CONTROL_UNIT.get());
                        output.accept(ModItems.ADVANCED_CONTROL_UNIT.get());
                        output.accept(ModItems.DYSFUNCTIONAL_NUCLEAR_REACTOR.get());
                        output.accept(ModItems.CAPACITOR.get());
                        output.accept(ModItems.TANTALIUM_CAPACITOR.get());
                        output.accept(ModItems.CONTROL_UNIT_CASING.get());
                        output.accept(ModItems.LEAD_QUAD_ROD.get());
                        output.accept(ModItems.URANIUM_QUAD_ROD.get());
                        output.accept(ModItems.NEPTUNIUM_237_QUAD_ROD.get());
                        output.accept(ModItems.CATHODE_RAY_TUBE.get());
                        output.accept(ModItems.ATOMIC_CLOCK.get());
                        output.accept(ModItems.VERSATILE_INTEGRATED_CIRCUIT.get());
                        output.accept(ModItems.SOLID_STATE_QUANTUM_PROCESSOR.get());
                        output.accept(ModItems.MAGNETRON.get());
                        output.accept(ModItems.SMALL_STEEL_GRID_FINS.get());
                        for (RegistryObject<Item> item : ModItems.FOUNDRY_SCRAPS.values()) {
                            output.accept(item.get());
                        }
                    }).build());

    public static final RegistryObject<CreativeModeTab> NTM_MACHINE_ITEMS_AND_FUEL = CREATIVE_MODE_TABS.register("hbm_ntm_machine_items_and_fuel",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.RTG_PELLETS.get("plutonium_238").get()))
                    .withTabsBefore(hbm_ntm_RESOURCES.getKey())
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
                        output.accept(ModItems.EMPTY_PARTICLE_CAPSULE.get());
                        output.accept(ModItems.HYDROGEN_ION_CAPSULE.get());
                        output.accept(ModItems.SPARKTICLE_CAPSULE.get());
                        output.accept(ModItems.DARK_MATTER_CAPSULE.get());
                        output.accept(ModItems.STRANGE_QUARK_CAPSULE.get());
                        output.accept(ModItems.LEAD_ION_CAPSULE.get());
                        output.accept(ModItems.MUON_CAPSULE.get());
                        output.accept(ModItems.THE_DIGAMMA_PARTICLE.get());
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
                        output.accept(ModItems.PIPETTE.get());
                        output.accept(ModItems.BORON_PIPETTE.get());
                        output.accept(ModItems.LABORATORY_GRADE_PIPETTE.get());
                        output.accept(ModItems.SIPHON.get());
                        output.accept(ModItems.BATTERY.get());
                        output.accept(ModItems.REDSTONE_POWER_CELL.get());
                        output.accept(ModItems.SIXFOLD_REDSTONE_POWER_CELL.get());
                        output.accept(ModItems.FOLD_REDSTONE_POWER_CELL.get());
                        output.accept(ModItems.ADVANCED_BATTERY.get());
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
                        output.accept(ModItems.SPEED_UPGRADE.get());
                        output.accept(ModItems.EFFECTIVENESS_UPGRADE.get());
                        output.accept(ModItems.POWER_SAVING_UPGRADE.get());
                        output.accept(ModItems.FORTUNE_UPGRADE.get());
                        output.accept(ModItems.AFTERBURNER_UPGRADE.get());
                        output.accept(ModItems.OVERDRIVE_UPGRADE.get());
                        output.accept(ModItems.EMITTER_RADIUS_UPGRADE.get());
                        output.accept(ModItems.EMITTER_HEALTH_UPGRADE.get());
                        output.accept(ModItems.SMELTER_UPGRADE.get());
                        output.accept(ModItems.SHREDDER_UPGRADE.get());
                        output.accept(ModItems.CENTRIFUGE_UPGRADE.get());
                        output.accept(ModItems.CRYSTALLIZER_UPGRADE.get());
                        output.accept(ModItems.SCRAP_DESTROYER_UPGRADE.get());
                        output.accept(ModItems.SCREAMING_SCIENTIST_UPGRADE.get());
                        output.accept(ModItems.GAS_CENTRIFUGE_OVERCLOCKING_UPGRADE.get());
                        output.accept(ModItems.RADIATION_EMITTER_UPGRADE.get());
                        output.accept(ModItems.STACK_EJECTION_UPGRADE.get());
                        output.accept(ModItems.EJECTION_SPEED_UPGRADE.get());
                        output.accept(ModItems.GRAPHITE_ELECTRODE.get());
                        output.accept(ModItems.LANTHANIUM_ELECTRODE.get());
                        output.accept(ModItems.DESH_ELECTRODE.get());
                        output.accept(ModItems.SATURNITE_ELECTRODE.get());
                        output.accept(ModItems.MOLTEN_GRAPHITE_ELECTRODE.get());
                        output.accept(ModItems.MOLTEN_LANTHANIUM_ELECTRODE.get());
                        output.accept(ModItems.MOLTEN_DESH_ELECTRODE.get());
                        output.accept(ModItems.MOLTEN_SATURNATE_ELECTRODE.get());
                        output.accept(ModItems.DESH_LASER_CRYSTAL.get());
                        output.accept(ModItems.BISMUTH_LASER_CRYSTAL.get());
                        output.accept(ModItems.CMB_SCHRABIDATE_ANTIMATTER_LASER_CRYSTAL.get());
                        output.accept(ModItems.SPARK_LASER_CRYSTAL.get());
                        output.accept(ModItems.DIGAMMA_LASER_CRYSTAL.get());
                        output.accept(ModItems.DYATLOV_INSTANT_MELTDOWN_APPLICATOR.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> NTM_TEMPLATES = CREATIVE_MODE_TABS.register("hbm_ntm_templates",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.ASSEMBLY_TEMPLATES.get("iron_plate").get()))
                    .withTabsBefore(NTM_MACHINE_ITEMS_AND_FUEL.getKey())
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

    public static final RegistryObject<CreativeModeTab> hbm_ntm_ENGINEERING = CREATIVE_MODE_TABS.register("hbm_ntm",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.URANIUM_ORE.get()))
                    .title(Component.translatable("creativetab.hbm_ntm"))
                    .withTabsBefore(NTM_TEMPLATES.getKey())
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
                        pOutput.accept(ModBlocks.METEOR_IRON_ORE.get());
                        pOutput.accept(ModBlocks.METEOR_COPPER_ORE.get());
                        pOutput.accept(ModBlocks.METEOR_ALUMINUM_ORE.get());
                        pOutput.accept(ModBlocks.METEOR_RARE_EARTH_ORE.get());
                        pOutput.accept(ModBlocks.HOT_METEORITE_COBBLESTONE.get());
                        pOutput.accept(ModBlocks.METEOR_COBALT_ORE.get());
                        pOutput.accept(ModBlocks.METEORITE_COBBLESTONE.get());
                        pOutput.accept(ModBlocks.BROKEN_METEORITE_BLOCK.get());
                        pOutput.accept(ModBlocks.STEEL_BLOCK.get());
                        pOutput.accept(ModBlocks.RED_COPPER_BLOCK.get());
                        pOutput.accept(ModBlocks.POWER_SWITCH.get());
                        pOutput.accept(ModItems.INDUSTRIAL_GRADE_COPPER.get());
                        pOutput.accept(ModItems.ADVANCED_ALLOY_INGOT.get());
                        pOutput.accept(ModItems.MINECRAFT_GRADE_COPPER.get());
                        pOutput.accept(ModBlocks.TUNGSTEN_ORE_BLOCK.get());
                        pOutput.accept(ModBlocks.THORIUM_ORE.get());
                        pOutput.accept(ModBlocks.LEAD_ORE.get());
                        pOutput.accept(ModBlocks.ASBESTOS_ORE.get());
                        pOutput.accept(ModBlocks.AUSTRALIAN_ORE.get());
                        pOutput.accept(ModBlocks.COBALT_ORE.get());
                        pOutput.accept(ModBlocks.CINNABAR_ORE.get());
                        pOutput.accept(ModBlocks.COLTAN_ORE.get());
                        pOutput.accept(ModBlocks.IRON_ORE_CLUSTER.get());
                        pOutput.accept(ModBlocks.TITANIUM_ORE_CLUSTER.get());
                        pOutput.accept(ModBlocks.ALUMINUM_ORE_CLUSTER.get());
                        pOutput.accept(ModBlocks.COPPER_ORE_CLUSTER.get());
                        pOutput.accept(ModBlocks.OILY_COAL_ORE.get());
                        pOutput.accept(ModBlocks.BURNING_OILY_COAL_ORE.get());
                        pOutput.accept(ModBlocks.SCHIST_IRON_ORE.get());
                        pOutput.accept(ModBlocks.SCHIST_GOLD_ORE.get());
                        pOutput.accept(ModBlocks.SCHIST_URANIUM_ORE.get());
                        pOutput.accept(ModBlocks.SCORCHED_SCHIST_URANIUM_ORE.get());
                        pOutput.accept(ModBlocks.SCHIST_COPPER_ORE.get());
                        pOutput.accept(ModBlocks.SCHIST_ASBESTOS_ORE.get());
                        pOutput.accept(ModBlocks.SCHIST_LITHIUM_ORE.get());
                        pOutput.accept(ModBlocks.SCHIST_SCHRABIDIUM_ORE.get());
                        pOutput.accept(ModBlocks.SCHIST_RARE_EARTH_ORE.get());
                        pOutput.accept(ModBlocks.DEPTH_CINNABAR_ORE.get());
                        pOutput.accept(ModBlocks.DEPTH_ZIRCONIUM_ORE.get());
                        pOutput.accept(ModBlocks.DEPTH_BORAX_ORE.get());
                        pOutput.accept(ModBlocks.DEPTH_IRON_ORE_CLUSTER.get());
                        pOutput.accept(ModBlocks.DEPTH_TITANIUM_ORE_CLUSTER.get());
                        pOutput.accept(ModBlocks.DEPTH_TUNGSTEN_ORE_CLUSTER.get());
                        pOutput.accept(ModBlocks.ALEXANDRITE_ORE.get());
                        pOutput.accept(ModBlocks.NETHER_DEPTH_NEODYMIUM_ORE.get());
                        pOutput.accept(ModBlocks.SULFUROUS_STONE.get());
                        pOutput.accept(ModBlocks.CHRYSOTILE.get());
                        pOutput.accept(ModBlocks.MALACHITE.get());
                        pOutput.accept(ModBlocks.HEMATITE.get());
                        pOutput.accept(ModBlocks.LIMESTONE.get());
                        pOutput.accept(ModBlocks.BAUXITE.get());
                        pOutput.accept(ModBlocks.GAS_SHALE.get());
                        pOutput.accept(ModBlocks.BLOCK_OF_SCRAP.get());
                        pOutput.accept(ModBlocks.FOAM.get());
                        pOutput.accept(ModBlocks.BLOCK_OF_COAL_COKE.get());
                        pOutput.accept(ModBlocks.BLOCK_OF_LIGNITE_COKE.get());
                        pOutput.accept(ModBlocks.BLOCK_OF_PETROLEUM_COKE.get());
                        pOutput.accept(ModBlocks.BLOCK_OF_BORON.get());
                        pOutput.accept(ModBlocks.ROLL_OF_INSULATION.get());
                        pOutput.accept(ModBlocks.BLOCK_OF_ASBESTOS.get());
                        pOutput.accept(ModBlocks.BLOCK_OF_TRINITITE.get());
                        pOutput.accept(ModBlocks.BLOCK_OF_NUCLEAR_WASTE.get());
                        pOutput.accept(ModBlocks.PAINTED_BLOCK_OF_NUCLEAR_WASTE.get());
                        pOutput.accept(ModBlocks.BLOCK_OF_SCHRABIDIUM.get());
                        pOutput.accept(ModBlocks.BLOCK_OF_RADIUM_226.get());
                        pOutput.accept(ModBlocks.BLOCK_OF_ACTINIUM.get());
                        pOutput.accept(ModBlocks.ASBESTOS_ROOF.get());
                        pOutput.accept(ModBlocks.DECO_LIGHT_EMITTER.get());
                        pOutput.accept(ModBlocks.DECO_PARTICLE_EMITTER.get());
                        pOutput.accept(ModBlocks.RBMK_DECO_BLOCK.get());
                        pOutput.accept(ModBlocks.SMOOTH_RBMK_DECO_BLOCK.get());
                        pOutput.accept(ModBlocks.DIAMOND_GRAVEL.get());
                        pOutput.accept(ModBlocks.BERYLLIUM_ORE.get());
                        pOutput.accept(ModItems.RAW_BERYLLIUM.get());
                        pOutput.accept(ModItems.BERYLLIUM_INGOT.get());
                        pOutput.accept(ModItems.FLUORITE.get());
                        pOutput.accept(ModItems.LEAD_DUST.get());
                        pOutput.accept(ModItems.ANALOG_CIRCUIT_BOARD.get());
                        pOutput.accept(ModItems.INSULATOR.get());
                        pOutput.accept(ModItems.WELDED_STEEL_PLATE.get());
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
                        pOutput.accept(ModItems.TITANIUM_PLATE.get());
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
                        pOutput.accept(ModItems.ENERGY_INJECTOR.get());
                        pOutput.accept(ModItems.RADIATION_MEASURER.get());
                        pOutput.accept(ModItems.FLUID_MEASURER.get());
                        pOutput.accept(ModItems.ELECTRIC_MOTOR.get());
                        pOutput.accept(ModBlocks.ULTRA_DENSE_STEAM_PIPE.get());
                        pOutput.accept(ModBlocks.SUPER_DENSE_STEAM_PIPE.get());
                        pOutput.accept(ModBlocks.DENSE_STEAM_PIPE.get());
                        pOutput.accept(ModBlocks.STEAM_PIPE.get());
                        pOutput.accept(ModBlocks.WATER_DUCT.get());
                        pOutput.accept(ModBlocks.CONCRETE_BRICKS.get());
                        pOutput.accept(ModItems.GEIGER_COUNTER.get());
                        pOutput.accept(ModItems.INTEGRATED_CIRCUIT_BOARD.get());
                        pOutput.accept(ModBlocks.DEAD_GRASS.get());
                        pOutput.accept(ModBlocks.REINFORCED_GLASS.get());
                        pOutput.accept(ModBlocks.WARNING_BLOCK.get());
                        pOutput.accept(ModBlocks.WOOD_BRICKS.get());
                        pOutput.accept(ModBlocks.SOLAR_PANEL.get());
                        pOutput.accept(ModItems.CENTRIFUGED_URANIUM_BEDROCK_ORE.get());
                        pOutput.accept(ModBlocks.FAN.get());
                        pOutput.accept(ModBlocks.THE_GADGET.get());
                        pOutput.accept(ModBlocks.LITTLE_BOY.get());
                        pOutput.accept(ModBlocks.FAT_MAN.get());
                        pOutput.accept(ModBlocks.IVY_MIKE.get());
                        pOutput.accept(ModBlocks.CASTLE_BRAVO.get());
                        pOutput.accept(ModBlocks.TSAR_BOMBA.get());
                        pOutput.accept(ModItems.NUKE_DETONATOR.get());
                        pOutput.accept(ModBlocks.WATER_TANK.get());
                        pOutput.accept(ModBlocks.SPARK_ENERGY_BATTERY.get());
                        pOutput.accept(ModBlocks.PARTICLE_ACCELERATOR_PLATING.get());
                        pOutput.accept(ModBlocks.SCHRABIDIUM_BLOCK.get());
                        pOutput.accept(ModBlocks.DET_CORD.get());
                        pOutput.accept(ModItems.DETONATOR.get());
                        pOutput.accept(ModBlocks.RADIOACTIVE_BARREL.get());
                        pOutput.accept(ModBlocks.MOLDY_DEBRIS.get());
                        pOutput.accept(ModBlocks.DEAD_LEAVES.get());
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
                        pOutput.accept(ModBlocks.RAW_LIGHTSTONE.get());
                        pOutput.accept(ModBlocks.LIGHTSTONE_TILE.get());
                        pOutput.accept(ModBlocks.LIGHTSTONE_BRICKS.get());
                        pOutput.accept(ModBlocks.LIGHTSTONE_CHISELED_BRICKS.get());
                        pOutput.accept(ModBlocks.CHISELED_LIGHTSTONE.get());
                        pOutput.accept(ModBlocks.LIGHTSTONE_TILE_STAIRS.get());
                        pOutput.accept(ModBlocks.LIGHTSTONE_BRICK_STAIRS.get());
                        pOutput.accept(ModBlocks.LIGHTSTONE_TILE_SLAB.get());
                        pOutput.accept(ModBlocks.LIGHTSTONE_BRICK_SLAB.get());
                        pOutput.accept(ModBlocks.CONCRETE.get());
                        pOutput.accept(ModBlocks.CONCRETE_SLAB.get());
                        pOutput.accept(ModBlocks.CONCRETE_BRICK_STAIRS.get());
                        pOutput.accept(ModBlocks.BROKEN_CONCRETE_BRICK_SLAB.get());
                        pOutput.accept(ModBlocks.CONCRETE_BRICK_SLAB.get());
                        pOutput.accept(ModBlocks.CONCRETE_STAIRS.get());
                        pOutput.accept(ModBlocks.CRACKED_CONCRETE_BRICK_STAIRS.get());
                        pOutput.accept(ModBlocks.BARBED_WIRE.get());
                        pOutput.accept(ModBlocks.FLAMING_BARBED_WIRE.get());
                        pOutput.accept(ModBlocks.POISONED_BARBED_WIRE.get());
                        pOutput.accept(ModBlocks.CAUSTIC_BARBED_WIRE.get());
                        pOutput.accept(ModBlocks.WITHERED_BARBED_WIRE.get());
                        pOutput.accept(ModBlocks.RADIOACTIVE_BARBED_WIRE.get());
                        pOutput.accept(ModBlocks.STEEL_PIPE.get());
                        pOutput.accept(ModBlocks.CONVEYOR_BELT.get());
                        pOutput.accept(ModItems.SCREWDRIVER.get());
                        pOutput.accept(ModBlocks.CONVEYOR_SPLITTER.get());
                        pOutput.accept(ModItems.STEEL_SPHERE.get());
                        pOutput.accept(ModItems.FLAT_STEEL_CASING.get());
                        pOutput.accept(ModItems.STEEL_PEDESTAL.get());
                        pOutput.accept(ModItems.MILITARY_GRADE_CIRCUIT_BOARD.get());
                        pOutput.accept(ModItems.GOLD_WIRE.get());
                        pOutput.accept(ModItems.SHORT_RANGE_TARGET_DESIGNATOR.get());
                        pOutput.accept(ModItems.LONG_RANGE_TARGET_DESIGNATOR.get());
                        pOutput.accept(ModItems.CABLE_DRUM.get());
                        pOutput.accept(ModItems.INFINITE_BATTERY.get());
                        pOutput.accept(ModItems.SIM_CARD.get());
                        pOutput.accept(ModItems.SIM_CARD_ACTIVATOR.get());
                        pOutput.accept(ModBlocks.SIM_CARD_SIGNAL_BROADCASTING_DEVICE.get());
                        pOutput.accept(ModBlocks.SHORT_RANGE_SIM_CARD_DATA_SENDER.get());
                        pOutput.accept(ModBlocks.CONVEYOR_CHAIN_LIFT.get());
                        pOutput.accept(ModBlocks.UNIVERSAL_FLUID_DUCT.get());
                        pOutput.accept(ModItems.INFINITE_WATER_TANK.get());
                        pOutput.accept(ModItems.CORIUM_BUCKET.get());
                        pOutput.accept(ModItems.PWR_CONTROLLER_LINKING_DEVICE.get());
                        pOutput.accept(ModItems.FAT_MAN_DROP_LOCATION_DESIGNATOR.get());
                        pOutput.accept(ModItems.INFINITE_FLUID_BARREL.get());
                        pOutput.accept(ModBlocks.CHEMICAL_PLANT.get());
                        pOutput.accept(ModBlocks.REDSTONE_BATTERY.get());
                        pOutput.accept(ModItems.HIGH_EXPLOSIVE_MISSILE.get());
                        pOutput.accept(ModItems.INCENDIARY_MISSILE.get());
                        pOutput.accept(ModItems.ANTI_BALLISTIC_MISSILE.get());
                        pOutput.accept(ModItems.NUCLEAR_MISSILE.get());
                        pOutput.accept(ModBlocks.STEEL_DECO_BLOCK.get());
                        pOutput.accept(ModBlocks.STEEL_POLE.get());
                        pOutput.accept(ModBlocks.ANTENNA_TOP.get());
                        pOutput.accept(ModBlocks.TAPE_RECORDER.get());
                        pOutput.accept(ModBlocks.STEEL_BARREL.get());
                    })
                    .build());

    public static final RegistryObject<CreativeModeTab> NTM_MACHINES = CREATIVE_MODE_TABS.register("ntm_machines",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.PWR_CONTROLLER.get()))
                    .title(Component.translatable("creativetab.ntm_machines"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.NUCLEAR_SIREN.get());
                        output.accept(ModItems.CORRUPTED_BROADCASTER.get());
                        output.accept(ModItems.SUIT_BATTERY.get());
                        output.accept(ModBlocks.CHAINLINK_FENCE.get());
                        output.accept(ModBlocks.CHAINLINK_FENCE_POST.get());
                        output.accept(ModBlocks.ASH.get());
                        output.accept(ModBlocks.BORON_SAND.get());
                        output.accept(ModBlocks.LEAD_SAND.get());
                        output.accept(ModBlocks.URANIUM_SAND.get());
                        output.accept(ModBlocks.POLONIUM_SAND.get());
                        output.accept(ModBlocks.BORON_GLASS.get());
                        output.accept(ModBlocks.LEAD_GLASS.get());
                        output.accept(ModBlocks.URANIUM_GLASS.get());
                        output.accept(ModBlocks.TRINITY_GLASS.get());
                        output.accept(ModBlocks.POLONIUM_GLASS.get());
                        output.accept(ModBlocks.ASH_GLASS.get());
                        output.accept(ModBlocks.POLARIZED_GLASS.get());
                        output.accept(ModBlocks.SILO_HATCH_FRAME.get());
                        output.accept(ModBlocks.SILO_HATCH_OPENER.get());
                        output.accept(ModBlocks.VAULT_TECH_BLAST_DOOR.get());
                        output.accept(ModBlocks.SLIDING_BLAST_DOOR.get());
                        output.accept(ModBlocks.FIRE_DOOR.get());
                        output.accept(ModItems.TRANSITION_SEAL.get());
                        output.accept(ModBlocks.SILO_HATCH.get());
                        output.accept(ModBlocks.IRON_CRATE.get());
                        output.accept(ModBlocks.STEEL_CRATE.get());
                        output.accept(ModBlocks.DESH_CRATE.get());
                        output.accept(ModBlocks.TUNGSTEN_CRATE.get());
                        output.accept(ModBlocks.TEMPLATE_CRATE.get());
                        output.accept(ModBlocks.SAFE.get());
                        output.accept(ModBlocks.MASS_STORAGE_UNIT.get());
                        output.accept(ModBlocks.AUTOMATIC_CRAFTING_TABLE.get());
                        output.accept(ModBlocks.BURNER_PRESS_PREHEATER.get());
                        output.accept(ModBlocks.LEAD_ANVIL.get());
                        output.accept(ModBlocks.BURNER_PRESS.get());
                        output.accept(ModBlocks.STEAM_PUMP.get());
                        output.accept(ModBlocks.ELECTRIC_GROUNDWATER_PUMP.get());
                        output.accept(ModBlocks.FIREBOX.get());
                        output.accept(ModBlocks.HEATING_OVEN.get());
                        output.accept(ModBlocks.ASHPIT.get());
                        output.accept(ModBlocks.IRON_FURNACE.get());
                        output.accept(ModBlocks.STEEL_FURNACE.get());
                        output.accept(ModBlocks.COMBINATION_OVEN.get());
                        output.accept(ModBlocks.CRUCIBLE.get());
                        output.accept(ModBlocks.BOILER.get());
                        output.accept(ModBlocks.INDUSTRIAL_BOILER.get());
                        output.accept(ModBlocks.SHALLOW_FOUNDRY_BASIN.get());
                        output.accept(ModBlocks.BLAST_FURNACE.get());
                        output.accept(ModBlocks.CENTRIFUGE.get());
                        output.accept(ModBlocks.FEL.get());
                        output.accept(ModBlocks.SILEX.get());
                        output.accept(ModBlocks.ORE_ACIDIZER.get());
                        output.accept(ModBlocks.BREEDING_REACTOR.get());
                        output.accept(ModBlocks.WOOD_BURNING_GENERATOR.get());
                        output.accept(ModBlocks.INDUSTRIAL_COMBUSTION_GENERATOR.get());
                        output.accept(ModBlocks.ZIRNOX_NUCLEAR_REACTOR.get());
                        output.accept(ModBlocks.CYCLOTRON.get());
                        output.accept(ModBlocks.EXPOSURE_CHAMBER.get());
                        output.accept(ModBlocks.FUEL_ROD_EMPTY.get());
                        output.accept(ModBlocks.FUEL_ROD_NORMAL.get());
                        output.accept(ModBlocks.CONTROL_ROD.get());
                        output.accept(ModBlocks.STRUCTURAL_COLUMN.get());
                        output.accept(ModBlocks.STEAM_CHANNEL.get());
                        output.accept(ModBlocks.NEUTRON_REFLECTOR.get());
                        output.accept(ModBlocks.NEUTRON_ABSORBER.get());
                        output.accept(ModBlocks.GRAPHITE_MODERATOR.get());
                        output.accept(ModBlocks.CONTROL_SYSTEM.get());
                        output.accept(ModBlocks.STEAM_SEPARATOR.get());
                        output.accept(ModBlocks.COPPER_CABLE.get());
                        output.accept(ModBlocks.PAINTABLE_RED_COPPER_CABLE.get());
                        output.accept(ModBlocks.LARGE_ELECTRICITY_PYLON.get());
                        output.accept(ModBlocks.POWER_SWITCH.get());
                        output.accept(ModBlocks.UNIVERSAL_FLUID_DUCT.get());
                        output.accept(ModBlocks.PAINTABLE_COATED_EXHAUST_PIPE.get());
                        output.accept(ModBlocks.PAINTABLE_COATED_UNIVERSAL_FLUID_DUCT.get());
                        output.accept(ModBlocks.FLOW_GAUGE_PIPE.get());
                        output.accept(ModBlocks.FLUID_VALVE.get());
                        output.accept(ModBlocks.REDSTONE_FLUID_VALVE.get());
                        output.accept(ModBlocks.DRAINAGE_PIPE.get());
                        output.accept(ModBlocks.CONVEYOR_EJECTOR.get());
                        output.accept(ModBlocks.CONVEYOR_INSERTER.get());
                        output.accept(ModBlocks.CONVEYOR_SPLITTER.get());
                        output.accept(ModBlocks.BATTERY_SOCKET.get());
                        output.accept(ModBlocks.ASSEMBLY_MACHINE.get());
                        output.accept(ModBlocks.ASSEMBLY_FACTORY.get());
                        output.accept(ModBlocks.CHEMICAL_PLANT.get());
                        output.accept(ModBlocks.CHEMICAL_FACTORY.get());
                        output.accept(ModBlocks.ARC_WELDER.get());
                        output.accept(ModBlocks.SOLDERING_STATION.get());
                        output.accept(ModBlocks.ELECTRIC_ARC_FURNACE.get());
                        output.accept(ModBlocks.TANK.get());
                        output.accept(ModBlocks.WATER_TANK.get());
                        output.accept(ModBlocks.INDUSTRIAL_STEAM_TURBINE.get());
                        output.accept(ModBlocks.LEVIATHAN_STEAM_TURBINE.get());
                        output.accept(ModBlocks.STEAM_CONDENSER.get());
                        output.accept(ModBlocks.COOLING_TOWER.get());
                        output.accept(ModBlocks.INDUSTRIAL_COOLING_TOWER.get());
                        output.accept(ModBlocks.DEUTERIUM_EXTRACTION_TOWER.get());
                        output.accept(ModBlocks.COMPRESSOR.get());
                        output.accept(ModBlocks.ELECTROLYSIS_MACHINE.get());
                        output.accept(ModBlocks.SHREDDER.get());
                        output.accept(ModBlocks.OIL_DERRICK.get());
                        output.accept(ModBlocks.PUMPJACK.get());
                        output.accept(ModBlocks.HYDRAULIC_FRACKING_TOWER.get());
                        output.accept(ModBlocks.FLARE_STACK.get());
                        output.accept(ModBlocks.SMOKESTACK.get());
                        output.accept(ModBlocks.INDUSTRIAL_SMOKESTACK.get());
                        output.accept(ModBlocks.OIL_REFINERY.get());
                        output.accept(ModBlocks.VACUUM_REFINERY.get());
                        output.accept(ModBlocks.FRACTIONATING_TOWER.get());
                        output.accept(ModBlocks.FRACTIONATING_TOWER_SEPARATOR.get());
                        output.accept(ModBlocks.CATALYTIC_CRACKING_TOWER.get());
                        output.accept(ModBlocks.CATALYTIC_REFORMER.get());
                        output.accept(ModBlocks.HYDROTREATER.get());
                        output.accept(ModBlocks.COKER_UNIT.get());
                        output.accept(ModBlocks.PYROLYSIS_OVEN.get());
                        output.accept(ModBlocks.LARGE_MINING_DRILL.get());
                        output.accept(ModBlocks.BEDROCK_ORE_PROCESSOR.get());
                        output.accept(ModBlocks.TURBOFAN.get());
                        output.accept(ModBlocks.MATTER_GIGAFACTORY.get());
                        output.accept(ModBlocks.RADIATION_ABSORBER.get());
                        output.accept(ModBlocks.ENHANCED_RADIATION_ABSORBER.get());
                        output.accept(ModBlocks.ADVANCED_RADIATION_ABSORBER.get());
                        output.accept(ModBlocks.ELITE_RADIATION_ABSORBER.get());
                        output.accept(ModBlocks.PLAYER_DECONTAMINATOR.get());
                        output.accept(ModBlocks.PWR_FUEL_ROD.get());
                        output.accept(ModBlocks.PWR_CONTROL_ROD.get());
                        output.accept(ModBlocks.PWR_COOLANT_CHANNEL.get());
                        output.accept(ModBlocks.PWR_HEAT_EXCHANGER.get());
                        output.accept(ModBlocks.PWR_HEATSINK.get());
                        output.accept(ModBlocks.PWR_NEUTRON_SOURCE.get());
                        output.accept(ModBlocks.PWR_NEUTRON_REFLECTOR.get());
                        output.accept(ModBlocks.DUCRETE_BRICKS.get());
                        output.accept(ModBlocks.PWR_CONTROLLER.get());
                        output.accept(ModBlocks.CHLORINE_VENT.get());
                        output.accept(ModBlocks.CHLORINE_SEAL.get());
                        output.accept(ModBlocks.CHLORINE_GAS.get());
                        output.accept(ModBlocks.CONVEYOR_BELT.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> NTM_BOMBS = CREATIVE_MODE_TABS.register("z_hbm_ntm_bombs",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.FAT_MAN.get()))
                    .backgroundSuffix("ntm_bombs.png")
                    .title(Component.translatable("creativetab.hbm_ntm_templates"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.THE_GADGET.get());
                        output.accept(ModBlocks.LITTLE_BOY.get());
                        output.accept(ModBlocks.FAT_MAN.get());
                        output.accept(ModBlocks.IVY_MIKE.get());
                        output.accept(ModBlocks.TSAR_BOMBA.get());
                        output.accept(ModBlocks.THE_GADGET.get());
                        output.accept(ModBlocks.THE_PROTOTYPE.get());
                        output.accept(ModBlocks.FLEIJA.get());
                        output.accept(ModBlocks.THE_BLUE_RINSE.get());
                        output.accept(ModBlocks.FIREWORK_BATTERY.get());
                        output.accept(ModBlocks.DYNAMITE.get());
                        output.accept(ModBlocks.ACTUAL_TNT.get());
                        output.accept(ModBlocks.SEMTEX.get());
                        output.accept(ModBlocks.C_4.get());
                        output.accept(ModBlocks.FISSURE_BOMB.get());
                        output.accept(ModBlocks.TIME_BOMB.get());
                        output.accept(ModBlocks.ANTI_PERSONELL_MINE.get());
                        output.accept(ModBlocks.FLAME_WAR_IN_A_BOX.get());
                        output.accept(ModBlocks.LEVITATION_BOMB.get());
                        output.accept(ModBlocks.ENDOTHERMIC_BOMB.get());
                        output.accept(ModBlocks.EXOTHERMIC_BOMB.get());
                        output.accept(ModBlocks.EMP_DEVICE.get());
                        output.accept(ModBlocks.EXPLOSIVE_CHARGE.get());
                        output.accept(ModBlocks.NUCLEAR_CHARGE.get());
                        output.accept(ModBlocks.MINING_CHARGE.get());
                        output.accept(ModItems.HIGH_EXPLOSIVE_LENSES.get());
                        output.accept(ModItems.PLUTONIUM_CORE.get());
                        output.accept(ModItems.BOMB_FIRING_UNIT.get());
                        output.accept(ModItems.WIRING.get());
                        output.accept(ModItems.NEUTRON_SHIELDING.get());
                        output.accept(ModItems.SUBCRITICAL_TARGET.get());
                        output.accept(ModItems.U235_PROJECTILE.get());
                        output.accept(ModItems.PROPELLANT.get());
                        output.accept(ModItems.BOMB_IGNITER.get());
                        output.accept(ModItems.LARGE_PLUTONIUM_CORE.get());
                        output.accept(ModItems.URANIUM_COATED_DEUTERIUM_TANK.get());
                        output.accept(ModItems.DEUTERIUM_TANK.get());
                        output.accept(ModItems.DEUTERIUM_COOLING_UNIT.get());
                        output.accept(ModItems.TSAR_BOMBA_CORE.get());
                        output.accept(ModItems.PULSE_IGNITER.get());
                        output.accept(ModItems.SCHRABIDIUM_PROPELLANT.get());
                        output.accept(ModItems.FLEIJA_URANIUM_CHARGE.get());
                        output.accept(ModItems.SOL_PULSE_IGNITER.get());
                        output.accept(ModItems.SOL_COMPRESSION_CHARGE.get());
                        output.accept(ModItems.SEMI_STABLE_SOLINIUM_CORE.get());
                        output.accept(ModItems.LARGE_EXPLOSIVE_CHARGE.get());
                        output.accept(ModItems.BALEFIRE_SHARD.get());
                        output.accept(ModItems.BALEFIRE_EGG.get());
                        output.accept(ModItems.CUSTOM_NUKE_EXPLOSIVE_CHARGE.get());
                        output.accept(ModItems.CUSTOM_NUKE_NUCLEAR_ROD.get());
                        output.accept(ModItems.CUSTOM_NUKE_HYDROGEN_ROD.get());
                        output.accept(ModItems.CUSTOM_NUKE_ANTIMATTER_ROD.get());
                        output.accept(ModItems.CUSTOM_NUKE_DIRTY_ROD.get());
                        output.accept(ModItems.CUSTOM_NUKE_SCHRABIDIUM_ROD.get());
                        output.accept(ModItems.CUSTOM_NUKE_DROP_UPGRADE.get());
                        output.accept(ModItems.IGNITER.get());
                        output.accept(ModItems.REMOTE_DETONATOR.get());
                        output.accept(ModItems.MULTI_DETONATOR.get());
                        output.accept(ModItems.THE_GADGET_KIT.get());
                        output.accept(ModItems.LITTLE_BOY_KIT.get());
                        output.accept(ModItems.FAT_MAN_KIT.get());
                        output.accept(ModItems.IVY_MIKE_KIT.get());
                        output.accept(ModItems.TSAR_BOMBA_KIT.get());
                        output.accept(ModItems.PROTOTYPE_KIT.get());
                        output.accept(ModItems.FLEIJA_KIT.get());
                        output.accept(ModItems.SOLINIUM_KIT.get());
                        output.accept(ModItems.MULTI_PURPOSE_BOMB_KIT.get());
                        output.accept(ModItems.CUSTOM_NUKE_KIT.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> NTM_MISSILES_AND_SATELLITES = CREATIVE_MODE_TABS.register("ntm_missiles_and_satellites",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.NUCLEAR_MISSILE.get()))
                    .title(Component.translatable("creativetab.ntm_missiles_and_satellites"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModBlocks.SATELLITE_ID_MANAGER.get());
                        output.accept(ModBlocks.LAUNCH_PAD_COMPONENT_BLOCK.get());
                        output.accept(ModBlocks.LAUNCH_PAD_SCAFFOLD_BLOCK.get());
                        output.accept(ModBlocks.COMPACT_LAUNCHER_CORE_COMPONENT.get());
                        output.accept(ModBlocks.LAUNCH_TABLE_CORE_COMPONENT.get());
                        output.accept(ModBlocks.SOYUZ_LAUNCHER_CORE_COMPONENT.get());
                        output.accept(ModBlocks.SILO_LAUNCH_PAD.get());
                        output.accept(ModBlocks.COMPACT_LAUNCH_PAD.get());
                        output.accept(ModBlocks.SOYUZ_LAUNCH_PLATFORM.get());
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
