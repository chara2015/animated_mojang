package net.labymod.v1_21_1.client.gfx.pipeline.blaze3d;

import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertex;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertexProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/gfx/pipeline/blaze3d/PositionTextureColorVertexConsumer.class */
public final class PositionTextureColorVertexConsumer extends Blaze3DVertexProvider implements fbm {
    private Blaze3DVertex current;

    private PositionTextureColorVertexConsumer() {
    }

    public static PositionTextureColorVertexConsumer create() {
        return new PositionTextureColorVertexConsumer();
    }

    public fbm a(float x, float y, float z) {
        this.current = new Blaze3DVertex(x, y, z);
        addVertex(this.current);
        return this;
    }

    public fbm a(int red, int green, int blue, int alpha) {
        this.current.setArgb(red, green, blue, alpha);
        return this;
    }

    public fbm a(float u, float v) {
        this.current.setUv(u, v);
        return this;
    }

    public fbm a(int var1, int var2) {
        return this;
    }

    public fbm b(int var1, int var2) {
        return this;
    }

    public fbm b(float var1, float var2, float var3) {
        return this;
    }
}
