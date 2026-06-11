package net.labymod.api.event.client.entity.player;

import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/entity/player/ClientHotbarSlotChangeEvent.class */
public class ClientHotbarSlotChangeEvent extends DefaultCancellable implements Event {
    private final int fromSlot;
    private int toSlot;
    private int delta;

    public ClientHotbarSlotChangeEvent(int fromSlot, int delta) {
        this.fromSlot = fromSlot;
        this.toSlot = (fromSlot - delta) % 9;
        if (this.toSlot < 0) {
            this.toSlot += 9;
        }
        this.delta = delta;
    }

    public int fromSlot() {
        return this.fromSlot;
    }

    public int toSlot() {
        return this.toSlot;
    }

    public void setToSlot(int toSlot) {
        this.toSlot = toSlot;
    }

    public int delta() {
        return this.delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }
}
