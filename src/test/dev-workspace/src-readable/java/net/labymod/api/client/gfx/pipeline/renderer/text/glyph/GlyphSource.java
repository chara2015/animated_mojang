package net.labymod.api.client.gfx.pipeline.renderer.text.glyph;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Random;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphSource.class */
public interface GlyphSource {
    BakedGlyph getGlyph(int i);

    BakedGlyph getRandomGlyph(Random random, float f);

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphSource$StorageGlyphSource.class */
    public static final class StorageGlyphSource extends Record implements GlyphSource {
        private final GlyphStorage storage;

        public StorageGlyphSource(GlyphStorage storage) {
            this.storage = storage;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, StorageGlyphSource.class), StorageGlyphSource.class, "storage", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphSource$StorageGlyphSource;->storage:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphStorage;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StorageGlyphSource.class), StorageGlyphSource.class, "storage", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphSource$StorageGlyphSource;->storage:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphStorage;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StorageGlyphSource.class, Object.class), StorageGlyphSource.class, "storage", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphSource$StorageGlyphSource;->storage:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphStorage;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public GlyphStorage storage() {
            return this.storage;
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphSource
        public BakedGlyph getGlyph(int codepoint) {
            return this.storage.getGlyph(codepoint);
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphSource
        public BakedGlyph getRandomGlyph(Random random, float advance) {
            return this.storage.getRandomGlyph(random, advance);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphSource$FailoverGlyphSource.class */
    public static class FailoverGlyphSource implements GlyphSource {
        private final GlyphStorage storage;
        private final GlyphStorage fallbackStorage;
        private GlyphStorage currentStorage;

        public FailoverGlyphSource(GlyphStorage storage, GlyphStorage fallbackStorage) {
            this.storage = storage;
            this.fallbackStorage = fallbackStorage;
            this.currentStorage = this.storage;
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphSource
        public BakedGlyph getGlyph(int codepoint) {
            if (this.currentStorage != this.storage) {
                this.currentStorage = this.storage;
            }
            BakedGlyph glyph = this.currentStorage.getGlyph(codepoint);
            if (glyph == null) {
                this.currentStorage = this.fallbackStorage;
                glyph = this.currentStorage.getGlyph(codepoint);
            }
            return glyph;
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphSource
        public BakedGlyph getRandomGlyph(Random random, float advance) {
            return this.currentStorage.getRandomGlyph(random, advance);
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphSource$StaticGlyphSource.class */
    public static final class StaticGlyphSource extends Record implements GlyphSource {
        private final BakedGlyph glyph;

        public StaticGlyphSource(BakedGlyph glyph) {
            this.glyph = glyph;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, StaticGlyphSource.class), StaticGlyphSource.class, "glyph", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphSource$StaticGlyphSource;->glyph:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/BakedGlyph;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StaticGlyphSource.class), StaticGlyphSource.class, "glyph", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphSource$StaticGlyphSource;->glyph:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/BakedGlyph;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StaticGlyphSource.class, Object.class), StaticGlyphSource.class, "glyph", "FIELD:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/GlyphSource$StaticGlyphSource;->glyph:Lnet/labymod/api/client/gfx/pipeline/renderer/text/glyph/BakedGlyph;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public BakedGlyph glyph() {
            return this.glyph;
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphSource
        public BakedGlyph getGlyph(int codepoint) {
            return this.glyph;
        }

        @Override // net.labymod.api.client.gfx.pipeline.renderer.text.glyph.GlyphSource
        public BakedGlyph getRandomGlyph(Random random, float advance) {
            return this.glyph;
        }
    }
}
