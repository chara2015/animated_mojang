package net.labymod.v26_1_2.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.GlyphProcessor;
import net.labymod.api.util.CastUtil;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/gfx/pipeline/renderer/text/VanillaFormattedTextLayout.class */
public class VanillaFormattedTextLayout extends FormattedTextLayout {
    private final FormattedText text;
    private FormattedCharSequence sequence;

    public VanillaFormattedTextLayout(String text, Style style) {
        this(FormattedText.of(text, style));
    }

    public VanillaFormattedTextLayout(FormattedText component) {
        this.text = component;
    }

    public VanillaFormattedTextLayout(FormattedCharSequence sequence) {
        this.text = null;
        this.sequence = sequence;
    }

    @Override // net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout
    public void format(GlyphProcessor processor) {
        getSequence().accept((position, style, codepoint) -> {
            return processor.accept(position, (net.labymod.api.client.component.format.Style) CastUtil.cast(style), codepoint);
        });
    }

    public FormattedCharSequence getSequence() {
        if (this.sequence != null) {
            return this.sequence;
        }
        this.sequence = Language.getInstance().getVisualOrder(this.text);
        return this.sequence;
    }
}
