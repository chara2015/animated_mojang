package net.labymod.api.client.gfx.pipeline.renderer.text.glyph;

import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphSource;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphProvider.class */
public interface GlyphProvider {
    GlyphSource glyphs(GlyphSourceDescription glyphSourceDescription);

    EffectGlyph effect();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphProvider$Simple.class */
    public static class Simple implements GlyphProvider {
        private final GlyphStorage storage;
        private final GlyphSource glyphSource;

        public Simple(GlyphStorage storage) {
            this.storage = storage;
            this.glyphSource = new GlyphSource.StorageGlyphSource(storage);
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProvider
        public GlyphSource glyphs(GlyphSourceDescription description) {
            return this.glyphSource;
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProvider
        public EffectGlyph effect() {
            return this.storage.effectGlyph();
        }
    }
}
