package net.labymod.v1_21_11.client.gfx.pipeline.renderer.text;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Random;
import javax.inject.Singleton;
import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.BakedGlyph;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphSource;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.VanillaGlyphStorage;
import net.labymod.api.models.Implements;
import net.labymod.api.util.math.MathHelper;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage.class */
@Singleton
@Implements(VanillaGlyphStorage.class)
public class VersionedVanillaGlyphStorage extends VanillaGlyphStorage {
    public VersionedVanillaGlyphStorage() {
        super(x$0 -> {
            return new VersionedVanillaGlyph[x$0];
        }, x$02 -> {
            return new VersionedVanillaGlyph[x$02];
        });
    }

    protected BakedGlyph createGlyph(int codepoint) {
        Font font = Minecraft.getInstance().font;
        net.minecraft.client.gui.font.glyphs.BakedGlyph bakedGlyph = FontAccessor.self(font).labyMod$getGlyph(codepoint, Style.EMPTY);
        return new VersionedVanillaGlyph(bakedGlyph);
    }

    public GlyphSource glyphs(GlyphSourceDescription description) {
        FontAccessor accessor = FontAccessor.self(Minecraft.getInstance().font);
        net.minecraft.client.gui.GlyphSource vanillaGlyphSource = accessor.labyMod$getGlyphSource(MinecraftUtil.toMinecraft(description));
        return new VanillaGlyphSource(vanillaGlyphSource);
    }

    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$VanillaGlyphSource.class */
    private static final class VanillaGlyphSource extends Record implements GlyphSource {
        private final net.minecraft.client.gui.GlyphSource source;
        private static final RandomSource RANDOM_SOURCE = RandomSource.create();

        private VanillaGlyphSource(net.minecraft.client.gui.GlyphSource source) {
            this.source = source;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, VanillaGlyphSource.class), VanillaGlyphSource.class, "source", "FIELD:Lnet/labymod/v1_21_11/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$VanillaGlyphSource;->source:Lnet/minecraft/client/gui/GlyphSource;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, VanillaGlyphSource.class), VanillaGlyphSource.class, "source", "FIELD:Lnet/labymod/v1_21_11/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$VanillaGlyphSource;->source:Lnet/minecraft/client/gui/GlyphSource;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, VanillaGlyphSource.class, Object.class), VanillaGlyphSource.class, "source", "FIELD:Lnet/labymod/v1_21_11/client/gfx/pipeline/renderer/text/VersionedVanillaGlyphStorage$VanillaGlyphSource;->source:Lnet/minecraft/client/gui/GlyphSource;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public net.minecraft.client.gui.GlyphSource source() {
            return this.source;
        }

        public BakedGlyph getGlyph(int codepoint) {
            return new VersionedVanillaGlyph(this.source.getGlyph(codepoint));
        }

        public BakedGlyph getRandomGlyph(Random random, float advance) {
            net.minecraft.client.gui.font.glyphs.BakedGlyph glyph = this.source.getRandomGlyph(RANDOM_SOURCE, MathHelper.ceil(advance));
            return new VersionedVanillaGlyph(glyph);
        }
    }
}
