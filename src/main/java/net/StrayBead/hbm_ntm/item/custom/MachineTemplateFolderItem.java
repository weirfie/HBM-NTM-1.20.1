package net.StrayBead.hbm_ntm.item.custom;

import net.StrayBead.hbm_ntm.screen.MachineTemplateFolderScreen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MachineTemplateFolderItem extends Item {
    public MachineTemplateFolderItem(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            openFolderGui();
        }
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }

    private void openFolderGui() {
        net.minecraft.client.Minecraft.getInstance().setScreen(new MachineTemplateFolderScreen());
    }
}
