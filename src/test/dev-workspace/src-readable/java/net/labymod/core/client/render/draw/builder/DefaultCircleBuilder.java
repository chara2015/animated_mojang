package net.labymod.core.client.render.draw.builder;

import net.labymod.api.client.render.draw.builder.CircleBuilder;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/builder/DefaultCircleBuilder.class */
public class DefaultCircleBuilder<T extends CircleBuilder<T>> extends DefaultRendererBuilder<T> implements CircleBuilder<T> {
    protected float innerRadius;
    protected float outerRadius;
    protected float startingAngle;
    protected float endingAngle;

    protected DefaultCircleBuilder() {
        resetBuilder();
    }

    @Override // net.labymod.api.client.render.draw.builder.CircleBuilder
    public T donutRadius(float innerRadius, float outerRadius) {
        this.innerRadius = innerRadius;
        this.outerRadius = outerRadius;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.CircleBuilder
    public T partial(float startingAngle, float endingAngle) {
        this.startingAngle = startingAngle;
        this.endingAngle = endingAngle;
        return this;
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRendererBuilder, net.labymod.api.client.render.draw.builder.RendererBuilder
    public void resetBuilder() {
        super.resetBuilder();
        this.innerRadius = 0.0f;
        this.outerRadius = 0.0f;
        this.startingAngle = 0.0f;
        this.endingAngle = 360.0f;
    }
}
