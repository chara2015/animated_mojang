package net.minecraft.world.level.levelgen.feature.featuresize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.OptionalInt;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/featuresize/TwoLayersFeatureSize.class */
public class TwoLayersFeatureSize extends FeatureSize {
    public static final MapCodec<TwoLayersFeatureSize> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.intRange(0, 81).fieldOf("limit").orElse(1).forGetter($$0 -> {
            return Integer.valueOf($$0.limit);
        }), Codec.intRange(0, 16).fieldOf("lower_size").orElse(0).forGetter($$02 -> {
            return Integer.valueOf($$02.lowerSize);
        }), Codec.intRange(0, 16).fieldOf("upper_size").orElse(1).forGetter($$03 -> {
            return Integer.valueOf($$03.upperSize);
        }), minClippedHeightCodec()).apply($$0, (v1, v2, v3, v4) -> {
            return new TwoLayersFeatureSize(v1, v2, v3, v4);
        });
    });
    private final int limit;
    private final int lowerSize;
    private final int upperSize;

    public TwoLayersFeatureSize(int $$0, int $$1, int $$2) {
        this($$0, $$1, $$2, OptionalInt.empty());
    }

    public TwoLayersFeatureSize(int $$0, int $$1, int $$2, OptionalInt $$3) {
        super($$3);
        this.limit = $$0;
        this.lowerSize = $$1;
        this.upperSize = $$2;
    }

    @Override // net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize
    protected FeatureSizeType<?> type() {
        return FeatureSizeType.TWO_LAYERS_FEATURE_SIZE;
    }

    @Override // net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize
    public int getSizeAtHeight(int $$0, int $$1) {
        return $$1 < this.limit ? this.lowerSize : this.upperSize;
    }
}
