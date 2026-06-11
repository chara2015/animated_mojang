package net.minecraft.server.players;

import com.google.gson.JsonObject;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/players/UserWhiteListEntry.class */
public class UserWhiteListEntry extends StoredUserEntry<NameAndId> {
    public UserWhiteListEntry(NameAndId $$0) {
        super($$0);
    }

    public UserWhiteListEntry(JsonObject $$0) {
        super(NameAndId.fromJson($$0));
    }

    @Override // net.minecraft.server.players.StoredUserEntry
    protected void serialize(JsonObject $$0) {
        if (getUser() == null) {
            return;
        }
        getUser().appendTo($$0);
    }
}
