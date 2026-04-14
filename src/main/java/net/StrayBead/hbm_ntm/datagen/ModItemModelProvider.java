package net.StrayBead.hbm_ntm.datagen;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, HBMNTM.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.DESH_HAND_DRILL);
        simpleItem(ModItems.METAL_DETECTOR);
        simpleItem(ModItems.REDCOPPER);
        simpleItem(ModItems.SEPARATED_URANIUM_BEDROCK_ORE);
        simpleItem(ModItems.TUNGSTEN_BOLT);
        simpleItem(ModItems.PURIFIED_URANIUM_BEDROCK_ORE);
        simpleItem(ModItems.FLAT_STAMP);
        simpleItem(ModItems.NITRATED_URANIUM_BEDROCK_ORE);
        simpleItem(ModItems.THERMOELECTRIC_ELEMENT);
        simpleItem(ModItems.STEEL_SHREDDER_BLADES);
        simpleItem(ModItems.OIL_RESERVOIR_DETECTOR);
        simpleItem(ModItems.CORRUPTED_BROADCASTER);
        simpleItem(ModItems.SUIT_BATTERY);
        simpleItem(ModItems.TRANSITION_SEAL);
        simpleItem(ModItems.REDSTONE_INGOT);
        simpleItem(ModItems.CRYOLITE_CHUNK);
        simpleItem(ModItems.POLYMER_BAR);
        simpleItem(ModItems.BAKELITE_BAR);
        simpleItem(ModItems.STEEL_SHELL);
        simpleItem(ModItems.TINY_PILE_OF_LITHIUM_POWDER);
        simpleItem(ModItems.QUARTZ_POWDER);
        simpleItem(ModItems.RUBBER_BAR);
        simpleItem(ModItems.STEEL_PIPE);
        simpleItem(ModItems.MINECRAFT_GRADE_COPPER_WIRE);
        simpleItem(ModItems.CABLE_DRUM);
        simpleItem(ModItems.WIRE_STAMP);
        simpleItem(ModItems.COPPER_COIL);
        simpleItem(ModItems.STEEL_SPHERE);
        simpleItem(ModItems.INFINITE_FLUID_BARREL);
        simpleItem(ModItems.ELECTRIC_MOTOR);
        simpleItem(ModItems.REDSTONE_CRYSTALS);
        simpleItem(ModItems.DROP_OF_MERCURY);
        simpleItem(ModItems.COAL_POWDER);
        simpleItem(ModItems.IRON_POWDER);
        simpleItem(ModItems.LEAD_INGOT);
        simpleItem(ModItems.INTEGRATED_CIRCUIT_BOARD);
        simpleItem(ModItems.CLAY_CATALYST);
        simpleItem(ModItems.STEEL_PEDESTAL);
        simpleItem(ModItems.AIRSTRIKE_DESIGNATOR);
        simpleItem(ModItems.RARE_EARTH_CRYSTALS);
        simpleItem(ModItems.DESH_BLEND);
        simpleItem(ModItems.DESHREADY_BLEND);
        simpleItem(ModItems.FLAT_STEEL_CASING);
        simpleItem(ModItems.STEEL_TANK);
        simpleItem(ModItems.ANALOG_CIRCUIT_BOARD);
        simpleItem(ModItems.COPPER_PLATE);
        simpleItem(ModItems.INSULATOR);
        simpleItem(ModItems.STEEL_BEAM);
        simpleItem(ModItems.STEEL_PLATE);
        simpleItem(ModItems.MOTOR);
        simpleItem(ModItems.GOLD_WIRE);
        simpleItem(ModItems.MILITARY_GRADE_CIRCUIT_BOARD);
        simpleItem(ModItems.INDUSTRIAL_GRADE_COPPER);
        simpleItem(ModItems.ADVANCED_ALLOY_INGOT);
        simpleItem(ModItems.MINECRAFT_GRADE_COPPER);
        simpleItem(ModItems.STEEL);
        simpleItem(ModItems.TITANIUM_INGOT);
        simpleItem(ModItems.THORIUM_INGOT);
        simpleItem(ModItems.URANIUM_ROD);
        simpleItem(ModItems.CLEANED_URANIUM_BEDROCK_ORE);
        simpleItem(ModItems.URANIUM_DUST);
        simpleItem(ModItems.CENTRIFUGED_URANIUM_BEDROCK_ORE);
        simpleItem(ModItems.URANIUM_BEDROCK_ORE);
        simpleItem(ModItems.TUNGSTEN_INGOT);
        simpleItem(ModItems.URANIUM_ROD_NEUTRON_SOURCE);
        simpleItem(ModItems.MACHINE_TEMPLATE_FOLDER);
        simpleItem(ModItems.RADIATION_MEASURER);
        simpleItem(ModItems.ENERGY_INJECTOR);
        simpleItem(ModItems.FLUID_MEASURER);
        simpleItem(ModItems.FLUID_INJECTOR);
        simpleItem(ModItems.GEIGER_COUNTER);
        simpleItem(ModItems.NUKE_DETONATOR);
        simpleItem(ModItems.LEAD_DUST);
        simpleItem(ModItems.FLUORITE);
        simpleItem(ModItems.DETONATOR);
        simpleItem(ModItems.RAW_BERYLLIUM);
        simpleItem(ModItems.INFINITE_BATTERY);
        simpleItem(ModItems.BERYLLIUM_INGOT);
        simpleItem(ModItems.SCREWDRIVER);


        simpleItem(ModItems.MAGNETIZED_TUNGSTEN_INGOT);
        simpleItem(ModItems.CMB_STEEL_INGOT);
        simpleItem(ModItems.HIGH_SPEED_STEEL_INGOT);
        simpleItem(ModItems.TECHNETIUM_99_INGOT);
        simpleItem(ModItems.TECHNETIUM_STEEL_INGOT);
        simpleItem(ModItems.POLYMER_BAR);
        simpleItem(ModItems.DESH_INGOT);
        simpleItem(ModItems.SATURNITE_INGOT);
        simpleItem(ModItems.STARMETAL_INGOT);
        simpleItem(ModItems.EUPHEMIUM_INGOT);
        simpleItem(ModItems.DINEUTRONIUM_INGOT);
        simpleItem(ModItems.BISMUTH_INGOT);
        simpleItem(ModItems.ZIRCONIUM_CUBE);
        simpleItem(ModItems.URANIUM_233_INGOT);
        simpleItem(ModItems.URANIUM_235_INGOT);
        simpleItem(ModItems.PLUTONIUM_INGOT);
        simpleItem(ModItems.RING_COIL);
        simpleItem(ModItems.CAST_STEEL_PLATE);
        simpleItem(ModItems.LONG_RANGE_TARGET_DESIGNATOR);
        simpleItem(ModItems.SIM_CARD);
        simpleItem(ModItems.SIM_CARD_ACTIVATOR);
        simpleItem(ModItems.INCENDIARY_MISSILE);
        simpleItem(ModItems.ANTI_BALLISTIC_MISSILE);
        simpleItem(ModItems.REACTOR_GRADE_PLUTONIUM_INGOT);

        simpleItem(ModItems.AMERICIUM_242_INGOT);
        simpleItem(ModItems.REACTOR_GRADE_AMERICIUM_INGOT);
        simpleItem(ModItems.INGOT_OF_URANIUM_FUEL);
        simpleItem(ModItems.INGOT_OF_PLUTONIUM_FUEL);
        simpleItem(ModItems.SHORT_RANGE_TARGET_DESIGNATOR);
        simpleItem(ModItems.HIGH_EXPLOSIVE_MISSILE);
        simpleItem(ModItems.NEPTUNIUM_FUEL_INGOT);
        simpleItem(ModItems.INGOT_OF_MOX_FUEL);
        simpleItem(ModItems.INGOT_OF_AMERICIUM_FUEL);
        simpleItem(ModItems.INGOT_OF_THORIUM_FUEL);
        simpleItem(ModItems.BORON_INGOT);
        simpleItem(ModItems.SMORE_INGOT);
        simpleItem(ModItems.NIOBIUM_INGOT);
        simpleItem(ModItems.NEODYMIUM_INGOT);
        simpleItem(ModItems.BROMINE_INGOT);
        simpleItem(ModItems.COAL_TAR);
        simpleItem(ModItems.FAT_MAN_DROP_LOCATION_DESIGNATOR);
        simpleItem(ModItems.INFINITE_WATER_TANK);
        simpleItem(ModItems.PWR_CONTROLLER_LINKING_DEVICE);
        simpleItem(ModItems.ALUMINUM_INGOT);
        simpleItem(ModItems.FIREBRICK);
        simpleItem(ModItems.ALUMINUM_PLATE);
        simpleItem(ModItems.CAPACITOR);
        simpleItem(ModItems.FIRECLAY);
        simpleItem(ModItems.VACUUM_TUBE);
        simpleItem(ModItems.CARBON_WIRE);
        simpleItem(ModItems.SCHRABIDIUM_WIRE);
        simpleItem(ModItems.PRINTED_CIRCUIT_BOARD);
        simpleItem(ModItems.COPPER_WIRE);
        simpleItem(ModItems.TUNGSTEN_WIRE);
        simpleItem(ModItems.ALUMINUM_WIRE);
        simpleItem(ModItems.LEAD_WIRE);
        simpleItem(ModItems.SCHRABIDIUM_INGOT);
        simpleItem(ModItems.ZIRCONIUM_WIRE);
        simpleItem(ModItems.STEEL_WIRE);
        simpleItem(ModItems.ADVANCED_ALLOY_WIRE);
        simpleItem(ModItems.MAGNETIZED_TUNGSTEN_WIRE);
        simpleItem(ModItems.STEEL_PICKAXE);
        simpleItem(ModItems.TITANIUM_PICKAXE);
        simpleItem(ModItems.COBALT_PICKAXE);
        simpleItem(ModItems.ADVANCED_ALLOY_PICKAXE);
        simpleItem(ModItems.WORKER_ALLOY_PICKAXE);
        simpleItem(ModItems.CMB_STEEL_PICKAXE);
        simpleItem(ModItems.SCHRABIDIUM_PICKAXE);
        simpleItem(ModItems.SULFUR);
        simpleItem(ModItems.STARMETAL_PICKAXE);
        simpleItem(ModItems.COMBUSTION_ENGINE_PISTON);
        simpleItem(ModItems.STEEL_PISTON_SET);
        simpleItem(ModItems.HIGH_SPEED_STEEL_PISTON_SET);
        simpleItem(ModItems.DESH_PISTON_SET);
        simpleItem(ModItems.STARMETAL_PISTON_SET);
        simpleItem(ModItems.STEEL_DRILLBIT);
        simpleItem(ModItems.HIGH_SPEED_STEEL_DRILLBIT);
        simpleItem(ModItems.DESH_DRILLBIT);
        simpleItem(ModItems.TECHNETIUM_STEEL_DRILLBIT);
        simpleItem(ModItems.NUCLEAR_MISSILE);
        simpleItem(ModItems.MICROCHIP);
        simpleItem(ModItems.PRINTED_SILICON_WAFER);
        simpleItem(ModItems.CIRCUIT_STAMP);
        simpleItem(ModItems.FERROURANIUM_DRILLBIT);
        simpleItem(ModItems.SILICON_WAFER);
        simpleItem(ModItems.SILICON_NUGGET);
        simpleItem(ModItems.SILICON_BOULE);
        simpleItem(ModItems.EMPTY_CELL);
        simpleItem(ModItems.URANIUM_HEXAFLUORIDE_CELL);
        simpleItem(ModItems.PLUTONIUM_HEXAFLUORIDE_CELL);
        simpleItem(ModItems.DEUTERIUM_CELL);
        simpleItem(ModItems.TRITIUM_CELL);
        simpleItem(ModItems.SCHRABIDIUM_TRISULFIDE_CELL);
        simpleItem(ModItems.ANTIMATTER_CELL);
        simpleItem(ModItems.ANTISCHRABIDIUM_CELL);
        simpleItem(ModItems.GASEOUS_BALEFIRE_CELL);
        simpleItem(ModItems.BLANK_FOUNDRY_MOLD);
        simpleItem(ModItems.NUGGET_FOUNDRY_MOLD);
        simpleItem(ModItems.BILLET_FOUNDRY_MOLD);
        simpleItem(ModItems.INGOT_FOUNDRY_MOLD);
        simpleItem(ModItems.PLATE_FOUNDRY_MOLD);
        simpleItem(ModItems.WIRE_FOUNDRY_MOLD);
        simpleItem(ModItems.CAST_PLATE_FOUNDRY_MOLD);
        simpleItem(ModItems.DENSE_WIRE_FOUNDRY_MOLD);
        simpleItem(ModItems.BLADE_FOUNDRY_MOLD);
        simpleItem(ModItems.SHREDDER_BLADE_FOUNDRY_MOLD);
        simpleItem(ModItems.SHELL_FOUNDRY_MOLD);
        simpleItem(ModItems.PIPE_FOUNDRY_MOLD);
        simpleItem(ModItems.CAST_IRON_PLATE);
        simpleItem(ModItems.DYATLOV_INSTANT_MELTDOWN_APPLICATOR);
        simpleItem(ModItems.IRON_PLATE);
        simpleItem(ModItems.STEEL_BOLT);
        simpleItem(ModItems.LIGNITE);
        simpleItem(ModItems.COAL_COKE);
        simpleItem(ModItems.LIGNITE_COKE);
        simpleItem(ModItems.PETROLEUM_COKE);
        simpleItem(ModItems.INFERNAL_COAL);
        simpleItem(ModItems.GUIDE_BOOK);
        simpleItem(ModItems.CRYSTALLINE_IRON_FRAGMENT);
        simpleItem(ModItems.CRYSTALLINE_COPPER_FRAGMENT);
        simpleItem(ModItems.CRYSTALLINE_LITHIUM_FRAGMENT);
        simpleItem(ModItems.CRYSTALLINE_SILICON_FRAGMENT);
        simpleItem(ModItems.CRYSTALLINE_LEAD_FRAGMENT);
        simpleItem(ModItems.CRYSTALLINE_TITANIUM_FRAGMENT);
        simpleItem(ModItems.CRYSTALLINE_ALUMINUM_FRAGMENT);
        simpleItem(ModItems.CRYSTALLINE_SULFUR_FRAGMENT);
        simpleItem(ModItems.CRYSTALLINE_CALCIUM_FRAGMENT);
        simpleItem(ModItems.CRYSTALLINE_BISMUTH_FRAGMENT);
        simpleItem(ModItems.CRYSTALLINE_RADIUM_FRAGMENT);
        simpleItem(ModItems.CRYSTALLINE_TECHNETIUM_FRAGMENT);
        simpleItem(ModItems.CRYSTALLINE_POLONIUM_FRAGMENT);
        simpleItem(ModItems.CRYSTALLINE_URANIUM_FRAGMENT);
        simpleItem(ModItems.CONTROL_UNIT);
        simpleItem(ModItems.TANTALIUM_CAPACITOR);
        simpleItem(ModItems.CONTROL_UNIT_CASING);
        simpleItem(ModItems.SPEED_UPGRADE);
        simpleItem(ModItems.CATHODE_RAY_TUBE);
        simpleItem(ModItems.URANIUM_INGOT);
        simpleItem(ModItems.EMPTY_PARTICLE_CAPSULE);
        simpleItem(ModItems.HYDROGEN_ION_CAPSULE);
        simpleItem(ModItems.SPARKTICLE_CAPSULE);
        simpleItem(ModItems.DARK_MATTER_CAPSULE);
        simpleItem(ModItems.STRANGE_QUARK_CAPSULE);
        simpleItem(ModItems.LEAD_ION_CAPSULE);
        simpleItem(ModItems.MUON_CAPSULE);
        simpleItem(ModItems.THE_DIGAMMA_PARTICLE);
        simpleItem(ModItems.ATOMIC_CLOCK);
        simpleItem(ModItems.VERSATILE_INTEGRATED_CIRCUIT);
        simpleItem(ModItems.SOLID_STATE_QUANTUM_PROCESSOR);
        simpleBlockItem(ModBlocks.BUNKER_DOOR);
        simpleBlockItem(ModBlocks.DOOR_METAL);

        evenSimplerBlockItem(ModBlocks.CONCRETE_STAIRS);
        evenSimplerBlockItem(ModBlocks.CRACKED_CONCRETE_BRICK_STAIRS);
        evenSimplerBlockItem(ModBlocks.CONCRETE_BRICK_STAIRS);
        evenSimplerBlockItem(ModBlocks.CONCRETE_SLAB);
        evenSimplerBlockItem(ModBlocks.CONCRETE_BRICK_SLAB);
        evenSimplerBlockItem(ModBlocks.BROKEN_CONCRETE_BRICK_SLAB);

        for (RegistryObject<Item> item : ModItems.FLUID_IDENTIFIERS.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/fluid_identifier_base"))
                    .texture("layer1", new ResourceLocation(HBMNTM.MOD_ID, "item/fluid_identifier_center"));
        }

        for (RegistryObject<Item> item : ModItems.BEDROCK_ORE_FRAGMENTS.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/bedrock_ore_fragment_base"))
                    .texture("layer1", new ResourceLocation(HBMNTM.MOD_ID, "item/bedrock_ore_fragment_center"));
        }

        for (RegistryObject<Item> item : ModItems.POWDERS.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/powder_base"))
                    .texture("layer1", new ResourceLocation(HBMNTM.MOD_ID, "item/powder_center"));
        }

        for (RegistryObject<Item> item : ModItems.BILLETS.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/billet_base"))
                    .texture("layer1", new ResourceLocation(HBMNTM.MOD_ID, "item/billet_center"));
        }

        for (RegistryObject<Item> item : ModItems.FLUID_BARRELS.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/fluid_barrel_base"))
                    .texture("layer1", new ResourceLocation(HBMNTM.MOD_ID, "item/fluid_barrel_center"));
        }

        for (RegistryObject<Item> item : ModItems.HAZARDOUS_MATERIAL_TANKS.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/hazardous_material_tank_base"))
                    .texture("layer1", new ResourceLocation(HBMNTM.MOD_ID, "item/hazardous_material_tank_center"));
        }

        for (RegistryObject<Item> item : ModItems.RTG_PELLETS.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/rtg_pellet"))
                    .texture("layer1", new ResourceLocation(HBMNTM.MOD_ID, "item/rtg_pellet"));
        }

        for (RegistryObject<Item> item : ModItems.CANISTERS.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/canister_base"))
                    .texture("layer1", new ResourceLocation(HBMNTM.MOD_ID, "item/canister_center"));
        }

        for (RegistryObject<Item> item : ModItems.PACKAGED_FLUIDS.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/packaged_fluid_base"))
                    .texture("layer1", new ResourceLocation(HBMNTM.MOD_ID, "item/packaged_fluid_center"));
        }

        for (RegistryObject<Item> item : ModItems.FLUID_TANK_NAMES.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/fluid_tank_base"))
                    .texture("layer1", new ResourceLocation(HBMNTM.MOD_ID, "item/fluid_tank_center"));
        }

        for (RegistryObject<Item> item : ModItems.ASSEMBLY_TEMPLATES.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/assembly_template_base"));
        }

        for (RegistryObject<Item> item : ModItems.CRUCIBLE_TEMPLATES.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/crucible_template_base"));
        }

        for (RegistryObject<Item> item : ModItems.CHEMISTRY_TEMPLATES.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/chemistry_template_base"));
        }

        for (RegistryObject<Item> item : ModItems.BEDROCK_ORE_NAMES.values()) {
            withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                    .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "item/bedrock_ore_base"))
                    .texture("layer1", new ResourceLocation(HBMNTM.MOD_ID, "item/bedrock_ore_center"));
        }

        withExistingParent(ModBlocks.UNIVERSAL_FLUID_DUCT.getId().getPath(), new ResourceLocation("item/generated"))
                .texture("layer0", new ResourceLocation(HBMNTM.MOD_ID, "block/universal_fluid_duct_base"))
                .texture("layer1", new ResourceLocation(HBMNTM.MOD_ID, "block/universal_fluid_duct_center"));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(HBMNTM.MOD_ID, "item/" + item.getId().getPath()));
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(HBMNTM.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  new ResourceLocation(HBMNTM.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  new ResourceLocation(HBMNTM.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  new ResourceLocation(HBMNTM.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(HBMNTM.MOD_ID,"item/" + item.getId().getPath()));
    }
}
