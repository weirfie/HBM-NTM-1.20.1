package net.StrayBead.hbm_ntm.screen;

import net.StrayBead.hbm_ntm.HBMNTM;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ControlPanelGuiButtonMessage {
    private final int buttonID, x, y, z;

    public ControlPanelGuiButtonMessage(FriendlyByteBuf buffer) {
        this.buttonID = buffer.readInt();
        this.x = buffer.readInt();
        this.y = buffer.readInt();
        this.z = buffer.readInt();
    }

    public ControlPanelGuiButtonMessage(int buttonID, int x, int y, int z) {
        this.buttonID = buttonID;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static void buffer(ControlPanelGuiButtonMessage message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.buttonID);
        buffer.writeInt(message.x);
        buffer.writeInt(message.y);
        buffer.writeInt(message.z);
    }

    public static void handler(ControlPanelGuiButtonMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            Player entity = context.getSender();
            int buttonID = message.buttonID;
            int x = message.x;
            int y = message.y;
            int z = message.z;
            handleButtonAction(entity, buttonID, x, y, z);
        });
        context.setPacketHandled(true);
    }

    public static void handleButtonAction(Player entity, int buttonID, int x, int y, int z) {
        Level world = entity.level();
        HashMap guistate = ControlPanelGuiMenu.guistate;
        // security measure to prevent arbitrary chunk generation
        if (!world.hasChunkAt(new BlockPos(x, y, z)))
            return;
        if (buttonID == 0) {

            ButtonClickedProcedure.execute(world, x, y, z);
        }
        if (buttonID == 1) {

            ExtractAllControlRodsProcedure.execute(world, x, y, z);
        }
        if (buttonID == 6) {

            FuelRodClickedProcedure.execute(world, x, y, z);
        }
        if (buttonID == 7) {

            ControlRodClickedProcedure.execute(world, x, y, z);
        }
        if (buttonID == 8) {

            ControlRodClickedProcedure.execute(world, x, y, z);
        }
        if (buttonID == 9) {

            ControlRodClickedProcedure.execute(world, x, y, z);
        }
        if (buttonID == 10) {

            ControlRodClickedProcedure.execute(world, x, y, z);
        }
        if (buttonID == 15) {

            GraphiteModeratorButtonClickedProcedure.execute(world, x, y, z, guistate);
        }
        if (buttonID == 16) {

            GraphiteModeratorButtonClickedProcedure.execute(world, x, y, z, guistate);
        }
        if (buttonID == 17) {

            GraphiteModeratorButtonClickedProcedure.execute(world, x, y, z, guistate);
        }
        if (buttonID == 19) {

            FuelRodClickedProcedure.execute(world, x, y, z);
        }
        if (buttonID == 20) {

            FuelRodClickedProcedure.execute(world, x, y, z);
        }
        if (buttonID == 21) {

            FuelRodClickedProcedure.execute(world, x, y, z);
        }
        if (buttonID == 22) {

            FuelRodClickedProcedure.execute(world, x, y, z);
        }
        if (buttonID == 23) {

            GraphiteModeratorButtonClickedProcedure.execute(world, x, y, z, guistate);
        }
        if (buttonID == 24) {

            GraphiteModeratorButtonClickedProcedure.execute(world, x, y, z, guistate);
        }
        if (buttonID == 25) {

            GraphiteModeratorButtonClickedProcedure.execute(world, x, y, z, guistate);
        }
        if (buttonID == 26) {

            GraphiteModeratorButtonClickedProcedure.execute(world, x, y, z, guistate);
        }
        if (buttonID == 27) {

            GraphiteModeratorButtonClickedProcedure.execute(world, x, y, z, guistate);
        }
        if (buttonID == 28) {

            GraphiteModeratorButtonClickedProcedure.execute(world, x, y, z, guistate);
        }
        if (buttonID == 29) {

            GraphiteModeratorButtonClickedProcedure.execute(world, x, y, z, guistate);
        }
        if (buttonID == 30) {

            GraphiteModeratorButtonClickedProcedure.execute(world, x, y, z, guistate);
        }
        if (buttonID == 31) {

            AZ5ClickedProcedure.execute(world, x, y, z);
        }
        if (buttonID == 32) {

            PowerUpButtonClickedProcedure.execute(world, x, y, z);
        }
        if (buttonID == 33) {

            PowerDownButtonClickedProcedure.execute(world, x, y, z);
        }
        if (buttonID == 34) {
            XenonScanProcedure.execute(world, x, y, z, entity);
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        HBMNTM.addNetworkMessage(ControlPanelGuiButtonMessage.class, ControlPanelGuiButtonMessage::buffer, ControlPanelGuiButtonMessage::new, ControlPanelGuiButtonMessage::handler);
    }
}
