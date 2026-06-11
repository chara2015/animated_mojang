package net.labymod.core.client.gfx.pipeline;

import java.util.function.Consumer;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.GFXRenderPipeline;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.context.FrameContextRegistry;
import net.labymod.api.client.gui.screen.widget.OverlappingTranslator;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.models.Implements;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/DefaultGFXRenderPipeline.class */
@Singleton
@Implements(GFXRenderPipeline.class)
public class DefaultGFXRenderPipeline implements GFXRenderPipeline {
    private final GFXBridge gfxBridge = Laby.gfx();
    private final RenderEnvironmentContext renderEnvironmentContext = Laby.references().renderEnvironmentContext();
    private final FrameContextRegistry frameContextRegistry = Laby.references().frameContextRegistry();
    private final Laby3D laby3D = Laby.references().laby3D();
    private RenderTarget currentTarget;

    @Override // net.labymod.api.client.gfx.pipeline.GFXRenderPipeline
    public FrameContextRegistry frameContextRegistry() {
        return this.frameContextRegistry;
    }

    @Override // net.labymod.api.client.gfx.pipeline.GFXRenderPipeline
    public GFXBridge gfx() {
        return this.gfxBridge;
    }

    @Override // net.labymod.api.client.gfx.pipeline.GFXRenderPipeline
    public RenderEnvironmentContext renderEnvironmentContext() {
        return this.renderEnvironmentContext;
    }

    @Override // net.labymod.api.client.gfx.pipeline.GFXRenderPipeline
    public void renderToTarget(RenderTarget target, Consumer<RenderTarget> renderer) {
        applyToTarget(target, renderer);
    }

    @Override // net.labymod.api.client.gfx.pipeline.GFXRenderPipeline
    public void applyToTarget(RenderTarget target, Consumer<RenderTarget> renderer) {
        RenderTarget prevRenderTarget = this.currentTarget;
        try {
            this.currentTarget = target;
            renderer.accept(target);
            this.currentTarget = prevRenderTarget;
        } catch (Throwable th) {
            this.currentTarget = prevRenderTarget;
            throw th;
        }
    }

    @Override // net.labymod.api.client.gfx.pipeline.GFXRenderPipeline
    public RenderTarget currentTarget() {
        return this.currentTarget == null ? this.laby3D.resolveDrawRenderTarget() : this.currentTarget;
    }

    @Override // net.labymod.api.client.gfx.pipeline.GFXRenderPipeline
    public OverlappingTranslator overlappingTranslator() {
        return Laby.references().overlappingTranslator();
    }
}
