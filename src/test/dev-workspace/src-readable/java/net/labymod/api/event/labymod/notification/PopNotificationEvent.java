package net.labymod.api.event.labymod.notification;

import net.labymod.api.event.Event;
import net.labymod.api.notification.Notification;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/notification/PopNotificationEvent.class */
public class PopNotificationEvent implements Event {
    private final Notification notification;

    public PopNotificationEvent(Notification notification) {
        this.notification = notification;
    }

    public Notification notification() {
        return this.notification;
    }
}
