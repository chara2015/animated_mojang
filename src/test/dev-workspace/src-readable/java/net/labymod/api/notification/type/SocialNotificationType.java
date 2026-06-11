package net.labymod.api.notification.type;

import net.labymod.api.Laby;
import net.labymod.api.labyconnect.configuration.LabyConnectConfigAccessor;
import net.labymod.api.labyconnect.protocol.model.UserStatus;
import net.labymod.api.notification.NotificationType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/notification/type/SocialNotificationType.class */
public class SocialNotificationType implements NotificationType {
    private SocialNotificationType() {
    }

    @Override // net.labymod.api.notification.NotificationType
    public boolean shouldPush() {
        return ((LabyConnectConfigAccessor) Laby.labyAPI().labyConnect().configProvider().get()).onlineStatus().get() != UserStatus.BUSY;
    }

    public static NotificationType create() {
        return new SocialNotificationType();
    }
}
