package net.StrayBead.hbm_ntm.fluid;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModFluidTypes {
    public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, HBMNTM.MOD_ID);
    public static final RegistryObject<FluidType> STEAM_TYPE = REGISTRY.register("steam", () -> new SteamFluidType());
    public static final RegistryObject<FluidType> OIL_TYPE = REGISTRY.register("crude_oil", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "oil", 0));
    public static final RegistryObject<FluidType> CORIUM_TYPE = REGISTRY.register("corium", () -> new CoriumFluidType());
    public static final RegistryObject<FluidType> CARBON_DIOXIDE_TYPE = REGISTRY.register("carbon_dioxide", () -> new CarbonDioxideFluidType());
    public static final RegistryObject<FluidType> HOT_CRUDE_OIL_TYPE = REGISTRY.register("hot_crude_oil", () -> new HotCrudeOilFluidType());
    public static final RegistryObject<FluidType> HEAVY_OIL_TYPE = REGISTRY.register("heavy_oil", () -> new HeavyOilFluidType());
    public static final RegistryObject<FluidType> NAPHTHA_TYPE = REGISTRY.register("naphtha", () -> new NaphthaFluidType());
    public static final RegistryObject<FluidType> LIGHT_OIL_TYPE = REGISTRY.register("light_oil", () -> new LightOilFluidType());
    public static final RegistryObject<FluidType> PETROLEUM_GAS_TYPE = REGISTRY.register("petroleum_gas", () -> new PetroleumGasFluidType());
    public static final RegistryObject<FluidType> BITUMEN_TYPE = REGISTRY.register("bitumen", () -> new BitumenFluidType());
    public static final RegistryObject<FluidType> DIESEL_TYPE = REGISTRY.register("diesel", () -> new DieselFluidType());
    public static final RegistryObject<FluidType> HEATING_OIL_TYPE = REGISTRY.register("heating_oil", () -> new HeatingOilFluidType());
    public static final RegistryObject<FluidType> INDUSTRIAL_OIL_TYPE = REGISTRY.register("industrial_oil", () -> new IndustrialOilFluidType());
    public static final RegistryObject<FluidType> KEROSENE_TYPE = REGISTRY.register("kerosene", () -> new KeroseneFluidType());
    public static final RegistryObject<FluidType> CRACKED_OIL_TYPE = REGISTRY.register("cracked_oil", () -> new CrackedOilFluidType());
    public static final RegistryObject<FluidType> AROMATIC_HYDROCARBONS_TYPE = REGISTRY.register("aromatic_hydrocarbons", () -> new AromaticHydrocarbonsFluidType());
    public static final RegistryObject<FluidType> UNSATURATED_HYDROCARBONS_TYPE = REGISTRY.register("unsaturated_hydrocarbons", () -> new UnsaturatedHydrocarbonsFluidType());
    public static final RegistryObject<FluidType> NATURAL_GAS_TYPE = REGISTRY.register("natural_gas", () -> new NaturalGasFluidType());
    public static final RegistryObject<FluidType> VACUUM_LIGHT_OIL = REGISTRY.register("vacuum_light_oil", () -> new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "vacuum_light_oil", 0));
    public static final RegistryObject<FluidType> HEAVY_HEATING_OIL = REGISTRY.register("heavy_heating_oil", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "heavy_heating_oil", 0));
    public static final RegistryObject<FluidType> REFORMATE_GAS = REGISTRY.register("reformate_gas", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "reformate_gas", 0));
    public static final RegistryObject<FluidType> VACUUM_HEAVY_OIL = REGISTRY.register("vacuum_heavy_oil", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "vacuum_heavy_oil", 0));
    public static final RegistryObject<FluidType> SOUR_GAS = REGISTRY.register("sour_gas", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "sour_gas", 0));
    public static final RegistryObject<FluidType> LIQUID_HYDROGEN = REGISTRY.register("liquid_hydrogen", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "liquid_hydrogen", 0));
    public static final RegistryObject<FluidType> MERCURY = REGISTRY.register("mercury", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "mercury", 0));
    public static final RegistryObject<FluidType> MOLTEN_STEEL = REGISTRY.register("molten_steel", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "molten_steel", 0));
    public static final RegistryObject<FluidType> MOLTEN_COPPER = REGISTRY.register("molten_copper", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "molten_copper", 0));
    public static final RegistryObject<FluidType> MOLTEN_IRON = REGISTRY.register("molten_iron", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "molten_iron", 0));
    public static final RegistryObject<FluidType> MOLTEN_GOLD = REGISTRY.register("molten_gold", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "molten_gold", 0));
    public static final RegistryObject<FluidType> MOLTEN_ALUMINUM = REGISTRY.register("molten_aluminum", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "molten_aluminum", 0));
    public static final RegistryObject<FluidType> HEAVY_WATER = REGISTRY.register("heavy_water", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "heavy_water", 0));
    public static final RegistryObject<FluidType> REFORMATE = REGISTRY.register("reformate", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "reformate", 0));
    public static final RegistryObject<FluidType> MOLTEN_REDSTONE = REGISTRY.register("molten_redstone", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "molten_redstone", 0));
    public static final RegistryObject<FluidType> MOLTEN_FUEL = REGISTRY.register("molten_fuel", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "molten_fuel", 0));
    public static final RegistryObject<FluidType> COKER_OIL = REGISTRY.register("coker_oil", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "coker_oil", 0));
    public static final RegistryObject<FluidType> ORE_SLOP = REGISTRY.register("ore_slop", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "ore_slop", 0));
    public static final RegistryObject<FluidType> VITROIL = REGISTRY.register("vitroil", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "vitroil", 0));
    public static final RegistryObject<FluidType> SULFURIC_ACID = REGISTRY.register("sulfuric_acid", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "sulfuric_acid", 0));
    public static final RegistryObject<FluidType> CHLORINE_GAS = REGISTRY.register("chlorine_gas_liquid", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "chlorine_gas_liquid", 0));
    public static final RegistryObject<FluidType> LIQUID_OXYGEN = REGISTRY.register("liquid_oxygen", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "liquid_oxygen", 0));
    public static final RegistryObject<FluidType> DEUTERIUM = REGISTRY.register("deuterium", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "deuterium", 0));
    public static final RegistryObject<FluidType> SYNGAS = REGISTRY.register("syngas", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "syngas", 0));
    public static final RegistryObject<FluidType> DESULFURIZED_CRUDE_OIL = REGISTRY.register("desulfurized_crude_oil", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "desulfurized_crude_oil", 0));
    public static final RegistryObject<FluidType> DEUTERATED_HYDROCARBON = REGISTRY.register("deuterated_hydrocarbon", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "deuterated_hydrocarbon", 0));
    public static final RegistryObject<FluidType> FRACKING_SOLUTION = REGISTRY.register("fracking_solution", () ->
            new GenericFluidType(FluidType.Properties.create().density(-100).viscosity(100).canSwim(false), "fracking_solution", 0));
}
