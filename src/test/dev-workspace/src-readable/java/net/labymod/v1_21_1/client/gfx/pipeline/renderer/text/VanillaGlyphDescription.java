package net.labymod.v1_21_1.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/gfx/pipeline/renderer/text/VanillaGlyphDescription.class */
public class VanillaGlyphDescription implements GlyphDescription {
    private final ezl info;

    public VanillaGlyphDescription(ezl info) {
        this.info = info;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getAdvance() {
        return this.info.getAdvance();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getBoldOffset() {
        return this.info.a();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getShadowOffset() {
        return this.info.b();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getAdvance(boolean bold) {
        return this.info.a(bold);
    }
}
