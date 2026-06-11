package net.labymod.v1_12_2.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/gfx/pipeline/renderer/text/VanillaGlyphDescription.class */
public class VanillaGlyphDescription implements GlyphDescription {
    private final float advance;
    private final float boldOffset;

    public VanillaGlyphDescription(float advance, float boldOffset) {
        this.advance = advance;
        this.boldOffset = boldOffset;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getAdvance() {
        return this.advance;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getBoldOffset() {
        return this.boldOffset;
    }
}
