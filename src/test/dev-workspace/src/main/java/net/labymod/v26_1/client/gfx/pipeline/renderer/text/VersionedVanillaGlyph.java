package net.labymod.v26_1.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable;
import net.labymod.api.util.CastUtil;
import net.labymod.core.client.gfx.pipeline.renderer.text.vanilla.VanillaGlyph;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/gfx/pipeline/renderer/text/VersionedVanillaGlyph.class */
public class VersionedVanillaGlyph extends VanillaGlyph {
    private final BakedGlyph bakedGlyph;
    private final GlyphDescription glyphDescription;

    protected VersionedVanillaGlyph(BakedGlyph glyph) {
        this.bakedGlyph = glyph;
        this.glyphDescription = new VanillaGlyphDescription(glyph.info());
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph
    public GlyphDescription description() {
        return this.glyphDescription;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph
    public StyledGlyphRenderable createGlyph(float x, float y, int color, int shadowColor, Style style, float boldOffset, float shadowOffset) {
        TextRenderable.Styled styledCreateGlyph = this.bakedGlyph.createGlyph(x, y, color, shadowColor, (net.minecraft.network.chat.Style) CastUtil.cast(style), boldOffset, shadowOffset);
        if (styledCreateGlyph == null) {
            return null;
        }
        return new VanillaRenderedGlyph(styledCreateGlyph);
    }
}
