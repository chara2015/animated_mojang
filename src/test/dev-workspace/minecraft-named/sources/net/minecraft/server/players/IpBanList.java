package net.minecraft.server.players;

import com.google.gson.JsonObject;
import java.io.File;
import java.net.SocketAddress;
import net.minecraft.server.notifications.NotificationService;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/players/IpBanList.class */
public class IpBanList extends StoredUserList<String, IpBanListEntry> {
    public IpBanList(File $$0, NotificationService $$1) {
        super($$0, $$1);
    }

    @Override // net.minecraft.server.players.StoredUserList
    protected StoredUserEntry<String> createEntry(JsonObject $$0) {
        return new IpBanListEntry($$0);
    }

    public boolean isBanned(SocketAddress $$0) {
        String $$1 = getIpFromAddress($$0);
        return contains($$1);
    }

    public boolean isBanned(String $$0) {
        return contains($$0);
    }

    public IpBanListEntry get(SocketAddress $$0) {
        String $$1 = getIpFromAddress($$0);
        return get($$1);
    }

    private String getIpFromAddress(SocketAddress $$0) {
        String $$1 = $$0.toString();
        if ($$1.contains("/")) {
            $$1 = $$1.substring($$1.indexOf(47) + 1);
        }
        if ($$1.contains(":")) {
            $$1 = $$1.substring(0, $$1.indexOf(58));
        }
        return $$1;
    }

    @Override // net.minecraft.server.players.StoredUserList
    public boolean add(IpBanListEntry $$0) {
        if (super.add($$0)) {
            if ($$0.getUser() != null) {
                this.notificationService.ipBanned($$0);
                return true;
            }
            return true;
        }
        return false;
    }

    @Override // net.minecraft.server.players.StoredUserList
    public boolean remove(String $$0) {
        if (super.remove($$0)) {
            this.notificationService.ipUnbanned($$0);
            return true;
        }
        return false;
    }

    @Override // net.minecraft.server.players.StoredUserList
    public void clear() {
        for (IpBanListEntry $$0 : getEntries()) {
            if ($$0.getUser() != null) {
                this.notificationService.ipUnbanned($$0.getUser());
            }
        }
        super.clear();
    }
}
