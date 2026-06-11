package net.labymod.core.client.render.draw.builder;

import net.labymod.api.client.render.draw.builder.RendererBuilder;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/builder/DefaultRendererBuilder.class */
public class DefaultRendererBuilder<T extends RendererBuilder<T>> implements RendererBuilder<T> {
    protected float x;
    protected float y;
    protected int color;

    protected DefaultRendererBuilder() {
        resetBuilder();
    }

    @Override // net.labymod.api.client.render.draw.builder.RendererBuilder
    public T pos(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RendererBuilder
    public T color(int color) {
        this.color = color;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RendererBuilder
    public void validateBuilder() {
    }

    @Override // net.labymod.api.client.render.draw.builder.RendererBuilder
    public void resetBuilder() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.color = -1;
    }
}
