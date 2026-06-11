package net.minecraft.client;

import com.mojang.util.UndashedUuid;
import java.util.Optional;
import java.util.UUID;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/User.class */
public class User {
    private final String name;
    private final UUID uuid;
    private final String accessToken;
    private final Optional<String> xuid;
    private final Optional<String> clientId;

    public User(String $$0, UUID $$1, String $$2, Optional<String> $$3, Optional<String> $$4) {
        this.name = $$0;
        this.uuid = $$1;
        this.accessToken = $$2;
        this.xuid = $$3;
        this.clientId = $$4;
    }

    public String getSessionId() {
        return "token:" + this.accessToken + ":" + UndashedUuid.toString(this.uuid);
    }

    public UUID getProfileId() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public Optional<String> getClientId() {
        return this.clientId;
    }

    public Optional<String> getXuid() {
        return this.xuid;
    }
}
