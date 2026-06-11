package net.labymod.core.client.gfx.pipeline.renderer.text.legacymsdf;

import net.labymod.api.Laby;
import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.EffectGlyph;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProvider;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphSource;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphStorage;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.VanillaGlyphStorage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/text/legacymsdf/LegacyMSDFGlyphProvider.class */
public class LegacyMSDFGlyphProvider implements GlyphProvider {
    private final GlyphStorage glyphStorage;
    private final GlyphSource glyphSource;
    private final VanillaGlyphStorage vanillaGlyphStorage = Laby.references().vanillaGlyphStorage();

    public LegacyMSDFGlyphProvider(GlyphStorage glyphStorage) {
        this.glyphStorage = glyphStorage;
        this.glyphSource = new GlyphSource.FailoverGlyphSource(glyphStorage, this.vanillaGlyphStorage);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProvider
    public GlyphSource glyphs(GlyphSourceDescription description) {
        if (description == null || (description instanceof GlyphSourceDescription.Resource)) {
            return this.glyphSource;
        }
        return this.vanillaGlyphStorage.glyphs(description);
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProvider
    public EffectGlyph effect() {
        return this.glyphStorage.effectGlyph();
    }
}
