package net.labymod.core.client.render.model.geometry.shapes;

import net.labymod.api.client.render.model.geometry.BoundingBox;
import net.labymod.api.client.render.model.geometry.ShapeQuad;
import net.labymod.api.client.render.model.geometry.ShapeVertex;
import net.labymod.api.client.render.model.geometry.shapes.PlaneShape;
import net.labymod.api.util.math.Direction;
import net.labymod.core.client.render.model.geometry.DefaultBoundingBox;
import net.labymod.core.client.render.model.geometry.DefaultShapeQuad;
import net.labymod.core.client.render.model.geometry.DefaultShapeVertex;
import net.labymod.core.client.render.model.geometry.DefaultUVBounds;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/geometry/shapes/DefaultPlaneShape.class */
public class DefaultPlaneShape implements PlaneShape {
    private final ShapeQuad[] quads;
    private final BoundingBox bounds;
    private final float width;
    private final float height;
    private final Direction normalDirection;
    private final boolean doubleSided;

    public DefaultPlaneShape(float originX, float originY, float originZ, float width, float height, @NotNull Direction normalDirection, boolean doubleSided, int texU, int texV, int texWidth, int texHeight) {
        this.width = width;
        this.height = height;
        this.normalDirection = normalDirection;
        this.doubleSided = doubleSided;
        ShapeVertex[] vertices = calculateVertices(originX, originY, originZ, width, height, normalDirection);
        DefaultShapeQuad frontQuad = new DefaultShapeQuad(vertices, texU, texV, texU + width, texV + height, texWidth, texHeight, false, normalDirection);
        if (doubleSided) {
            ShapeVertex[] backVertices = {vertices[3], vertices[2], vertices[1], vertices[0]};
            DefaultShapeQuad backQuad = new DefaultShapeQuad(backVertices, new Vector3f(normalDirection.getNormal()).negate(), new DefaultUVBounds(texU / texWidth, texV / texHeight, (texU + width) / texWidth, (texV + height) / texHeight));
            this.quads = new ShapeQuad[]{frontQuad, backQuad};
        } else {
            this.quads = new ShapeQuad[]{frontQuad};
        }
        this.bounds = calculateBounds(originX, originY, originZ, width, height, normalDirection);
    }

    private ShapeVertex[] calculateVertices(float x, float y, float z, float width, float height, Direction direction) {
        switch (direction) {
            case NORTH:
                return new ShapeVertex[]{new DefaultShapeVertex(x + width, y, z, 0.0f, 0.0f), new DefaultShapeVertex(x, y, z, 0.0f, 0.0f), new DefaultShapeVertex(x, y + height, z, 0.0f, 0.0f), new DefaultShapeVertex(x + width, y + height, z, 0.0f, 0.0f)};
            case SOUTH:
                return new ShapeVertex[]{new DefaultShapeVertex(x, y, z, 0.0f, 0.0f), new DefaultShapeVertex(x + width, y, z, 0.0f, 0.0f), new DefaultShapeVertex(x + width, y + height, z, 0.0f, 0.0f), new DefaultShapeVertex(x, y + height, z, 0.0f, 0.0f)};
            case EAST:
                return new ShapeVertex[]{new DefaultShapeVertex(x, y, z, 0.0f, 0.0f), new DefaultShapeVertex(x, y, z + width, 0.0f, 0.0f), new DefaultShapeVertex(x, y + height, z + width, 0.0f, 0.0f), new DefaultShapeVertex(x, y + height, z, 0.0f, 0.0f)};
            case WEST:
                return new ShapeVertex[]{new DefaultShapeVertex(x, y, z + width, 0.0f, 0.0f), new DefaultShapeVertex(x, y, z, 0.0f, 0.0f), new DefaultShapeVertex(x, y + height, z, 0.0f, 0.0f), new DefaultShapeVertex(x, y + height, z + width, 0.0f, 0.0f)};
            case UP:
                return new ShapeVertex[]{new DefaultShapeVertex(x, y, z, 0.0f, 0.0f), new DefaultShapeVertex(x + width, y, z, 0.0f, 0.0f), new DefaultShapeVertex(x + width, y, z + height, 0.0f, 0.0f), new DefaultShapeVertex(x, y, z + height, 0.0f, 0.0f)};
            case DOWN:
            default:
                return new ShapeVertex[]{new DefaultShapeVertex(x, y, z + height, 0.0f, 0.0f), new DefaultShapeVertex(x + width, y, z + height, 0.0f, 0.0f), new DefaultShapeVertex(x + width, y, z, 0.0f, 0.0f), new DefaultShapeVertex(x, y, z, 0.0f, 0.0f)};
        }
    }

    private BoundingBox calculateBounds(float x, float y, float z, float width, float height, Direction direction) {
        switch (direction) {
            case NORTH:
            case SOUTH:
                return new DefaultBoundingBox(x, y, z - 0.001f, x + width, y + height, z + 0.001f);
            case EAST:
            case WEST:
                return new DefaultBoundingBox(x - 0.001f, y, z, x + 0.001f, y + height, z + width);
            case UP:
            case DOWN:
            default:
                return new DefaultBoundingBox(x, y - 0.001f, z, x + width, y + 0.001f, z + height);
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

    @Override // net.labymod.api.client.render.model.geometry.shapes.PlaneShape
    public float getWidth() {
        return this.width;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.PlaneShape
    public float getHeight() {
        return this.height;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.PlaneShape
    @NotNull
    public Direction getNormalDirection() {
        return this.normalDirection;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.PlaneShape
    public boolean isDoubleSided() {
        return this.doubleSided;
    }
}
