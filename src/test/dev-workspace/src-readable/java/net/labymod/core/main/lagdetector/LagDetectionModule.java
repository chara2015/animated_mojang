package net.labymod.core.main.lagdetector;

import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.client.input.KeyEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/lagdetector/LagDetectionModule.class */
public abstract class LagDetectionModule {
    private final String name;

    public abstract void onUpdate();

    public LagDetectionModule(String name) {
        this.name = name;
    }

    protected void onKey(Key key, KeyEvent.State state) {
    }

    public String getName() {
        return this.name;
    }
}
