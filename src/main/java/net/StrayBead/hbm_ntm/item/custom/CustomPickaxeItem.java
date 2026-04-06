package net.StrayBead.hbm_ntm.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;

public class CustomPickaxeItem extends PickaxeItem {
    private final float customSpeed;
    private final boolean fakeEnchant;
    private final boolean vMiner, aoe, fAoe, silk, fortune, smelt, shred;

    public CustomPickaxeItem(Tier tier, int attack, float attackSpeed, float mineSpeed, boolean glint,
                             boolean vMiner, boolean aoe, boolean fAoe, boolean silk, boolean fortune,
                             boolean smelt, boolean shred, Properties props) {
        super(tier, attack, attackSpeed, props);
        this.customSpeed = mineSpeed;
        this.fakeEnchant = glint;
        this.vMiner = vMiner;
        this.aoe = aoe;
        this.fAoe = fAoe;
        this.silk = silk;
        this.fortune = fortune;
        this.smelt = smelt;
        this.shred = shred;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if (vMiner || aoe || fAoe || silk || fortune || smelt || shred) {
            CompoundTag nbt = stack.getOrCreateTag();
            int mode = nbt.getInt("MiningMode");
            tooltip.add(Component.literal("Current Mode: ").append(Component.literal(getModeName(mode)).withStyle(ChatFormatting.AQUA)));

            tooltip.add(Component.literal("")); // Spacer
            tooltip.add(Component.literal("Abilities:").withStyle(ChatFormatting.GRAY));

            if (vMiner) tooltip.add(Component.literal("- Vein Miner").withStyle(ChatFormatting.GOLD));
            if (aoe) tooltip.add(Component.literal("- 3x3 AoE").withStyle(ChatFormatting.GOLD));
            if (fAoe) tooltip.add(Component.literal("- Flat AoE").withStyle(ChatFormatting.GOLD));
            if (silk) tooltip.add(Component.literal("- Silk Touch").withStyle(ChatFormatting.GOLD));
            if (fortune) tooltip.add(Component.literal("- Fortune").withStyle(ChatFormatting.GOLD));
            if (smelt) tooltip.add(Component.literal("- Auto-Smelter").withStyle(ChatFormatting.GOLD));
            if (shred) tooltip.add(Component.literal("- Auto-Shredder").withStyle(ChatFormatting.GOLD));
        }
        super.appendHoverText(stack, level, tooltip, flag);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WITH_PICKAXE) ? this.customSpeed : super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return this.fakeEnchant || super.isFoil(stack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!vMiner && !aoe && !fAoe && !silk && !fortune && !smelt && !shred) {
            return InteractionResultHolder.pass(stack);
        }

        if (!level.isClientSide()) {
            cycleMode(stack, player);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    private void cycleMode(ItemStack stack, Player player) {
        CompoundTag nbt = stack.getOrCreateTag();
        int mode = nbt.getInt("MiningMode");

        int attempts = 0;
        do {
            mode = (mode + 1) % 8;
            attempts++;
        } while (!isModeEnabled(mode) && attempts < 8);

        if (attempts >= 8) mode = 0;

        nbt.putInt("MiningMode", mode);
        player.sendSystemMessage(Component.literal("Mode: " + getModeName(mode)));
    }

    private boolean isModeEnabled(int mode) {
        return switch (mode) {
            case 1 -> vMiner;
            case 2 -> aoe;
            case 3 -> fAoe;
            case 4 -> silk;
            case 5 -> fortune;
            case 6 -> smelt;
            case 7 -> shred;
            default -> true;
        };
    }

    private String getModeName(int mode) {
        String[] names = {"Normal", "Vein Miner", "3x3 AoE", "Flat AoE", "Silk Touch", "Fortune", "Auto-Smelter", "Auto-Shredder"};
        return names[mode];
    }
}