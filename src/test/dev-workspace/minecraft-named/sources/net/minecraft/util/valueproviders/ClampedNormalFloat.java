package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/valueproviders/ClampedNormalFloat.class */
public class ClampedNormalFloat extends FloatProvider {
    public static final MapCodec<ClampedNormalFloat> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.FLOAT.fieldOf("mean").forGetter($$0 -> {
            return Float.valueOf($$0.mean);
        }), Codec.FLOAT.fieldOf("deviation").forGetter($$02 -> {
            return Float.valueOf($$02.deviation);
        }), Codec.FLOAT.fieldOf("min").forGetter($$03 -> {
            return Float.valueOf($$03.min);
        }), Codec.FLOAT.fieldOf("max").forGetter($$04 -> {
            return Float.valueOf($$04.max);
        })).apply($$0, (v1, v2, v3, v4) -> {
            return new ClampedNormalFloat(v1, v2, v3, v4);
        });
    }).validate($$02 -> {
        if ($$02.max < $$02.min) {
            return DataResult.error(() -> {
                return "Max must be larger than min: [" + $$02.min + ", " + $$02.max + "]";
            });
        }
        return DataResult.success($$02);
    });
    private final float mean;
    private final float deviation;
    private final float min;
    private final float max;

    public static ClampedNormalFloat of(float $$0, float $$1, float $$2, float $$3) {
        return new ClampedNormalFloat($$0, $$1, $$2, $$3);
    }

    private ClampedNormalFloat(float $$0, float $$1, float $$2, float $$3) {
        this.mean = $$0;
        this.deviation = $$1;
        this.min = $$2;
        this.max = $$3;
    }

    @Override // net.minecraft.util.valueproviders.SampledFloat
    public float sample(RandomSource $$0) {
        return sample($$0, this.mean, this.deviation, this.min, this.max);
    }

    public static float sample(RandomSource $$0, float $$1, float $$2, float $$3, float $$4) {
        return Mth.clamp(Mth.normal($$0, $$1, $$2), $$3, $$4);
    }

    @Override // net.minecraft.util.valueproviders.FloatProvider
    public float getMinValue() {
        return this.min;
    }

    @Override // net.minecraft.util.valueproviders.FloatProvider
    public float getMaxValue() {
        return this.max;
    }

    @Override // net.minecraft.util.valueproviders.FloatProvider
    public FloatProviderType<?> getType() {
        return FloatProviderType.CLAMPED_NORMAL;
    }

    public String toString() {
        return "normal(" + this.mean + ", " + this.deviation + ") in [" + this.min + "-" + this.max + "]";
    }
}
