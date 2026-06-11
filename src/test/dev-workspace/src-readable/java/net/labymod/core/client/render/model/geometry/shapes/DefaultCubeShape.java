package net.labymod.core.client.render.model.geometry.shapes;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import net.labymod.api.client.render.model.geometry.BoundingBox;
import net.labymod.api.client.render.model.geometry.ShapeQuad;
import net.labymod.api.client.render.model.geometry.ShapeVertex;
import net.labymod.api.client.render.model.geometry.shapes.CubeShape;
import net.labymod.api.util.math.Direction;
import net.labymod.core.client.render.model.geometry.DefaultBoundingBox;
import net.labymod.core.client.render.model.geometry.DefaultShapeQuad;
import net.labymod.core.client.render.model.geometry.DefaultShapeVertex;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/geometry/shapes/DefaultCubeShape.class */
public class DefaultCubeShape implements CubeShape {
    private static final Set<Direction> ALL_FACES = EnumSet.allOf(Direction.class);
    private final ShapeQuad[] quads;
    private final BoundingBox bounds;
    private final boolean mirror;
    private final Set<Direction> visibleFaces;

    public DefaultCubeShape(int texU, int texV, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float growX, float growY, float growZ, boolean mirror, float texWidth, float texHeight) {
        this(texU, texV, x, y, z, sizeX, sizeY, sizeZ, growX, growY, growZ, mirror, texWidth, texHeight, ALL_FACES);
    }

    public DefaultCubeShape(int texU, int texV, float x, float y, float z, float sizeX, float sizeY, float sizeZ, float growX, float growY, float growZ, boolean mirror, float texWidth, float texHeight, @NotNull Set<Direction> visibleFaces) {
        this.quads = new ShapeQuad[6];
        this.mirror = mirror;
        this.visibleFaces = EnumSet.copyOf((Collection) visibleFaces);
        float minX = x - growX;
        float minY = y - growY;
        float minZ = z - growZ;
        float maxX = x + sizeX + growX;
        float maxY = y + sizeY + growY;
        float maxZ = z + sizeZ + growZ;
        this.bounds = new DefaultBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
        if (mirror) {
            maxX = minX;
            minX = maxX;
        }
        ShapeVertex v000 = new DefaultShapeVertex(minX, minY, minZ, 0.0f, 0.0f);
        ShapeVertex v100 = new DefaultShapeVertex(maxX, minY, minZ, 0.0f, 8.0f);
        ShapeVertex v110 = new DefaultShapeVertex(maxX, maxY, minZ, 8.0f, 8.0f);
        ShapeVertex v010 = new DefaultShapeVertex(minX, maxY, minZ, 8.0f, 0.0f);
        ShapeVertex v001 = new DefaultShapeVertex(minX, minY, maxZ, 0.0f, 0.0f);
        ShapeVertex v101 = new DefaultShapeVertex(maxX, minY, maxZ, 0.0f, 8.0f);
        ShapeVertex v111 = new DefaultShapeVertex(maxX, maxY, maxZ, 8.0f, 8.0f);
        ShapeVertex v011 = new DefaultShapeVertex(minX, maxY, maxZ, 8.0f, 0.0f);
        float u0 = texU;
        float u1 = texU + sizeZ;
        float u2 = texU + sizeZ + sizeX;
        float u3 = texU + sizeZ + sizeX + sizeX;
        float u4 = texU + sizeZ + sizeX + sizeZ;
        float u5 = texU + sizeZ + sizeX + sizeZ + sizeX;
        float v0 = texV;
        float v1 = texV + sizeZ;
        float v2 = texV + sizeZ + sizeY;
        int faceIndex = 0;
        if (visibleFaces.contains(Direction.DOWN)) {
            faceIndex = 0 + 1;
            this.quads[0] = new DefaultShapeQuad(new ShapeVertex[]{v101, v001, v000, v100}, u1, v0, u2, v1, texWidth, texHeight, mirror, Direction.DOWN);
        }
        if (visibleFaces.contains(Direction.UP)) {
            int i = faceIndex;
            faceIndex++;
            this.quads[i] = new DefaultShapeQuad(new ShapeVertex[]{v110, v010, v011, v111}, u2, v1, u3, v0, texWidth, texHeight, mirror, Direction.UP);
        }
        if (visibleFaces.contains(Direction.WEST)) {
            int i2 = faceIndex;
            faceIndex++;
            this.quads[i2] = new DefaultShapeQuad(new ShapeVertex[]{v000, v001, v011, v010}, u0, v1, u1, v2, texWidth, texHeight, mirror, Direction.WEST);
        }
        if (visibleFaces.contains(Direction.NORTH)) {
            int i3 = faceIndex;
            faceIndex++;
            this.quads[i3] = new DefaultShapeQuad(new ShapeVertex[]{v100, v000, v010, v110}, u1, v1, u2, v2, texWidth, texHeight, mirror, Direction.NORTH);
        }
        if (visibleFaces.contains(Direction.EAST)) {
            int i4 = faceIndex;
            faceIndex++;
            this.quads[i4] = new DefaultShapeQuad(new ShapeVertex[]{v101, v100, v110, v111}, u2, v1, u4, v2, texWidth, texHeight, mirror, Direction.EAST);
        }
        if (visibleFaces.contains(Direction.SOUTH)) {
            this.quads[faceIndex] = new DefaultShapeQuad(new ShapeVertex[]{v001, v101, v111, v011}, u4, v1, u5, v2, texWidth, texHeight, mirror, Direction.SOUTH);
        }
    }

    @Override // net.labymod.api.client.render.model.geometry.Shape
    @NotNull
    public ShapeQuad[] getQuads() {
        return this.quads;
    }

    @Override // net.labymod.api.client.render.model.geometry.Shape
    @NotNull
    public BoundingBox getBounds() {
        return this.bounds;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CubeShape
    public boolean isMirror() {
        return this.mirror;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.CubeShape
    @NotNull
    public Set<Direction> getVisibleFaces() {
        return this.visibleFaces;
    }
}
