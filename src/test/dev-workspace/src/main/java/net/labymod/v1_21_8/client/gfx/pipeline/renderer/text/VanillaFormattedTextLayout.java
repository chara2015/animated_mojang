package net.labymod.v1_21_8.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/gfx/pipeline/renderer/text/VanillaFormattedTextLayout.class */
public class VanillaFormattedTextLayout extends FormattedTextLayout {
    private final xt text;
    private bbm sequence;

    public VanillaFormattedTextLayout(String text, yl style) {
        this(xt.a(text, style));
    }

    public VanillaFormattedTextLayout(xt component) {
        this.text = component;
    }

    public VanillaFormattedTextLayout(bbm sequence) {
        this.text = null;
        this.sequence = sequence;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout
    public void format(GlyphProcessor processor) {
        getSequence().accept((position, style, codepoint) -> {
            return processor.accept(position, (Style) style, codepoint);
        });
    }

    public bbm getSequence() {
        if (this.sequence != null) {
            return this.sequence;
        }
        this.sequence = ud.a().a(this.text);
        return this.sequence;
    }
}
