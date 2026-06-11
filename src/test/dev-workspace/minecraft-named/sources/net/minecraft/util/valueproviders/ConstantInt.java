package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/valueproviders/ConstantInt.class */
public class ConstantInt extends IntProvider {
    public static final ConstantInt ZERO = new ConstantInt(0);
    public static final MapCodec<ConstantInt> CODEC = Codec.INT.fieldOf("value").xmap((v0) -> {
        return of(v0);
    }, (v0) -> {
        return v0.getValue();
    });
    private final int value;

    public static ConstantInt of(int $$0) {
        if ($$0 == 0) {
            return ZERO;
        }
        return new ConstantInt($$0);
    }

    private ConstantInt(int $$0) {
        this.value = $$0;
    }

    public int getValue() {
        return this.value;
    }

    @Override // net.minecraft.util.valueproviders.IntProvider
    public int sample(RandomSource $$0) {
        return this.value;
    }

    @Override // net.minecraft.util.valueproviders.IntProvider
    public int getMinValue() {
        return this.value;
    }

    @Override // net.minecraft.util.valueproviders.IntProvider
    public int getMaxValue() {
        return this.value;
    }

    @Override // net.minecraft.util.valueproviders.IntProvider
    public IntProviderType<?> getType() {
        return IntProviderType.CONSTANT;
    }

    public String toString() {
        return Integer.toString(this.value);
    }
}
