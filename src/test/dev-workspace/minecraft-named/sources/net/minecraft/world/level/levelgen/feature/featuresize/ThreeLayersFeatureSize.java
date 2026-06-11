package net.minecraft.world.level.levelgen.feature.featuresize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.OptionalInt;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/featuresize/ThreeLayersFeatureSize.class */
public class ThreeLayersFeatureSize extends FeatureSize {
    public static final MapCodec<ThreeLayersFeatureSize> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.intRange(0, 80).fieldOf("limit").orElse(1).forGetter($$0 -> {
            return Integer.valueOf($$0.limit);
        }), Codec.intRange(0, 80).fieldOf("upper_limit").orElse(1).forGetter($$02 -> {
            return Integer.valueOf($$02.upperLimit);
        }), Codec.intRange(0, 16).fieldOf("lower_size").orElse(0).forGetter($$03 -> {
            return Integer.valueOf($$03.lowerSize);
        }), Codec.intRange(0, 16).fieldOf("middle_size").orElse(1).forGetter($$04 -> {
            return Integer.valueOf($$04.middleSize);
        }), Codec.intRange(0, 16).fieldOf("upper_size").orElse(1).forGetter($$05 -> {
            return Integer.valueOf($$05.upperSize);
        }), minClippedHeightCodec()).apply($$0, (v1, v2, v3, v4, v5, v6) -> {
            return new ThreeLayersFeatureSize(v1, v2, v3, v4, v5, v6);
        });
    });
    private final int limit;
    private final int upperLimit;
    private final int lowerSize;
    private final int middleSize;
    private final int upperSize;

    public ThreeLayersFeatureSize(int $$0, int $$1, int $$2, int $$3, int $$4, OptionalInt $$5) {
        super($$5);
        this.limit = $$0;
        this.upperLimit = $$1;
        this.lowerSize = $$2;
        this.middleSize = $$3;
        this.upperSize = $$4;
    }

    @Override // net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize
    protected FeatureSizeType<?> type() {
        return FeatureSizeType.THREE_LAYERS_FEATURE_SIZE;
    }

    @Override // net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize
    public int getSizeAtHeight(int $$0, int $$1) {
        if ($$1 < this.limit) {
            return this.lowerSize;
        }
        if ($$1 >= $$0 - this.upperLimit) {
            return this.upperSize;
        }
        return this.middleSize;
    }
}
