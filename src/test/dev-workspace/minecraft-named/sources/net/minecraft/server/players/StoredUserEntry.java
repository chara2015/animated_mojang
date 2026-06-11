package net.minecraft.server.players;

import com.google.gson.JsonObject;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/players/StoredUserEntry.class */
public abstract class StoredUserEntry<T> {
    private final T user;

    protected abstract void serialize(JsonObject jsonObject);

    public StoredUserEntry(T $$0) {
        this.user = $$0;
    }

    public T getUser() {
        return this.user;
    }

    boolean hasExpired() {
        return false;
    }
}
