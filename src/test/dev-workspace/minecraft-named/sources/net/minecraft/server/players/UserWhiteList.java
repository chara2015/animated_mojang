package net.minecraft.server.players;

import com.google.gson.JsonObject;
import java.io.File;
import java.util.Objects;
import net.minecraft.server.notifications.NotificationService;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/players/UserWhiteList.class */
public class UserWhiteList extends StoredUserList<NameAndId, UserWhiteListEntry> {
    public UserWhiteList(File $$0, NotificationService $$1) {
        super($$0, $$1);
    }

    @Override // net.minecraft.server.players.StoredUserList
    protected StoredUserEntry<NameAndId> createEntry(JsonObject $$0) {
        return new UserWhiteListEntry($$0);
    }

    public boolean isWhiteListed(NameAndId $$0) {
        return contains($$0);
    }

    @Override // net.minecraft.server.players.StoredUserList
    public boolean add(UserWhiteListEntry $$0) {
        if (super.add($$0)) {
            if ($$0.getUser() != null) {
                this.notificationService.playerAddedToAllowlist($$0.getUser());
                return true;
            }
            return true;
        }
        return false;
    }

    @Override // net.minecraft.server.players.StoredUserList
    public boolean remove(NameAndId $$0) {
        if (super.remove($$0)) {
            this.notificationService.playerRemovedFromAllowlist($$0);
            return true;
        }
        return false;
    }

    @Override // net.minecraft.server.players.StoredUserList
    public void clear() {
        for (UserWhiteListEntry $$0 : getEntries()) {
            if ($$0.getUser() != null) {
                this.notificationService.playerRemovedFromAllowlist($$0.getUser());
            }
        }
        super.clear();
    }

    @Override // net.minecraft.server.players.StoredUserList
    public String[] getUserList() {
        return (String[]) getEntries().stream().map((v0) -> {
            return v0.getUser();
        }).filter((v0) -> {
            return Objects.nonNull(v0);
        }).map((v0) -> {
            return v0.name();
        }).toArray($$0 -> {
            return new String[$$0];
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.server.players.StoredUserList
    public String getKeyForUser(NameAndId $$0) {
        return $$0.id().toString();
    }
}
