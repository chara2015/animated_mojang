package net.labymod.core.labymodnet.widgetoptions;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.notification.Notification;
import net.labymod.api.notification.NotificationController;
import net.labymod.api.util.Debounce;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.cosmetics.CosmeticSettingsWidget;
import net.labymod.core.labymodnet.LabyModNetService;
import net.labymod.core.labymodnet.models.ChangeResponse;
import net.labymod.core.labymodnet.models.Cosmetic;
import net.labymod.core.main.LabyMod;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/widgetoptions/WidgetOption.class */
public abstract class WidgetOption {
    protected static final Logging LOGGER = Logging.create((Class<?>) WidgetOption.class);
    protected static final LabyModNetService LABYMOD_NET_SERVICE = LabyMod.references().labyModNetService();
    protected final String optionName;
    protected final int optionIndex;
    protected Cosmetic cosmetic;
    protected String translationKeyPrefix;
    protected String dataAtStart;
    protected CosmeticSettingsWidget.CosmeticSettingsListener listener;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labymodnet/widgetoptions/WidgetOption$Type.class */
    public enum Type {
        COLOR,
        SLIDER,
        DROPDOWN,
        TOGGLE
    }

    protected abstract void create(String str, Consumer<Widget> consumer);

    protected WidgetOption(String optionName, int optionIndex) {
        this.optionName = optionName;
        this.optionIndex = optionIndex;
    }

    public static void handleUpdateResponse(ChangeResponse changeResponse) {
        if (changeResponse != null && changeResponse.isDone()) {
            return;
        }
        ThreadSafe.executeOnRenderThread(() -> {
            pushNotification(changeResponse);
        });
    }

    public static void pushNotification(@Nullable ChangeResponse response) {
        Component componentText;
        String message = null;
        if (response != null) {
            message = response.getMessage();
        }
        NotificationController notificationController = Laby.references().notificationController();
        Notification.Builder builderTitle = Notification.builder().title(Component.translatable("labymod.misc.error", new Component[0]));
        if (message == null) {
            componentText = Component.translatable("labymod.activity.customization.cosmetics.updateFailed", new Component[0]);
        } else {
            componentText = Component.text(message);
        }
        notificationController.push(builderTitle.text(componentText).duration(3000L).build());
    }

    public void begin(Cosmetic cosmetic, CosmeticSettingsWidget.CosmeticSettingsListener listener, String translationKeyPrefix) {
        this.cosmetic = cosmetic;
        this.listener = listener;
        this.translationKeyPrefix = translationKeyPrefix;
        this.dataAtStart = this.cosmetic.getData()[this.optionIndex];
    }

    public final void create(Consumer<Widget> consumer) {
        try {
            create(this.cosmetic.getData()[this.optionIndex], consumer);
        } catch (Exception exception) {
            LOGGER.error("Failed to create widget option " + this.optionName, exception);
        }
    }

    protected void debounce(String id, Runnable runnable) {
        long debounceLength = getDebounceLength();
        if (debounceLength == 0) {
            runnable.run();
        } else {
            Debounce.of(id + this.cosmetic.getItemId(), debounceLength, runnable);
        }
    }

    protected void setData(String value) {
        this.cosmetic.getData()[this.optionIndex] = value;
    }

    protected void setData(String debounceId, String value) {
        setData(value);
        if (this.listener != null) {
            this.listener.onDataUpdate(this.cosmetic);
        }
        if (this.listener == null || !this.listener.shouldSendRemoteRequest()) {
            return;
        }
        debounce(debounceId, () -> {
            if (value.equals(this.dataAtStart)) {
                return;
            }
            this.dataAtStart = value;
            LABYMOD_NET_SERVICE.updateCosmetic(this.cosmetic, WidgetOption::handleUpdateResponse);
        });
    }

    protected long getDebounceLength() {
        return 1000L;
    }
}
