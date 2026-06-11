package net.minecraft.server.notifications;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/notifications/ServerActivityMonitor.class */
public class ServerActivityMonitor {
    private final long minimumMillisBetweenNotifications;
    private final AtomicLong lastNotificationTime = new AtomicLong();
    private final AtomicBoolean serverActivity = new AtomicBoolean(false);
    private final NotificationManager notificationManager;

    public ServerActivityMonitor(NotificationManager $$0, int $$1) {
        this.notificationManager = $$0;
        this.minimumMillisBetweenNotifications = TimeUnit.SECONDS.toMillis($$1);
    }

    public void tick() {
        processWithRateLimit();
    }

    public void reportLoginActivity() {
        this.serverActivity.set(true);
        processWithRateLimit();
    }

    private void processWithRateLimit() {
        long $$0 = Util.getMillis();
        if (this.serverActivity.get() && $$0 - this.lastNotificationTime.get() >= this.minimumMillisBetweenNotifications) {
            this.notificationManager.serverActivityOccured();
            this.lastNotificationTime.set(Util.getMillis());
        }
        this.serverActivity.set(false);
    }
}
