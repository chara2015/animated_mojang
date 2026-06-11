package net.labymod.core.client.gfx.pipeline.texture.atlas;

import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/texture/atlas/DefaultTextureUV.class */
public class DefaultTextureUV implements TextureUV {
    private final float minU;
    private final float minV;
    private final float maxU;
    private final float maxV;

    public DefaultTextureUV(float minU, float minV, float maxU, float maxV) {
        this.minU = minU;
        this.minV = minV;
        this.maxU = maxU;
        this.maxV = maxV;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV
    public float getMinU() {
        return this.minU;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV
    public float getMaxU() {
        return this.maxU;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV
    public float getMinV() {
        return this.minV;
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.TextureUV
    public float getMaxV() {
        return this.maxV;
    }
}
