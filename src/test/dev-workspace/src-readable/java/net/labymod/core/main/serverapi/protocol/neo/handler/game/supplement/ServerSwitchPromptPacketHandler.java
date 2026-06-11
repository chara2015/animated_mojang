package net.labymod.core.main.serverapi.protocol.neo.handler.game.supplement;

import java.util.Map;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.network.server.ConnectableServerData;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.configuration.labymod.main.laby.OtherConfig;
import net.labymod.core.client.gui.screen.activity.activities.ingame.serverapi.ServerSwitchActivity;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.model.supplement.ServerSwitchPrompt;
import net.labymod.serverapi.core.packet.clientbound.game.supplement.ServerSwitchPromptPacket;
import net.labymod.serverapi.core.packet.serverbound.game.supplement.ServerSwitchPromptResponsePacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/supplement/ServerSwitchPromptPacketHandler.class */
public class ServerSwitchPromptPacketHandler implements PacketHandler<ServerSwitchPromptPacket> {
    public void handle(@NotNull UUID uuid, @NotNull ServerSwitchPromptPacket packet) {
        LabyAPI labyAPI = Laby.labyAPI();
        ServerData currentServerData = labyAPI.serverController().getCurrentServerData();
        if (currentServerData == null) {
            return;
        }
        OtherConfig otherConfig = LabyConfigProvider.INSTANCE.get().other();
        Map<String, Boolean> serverSwitchChoices = otherConfig.serverSwitchChoices().get();
        ServerSwitchPrompt prompt = packet.prompt();
        String rememberedCombination = currentServerData.actualAddress().toString() + "#" + prompt.getAddress();
        Boolean savedChoice = serverSwitchChoices.get(rememberedCombination);
        if (savedChoice != null) {
            if (savedChoice.booleanValue()) {
                labyAPI.serverController().joinServer(ConnectableServerData.builder().address(prompt.getAddress()).build());
            }
            sendResponse(packet, savedChoice.booleanValue());
        } else {
            ServerSwitchActivity activity = new ServerSwitchActivity(packet.prompt(), currentServerData, value -> {
                sendResponse(packet, value);
            });
            labyAPI.minecraft().minecraftWindow().displayScreen(activity);
        }
    }

    private void sendResponse(ServerSwitchPromptPacket packet, boolean value) {
        Laby.references().labyModProtocolService().sendLabyModPacket(new ServerSwitchPromptResponsePacket(packet, value));
    }
}
