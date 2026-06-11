package net.labymod.core.client.render.model.geometry.shapes;

import net.labymod.api.client.render.model.geometry.shapes.SphereShape;
import net.labymod.api.models.Implements;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/geometry/shapes/DefaultSphereShapeBuilder.class */
@Implements(SphereShape.Builder.class)
public class DefaultSphereShapeBuilder implements SphereShape.Builder {
    private float originX;
    private float originY;
    private float originZ;
    private int textureOffsetU;
    private int textureOffsetV;
    private float radius = 1.0f;
    private int longitudeSegments = 16;
    private int latitudeSegments = 8;
    private int textureWidth = 64;
    private int textureHeight = 32;

    @Override // net.labymod.api.client.render.model.geometry.shapes.SphereShape.Builder
    @NotNull
    public SphereShape.Builder origin(float x, float y, float z) {
        this.originX = x;
        this.originY = y;
        this.originZ = z;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.SphereShape.Builder
    @NotNull
    public SphereShape.Builder radius(float radius) {
        this.radius = radius;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.SphereShape.Builder
    @NotNull
    public SphereShape.Builder segments(int longitudeSegments, int latitudeSegments) {
        this.longitudeSegments = Math.max(3, longitudeSegments);
        this.latitudeSegments = Math.max(2, latitudeSegments);
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.SphereShape.Builder
    @NotNull
    public SphereShape.Builder textureOffset(int u, int v) {
        this.textureOffsetU = u;
        this.textureOffsetV = v;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.SphereShape.Builder
    @NotNull
    public SphereShape.Builder textureSize(int width, int height) {
        this.textureWidth = width;
        this.textureHeight = height;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.SphereShape.Builder
    @NotNull
    public SphereShape build() {
        return new DefaultSphereShape(this.originX, this.originY, this.originZ, this.radius, this.longitudeSegments, this.latitudeSegments, this.textureOffsetU, this.textureOffsetV, this.textureWidth, this.textureHeight);
    }
}
