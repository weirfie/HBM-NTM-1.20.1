package net.StrayBead.hbm_ntm.particle;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, HBMNTM.MOD_ID);

    public static final RegistryObject<SimpleParticleType> SMOKE_PARTICLE =
            PARTICLE_TYPES.register("smoke", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> NEUTRON_PARTICLE =
            PARTICLE_TYPES.register("neutron", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> SHOCKWAVE_PARTICLE =
            PARTICLE_TYPES.register("shockwave_particle", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> FAT_MAN_PARTICLE =
            PARTICLE_TYPES.register("fat_man", () -> new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType> ERUPTION_PARTICLE =
            PARTICLE_TYPES.register("eruption_particle", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
