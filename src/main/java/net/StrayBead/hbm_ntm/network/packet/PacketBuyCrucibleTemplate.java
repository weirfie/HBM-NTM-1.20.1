package net.StrayBead.hbm_ntm.network.packet;

import net.StrayBead.hbm_ntm.item.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketBuyCrucibleTemplate {
    private final String name;

    public PacketBuyCrucibleTemplate(String name) { this.name = name; }
    public PacketBuyCrucibleTemplate(FriendlyByteBuf buf) { this.name = buf.readUtf(); }

    public void toBytes(FriendlyByteBuf buf) { buf.writeUtf(name); }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            player.getInventory().add(new ItemStack(ModItems.CRUCIBLE_TEMPLATES.get(name).get()));
        });
        return true;
    }
}
