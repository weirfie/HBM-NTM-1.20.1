package net.StrayBead.hbm_ntm.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class DigammaLaserCrystalItem extends Item {
    public DigammaLaserCrystalItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Qq¬âAâtmÄzCöâÐÿßæ7ÄñåègeGX")
                .withStyle(ChatFormatting.GRAY, ChatFormatting.OBFUSCATED));
        tooltip.add(Component.literal("Digamma - 1916169 planck lengths").withStyle(ChatFormatting.DARK_RED));

        super.appendHoverText(stack, level, tooltip, flag);
    }
}
