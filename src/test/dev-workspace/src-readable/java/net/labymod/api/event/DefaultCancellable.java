package net.labymod.api.event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/DefaultCancellable.class */
public abstract class DefaultCancellable implements Cancellable {
    private boolean cancelled;

    @Override // net.labymod.api.event.Cancellable
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override // net.labymod.api.event.Cancellable
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
