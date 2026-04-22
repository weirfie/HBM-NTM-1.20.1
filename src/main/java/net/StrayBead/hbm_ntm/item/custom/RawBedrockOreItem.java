package net.StrayBead.hbm_ntm.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class RawBedrockOreItem extends Item {
    public RawBedrockOreItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Light Metal: 0.3 ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal("(Poor)").withStyle(ChatFormatting.RED)));
        tooltip.add(Component.literal("Heavy Metal: 0.53 ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal("(Low)").withStyle(ChatFormatting.GOLD)));
        tooltip.add(Component.literal("Rare Earth: 0.58 ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal("(Low)").withStyle(ChatFormatting.GOLD)));
        tooltip.add(Component.literal("Actinide: 0.58 ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal("(Low)").withStyle(ChatFormatting.GOLD)));
        tooltip.add(Component.literal("Non-Metal: 0.01 ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal("(Very Poor)").withStyle(ChatFormatting.DARK_RED)));
        tooltip.add(Component.literal("Crystalline: 0.52 ")
                .withStyle(ChatFormatting.GRAY)
                .append(Component.literal("(Low)").withStyle(ChatFormatting.GOLD)));

        super.appendHoverText(stack, level, tooltip, flag);
    }
}
