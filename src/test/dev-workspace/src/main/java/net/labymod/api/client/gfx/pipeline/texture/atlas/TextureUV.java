package net.labymod.api.client.gfx.pipeline.texture.atlas;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/texture/atlas/TextureUV.class */
public interface TextureUV {
    float getMinU();

    float getMaxU();

    float getMinV();

    float getMaxV();

    default float getU(float value) {
        float diff = getMaxU() - getMinU();
        return getMinU() + (diff * value);
    }

    default float getV(float value) {
        float diff = getMaxV() - getMinV();
        return getMinV() + (diff * value);
    }
}
