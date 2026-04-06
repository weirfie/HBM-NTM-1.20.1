package net.StrayBead.hbm_ntm.potion;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.potion.custom.HasSimCardMobEffect;
import net.StrayBead.hbm_ntm.potion.custom.RadiationPoisoningMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, HBMNTM.MOD_ID);

    public static final RegistryObject<MobEffect> RADIATION_POISONING = MOB_EFFECTS.register("radiation_poisoning",
            RadiationPoisoningMobEffect::new);
    public static final RegistryObject<MobEffect> HAS_SIM_CARD = MOB_EFFECTS.register("has_sim_card",
            HasSimCardMobEffect::new);

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
