package net.StrayBead.hbm_ntm.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class FluidTankItem extends Item {
    private final String type;
    private static final String NBT_KEY = "fluid_amount";
    private static final String TYPE_KEY = "fluid_type";
    public static final int TANK_CAPACITY = 50000;

    public FluidTankItem(Properties p_41383_, String type) {
        super(p_41383_);
        this.type = type;
    }

    public static int getFluidAmount(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains(NBT_KEY)) {
            return stack.getTag().getInt(NBT_KEY);
        }
        return 0;
    }

    public static void setFluidAmount(ItemStack stack, int amount) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(NBT_KEY, amount);
    }

    public int getCapacity() {
        return TANK_CAPACITY;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        int amount = getFluidAmount(stack);
        tooltip.add(Component.literal("Fluid Amount: " + amount + "mB"));
        super.appendHoverText(stack, level, tooltip, flag);
    }

    public String getFluidName() {
        return this.type;
    }

    public static String getFluidType(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains(TYPE_KEY)) {
            return stack.getTag().getString(TYPE_KEY);
        }
        return "";
    }

    public static void setFluidData(ItemStack stack, int amount, String type) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putInt(NBT_KEY, amount);
        tag.putString(TYPE_KEY, type);
    }
}
