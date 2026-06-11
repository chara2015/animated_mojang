package net.minecraft.client.resources.metadata.animation;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/metadata/animation/AnimationFrame.class */
public final class AnimationFrame extends Record {
    private final int index;
    private final Optional<Integer> time;
    public static final Codec<AnimationFrame> FULL_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ExtraCodecs.NON_NEGATIVE_INT.fieldOf("index").forGetter((v0) -> {
            return v0.index();
        }), ExtraCodecs.POSITIVE_INT.optionalFieldOf("time").forGetter((v0) -> {
            return v0.time();
        })).apply($$0, (v1, v2) -> {
            return new AnimationFrame(v1, v2);
        });
    });
    public static final Codec<AnimationFrame> CODEC = Codec.either(ExtraCodecs.NON_NEGATIVE_INT, FULL_CODEC).xmap($$0 -> {
        return (AnimationFrame) $$0.map((v1) -> {
            return new AnimationFrame(v1);
        }, $$0 -> {
            return $$0;
        });
    }, $$02 -> {
        return $$02.time.isPresent() ? Either.right($$02) : Either.left(Integer.valueOf($$02.index));
    });

    public AnimationFrame(int $$0, Optional<Integer> $$1) {
        this.index = $$0;
        this.time = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AnimationFrame.class), AnimationFrame.class, "index;time", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationFrame;->index:I", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationFrame;->time:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AnimationFrame.class), AnimationFrame.class, "index;time", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationFrame;->index:I", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationFrame;->time:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AnimationFrame.class, Object.class), AnimationFrame.class, "index;time", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationFrame;->index:I", "FIELD:Lnet/minecraft/client/resources/metadata/animation/AnimationFrame;->time:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int index() {
        return this.index;
    }

    public Optional<Integer> time() {
        return this.time;
    }

    public AnimationFrame(int $$0) {
        this($$0, Optional.empty());
    }

    public int timeOr(int $$0) {
        return this.time.orElse(Integer.valueOf($$0)).intValue();
    }
}
