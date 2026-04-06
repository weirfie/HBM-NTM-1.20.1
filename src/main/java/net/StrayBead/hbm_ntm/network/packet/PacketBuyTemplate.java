package net.StrayBead.hbm_ntm.network.packet;

import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketBuyTemplate {
    private final String templateId;

    public PacketBuyTemplate(String templateId) {
        this.templateId = templateId;
    }

    public static void encode(PacketBuyTemplate msg, FriendlyByteBuf buffer) {
        buffer.writeUtf(msg.templateId);
    }

    public static PacketBuyTemplate decode(FriendlyByteBuf buffer) {
        return new PacketBuyTemplate(buffer.readUtf());
    }

    public static void handle(PacketBuyTemplate msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            int removed = player.getInventory().clearOrCountMatchingItems(
                    stack -> stack.is(Items.PAPER),
                    1,
                    player.inventoryMenu.getCraftSlots()
            );

            if (removed > 0) {
                ItemStack reward = new ItemStack(ModItems.ASSEMBLY_TEMPLATES.get(msg.templateId).get());
                if (!player.getInventory().add(reward)) {
                    player.drop(reward, false);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
