package net.StrayBead.hbm_ntm.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ShortRangeTargetDesignatorItem extends Item {
    public ShortRangeTargetDesignatorItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        BlockPos pos = getTargetPos(stack);

        if (pos != null) {
            tooltip.add(Component.literal("Target: ")
                    .append(Component.literal(pos.getX() + ", " + pos.getY() + ", " + pos.getZ())
                            .withStyle(ChatFormatting.AQUA)));
        } else {
            tooltip.add(Component.literal("No target set")
                    .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }

        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!context.getLevel().isClientSide()) {
            BlockPos clickedPos = context.getClickedPos();
            saveTargetPos(context.getItemInHand(), clickedPos);

            context.getPlayer().sendSystemMessage(Component.literal("Target set to: " + clickedPos.toShortString()));
        }
        return InteractionResult.sidedSuccess(context.getLevel().isClientSide());
    }

    public void saveTargetPos(ItemStack stack, BlockPos pos) {
        stack.getOrCreateTag().put("TargetPos", NbtUtils.writeBlockPos(pos));
    }

    @Nullable
    public BlockPos getTargetPos(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("TargetPos")) {
            return NbtUtils.readBlockPos(tag.getCompound("TargetPos"));
        }
        return null;
    }
}
