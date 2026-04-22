package net.StrayBead.hbm_ntm.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

public class KitItem extends Item {
    private final Supplier<List<ItemStack>> contents;

    public KitItem(Properties properties, Supplier<List<ItemStack>> contents) {
        super(properties);
        this.contents = contents;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack kitStack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            List<ItemStack> itemsToGive = contents.get();

            for (ItemStack stack : itemsToGive) {
                ItemStack copy = stack.copy();

                if (!player.getInventory().add(copy)) {
                    player.drop(copy, false);
                }
            }

            if (!player.getAbilities().instabuild) {
                kitStack.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(kitStack, level.isClientSide());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Please empty inventory before opening!").withStyle(ChatFormatting.GRAY));

        super.appendHoverText(stack, level, tooltip, flag);
    }
}
