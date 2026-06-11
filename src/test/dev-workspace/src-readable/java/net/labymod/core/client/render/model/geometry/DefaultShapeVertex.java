package net.labymod.core.client.render.model.geometry;

import net.labymod.api.client.render.model.geometry.ShapeVertex;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/geometry/DefaultShapeVertex.class */
public class DefaultShapeVertex implements ShapeVertex {
    private final Vector3f position;
    private final Vector2f uv;

    public DefaultShapeVertex(float x, float y, float z, float u, float v) {
        this(new Vector3f(x, y, z), new Vector2f(u, v));
    }

    public DefaultShapeVertex(@NotNull Vector3f position, float u, float v) {
        this(position, new Vector2f(u, v));
    }

    public DefaultShapeVertex(@NotNull Vector3f position, @NotNull Vector2f uv) {
        this.position = position;
        this.uv = uv;
    }

    @Override // net.labymod.api.client.render.model.geometry.ShapeVertex
    @NotNull
    public Vector3f getPosition() {
        return this.position;
    }

    @Override // net.labymod.api.client.render.model.geometry.ShapeVertex
    @NotNull
    public Vector2f getUV() {
        return this.uv;
    }

    @Override // net.labymod.api.client.render.model.geometry.ShapeVertex
    @NotNull
    public ShapeVertex remap(float u, float v) {
        return new DefaultShapeVertex(this.position, u, v);
    }
}
