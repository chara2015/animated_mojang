package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/heightproviders/BiasedToBottomHeight.class */
public class BiasedToBottomHeight extends HeightProvider {
    public static final MapCodec<BiasedToBottomHeight> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(VerticalAnchor.CODEC.fieldOf("min_inclusive").forGetter($$0 -> {
            return $$0.minInclusive;
        }), VerticalAnchor.CODEC.fieldOf("max_inclusive").forGetter($$02 -> {
            return $$02.maxInclusive;
        }), Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("inner", 1).forGetter($$03 -> {
            return Integer.valueOf($$03.inner);
        })).apply($$0, (v1, v2, v3) -> {
            return new BiasedToBottomHeight(v1, v2, v3);
        });
    });
    private static final Logger LOGGER = LogUtils.getLogger();
    private final VerticalAnchor minInclusive;
    private final VerticalAnchor maxInclusive;
    private final int inner;

    private BiasedToBottomHeight(VerticalAnchor $$0, VerticalAnchor $$1, int $$2) {
        this.minInclusive = $$0;
        this.maxInclusive = $$1;
        this.inner = $$2;
    }

    public static BiasedToBottomHeight of(VerticalAnchor $$0, VerticalAnchor $$1, int $$2) {
        return new BiasedToBottomHeight($$0, $$1, $$2);
    }

    @Override // net.minecraft.world.level.levelgen.heightproviders.HeightProvider
    public int sample(RandomSource $$0, WorldGenerationContext $$1) {
        int $$2 = this.minInclusive.resolveY($$1);
        int $$3 = this.maxInclusive.resolveY($$1);
        if ((($$3 - $$2) - this.inner) + 1 <= 0) {
            LOGGER.warn("Empty height range: {}", this);
            return $$2;
        }
        int $$4 = $$0.nextInt((($$3 - $$2) - this.inner) + 1);
        return $$0.nextInt($$4 + this.inner) + $$2;
    }

    @Override // net.minecraft.world.level.levelgen.heightproviders.HeightProvider
    public HeightProviderType<?> getType() {
        return HeightProviderType.BIASED_TO_BOTTOM;
    }

    public String toString() {
        return "biased[" + String.valueOf(this.minInclusive) + "-" + String.valueOf(this.maxInclusive) + " inner: " + this.inner + "]";
    }
}
