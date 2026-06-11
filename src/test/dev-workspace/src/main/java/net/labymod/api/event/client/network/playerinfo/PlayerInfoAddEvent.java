package net.labymod.api.event.client.network.playerinfo;

import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/playerinfo/PlayerInfoAddEvent.class */
public class PlayerInfoAddEvent implements Event {
    private final NetworkPlayerInfo playerInfo;

    public PlayerInfoAddEvent(@NotNull NetworkPlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    @NotNull
    public NetworkPlayerInfo playerInfo() {
        return this.playerInfo;
    }
}
