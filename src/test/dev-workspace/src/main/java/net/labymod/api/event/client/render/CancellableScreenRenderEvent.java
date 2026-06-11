package net.labymod.api.event.client.render;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.event.Cancellable;
import net.labymod.api.event.Phase;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/CancellableScreenRenderEvent.class */
public abstract class CancellableScreenRenderEvent extends ScreenRenderEvent implements Cancellable {
    private boolean cancelled;

    protected CancellableScreenRenderEvent(@NotNull ScreenContext screenContext, @NotNull Phase phase) {
        super(screenContext, phase);
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
