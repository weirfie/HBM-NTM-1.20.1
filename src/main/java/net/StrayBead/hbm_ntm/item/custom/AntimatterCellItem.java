package net.StrayBead.hbm_ntm.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class AntimatterCellItem extends Item {
    public AntimatterCellItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack stack, ItemEntity entity) {
        Level level = entity.level();

        if (!level.isClientSide && entity.onGround()) {

            level.explode(entity, entity.getX(), entity.getY(), entity.getZ(),
                    4.0F, true, Level.ExplosionInteraction.BLOCK);

            entity.discard();
        }

        return super.onEntityItemUpdate(stack, entity);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Warning: Exposure to matter will lead to violent annihilation!").withStyle(ChatFormatting.GRAY));

        tooltip.add(Component.literal("[Dangerous Drop]").withStyle(ChatFormatting.RED));

        super.appendHoverText(stack, level, tooltip, flag);
    }
}
