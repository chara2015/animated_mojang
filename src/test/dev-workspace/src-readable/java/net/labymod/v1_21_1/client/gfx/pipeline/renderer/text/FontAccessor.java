package net.labymod.v1_21_1.client.gfx.pipeline.renderer.text;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/gfx/pipeline/renderer/text/FontAccessor.class */
public interface FontAccessor {
    fld labyMod$getFontSet(akr akrVar);

    boolean labyMod$filterFishyGlyphs();

    MinecraftGlyph labyMod$getGlyph(int i, Style style);

    static FontAccessor self(Object obj) {
        return (FontAccessor) CastUtil.requireInstanceOf(obj, FontAccessor.class);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/gfx/pipeline/renderer/text/FontAccessor$MinecraftGlyph.class */
    public static final class MinecraftGlyph extends Record {
        private final flh glyph;
        private final ezl info;

        public MinecraftGlyph(flh glyph, ezl info) {
            this.glyph = glyph;
            this.info = info;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MinecraftGlyph.class), MinecraftGlyph.class, "glyph;info", "FIELD:Lnet/labymod/v1_21_1/client/gfx/pipeline/renderer/text/FontAccessor$MinecraftGlyph;->glyph:Lflh;", "FIELD:Lnet/labymod/v1_21_1/client/gfx/pipeline/renderer/text/FontAccessor$MinecraftGlyph;->info:Lezl;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MinecraftGlyph.class), MinecraftGlyph.class, "glyph;info", "FIELD:Lnet/labymod/v1_21_1/client/gfx/pipeline/renderer/text/FontAccessor$MinecraftGlyph;->glyph:Lflh;", "FIELD:Lnet/labymod/v1_21_1/client/gfx/pipeline/renderer/text/FontAccessor$MinecraftGlyph;->info:Lezl;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MinecraftGlyph.class, Object.class), MinecraftGlyph.class, "glyph;info", "FIELD:Lnet/labymod/v1_21_1/client/gfx/pipeline/renderer/text/FontAccessor$MinecraftGlyph;->glyph:Lflh;", "FIELD:Lnet/labymod/v1_21_1/client/gfx/pipeline/renderer/text/FontAccessor$MinecraftGlyph;->info:Lezl;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public flh glyph() {
            return this.glyph;
        }

        public ezl info() {
            return this.info;
        }
    }
}
