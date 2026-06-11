package net.labymod.v1_21_11.client.gfx.pipeline.renderer.text;

import com.mojang.blaze3d.font.GlyphInfo;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphDescription;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/gfx/pipeline/renderer/text/VanillaGlyphDescription.class */
public class VanillaGlyphDescription implements GlyphDescription {
    private final GlyphInfo info;

    public VanillaGlyphDescription(GlyphInfo info) {
        this.info = info;
    }

    public float getAdvance() {
        return this.info.getAdvance();
    }

    public float getBoldOffset() {
        return this.info.getBoldOffset();
    }

    public float getShadowOffset() {
        return this.info.getShadowOffset();
    }

    public float getAdvance(boolean bold) {
        return this.info.getAdvance(bold);
    }
}
