package net.minecraft.server.players;

import com.google.gson.JsonObject;
import java.io.File;
import java.util.Objects;
import net.minecraft.server.notifications.NotificationService;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/players/ServerOpList.class */
public class ServerOpList extends StoredUserList<NameAndId, ServerOpListEntry> {
    public ServerOpList(File $$0, NotificationService $$1) {
        super($$0, $$1);
    }

    @Override // net.minecraft.server.players.StoredUserList
    protected StoredUserEntry<NameAndId> createEntry(JsonObject $$0) {
        return new ServerOpListEntry($$0);
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

    @Override // net.minecraft.server.players.StoredUserList
    public boolean add(ServerOpListEntry $$0) {
        if (super.add($$0)) {
            if ($$0.getUser() != null) {
                this.notificationService.playerOped($$0);
                return true;
            }
            return true;
        }
        return false;
    }

    @Override // net.minecraft.server.players.StoredUserList
    public boolean remove(NameAndId $$0) {
        ServerOpListEntry $$1 = get($$0);
        if (super.remove($$0)) {
            if ($$1 != null) {
                this.notificationService.playerDeoped($$1);
                return true;
            }
            return true;
        }
        return false;
    }

    @Override // net.minecraft.server.players.StoredUserList
    public void clear() {
        for (ServerOpListEntry $$0 : getEntries()) {
            if ($$0.getUser() != null) {
                this.notificationService.playerDeoped($$0);
            }
        }
        super.clear();
    }

    public boolean canBypassPlayerLimit(NameAndId $$0) {
        ServerOpListEntry $$1 = get($$0);
        if ($$1 != null) {
            return $$1.getBypassesPlayerLimit();
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.minecraft.server.players.StoredUserList
    public String getKeyForUser(NameAndId $$0) {
        return $$0.id().toString();
    }
}
