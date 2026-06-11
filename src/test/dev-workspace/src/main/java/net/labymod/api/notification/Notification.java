package net.labymod.api.notification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.notification.type.DefaultNotificationType;
import net.labymod.api.notification.type.SocialNotificationType;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/notification/Notification.class */
public class Notification {
    public static final long FADE_DURATION = 800;
    private final Component title;
    private final Component text;
    private final Icon icon;
    private final Icon secondaryIcon;
    private final Icon thumbnail;
    private final long duration;
    private final NotificationType type;
    private final Consumer<Notification> onClick;
    private final List<NotificationButton> buttons;
    private long startTime;
    private long holdTime = -1;
    private int index;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/notification/Notification$Type.class */
    public static class Type {
        public static NotificationType SYSTEM = DefaultNotificationType.create();
        public static NotificationType SOCIAL = SocialNotificationType.create();
    }

    private Notification(Component title, Component text, Icon icon, Icon secondaryIcon, Icon thumbnail, long duration, NotificationType type, List<NotificationButton> buttons, Consumer<Notification> onClick) {
        this.title = title;
        this.text = text;
        this.icon = icon;
        this.secondaryIcon = secondaryIcon;
        this.thumbnail = thumbnail;
        this.duration = duration;
        this.type = type;
        this.buttons = buttons;
        this.onClick = onClick;
        resetProgress();
    }

    @Contract(value = " -> new", pure = true)
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    @NotNull
    public static Builder builder(Notification notification) {
        return new Builder(notification);
    }

    @ApiStatus.Experimental
    public List<NotificationButton> buttons() {
        return this.buttons;
    }

    public Component title() {
        return this.title;
    }

    public Component text() {
        return this.text;
    }

    public Icon icon() {
        return this.icon;
    }

    public Icon secondaryIcon() {
        return this.secondaryIcon;
    }

    public Icon thumbnail() {
        return this.thumbnail;
    }

    public long duration() {
        return this.duration;
    }

    public NotificationType type() {
        return this.type;
    }

    public float progress() {
        if (!isAlive()) {
            return 1.0f;
        }
        float visibleTimeAlive = timeAlive() - 800;
        float maxVisibleDuration = this.duration - 1600;
        return MathHelper.clamp(visibleTimeAlive / maxVisibleDuration, 0.0f, 1.0f);
    }

    public void resetProgress() {
        this.startTime = TimeUtil.getMillis();
    }

    public void holdProgress() {
        if (this.holdTime != -1 || isFading()) {
            return;
        }
        this.holdTime = timeAlive();
    }

    public void resumeProgress() {
        if (this.holdTime == -1) {
            return;
        }
        this.startTime = TimeUtil.getMillis() - this.holdTime;
        this.holdTime = -1L;
    }

    public boolean isAlive() {
        return timeAlive() <= this.duration;
    }

    public long timeAlive() {
        if (this.holdTime != -1) {
            return this.holdTime;
        }
        return TimeUtil.getMillis() - this.startTime;
    }

    public boolean isFading() {
        return isFadingIn() || isFadingOut();
    }

    public boolean isFadingIn() {
        return timeAlive() <= 800;
    }

    public boolean isFadingOut() {
        return timeAlive() >= this.duration - 800;
    }

    public void remove() {
        resumeProgress();
        this.startTime = TimeUtil.getMillis() - (this.duration - 800);
    }

    public int getIndex() {
        return this.index;
    }

    public boolean click() {
        if (this.onClick != null && isAlive()) {
            this.onClick.accept(this);
            remove();
            return true;
        }
        return false;
    }

    @ApiStatus.Internal
    public void addIndex() {
        this.index++;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Notification that = (Notification) o;
        return this.duration == that.duration && Objects.equals(this.buttons, that.buttons) && Objects.equals(this.title, that.title) && Objects.equals(this.text, that.text) && Objects.equals(this.icon, that.icon) && Objects.equals(this.type, that.type);
    }

