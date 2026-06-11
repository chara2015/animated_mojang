package net.labymod.v26_2_snapshot_8.client.gfx.pipeline.renderer.text;

import com.mojang.blaze3d.font.GlyphInfo;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/gfx/pipeline/renderer/text/VanillaGlyphDescription.class */
public class VanillaGlyphDescription implements GlyphDescription {
    private final GlyphInfo info;

    public VanillaGlyphDescription(GlyphInfo info) {
        this.info = info;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getAdvance() {
        return this.info.getAdvance();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getBoldOffset() {
        return this.info.getBoldOffset();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getShadowOffset() {
        return this.info.getShadowOffset();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription
    public float getAdvance(boolean bold) {
        return this.info.getAdvance(bold);
    }
}
