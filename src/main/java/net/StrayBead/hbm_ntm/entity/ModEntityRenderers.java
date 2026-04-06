package net.StrayBead.hbm_ntm.entity;

import net.StrayBead.hbm_ntm.entity.custom.*;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEntityRenderers {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.NEUTRON_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.LASER_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.SUBMUNITION_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.SHOCKWAVE_PROJECTILE.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.FAT_MAN_NUKE_DROP_ENTITY.get(), FatManNukeDropRenderer::new);
        event.registerEntityRenderer(ModEntities.INCENDIARY_MISSILE_ENTITY.get(), IncendiaryMissileRenderer::new);
        event.registerEntityRenderer(ModEntities.NUCLEAR_MISSILE_ENTITY.get(), NuclearMissileRenderer::new);
        event.registerEntityRenderer(ModEntities.ANTI_BALLISTIC_MISSILE_ENTITY.get(), AntiBallisticMissileRenderer::new);
        event.registerEntityRenderer(ModEntities.HIGH_EXPLOSIVE_MISSILE_ENTITY.get(), HighExplosiveMissileRenderer::new);
    }
}
