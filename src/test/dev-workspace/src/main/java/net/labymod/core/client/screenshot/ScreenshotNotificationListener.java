package net.labymod.core.client.screenshot;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import net.labymod.api.event.client.misc.ScreenshotNotificationEvent;
import net.labymod.api.event.client.misc.WriteScreenshotEvent;
import net.labymod.api.models.OperatingSystem;
import net.labymod.api.notification.Notification;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots.ScreenshotBrowserActivity;
import net.labymod.core.client.screenshot.Screenshot;
import net.labymod.core.client.screenshot.meta.ScreenshotMeta;
import net.labymod.core.localization.keys.ActivityTranslationKeys;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/screenshot/ScreenshotNotificationListener.class */
public class ScreenshotNotificationListener {
    private static final Logging LOGGER = Logging.getLogger();
    private Path lastScreenshot;

    @Subscribe
    public void onWriteScreenshot(WriteScreenshotEvent event) {
        if (event.getPhase() != Phase.POST) {
            return;
        }
        this.lastScreenshot = event.getDestination().toPath();
        if (!Laby.labyAPI().config().notifications().screenshot().get().booleanValue()) {
            return;
        }
        LabyExecutors.executeBackgroundTask(() -> {
            handleScreenshotNotification(event);
        });
    }

    private void handleScreenshotNotification(WriteScreenshotEvent event) {
        try {
            Path path = event.getDestination().toPath();
            Screenshot screenshot = new Screenshot(path, new ScreenshotMeta(path));
            screenshot.updateQuality(Screenshot.QualityType.HIGH);
            Notification.Builder builder = buildDefaultNotification(screenshot);
            ScreenshotNotificationEvent notificationEvent = new ScreenshotNotificationEvent(path, screenshot.getIcon(), builder);
            Laby.fireEvent(notificationEvent);
            if (!notificationEvent.isCancelled()) {
                Laby.references().notificationController().push(notificationEvent.getNotificationBuilder().build());
            }
        } catch (Exception exception) {
            LOGGER.warn("Failed to create screenshot notification", exception);
        }
    }

    private Notification.Builder buildDefaultNotification(Screenshot screenshot) {
        Notification.Builder builder = Notification.builder();
        Notification.Builder builderOnClick = builder.title(ActivityTranslationKeys.getScreenshotBrowserSaveTitle()).thumbnail(screenshot.getIcon()).onClick(notification -> {
            ScreenshotBrowserActivity.openScreenshot(this.lastScreenshot);
        });
        Component screenshotBrowserContextOpen = ActivityTranslationKeys.getScreenshotBrowserContextOpen();
        Objects.requireNonNull(screenshot);
        builderOnClick.addButton(Notification.NotificationButton.of(screenshotBrowserContextOpen, screenshot::openInSystem));
        if (OperatingSystem.getPlatform() == OperatingSystem.WINDOWS) {
            Component screenshotBrowserContextCopy = ActivityTranslationKeys.getScreenshotBrowserContextCopy();
            Objects.requireNonNull(screenshot);
            builder.addButton(Notification.NotificationButton.of(screenshotBrowserContextCopy, screenshot::copyToClipboard));
        }
        Notification.Builder builderAddButton = builder.addButton(Notification.NotificationButton.of(ActivityTranslationKeys.getScreenshotBrowserContextUpload(), () -> {
            screenshot.upload().thenAccept(url -> {
                if (url == null) {
                    return;
                }
                OperatingSystem.getPlatform().openUrl(url);
                Laby.labyAPI().minecraft().chatExecutor().copyToClipboard(url);
            });
        }));
        Component screenshotBrowserContextDelete = ActivityTranslationKeys.getScreenshotBrowserContextDelete();
        Objects.requireNonNull(screenshot);
        builderAddButton.addButton(Notification.NotificationButton.of(screenshotBrowserContextDelete, screenshot::delete));
        return builder;
    }

    @Subscribe
    public void onChat(ChatReceiveEvent event) {
        Component message = event.message();
        if (!(message instanceof TranslatableComponent)) {
            return;
        }
        TranslatableComponent translatableComponent = (TranslatableComponent) message;
        if (!"screenshot.success".equals(translatableComponent.getKey()) || this.lastScreenshot == null) {
            return;
        }
        List<Component> arguments = translatableComponent.getArguments();
        if (arguments.isEmpty()) {
            return;
        }
        Component argument = (Component) arguments.getFirst();
        argument.clickEvent(ClickEvent.runCommand("/screenshot-viewer " + String.valueOf(this.lastScreenshot)));
        event.setMessage(message);
    }
}
