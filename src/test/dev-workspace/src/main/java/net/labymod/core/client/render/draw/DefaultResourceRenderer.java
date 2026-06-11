package net.labymod.core.client.render.draw;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.shader.ShaderTextures;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.render.draw.HeartRenderer;
import net.labymod.api.client.render.draw.PlayerHeadRenderer;
import net.labymod.api.client.render.draw.ResourceRenderer;
import net.labymod.api.client.render.draw.batch.BatchResourceRenderer;
import net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.laby3d.pipeline.RenderStates;
import net.labymod.api.models.Implements;
import net.labymod.api.util.ColorUtil;
import net.labymod.core.client.render.draw.builder.DefaultResourceBuilder;
import net.labymod.laby3d.api.pipeline.RenderState;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/DefaultResourceRenderer.class */
@Singleton
@Implements(ResourceRenderer.class)
public class DefaultResourceRenderer extends DefaultResourceBuilder<ResourceRenderer> implements ResourceRenderer {
    private final ResourceRenderContext resourceRenderContext;
    private final PlayerHeadRenderer playerHeadRenderer;
    private final BatchResourceRenderer batchResourceRenderer;

    @Inject
    public DefaultResourceRenderer(ResourceRenderContext resourceRenderContext, PlayerHeadRenderer playerHeadRenderer, BatchResourceRenderer batchResourceRenderer) {
        this.resourceRenderContext = resourceRenderContext;
        this.playerHeadRenderer = playerHeadRenderer;
        this.batchResourceRenderer = batchResourceRenderer;
    }

    @Override // net.labymod.api.client.render.draw.ResourceRenderer
    public PlayerHeadRenderer head() {
        return this.playerHeadRenderer;
    }

    @Override // net.labymod.api.client.render.draw.ResourceRenderer
    public BatchResourceRenderer beginBatch(Stack stack, ResourceLocation location) {
        return this.batchResourceRenderer.beginBatch(stack, location);
    }

    @Override // net.labymod.api.client.render.draw.ResourceRenderer
    public BatchResourceRenderer beginBatch(Stack stack, RenderState renderState, ResourceLocation location) {
        return this.batchResourceRenderer.beginBatch(stack, renderState, location);
    }

    @Override // net.labymod.api.client.render.draw.ResourceRenderer
    public HeartRenderer heartRenderer() {
        return Laby.references().heartRenderer();
    }

    @Override // net.labymod.api.client.render.draw.builder.DirectRendererBuilder
    public void render(Stack stack) {
        validateBuilder();
        if (ColorUtil.isNoColor(this.color)) {
            resetBuilder();
            return;
        }
        this.resourceRenderContext.begin(stack);
        if (this.spriteXChanged) {
            float resolutionWidth = this.resolutionWidthChanged ? this.resolutionWidth : 256.0f;
            float resolutionHeight = this.resolutionHeightChanged ? this.resolutionHeight : 256.0f;
            this.resourceRenderContext.blit(this.x, this.y, this.width, this.height, this.spriteX, this.spriteY, this.spriteWidth, this.spriteHeight, resolutionWidth, resolutionHeight, this.color);
        } else {
            this.resourceRenderContext.directBlit(this.x, this.y, this.width, this.height, this.offset, this.color);
        }
        ShaderTextures.setShaderTexture(0, this.resourceLocation);
        if (this.data == null) {
            this.resourceRenderContext.uploadToBuffer();
        } else {
            this.resourceRenderContext.uploadToBuffer(RenderStates.GUI_TEXTURED_ROUNDED, command -> {
                RoundedGeometryBuilder.RoundedDataApplier.apply(this.data, command);
            });
        }
        resetBuilder();
    }

    @Override // net.labymod.api.client.render.draw.builder.DirectRendererBuilder
    public void render(Stack stack, RenderState renderState) {
        if (renderState == null) {
            render(stack);
            return;
        }
        validateBuilder();
        if (ColorUtil.isNoColor(this.color)) {
            resetBuilder();
            return;
        }
        this.resourceRenderContext.begin(stack, renderState);
        if (this.spriteXChanged) {
            float resolutionWidth = this.resolutionWidthChanged ? this.resolutionWidth : 256.0f;
            float resolutionHeight = this.resolutionHeightChanged ? this.resolutionHeight : 256.0f;
            this.resourceRenderContext.blit(this.x, this.y, this.width, this.height, this.spriteX, this.spriteY, this.spriteWidth, this.spriteHeight, resolutionWidth, resolutionHeight, this.color);
        } else {
            this.resourceRenderContext.directBlit(this.x, this.y, this.width, this.height, this.offset, this.color);
        }
        ShaderTextures.setShaderTexture(0, this.resourceLocation);
        if (this.data == null) {
            this.resourceRenderContext.uploadToBuffer(renderState);
        } else {
            this.resourceRenderContext.uploadToBuffer(renderState, command -> {
                RoundedGeometryBuilder.RoundedDataApplier.apply(this.data, command);
            });
        }
        resetBuilder();
    }
}
