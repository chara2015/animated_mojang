package net.labymod.api.client.entity.player.tag.event;

import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/event/NameTagBackgroundRenderEvent.class */
public class NameTagBackgroundRenderEvent extends DefaultCancellable implements Event {
    private static final NameTagBackgroundRenderEvent EVENT = new NameTagBackgroundRenderEvent();
    private int color = 0;

    public static NameTagBackgroundRenderEvent singleton() {
        EVENT.setColor(0);
        EVENT.setCancelled(false);
        return EVENT;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
