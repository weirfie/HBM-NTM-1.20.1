package net.StrayBead.hbm_ntm.datagen;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.TankBlock;
import net.StrayBead.hbm_ntm.block.custom.TankTextureType;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.loaders.ObjModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.Locale;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, HBMNTM.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (RegistryObject<? extends Block> blockObj : ModBlocks.GENERAL_SIMPLE_BLOCK.values()) {
            simpleBlockWithItem(blockObj.get(), cubeAll(blockObj.get()));
        }
        blockWithItem(ModBlocks.BLOCK_OF_GRAPHITE);
        blockWithItem(ModBlocks.BLOCK_ON);
        blockWithItem(ModBlocks.BLOCK_OFF);
        blockWithItem(ModBlocks.SIGNAL_BLOCK);
        blockWithItem(ModBlocks.TITANIUM_ORE_BLOCK);
        blockWithItem(ModBlocks.SIM_CARD_SIGNAL_BROADCASTING_DEVICE);
        blockWithItem(ModBlocks.STEAM_CONDENSER);
        blockWithItem(ModBlocks.TITANIUM_ORE);
        blockWithItem(ModBlocks.CONVEYOR_SPLITTER);
        blockWithItem(ModBlocks.RARE_EARTH_ORE);
        blockWithItem(ModBlocks.LIGNITE_ORE);
        blockWithItem(ModBlocks.METEOR_COPPER_ORE);
        blockWithItem(ModBlocks.STEEL_BLOCK);
        blockWithItem(ModBlocks.OIL_RESERVOIR);
        blockWithItem(ModBlocks.SULFUR_ORE);
        blockWithItem(ModBlocks.MALACHITE);
        blockWithItem(ModBlocks.HEMATITE);
        blockWithItem(ModBlocks.ALUMINUM_ORE);
        blockWithItem(ModBlocks.POWER_SWITCH);
        blockWithItem(ModBlocks.RED_COPPER_BLOCK);
        blockWithItem(ModBlocks.QUARTZ_GLASS);
        blockWithItem(ModBlocks.QUARTZ_SAND);
        blockWithItem(ModBlocks.TUNGSTEN_ORE_BLOCK);
        blockWithItem(ModBlocks.TUNGSTEN_ORE);
        blockWithItem(ModBlocks.URANIUM_ORE_BLOCK);
        blockWithItem(ModBlocks.GENERIC_BOUNDING_BOX);
        blockWithItem(ModBlocks.URANIUM_ORE);
        blockWithItem(ModBlocks.DRILLED_GRAPHITE);
        blockWithItem(ModBlocks.GRAPHITE);
        blockWithItem(ModBlocks.FLOURITE_ORE);
        blockWithItem(ModBlocks.DRILLED_GRAPHITE_NEUTRON_SOURCE);
        blockWithItem(ModBlocks.FUEL_ROD);
        blockWithItem(ModBlocks.FUEL_ROD_EMPTY);
        blockWithItem(ModBlocks.CONTROL_ROD);
        blockWithItem(ModBlocks.GRAPHITE_MODERATOR);
        blockWithItem(ModBlocks.STEAM_CHANNEL);
        blockWithItem(ModBlocks.FUEL_ROD_NORMAL);
        blockWithItem(ModBlocks.NEUTRON_REFLECTOR);
        blockWithItem(ModBlocks.RADIATED_GRAPHITE);
        blockWithItem(ModBlocks.WATER_DUCT);
        blockWithItem(ModBlocks.NUCLEAR_SIREN);
        blockWithItem(ModBlocks.CONCRETE_BRICKS);
        blockWithItem(ModBlocks.STEAM_SEPARATOR);
        blockWithItem(ModBlocks.ULTRA_DENSE_STEAM_PIPE);
        blockWithItem(ModBlocks.SUPER_DENSE_STEAM_PIPE);
        blockWithItem(ModBlocks.DENSE_STEAM_PIPE);
        blockWithItem(ModBlocks.STEAM_PIPE);
        blockWithItem(ModBlocks.DEAD_GRASS);
        blockWithItem(ModBlocks.REINFORCED_GLASS);
        blockWithItem(ModBlocks.WARNING_BLOCK);
        blockWithItem(ModBlocks.WOOD_BRICKS);
        blockWithItem(ModBlocks.PWR_CONTROL_ROD);
        blockWithItem(ModBlocks.PWR_COOLANT_CHANNEL);
        blockWithItem(ModBlocks.PWR_FUEL_ROD);
        blockWithItem(ModBlocks.PWR_HEATSINK);
        blockWithItem(ModBlocks.PWR_HEAT_EXCHANGER);
        blockWithItem(ModBlocks.PWR_NEUTRON_SOURCE);
        blockWithItem(ModBlocks.PWR_NEUTRON_REFLECTOR);
        blockWithItem(ModBlocks.SOLAR_PANEL);
        blockWithItem(ModBlocks.FAN);
        blockWithItem(ModBlocks.SHREDDER);
        blockWithItem(ModBlocks.SPARK_ENERGY_BATTERY);
        blockWithItem(ModBlocks.PARTICLE_ACCELERATOR_PLATING);
        blockWithItem(ModBlocks.DET_CORD);
        blockWithItem(ModBlocks.SCHRABIDIUM_BLOCK);
        blockWithItem(ModBlocks.THORIUM_ORE);
        blockWithItem(ModBlocks.LEAD_ORE);
        blockWithItem(ModBlocks.LEAD_BLOCK);
        blockWithItem(ModBlocks.SCHRABIDIUM_ORE);
        blockWithItem(ModBlocks.NEUTRON_ABSORBER);
        blockWithItem(ModBlocks.TSAR_BOMBA);
        blockWithItem(ModBlocks.BLAST_FURNACE);
        blockWithItem(ModBlocks.COMBUSTION_GENERATOR);
        blockWithItem(ModBlocks.RADIATION_ABSORBER);
        blockWithItem(ModBlocks.ENHANCED_RADIATION_ABSORBER);
        blockWithItem(ModBlocks.ADVANCED_RADIATION_ABSORBER);
        blockWithItem(ModBlocks.ELITE_RADIATION_ABSORBER);
        blockWithItem(ModBlocks.PLAYER_DECONTAMINATOR);
        blockWithItem(ModBlocks.BERYLLIUM_ORE);
        blockWithItem(ModBlocks.CHLORINE_VENT);
        blockWithItem(ModBlocks.CHLORINE_SEAL);
        blockWithItem(ModBlocks.CHLORINE_GAS);
        blockWithItem(ModBlocks.MOLDY_DEBRIS);
        blockWithItem(ModBlocks.ASPHALT);
        blockWithItem(ModBlocks.GLOWING_ASPHALT);
        blockWithItem(ModBlocks.LARGE_VINYL_TILE);
        blockWithItem(ModBlocks.SMALL_VINYL_TILES);
        blockWithItem(ModBlocks.FIREBRICKS);
        blockWithItem(ModBlocks.REINFORCED_GLOWSTONE);
        blockWithItem(ModBlocks.REINFORCED_LAMP);
        blockWithItem(ModBlocks.REINFORCED_SANDSTONE);
        blockWithItem(ModBlocks.CRUSHED_OBSIDIAN);
        blockWithItem(ModBlocks.LIGHT_BRICKS);
        blockWithItem(ModBlocks.REINFORCED_LAMINATE);
        blockWithItem(ModBlocks.MARKED_CONCRETE_BRICKS);
        blockWithItem(ModBlocks.BROKEN_CONCRETE_BRICKS);
        blockWithItem(ModBlocks.CRACKED_CONCRETE_BRICKS);
        blockWithItem(ModBlocks.DENSE_STONE);
        blockWithItem(ModBlocks.CONCRETE_TILE);
        blockWithItem(ModBlocks.ASBESTOS_CONCRETE);
        blockWithItem(ModBlocks.REBAR_REINFORCED_CONCRETE_PILLAR);
        blockWithItem(ModBlocks.MOSSY_CONCRETE_BRICKS);
        blockWithItem(ModBlocks.CMB_STEEL_TILE);
        blockWithItem(ModBlocks.DUCRETE);
        blockWithItem(ModBlocks.DUCRETE_TILE);
        blockWithItem(ModBlocks.OBSIDIAN_BRICKS);
        blockWithItem(ModBlocks.REINFORCED_STONE);
        blockWithItem(ModBlocks.UBER_CONCRETE);
        blockWithItem(ModBlocks.COMPOUND_MESH);
        blockWithItem(ModBlocks.DUCRETE_BRICKS);
        blockWithItem(ModBlocks.REINFORCED_DUCRETE);
        blockWithItem(ModBlocks.REINFORCED_CMB_BRICKS);
        blockWithItem(ModBlocks.IVY_MIKE);
        blockWithItem(ModBlocks.CASTLE_BRAVO);
        blockWithItem(ModBlocks.PWR_CONTROLLER);
        blockWithItem(ModBlocks.CONVEYOR_INSERTER);
        blockWithItem(ModBlocks.METEORITE_COBBLESTONE);
        blockWithItem(ModBlocks.METEOR_RARE_EARTH_ORE);
        blockWithItem(ModBlocks.METEOR_ALUMINUM_ORE);
        blockWithItem(ModBlocks.METEOR_COBALT_ORE);
        blockWithItem(ModBlocks.METEOR_IRON_ORE);
        blockWithItem(ModBlocks.BROKEN_METEORITE_BLOCK);
        blockWithItem(ModBlocks.HOT_METEORITE_COBBLESTONE);
        blockWithItem(ModBlocks.CONVEYOR_EJECTOR);
        blockWithItem(ModBlocks.ASH);
        blockWithItem(ModBlocks.BORON_SAND);
        blockWithItem(ModBlocks.LEAD_SAND);
        blockWithItem(ModBlocks.URANIUM_SAND);
        blockWithItem(ModBlocks.POLONIUM_SAND);
        blockWithItem(ModBlocks.BORON_GLASS);
        blockWithItem(ModBlocks.FLAME_WAR_IN_A_BOX);
        blockWithItem(ModBlocks.LEAD_GLASS);
        blockWithItem(ModBlocks.URANIUM_GLASS);
        blockWithItem(ModBlocks.TRINITY_GLASS);
        blockWithItem(ModBlocks.POLONIUM_GLASS);
        blockWithItem(ModBlocks.ASH_GLASS);
        blockWithItem(ModBlocks.POLARIZED_GLASS);
        blockWithItem(ModBlocks.SILO_HATCH_FRAME);
        blockWithItem(ModBlocks.SILO_HATCH_OPENER);
        blockWithItem(ModBlocks.EXPLOSIVE_CHARGE);
        fixedColumnBlock(ModBlocks.IRON_CRATE);
        fixedColumnBlock(ModBlocks.STEEL_CRATE);
        fixedColumnBlock(ModBlocks.DESH_CRATE);
        fixedColumnBlock(ModBlocks.TUNGSTEN_CRATE);
        fixedColumnBlock(ModBlocks.FIREWORK_BATTERY);
        fixedColumnBlock(ModBlocks.DYNAMITE);
        fixedColumnBlock(ModBlocks.ACTUAL_TNT);
        fixedColumnBlock(ModBlocks.SEMTEX);
        fixedColumnBlock(ModBlocks.C_4);
        fixedColumnBlock(ModBlocks.FISSURE_BOMB);
        fixedColumnBlock(ModBlocks.LEVITATION_BOMB);
        fixedColumnBlock(ModBlocks.ENDOTHERMIC_BOMB);
        fixedColumnBlock(ModBlocks.EXOTHERMIC_BOMB);
        fixedColumnBlock(ModBlocks.NUCLEAR_CHARGE);
        fixedColumnBlock(ModBlocks.MINING_CHARGE);
        fixedColumnBlock(ModBlocks.LIGHTSTONE_CHISELED_BRICKS);
        fixedColumnBlock(ModBlocks.CHISELED_LIGHTSTONE);
        fixedColumnBlock(ModBlocks.EMP_DEVICE);
        blockWithItem(ModBlocks.TEMPLATE_CRATE);
        blockWithItem(ModBlocks.SAFE);
        fixedColumnBlock(ModBlocks.MASS_STORAGE_UNIT);
        fixedColumnBlock(ModBlocks.AUTOMATIC_CRAFTING_TABLE);
        blockWithItem(ModBlocks.BURNER_PRESS_PREHEATER);
        doorBlockWithRenderType(((DoorBlock) ModBlocks.BUNKER_DOOR.get()), modLoc("block/door_bunker_lower"), modLoc("block/door_bunker_upper"), "cutout");
        doorBlockWithRenderType(((DoorBlock) ModBlocks.DOOR_METAL.get()), modLoc("block/door_metal_lower"), modLoc("block/door_metal_upper"), "solid");

        stairsBlock(((StairBlock) ModBlocks.CONCRETE_STAIRS.get()), blockTexture(Blocks.LIGHT_GRAY_CONCRETE));
        stairsBlock(((StairBlock) ModBlocks.CRACKED_CONCRETE_BRICK_STAIRS.get()), blockTexture(ModBlocks.CRACKED_CONCRETE_BRICKS.get()));
        stairsBlock(((StairBlock) ModBlocks.CONCRETE_BRICK_STAIRS.get()), blockTexture(ModBlocks.CONCRETE_BRICKS.get()));
        stairsBlock(((StairBlock) ModBlocks.LIGHTSTONE_TILE_STAIRS.get()), blockTexture(ModBlocks.LIGHTSTONE_TILE.get()));
        stairsBlock(((StairBlock) ModBlocks.LIGHTSTONE_BRICK_STAIRS.get()), blockTexture(ModBlocks.LIGHTSTONE_BRICKS.get()));

        slabBlock(((SlabBlock) ModBlocks.CONCRETE_SLAB.get()), blockTexture(Blocks.LIGHT_GRAY_CONCRETE), blockTexture(Blocks.LIGHT_GRAY_CONCRETE));
        slabBlock(((SlabBlock) ModBlocks.LIGHTSTONE_TILE_SLAB.get()), blockTexture(ModBlocks.LIGHTSTONE_TILE.get()), blockTexture(ModBlocks.LIGHTSTONE_TILE.get()));
        slabBlock(((SlabBlock) ModBlocks.LIGHTSTONE_BRICK_SLAB.get()), blockTexture(ModBlocks.LIGHTSTONE_BRICKS.get()), blockTexture(ModBlocks.LIGHTSTONE_BRICKS.get()));
        slabBlock(((SlabBlock) ModBlocks.CONCRETE_BRICK_SLAB.get()),
                blockTexture(ModBlocks.CONCRETE_BRICKS.get()),
                blockTexture(ModBlocks.CONCRETE_BRICKS.get()));
        slabBlock(((SlabBlock) ModBlocks.BROKEN_CONCRETE_BRICK_SLAB.get()), blockTexture(ModBlocks.BROKEN_CONCRETE_BRICKS.get()), blockTexture(ModBlocks.BROKEN_CONCRETE_BRICKS.get()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void fixedColumnBlock(RegistryObject<Block> block) {
        ResourceLocation side = modLoc("block/" + block.getId().getPath() + "_side");
        ResourceLocation top = modLoc("block/" + block.getId().getPath() + "_top");

        simpleBlock(block.get(), models().cubeColumn(block.getId().getPath(), side, top));
        simpleBlockItem(block.get(), models().getExistingFile(block.getId()));
    }
}
