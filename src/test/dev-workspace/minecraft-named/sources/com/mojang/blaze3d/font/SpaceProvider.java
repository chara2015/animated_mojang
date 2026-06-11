package com.mojang.blaze3d.font;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import net.minecraft.client.gui.font.glyphs.EmptyGlyph;
import net.minecraft.client.gui.font.providers.GlyphProviderDefinition;
import net.minecraft.client.gui.font.providers.GlyphProviderType;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/font/SpaceProvider.class */
public class SpaceProvider implements GlyphProvider {
    private final Int2ObjectMap<EmptyGlyph> glyphs;

    public SpaceProvider(Map<Integer, Float> $$0) {
        this.glyphs = new Int2ObjectOpenHashMap($$0.size());
        $$0.forEach(($$02, $$1) -> {
            this.glyphs.put($$02.intValue(), new EmptyGlyph($$1.floatValue()));
        });
    }

    @Override // com.mojang.blaze3d.font.GlyphProvider
    public UnbakedGlyph getGlyph(int $$0) {
        return (UnbakedGlyph) this.glyphs.get($$0);
    }

    @Override // com.mojang.blaze3d.font.GlyphProvider
    public IntSet getSupportedGlyphs() {
        return IntSets.unmodifiable(this.glyphs.keySet());
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/font/SpaceProvider$Definition.class */
    public static final class Definition extends Record implements GlyphProviderDefinition {
        private final Map<Integer, Float> advances;
        public static final MapCodec<Definition> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Codec.unboundedMap(ExtraCodecs.CODEPOINT, Codec.FLOAT).fieldOf("advances").forGetter((v0) -> {
                return v0.advances();
            })).apply($$0, Definition::new);
        });

        public Definition(Map<Integer, Float> $$0) {
            this.advances = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Definition.class), Definition.class, "advances", "FIELD:Lcom/mojang/blaze3d/font/SpaceProvider$Definition;->advances:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Definition.class), Definition.class, "advances", "FIELD:Lcom/mojang/blaze3d/font/SpaceProvider$Definition;->advances:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Definition.class, Object.class), Definition.class, "advances", "FIELD:Lcom/mojang/blaze3d/font/SpaceProvider$Definition;->advances:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Map<Integer, Float> advances() {
            return this.advances;
        }

        @Override // net.minecraft.client.gui.font.providers.GlyphProviderDefinition
        public GlyphProviderType type() {
            return GlyphProviderType.SPACE;
        }

        @Override // net.minecraft.client.gui.font.providers.GlyphProviderDefinition
        public Either<GlyphProviderDefinition.Loader, GlyphProviderDefinition.Reference> unpack() {
            GlyphProviderDefinition.Loader $$0 = $$02 -> {
                return new SpaceProvider(this.advances);
            };
            return Either.left($$0);
        }
    }
}
