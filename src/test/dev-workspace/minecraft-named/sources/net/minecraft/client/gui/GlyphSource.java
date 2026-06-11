package net.minecraft.client.gui;

import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/GlyphSource.class */
public interface GlyphSource {
    BakedGlyph getGlyph(int i);

    BakedGlyph getRandomGlyph(RandomSource randomSource, int i);
}
