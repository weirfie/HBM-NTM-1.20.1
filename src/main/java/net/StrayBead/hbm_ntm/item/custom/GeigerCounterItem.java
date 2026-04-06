package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.potion.ModMobEffects;
import net.StrayBead.hbm_ntm.sounds.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class GeigerCounterItem extends Item {
    public GeigerCounterItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide && entity instanceof Player player) {
            int radiationLevel = 0;
            if (player.hasEffect(ModMobEffects.RADIATION_POISONING.get())) {
                radiationLevel = player.getEffect(ModMobEffects.RADIATION_POISONING.get()).getAmplifier() + 1;
            }

            if (level.random.nextDouble() < 0.005) {
                playGeigerSound(level, player, 0.2f, 1.2f);
            }

            if (radiationLevel > 0) {
                if (radiationLevel >= 19) {
                    int staticIntensity = 10 + level.random.nextInt(5);
                    for (int i = 0; i < staticIntensity; i++) {
                        playGeigerSound(level, player, 0.5f, 0.2f);
                    }
                } else {
                    double exponentialFactor = Math.pow(radiationLevel / 20.0, 3);
                    for (int i = 0; i < 5; i++) {
                        if (level.random.nextDouble() < (exponentialFactor * 2.0)) {
                            playGeigerSound(level, player, 0.4f, 0.2f);
                        }
                    }
                }
            }
        }
    }

    private void playGeigerSound(Level level, Player player, float volume, float pitchRange) {
        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                ModSounds.GEIGER_CLICK.get(), SoundSource.PLAYERS,
                volume,
                0.85f + level.random.nextFloat() * pitchRange);
    }
}
