package net.labymod.core.client.render.draw.builder;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gui.screen.widget.attributes.BorderRadius;
import net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder;
import net.labymod.api.models.Implements;
import net.labymod.api.util.bounds.Rectangle;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/draw/builder/DefaultRoundedGeometryBuilder.class */
@Singleton
@Implements(RoundedGeometryBuilder.class)
public class DefaultRoundedGeometryBuilder implements RoundedGeometryBuilder {
    private float left;
    private float top;
    private float right;
    private float bottom;
    private float leftTopRadius;
    private float rightTopRadius;
    private float leftBottomRadius;
    private float rightBottomRadius;
    private float lowerEdgeSoftness;
    private float upperEdgeSoftness;
    private float borderThickness;
    private float borderSoftness;
    private int borderColor;

    @Inject
    public DefaultRoundedGeometryBuilder() {
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder left(float left) {
        this.left = left;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder top(float top) {
        this.top = top;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder right(float right) {
        this.right = right;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder bottom(float bottom) {
        this.bottom = bottom;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder pos(Rectangle rectangle) {
        return pos(rectangle.getLeft(), rectangle.getTop(), rectangle.getRight(), rectangle.getBottom());
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder pos(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder leftTopRadius(float leftTopRadius) {
        this.leftTopRadius = leftTopRadius;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder rightTopRadius(float rightTopRadius) {
        this.rightTopRadius = rightTopRadius;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder leftBottomRadius(float leftBottomRadius) {
        this.leftBottomRadius = leftBottomRadius;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder rightBottomRadius(float rightBottomRadius) {
        this.rightBottomRadius = rightBottomRadius;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder radius(float leftTopRadius, float rightTopRadius, float leftBottomRadius, float rightBottomRadius) {
        this.leftTopRadius = leftTopRadius;
        this.rightTopRadius = rightTopRadius;
        this.leftBottomRadius = leftBottomRadius;
        this.rightBottomRadius = rightBottomRadius;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder lowerEdgeSoftness(float lowerEdgeSoftness) {
        this.lowerEdgeSoftness = lowerEdgeSoftness;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder upperEdgeSoftness(float upperEdgeSoftness) {
        this.upperEdgeSoftness = upperEdgeSoftness;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder borderThickness(float borderThickness) {
        this.borderThickness = borderThickness;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder borderSoftness(float borderSoftness) {
        this.borderSoftness = borderSoftness;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder borderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder borderRadius(BorderRadius borderRadius) {
        if (borderRadius == null) {
            return this;
        }
        this.leftTopRadius = borderRadius.getLeftTop();
        this.rightTopRadius = borderRadius.getRightTop();
        this.leftBottomRadius = borderRadius.getLeftBottom();
        this.rightBottomRadius = borderRadius.getRightBottom();
        this.upperEdgeSoftness = borderRadius.getUpperEdgeSoftness();
        this.lowerEdgeSoftness = borderRadius.getLowerEdgeSoftness();
        return this;
    }

    @Override // net.labymod.api.client.render.draw.builder.RoundedGeometryBuilder
    public RoundedGeometryBuilder.RoundedData build() {
        RoundedGeometryBuilder.RoundedData data = new RoundedGeometryBuilder.RoundedData(this.left, this.top, this.right, this.bottom, this.leftTopRadius, this.rightTopRadius, this.leftBottomRadius, this.rightBottomRadius, this.lowerEdgeSoftness, this.upperEdgeSoftness, this.borderThickness, this.borderSoftness, this.borderColor);
        invalidate();
        return data;
    }

    private void invalidate() {
        this.left = 0.0f;
        this.top = 0.0f;
        this.right = 0.0f;
        this.bottom = 0.0f;
        this.leftTopRadius = 0.0f;
        this.rightTopRadius = 0.0f;
        this.leftBottomRadius = 0.0f;
        this.rightBottomRadius = 0.0f;
        this.lowerEdgeSoftness = 0.0f;
        this.upperEdgeSoftness = 0.0f;
        this.borderThickness = 0.0f;
        this.borderSoftness = 0.0f;
        this.borderColor = -1;
    }
}
