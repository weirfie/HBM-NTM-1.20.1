package net.StrayBead.hbm_ntm.network;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.StrayBead.hbm_ntm.client.ParticlePacket;
import net.StrayBead.hbm_ntm.network.packet.*;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id() { return packetId++; }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(HBMNTM.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(EnergySyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnergySyncS2CPacket::new)
                .encoder(EnergySyncS2CPacket::toBytes)
                .consumerMainThread(EnergySyncS2CPacket::handle)
                .add();

        net.messageBuilder(FluidSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FluidSyncS2CPacket::new)
                .encoder(FluidSyncS2CPacket::toBytes)
                .consumerMainThread(FluidSyncS2CPacket::handle)
                .add();

        net.messageBuilder(PacketBuyIdentifier.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketBuyIdentifier::decode)
                .encoder(PacketBuyIdentifier::encode)
                .consumerMainThread(PacketBuyIdentifier::handle)
                .add();

        net.messageBuilder(PacketBuyCrucibleTemplate.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketBuyCrucibleTemplate::new)
                .encoder(PacketBuyCrucibleTemplate::toBytes)
                .consumerMainThread(PacketBuyCrucibleTemplate::handle)
                .add();

        net.messageBuilder(ParticlePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ParticlePacket::decode)
                .encoder(ParticlePacket::encode)
                .consumerMainThread(ParticlePacket::handle)
                .add();

        net.messageBuilder(ItemStackSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ItemStackSyncS2CPacket::new)
                .encoder(ItemStackSyncS2CPacket::toBytes)
                .consumerMainThread(ItemStackSyncS2CPacket::handle)
                .add();

        net.messageBuilder(ButtonPressedPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ButtonPressedPacket::new)
                .encoder(ButtonPressedPacket::toBytes)
                .consumerMainThread(ButtonPressedPacket::handle)
                .add();

        net.messageBuilder(PacketBuyTank.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketBuyTank::decode)
                .encoder(PacketBuyTank::encode)
                .consumerMainThread(PacketBuyTank::handle)
                .add();

        net.messageBuilder(PacketBuyTemplate.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PacketBuyTemplate::decode)
                .encoder(PacketBuyTemplate::encode)
                .consumerMainThread(PacketBuyTemplate::handle)
                .add();

        net.messageBuilder(ToggleOutputPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ToggleOutputPacket::new)
                .encoder(ToggleOutputPacket::toBytes)
                .consumerMainThread(ToggleOutputPacket::handle)
                .add();

        net.messageBuilder(SetPressureC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SetPressureC2SPacket::new)
                .encoder(SetPressureC2SPacket::toBytes)
                .consumerMainThread(SetPressureC2SPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        if (INSTANCE != null) {
            INSTANCE.send(PacketDistributor.ALL.noArg(), message);
        }
    }

    public static <MSG> void sendToClientsTracking(MSG message, ServerLevel level, BlockPos pos) {
        if (INSTANCE != null) {
            INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), message);
        }
    }
}
