package net.labymod.api.laby3d.foreign.target;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/foreign/target/ForeignRenderTargetRegistry.class */
public abstract class ForeignRenderTargetRegistry {
    public abstract ForeignRenderTarget findDrawTarget();

    public abstract ForeignRenderTarget findReadTarget();

    public abstract void register(ForeignRenderTarget foreignRenderTarget);

    public abstract void unregister(ForeignRenderTarget foreignRenderTarget);

    public abstract ForeignRenderTarget get(Object obj);

    protected ForeignRenderTargetRegistry() {
    }
}
