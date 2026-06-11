package net.labymod.v1_8_9.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor;
import net.labymod.api.client.render.font.text.StringIterator;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/gfx/pipeline/renderer/text/VanillaFormattedTextLayout.class */
public class VanillaFormattedTextLayout extends FormattedTextLayout {
    private final eu text;
    private final String formattedText;

    public VanillaFormattedTextLayout(String text, ez style) {
        this(new fa(text).a(style));
    }

    public VanillaFormattedTextLayout(eu component) {
        this.text = component;
        this.formattedText = component.d();
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout
    public void format(GlyphProcessor processor) {
        StringIterator.iterateFormatted(this.formattedText, Style.EMPTY, false, processor);
    }

    public eu getText() {
        return this.text;
    }

    public String getFormattedText() {
        return this.formattedText;
    }
}
