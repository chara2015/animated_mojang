package net.minecraft.world.level.levelgen.feature.featuresize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.core.registries.BuiltInRegistries;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/levelgen/feature/featuresize/FeatureSize.class */
public abstract class FeatureSize {
    public static final Codec<FeatureSize> CODEC = BuiltInRegistries.FEATURE_SIZE_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.type();
    }, (v0) -> {
        return v0.codec();
    });
    protected static final int MAX_WIDTH = 16;
    protected final OptionalInt minClippedHeight;

    protected abstract FeatureSizeType<?> type();

    public abstract int getSizeAtHeight(int i, int i2);

    protected static <S extends FeatureSize> RecordCodecBuilder<S, OptionalInt> minClippedHeightCodec() {
        return Codec.intRange(0, 80).optionalFieldOf("min_clipped_height").xmap($$0 -> {
            return (OptionalInt) $$0.map((v0) -> {
                return OptionalInt.of(v0);
            }).orElse(OptionalInt.empty());
        }, $$02 -> {
            return $$02.isPresent() ? Optional.of(Integer.valueOf($$02.getAsInt())) : Optional.empty();
        }).forGetter($$03 -> {
            return $$03.minClippedHeight;
        });
    }

    public FeatureSize(OptionalInt $$0) {
        this.minClippedHeight = $$0;
    }

    public OptionalInt minClippedHeight() {
        return this.minClippedHeight;
    }
}
