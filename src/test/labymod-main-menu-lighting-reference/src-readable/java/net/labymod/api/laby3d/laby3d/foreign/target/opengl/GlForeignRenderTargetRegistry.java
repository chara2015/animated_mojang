package net.labymod.api.laby3d.foreign.target.opengl;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.labymod.api.laby3d.foreign.target.ForeignRenderTarget;
import net.labymod.api.laby3d.foreign.target.ForeignRenderTargetRegistry;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/foreign/target/opengl/GlForeignRenderTargetRegistry.class */
public class GlForeignRenderTargetRegistry extends ForeignRenderTargetRegistry {
    private static final Logging LOGGER = Logging.getLogger();
    private final Int2ObjectMap<ForeignRenderTarget> targets = new Int2ObjectOpenHashMap();
    private int drawFbo;
    private int readFbo;

    public void setDrawFbo(int drawFbo) {
        this.drawFbo = drawFbo;
    }

    public void setReadFbo(int readFbo) {
        this.readFbo = readFbo;
    }

    @Override // net.labymod.api.laby3d.foreign.target.ForeignRenderTargetRegistry
    public ForeignRenderTarget findDrawTarget() {
        return (ForeignRenderTarget) this.targets.get(this.drawFbo);
    }

    @Override // net.labymod.api.laby3d.foreign.target.ForeignRenderTargetRegistry
    public ForeignRenderTarget findReadTarget() {
        return (ForeignRenderTarget) this.targets.get(this.readFbo);
    }

    @Override // net.labymod.api.laby3d.foreign.target.ForeignRenderTargetRegistry
    public void register(ForeignRenderTarget target) {
        GlForeignRenderTarget glForeignRenderTarget = (GlForeignRenderTarget) CastUtil.requireInstanceOf(target, GlForeignRenderTarget.class);
        this.targets.put(glForeignRenderTarget.getId(), target);
    }

    @Override // net.labymod.api.laby3d.foreign.target.ForeignRenderTargetRegistry
    public void unregister(ForeignRenderTarget target) {
        GlForeignRenderTarget glForeignRenderTarget = (GlForeignRenderTarget) CastUtil.requireInstanceOf(target, GlForeignRenderTarget.class);
        ForeignRenderTarget removed = (ForeignRenderTarget) this.targets.remove(glForeignRenderTarget.getId());
        if (removed != null) {
            removed.close();
        }
    }

    @Override // net.labymod.api.laby3d.foreign.target.ForeignRenderTargetRegistry
    public ForeignRenderTarget get(Object key) {
        Integer fboId = (Integer) CastUtil.requireInstanceOf(key, Integer.class);
        return (ForeignRenderTarget) this.targets.get(fboId.intValue());
    }
}
