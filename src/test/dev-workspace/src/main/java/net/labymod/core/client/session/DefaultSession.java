package net.labymod.core.client.session;

import java.util.UUID;
import net.labymod.api.client.session.Session;
import net.labymod.api.util.UUIDHelper;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/session/DefaultSession.class */
public class DefaultSession implements Session {
    private final String username;
    private final UUID uniqueId;
    private final boolean hasUniqueId;
    private final String accessToken;
    private final Session.Type type;
    private final boolean premium;

    public DefaultSession(String username, UUID uniqueId, String accessToken, Session.Type type) {
        this.username = username;
        this.uniqueId = uniqueId == null ? UUIDHelper.createUniqueId(username) : uniqueId;
        this.hasUniqueId = uniqueId != null;
        this.accessToken = accessToken;
        this.type = type;
        this.premium = SessionUtil.isPremium(this.accessToken);
    }

    @Override // net.labymod.api.client.session.Session
    @NotNull
    public String getUsername() {
        return this.username;
    }

    @Override // net.labymod.api.client.session.Session
    public UUID getUniqueId() {
        return this.uniqueId;
    }

    @Override // net.labymod.api.client.session.Session
    public boolean hasUniqueId() {
        return this.hasUniqueId;
    }

    @Override // net.labymod.api.client.session.Session
    public String getAccessToken() {
        return this.accessToken;
    }

    @Override // net.labymod.api.client.session.Session
    public Session.Type getType() {
        return this.type;
    }

    @Override // net.labymod.api.client.session.Session
    public boolean isPremium() {
        return this.premium;
    }
}
