package net.minecraft.client.gui.font;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.gui.GlyphSource;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/SingleSpriteSource.class */
public final class SingleSpriteSource extends Record implements GlyphSource {
    private final BakedGlyph glyph;

    public SingleSpriteSource(BakedGlyph $$0) {
        this.glyph = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SingleSpriteSource.class), SingleSpriteSource.class, "glyph", "FIELD:Lnet/minecraft/client/gui/font/SingleSpriteSource;->glyph:Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SingleSpriteSource.class), SingleSpriteSource.class, "glyph", "FIELD:Lnet/minecraft/client/gui/font/SingleSpriteSource;->glyph:Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SingleSpriteSource.class, Object.class), SingleSpriteSource.class, "glyph", "FIELD:Lnet/minecraft/client/gui/font/SingleSpriteSource;->glyph:Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public BakedGlyph glyph() {
        return this.glyph;
    }

    @Override // net.minecraft.client.gui.GlyphSource
    public BakedGlyph getGlyph(int $$0) {
        return this.glyph;
    }

    @Override // net.minecraft.client.gui.GlyphSource
    public BakedGlyph getRandomGlyph(RandomSource $$0, int $$1) {
        return this.glyph;
    }
}
