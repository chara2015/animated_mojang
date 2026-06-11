package net.labymod.api.client.gui.screen.key.tracker;

import java.util.Objects;
import net.labymod.api.client.gui.screen.key.Key;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/key/tracker/KeyTracker.class */
public abstract class KeyTracker {
    private final Key key;

    public abstract int getClicksPerSecond();

    public abstract void press();

    public abstract void update();

    protected KeyTracker(@NotNull Key key) {
        Objects.requireNonNull(key, "Key cannot be null");
        this.key = key;
    }

    @NotNull
    public Key key() {
        return this.key;
    }
}
