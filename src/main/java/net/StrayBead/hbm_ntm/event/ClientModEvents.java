package net.StrayBead.hbm_ntm.event;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.FluidBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.ModBlockEntites;
import net.StrayBead.hbm_ntm.block.custom.render.*;
import net.StrayBead.hbm_ntm.entity.ModEntities;
import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.model.obj.ObjLoader;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = "hbm_ntm", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntites.CONTROL_ROD_TOP.get(),
                ControlRodTopRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntites.BATTERY_SOCKET.get(),
                BatterySocketBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntites.BURNER_PRESS.get(),
                BurnerPressBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntites.SOLDERING_STATION.get(),
                SolderingStationBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntites.SHALLOW_FOUNDRY_BASIN.get(),
                ShallowFoundryBasinRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntites.CRUCIBLE.get(),
                CrucibleRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntites.COATED_FLUID_DUCT.get(), DuctRenderer::new);
        event.registerEntityRenderer(ModEntities.FLYING_TERRAIN.get(), FlyingTerrainRenderer::new);
    }

    @SubscribeEvent
    public static void onModelBaking(net.minecraftforge.client.event.ModelEvent.ModifyBakingResult event) {
        net.minecraft.client.resources.model.ModelResourceLocation loc =
                new net.minecraft.client.resources.model.ModelResourceLocation("hbm_ntm", "paintable_coated_universal_fluid_duct", "");

        var existing = event.getModels().get(loc);
        if (existing != null) {
            event.getModels().put(loc, new DuctBakedModel(existing));
        }
    }

    @SubscribeEvent
    public static void registerGeometryLoaders(ModelEvent.RegisterGeometryLoaders event) {
        event.register("obj", ObjLoader.INSTANCE);
    }

    @SubscribeEvent
    public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.FLUID_IDENTIFIERS.entrySet()) {
            event.register((stack, tintIndex) -> {
                if (tintIndex == 1) {
                    return FluidColorRegistry.getColor(entry.getKey());
                }
                return -1;
            }, entry.getValue().get());
        }

        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.HAZARDOUS_MATERIAL_TANKS.entrySet()) {
            event.register((stack, tintIndex) -> {
                if (tintIndex == 1) {
                    return TankColorRegistry.getColor(entry.getKey());
                }
                return -1;
            }, entry.getValue().get());
        }

        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.FLUID_BARRELS.entrySet()) {
            event.register((stack, tintIndex) -> {
                if (tintIndex == 1) {
                    return TankColorRegistry.getColor(entry.getKey());
                }
                return -1;
            }, entry.getValue().get());
        }

        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.CANISTERS.entrySet()) {
            event.register((stack, tintIndex) -> {
                if (tintIndex == 1) {
                    return CanisterColorRegistry.getColor(entry.getKey());
                }
                return -1;
            }, entry.getValue().get());
        }

        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.PACKAGED_FLUIDS.entrySet()) {
            event.register((stack, tintIndex) -> {
                if (tintIndex == 1) {
                    return TankColorRegistry.getColor(entry.getKey());
                }
                return -1;
            }, entry.getValue().get());
        }

        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.RTG_PELLETS.entrySet()) {
            event.register((stack, tintIndex) -> {
                if (tintIndex == 1) {
                    return RTGPelletColorRegistry.getColor(entry.getKey());
                }
                return -1;
            }, entry.getValue().get());
        }

        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.BEDROCK_ORE_NAMES.entrySet()) {
            event.register((stack, tintIndex) -> {
                if (tintIndex == 1) {
                    return BedrockOreColorRegistry.getColor(entry.getKey());
                }
                return -1;
            }, entry.getValue().get());
        }

        for (Map.Entry<String, RegistryObject<Item>> entry : ModItems.FLUID_TANK_NAMES.entrySet()) {
            event.register((stack, tintIndex) -> {
                if (tintIndex == 1) {
                    return TankColorRegistry.getColor(entry.getKey());
                }
                return -1;
            }, entry.getValue().get());
        }

        event.register((stack, tintIndex) -> {
            if (tintIndex == 1) {
                if (stack.hasTag() && stack.getTag().contains("FluidName")) {
                    String fluidName = stack.getTag().getString("FluidName");
                    return FluidColorRegistry.getColor(fluidName);
                }
                return 0xFFFFFF;
            }
            return -1;
        }, ModBlocks.UNIVERSAL_FLUID_DUCT.get());
    }

    @SubscribeEvent
    public static void onRegisterBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            if (level != null && pos != null) {
                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof FluidBlockEntity fluidBE) {
                    if (tintIndex == 0) {
                        return fluidBE.getFilterColor();
                    }
                }
            }
            return 0xFFFFFF;
        }, ModBlocks.UNIVERSAL_FLUID_DUCT.get());
    }
}