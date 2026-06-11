package net.minecraft.client.gui.font.providers;

import com.mojang.blaze3d.font.GlyphBitmap;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.GlyphProvider;
import com.mojang.blaze3d.font.UnbakedGlyph;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.datafixers.util.Either;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.font.CodepointMap;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.providers.GlyphProviderDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.Display;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/providers/BitmapProvider.class */
public class BitmapProvider implements GlyphProvider {
    static final Logger LOGGER = LogUtils.getLogger();
    private final NativeImage image;
    private final CodepointMap<Glyph> glyphs;

    BitmapProvider(NativeImage $$0, CodepointMap<Glyph> $$1) {
        this.image = $$0;
        this.glyphs = $$1;
    }

    @Override // com.mojang.blaze3d.font.GlyphProvider, java.lang.AutoCloseable
    public void close() {
        this.image.close();
    }

    @Override // com.mojang.blaze3d.font.GlyphProvider
    public UnbakedGlyph getGlyph(int $$0) {
        return this.glyphs.get($$0);
    }

    @Override // com.mojang.blaze3d.font.GlyphProvider
    public IntSet getSupportedGlyphs() {
        return IntSets.unmodifiable(this.glyphs.keySet());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/providers/BitmapProvider$Definition.class */
    public static final class Definition extends Record implements GlyphProviderDefinition {
        private final Identifier file;
        private final int height;
        private final int ascent;
        private final int[][] codepointGrid;
        private static final Codec<int[][]> CODEPOINT_GRID_CODEC = Codec.STRING.listOf().xmap($$0 -> {
            int $$1 = $$0.size();
            ?? r0 = new int[$$1];
            for (int $$3 = 0; $$3 < $$1; $$3++) {
                r0[$$3] = ((String) $$0.get($$3)).codePoints().toArray();
            }
            return r0;
        }, $$02 -> {
            List<String> $$1 = new ArrayList<>($$02.length);
            for (int[] $$2 : $$02) {
                $$1.add(new String($$2, 0, $$2.length));
            }
            return $$1;
        }).validate(Definition::validateDimensions);
        public static final MapCodec<Definition> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Identifier.CODEC.fieldOf("file").forGetter((v0) -> {
                return v0.file();
            }), Codec.INT.optionalFieldOf(Display.TAG_HEIGHT, 8).forGetter((v0) -> {
                return v0.height();
            }), Codec.INT.fieldOf("ascent").forGetter((v0) -> {
                return v0.ascent();
            }), CODEPOINT_GRID_CODEC.fieldOf("chars").forGetter((v0) -> {
                return v0.codepointGrid();
            })).apply($$0, (v1, v2, v3, v4) -> {
                return new Definition(v1, v2, v3, v4);
            });
        }).validate(Definition::validate);

        public Definition(Identifier $$0, int $$1, int $$2, int[][] $$3) {
            this.file = $$0;
            this.height = $$1;
            this.ascent = $$2;
            this.codepointGrid = $$3;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Definition.class), Definition.class, "file;height;ascent;codepointGrid", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;->file:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;->height:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;->ascent:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;->codepointGrid:[[I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Definition.class), Definition.class, "file;height;ascent;codepointGrid", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;->file:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;->height:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;->ascent:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;->codepointGrid:[[I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Definition.class, Object.class), Definition.class, "file;height;ascent;codepointGrid", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;->file:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;->height:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;->ascent:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Definition;->codepointGrid:[[I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Identifier file() {
            return this.file;
        }

        public int height() {
            return this.height;
        }

        public int ascent() {
            return this.ascent;
        }

        public int[][] codepointGrid() {
            return this.codepointGrid;
        }

        private static DataResult<int[][]> validateDimensions(int[][] $$0) {
            int $$1 = $$0.length;
            if ($$1 == 0) {
                return DataResult.error(() -> {
                    return "Expected to find data in codepoint grid";
                });
            }
            int[] $$2 = $$0[0];
            int $$3 = $$2.length;
            if ($$3 == 0) {
                return DataResult.error(() -> {
                    return "Expected to find data in codepoint grid";
                });
            }
            for (int $$4 = 1; $$4 < $$1; $$4++) {
                int[] $$5 = $$0[$$4];
                if ($$5.length != $$3) {
                    return DataResult.error(() -> {
                        return "Lines in codepoint grid have to be the same length (found: " + $$5.length + " codepoints, expected: " + $$3 + "), pad with \\u0000";
                    });
                }
            }
            return DataResult.success($$0);
        }

        private static DataResult<Definition> validate(Definition $$0) {
            if ($$0.ascent > $$0.height) {
                return DataResult.error(() -> {
                    return "Ascent " + $$0.ascent + " higher than height " + $$0.height;
                });
            }
            return DataResult.success($$0);
        }

        @Override // net.minecraft.client.gui.font.providers.GlyphProviderDefinition
        public GlyphProviderType type() {
            return GlyphProviderType.BITMAP;
        }

        @Override // net.minecraft.client.gui.font.providers.GlyphProviderDefinition
        public Either<GlyphProviderDefinition.Loader, GlyphProviderDefinition.Reference> unpack() {
            return Either.left(this::load);
        }

        private GlyphProvider load(ResourceManager $$0) throws IOException {
            Identifier $$1 = this.file.withPrefix("textures/");
            InputStream $$2 = $$0.open($$1);
            try {
                NativeImage $$3 = NativeImage.read(NativeImage.Format.RGBA, $$2);
                int $$4 = $$3.getWidth();
                int $$5 = $$3.getHeight();
                int $$6 = $$4 / this.codepointGrid[0].length;
                int $$7 = $$5 / this.codepointGrid.length;
                float $$8 = this.height / $$7;
                CodepointMap<Glyph> $$9 = new CodepointMap<>($$02 -> {
                    return new Glyph[$$02];
                }, $$03 -> {
                    return new Glyph[$$03];
                });
                for (int $$10 = 0; $$10 < this.codepointGrid.length; $$10++) {
                    int $$11 = 0;
                    for (int $$12 : this.codepointGrid[$$10]) {
                        int $$13 = $$11;
                        $$11++;
                        if ($$12 != 0) {
                            int $$14 = getActualGlyphWidth($$3, $$6, $$7, $$13, $$10);
                            Glyph $$15 = $$9.put($$12, new Glyph($$8, $$3, $$13 * $$6, $$10 * $$7, $$6, $$7, ((int) (0.5d + ((double) ($$14 * $$8)))) + 1, this.ascent));
                            if ($$15 != null) {
                                BitmapProvider.LOGGER.warn("Codepoint '{}' declared multiple times in {}", Integer.toHexString($$12), $$1);
                            }
                        }
                    }
                }
                BitmapProvider bitmapProvider = new BitmapProvider($$3, $$9);
                if ($$2 != null) {
                    $$2.close();
                }
                return bitmapProvider;
            } catch (Throwable th) {
                if ($$2 != null) {
                    try {
                        $$2.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        private int getActualGlyphWidth(NativeImage $$0, int $$1, int $$2, int $$3, int $$4) {
            int $$5 = $$1 - 1;
            while ($$5 >= 0) {
                int $$6 = ($$3 * $$1) + $$5;
                for (int $$7 = 0; $$7 < $$2; $$7++) {
                    int $$8 = ($$4 * $$2) + $$7;
                    if ($$0.getLuminanceOrAlpha($$6, $$8) != 0) {
                        return $$5 + 1;
                    }
                }
                $$5--;
            }
            return $$5 + 1;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/providers/BitmapProvider$Glyph.class */
    static final class Glyph extends Record implements UnbakedGlyph {
        private final float scale;
        private final NativeImage image;
        private final int offsetX;
        private final int offsetY;
        private final int width;
        private final int height;
        private final int advance;
        private final int ascent;

        Glyph(float $$0, NativeImage $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7) {
            this.scale = $$0;
            this.image = $$1;
            this.offsetX = $$2;
            this.offsetY = $$3;
            this.width = $$4;
            this.height = $$5;
            this.advance = $$6;
            this.ascent = $$7;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Glyph.class), Glyph.class, "scale;image;offsetX;offsetY;width;height;advance;ascent", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->scale:F", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->image:Lcom/mojang/blaze3d/platform/NativeImage;", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->offsetX:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->offsetY:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->width:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->height:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->advance:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->ascent:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Glyph.class), Glyph.class, "scale;image;offsetX;offsetY;width;height;advance;ascent", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->scale:F", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->image:Lcom/mojang/blaze3d/platform/NativeImage;", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->offsetX:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->offsetY:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->width:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->height:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->advance:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->ascent:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Glyph.class, Object.class), Glyph.class, "scale;image;offsetX;offsetY;width;height;advance;ascent", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->scale:F", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->image:Lcom/mojang/blaze3d/platform/NativeImage;", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->offsetX:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->offsetY:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->width:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->height:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->advance:I", "FIELD:Lnet/minecraft/client/gui/font/providers/BitmapProvider$Glyph;->ascent:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public float scale() {
            return this.scale;
        }

        public NativeImage image() {
            return this.image;
        }

        public int offsetX() {
            return this.offsetX;
        }

        public int offsetY() {
            return this.offsetY;
        }

        public int width() {
            return this.width;
        }

        public int height() {
            return this.height;
        }

        public int advance() {
            return this.advance;
        }

        public int ascent() {
            return this.ascent;
        }

        @Override // com.mojang.blaze3d.font.UnbakedGlyph
        public GlyphInfo info() {
            return GlyphInfo.simple(this.advance);
        }

        @Override // com.mojang.blaze3d.font.UnbakedGlyph
        public BakedGlyph bake(UnbakedGlyph.Stitcher $$0) {
            return $$0.stitch(info(), new GlyphBitmap() { // from class: net.minecraft.client.gui.font.providers.BitmapProvider.Glyph.1
                @Override // com.mojang.blaze3d.font.GlyphBitmap
                public float getOversample() {
                    return 1.0f / Glyph.this.scale;
                }

                @Override // com.mojang.blaze3d.font.GlyphBitmap
                public int getPixelWidth() {
                    return Glyph.this.width;
                }

                @Override // com.mojang.blaze3d.font.GlyphBitmap
                public int getPixelHeight() {
                    return Glyph.this.height;
                }

                @Override // com.mojang.blaze3d.font.GlyphBitmap
                public float getBearingTop() {
                    return Glyph.this.ascent;
                }

                @Override // com.mojang.blaze3d.font.GlyphBitmap
                public void upload(int $$02, int $$1, GpuTexture $$2) {
                    RenderSystem.getDevice().createCommandEncoder().writeToTexture($$2, Glyph.this.image, 0, 0, $$02, $$1, Glyph.this.width, Glyph.this.height, Glyph.this.offsetX, Glyph.this.offsetY);
                }

                @Override // com.mojang.blaze3d.font.GlyphBitmap
                public boolean isColored() {
                    return Glyph.this.image.format().components() > 1;
                }
            });
        }
    }
}
