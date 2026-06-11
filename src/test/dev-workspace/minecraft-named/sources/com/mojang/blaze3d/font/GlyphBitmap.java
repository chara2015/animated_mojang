package com.mojang.blaze3d.font;

import com.mojang.blaze3d.textures.GpuTexture;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/font/GlyphBitmap.class */
public interface GlyphBitmap {
    int getPixelWidth();

    int getPixelHeight();

    void upload(int i, int i2, GpuTexture gpuTexture);

    boolean isColored();

    float getOversample();

    default float getLeft() {
        return getBearingLeft();
    }

    default float getRight() {
        return getLeft() + (getPixelWidth() / getOversample());
    }

    default float getTop() {
        return 7.0f - getBearingTop();
    }

    default float getBottom() {
        return getTop() + (getPixelHeight() / getOversample());
    }

    default float getBearingLeft() {
        return 0.0f;
    }

    default float getBearingTop() {
        return 7.0f;
    }
}
