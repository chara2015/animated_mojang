package net.labymod.api.client.gfx.pipeline.renderer.text.state;

import java.util.Collections;
import java.util.List;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable;
import net.labymod.api.util.collection.Lists;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/state/TextStateBuilder.class */
public class TextStateBuilder {
    private List<StyledGlyphRenderable> glyphs;
    private List<GlyphRenderable> effects;

    public void addGlyph(StyledGlyphRenderable glyph) {
        if (this.glyphs == null) {
            this.glyphs = Lists.newArrayList();
        }
        this.glyphs.add(glyph);
    }

    public void addEffect(GlyphRenderable effect) {
        if (this.effects == null) {
            this.effects = Lists.newArrayList();
        }
        this.effects.add(effect);
    }

    public TextState build() {
        return new TextState(getGlyphs(), getEffects());
    }

    public TextState build(GlyphProperties properties) {
        return new TextState(getGlyphs(), getEffects(), properties);
    }

    private List<StyledGlyphRenderable> getGlyphs() {
        return this.glyphs == null ? Collections.emptyList() : this.glyphs;
    }

    private List<GlyphRenderable> getEffects() {
        return this.effects == null ? Collections.emptyList() : this.effects;
    }
}
