package net.StrayBead.hbm_ntm.fluid;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModFluids {
    public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(ForgeRegistries.FLUIDS, HBMNTM.MOD_ID);
    public static final RegistryObject<FlowingFluid> STEAM = REGISTRY.register("steam", () -> new SteamFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_STEAM = REGISTRY.register("flowing_steam", () -> new SteamFluid.Flowing());
    public static final RegistryObject<FlowingFluid> CRUDE_OIL = register("crude_oil", ModFluidTypes.OIL_TYPE, () -> (LiquidBlock) ModBlocks.OIL.get());
    public static final RegistryObject<FlowingFluid> FLOWING_OIL = REGISTRY.register("flowing_oil", () -> new OilFluid.Flowing());
    public static final RegistryObject<FlowingFluid> CORIUM = REGISTRY.register("corium", () -> new CoriumFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_CORIUM = REGISTRY.register("flowing_corium", () -> new CoriumFluid.Flowing());
    public static final RegistryObject<FlowingFluid> CARBON_DIOXIDE = REGISTRY.register("carbon_dioxide", () -> new CarbonDioxideFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_CARBON_DIOXIDE = REGISTRY.register("flowing_carbon_dioxide", () -> new CarbonDioxideFluid.Flowing());
    public static final RegistryObject<FlowingFluid> HOT_CRUDE_OIL = REGISTRY.register("hot_crude_oil", () -> new HotCrudeOilFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_HOT_CRUDE_OIL = REGISTRY.register("flowing_hot_crude_oil", () -> new HotCrudeOilFluid.Flowing());
    public static final RegistryObject<FlowingFluid> HEAVY_OIL = REGISTRY.register("heavy_oil", () -> new HeavyOilFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_HEAVY_OIL = REGISTRY.register("flowing_heavy_oil", () -> new HeavyOilFluid.Flowing());
    public static final RegistryObject<FlowingFluid> PETROLEUM_GAS = REGISTRY.register("petroleum_gas", () -> new PetroleumGasFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_PETROLEUM_GAS = REGISTRY.register("flowing_petroleum_gas", () -> new PetroleumGasFluid.Flowing());
    public static final RegistryObject<FlowingFluid> NAPHTHA = REGISTRY.register("naphtha", () -> new NaphthaFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_NAPHTHA = REGISTRY.register("flowing_naphtha", () -> new NaphthaFluid.Flowing());
    public static final RegistryObject<FlowingFluid> LIGHT_OIL = REGISTRY.register("light_oil", () -> new LightOilFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_LIGHT_OIL = REGISTRY.register("flowing_light_oil", () -> new LightOilFluid.Flowing());
    public static final RegistryObject<FlowingFluid> BITUMEN = REGISTRY.register("bitumen", () -> new BitumenFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_BITUMEN = REGISTRY.register("flowing_bitumen", () -> new BitumenFluid.Flowing());
    public static final RegistryObject<FlowingFluid> DIESEL = REGISTRY.register("diesel", () -> new DieselFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_DIESEL = REGISTRY.register("flowing_diesel", () -> new DieselFluid.Flowing());
    public static final RegistryObject<FlowingFluid> HEATING_OIL = REGISTRY.register("heating_oil", () -> new HeatingOilFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_HEATING_OIL = REGISTRY.register("flowing_heating_oil", () -> new HeatingOilFluid.Flowing());
    public static final RegistryObject<FlowingFluid> INDUSTRIAL_OIL = REGISTRY.register("industrial_oil", () -> new IndustrialOilFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_INDUSTRIAL_OIL = REGISTRY.register("flowing_industrial_oil", () -> new IndustrialOilFluid.Flowing());
    public static final RegistryObject<FlowingFluid> KEROSENE = REGISTRY.register("kerosene", () -> new KeroseneFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_KEROSENE = REGISTRY.register("flowing_kerosene", () -> new KeroseneFluid.Flowing());
    public static final RegistryObject<FlowingFluid> CRACKED_OIL = REGISTRY.register("cracked_oil", () -> new CrackedOilFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_CRACKED_OIL = REGISTRY.register("flowing_cracked_oil", () -> new CrackedOilFluid.Flowing());
    public static final RegistryObject<FlowingFluid> AROMATIC_HYDROCARBONS = REGISTRY.register("aromatic_hydrocarbons", () -> new AromaticHydrocarbonsFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_AROMATIC_HYDROCARBONS = REGISTRY.register("flowing_aromatic_hydrocarbons", () -> new AromaticHydrocarbonsFluid.Flowing());
    public static final RegistryObject<FlowingFluid> UNSATURATED_HYDROCARBONS = REGISTRY.register("unsaturated_hydrocarbons", () -> new UnsaturatedHydrocarbonsFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_UNSATURATED_HYDROCARBONS = REGISTRY.register("flowing_unsaturated_hydrocarbons", () -> new UnsaturatedHydrocarbonsFluid.Flowing());
    public static final RegistryObject<FlowingFluid> NATURAL_GAS = REGISTRY.register("natural_gas", () -> new NaturalGasFluid.Source());
    public static final RegistryObject<FlowingFluid> FLOWING_NATURAL_GAS = REGISTRY.register("flowing_natural_gas", () -> new NaturalGasFluid.Flowing());
    public static final RegistryObject<FlowingFluid> VACUUM_LIGHT_OIL = register("vacuum_light_oil", ModFluidTypes.VACUUM_LIGHT_OIL, () -> (LiquidBlock) ModBlocks.VACUUM_LIGHT_OIL.get());
    public static final RegistryObject<FlowingFluid> REFORMATE_GAS = register("reformate_gas", ModFluidTypes.REFORMATE_GAS, () -> (LiquidBlock) ModBlocks.REFORMATE_GAS.get());
    public static final RegistryObject<FlowingFluid> HEAVY_HEATING_OIL = register("heavy_heating_oil", ModFluidTypes.HEAVY_HEATING_OIL, () -> (LiquidBlock) ModBlocks.HEAVY_HEATING_OIL.get());
    public static final RegistryObject<FlowingFluid> VACUUM_HEAVY_OIL = register("vacuum_heavy_oil", ModFluidTypes.VACUUM_HEAVY_OIL, () -> (LiquidBlock) ModBlocks.VACUUM_HEAVY_OIL.get());
    public static final RegistryObject<FlowingFluid> SOUR_GAS = register("sour_gas", ModFluidTypes.SOUR_GAS, () -> (LiquidBlock) ModBlocks.SOUR_GAS.get());
    public static final RegistryObject<FlowingFluid> LIQUID_HYDROGEN = register("liquid_hydrogen", ModFluidTypes.LIQUID_HYDROGEN, () -> (LiquidBlock) ModBlocks.LIQUID_HYDROGEN.get());
    public static final RegistryObject<FlowingFluid> MERCURY = register("mercury", ModFluidTypes.MERCURY, () -> (LiquidBlock) ModBlocks.MERCURY.get());
    public static final RegistryObject<FlowingFluid> REFORMATE = register("reformate", ModFluidTypes.REFORMATE, () -> (LiquidBlock) ModBlocks.REFORMATE.get());
    public static final RegistryObject<FlowingFluid> MOLTEN_STEEL = register("molten_steel", ModFluidTypes.MOLTEN_STEEL, () -> (LiquidBlock) ModBlocks.MOLTEN_STEEL.get());
    public static final RegistryObject<FlowingFluid> MOLTEN_COPPER = register("molten_copper", ModFluidTypes.MOLTEN_COPPER, () -> (LiquidBlock) ModBlocks.MOLTEN_COPPER.get());
    public static final RegistryObject<FlowingFluid> MOLTEN_IRON = register("molten_iron", ModFluidTypes.MOLTEN_IRON, () -> (LiquidBlock) ModBlocks.MOLTEN_IRON.get());
    public static final RegistryObject<FlowingFluid> MOLTEN_GOLD = register("molten_gold", ModFluidTypes.MOLTEN_GOLD, () -> (LiquidBlock) ModBlocks.MOLTEN_GOLD.get());
    public static final RegistryObject<FlowingFluid> MOLTEN_ALUMINUM = register("molten_aluminum", ModFluidTypes.MOLTEN_ALUMINUM, () -> (LiquidBlock) ModBlocks.MOLTEN_ALUMINUM.get());
    public static final RegistryObject<FlowingFluid> MOLTEN_FUEL = register("molten_fuel", ModFluidTypes.MOLTEN_FUEL, () -> (LiquidBlock) ModBlocks.MOLTEN_FUEL.get());
    public static final RegistryObject<FlowingFluid> MOLTEN_REDSTONE = register("molten_redstone", ModFluidTypes.MOLTEN_REDSTONE, () -> (LiquidBlock) ModBlocks.MOLTEN_REDSTONE.get());
    public static final RegistryObject<FlowingFluid> HEAVY_WATER = register("heavy_water", ModFluidTypes.HEAVY_WATER, () -> (LiquidBlock) ModBlocks.HEAVY_WATER.get());

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class FluidsClientSideHandler {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ItemBlockRenderTypes.setRenderLayer(STEAM.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_STEAM.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(CRUDE_OIL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_OIL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(CORIUM.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_CORIUM.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(CARBON_DIOXIDE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_CARBON_DIOXIDE.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(HOT_CRUDE_OIL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_HOT_CRUDE_OIL.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(HEAVY_OIL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_HEAVY_OIL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(PETROLEUM_GAS.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_PETROLEUM_GAS.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(NAPHTHA.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_NAPHTHA.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(LIGHT_OIL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_LIGHT_OIL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(BITUMEN.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_BITUMEN.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(DIESEL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_DIESEL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(HEATING_OIL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_HEATING_OIL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(INDUSTRIAL_OIL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_INDUSTRIAL_OIL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(KEROSENE.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_KEROSENE.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(CRACKED_OIL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_CRACKED_OIL.get(), RenderType.solid());
            ItemBlockRenderTypes.setRenderLayer(AROMATIC_HYDROCARBONS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_AROMATIC_HYDROCARBONS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(UNSATURATED_HYDROCARBONS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_UNSATURATED_HYDROCARBONS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(NATURAL_GAS.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FLOWING_NATURAL_GAS.get(), RenderType.translucent());
        }
    }

    private static RegistryObject<FlowingFluid> register(String name, Supplier<FluidType> type, Supplier<LiquidBlock> block) {
        ForgeFlowingFluid.Properties props = new ForgeFlowingFluid.Properties(
                type,
                () -> ForgeRegistries.FLUIDS.getValue(new net.minecraft.resources.ResourceLocation(HBMNTM.MOD_ID, name)),
                () -> ForgeRegistries.FLUIDS.getValue(new net.minecraft.resources.ResourceLocation(HBMNTM.MOD_ID, "flowing_" + name))
        ).block(block).explosionResistance(100f).tickRate(1);

        RegistryObject<FlowingFluid> source = REGISTRY.register(name, () -> new GenericFluid.Source(props));
        REGISTRY.register("flowing_" + name, () -> new GenericFluid.Flowing(props));
        return source;
    }
}
