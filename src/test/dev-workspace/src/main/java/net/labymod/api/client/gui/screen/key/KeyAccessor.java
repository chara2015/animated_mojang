package net.labymod.api.client.gui.screen.key;

import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/key/KeyAccessor.class */
public final class KeyAccessor {
    private final Supplier<Key> keyGetter;
    private final Supplier<Key> defaultKeyGetter;
    private final Consumer<Key> keyUpdater;

    public KeyAccessor(Supplier<Key> keyGetter, Supplier<Key> defaultKeyGetter, Consumer<Key> keyUpdater) {
        this.keyGetter = keyGetter;
        this.defaultKeyGetter = defaultKeyGetter;
        this.keyUpdater = keyUpdater;
    }

    public Key get() {
        return this.keyGetter.get();
    }

    public Key getDefault() {
        return this.defaultKeyGetter.get();
    }

    public void set(Key key) {
        this.keyUpdater.accept(key);
    }
}
