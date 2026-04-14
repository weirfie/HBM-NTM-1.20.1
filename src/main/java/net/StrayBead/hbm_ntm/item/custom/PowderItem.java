package net.StrayBead.hbm_ntm.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class PowderItem extends Item {
    private final String type;

    public PowderItem(Properties p_41383_, String type) {
        super(p_41383_);
        this.type = type;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, net.minecraft.world.level.LevelReader level, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if (Objects.equals(type, "asbestos")) {
            tooltip.add(Component.literal("'Sniffffffff- MHHHHHHMHHHHHHHHHHHHHHH'").withStyle(ChatFormatting.GRAY));
        }
        if (Objects.equals(type, "poison")) {
            tooltip.add(Component.literal("Used in multi purpose bombs").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Warning: Poisonous!").withStyle(ChatFormatting.GRAY));
        }
        if (Objects.equals(type, "euphemium")) {
            tooltip.add(Component.literal("Pulverized Pink.").withStyle(ChatFormatting.GRAY));
            tooltip.add(Component.literal("Tastes like strawberries.").withStyle(ChatFormatting.GRAY));
        }

        super.appendHoverText(stack, level, tooltip, flag);
    }

    public String getFluidName() {
        return this.type;
    }
}
