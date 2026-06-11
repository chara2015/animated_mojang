package net.labymod.v1_21_11.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.gfx.pipeline.renderer.text.vanilla.VanillaGlyph;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/gfx/pipeline/renderer/text/VersionedVanillaGlyph.class */
public class VersionedVanillaGlyph extends VanillaGlyph {
    private final goa bakedGlyph;
    private final GlyphDescription glyphDescription;

    protected VersionedVanillaGlyph(goa glyph) {
        this.bakedGlyph = glyph;
        this.glyphDescription = new VanillaGlyphDescription(glyph.a());
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph
    public GlyphDescription description() {
        return this.glyphDescription;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph
    public StyledGlyphRenderable createGlyph(float x, float y, int color, int shadowColor, Style style, float boldOffset, float shadowOffset) {
        a aVarA = this.bakedGlyph.a(x, y, color, shadowColor, (zf) CastUtil.cast(style), boldOffset, shadowOffset);
        if (aVarA == null) {
            return null;
        }
        return new VanillaRenderedGlyph(aVarA);
    }
}
