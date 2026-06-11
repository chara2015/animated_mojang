package net.labymod.v1_16_5.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.gfx.pipeline.renderer.text.vanilla.VanillaGlyph;
import net.labymod.v1_16_5.client.gfx.pipeline.renderer.text.FontAccessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/gfx/pipeline/renderer/text/VersionedVanillaGlyph.class */
public class VersionedVanillaGlyph extends VanillaGlyph {
    private final dmz bakedGlyph;
    private final GlyphDescription glyphDescription;

    protected VersionedVanillaGlyph(FontAccessor.MinecraftGlyph glyph) {
        this.bakedGlyph = glyph.glyph();
        this.glyphDescription = new VanillaGlyphDescription(glyph.info());
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph
    public GlyphDescription description() {
        return this.glyphDescription;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph
    public StyledGlyphRenderable createGlyph(float x, float y, int color, int shadowColor, Style style, float boldOffset, float shadowOffset) {
        return new VanillaRenderedGlyph(new GlyphInstance(x, y, color, shadowColor, this.bakedGlyph, (Style) CastUtil.cast(style), boldOffset, shadowOffset));
    }
}
