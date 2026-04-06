package net.StrayBead.hbm_ntm.client;

import net.StrayBead.hbm_ntm.block.custom.FatManBlock;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ParticlePacket {
    private final double x, y, z, dx, dy, dz;
    private final int count;

    public ParticlePacket(double x, double y, double z, double dx, double dy, double dz, int count) {
        this.x = x; this.y = y; this.z = z;
        this.dx = dx; this.dy = dy; this.dz = dz;
        this.count = count;
    }

    public static void encode(ParticlePacket msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.x); buf.writeDouble(msg.y); buf.writeDouble(msg.z);
        buf.writeDouble(msg.dx); buf.writeDouble(msg.dy); buf.writeDouble(msg.dz);
        buf.writeInt(msg.count);
    }

    public static ParticlePacket decode(FriendlyByteBuf buf) {
        return new ParticlePacket(buf.readDouble(), buf.readDouble(), buf.readDouble(),
                buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt());
    }

    public static void handle(ParticlePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            FatManBlock.spawnCustomParticles(msg.x, msg.y, msg.z, msg.dx, msg.dy, msg.dz, msg.count);
        });
        ctx.get().setPacketHandled(true);
    }
}
