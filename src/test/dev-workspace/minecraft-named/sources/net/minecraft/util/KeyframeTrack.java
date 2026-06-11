package net.minecraft.util;

import com.google.common.collect.Comparators;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import net.minecraft.world.attribute.LerpFunction;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/KeyframeTrack.class */
public final class KeyframeTrack<T> extends Record {
    private final List<Keyframe<T>> keyframes;
    private final EasingType easingType;

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, KeyframeTrack.class), KeyframeTrack.class, "keyframes;easingType", "FIELD:Lnet/minecraft/util/KeyframeTrack;->keyframes:Ljava/util/List;", "FIELD:Lnet/minecraft/util/KeyframeTrack;->easingType:Lnet/minecraft/util/EasingType;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, KeyframeTrack.class), KeyframeTrack.class, "keyframes;easingType", "FIELD:Lnet/minecraft/util/KeyframeTrack;->keyframes:Ljava/util/List;", "FIELD:Lnet/minecraft/util/KeyframeTrack;->easingType:Lnet/minecraft/util/EasingType;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, KeyframeTrack.class, Object.class), KeyframeTrack.class, "keyframes;easingType", "FIELD:Lnet/minecraft/util/KeyframeTrack;->keyframes:Ljava/util/List;", "FIELD:Lnet/minecraft/util/KeyframeTrack;->easingType:Lnet/minecraft/util/EasingType;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public List<Keyframe<T>> keyframes() {
        return this.keyframes;
    }

    public EasingType easingType() {
        return this.easingType;
    }

    public KeyframeTrack(List<Keyframe<T>> $$0, EasingType $$1) {
        if (!$$0.isEmpty()) {
            this.keyframes = $$0;
            this.easingType = $$1;
            return;
        }
        throw new IllegalArgumentException("Track has no keyframes");
    }

    public static <T> MapCodec<KeyframeTrack<T>> mapCodec(Codec<T> $$0) {
        Codec<List<Keyframe<T>>> $$1 = Keyframe.codec($$0).listOf().validate(KeyframeTrack::validateKeyframes);
        return RecordCodecBuilder.mapCodec($$12 -> {
            return $$12.group($$1.fieldOf("keyframes").forGetter((v0) -> {
                return v0.keyframes();
            }), EasingType.CODEC.optionalFieldOf("ease", EasingType.LINEAR).forGetter((v0) -> {
                return v0.easingType();
            })).apply($$12, KeyframeTrack::new);
        });
    }

    static <T> DataResult<List<Keyframe<T>>> validateKeyframes(List<Keyframe<T>> $$0) {
        if ($$0.isEmpty()) {
            return DataResult.error(() -> {
                return "Keyframes must not be empty";
            });
        }
        if (!Comparators.isInOrder($$0, Comparator.comparingInt((v0) -> {
            return v0.ticks();
        }))) {
            return DataResult.error(() -> {
                return "Keyframes must be ordered by ticks field";
            });
        }
        if ($$0.size() > 1) {
            int $$1 = 0;
            int $$2 = ((Keyframe) $$0.getLast()).ticks();
            for (Keyframe<T> $$3 : $$0) {
                if ($$3.ticks() == $$2) {
                    $$1++;
                    if ($$1 > 2) {
                        return DataResult.error(() -> {
                            return "More than 2 keyframes on same tick: " + $$3.ticks();
                        });
                    }
                } else {
                    $$1 = 0;
                }
                $$2 = $$3.ticks();
            }
        }
        return DataResult.success($$0);
    }

    public static DataResult<KeyframeTrack<?>> validatePeriod(KeyframeTrack<?> $$0, int $$1) {
        for (Keyframe<?> $$2 : $$0.keyframes()) {
            int $$3 = $$2.ticks();
            if ($$3 < 0 || $$3 > $$1) {
                return DataResult.error(() -> {
                    return "Keyframe at tick " + $$2.ticks() + " must be in range [0; " + $$1 + "]";
                });
            }
        }
        return DataResult.success($$0);
    }

    public KeyframeTrackSampler<T> bakeSampler(Optional<Integer> $$0, LerpFunction<T> $$1) {
        return new KeyframeTrackSampler<>(this, $$0, $$1);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/KeyframeTrack$Builder.class */
    public static class Builder<T> {
        private final ImmutableList.Builder<Keyframe<T>> keyframes = ImmutableList.builder();
        private EasingType easing = EasingType.LINEAR;

        public Builder<T> addKeyframe(int $$0, T $$1) {
            this.keyframes.add(new Keyframe($$0, $$1));
            return this;
        }

        public Builder<T> setEasing(EasingType $$0) {
            this.easing = $$0;
            return this;
        }

        public KeyframeTrack<T> build() {
            List<Keyframe<T>> $$0 = (List) KeyframeTrack.validateKeyframes(this.keyframes.build()).getOrThrow();
            return new KeyframeTrack<>($$0, this.easing);
        }
    }
}
