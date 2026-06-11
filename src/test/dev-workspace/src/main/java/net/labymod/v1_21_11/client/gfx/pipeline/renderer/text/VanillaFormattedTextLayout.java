package net.labymod.v1_21_11.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor;
import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/gfx/pipeline/renderer/text/VanillaFormattedTextLayout.class */
public class VanillaFormattedTextLayout extends FormattedTextLayout {
    private final yn text;
    private bfr sequence;

    public VanillaFormattedTextLayout(String text, zf style) {
        this(yn.a(text, style));
    }

    public VanillaFormattedTextLayout(yn component) {
        this.text = component;
    }

    public VanillaFormattedTextLayout(bfr sequence) {
        this.text = null;
        this.sequence = sequence;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout
    public void format(GlyphProcessor processor) {
        getSequence().accept((position, style, codepoint) -> {
            return processor.accept(position, (Style) CastUtil.cast(style), codepoint);
        });
    }

    public bfr getSequence() {
        if (this.sequence != null) {
            return this.sequence;
        }
        this.sequence = uu.a().a(this.text);
        return this.sequence;
    }
}
