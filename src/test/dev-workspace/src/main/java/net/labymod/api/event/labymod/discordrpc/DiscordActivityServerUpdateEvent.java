package net.labymod.api.event.labymod.discordrpc;

import java.util.Objects;
import net.labymod.api.client.network.server.ServerData;
import net.labymod.api.thirdparty.discord.DiscordActivity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/discordrpc/DiscordActivityServerUpdateEvent.class */
public class DiscordActivityServerUpdateEvent extends DiscordActivityUpdateEvent {
    private final ServerData serverData;

    @ApiStatus.Internal
    public DiscordActivityServerUpdateEvent(@NotNull ServerData serverData, @NotNull DiscordActivity activity) {
        super(activity);
        Objects.requireNonNull(serverData, "Server data cannot be null!");
        this.serverData = serverData;
    }

    @NotNull
    public ServerData serverData() {
        return this.serverData;
    }
}
