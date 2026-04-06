package net.StrayBead.hbm_ntm.network.packet;

import net.StrayBead.hbm_ntm.block.custom.entity.CompressorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SetPressureC2SPacket {
    private final BlockPos pos;
    private final int pressure;

    public SetPressureC2SPacket(BlockPos pos, int pressure) {
        this.pos = pos;
        this.pressure = pressure;
    }

    public SetPressureC2SPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.pressure = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(pressure);
    }

    public static void handle(SetPressureC2SPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            Level level = player.level();
            BlockEntity be = level.getBlockEntity(msg.pos);

            if (be instanceof CompressorBlockEntity compressor) {
                compressor.setPressure(msg.pressure);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
