package net.labymod.api.event.labymod.notification;

import net.labymod.api.event.Event;
import net.labymod.api.notification.Notification;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/notification/PushNotificationEvent.class */
public class PushNotificationEvent implements Event {
    private final Notification notification;

    public PushNotificationEvent(Notification notification) {
        this.notification = notification;
    }

    public Notification notification() {
        return this.notification;
    }
}
