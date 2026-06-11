package net.labymod.core.client.render.model.box;

import net.labymod.api.client.render.model.box.ModelBoxVertex;
import org.joml.Vector2f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/model/box/DefaultModelBoxVertex.class */
public class DefaultModelBoxVertex implements ModelBoxVertex {
    private final Vector3f position;
    private final Vector2f uv;

    public DefaultModelBoxVertex(float x, float y, float z, float texU, float texV) {
        this(new Vector3f(x, y, z), texU, texV);
    }

    public DefaultModelBoxVertex(Vector3f position, float textureU, float textureV) {
        this(position, new Vector2f(textureU, textureV));
    }

    public DefaultModelBoxVertex(Vector3f position, Vector2f uv) {
        this.position = position;
        this.uv = uv;
    }

    @Override // net.labymod.api.client.render.model.box.ModelBoxVertex
    public ModelBoxVertex remap(float u, float v) {
        return remap(new Vector2f(u, v));
    }

    @Override // net.labymod.api.client.render.model.box.ModelBoxVertex
    public ModelBoxVertex remap(Vector2f uv) {
        return new DefaultModelBoxVertex(this.position, uv);
    }

    @Override // net.labymod.api.client.render.model.box.ModelBoxVertex
    public Vector3f getPosition() {
        return this.position;
    }

    @Override // net.labymod.api.client.render.model.box.ModelBoxVertex
    public Vector2f getUV() {
        return this.uv;
    }
}
