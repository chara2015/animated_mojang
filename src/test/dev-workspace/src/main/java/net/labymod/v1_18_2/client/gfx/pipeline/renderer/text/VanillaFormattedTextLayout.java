package net.labymod.v1_18_2.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/gfx/pipeline/renderer/text/VanillaFormattedTextLayout.class */
public class VanillaFormattedTextLayout extends FormattedTextLayout {
    private final qn text;
    private aiz sequence;

    public VanillaFormattedTextLayout(String text, qu style) {
        this(qn.a(text, style));
    }

    public VanillaFormattedTextLayout(qn component) {
        this.text = component;
    }

    public VanillaFormattedTextLayout(aiz sequence) {
        this.text = null;
        this.sequence = sequence;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout
    public void format(GlyphProcessor processor) {
        getSequence().accept((position, style, codepoint) -> {
            return processor.accept(position, (Style) style, codepoint);
        });
    }

    public aiz getSequence() {
        if (this.sequence != null) {
            return this.sequence;
        }
        this.sequence = of.a().a(this.text);
        return this.sequence;
    }
}
