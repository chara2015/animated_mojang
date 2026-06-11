package net.labymod.v1_18_2.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/gfx/pipeline/renderer/text/VanillaGlyphDescription.class */
public class VanillaGlyphDescription implements GlyphDescription {
    private final drq info;

    public VanillaGlyphDescription(drq info) {
        this.info = info;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getAdvance() {
        return this.info.getAdvance();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getBoldOffset() {
        return this.info.c();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getShadowOffset() {
        return this.info.d();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getAdvance(boolean bold) {
        return this.info.a(bold);
    }
}
