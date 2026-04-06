package net.StrayBead.hbm_ntm.entity;

import net.StrayBead.hbm_ntm.entity.client.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class HBMModModels {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(Modelfat_man_drop.LAYER_LOCATION, Modelfat_man_drop::createBodyLayer);
        event.registerLayerDefinition(Modelincendiary_missile.LAYER_LOCATION, Modelincendiary_missile::createBodyLayer);
        event.registerLayerDefinition(Modelnuclear_missile.LAYER_LOCATION, Modelnuclear_missile::createBodyLayer);
        event.registerLayerDefinition(Modelanti_ballistic_missile.LAYER_LOCATION, Modelanti_ballistic_missile::createBodyLayer);
        event.registerLayerDefinition(Modelhigh_explosive_missile.LAYER_LOCATION, Modelhigh_explosive_missile::createBodyLayer);
    }
}
