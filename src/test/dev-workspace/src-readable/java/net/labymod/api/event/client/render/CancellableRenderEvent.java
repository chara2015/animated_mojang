package net.labymod.api.event.client.render;

import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.event.Cancellable;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/CancellableRenderEvent.class */
public abstract class CancellableRenderEvent extends RenderEvent implements Cancellable {
    private boolean cancelled;

    protected CancellableRenderEvent(@NotNull Stack stack, @NotNull Phase phase) {
        super(stack, phase);
    }

    @Override // net.labymod.api.event.Cancellable
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override // net.labymod.api.event.Cancellable
    public void setCancelled(boolean cancelled) {
        if (phase() == Phase.POST) {
            throw new IllegalStateException("Cannot cancel event in post phase!");
        }
        this.cancelled = cancelled;
    }
}
