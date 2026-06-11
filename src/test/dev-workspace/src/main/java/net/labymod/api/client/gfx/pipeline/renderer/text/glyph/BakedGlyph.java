package net.labymod.api.client.gfx.pipeline.renderer.text.glyph;

import net.labymod.api.client.component.format.Style;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/BakedGlyph.class */
public interface BakedGlyph {
    GlyphDescription description();

    StyledGlyphRenderable createGlyph(float f, float f2, int i, int i2, Style style, float f3, float f4);
}
