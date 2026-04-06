package net.StrayBead.hbm_ntm.particle;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, HBMNTM.MOD_ID);
    public static final RegistryObject<SimpleParticleType> SMOKE = REGISTRY.register("smoke", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> NEUTRON = REGISTRY.register("neutron", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SHOCKWAVE = REGISTRY.register("shockwave", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> FAT_MAN = REGISTRY.register("fat_man", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> ERUPTION_PARTICLE = REGISTRY.register("eruption_particle", () -> new SimpleParticleType(true));
}
