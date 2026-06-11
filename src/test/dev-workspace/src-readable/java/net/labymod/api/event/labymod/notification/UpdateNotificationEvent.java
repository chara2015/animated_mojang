package net.labymod.api.event.labymod.notification;

import net.labymod.api.event.Event;
import net.labymod.api.notification.Notification;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/notification/UpdateNotificationEvent.class */
public class UpdateNotificationEvent implements Event {
    private final Notification notification;

    public UpdateNotificationEvent(Notification notification) {
        this.notification = notification;
    }

    public Notification notification() {
        return this.notification;
    }
}
