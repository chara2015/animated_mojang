package net.labymod.api.client.chat;

import net.labymod.api.client.component.Component;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/ActionBar.class */
public class ActionBar {
    private final Runnable removeHandler;
    private Component message;
    private int tick;

    public ActionBar(Runnable removeHandler, Component message) {
        this.removeHandler = removeHandler;
        this.message = message;
    }

    public void remove() {
        this.removeHandler.run();
    }

    @NotNull
    public Component message() {
        return this.message;
    }

    public void setMessage(@NotNull Component message) {
        this.message = message;
    }

    @ApiStatus.Internal
    public int getTick() {
        return this.tick;
    }

    @ApiStatus.Internal
    public void setTick(int tick) {
        this.tick = tick;
    }
}
