package net.StrayBead.hbm_ntm.network.packet;

import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketBuyIdentifier {
    private final String fluidName;

    public PacketBuyIdentifier(String fluidName) { this.fluidName = fluidName; }

    public static void encode(PacketBuyIdentifier msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.fluidName);
    }

    public static PacketBuyIdentifier decode(FriendlyByteBuf buffer) {
        return new PacketBuyIdentifier(buffer.readUtf());
    }

    public static void handle(PacketBuyIdentifier msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                ItemStack steelPlate = new ItemStack(ModItems.STEEL_PLATE.get());
                if (player.getInventory().clearOrCountMatchingItems(p -> p.is(steelPlate.getItem()), 1, player.inventoryMenu.getCraftSlots()) > 0) {
                    ItemStack identifier = new ItemStack(ModItems.FLUID_IDENTIFIERS.get(msg.fluidName).get());
                    player.addItem(identifier);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
