package net.StrayBead.hbm_ntm.datagen.loot;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.add(ModBlocks.LIGNITE_ORE.get(),
                block -> createOreDrop(ModBlocks.LIGNITE_ORE.get(), ModItems.LIGNITE.get()));
        this.dropSelf(ModBlocks.BLOCK_ON.get());
        this.dropSelf(ModBlocks.BLOCK_OFF.get());
        this.dropSelf(ModBlocks.BLOCK_OF_GRAPHITE.get());
        this.dropSelf(ModBlocks.DRILLED_GRAPHITE.get());
        this.dropSelf(ModBlocks.DRILLED_GRAPHITE_NEUTRON_SOURCE.get());
        this.dropSelf(ModBlocks.GRAPHITE.get());
        this.dropSelf(ModBlocks.OIL_DERRICK.get());
        this.dropSelf(ModBlocks.OIL_REFINERY.get());
        this.dropSelf(ModBlocks.SILO_LAUNCH_PAD.get());
        this.dropSelf(ModBlocks.INDUSTRIAL_STEAM_TURBINE.get());
        this.dropSelf(ModBlocks.THE_PROTOTYPE.get());
        this.dropSelf(ModBlocks.FLEIJA.get());
        this.dropSelf(ModBlocks.THE_BLUE_RINSE.get());
        this.dropSelf(ModBlocks.STEEL_BLOCK.get());
        this.dropSelf(ModBlocks.WOOD_BURNING_GENERATOR.get());
        this.dropSelf(ModBlocks.POWER_SWITCH.get());
        this.dropSelf(ModBlocks.RED_COPPER_BLOCK.get());
        this.dropSelf(ModBlocks.SIM_CARD_SIGNAL_BROADCASTING_DEVICE.get());
        this.dropSelf(ModBlocks.OIL_RESERVOIR.get());
        this.dropSelf(ModBlocks.SHORT_RANGE_SIM_CARD_DATA_SENDER.get());
        this.dropSelf(ModBlocks.CONCRETE_BRICK_STAIRS.get());
        this.dropSelf(ModBlocks.CONCRETE_STAIRS.get());
        this.dropSelf(ModBlocks.CRACKED_CONCRETE_BRICK_STAIRS.get());
        this.dropSelf(ModBlocks.ELECTRIC_ARC_FURNACE.get());
        this.dropSelf(ModBlocks.PUMPJACK.get());
        this.dropSelf(ModBlocks.EXPOSURE_CHAMBER.get());
        this.dropSelf(ModBlocks.VACUUM_REFINERY.get());
        this.dropSelf(ModBlocks.COMPRESSOR.get());
        this.dropSelf(ModBlocks.FLARE_STACK.get());
        this.dropSelf(ModBlocks.LARGE_MINING_DRILL.get());
        this.dropSelf(ModBlocks.HEATING_OVEN.get());
        this.dropSelf(ModBlocks.SHALLOW_FOUNDRY_BASIN.get());
        this.dropSelf(ModBlocks.TURBOFAN.get());
        this.dropSelf(ModBlocks.MALACHITE.get());
        this.dropSelf(ModBlocks.HEMATITE.get());
        this.dropSelf(ModBlocks.ASSEMBLY_MACHINE.get());
        this.dropSelf(ModBlocks.INDUSTRIAL_COMBUSTION_GENERATOR.get());
        this.dropSelf(ModBlocks.INDUSTRIAL_BOILER.get());
        this.dropSelf(ModBlocks.CRUCIBLE.get());
        this.dropSelf(ModBlocks.RARE_EARTH_ORE.get());
        this.dropSelf(ModBlocks.DRAINAGE_PIPE.get());
        this.dropSelf(ModBlocks.FRACTIONATING_TOWER.get());
        this.dropSelf(ModBlocks.REINFORCED_STEEL_SCAFFOLD.get());
        this.dropSelf(ModBlocks.CHEMICAL_FACTORY.get());
        this.dropSelf(ModBlocks.LARGE_ELECTRICITY_PYLON.get());
        this.dropSelf(ModBlocks.DEUTERIUM_EXTRACTION_TOWER.get());
        this.dropSelf(ModBlocks.FIREBOX.get());
        this.dropSelf(ModBlocks.CHEMICAL_PLANT.get());
        this.dropSelf(ModBlocks.CATALYTIC_REFORMER.get());
        this.dropSelf(ModBlocks.STEAM_CONDENSER.get());
        this.dropSelf(ModBlocks.MATTER_GIGAFACTORY.get());
        this.dropSelf(ModBlocks.FRACTIONATING_TOWER_SEPARATOR.get());
        this.dropSelf(ModBlocks.BATTERY_SOCKET.get());
        this.dropSelf(ModBlocks.REDSTONE_BATTERY.get());
        this.dropSelf(ModBlocks.SIGNAL_BLOCK.get());
        this.dropSelf(ModBlocks.TUNGSTEN_ORE_BLOCK.get());
        this.dropSelf(ModBlocks.TUNGSTEN_ORE.get());
        this.dropSelf(ModBlocks.TITANIUM_ORE.get());
        this.dropSelf(ModBlocks.TITANIUM_ORE_BLOCK.get());
        this.dropSelf(ModBlocks.QUARTZ_SAND.get());
        this.dropSelf(ModBlocks.QUARTZ_GLASS.get());
        this.dropSelf(ModBlocks.CASTLE_BRAVO.get());
        this.dropSelf(ModBlocks.TEST.get());
        this.dropSelf(ModBlocks.URANIUM_ORE.get());
        this.dropSelf(ModBlocks.BURNER_PRESS.get());
        this.dropSelf(ModBlocks.URANIUM_ORE_BLOCK.get());
        this.dropSelf(ModBlocks.FUEL_ROD.get());
        this.dropSelf(ModBlocks.CRUDE_OIL_PIPE.get());
        this.dropSelf(ModBlocks.CONVEYOR_LIFT.get());
        this.dropSelf(ModBlocks.PWR_CONTROLLER.get());
        this.dropSelf(ModBlocks.ZIRNOX_NUCLEAR_REACTOR.get());
        this.dropSelf(ModBlocks.CONVEYOR_CHAIN_LIFT.get());
        this.dropSelf(ModBlocks.UNIVERSAL_FLUID_DUCT.get());
        this.dropSelf(ModBlocks.FUEL_ROD_EMPTY.get());
        this.dropSelf(ModBlocks.CONTROL_ROD.get());
        this.dropSelf(ModBlocks.GRAPHITE_MODERATOR.get());
        this.dropSelf(ModBlocks.STRUCTURAL_COLUMN.get());
        this.dropSelf(ModBlocks.STEAM_CHANNEL.get());
        this.dropSelf(ModBlocks.FUEL_ROD_NORMAL.get());
        this.dropSelf(ModBlocks.NEUTRON_REFLECTOR.get());
        this.dropSelf(ModBlocks.RADIATED_GRAPHITE.get());
        this.dropSelf(ModBlocks.NUCLEAR_SIREN.get());
        this.dropSelf(ModBlocks.STEAM_SEPARATOR.get());
        this.dropSelf(ModBlocks.WATER_DUCT.get());
        this.dropSelf(ModBlocks.ULTRA_DENSE_STEAM_PIPE.get());
        this.dropSelf(ModBlocks.SUPER_DENSE_STEAM_PIPE.get());
        this.dropSelf(ModBlocks.DENSE_STEAM_PIPE.get());
        this.dropSelf(ModBlocks.STEAM_PIPE.get());
        this.dropSelf(ModBlocks.CONCRETE_BRICKS.get());
        this.dropSelf(ModBlocks.CONTROL_SYSTEM.get());
        this.dropSelf(ModBlocks.DEAD_GRASS.get());
        this.dropSelf(ModBlocks.REINFORCED_GLASS.get());
        this.dropSelf(ModBlocks.WARNING_BLOCK.get());
        this.dropSelf(ModBlocks.WOOD_BRICKS.get());
        this.dropSelf(ModBlocks.PWR_COOLANT_CHANNEL.get());
        this.dropSelf(ModBlocks.PWR_NEUTRON_REFLECTOR.get());
        this.dropSelf(ModBlocks.PWR_HEATSINK.get());
        this.dropSelf(ModBlocks.PWR_HEAT_EXCHANGER.get());
        this.dropSelf(ModBlocks.PWR_CONTROL_ROD.get());
        this.dropSelf(ModBlocks.PWR_FUEL_ROD.get());
        this.dropSelf(ModBlocks.PWR_NEUTRON_SOURCE.get());
        this.dropSelf(ModBlocks.FAT_MAN.get());
        this.dropSelf(ModBlocks.FAN.get());
        this.dropSelf(ModBlocks.SOLAR_PANEL.get());
        this.dropSelf(ModBlocks.LEVIATHAN_STEAM_TURBINE.get());
        this.dropSelf(ModBlocks.COOLING_TOWER.get());
        this.dropSelf(ModBlocks.COOLING_TOWER_1.get());
        this.dropSelf(ModBlocks.DET_CORD.get());
        this.dropSelf(ModBlocks.SCHRABIDIUM_BLOCK.get());
        this.dropSelf(ModBlocks.SPARK_ENERGY_BATTERY.get());
        this.dropSelf(ModBlocks.SHREDDER.get());
        this.dropSelf(ModBlocks.PARTICLE_ACCELERATOR_PLATING.get());
        this.dropSelf(ModBlocks.WATER_TANK.get());
        this.dropSelf(ModBlocks.WATER_TANK_TOP.get());
        this.dropSelf(ModBlocks.THORIUM_ORE.get());
        this.dropSelf(ModBlocks.LEAD_BLOCK.get());
        this.dropSelf(ModBlocks.LEAD_ORE.get());
        this.dropSelf(ModBlocks.SCHRABIDIUM_ORE.get());
        this.dropSelf(ModBlocks.RADIOACTIVE_BARREL.get());
        this.dropSelf(ModBlocks.NEUTRON_ABSORBER.get());
        this.dropSelf(ModBlocks.TSAR_BOMBA.get());
        this.dropSelf(ModBlocks.LEAD_ANVIL.get());
        this.dropSelf(ModBlocks.BLAST_FURNACE.get());
        this.dropSelf(ModBlocks.COMBUSTION_GENERATOR.get());
        this.dropSelf(ModBlocks.RADIATION_ABSORBER.get());
        this.dropSelf(ModBlocks.ENHANCED_RADIATION_ABSORBER.get());
        this.dropSelf(ModBlocks.ADVANCED_RADIATION_ABSORBER.get());
        this.dropSelf(ModBlocks.ELITE_RADIATION_ABSORBER.get());
        this.dropSelf(ModBlocks.PLAYER_DECONTAMINATOR.get());
        this.dropSelf(ModBlocks.SOLDERING_STATION.get());
        this.dropSelf(ModBlocks.ARC_WELDER.get());
        this.dropSelf(ModBlocks.CHLORINE_VENT.get());
        this.dropSelf(ModBlocks.CHLORINE_SEAL.get());
        this.dropSelf(ModBlocks.CHLORINE_GAS.get());
        this.dropSelf(ModBlocks.MOLDY_DEBRIS.get());
        this.dropSelf(ModBlocks.LIGHT_BRICKS.get());
        this.dropSelf(ModBlocks.CRUSHED_OBSIDIAN.get());
        this.dropSelf(ModBlocks.ASPHALT.get());
        this.dropSelf(ModBlocks.GLOWING_ASPHALT.get());
        this.dropSelf(ModBlocks.FIREBRICKS.get());
        this.dropSelf(ModBlocks.MARKED_CONCRETE_BRICKS.get());
        this.dropSelf(ModBlocks.CRACKED_CONCRETE_BRICKS.get());
        this.dropSelf(ModBlocks.BROKEN_CONCRETE_BRICKS.get());
        this.dropSelf(ModBlocks.LARGE_VINYL_TILE.get());
        this.dropSelf(ModBlocks.REINFORCED_SANDSTONE.get());
        this.dropSelf(ModBlocks.REINFORCED_GLOWSTONE.get());
        this.dropSelf(ModBlocks.REINFORCED_LAMP.get());
        this.dropSelf(ModBlocks.STEAM_CHANNEL_TOP.get());
        this.dropSelf(ModBlocks.REINFORCED_LAMINATE.get());
        this.dropSelf(ModBlocks.CONTROL_ROD_TOP.get());
        this.dropSelf(ModBlocks.SMALL_VINYL_TILES.get());
        this.dropSelf(ModBlocks.DENSE_STONE.get());
        this.dropSelf(ModBlocks.CONCRETE_TILE.get());
        this.dropSelf(ModBlocks.ASBESTOS_CONCRETE.get());
        this.dropSelf(ModBlocks.REBAR_REINFORCED_CONCRETE_PILLAR.get());
        this.dropSelf(ModBlocks.MOSSY_CONCRETE_BRICKS.get());
        this.dropSelf(ModBlocks.CMB_STEEL_TILE.get());
        this.dropSelf(ModBlocks.DUCRETE.get());
        this.dropSelf(ModBlocks.DUCRETE_TILE.get());
        this.dropSelf(ModBlocks.OBSIDIAN_BRICKS.get());
        this.dropSelf(ModBlocks.REINFORCED_STONE.get());
        this.dropSelf(ModBlocks.UBER_CONCRETE.get());
        this.dropSelf(ModBlocks.COMPOUND_MESH.get());
        this.dropSelf(ModBlocks.DUCRETE_BRICKS.get());
        this.dropSelf(ModBlocks.REINFORCED_DUCRETE.get());
        this.dropSelf(ModBlocks.REINFORCED_CMB_BRICKS.get());
        this.dropSelf(ModBlocks.LITTLE_BOY.get());
        this.dropSelf(ModBlocks.IVY_MIKE.get());
        this.dropSelf(ModBlocks.CONVEYOR_BELT.get());
        this.dropSelf(ModBlocks.CONVEYOR_BELT_FACING_Z.get());
        this.dropSelf(ModBlocks.INDUSTRIAL_COOLING_TOWER.get());
        this.dropSelf(ModBlocks.THE_GADGET.get());
        this.dropSelf(ModBlocks.CENTRIFUGE.get());
        this.dropSelf(ModBlocks.CONVEYOR_INSERTER.get());
        this.dropSelf(ModBlocks.CONVEYOR_EJECTOR.get());
        this.dropSelf(ModBlocks.ORE_ACIDIZER.get());
        this.dropSelf(ModBlocks.CONVEYOR_SPLITTER.get());
        this.dropSelf(ModBlocks.BOILER.get());
        this.dropSelf(ModBlocks.COPPER_CABLE.get());
        this.dropSelf(ModBlocks.COPPER_CABLE_TURN_RIGHT.get());
        this.dropSelf(ModBlocks.COPPER_CABLE_TURN_LEFT.get());
        this.dropSelf(ModBlocks.COPPER_CABLE_THREE_SIDES.get());
        this.dropSelf(ModBlocks.COPPER_CABLE_FOUR_SIDES.get());
        this.dropSelf(ModBlocks.COPPER_CABLE_FIVE_SIDES.get());
        this.dropSelf(ModBlocks.COPPER_CABLE_ALL_SIDES.get());
        this.dropSelf(ModBlocks.TANK.get());
        this.dropSelf(ModBlocks.BOILER.get());
        this.dropSelf(ModBlocks.SMOKESTACK.get());
        this.dropSelf(ModBlocks.HYDRAULIC_FRACKING_TOWER.get());
        this.dropSelf(ModBlocks.HYDROTREATER.get());
        this.dropSelf(ModBlocks.ELECTROLYSIS_MACHINE.get());
        this.dropSelf(ModBlocks.BREEDING_REACTOR.get());
        this.dropSelf(ModBlocks.SILEX.get());
        this.dropSelf(ModBlocks.FEL.get());
        this.dropSelf(ModBlocks.CATALYTIC_CRACKING_TOWER.get());
        this.dropSelf(ModBlocks.COMBINATION_OVEN.get());
        this.dropSelf(ModBlocks.STEEL_GRATE.get());
        this.dropSelf(ModBlocks.STEEL_SCAFFOLD.get());
        this.dropSelf(ModBlocks.STEEL_DECO_BLOCK.get());
        this.dropSelf(ModBlocks.PAINTABLE_COATED_UNIVERSAL_FLUID_DUCT.get());
        this.dropSelf(ModBlocks.SULFUR_ORE.get());
        this.dropSelf(ModBlocks.ALUMINUM_ORE.get());
        this.dropSelf(ModBlocks.TAPE_RECORDER.get());
        this.dropSelf(ModBlocks.STEEL_POLE.get());
        this.dropSelf(ModBlocks.ANTENNA_TOP.get());
        this.dropSelf(ModBlocks.CONVEYOR_CHAIN_LIFT.get());
        this.dropSelf(ModBlocks.BROKEN_METEORITE_BLOCK.get());
        this.dropSelf(ModBlocks.METEORITE_COBBLESTONE.get());
        this.dropSelf(ModBlocks.METEOR_COBALT_ORE.get());
        this.dropSelf(ModBlocks.METEOR_IRON_ORE.get());
        this.dropSelf(ModBlocks.METEOR_COPPER_ORE.get());
        this.dropSelf(ModBlocks.METEOR_ALUMINUM_ORE.get());
        this.dropSelf(ModBlocks.METEOR_RARE_EARTH_ORE.get());
        this.dropSelf(ModBlocks.HOT_METEORITE_COBBLESTONE.get());
        this.dropSelf(ModBlocks.CHAINLINK_FENCE.get());
        this.dropSelf(ModBlocks.CHAINLINK_FENCE_POST.get());
        this.dropSelf(ModBlocks.ASH.get());
        this.dropSelf(ModBlocks.BORON_SAND.get());
        this.dropSelf(ModBlocks.LEAD_SAND.get());
        this.dropSelf(ModBlocks.URANIUM_SAND.get());
        this.dropSelf(ModBlocks.POLONIUM_SAND.get());
        this.dropSelf(ModBlocks.BORON_GLASS.get());
        this.dropSelf(ModBlocks.LEAD_GLASS.get());
        this.dropSelf(ModBlocks.URANIUM_GLASS.get());
        this.dropSelf(ModBlocks.TRINITY_GLASS.get());
        this.dropSelf(ModBlocks.POLONIUM_GLASS.get());
        this.dropSelf(ModBlocks.ASH_GLASS.get());
        this.dropSelf(ModBlocks.POLARIZED_GLASS.get());
        this.dropSelf(ModBlocks.SILO_HATCH_FRAME.get());
        this.dropSelf(ModBlocks.SILO_HATCH_OPENER.get());
        this.dropSelf(ModBlocks.IRON_CRATE.get());
        this.dropSelf(ModBlocks.STEEL_CRATE.get());
        this.dropSelf(ModBlocks.DESH_CRATE.get());
        this.dropSelf(ModBlocks.TUNGSTEN_CRATE.get());
        this.dropSelf(ModBlocks.TEMPLATE_CRATE.get());
        this.dropSelf(ModBlocks.SAFE.get());
        this.dropSelf(ModBlocks.SILO_HATCH.get());
        this.dropSelf(ModBlocks.CYCLOTRON.get());
        this.dropSelf(ModBlocks.ASHPIT.get());
        this.dropSelf(ModBlocks.FIRE_DOOR.get());
        this.dropSelf(ModBlocks.VAULT_TECH_BLAST_DOOR.get());
        this.dropSelf(ModBlocks.SLIDING_BLAST_DOOR.get());
        this.dropSelf(ModBlocks.MASS_STORAGE_UNIT.get());
        this.dropSelf(ModBlocks.STEEL_BARREL.get());
        this.dropSelf(ModBlocks.AUTOMATIC_CRAFTING_TABLE.get());
        this.dropSelf(ModBlocks.BURNER_PRESS_PREHEATER.get());
        this.add(ModBlocks.BERYLLIUM_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.BERYLLIUM_ORE.get(), ModItems.RAW_BERYLLIUM.get()));
        this.add(ModBlocks.FLOURITE_ORE.get(),
                block -> createCopperLikeOreDrops(ModBlocks.FLOURITE_ORE.get(), ModItems.FLUORITE.get()));

        this.add(ModBlocks.BUNKER_DOOR.get(),
                block -> createDoorTable(ModBlocks.BUNKER_DOOR.get()));
        this.add(ModBlocks.DOOR_METAL.get(),
                block -> createDoorTable(ModBlocks.DOOR_METAL.get()));

        this.add(ModBlocks.BROKEN_CONCRETE_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.BROKEN_CONCRETE_BRICK_SLAB.get()));
        this.add(ModBlocks.CONCRETE_BRICK_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.CONCRETE_BRICK_SLAB.get()));
        this.add(ModBlocks.CONCRETE_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.CONCRETE_SLAB.get()));
    }

    protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
        return createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
