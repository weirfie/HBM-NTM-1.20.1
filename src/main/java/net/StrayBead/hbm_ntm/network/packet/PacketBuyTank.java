package net.StrayBead.hbm_ntm.network.packet;

import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketBuyTank {
    private final String fluidName;

    public PacketBuyTank(String fluidName) {
        this.fluidName = fluidName;
    }

    public static void encode(PacketBuyTank msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.fluidName);
    }

    public static PacketBuyTank decode(FriendlyByteBuf buffer) {
        return new PacketBuyTank(buffer.readUtf());
    }

    public static void handle(PacketBuyTank msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            ItemStack costItem = new ItemStack(ModItems.STEEL_TANK.get());

            int removed = player.getInventory().clearOrCountMatchingItems(
                    stack -> stack.getItem() == costItem.getItem(),
                    1,
                    player.inventoryMenu.getCraftSlots()
            );

            if (removed > 0) {
                ItemStack reward = new ItemStack(ModItems.FLUID_TANK_NAMES.get(msg.fluidName).get());
                if (!player.getInventory().add(reward)) {
                    player.drop(reward, false);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
