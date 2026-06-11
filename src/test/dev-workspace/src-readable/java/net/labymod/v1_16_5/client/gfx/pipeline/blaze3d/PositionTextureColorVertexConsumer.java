package net.labymod.v1_16_5.client.gfx.pipeline.blaze3d;

import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertex;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertexProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/gfx/pipeline/blaze3d/PositionTextureColorVertexConsumer.class */
public final class PositionTextureColorVertexConsumer extends Blaze3DVertexProvider implements dfq {
    private Blaze3DVertex current;

    private PositionTextureColorVertexConsumer() {
    }

    public static PositionTextureColorVertexConsumer create() {
        return new PositionTextureColorVertexConsumer();
    }

    public dfq a(double x, double y, double z) {
        this.current = new Blaze3DVertex((float) x, (float) y, (float) z);
        return this;
    }

    public dfq a(int red, int green, int blue, int alpha) {
        this.current.setArgb(red, green, blue, alpha);
        return this;
    }

    public dfq a(float u, float v) {
        this.current.setUv(u, v);
        return this;
    }

    public dfq a(int i, int i1) {
        return this;
    }

    public dfq b(int var1, int var2) {
        return this;
    }

    public dfq b(float var1, float var2, float var3) {
        return this;
    }

    public void d() {
        addVertex(this.current);
    }
}
