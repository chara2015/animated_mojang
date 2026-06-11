package net.labymod.v1_21_5.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/gfx/pipeline/renderer/text/VanillaFormattedTextLayout.class */
public class VanillaFormattedTextLayout extends FormattedTextLayout {
    private final xl text;
    private azk sequence;

    public VanillaFormattedTextLayout(String text, yd style) {
        this(xl.a(text, style));
    }

    public VanillaFormattedTextLayout(xl component) {
        this.text = component;
    }

    public VanillaFormattedTextLayout(azk sequence) {
        this.text = null;
        this.sequence = sequence;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout
    public void format(GlyphProcessor processor) {
        getSequence().accept((position, style, codepoint) -> {
            return processor.accept(position, (Style) style, codepoint);
        });
    }

    public azk getSequence() {
        if (this.sequence != null) {
            return this.sequence;
        }
        this.sequence = tv.a().a(this.text);
        return this.sequence;
    }
}