    public int hashCode() {
        int result = this.buttons != null ? this.buttons.hashCode() : 0;
        return (31 * ((31 * ((31 * ((31 * ((31 * result) + (this.title != null ? this.title.hashCode() : 0))) + (this.text != null ? this.text.hashCode() : 0))) + (this.icon != null ? this.icon.hashCode() : 0))) + ((int) (this.duration ^ (this.duration >>> 32))))) + (this.type != null ? this.type.hashCode() : 0);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/notification/Notification$Builder.class */
    public static class Builder {
        private Component title;
        private Component text;
        private Icon icon;
        private Icon secondaryIcon;
        private Icon thumbnail;
        private long duration;
        private NotificationType type;
        private Consumer<Notification> onClick;
        private final List<NotificationButton> buttons;

        private Builder() {
            this.duration = 7000L;
            this.type = Type.SYSTEM;
            this.buttons = new ArrayList();
        }

        private Builder(Notification notification) {
            this.duration = 7000L;
            this.type = Type.SYSTEM;
            this.title = notification.title;
            this.text = notification.text;
            this.icon = notification.icon;
            this.secondaryIcon = notification.secondaryIcon;
            this.thumbnail = notification.thumbnail;
            this.duration = notification.duration;
            this.type = notification.type;
            this.onClick = notification.onClick;
            this.buttons = notification.buttons;
        }

        public Builder title(Component title) {
            this.title = title;
            return this;
        }

        public Builder text(Component text) {
            this.text = text;
            return this;
        }

        public Builder icon(Icon icon) {
            this.icon = icon;
            return this;
        }

        public Builder secondaryIcon(Icon icon) {
            this.secondaryIcon = icon;
            return this;
        }

        public Builder thumbnail(Icon icon) {
            this.thumbnail = icon;
            return this;
        }

        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public Builder type(NotificationType type) {
            this.type = type;
            return this;
        }

        public Builder addButton(NotificationButton button) {
            this.buttons.add(button);
            return this;
        }

        public Builder addButtons(NotificationButton... buttons) {
            Collections.addAll(this.buttons, buttons);
            return this;
        }

        public Builder addButtons(Collection<NotificationButton> buttons) {
            this.buttons.addAll(buttons);
            return this;
        }

        public Builder clearButtons() {
            this.buttons.clear();
            return this;
        }

        public Builder onClick(Consumer<Notification> onClick) {
            this.onClick = onClick;
            return this;
        }

        public Notification build() {
            if (this.title == null) {
                throw new IllegalArgumentException("Missing title");
            }
            if (this.text == null && this.thumbnail == null) {
                throw new IllegalArgumentException("Missing text or thumbnail");
            }
            return new Notification(this.title, this.text, this.icon, this.secondaryIcon, this.thumbnail, this.duration, this.type, this.buttons, this.onClick);
        }

        public Notification buildAndPush() {
            Notification notification = build();
            Laby.references().notificationController().push(notification);
            return notification;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/notification/Notification$NotificationButton.class */
    public static class NotificationButton {
        private final Component text;
        private final Component hoverText;
        private final Runnable action;
        private final boolean primary;

        private NotificationButton(Component text, Component hoverText, Runnable action, boolean primary) {
            this.text = text;
            this.hoverText = hoverText;
            this.action = action;
            this.primary = primary;
        }

        public Component text() {
            return this.text;
        }

        public Component hoverText() {
            return this.hoverText;
        }

        public Runnable action() {
            return this.action;
        }

        public boolean isPrimary() {
            return this.primary;
        }

        public static NotificationButton primary(Component text, Runnable action) {
            return new NotificationButton(text, null, action, true);
        }

        public static NotificationButton primary(Component text, Component hoverText, Runnable action) {
            return new NotificationButton(text, hoverText, action, true);
        }

        public static NotificationButton of(Component text, Runnable action) {
            return new NotificationButton(text, null, action, false);
        }

        public static NotificationButton of(Component text, Component hoverText, Runnable action) {
            return new NotificationButton(text, hoverText, action, false);
        }
    }
}
