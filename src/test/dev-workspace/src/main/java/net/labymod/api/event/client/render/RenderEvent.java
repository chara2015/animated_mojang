package net.labymod.api.event.client.render;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/RenderEvent.class */
public class RenderEvent extends StackCheckingEvent {
    private final Stack stack;
    private final Phase phase;

    public RenderEvent(@NotNull Stack stack, @NotNull Phase phase) {
        this.stack = stack;
        this.phase = phase;
    }

    @Override // net.labymod.api.event.client.render.StackCheckingEvent
    @NotNull
    public Stack stack() {
        return this.stack;
    }

    @NotNull
    public Phase phase() {
        return this.phase;
    }
}
