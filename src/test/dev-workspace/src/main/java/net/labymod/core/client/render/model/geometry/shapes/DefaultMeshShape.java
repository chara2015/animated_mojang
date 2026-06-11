package net.labymod.core.client.render.model.geometry.shapes;

import java.util.List;
import net.labymod.api.client.render.model.geometry.BoundingBox;
import net.labymod.api.client.render.model.geometry.ShapeQuad;
import net.labymod.api.client.render.model.geometry.ShapeVertex;
import net.labymod.api.client.render.model.geometry.shapes.MeshShape;
import net.labymod.core.client.render.model.geometry.DefaultBoundingBox;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/geometry/shapes/DefaultMeshShape.class */
public class DefaultMeshShape implements MeshShape {
    private final ShapeQuad[] quads;
    private final BoundingBox bounds = calculateBounds();

    public DefaultMeshShape(@NotNull List<ShapeQuad> quads) {
        this.quads = (ShapeQuad[]) quads.toArray(new ShapeQuad[0]);
    }

    private BoundingBox calculateBounds() {
        if (this.quads.length == 0) {
            return new DefaultBoundingBox(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        }
        float minX = Float.MAX_VALUE;
        float minY = Float.MAX_VALUE;
        float minZ = Float.MAX_VALUE;
        float maxX = -3.4028235E38f;
        float maxY = -3.4028235E38f;
        float maxZ = -3.4028235E38f;
        for (ShapeQuad quad : this.quads) {
            if (quad != null) {
                for (ShapeVertex vertex : quad.getVertices()) {
                    Vector3f pos = vertex.getPosition();
                    minX = Math.min(minX, pos.x());
                    minY = Math.min(minY, pos.y());
                    minZ = Math.min(minZ, pos.z());
                    maxX = Math.max(maxX, pos.x());
                    maxY = Math.max(maxY, pos.y());
                    maxZ = Math.max(maxZ, pos.z());
                }
            }
        }
        return new DefaultBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
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

    @Override // net.labymod.api.client.render.model.geometry.shapes.MeshShape
    public int getQuadCount() {
        return this.quads.length;
    }
}
