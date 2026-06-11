package net.labymod.api.event.client.gui.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/IngameMenuInitializeEvent.class */
public class IngameMenuInitializeEvent implements Event {
    private List<PauseMenuButton> buttons;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/IngameMenuInitializeEvent$PauseMenuButtonPosition.class */
    public enum PauseMenuButtonPosition {
        LEFT,
        RIGHT
    }

    public void addButton(@NotNull PauseMenuButton button) {
        Objects.requireNonNull(button, "Button cannot be null");
        if (this.buttons == null) {
            this.buttons = new ArrayList();
        }
        this.buttons.add(button);
    }

    public void addLeftButton(@NotNull Component text, @Nullable Icon icon, @Nullable Runnable onClick) {
        addButton(new PauseMenuButton(PauseMenuButtonPosition.LEFT, text, icon, onClick));
    }

    public void addRightButton(@NotNull Component text, @Nullable Icon icon, @Nullable Runnable onClick) {
        addButton(new PauseMenuButton(PauseMenuButtonPosition.RIGHT, text, icon, onClick));
    }

    public void addLeftButton(@NotNull Component text, @Nullable Runnable onClick) {
        addLeftButton(text, null, onClick);
    }

    public void addRightButton(@NotNull Component text, @Nullable Runnable onClick) {
        addRightButton(text, null, onClick);
    }

    public void forEachRightButton(@NotNull Consumer<PauseMenuButton> consumer) {
        forEachButton(PauseMenuButtonPosition.RIGHT, consumer);
    }

    public void forEachLeftButton(@NotNull Consumer<PauseMenuButton> consumer) {
        forEachButton(PauseMenuButtonPosition.LEFT, consumer);
    }

    public void forEachButton(@NotNull PauseMenuButtonPosition position, @NotNull Consumer<PauseMenuButton> consumer) {
        Objects.requireNonNull(position, "Position cannot be null");
        Objects.requireNonNull(consumer, "Consumer cannot be null");
        if (this.buttons == null) {
            return;
        }
        for (PauseMenuButton button : this.buttons) {
            if (button.position() == position) {
                consumer.accept(button);
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/gui/screen/IngameMenuInitializeEvent$PauseMenuButton.class */
    public static class PauseMenuButton {
        private final PauseMenuButtonPosition position;
        private final Component text;
        private final Icon icon;
        private final Runnable onClick;

        public PauseMenuButton(@NotNull PauseMenuButtonPosition position, @NotNull Component text, @Nullable Icon icon, @Nullable Runnable onClick) {
            Objects.requireNonNull(position, "Position cannot be null");
            Objects.requireNonNull(text, "Text cannot be null");
            this.position = position;
            this.text = text;
            this.icon = icon;
            this.onClick = onClick;
        }

        @NotNull
        public PauseMenuButtonPosition position() {
            return this.position;
        }

        @NotNull
        public Component text() {
            return this.text;
        }

        @Nullable
        public Icon getIcon() {
            return this.icon;
        }

        @Nullable
        public Runnable onClick() {
            return this.onClick;
        }
    }
}
