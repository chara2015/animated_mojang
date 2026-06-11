package net.labymod.core.client.render.model.geometry;

import net.labymod.api.client.render.model.geometry.ShapeQuad;
import net.labymod.api.client.render.model.geometry.ShapeVertex;
import net.labymod.api.client.render.model.geometry.UVBounds;
import net.labymod.api.util.math.Axis;
import net.labymod.api.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/geometry/DefaultShapeQuad.class */
public class DefaultShapeQuad implements ShapeQuad {
    private final ShapeVertex[] vertices;
    private final Vector3f normal;
    private final UVBounds uvBounds;
    private boolean visible = true;

    public DefaultShapeQuad(@NotNull ShapeVertex[] vertices, @NotNull Vector3f normal, @NotNull UVBounds uvBounds) {
        this.vertices = vertices;
        this.normal = normal;
        this.uvBounds = uvBounds;
    }

    public DefaultShapeQuad(@NotNull ShapeVertex[] vertices, float minU, float minV, float maxU, float maxV, float textureWidth, float textureHeight, boolean mirror, @NotNull Direction direction) {
        Direction normalDirection;
        this.uvBounds = new DefaultUVBounds(minU / textureWidth, minV / textureHeight, maxU / textureWidth, maxV / textureHeight);
        this.vertices = vertices;
        remapVertex(0, maxU / textureWidth, minV / textureHeight);
        remapVertex(1, minU / textureWidth, minV / textureHeight);
        remapVertex(2, minU / textureWidth, maxV / textureHeight);
        remapVertex(3, maxU / textureWidth, maxV / textureHeight);
        if (mirror) {
            normalDirection = mirrorDirection(direction);
            int length = vertices.length;
            for (int index = 0; index < length / 2; index++) {
                ShapeVertex vertex = this.vertices[index];
                this.vertices[index] = this.vertices[(length - 1) - index];
                this.vertices[(length - 1) - index] = vertex;
            }
        } else {
            normalDirection = direction;
        }
        this.normal = new Vector3f(normalDirection.getNormal());
    }

    private Direction mirrorDirection(Direction direction) {
        return direction.getAxis() == Axis.X ? direction.getOpposite() : direction;
    }

    private void remapVertex(int index, float u, float v) {
        this.vertices[index] = this.vertices[index].remap(u, v);
    }

    @Override // net.labymod.api.client.render.model.geometry.ShapeQuad
    @NotNull
    public ShapeVertex[] getVertices() {
        return this.vertices;
    }

    @Override // net.labymod.api.client.render.model.geometry.ShapeQuad
    @NotNull
    public Vector3f getNormal() {
        return this.normal;
    }

    @Override // net.labymod.api.client.render.model.geometry.ShapeQuad
    public boolean isVisible() {
        return this.visible;
    }

    @Override // net.labymod.api.client.render.model.geometry.ShapeQuad
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override // net.labymod.api.client.render.model.geometry.ShapeQuad
    @NotNull
    public UVBounds getUVBounds() {
        return this.uvBounds;
    }
}
