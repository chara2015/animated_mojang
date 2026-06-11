package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/valueproviders/ConstantFloat.class */
public class ConstantFloat extends FloatProvider {
    public static final ConstantFloat ZERO = new ConstantFloat(0.0f);
    public static final MapCodec<ConstantFloat> CODEC = Codec.FLOAT.fieldOf("value").xmap((v0) -> {
        return of(v0);
    }, (v0) -> {
        return v0.getValue();
    });
    private final float value;

    public static ConstantFloat of(float $$0) {
        if ($$0 == 0.0f) {
            return ZERO;
        }
        return new ConstantFloat($$0);
    }

    private ConstantFloat(float $$0) {
        this.value = $$0;
    }

    public float getValue() {
        return this.value;
    }

    @Override // net.minecraft.util.valueproviders.SampledFloat
    public float sample(RandomSource $$0) {
        return this.value;
    }

    @Override // net.minecraft.util.valueproviders.FloatProvider
    public float getMinValue() {
        return this.value;
    }

    @Override // net.minecraft.util.valueproviders.FloatProvider
    public float getMaxValue() {
        return this.value;
    }

    @Override // net.minecraft.util.valueproviders.FloatProvider
    public FloatProviderType<?> getType() {
        return FloatProviderType.CONSTANT;
    }

    public String toString() {
        return Float.toString(this.value);
    }
}
