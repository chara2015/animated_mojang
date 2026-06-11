package net.labymod.api.event.client.render;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/ScreenRenderEvent.class */
public class ScreenRenderEvent implements Event {
    private final ScreenContext screenContext;
    private final Phase phase;

    public ScreenRenderEvent(@NotNull ScreenContext screenContext, @NotNull Phase phase) {
        this.screenContext = screenContext;
        this.phase = phase;
    }

    @NotNull
    public ScreenContext screenContext() {
        return this.screenContext;
    }

    @NotNull
    public Phase phase() {
        return this.phase;
    }

    @Deprecated(forRemoval = true, since = "4.3.0")
    public float getTickDelta() {
        return Laby.labyAPI().minecraft().getTickDelta();
    }
}
