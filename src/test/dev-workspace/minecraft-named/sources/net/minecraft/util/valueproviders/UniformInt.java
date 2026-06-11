package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/valueproviders/UniformInt.class */
public class UniformInt extends IntProvider {
    public static final MapCodec<UniformInt> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.INT.fieldOf("min_inclusive").forGetter($$0 -> {
            return Integer.valueOf($$0.minInclusive);
        }), Codec.INT.fieldOf("max_inclusive").forGetter($$02 -> {
            return Integer.valueOf($$02.maxInclusive);
        })).apply($$0, (v1, v2) -> {
            return new UniformInt(v1, v2);
        });
    }).validate($$02 -> {
        if ($$02.maxInclusive < $$02.minInclusive) {
            return DataResult.error(() -> {
                return "Max must be at least min, min_inclusive: " + $$02.minInclusive + ", max_inclusive: " + $$02.maxInclusive;
            });
        }
        return DataResult.success($$02);
    });
    private final int minInclusive;
    private final int maxInclusive;

    private UniformInt(int $$0, int $$1) {
        this.minInclusive = $$0;
        this.maxInclusive = $$1;
    }

    public static UniformInt of(int $$0, int $$1) {
        return new UniformInt($$0, $$1);
    }

    @Override // net.minecraft.util.valueproviders.IntProvider
    public int sample(RandomSource $$0) {
        return Mth.randomBetweenInclusive($$0, this.minInclusive, this.maxInclusive);
    }

    @Override // net.minecraft.util.valueproviders.IntProvider
    public int getMinValue() {
        return this.minInclusive;
    }

    @Override // net.minecraft.util.valueproviders.IntProvider
    public int getMaxValue() {
        return this.maxInclusive;
    }

    @Override // net.minecraft.util.valueproviders.IntProvider
    public IntProviderType<?> getType() {
        return IntProviderType.UNIFORM;
    }

    public String toString() {
        return "[" + this.minInclusive + "-" + this.maxInclusive + "]";
    }
}
