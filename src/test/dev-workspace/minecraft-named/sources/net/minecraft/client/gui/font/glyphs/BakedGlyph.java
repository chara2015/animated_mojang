package net.minecraft.client.gui.font.glyphs;

import com.mojang.blaze3d.font.GlyphInfo;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.network.chat.Style;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/glyphs/BakedGlyph.class */
public interface BakedGlyph {
    GlyphInfo info();

    TextRenderable.Styled createGlyph(float f, float f2, int i, int i2, Style style, float f3, float f4);
}
