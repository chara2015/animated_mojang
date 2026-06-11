package net.labymod.core.client.gfx.pipeline.blaze3d;

import net.labymod.api.util.color.format.ColorFormat;
import org.joml.Vector2f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/blaze3d/Blaze3DVertex.class */
public class Blaze3DVertex {
    private final Vector3f position;
    private final Vector2f uv;
    private int argb;

    public Blaze3DVertex(float x, float y, float z) {
        this(new Vector3f(x, y, z));
    }

    public Blaze3DVertex(Vector3f position) {
        this.uv = new Vector2f();
        this.argb = -1;
        this.position = position;
    }

    public Vector3f getPosition() {
        return this.position;
    }

    public float getX() {
        return this.position.x();
    }

    public float getY() {
        return this.position.y();
    }

    public float getZ() {
        return this.position.z();
    }

    public Vector2f getUv() {
        return this.uv;
    }

    public float getU() {
        return this.uv.x();
    }

    public float getV() {
        return this.uv.y();
    }

    public void setUv(float u, float v) {
        this.uv.set(u, v);
    }

    public int getArgb() {
        return this.argb;
    }

    public void setArgb(int red, int green, int blue, int alpha) {
        setArgb(ColorFormat.ARGB32.pack(red, green, blue, alpha));
    }

    public void setArgb(int argb) {
        this.argb = argb;
    }
}
