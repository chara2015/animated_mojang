package net.labymod.api.notification;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/notification/NotificationType.class */
public interface NotificationType {
    default boolean shouldPush() {
        return true;
    }

    @Deprecated(forRemoval = true, since = "4.1.18")
    default int getColor() {
        return 0;
    }
}
