package net.StrayBead.hbm_ntm.event;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.particle.*;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = HBMNTM.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
        Minecraft.getInstance().particleEngine.register(ModParticles.SMOKE_PARTICLE.get(),
                SmokeParticle.SmokeParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.NEUTRON_PARTICLE.get(),
                NeutronParticle.NeutronParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.SHOCKWAVE_PARTICLE.get(),
                ShockwaveParticle.ShockwaveParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.FAT_MAN_PARTICLE.get(),
                FatManParticle.FatManParticleProvider::new);
        Minecraft.getInstance().particleEngine.register(ModParticles.ERUPTION_PARTICLE.get(),
                EruptionParticle.EruptionParticleProvider::new);
    }
}
