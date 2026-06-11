package com.mojang.blaze3d.font;

import net.minecraft.client.gui.font.glyphs.BakedGlyph;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/font/UnbakedGlyph.class */
public interface UnbakedGlyph {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/font/UnbakedGlyph$Stitcher.class */
    public interface Stitcher {
        BakedGlyph stitch(GlyphInfo glyphInfo, GlyphBitmap glyphBitmap);

        BakedGlyph getMissing();
    }

    GlyphInfo info();

    BakedGlyph bake(Stitcher stitcher);
}
