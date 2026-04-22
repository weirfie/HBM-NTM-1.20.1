package net.StrayBead.hbm_ntm.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.StrayBead.hbm_ntm.event.FluidColorRegistry;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.StrayBead.hbm_ntm.network.ModMessages;
import net.StrayBead.hbm_ntm.network.packet.PacketBuyCrucibleTemplate;
import net.StrayBead.hbm_ntm.network.packet.PacketBuyIdentifier;
import net.StrayBead.hbm_ntm.network.packet.PacketBuyTank;
import net.StrayBead.hbm_ntm.network.packet.PacketBuyTemplate;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MachineTemplateFolderScreen extends Screen {
    private int currentPage = 0;
    private int maxPages = 1;
    private final int bgWidth = 256;
    private final int bgHeight = 180;

    private EditBox searchBox;
    private String lastSearch = "";

    private List<String> filteredItems;
    private String currentCategoryType = "identifier";

    public MachineTemplateFolderScreen() {
        super(Component.literal("Machine Template Folder"));
    }

    private static final String[] FLUID_TANKS = {
            "compressed_air", "water", "heavy_water", "hot_heavy_water", "lava", "steam", "dense_steam", "super_dense_steam",
            "ultra_dense_steam", "carbon_dioxide", "coolant", "hot_coolant", "perfluoromethyl", "cold_perfluoromethyl", "hot_perfluoromethyl",
            "cryogel", "mug_root_beer", "hot_mug_root_beer", "blood", "hot_blood", "liquid_sodium", "hot_liquid_sodium", "liquid_lead",
            "liquid_thorium_salt", "hot_liquid_thorium_salt", "depleted_liquid_thorium_salt", "liquid_hydrogen", "deuterium",
            "tritium", "helium_3", "helium_4", "liquid_oxygen", "xenon_gas", "chlorine_gas", "mercury", "crude_oil", "desulfurized_crude_oil",
            "cracked_oil", "desulfurized_cracked_oil", "coal_oil", "coker_oil", "hot_crude_oil", "desulfurized_hot_crude_oil", "hot_cracked_oil",
            "desulfurized_hot_cracked_oil", "heavy_oil", "vacuum_heavy_oil", "naphtha", "desulfurized_naphtha", "cracked_naphtha", "coker_naphtha",
            "reformate", "light_oil", "desulfurized_light_oil", "cracked_light_oil", "vacuum_light_oil", "bitumen", "industrial_oil", "heating_oil",
            "heavy_heating_oil", "reclaimed_industrial_oil", "engine_lubricant", "natural_gas", "coker_gas", "petroleum_gas", "sour_gas",
            "lpg", "syngas", "oxyhydrogen", "aromatic_hydrocarbons", "unsaturated_hydrocarbons", "reformate_gas", "diesel", "high_cetane_diesel",
            "cracked_diesel", "high_cetane_cracked_diesel", "kerosene", "jet_fuel", "petroil", "leaded_petroil", "gasoline", "leaded_gasoline",
            "coal_gasoline", "leaded_coal_gasoline", "coal_tar_gasoline", "wood_oil", "biogas", "biofuel", "ethanol", "fish_oil", "sunflower_seed_oil",
            "bf_rocket_fuel", "salient_green", "seeding_slurry", "colloid", "vitriol", "ore_slop", "ionic_gel", "hydrogen_peroxide", "sulfuric_acid",
            "nitric_acid", "solvent", "high_performance_solvent", "schrabidic_acid", "uranium_hexafluoride", "plutonium_hexafluoride", "schrabidium_trisulfide",
            "poisonous_mud", "fullerene_solution", "dissolved_egg", "cholesterol_solution", "sodium_aluminate", "bauxite_solution", "alumina",
            "liquid_concrete", "fracking_solution", "lye", "phosgene", "mustard_gas", "estradiol_solution", "nitroglycerin", "antimatter", "antishrabidium",
            "experience_juice", "ender_juice", "stellar_flux", "booster_pheromone", "modified_booster_pheromone", "custom_fluid_demo"
    };

    private static final String[] ASSEMBLY_TEMPLATE_NAMES = {
            "iron_plate", "gold_plate", "titanium_plate", "aluminum_plate", "steel_plate", "lead_plate", "copper_plate", "advanced_alloy_plate",
            "schrabidium_plate", "cmb_steel_plate", "saturnite_plate", "mixed_plate", "hazmat_cloth", "fire_proximity_cloth",
            "activated_carbon_filter", "centrifuge_element", "breeding_reactor_core", "rtg_unit", "titanium_drill", "entanglement_kit",
            "dysfunctional_nuclear_reactor", "small_missile_assembly", "small_warhead", "medium_warhead", "large_warhead", "small_incendiary_warhead",
            "medium_incendiary_warhead", "crucible", "heating_oven", "firebox", "large_incendiary_warhead", "small_cluster_warhead", "medium_cluster_warhead",
            "large_cluster_warhead", "small_bunker_buster_warhead", "medium_bunker_buster_warhead", "large_bunker_buster_warhead",
            "nuclear_warhead", "thermonuclear_warhead", "tectonic_warhead", "stealth_missile", "explosive_pellets", "lead_pellets",
            "magnetron", "redcoil_capacitor", "soldering_station", "box_of_lithium_dust", "box_of_beryllium_dust", "box_of_carbon_dust", "box_of_copper_dust",
            "box_of_plutonium_dust", "thermoelectric_element", "angry_metal", "meteorite_block", "cmb_steel_tile", "reinforced_cmb_bricks",
            "silo_hatch_frame", "silo_hatch_opener", "centrifuge", "gas_centrifuge", "diesel_generator", "rt_generator", "energy_storage_block",
            "spark_energy_storage_block", "shredder", "oil_derrick", "electric_arc_furnace", "pumpjack", "flare_stock", "coker_unit", "oil_refinery", "electric_press",
            "chemical_plant", "ore_acidizer", "tank", "catalytic_cracking_tower", "silex", "heavy_magnetic_storage_tank", "mining_laser", "turbofan", "combined_cycle_gas_turbine",
            "teleporter", "schrabidium_transmutation_device", "big_ass_tank", "superconducting_magnet", "central_magnet_piece", "magnet_motor_piece",
            "plasma_heater_component", "watz_reaction_chamber", "watz_reactor_supercooler", "watz_reactor_stability_element", "naval_mine",
            "the_gadget", "ivy_mike", "tsar_bomba", "the_prototype", "the_blue_rinse", "rbmk_structural_column", "custom_nuke", "levitation_bomb", "endothermic_bomb",
            "exothermic_bomb", "flame_frag_grenade", "shrapnel_grenade", "cluster_bomb", "signal_flare", "lightning_bomb", "impulse_grenade",
            "plasma_grenade", "tau_grenade", "schrabidium_grenade", "boiler", "nuka_grenade", "negative_energy_pair_annihilation_grenade"
    };

    private static final String[] CRUCIBLE_TEMPLATE_NAMES = {
            "steel_production", "iron_production_from_hematite", "copper_production_from_malachite", "red_copper_production", "advanced_alloy_production",
            "high_speed_steel_production", "ferrouranium_production", "aluminum_production", "technetium_steel_production", "cadmium_steel_production", "bismuth_bronze_production",
            "arsenic_bronze_production", "cmb_steel_production", "magnetized_tungsten_production", "bscco_production"
    };

    @Override
    protected void init() {
        int x = (this.width - bgWidth) / 2;
        int y = (this.height - bgHeight) / 2;

        // 1. Initialize Search Box
        this.searchBox = new EditBox(this.font, x + 80, y + 152, 100, 12, Component.literal("Search..."));
        this.searchBox.setValue(lastSearch);
        this.searchBox.setResponder(s -> {
            this.lastSearch = s;
            this.currentPage = 0; // Reset to page 1 on search
            this.rebuildWidgets();
        });
        this.addRenderableWidget(this.searchBox);

        // 2. Navigation Buttons
        this.addRenderableWidget(Button.builder(Component.literal("<"), b -> {
            if (currentPage > 0) { currentPage--; this.rebuildWidgets(); }
        }).bounds(x + 20, y + 150, 20, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal(">"), b -> {
            if (currentPage < maxPages - 1) { currentPage++; this.rebuildWidgets(); }
        }).bounds(x + bgWidth - 40, y + 150, 20, 20).build());

        // 3. Category Tabs (Optional but helpful)
        this.addRenderableWidget(Button.builder(Component.literal("ID"), b -> { currentCategoryType = "identifier"; currentPage = 0; this.rebuildWidgets(); })
                .bounds(x - 25, y + 10, 25, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("TK"), b -> { currentCategoryType = "tank"; currentPage = 0; this.rebuildWidgets(); })
                .bounds(x - 25, y + 35, 25, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("TP"), b -> { currentCategoryType = "template"; currentPage = 0; this.rebuildWidgets(); })
                .bounds(x - 25, y + 60, 25, 20).build());
        this.addRenderableWidget(Button.builder(Component.literal("CT"), b -> { currentCategoryType = "crucible"; currentPage = 0; this.rebuildWidgets(); })
                .bounds(x - 25, y + 85, 25, 20).build());
        // 4. Logic to Filter and Paginate
        updateFilteredList();

        int startX = x + 30;
        int startY = y + 35;
        int itemsPerPage = 45;
        int startIndex = currentPage * itemsPerPage;

        for (int i = 0; i < Math.min(itemsPerPage, filteredItems.size() - startIndex); i++) {
            String name = filteredItems.get(startIndex + i);
            int col = i % 9;
            int row = i / 9;

            ItemStack stack = getStackForType(name, currentCategoryType);
            this.addRenderableWidget(Button.builder(Component.literal(""), b -> sendPurchasePacket(name))
                    .bounds(startX + (col * 22), startY + (row * 22), 20, 20)
                    .tooltip(Tooltip.create(stack.getHoverName()))
                    .build());
        }
    }

    private void updateFilteredList() {
        String[] sourceArray = switch (currentCategoryType) {
            case "identifier" -> FluidColorRegistry.getRegisteredFluids().toArray(new String[0]);
            case "tank" -> FLUID_TANKS;
            case "crucible" -> CRUCIBLE_TEMPLATE_NAMES;
            default -> ModItems.ASSEMBLY_TEMPLATE_NAMES;
        };

        filteredItems = Arrays.stream(sourceArray)
                .filter(name -> name.toLowerCase().contains(lastSearch.toLowerCase()))
                .collect(Collectors.toList());

        this.maxPages = (int) Math.ceil(filteredItems.size() / 45.0);
        if (maxPages == 0) maxPages = 1;
    }

    private void sendPurchasePacket(String name) {
        switch (currentCategoryType) {
            case "identifier" -> ModMessages.sendToServer(new PacketBuyIdentifier(name));
            case "tank" -> ModMessages.sendToServer(new PacketBuyTank(name));
            case "template" -> ModMessages.sendToServer(new PacketBuyTemplate(name));
            case "crucible" -> ModMessages.sendToServer(new PacketBuyCrucibleTemplate(name));
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        int x = (this.width - bgWidth) / 2;
        int y = (this.height - bgHeight) / 2;

        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        guiGraphics.fill(x, y, x + bgWidth, y + bgHeight, 0xFF00008B);
        guiGraphics.renderOutline(x, y, bgWidth, bgHeight, 0xFFFFFFFF);

        guiGraphics.drawString(this.font, "Category: " + currentCategoryType.toUpperCase(), x + 30, y + 10, 0xAAAAAA);
        guiGraphics.drawCenteredString(this.font, (currentPage + 1) + "/" + maxPages, x + bgWidth / 2, y + 140, 0xFFFFFF);

        int startIndex = currentPage * 45;
        for (int i = 0; i < Math.min(45, filteredItems.size() - startIndex); i++) {
            String name = filteredItems.get(startIndex + i);
            int col = i % 9;
            int row = i / 9;
            guiGraphics.renderFakeItem(getStackForType(name, currentCategoryType), x + 32 + (col * 22), y + 37 + (row * 22));
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    private ItemStack getStackForType(String name, String type) {
        try {
            return switch (type) {
                case "identifier" -> new ItemStack(ModItems.FLUID_IDENTIFIERS.get(name).get());
                case "tank" -> new ItemStack(ModItems.FLUID_TANK_NAMES.get(name).get());
                case "crucible" -> new ItemStack(ModItems.CRUCIBLE_TEMPLATES.get(name).get());
                default -> new ItemStack(ModItems.ASSEMBLY_TEMPLATES.get(name).get());
            };
        } catch (Exception e) { return ItemStack.EMPTY; }
    }

    @Override public boolean isPauseScreen() { return false; }
}
