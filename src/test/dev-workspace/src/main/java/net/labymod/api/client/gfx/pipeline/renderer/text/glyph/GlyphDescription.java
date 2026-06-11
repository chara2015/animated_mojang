package net.labymod.api.client.gfx.pipeline.renderer.text.glyph;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphDescription.class */
public interface GlyphDescription {
    float getAdvance();

    static GlyphDescription simple(float advance) {
        return () -> {
            return advance;
        };
    }

    default float getAdvance(boolean bold) {
        return getAdvance() + (bold ? getBoldOffset() : 0.0f);
    }

    default float getBoldOffset() {
        return 1.0f;
    }

    default float getShadowOffset() {
        return 1.0f;
    }
}
