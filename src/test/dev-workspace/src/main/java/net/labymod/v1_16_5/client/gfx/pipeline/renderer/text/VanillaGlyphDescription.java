package net.labymod.v1_16_5.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/gfx/pipeline/renderer/text/VanillaGlyphDescription.class */
public class VanillaGlyphDescription implements GlyphDescription {
    private final dea info;

    public VanillaGlyphDescription(dea info) {
        this.info = info;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getAdvance() {
        return this.info.getAdvance();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getBoldOffset() {
        return this.info.b();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getShadowOffset() {
        return this.info.c();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getAdvance(boolean bold) {
        return this.info.a(bold);
    }
}
