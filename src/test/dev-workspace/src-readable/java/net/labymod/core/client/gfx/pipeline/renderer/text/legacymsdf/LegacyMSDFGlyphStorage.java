package net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf;

import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.AbstractGlyphStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/LegacyMSDFGlyphStorage.class */
public class LegacyMSDFGlyphStorage extends AbstractGlyphStorage {
    public LegacyMSDFGlyphStorage() {
        super(x$0 -> {
            return new LegacyMSDFBakedGlyph[x$0];
        }, x$02 -> {
            return new LegacyMSDFBakedGlyph[x$02];
        });
    }
}
