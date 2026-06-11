package net.minecraft.client.renderer.texture.atlas.sources;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntUnaryOperator;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.resources.metadata.animation.FrameSize;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.ARGB;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations.class */
public final class PalettedPermutations extends Record implements SpriteSource {
    private final List<Identifier> textures;
    private final Identifier paletteKey;
    private final Map<String, Identifier> permutations;
    private final String separator;
    public static final String DEFAULT_SEPARATOR = "_";
    static final Logger LOGGER = LogUtils.getLogger();
    public static final MapCodec<PalettedPermutations> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.list(Identifier.CODEC).fieldOf("textures").forGetter((v0) -> {
            return v0.textures();
        }), Identifier.CODEC.fieldOf("palette_key").forGetter((v0) -> {
            return v0.paletteKey();
        }), Codec.unboundedMap(Codec.STRING, Identifier.CODEC).fieldOf("permutations").forGetter((v0) -> {
            return v0.permutations();
        }), Codec.STRING.optionalFieldOf("separator", "_").forGetter((v0) -> {
            return v0.separator();
        })).apply($$0, PalettedPermutations::new);
    });

    public PalettedPermutations(List<Identifier> $$0, Identifier $$1, Map<String, Identifier> $$2, String $$3) {
        this.textures = $$0;
        this.paletteKey = $$1;
        this.permutations = $$2;
        this.separator = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PalettedPermutations.class), PalettedPermutations.class, "textures;paletteKey;permutations;separator", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations;->textures:Ljava/util/List;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations;->paletteKey:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations;->permutations:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations;->separator:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PalettedPermutations.class), PalettedPermutations.class, "textures;paletteKey;permutations;separator", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations;->textures:Ljava/util/List;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations;->paletteKey:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations;->permutations:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations;->separator:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PalettedPermutations.class, Object.class), PalettedPermutations.class, "textures;paletteKey;permutations;separator", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations;->textures:Ljava/util/List;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations;->paletteKey:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations;->permutations:Ljava/util/Map;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations;->separator:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public List<Identifier> textures() {
        return this.textures;
    }

    public Identifier paletteKey() {
        return this.paletteKey;
    }

    public Map<String, Identifier> permutations() {
        return this.permutations;
    }

    public String separator() {
        return this.separator;
    }

    public PalettedPermutations(List<Identifier> $$0, Identifier $$1, Map<String, Identifier> $$2) {
        this($$0, $$1, $$2, "_");
    }

    @Override // net.minecraft.client.renderer.texture.atlas.SpriteSource
    public void run(ResourceManager $$0, SpriteSource.Output $$1) {
        Supplier supplierMemoize = Suppliers.memoize(() -> {
            return loadPaletteEntryFromImage($$0, this.paletteKey);
        });
        Map<String, java.util.function.Supplier<IntUnaryOperator>> $$3 = new HashMap<>();
        this.permutations.forEach(($$32, $$4) -> {
            $$3.put($$32, Suppliers.memoize(() -> {
                return createPaletteMapping((int[]) supplierMemoize.get(), loadPaletteEntryFromImage($$0, $$4));
            }));
        });
        for (Identifier $$42 : this.textures) {
            Identifier $$5 = TEXTURE_ID_CONVERTER.idToFile($$42);
            Optional<Resource> $$6 = $$0.getResource($$5);
            if ($$6.isEmpty()) {
                LOGGER.warn("Unable to find texture {}", $$5);
            } else {
                LazyLoadedImage $$7 = new LazyLoadedImage($$5, $$6.get(), $$3.size());
                for (Map.Entry<String, java.util.function.Supplier<IntUnaryOperator>> $$8 : $$3.entrySet()) {
                    Identifier $$9 = $$42.withSuffix(this.separator + $$8.getKey());
                    $$1.add($$9, new PalettedSpriteSupplier($$7, $$8.getValue(), $$9));
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static IntUnaryOperator createPaletteMapping(int[] $$0, int[] $$1) {
        if ($$1.length != $$0.length) {
            LOGGER.warn("Palette mapping has different sizes: {} and {}", Integer.valueOf($$0.length), Integer.valueOf($$1.length));
            throw new IllegalArgumentException();
        }
        Int2IntOpenHashMap int2IntOpenHashMap = new Int2IntOpenHashMap($$1.length);
        for (int $$3 = 0; $$3 < $$0.length; $$3++) {
            int $$4 = $$0[$$3];
            if (ARGB.alpha($$4) != 0) {
                int2IntOpenHashMap.put(ARGB.transparent($$4), $$1[$$3]);
            }
        }
        return $$12 -> {
            int $$2 = ARGB.alpha($$12);
            if ($$2 == 0) {
                return $$12;
            }
            int $$32 = ARGB.transparent($$12);
            int $$42 = int2IntOpenHashMap.getOrDefault($$32, ARGB.opaque($$32));
            int $$5 = ARGB.alpha($$42);
            return ARGB.color(($$2 * $$5) / 255, $$42);
        };
    }

    private static int[] loadPaletteEntryFromImage(ResourceManager $$0, Identifier $$1) {
        Optional<Resource> $$2 = $$0.getResource(TEXTURE_ID_CONVERTER.idToFile($$1));
        if ($$2.isEmpty()) {
            LOGGER.error("Failed to load palette image {}", $$1);
            throw new IllegalArgumentException();
        }
        try {
            InputStream $$3 = $$2.get().open();
            try {
                NativeImage $$4 = NativeImage.read($$3);
                try {
                    int[] pixels = $$4.getPixels();
                    if ($$4 != null) {
                        $$4.close();
                    }
                    if ($$3 != null) {
                        $$3.close();
                    }
                    return pixels;
                } catch (Throwable th) {
                    if ($$4 != null) {
                        try {
                            $$4.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } finally {
            }
        } catch (Exception $$5) {
            LOGGER.error("Couldn't load texture {}", $$1, $$5);
            throw new IllegalArgumentException();
        }
    }

    @Override // net.minecraft.client.renderer.texture.atlas.SpriteSource
    public MapCodec<PalettedPermutations> codec() {
        return MAP_CODEC;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier.class */
    static final class PalettedSpriteSupplier extends Record implements SpriteSource.DiscardableLoader {
        private final LazyLoadedImage baseImage;
        private final java.util.function.Supplier<IntUnaryOperator> palette;
        private final Identifier permutationLocation;

        PalettedSpriteSupplier(LazyLoadedImage $$0, java.util.function.Supplier<IntUnaryOperator> $$1, Identifier $$2) {
            this.baseImage = $$0;
            this.palette = $$1;
            this.permutationLocation = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PalettedSpriteSupplier.class), PalettedSpriteSupplier.class, "baseImage;palette;permutationLocation", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;->baseImage:Lnet/minecraft/client/renderer/texture/atlas/sources/LazyLoadedImage;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;->palette:Ljava/util/function/Supplier;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;->permutationLocation:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PalettedSpriteSupplier.class), PalettedSpriteSupplier.class, "baseImage;palette;permutationLocation", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;->baseImage:Lnet/minecraft/client/renderer/texture/atlas/sources/LazyLoadedImage;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;->palette:Ljava/util/function/Supplier;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;->permutationLocation:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PalettedSpriteSupplier.class, Object.class), PalettedSpriteSupplier.class, "baseImage;palette;permutationLocation", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;->baseImage:Lnet/minecraft/client/renderer/texture/atlas/sources/LazyLoadedImage;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;->palette:Ljava/util/function/Supplier;", "FIELD:Lnet/minecraft/client/renderer/texture/atlas/sources/PalettedPermutations$PalettedSpriteSupplier;->permutationLocation:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public LazyLoadedImage baseImage() {
            return this.baseImage;
        }

        public java.util.function.Supplier<IntUnaryOperator> palette() {
            return this.palette;
        }

        public Identifier permutationLocation() {
            return this.permutationLocation;
        }

        @Override // net.minecraft.client.renderer.texture.atlas.SpriteSource.Loader
        public SpriteContents get(SpriteResourceLoader $$0) {
            try {
                try {
                    NativeImage $$1 = this.baseImage.get().mappedCopy(this.palette.get());
                    SpriteContents spriteContents = new SpriteContents(this.permutationLocation, new FrameSize($$1.getWidth(), $$1.getHeight()), $$1);
                    this.baseImage.release();
                    return spriteContents;
                } catch (IOException | IllegalArgumentException $$2) {
                    PalettedPermutations.LOGGER.error("unable to apply palette to {}", this.permutationLocation, $$2);
                    this.baseImage.release();
                    return null;
                }
            } catch (Throwable th) {
                this.baseImage.release();
                throw th;
            }
        }

        @Override // net.minecraft.client.renderer.texture.atlas.SpriteSource.DiscardableLoader
        public void discard() {
            this.baseImage.release();
        }
    }
}
