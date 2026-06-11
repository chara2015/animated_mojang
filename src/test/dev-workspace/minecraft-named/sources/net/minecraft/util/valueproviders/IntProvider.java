package net.minecraft.util.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.RandomSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/valueproviders/IntProvider.class */
public abstract class IntProvider {
    private static final Codec<Either<Integer, IntProvider>> CONSTANT_OR_DISPATCH_CODEC = Codec.either(Codec.INT, BuiltInRegistries.INT_PROVIDER_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.getType();
    }, (v0) -> {
        return v0.codec();
    }));
    public static final Codec<IntProvider> CODEC = CONSTANT_OR_DISPATCH_CODEC.xmap($$0 -> {
        return (IntProvider) $$0.map((v0) -> {
            return ConstantInt.of(v0);
        }, $$0 -> {
            return $$0;
        });
    }, $$02 -> {
        return $$02.getType() == IntProviderType.CONSTANT ? Either.left(Integer.valueOf(((ConstantInt) $$02).getValue())) : Either.right($$02);
    });
    public static final Codec<IntProvider> NON_NEGATIVE_CODEC = codec(0, Integer.MAX_VALUE);
    public static final Codec<IntProvider> POSITIVE_CODEC = codec(1, Integer.MAX_VALUE);

    public abstract int sample(RandomSource randomSource);

    public abstract int getMinValue();

    public abstract int getMaxValue();

    public abstract IntProviderType<?> getType();

    public static Codec<IntProvider> codec(int $$0, int $$1) {
        return validateCodec($$0, $$1, CODEC);
    }

    public static <T extends IntProvider> Codec<T> validateCodec(int $$0, int $$1, Codec<T> $$2) {
        return $$2.validate($$22 -> {
            return validate($$0, $$1, $$22);
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T extends IntProvider> DataResult<T> validate(int $$0, int $$1, T $$2) {
        if ($$2.getMinValue() < $$0) {
            return DataResult.error(() -> {
                return "Value provider too low: " + $$0 + " [" + $$2.getMinValue() + "-" + $$2.getMaxValue() + "]";
            });
        }
        if ($$2.getMaxValue() > $$1) {
            return DataResult.error(() -> {
                return "Value provider too high: " + $$1 + " [" + $$2.getMinValue() + "-" + $$2.getMaxValue() + "]";
            });
        }
        return DataResult.success($$2);
    }
}
