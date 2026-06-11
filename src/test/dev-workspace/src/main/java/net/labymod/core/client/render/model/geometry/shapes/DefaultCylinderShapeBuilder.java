package net.labymod.core.client.render.model.geometry.shapes;

import net.labymod.api.client.render.model.geometry.shapes.CylinderShape;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/geometry/shapes/DefaultCylinderShapeBuilder.class */
@Implements(CylinderShape.Builder.class)
public class DefaultCylinderShapeBuilder implements CylinderShape.Builder {
    private float originX;
    private float originY;
    private float originZ;
    private int textureOffsetU;
    private int textureOffsetV;
    private float radius = 1.0f;
    private float height = 1.0f;
    private int segments = 8;
    private boolean topCap = true;
    private boolean bottomCap = true;
    private int textureWidth = 64;
    private int textureHeight = 32;

    @Override // net.labymod.api.client.render.model.geometry.shapes.CylinderShape.Builder
    @NotNull
    public CylinderShape.Builder origin(float x, float y, float z) {
        this.originX = x;
        this.originY = y;
        this.originZ = z;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CylinderShape.Builder
    @NotNull
    public CylinderShape.Builder radius(float radius) {
        this.radius = radius;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CylinderShape.Builder
    @NotNull
    public CylinderShape.Builder height(float height) {
        this.height = height;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CylinderShape.Builder
    @NotNull
    public CylinderShape.Builder segments(int segments) {
        this.segments = Math.max(3, segments);
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CylinderShape.Builder
    @NotNull
    public CylinderShape.Builder caps(boolean topCap, boolean bottomCap) {
        this.topCap = topCap;
        this.bottomCap = bottomCap;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CylinderShape.Builder
    @NotNull
    public CylinderShape.Builder textureOffset(int u, int v) {
        this.textureOffsetU = u;
        this.textureOffsetV = v;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CylinderShape.Builder
    @NotNull
    public CylinderShape.Builder textureSize(int width, int height) {
        this.textureWidth = width;
        this.textureHeight = height;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CylinderShape.Builder
    @NotNull
    public CylinderShape build() {
        return new DefaultCylinderShape(this.originX, this.originY, this.originZ, this.radius, this.height, this.segments, this.topCap, this.bottomCap, this.textureOffsetU, this.textureOffsetV, this.textureWidth, this.textureHeight);
    }
}
