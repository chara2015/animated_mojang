package net.labymod.v26_1.client.gfx.pipeline.renderer.text;

import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;
import net.minecraft.client.gui.GlyphSource;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.network.chat.FontDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/gfx/pipeline/renderer/text/FontAccessor.class */
public interface FontAccessor {
    BakedGlyph labyMod$getGlyph(int i, Style style);

    GlyphSource labyMod$getGlyphSource(FontDescription fontDescription);

    static FontAccessor self(Object obj) {
        return (FontAccessor) CastUtil.requireInstanceOf(obj, FontAccessor.class);
    }
}
