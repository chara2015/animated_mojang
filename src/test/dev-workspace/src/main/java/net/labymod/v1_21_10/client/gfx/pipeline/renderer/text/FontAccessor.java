package net.labymod.v1_21_10.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/gfx/pipeline/renderer/text/FontAccessor.class */
public interface FontAccessor {
    gii labyMod$getGlyph(int i, Style style);

    gdb labyMod$getGlyphSource(yc ycVar);

    static FontAccessor self(Object obj) {
        return (FontAccessor) CastUtil.requireInstanceOf(obj, FontAccessor.class);
    }
}
