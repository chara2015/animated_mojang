package net.labymod.core.main.serverapi.protocol.neo.handler.game.supplement;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.core.client.gui.screen.activity.activities.ingame.serverapi.InputPromptActivity;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.packet.clientbound.game.supplement.InputPromptPacket;
import net.labymod.serverapi.core.packet.serverbound.game.supplement.InputPromptResponsePacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/supplement/InputPromptPacketHandler.class */
public class InputPromptPacketHandler implements PacketHandler<InputPromptPacket> {
    public void handle(@NotNull UUID uuid, @NotNull InputPromptPacket packet) {
        InputPromptActivity activity = new InputPromptActivity(packet.prompt(), value -> {
            Laby.references().labyModProtocolService().sendLabyModPacket(new InputPromptResponsePacket(packet, value));
        });
        Laby.labyAPI().minecraft().minecraftWindow().displayScreen(activity);
    }
}
