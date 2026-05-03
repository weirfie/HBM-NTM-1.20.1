package net.StrayBead.hbm_ntm.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class BismuthLaserCrystalItem extends Item {
    public BismuthLaserCrystalItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Bismuth-Samarium-Uranium-Thorium crystal matrix").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Visible Light - 400nm-780nm").withStyle(ChatFormatting.GREEN));

        super.appendHoverText(stack, level, tooltip, flag);
    }
}
