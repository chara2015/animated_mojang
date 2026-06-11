package net.labymod.core.client.gfx.pipeline.shader;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderPipeline;
import net.labymod.api.client.gfx.pipeline.renderer.shadow.ShadowRenderPassContext;
import net.labymod.api.event.client.render.shader.ShaderPipelineContextEvent;
import net.labymod.api.models.Implements;
import net.labymod.core.client.gfx.pipeline.renderer.shadow.DefaultShadowRenderPassContext;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/shader/DefaultShaderPipeline.class */
@Singleton
@Implements(ShaderPipeline.class)
public class DefaultShaderPipeline implements ShaderPipeline {
    private ShadowRenderPassContext cachedContext = new DefaultShadowRenderPassContext();
    private ShaderPipelineContextEvent event;

    @Inject
    public DefaultShaderPipeline() {
        fireShaderPipelineContextEvent();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderPipeline
    public boolean hasActiveShaderPack() {
        return this.event.getActiveShaderPackSupplier().getAsBoolean();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.shader.ShaderPipeline
    public ShadowRenderPassContext shadowRenderPassContext() {
        ShadowRenderPassContext context = this.event.getShadowRenderPassContext();
        if (context != null && this.cachedContext != context) {
            this.cachedContext = context;
        }
        return this.cachedContext;
    }

    public void fireShaderPipelineContextEvent() {
        this.event = new ShaderPipelineContextEvent();
        Laby.fireEvent(this.event);
    }
}
