package net.labymod.api.client.render.model.geometry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/geometry/UVBounds.class */
public interface UVBounds {
    float getMinU();

    float getMinV();

    float getMaxU();

    float getMaxV();

    default float getWidth() {
        return getMaxU() - getMinU();
    }

    default float getHeight() {
        return getMaxV() - getMinV();
    }
}
