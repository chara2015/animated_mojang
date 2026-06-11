package net.labymod.api.event.client.render;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Event;
import net.labymod.api.event.Phase;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/GameRenderEvent.class */
public class GameRenderEvent implements Event {
    private final Phase phase;
    private final ScreenContext context;

    public GameRenderEvent(Phase phase, ScreenContext context) {
        this.phase = phase;
        this.context = context;
    }

    public Phase phase() {
        return this.phase;
    }

    public Stack stack() {
        return this.context.stack();
    }

    public ScreenContext context() {
        return this.context;
    }

    public float getPartialTicks() {
        return this.context.getTickDelta();
    }
}
