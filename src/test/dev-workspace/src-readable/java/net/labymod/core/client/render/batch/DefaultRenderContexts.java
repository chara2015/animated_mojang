package net.labymod.core.client.render.batch;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.render.batch.LineRenderContext;
import net.labymod.api.client.render.batch.RectangleRenderContext;
import net.labymod.api.client.render.batch.RenderContexts;
import net.labymod.api.client.render.batch.ResourceRenderContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/batch/DefaultRenderContexts.class */
@Singleton
@Implements(RenderContexts.class)
public class DefaultRenderContexts implements RenderContexts {
    private final RectangleRenderContext rectangleRenderContext;
    private final ResourceRenderContext resourceRenderContext;
    private final LineRenderContext lineRenderContext;
    private Stack currentStack;

    @Inject
    public DefaultRenderContexts(ResourceRenderContext resourceRenderContext, LineRenderContext lineRenderContext, RectangleRenderContext rectangleRenderContext) {
        this.lineRenderContext = lineRenderContext;
        this.rectangleRenderContext = rectangleRenderContext;
        this.resourceRenderContext = resourceRenderContext;
    }

    @Override // net.labymod.api.client.render.batch.RenderContexts
    public RectangleRenderContext rectangleRenderContext() {
        return this.rectangleRenderContext;
    }

    @Override // net.labymod.api.client.render.batch.RenderContexts
    public ResourceRenderContext resourceRenderContext() {
        return this.resourceRenderContext;
    }

    @Override // net.labymod.api.client.render.batch.RenderContexts
    public LineRenderContext lineRenderContext() {
        return this.lineRenderContext;
    }

    @Override // net.labymod.api.client.render.batch.RenderContexts
    public Stack currentStack() {
        return this.currentStack;
    }

    public void setCurrentStack(Stack currentStack) {
        this.currentStack = currentStack;
    }
}
