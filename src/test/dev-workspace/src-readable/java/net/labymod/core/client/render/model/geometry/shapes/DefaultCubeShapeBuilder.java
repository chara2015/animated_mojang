package net.labymod.core.client.render.model.geometry.shapes;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import net.labymod.api.client.render.model.geometry.shapes.CubeShape;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.Direction;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/geometry/shapes/DefaultCubeShapeBuilder.class */
@Implements(CubeShape.Builder.class)
public class DefaultCubeShapeBuilder implements CubeShape.Builder {
    private float originX;
    private float originY;
    private float originZ;
    private float sizeX;
    private float sizeY;
    private float sizeZ;
    private float growX;
    private float growY;
    private float growZ;
    private int textureOffsetU;
    private int textureOffsetV;
    private boolean mirror;
    private int textureWidth = 64;
    private int textureHeight = 32;
    private Set<Direction> visibleFaces = EnumSet.allOf(Direction.class);

    @Override // net.labymod.api.client.render.model.geometry.shapes.CubeShape.Builder
    @NotNull
    public CubeShape.Builder origin(float x, float y, float z) {
        this.originX = x;
        this.originY = y;
        this.originZ = z;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CubeShape.Builder
    @NotNull
    public CubeShape.Builder size(float width, float height, float depth) {
        this.sizeX = width;
        this.sizeY = height;
        this.sizeZ = depth;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CubeShape.Builder
    @NotNull
    public CubeShape.Builder grow(float growX, float growY, float growZ) {
        this.growX = growX;
        this.growY = growY;
        this.growZ = growZ;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CubeShape.Builder
    @NotNull
    public CubeShape.Builder textureOffset(int u, int v) {
        this.textureOffsetU = u;
        this.textureOffsetV = v;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CubeShape.Builder
    @NotNull
    public CubeShape.Builder textureSize(int width, int height) {
        this.textureWidth = width;
        this.textureHeight = height;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CubeShape.Builder
    @NotNull
    public CubeShape.Builder mirror(boolean mirror) {
        this.mirror = mirror;
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CubeShape.Builder
    @NotNull
    public CubeShape.Builder visibleFaces(@NotNull Set<Direction> faces) {
        this.visibleFaces = EnumSet.copyOf((Collection) faces);
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CubeShape.Builder
    @NotNull
    public CubeShape.Builder addVisibleFace(@NotNull Direction direction) {
        this.visibleFaces.add(direction);
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CubeShape.Builder
    @NotNull
    public CubeShape.Builder removeVisibleFace(@NotNull Direction direction) {
        this.visibleFaces.remove(direction);
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CubeShape.Builder
    @NotNull
    public CubeShape build() {
        return new DefaultCubeShape(this.textureOffsetU, this.textureOffsetV, this.originX, this.originY, this.originZ, this.sizeX, this.sizeY, this.sizeZ, this.growX, this.growY, this.growZ, this.mirror, this.textureWidth, this.textureHeight, this.visibleFaces);
    }
}
