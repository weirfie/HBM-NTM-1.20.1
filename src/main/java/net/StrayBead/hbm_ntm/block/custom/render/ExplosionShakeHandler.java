package net.StrayBead.hbm_ntm.block.custom.render;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "hbm_ntm")
public class ExplosionShakeHandler {

    public static BlockPos explosionCenter = null;
    public static float currentRadius = 0;
    public static boolean isShaking = false;

    private static final float SHAKE_THRESHOLD = 70.0f;
    private static final float MAX_ROTATION = 3.0f;

    @SubscribeEvent
    public static void onPlayerTick(LivingEvent.LivingTickEvent event) {
        if (!(event.getEntity() instanceof Player player) || explosionCenter == null || !isShaking) {
            return;
        }

        if (!player.level().isClientSide) return;

        double distToCenter = Math.sqrt(player.distanceToSqr(explosionCenter.getX(), explosionCenter.getY(), explosionCenter.getZ()));

        float diff = (float) Math.abs(distToCenter - currentRadius);

        if (diff < SHAKE_THRESHOLD) {
            float intensity = 1.0f - (diff / SHAKE_THRESHOLD);

            RandomSource random = player.getRandom();
            float shakeYaw = (random.nextFloat() - 0.5f) * 2.0f * MAX_ROTATION * intensity;
            float shakePitch = (random.nextFloat() - 0.5f) * 2.0f * MAX_ROTATION * intensity;

            player.setYRot(player.getYRot() + shakeYaw);
            player.setXRot(player.getXRot() + shakePitch);
        } else if (currentRadius > distToCenter + SHAKE_THRESHOLD && currentRadius > 400) {
            explosionCenter = null;
            currentRadius = 0;
            isShaking = false;
        }
    }
}
