package net.labymod.api.client.render.model.geometry;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/ShapeVertex.class */
public interface ShapeVertex {
    @NotNull
    Vector3f getPosition();

    @NotNull
    Vector2f getUV();

    @NotNull
    ShapeVertex remap(float f, float f2);

    @NotNull
    default ShapeVertex remap(@NotNull Vector2f uv) {
        return remap(uv.x(), uv.y());
    }
}
