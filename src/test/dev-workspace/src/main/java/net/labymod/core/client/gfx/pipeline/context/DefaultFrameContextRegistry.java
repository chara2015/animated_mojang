package net.labymod.core.client.gfx.pipeline.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.context.FrameContext;
import net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.util.FboWriteTracker;
import net.labymod.api.models.Implements;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.util.pool.FrameObjectPools;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/context/DefaultFrameContextRegistry.class */
@Singleton
@Implements(FrameContextRegistry.class)
public final class DefaultFrameContextRegistry implements FrameContextRegistry {
    private final List<FrameContext> frameContexts = new ArrayList();
    private final FrameContext recordFrameContext;
    private final Laby3D laby3D;
    private int index;

    @Inject
    public DefaultFrameContextRegistry() {
        this.frameContexts.add(new RenderAttributesFrameContext());
        this.recordFrameContext = new RecordFrameContext();
        this.laby3D = Laby.references().laby3D();
    }

    @Override // net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry
    public void register(FrameContext context) {
        Objects.requireNonNull(context, "context must not be null");
        ThreadSafe.executeOnRenderThread(() -> {
            this.frameContexts.add(context);
        });
    }

    @Override // net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry
    public void beginFrame() {
        this.index++;
        if (this.index != 1) {
            return;
        }
        this.recordFrameContext.beginFrame();
        for (FrameContext frameContext : this.frameContexts) {
            frameContext.beginFrame();
        }
    }

    @Override // net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry
    public void endFrame() {
        this.index--;
        if (this.index != 0) {
            return;
        }
        for (FrameContext frameContext : this.frameContexts) {
            frameContext.endFrame();
        }
        this.recordFrameContext.endFrame();
        FboWriteTracker.INSTANCE.nextFrame();
        this.laby3D.nextFrame();
        FrameObjectPools.instance().resetAll();
    }
}
