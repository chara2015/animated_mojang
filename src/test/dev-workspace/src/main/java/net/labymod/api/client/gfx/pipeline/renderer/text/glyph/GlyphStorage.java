package net.labymod.api.client.gfx.pipeline.renderer.text.glyph;

import java.util.Random;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphStorage.class */
public interface GlyphStorage {
    @Nullable
    BakedGlyph getGlyph(int i);

    BakedGlyph getRandomGlyph(Random random, float f);

    EffectGlyph effectGlyph();

    default boolean hasGlyph(int codepoint) {
        return getGlyph(codepoint) != null;
    }
}
