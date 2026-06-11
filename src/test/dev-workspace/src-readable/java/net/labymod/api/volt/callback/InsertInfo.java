package net.labymod.api.volt.callback;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/volt/callback/InsertInfo.class */
public class InsertInfo {
    private final boolean cancellable;
    private boolean cancelled;
    private boolean jumping;

    public InsertInfo(boolean cancellable) {
        this.cancellable = cancellable;
    }

    public boolean isCancellable() {
        return this.cancellable;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isJumping() {
        return this.jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public void jump() {
        this.jumping = true;
    }

    public void reset() {
        this.cancelled = false;
        this.jumping = false;
    }
}
