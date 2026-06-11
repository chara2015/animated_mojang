package net.labymod.v1_17_1.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/gfx/pipeline/renderer/text/VanillaFormattedTextLayout.class */
public class VanillaFormattedTextLayout extends FormattedTextLayout {
    private final ov text;
    private ags sequence;

    public VanillaFormattedTextLayout(String text, pc style) {
        this(ov.a(text, style));
    }

    public VanillaFormattedTextLayout(ov component) {
        this.text = component;
    }

    public VanillaFormattedTextLayout(ags sequence) {
        this.text = null;
        this.sequence = sequence;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout
    public void format(GlyphProcessor processor) {
        getSequence().accept((position, style, codepoint) -> {
            return processor.accept(position, (Style) style, codepoint);
        });
    }

    public ags getSequence() {
        if (this.sequence != null) {
            return this.sequence;
        }
        this.sequence = mv.a().a(this.text);
        return this.sequence;
    }
}
