package com.mojang.blaze3d.font;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/font/GlyphInfo.class */
public interface GlyphInfo {
    float getAdvance();

    default float getAdvance(boolean $$0) {
        return getAdvance() + ($$0 ? getBoldOffset() : 0.0f);
    }

    default float getBoldOffset() {
        return 1.0f;
    }

    default float getShadowOffset() {
        return 1.0f;
    }

    static GlyphInfo simple(float $$0) {
        return () -> {
            return $$0;
        };
    }
}
