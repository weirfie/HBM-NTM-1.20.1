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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FoundryScrapItem extends Item {
    private final String type;

    public FoundryScrapItem(Properties p_41383_, String type) {
        super(p_41383_);
        this.type = type;
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, net.minecraft.world.level.LevelReader level, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        String prettyName = Arrays.stream(type.split("_"))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
                .collect(Collectors.joining(" "));
        tooltip.add(Component.literal(prettyName + ", 1 Ingot").withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, level, tooltip, flag);
    }

    public String getFluidName() {
        return this.type;
    }
}
