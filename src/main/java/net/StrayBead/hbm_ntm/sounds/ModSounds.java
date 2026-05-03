package net.StrayBead.hbm_ntm.sounds;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, HBMNTM.MOD_ID);

    public static final RegistryObject<SoundEvent> GEIGER_CLICK = registerSoundEvents("geiger_click");
    public static final RegistryObject<SoundEvent> DUCT_PLACE = registerSoundEvents("duct_place");
    public static final RegistryObject<SoundEvent> NUCLEAR_EXPLOSION = registerSoundEvents("nuclear_explosion");
    public static final RegistryObject<SoundEvent> NUCLEAR_SIREN = registerSoundEvents("nuclear_siren");
    public static final RegistryObject<SoundEvent> EXPLOSION_HIT = registerSoundEvents("explosion_hit");
    public static final RegistryObject<SoundEvent> MISSILE_LAUNCH = registerSoundEvents("missile_launch");
    public static final RegistryObject<SoundEvent> RBMK_EXPLOSION = registerSoundEvents("rbmk_explosion");
    public static final RegistryObject<SoundEvent> FREE_BIRD = registerSoundEvents("free_bird");
    public static final RegistryObject<SoundEvent> DOOR_CREAK_OPEN = registerSoundEvents("door_creak_open");
    public static final RegistryObject<SoundEvent> SIM_CARD_ACTIVATION = registerSoundEvents("sim_card_activation");
    public static final RegistryObject<SoundEvent> VOMIT = registerSoundEvents("vomit");
    public static final RegistryObject<SoundEvent> EXPLOSION_NUKE = registerSoundEvents("nuke_explosion");
    public static final RegistryObject<SoundEvent> CONTROL_SYSTEM_ALARM = registerSoundEvents("control_system_alarm");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(HBMNTM.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
