package net.labymod.v26_1_1.laby3d.buffer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.labymod.api.laby3d.buffer.GameVertexConsumer;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/laby3d/buffer/VersionedGameVertexConsumer.class */
public class VersionedGameVertexConsumer implements GameVertexConsumer {
    private static final int MASK = 255;
    private static final int SHIFT = 16;
    private final VertexConsumer delegate;

    public VersionedGameVertexConsumer(VertexConsumer delegate) {
        this.delegate = delegate;
    }

    @NotNull
    public net.labymod.laby3d.api.vertex.VertexConsumer addVertex(float x, float y, float z) {
        this.delegate.addVertex(x, y, z);
        return this;
    }

    @NotNull
    public net.labymod.laby3d.api.vertex.VertexConsumer setColor(int red, int green, int blue, int alpha) {
        this.delegate.setColor(red, green, blue, alpha);
        return this;
    }

    @NotNull
    public net.labymod.laby3d.api.vertex.VertexConsumer setColor(float red, float green, float blue, float alpha) {
        this.delegate.setColor(red, green, blue, alpha);
        return this;
    }

    @NotNull
    public net.labymod.laby3d.api.vertex.VertexConsumer setColor(int argb) {
        this.delegate.setColor(argb);
        return this;
    }

    @NotNull
    public net.labymod.laby3d.api.vertex.VertexConsumer setUv(float u, float v) {
        this.delegate.setUv(u, v);
        return this;
    }

    @NotNull
    public net.labymod.laby3d.api.vertex.VertexConsumer setPackedLight(int lightCoords) {
        short u = (short) (lightCoords & 255);
        short v = (short) ((lightCoords >> 16) & 255);
        this.delegate.setUv2(u, v);
        return this;
    }

    @NotNull
    public net.labymod.laby3d.api.vertex.VertexConsumer setPackedOverlay(int overlayCoords) {
        short u = (short) (overlayCoords & 255);
        short v = (short) ((overlayCoords >> 16) & 255);
        this.delegate.setUv1(u, v);
        return this;
    }

    @NotNull
    public net.labymod.laby3d.api.vertex.VertexConsumer setUv(short i, short i1) {
        return this;
    }

    @NotNull
    public net.labymod.laby3d.api.vertex.VertexConsumer setNormal(float x, float y, float z) {
        this.delegate.setNormal(x, y, z);
        return this;
    }

    @NotNull
    public net.labymod.laby3d.api.vertex.VertexConsumer setInt(int i) {
        return this;
    }

    @NotNull
    public net.labymod.laby3d.api.vertex.VertexConsumer setShort(short i) {
        return this;
    }

    @NotNull
    public net.labymod.laby3d.api.vertex.VertexConsumer setFloat(float v) {
        return this;
    }
}
