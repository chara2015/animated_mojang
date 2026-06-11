package net.labymod.api.laby3d.foreign.target;

import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/foreign/target/ForeignRenderTarget.class */
public abstract class ForeignRenderTarget implements RenderTarget {
    protected final Vector2i size = new Vector2i();
    private boolean closed;

    public int width() {
        return this.size.x();
    }

    public int height() {
        return this.size.y();
    }

    public void clear(boolean unbindTarget) {
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void close() {
        this.closed = true;
    }

    @NotNull
    public String label() {
        return "ForeignRenderTarget";
    }
}
