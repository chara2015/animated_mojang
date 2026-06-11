package net.labymod.v26_2_snapshot_8.client.gfx.pipeline.blaze3d;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertex;
import net.labymod.core.client.gfx.pipeline.blaze3d.Blaze3DVertexProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/gfx/pipeline/blaze3d/PositionTextureColorVertexConsumer.class */
public final class PositionTextureColorVertexConsumer extends Blaze3DVertexProvider implements VertexConsumer {
    private Blaze3DVertex current;

    private PositionTextureColorVertexConsumer() {
    }

    public static PositionTextureColorVertexConsumer create() {
        return new PositionTextureColorVertexConsumer();
    }

    public VertexConsumer addVertex(float x, float y, float z) {
        this.current = new Blaze3DVertex(x, y, z);
        addVertex(this.current);
        return this;
    }

    public VertexConsumer setColor(int red, int green, int blue, int alpha) {
        this.current.setArgb(red, green, blue, alpha);
        return this;
    }

    public VertexConsumer setColor(int var1) {
        this.current.setArgb(var1);
        return this;
    }

    public VertexConsumer setUv(float u, float v) {
        this.current.setUv(u, v);
        return this;
    }

    public VertexConsumer setUv1(int var1, int var2) {
        return this;
    }

    public VertexConsumer setUv2(int var1, int var2) {
        return this;
    }

    public VertexConsumer setNormal(float var1, float var2, float var3) {
        return this;
    }

    public VertexConsumer setLineWidth(float var1) {
        return this;
    }
}
