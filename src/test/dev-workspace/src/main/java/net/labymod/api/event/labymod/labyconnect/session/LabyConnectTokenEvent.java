package net.labymod.api.event.labymod.labyconnect.session;

import java.util.UUID;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.TokenStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/LabyConnectTokenEvent.class */
public class LabyConnectTokenEvent extends LabyConnectEvent {
    private final TokenStorage.Purpose purpose;
    private final UUID uniqueId;
    private final TokenStorage.Token token;

    public LabyConnectTokenEvent(LabyConnect api, TokenStorage.Purpose purpose, UUID uniqueId, TokenStorage.Token token) {
        super(api);
        this.purpose = purpose;
        this.uniqueId = uniqueId;
        this.token = token;
    }

    public TokenStorage.Purpose purpose() {
        return this.purpose;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public TokenStorage.Token token() {
        return this.token;
    }
}
