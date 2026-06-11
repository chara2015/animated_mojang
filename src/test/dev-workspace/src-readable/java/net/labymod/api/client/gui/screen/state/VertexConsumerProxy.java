package net.labymod.api.client.gui.screen.state;

import net.labymod.laby3d.api.vertex.VertexConsumer;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/state/VertexConsumerProxy.class */
public final class VertexConsumerProxy implements VertexConsumer {
    private VertexConsumer delegate;

    public VertexConsumer withDelegate(VertexConsumer delegate) {
        this.delegate = delegate;
        return this;
    }

    @NotNull
    public VertexConsumer addVertex2D(@NotNull Vector3f vector3f) {
        return this.delegate.addVertex(vector3f.x(), vector3f.y(), vector3f.z());
    }

    @NotNull
    public VertexConsumer addVertex2D(@NotNull Vector2f vector2f) {
        return this.delegate.addVertex2D(vector2f);
    }

    @NotNull
    public VertexConsumer addVertex2D(double x, double y) {
        return this.delegate.addVertex2D(x, y);
    }

    @NotNull
    public VertexConsumer addVertex2D(float x, float y) {
        return this.delegate.addVertex2D(x, y);
    }

    @NotNull
    public VertexConsumer addVertex(@NotNull Matrix4f pose, double x, double y, double z) {
        return this.delegate.addVertex(pose, x, y, z);
    }

    @NotNull
    public VertexConsumer addVertex(@NotNull Matrix4f pose, float x, float y, float z) {
        return this.delegate.addVertex(pose, x, y, z);
    }

    @NotNull
    public VertexConsumer addVertex(@NotNull Vector3f vec) {
        return this.delegate.addVertex(vec);
    }

    @NotNull
    public VertexConsumer addVertex(float x, float y, float z) {
        return this.delegate.addVertex(x, y, z);
    }

    @NotNull
    public VertexConsumer setColor(int red, int green, int blue, int alpha) {
        return this.delegate.setColor(red, green, blue, alpha);
    }

    @NotNull
    public VertexConsumer setColor(float red, float green, float blue, float alpha) {
        return this.delegate.setColor(red, green, blue, alpha);
    }

    @NotNull
    public VertexConsumer setColor(int argb) {
        return this.delegate.setColor(argb);
    }

    @NotNull
    public VertexConsumer setBlankUv() {
        return this.delegate.setBlankUv();
    }

    @NotNull
    public VertexConsumer setUv(float u, float v) {
        return this.delegate.setUv(u, v);
    }

    @NotNull
    public VertexConsumer setPackedLight(int packedLightCoords) {
        return this.delegate.setPackedLight(packedLightCoords);
    }

    @NotNull
    public VertexConsumer setPackedOverlay(int packedOverlayCoords) {
        return this.delegate.setPackedOverlay(packedOverlayCoords);
    }

    @NotNull
    public VertexConsumer setUv(short u, short v) {
        return this.delegate.setUv(u, v);
    }

    @NotNull
    public VertexConsumer setNormal(@NotNull Matrix3f normalMatrix, float x, float y, float z) {
        return this.delegate.setNormal(normalMatrix, x, y, z);
    }

    @NotNull
    public VertexConsumer setNormal(@NotNull Vector3f vec) {
        return this.delegate.setNormal(vec);
    }

    @NotNull
    public VertexConsumer setNormal(float x, float y, float z) {
        return this.delegate.setNormal(x, y, z);
    }

    @NotNull
    public VertexConsumer setInt(int value) {
        return this.delegate.setInt(value);
    }

    @NotNull
    public VertexConsumer setShort(short value) {
        return this.delegate.setShort(value);
    }

    @NotNull
    public VertexConsumer setFloat(float value) {
        return this.delegate.setFloat(value);
    }
}
