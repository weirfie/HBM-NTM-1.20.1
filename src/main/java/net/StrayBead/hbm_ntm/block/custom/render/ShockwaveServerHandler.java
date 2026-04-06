package net.StrayBead.hbm_ntm.block.custom.render;

import net.StrayBead.hbm_ntm.client.ShockwaveData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = "hbm_ntm", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ShockwaveServerHandler {
    private static final List<ShockwaveData> SERVER_SHOCKWAVES = new ArrayList<>();

    // Call this when your explosion happens!
    public static void spawn(Level level, Vec3 pos, float maxRadius, int maxAge) {
        if (!level.isClientSide) {
            SERVER_SHOCKWAVES.add(new ShockwaveData(pos, maxRadius, maxAge, 1.4f));
        }
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        // Run physics on the server level
        if (event.phase == TickEvent.Phase.END && !event.level.isClientSide) {
            SERVER_SHOCKWAVES.forEach(wave -> wave.tick(event.level));
            SERVER_SHOCKWAVES.removeIf(s -> s.age >= s.maxAge);
        }
    }
}
