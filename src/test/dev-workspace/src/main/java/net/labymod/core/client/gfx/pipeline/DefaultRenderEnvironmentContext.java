package net.labymod.core.client.gfx.pipeline;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderAttributesStack;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderPipeline;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/DefaultRenderEnvironmentContext.class */
@Singleton
@Implements(RenderEnvironmentContext.class)
public class DefaultRenderEnvironmentContext implements RenderEnvironmentContext {
    private int currentPackedLight;
    private boolean screenContext;
    private final ScreenContext environmentScreenContext = ScreenContext.create();
    private final ShaderPipeline shaderPipeline = Laby.references().shaderPipeline();
    private final RenderAttributesStack renderAttributesStack = new RenderAttributesStack();

    @Inject
    public DefaultRenderEnvironmentContext() {
    }

    @Override // net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext
    public RenderAttributesStack renderAttributesStack() {
        return this.renderAttributesStack;
    }

    @Override // net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext
    public boolean isScreenContext() {
        return this.screenContext;
    }

    @Override // net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext
    public void setScreenContext(boolean screenContext) {
        this.screenContext = screenContext;
    }

    @Override // net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext
    public ShaderPipeline shaderPipeline() {
        return this.shaderPipeline;
    }

    @Override // net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext
    public int getPackedLight() {
        return this.currentPackedLight;
    }

    @Override // net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext
    public void setPackedLight(int packedLight) {
        this.currentPackedLight = packedLight;
    }

    @Override // net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext
    public ScreenContext screenContext() {
        return this.environmentScreenContext;
    }
}
