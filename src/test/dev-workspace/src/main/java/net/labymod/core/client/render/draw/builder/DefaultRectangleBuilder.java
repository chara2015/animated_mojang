package net.labymod.core.client.render.draw.builder;

import net.labymod.api.client.render.draw.builder.RectangleBuilder;
import net.labymod.core.client.render.draw.property.IntProperty;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/builder/DefaultRectangleBuilder.class */
public class DefaultRectangleBuilder<T extends RectangleBuilder<T>> extends DefaultRendererBuilder<T> implements RectangleBuilder<T> {
    protected float width;
    protected float height;
    protected float leftTopRadius;
    protected float rightTopRadius;
    protected float leftBottomRadius;
    protected float rightBottomRadius;
    protected float lowerEdgeSoftness;
    protected float upperEdgeSoftness;
    protected float borderThickness;
    protected float borderSoftness;
    protected int borderColor;
    protected final IntProperty leftColor = new IntProperty();
    protected final IntProperty rightColor = new IntProperty();
    protected final IntProperty topColor = new IntProperty();
    protected final IntProperty bottomColor = new IntProperty();

    protected DefaultRectangleBuilder() {
        resetBuilder();
    }

    @Override // net.labymod.api.client.render.draw.builder.RectangleBuilder
    public T size(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public T rounded(float leftTopRadius, float rightTopRadius, float leftBottomRadius, float rightBottomRadius) {
        this.leftTopRadius = leftTopRadius;
        this.rightTopRadius = rightTopRadius;
        this.leftBottomRadius = leftBottomRadius;
        this.rightBottomRadius = rightBottomRadius;
        return this;
    }

    public T lowerEdgeSoftness(float softness) {
        this.lowerEdgeSoftness = softness;
        return this;
    }

    public T upperEdgeSoftness(float softness) {
        this.upperEdgeSoftness = softness;
        return this;
    }

    public T borderThickness(float thickness) {
        this.borderThickness = thickness;
        return this;
    }

    public T borderSoftness(float softness) {
        this.borderSoftness = softness;
        return this;
    }

    public T borderColor(int color) {
        this.borderColor = color;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RectangleBuilder
    public T gradientHorizontal(int leftColor, int rightColor) {
        this.leftColor.setValue(leftColor);
        this.rightColor.setValue(rightColor);
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RectangleBuilder
    public T gradientVertical(int topColor, int bottomColor) {
        this.topColor.setValue(topColor);
        this.bottomColor.setValue(bottomColor);
        return this;
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRendererBuilder, net.labymod.api.client.render.draw.builder.RendererBuilder
    public void validateBuilder() {
        super.validateBuilder();
        if (this.leftColor.isSet() && this.topColor.isSet()) {
            throw new IllegalStateException("Cannot use horizontalGradient() and verticalGradient() at the same time");
        }
    }

    @Override // net.labymod.core.client.render.draw.builder.DefaultRendererBuilder, net.labymod.api.client.render.draw.builder.RendererBuilder
    public void resetBuilder() {
        super.resetBuilder();
        this.width = 0.0f;
        this.height = 0.0f;
        this.leftTopRadius = 0.0f;
        this.rightTopRadius = 0.0f;
        this.leftBottomRadius = 0.0f;
        this.rightBottomRadius = 0.0f;
        this.lowerEdgeSoftness = 0.0f;
        this.upperEdgeSoftness = 0.0f;
        this.borderThickness = 0.0f;
        this.borderSoftness = 0.0f;
        this.borderColor = 0;
        if (this.leftColor != null) {
            this.leftColor.reset();
        }
        if (this.rightColor != null) {
            this.rightColor.reset();
        }
        if (this.topColor != null) {
            this.topColor.reset();
        }
        if (this.bottomColor != null) {
            this.bottomColor.reset();
        }
    }
}
