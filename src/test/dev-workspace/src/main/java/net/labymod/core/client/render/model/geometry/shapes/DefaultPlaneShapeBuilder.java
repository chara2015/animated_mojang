package net.labymod.core.client.render.model.geometry.shapes;

import net.labymod.api.client.render.model.geometry.shapes.PlaneShape;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.Direction;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/geometry/shapes/DefaultPlaneShapeBuilder.class */
@Implements(PlaneShape.Builder.class)
public class DefaultPlaneShapeBuilder implements PlaneShape.Builder {
    private float originX;
    private float originY;
    private float originZ;
    private float width;
    private float height;
    private boolean doubleSided;
    private int textureOffsetU;
    private int textureOffsetV;
    private Direction normalDirection = Direction.SOUTH;
    private int textureWidth = 64;
    private int textureHeight = 32;

    @Override // net.labymod.api.client.render.model.geometry.shapes.PlaneShape.Builder
    @NotNull
    public PlaneShape.Builder origin(float x, float y, float z) {
        this.originX = x;
        this.originY = y;
        this.originZ = z;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.PlaneShape.Builder
    @NotNull
    public PlaneShape.Builder size(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.PlaneShape.Builder
    @NotNull
    public PlaneShape.Builder normal(@NotNull Direction direction) {
        this.normalDirection = direction;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.PlaneShape.Builder
    @NotNull
    public PlaneShape.Builder doubleSided(boolean doubleSided) {
        this.doubleSided = doubleSided;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.PlaneShape.Builder
    @NotNull
    public PlaneShape.Builder textureOffset(int u, int v) {
        this.textureOffsetU = u;
        this.textureOffsetV = v;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.PlaneShape.Builder
    @NotNull
    public PlaneShape.Builder textureSize(int width, int height) {
        this.textureWidth = width;
        this.textureHeight = height;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.PlaneShape.Builder
    @NotNull
    public PlaneShape build() {
        return new DefaultPlaneShape(this.originX, this.originY, this.originZ, this.width, this.height, this.normalDirection, this.doubleSided, this.textureOffsetU, this.textureOffsetV, this.textureWidth, this.textureHeight);
    }
}
