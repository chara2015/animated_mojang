package net.labymod.v1_21_3.laby3d.buffer;

import net.labymod.api.laby3d.buffer.GameVertexConsumer;
import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/laby3d/buffer/VersionedGameVertexConsumer.class */
public class VersionedGameVertexConsumer implements GameVertexConsumer {
    private static final int MASK = 255;
    private static final int SHIFT = 16;
    private final fgw delegate;

    public VersionedGameVertexConsumer(fgw delegate) {
        this.delegate = delegate;
    }

    @NotNull
    public VertexConsumer addVertex(float x, float y, float z) {
        this.delegate.a(x, y, z);
        return this;
    }

    @NotNull
    public VertexConsumer setColor(int red, int green, int blue, int alpha) {
        this.delegate.a(red, green, blue, alpha);
        return this;
    }

    @NotNull
    public VertexConsumer setColor(float red, float green, float blue, float alpha) {
        this.delegate.a(red, green, blue, alpha);
        return this;
    }

    @NotNull
    public VertexConsumer setColor(int argb) {
        this.delegate.a(argb);
        return this;
    }

    @NotNull
    public VertexConsumer setUv(float u, float v) {
        this.delegate.a(u, v);
        return this;
    }

    @NotNull
    public VertexConsumer setPackedLight(int lightCoords) {
        short u = (short) (lightCoords & 255);
        short v = (short) ((lightCoords >> 16) & 255);
        this.delegate.b(u, v);
        return this;
    }

    @NotNull
    public VertexConsumer setPackedOverlay(int overlayCoords) {
        short u = (short) (overlayCoords & 255);
        short v = (short) ((overlayCoords >> 16) & 255);
        this.delegate.a(u, v);
        return this;
    }

    @NotNull
    public VertexConsumer setUv(short i, short i1) {
        return this;
    }

    @NotNull
    public VertexConsumer setNormal(float x, float y, float z) {
        this.delegate.b(x, y, z);
        return this;
    }

    @NotNull
    public VertexConsumer setInt(int i) {
        return this;
    }

    @NotNull
    public VertexConsumer setShort(short i) {
        return this;
    }

    @NotNull
    public VertexConsumer setFloat(float v) {
        return this;
    }
}
