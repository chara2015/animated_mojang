package net.labymod.api.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/FormattedTextLayout.class */
public abstract class FormattedTextLayout {
    public abstract void format(GlyphProcessor glyphProcessor);

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/FormattedTextLayout$Factory.class */
    @Referenceable
    public interface Factory {
        FormattedTextLayout create(String str, Style style);

        FormattedTextLayout create(Component component);

        default FormattedTextLayout create(String text) {
            return create(text, Style.EMPTY);
        }
    }
}
