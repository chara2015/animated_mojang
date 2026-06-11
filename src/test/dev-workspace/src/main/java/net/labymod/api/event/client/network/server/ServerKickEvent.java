package net.labymod.api.event.client.network.server;

import java.util.Objects;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.network.server.ConnectableServerData;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/ServerKickEvent.class */
public class ServerKickEvent extends ServerEvent {
    private final Context context;
    private Component reason;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/network/server/ServerKickEvent$Context.class */
    public enum Context {
        PRE_LOGIN,
        LOGIN,
        PLAY
    }

    @Override // net.labymod.api.event.client.network.server.ServerEvent
    @NotNull
    public /* bridge */ /* synthetic */ ConnectableServerData serverData() {
        return super.serverData();
    }

    public ServerKickEvent(@NotNull ConnectableServerData serverData, @NotNull Component reason, @NotNull Context context) {
        super(serverData);
        Objects.requireNonNull(reason, "Reason cannot be null!");
        Objects.requireNonNull(context, "Context cannot be null!");
        this.reason = reason;
        this.context = context;
    }

    @NotNull
    public Context context() {
        return this.context;
    }

    @NotNull
    public Component reason() {
        return this.reason;
    }

    public void setReason(@NotNull Component reason) {
        Objects.requireNonNull(reason, "Reason cannot be null!");
        this.reason = reason;
    }
}
