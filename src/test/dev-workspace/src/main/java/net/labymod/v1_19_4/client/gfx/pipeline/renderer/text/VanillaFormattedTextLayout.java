package net.labymod.v1_19_4.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/gfx/pipeline/renderer/text/VanillaFormattedTextLayout.class */
public class VanillaFormattedTextLayout extends FormattedTextLayout {
    private final tn text;
    private aov sequence;

    public VanillaFormattedTextLayout(String text, uf style) {
        this(tn.a(text, style));
    }

    public VanillaFormattedTextLayout(tn component) {
        this.text = component;
    }

    public VanillaFormattedTextLayout(aov sequence) {
        this.text = null;
        this.sequence = sequence;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout
    public void format(GlyphProcessor processor) {
        getSequence().accept((position, style, codepoint) -> {
            return processor.accept(position, (Style) style, codepoint);
        });
    }

    public aov getSequence() {
        if (this.sequence != null) {
            return this.sequence;
        }
        this.sequence = qz.a().a(this.text);
        return this.sequence;
    }
}
