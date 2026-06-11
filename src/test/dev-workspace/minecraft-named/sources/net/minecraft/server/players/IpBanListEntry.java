package net.minecraft.server.players;

import com.google.gson.JsonObject;
import java.util.Date;
import net.minecraft.network.chat.Component;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/players/IpBanListEntry.class */
public class IpBanListEntry extends BanListEntry<String> {
    public IpBanListEntry(String $$0) {
        this($$0, null, null, null, null);
    }

    public IpBanListEntry(String $$0, Date $$1, String $$2, Date $$3, String $$4) {
        super($$0, $$1, $$2, $$3, $$4);
    }

    @Override // net.minecraft.server.players.BanListEntry
    public Component getDisplayName() {
        return Component.literal(String.valueOf(getUser()));
    }

    public IpBanListEntry(JsonObject $$0) {
        super(createIpInfo($$0), $$0);
    }

    private static String createIpInfo(JsonObject $$0) {
        if ($$0.has("ip")) {
            return $$0.get("ip").getAsString();
        }
        return null;
    }

    @Override // net.minecraft.server.players.BanListEntry, net.minecraft.server.players.StoredUserEntry
    protected void serialize(JsonObject $$0) {
        if (getUser() == null) {
            return;
        }
        $$0.addProperty("ip", getUser());
        super.serialize($$0);
    }
}
