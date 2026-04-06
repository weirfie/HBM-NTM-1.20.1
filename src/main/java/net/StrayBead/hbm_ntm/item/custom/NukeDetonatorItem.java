package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.block.custom.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NukeDetonatorItem extends Item {
    public NukeDetonatorItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!world.isClientSide && world instanceof ServerLevel serverLevel) {
            if (FatManBlock.isSet) {
                BlockPos targetPos = FatManBlock.getStoredPos();

                player.displayClientMessage(Component.literal("💥 Fat Man nuke detonated!"), true);

                FatManBlock.detonate(serverLevel, targetPos);
            } else if (TsarBombaBlock.isSet) {
                BlockPos targetPos = TsarBombaBlock.getStoredPos();

                player.displayClientMessage(Component.literal("💥 Tsar Bomba nuke detonated!"), true);

                TsarBombaBlock.detonate(serverLevel, targetPos);
            } else if (CastleBravoBlock.isSet) {
                BlockPos targetPos = CastleBravoBlock.getStoredPos();

                player.displayClientMessage(Component.literal("💥 Castle Bravo nuke detonated!"), true);

                CastleBravoBlock.detonate(serverLevel, targetPos);
            } else if (TheGadgetBlock.isSet) {
                BlockPos targetPos = TheGadgetBlock.getStoredPos();

                player.displayClientMessage(Component.literal("💥 Trinity nuke detonated!"), true);

                TheGadgetBlock.detonate(serverLevel, targetPos);
            } else if (IvyMikeBlock.isSet) {
                BlockPos targetPos = IvyMikeBlock.getStoredPos();

                player.displayClientMessage(Component.literal("💥 Ivy Mike nuke detonated!"), true);

                IvyMikeBlock.detonate(serverLevel, targetPos);
            } else if (LittleBoyBlock.isSet) {
                BlockPos targetPos = LittleBoyBlock.getStoredPos();

                player.displayClientMessage(Component.literal("💥 Little Boy nuke detonated!"), true);

                LittleBoyBlock.detonate(serverLevel, targetPos);
            } else {
                player.displayClientMessage(Component.literal("No nuke has been placed yet!"), true);
            }
        }

        return InteractionResultHolder.success(itemstack);
    }
}
