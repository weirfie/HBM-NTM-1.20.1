package net.StrayBead.hbm_ntm.network.packet;

import net.StrayBead.hbm_ntm.block.custom.entity.SparkEnergyBatteryBlockEntity;
import net.StrayBead.hbm_ntm.block.custom.entity.WaterTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleOutputPacket {
    private final BlockPos pos;

    public ToggleOutputPacket(BlockPos pos) {
        this.pos = pos;
    }

    public ToggleOutputPacket(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                ServerLevel level = player.serverLevel();
                var blockEntity = level.getBlockEntity(pos);

                if (blockEntity instanceof SparkEnergyBatteryBlockEntity battery) {
                    battery.IS_OUTPUTTING = !battery.IS_OUTPUTTING;
                    finishToggle(level, battery);
                }
                else if (blockEntity instanceof WaterTankBlockEntity tank) {
                    tank.IS_OUTPUTTING = !tank.IS_OUTPUTTING;
                    finishToggle(level, tank);
                }
            }
        });
        return true;
    }

    private void finishToggle(ServerLevel level, BlockEntity be) {
        be.setChanged();
        level.sendBlockUpdated(pos, be.getBlockState(), be.getBlockState(), 3);
    }
}
