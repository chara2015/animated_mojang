package net.labymod.api.event.client.gui.screen;

import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/ActivityOpenEvent.class */
public class ActivityOpenEvent implements Event {
    private final Activity activity;

    public ActivityOpenEvent(Activity activity) {
        this.activity = activity;
    }

    public Activity activity() {
        return this.activity;
    }
}
