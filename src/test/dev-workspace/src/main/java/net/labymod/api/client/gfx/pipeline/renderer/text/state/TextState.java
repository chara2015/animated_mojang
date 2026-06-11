package net.labymod.api.client.gfx.pipeline.renderer.text.state;

import java.util.List;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphProperties;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphRenderable;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.StyledGlyphRenderable;
import net.labymod.api.laby3d.buffer.RenderingResourceAllocator;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/state/TextState.class */
public class TextState {
    private final List<StyledGlyphRenderable> glyphs;
    private final List<GlyphRenderable> effects;
    private final GlyphProperties properties;

    public TextState(List<StyledGlyphRenderable> glyphs, List<GlyphRenderable> effects) {
        this(glyphs, effects, GlyphProperties.empty());
    }

    public TextState(List<StyledGlyphRenderable> glyphs, List<GlyphRenderable> effects, GlyphProperties properties) {
        this.glyphs = glyphs;
        this.effects = effects;
        this.properties = properties;
    }

    public void buildVertices(RenderingResourceAllocator allocator, Matrix4f pose, int lightCoords) {
        for (StyledGlyphRenderable glyph : this.glyphs) {
            glyph.buildVertices(pose, allocator.getBuffer(glyph.guiRenderState(), glyph.textureView()), lightCoords, false, this.properties);
        }
        for (GlyphRenderable effect : this.effects) {
            effect.buildVertices(pose, allocator.getBuffer(effect.guiRenderState(), effect.textureView()), lightCoords, false, this.properties);
        }
    }

    public List<StyledGlyphRenderable> getGlyphs() {
        return this.glyphs;
    }

    public List<GlyphRenderable> getEffects() {
        return this.effects;
    }

    public GlyphProperties getProperties() {
        return this.properties;
    }
}
