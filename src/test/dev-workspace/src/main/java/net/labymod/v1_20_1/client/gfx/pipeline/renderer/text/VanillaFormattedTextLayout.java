package net.labymod.v1_20_1.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/gfx/pipeline/renderer/text/VanillaFormattedTextLayout.class */
public class VanillaFormattedTextLayout extends FormattedTextLayout {
    private final ta text;
    private aom sequence;

    public VanillaFormattedTextLayout(String text, ts style) {
        this(ta.a(text, style));
    }

    public VanillaFormattedTextLayout(ta component) {
        this.text = component;
    }

    public VanillaFormattedTextLayout(aom sequence) {
        this.text = null;
        this.sequence = sequence;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout
    public void format(GlyphProcessor processor) {
        getSequence().accept((position, style, codepoint) -> {
            return processor.accept(position, (Style) style, codepoint);
        });
    }

    public aom getSequence() {
        if (this.sequence != null) {
            return this.sequence;
        }
        this.sequence = qm.a().a(this.text);
        return this.sequence;
    }
}
