package net.labymod.api.event.client.network.playerinfo;

import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/playerinfo/PlayerInfoUpdateEvent.class */
public class PlayerInfoUpdateEvent implements Event {
    private final NetworkPlayerInfo playerInfo;
    private final UpdateType type;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/playerinfo/PlayerInfoUpdateEvent$UpdateType.class */
    public enum UpdateType {
        GAME_MODE,
        PING,
        DISPLAY_NAME,
        UPDATE_LISTED,
        UPDATE_LIST_ORDER,
        UPDATE_HAT
    }

    public PlayerInfoUpdateEvent(@NotNull NetworkPlayerInfo playerInfo, @NotNull UpdateType type) {
        this.playerInfo = playerInfo;
        this.type = type;
    }

    @NotNull
    public NetworkPlayerInfo playerInfo() {
        return this.playerInfo;
    }

    @NotNull
    public UpdateType type() {
        return this.type;
    }
}
