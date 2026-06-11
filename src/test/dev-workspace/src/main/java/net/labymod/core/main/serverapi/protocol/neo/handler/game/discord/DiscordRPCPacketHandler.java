package net.labymod.core.main.serverapi.protocol.neo.handler.game.discord;

import java.time.Instant;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.thirdparty.discord.DiscordActivity;
import net.labymod.core.thirdparty.discord.DefaultDiscordApp;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.model.feature.DiscordRPC;
import net.labymod.serverapi.core.packet.clientbound.game.feature.DiscordRPCPacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/discord/DiscordRPCPacketHandler.class */
public class DiscordRPCPacketHandler implements PacketHandler<DiscordRPCPacket> {
    private static final DefaultDiscordApp DISCORD_APP = (DefaultDiscordApp) Laby.references().discordApp();

    public void handle(@NotNull UUID uuid, @NotNull DiscordRPCPacket packet) {
        DiscordActivity displayedActivity = DISCORD_APP.getDisplayedActivity();
        if (displayedActivity == null || displayedActivity.isCustom()) {
            return;
        }
        DiscordActivity.Builder builder = DiscordActivity.builder(this, displayedActivity);
        DiscordRPC discordRPC = packet.discordRPC();
        builder.state(discordRPC.getGameMode());
        if (discordRPC.getStartTime() != 0) {
            builder.start(Instant.ofEpochMilli(discordRPC.getStartTime()));
        }
        if (discordRPC.getEndTime() != 0) {
            builder.start(Instant.ofEpochMilli(discordRPC.getEndTime()));
        }
        DISCORD_APP.displayServerActivity(Laby.labyAPI().serverController().getCurrentServerData(), builder.build());
    }
}
