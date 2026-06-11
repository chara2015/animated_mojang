package net.labymod.api.event.labymod.labyconnect.session;

import com.google.gson.JsonElement;
import java.util.Objects;
import java.util.UUID;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;
import net.labymod.api.labyconnect.LabyConnect;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/LabyConnectBroadcastEvent.class */
public class LabyConnectBroadcastEvent extends LabyConnectEvent {
    private final Action action;
    private final UUID sender;
    private final String key;
    private final JsonElement payload;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/LabyConnectBroadcastEvent$Action.class */
    public enum Action {
        SEND,
        RECEIVE
    }

    public LabyConnectBroadcastEvent(@NotNull LabyConnect api, @NotNull Action action, @NotNull UUID sender, @NotNull String key, @NotNull JsonElement payload) {
        super(api);
        Objects.requireNonNull(action, "Action cannot be null!");
        Objects.requireNonNull(sender, "Sender cannot be null!");
        Objects.requireNonNull(key, "Key cannot be null!");
        Objects.requireNonNull(payload, "Payload cannot be null!");
        this.action = action;
        this.sender = sender;
        this.key = key;
        this.payload = payload;
    }

    @NotNull
    public Action action() {
        return this.action;
    }

    @NotNull
    public UUID getSender() {
        return this.sender;
    }

    @NotNull
    public String getKey() {
        return this.key;
    }

    @NotNull
    public JsonElement getPayload() {
        return this.payload;
    }
}
