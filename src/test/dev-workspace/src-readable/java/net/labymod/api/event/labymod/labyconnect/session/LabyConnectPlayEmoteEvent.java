package net.labymod.api.event.labymod.labyconnect.session;

import java.util.UUID;
import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;
import net.labymod.api.labyconnect.LabyConnect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/LabyConnectPlayEmoteEvent.class */
public class LabyConnectPlayEmoteEvent extends LabyConnectEvent {
    private final UUID uniqueId;
    private final short emoteId;

    public LabyConnectPlayEmoteEvent(LabyConnect api, UUID uniqueId, short emoteId) {
        super(api);
        this.uniqueId = uniqueId;
        this.emoteId = emoteId;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public short getEmoteId() {
        return this.emoteId;
    }
}
