package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.block.ModBlocks;
import net.StrayBead.hbm_ntm.block.custom.entity.GenericBoundingBoxBE;
import net.StrayBead.hbm_ntm.block.custom.entity.PylonBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class CableDrumItem extends Item {

    public CableDrumItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        ItemStack stack = context.getItemInHand();

        BlockPos targetPos = clickedPos;
        if (clickedState.is(ModBlocks.GENERIC_BOUNDING_BOX.get())) {
            if (level.getBlockEntity(clickedPos) instanceof GenericBoundingBoxBE boundingBE) {
                targetPos = boundingBE.getCorePos();
            }
        }

        if (level.getBlockState(targetPos).is(ModBlocks.LARGE_ELECTRICITY_PYLON.get())) {
            if (!level.isClientSide()) {
                if (hasWireStarted(stack)) {
                    context.getPlayer().sendSystemMessage(Component.literal("Wire connected to: " + targetPos.toShortString()));

                    connectPylons(level, getSavedPos(stack), targetPos);

                    setWireStarted(stack, false);
                } else {
                    context.getPlayer().sendSystemMessage(Component.literal("Wire started at: " + targetPos.toShortString()));
                    savePos(stack, targetPos);
                    setWireStarted(stack, true);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    private void savePos(ItemStack stack, BlockPos pos) {
        stack.getOrCreateTag().putLong("startPos", pos.asLong());
    }

    private BlockPos getSavedPos(ItemStack stack) {
        return BlockPos.of(stack.getTag().getLong("startPos"));
    }

    private void setWireStarted(ItemStack stack, boolean started) {
        stack.getOrCreateTag().putBoolean("hasWireStarted", started);
    }

    private boolean hasWireStarted(ItemStack stack) {
        return stack.hasTag() && stack.getTag().getBoolean("hasWireStarted");
    }

    private void connectPylons(Level level, BlockPos p1, BlockPos p2) {
        if (level.getBlockEntity(p1) instanceof PylonBlockEntity be) {
            be.addConnection(p2);
        }
    }
}
