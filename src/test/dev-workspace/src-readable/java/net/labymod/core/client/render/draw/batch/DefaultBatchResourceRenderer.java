package net.labymod.core.client.render.draw.batch;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.models.Implements;
import net.labymod.core.client.render.draw.builder.DefaultResourceBuilder;
import net.labymod.laby3d.api.pipeline.RenderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/batch/DefaultBatchResourceRenderer.class */
@Singleton
@Implements(BatchResourceRenderer.class)
public class DefaultBatchResourceRenderer extends DefaultResourceBuilder<BatchResourceRenderer> implements BatchResourceRenderer {
    private final ResourceRenderContext resourceRenderContext;

    @Inject
    public DefaultBatchResourceRenderer(ResourceRenderContext resourceRenderContext) {
        this.resourceRenderContext = resourceRenderContext;
    }

    @Override // net.labymod.api.client.render.draw.batch.BatchResourceRenderer
    public BatchResourceRenderer beginBatch(Stack stack, ResourceLocation resourceLocation) {
        this.resourceLocation = null;
        this.resourceRenderContext.begin(stack);
        this.resourceLocation = resourceLocation;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.batch.BatchResourceRenderer
    public BatchResourceRenderer beginBatch(Stack stack, RenderState renderState, ResourceLocation resourceLocation) {
        this.resourceLocation = null;
        this.resourceRenderContext.begin(stack, renderState);
        this.resourceLocation = resourceLocation;
        return this;
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultResourceBuilder, net.labymod.api.client.render.draw.builder.ResourceBuilder
    public BatchResourceRenderer texture(ResourceLocation resourceLocation) {
        throw new UnsupportedOperationException("Cannot change the texture of a MultiResourceRenderer");
    }

    @Override // net.labymod.api.client.render.draw.batch.BatchRenderer
    public void upload() {
        ShaderTextures.setShaderTexture(0, this.resourceLocation);
        this.resourceRenderContext.uploadToBuffer();
    }

    @Override // net.labymod.api.client.render.draw.batch.BatchRenderer
    public void upload(RenderState renderState) {
        ShaderTextures.setShaderTexture(0, this.resourceLocation);
        this.resourceRenderContext.uploadToBuffer(renderState);
    }

    @Override // net.labymod.api.client.render.draw.batch.BatchRenderer
    public BatchResourceRenderer build() {
        validateBuilder();
        if (this.spriteXChanged) {
            float resolutionWidth = this.resolutionWidthChanged ? this.resolutionWidth : 256.0f;
            float resolutionHeight = this.resolutionHeightChanged ? this.resolutionHeight : 256.0f;
            this.resourceRenderContext.blit(this.x, this.y, this.width, this.height, this.spriteX, this.spriteY, this.spriteWidth, this.spriteHeight, resolutionWidth, resolutionHeight, this.color);
        } else {
            this.resourceRenderContext.directBlit(this.x, this.y, this.width, this.height, this.offset, this.color);
        }
        resetBuilder();
        return this;
    }
}
