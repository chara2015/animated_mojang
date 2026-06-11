package net.labymod.core.client.render.model.geometry.shapes;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.render.model.geometry.ShapeQuad;
import net.labymod.api.client.render.model.geometry.ShapeVertex;
import net.labymod.api.client.render.model.geometry.shapes.MeshShape;
import net.labymod.api.models.Implements;
import net.labymod.core.client.render.model.geometry.DefaultShapeQuad;
import net.labymod.core.client.render.model.geometry.DefaultUVBounds;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/geometry/shapes/DefaultMeshShapeBuilder.class */
@Implements(MeshShape.Builder.class)
public class DefaultMeshShapeBuilder implements MeshShape.Builder {
    private final List<ShapeQuad> quads = new ArrayList();

    @Override // net.labymod.api.client.render.model.geometry.shapes.MeshShape.Builder
    @NotNull
    public MeshShape.Builder addQuad(@NotNull ShapeVertex[] vertices, @NotNull Vector3f normal) {
        if (vertices == null || vertices.length != 4) {
            throw new IllegalArgumentException("Quad must have exactly 4 vertices");
        }
        this.quads.add(new DefaultShapeQuad((ShapeVertex[]) vertices.clone(), new Vector3f(normal), new DefaultUVBounds(0.0f, 0.0f, 1.0f, 1.0f)));
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.MeshShape.Builder
    @NotNull
    public MeshShape.Builder addQuad(@NotNull ShapeVertex v0, @NotNull ShapeVertex v1, @NotNull ShapeVertex v2, @NotNull ShapeVertex v3) {
        Vector3f edge1 = new Vector3f(v1.getPosition()).sub(v0.getPosition());
        Vector3f edge2 = new Vector3f(v2.getPosition()).sub(v0.getPosition());
        Vector3f normal = new Vector3f();
        edge1.cross(edge2, normal).normalize();
        return addQuad(new ShapeVertex[]{v0, v1, v2, v3}, normal);
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.MeshShape.Builder
    @NotNull
    public MeshShape.Builder addTriangle(@NotNull ShapeVertex v0, @NotNull ShapeVertex v1, @NotNull ShapeVertex v2, @NotNull Vector3f normal) {
        return addQuad(new ShapeVertex[]{v0, v1, v2, v0}, normal);
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.MeshShape.Builder
    @NotNull
    public MeshShape.Builder addQuad(@NotNull ShapeQuad quad) {
        this.quads.add(quad);
        return this;
    }

    @Override // net.labymod.api.client.render.model.geometry.shapes.MeshShape.Builder
    @NotNull
    public MeshShape build() {
        return new DefaultMeshShape(this.quads);
    }
}
