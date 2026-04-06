package net.StrayBead.hbm_ntm.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class Uranium233Item extends Item {
    private final String radAmount;

    public Uranium233Item(Properties properties, String radAmount) {
        super(properties);
        this.radAmount = radAmount;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("[Radioactive]").withStyle(ChatFormatting.GREEN));

        tooltip.add(Component.literal(radAmount + " RADS/s").withStyle(ChatFormatting.YELLOW));

        super.appendHoverText(stack, level, tooltip, flag);
    }
}
