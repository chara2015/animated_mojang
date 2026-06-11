package net.labymod.api.event.client.gui.screen.playerlist;

import java.util.Objects;
import net.labymod.api.client.network.NetworkPlayerInfo;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/playerlist/PlayerListUserUpdateEvent.class */
public class PlayerListUserUpdateEvent extends PlayerListUpdateEvent {
    private final NetworkPlayerInfo playerInfo;

    public PlayerListUserUpdateEvent(@NotNull NetworkPlayerInfo playerInfo) {
        Objects.requireNonNull(playerInfo, "Player info cannot be null!");
        this.playerInfo = playerInfo;
    }

    @NotNull
    public NetworkPlayerInfo playerInfo() {
        return this.playerInfo;
    }
}
