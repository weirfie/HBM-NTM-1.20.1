package net.StrayBead.hbm_ntm.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SchrabidiumIngotItem extends Item {
    public SchrabidiumIngotItem(Properties props) {
        super(props);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("[Radioactive]").withStyle(ChatFormatting.GREEN));

        tooltip.add(Component.literal("15.0RAD/s").withStyle(ChatFormatting.YELLOW));

        tooltip.add(Component.literal("STACK: 960.0RAD/s").withStyle(ChatFormatting.YELLOW));

        tooltip.add(Component.literal("[Blinding]").withStyle(ChatFormatting.DARK_AQUA));

        super.appendHoverText(stack, level, tooltip, flag);
    }
}