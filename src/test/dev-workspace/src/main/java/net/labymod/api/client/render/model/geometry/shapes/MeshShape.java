package net.labymod.api.client.render.model.geometry.shapes;

import net.labymod.api.Laby;
import net.labymod.api.client.render.model.geometry.Shape;
import net.labymod.api.client.render.model.geometry.ShapeQuad;
import net.labymod.api.client.render.model.geometry.ShapeType;
import net.labymod.api.client.render.model.geometry.ShapeVertex;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/shapes/MeshShape.class */
public interface MeshShape extends Shape {

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/shapes/MeshShape$Builder.class */
    @Referenceable
    public interface Builder {
        @NotNull
        Builder addQuad(@NotNull ShapeVertex[] shapeVertexArr, @NotNull Vector3f vector3f);

        @NotNull
        Builder addQuad(@NotNull ShapeVertex shapeVertex, @NotNull ShapeVertex shapeVertex2, @NotNull ShapeVertex shapeVertex3, @NotNull ShapeVertex shapeVertex4);

        @NotNull
        Builder addTriangle(@NotNull ShapeVertex shapeVertex, @NotNull ShapeVertex shapeVertex2, @NotNull ShapeVertex shapeVertex3, @NotNull Vector3f vector3f);

        @NotNull
        Builder addQuad(@NotNull ShapeQuad shapeQuad);

        @NotNull
        MeshShape build();
    }

    int getQuadCount();

    @Override // net.labymod.api.client.render.model.geometry.Shape
    @NotNull
    default ShapeType getType() {
        return ShapeType.MESH;
    }

    @NotNull
    static Builder builder() {
        return Laby.references().meshShapeBuilder();
    }
}
