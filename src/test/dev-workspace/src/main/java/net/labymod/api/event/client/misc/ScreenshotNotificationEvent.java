package net.labymod.api.event.client.misc;

import java.nio.file.Path;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import net.labymod.api.notification.Notification;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/misc/ScreenshotNotificationEvent.class */
public class ScreenshotNotificationEvent extends DefaultCancellable implements Event {
    private final Path screenshotPath;
    private final Icon screenshotIcon;
    private Notification.Builder notificationBuilder;

    public ScreenshotNotificationEvent(@NotNull Path screenshotPath, @NotNull Icon screenshotIcon, @NotNull Notification.Builder notificationBuilder) {
        this.screenshotPath = screenshotPath;
        this.screenshotIcon = screenshotIcon;
        this.notificationBuilder = notificationBuilder;
    }

    @NotNull
    public Path getScreenshotPath() {
        return this.screenshotPath;
    }

    @NotNull
    public Icon getScreenshotIcon() {
        return this.screenshotIcon;
    }

    @NotNull
    public Notification.Builder getNotificationBuilder() {
        return this.notificationBuilder;
    }

    public void setNotificationBuilder(@NotNull Notification.Builder notificationBuilder) {
        this.notificationBuilder = notificationBuilder;
    }
}
