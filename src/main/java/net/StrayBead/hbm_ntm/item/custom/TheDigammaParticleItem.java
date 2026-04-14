package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.block.custom.BlackHoleManager;
import net.StrayBead.hbm_ntm.render.custom.BlackHole;
import net.StrayBead.hbm_ntm.render.custom.FolkvangrFieldRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class TheDigammaParticleItem extends Item {

    public TheDigammaParticleItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level level = entity.level();

        if (entity.onGround()) {
            Vec3 spawnPos = entity.position().add(0, 1, 0);
            BlackHoleManager.spawn(spawnPos, 4f);

            level.explode(
                    null,
                    entity.getX(),
                    entity.getY(),
                    entity.getZ(),
                    20.0f,
                    Level.ExplosionInteraction.BLOCK
            );

            entity.discard();
        }

        return super.onEntityItemUpdate(stack, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Particle Half-Life: 1.67*10^34a").withStyle(ChatFormatting.GOLD));
        tooltip.add(Component.literal("Player Half-Life: 3.0s").withStyle(ChatFormatting.RED));
        tooltip.add(Component.literal(" ").withStyle(ChatFormatting.RED));
        tooltip.add(Component.literal("[Digamma Radiation]").withStyle(ChatFormatting.RED));
        tooltip.add(Component.literal("333.3mDRX/s").withStyle(ChatFormatting.DARK_RED));
        tooltip.add(Component.literal("[Dangerous Drop]").withStyle(ChatFormatting.RED));


        super.appendHoverText(stack, level, tooltip, flag);
    }
}
