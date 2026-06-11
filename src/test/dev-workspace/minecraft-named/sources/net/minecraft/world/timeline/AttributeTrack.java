package net.minecraft.world.timeline;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import java.util.function.LongSupplier;
import net.minecraft.util.KeyframeTrack;
import net.minecraft.util.Util;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.modifier.AttributeModifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/timeline/AttributeTrack.class */
public final class AttributeTrack<Value, Argument> extends Record {
    private final AttributeModifier<Value, Argument> modifier;
    private final KeyframeTrack<Argument> argumentTrack;

    public AttributeTrack(AttributeModifier<Value, Argument> $$0, KeyframeTrack<Argument> $$1) {
        this.modifier = $$0;
        this.argumentTrack = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AttributeTrack.class), AttributeTrack.class, "modifier;argumentTrack", "FIELD:Lnet/minecraft/world/timeline/AttributeTrack;->modifier:Lnet/minecraft/world/attribute/modifier/AttributeModifier;", "FIELD:Lnet/minecraft/world/timeline/AttributeTrack;->argumentTrack:Lnet/minecraft/util/KeyframeTrack;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AttributeTrack.class), AttributeTrack.class, "modifier;argumentTrack", "FIELD:Lnet/minecraft/world/timeline/AttributeTrack;->modifier:Lnet/minecraft/world/attribute/modifier/AttributeModifier;", "FIELD:Lnet/minecraft/world/timeline/AttributeTrack;->argumentTrack:Lnet/minecraft/util/KeyframeTrack;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AttributeTrack.class, Object.class), AttributeTrack.class, "modifier;argumentTrack", "FIELD:Lnet/minecraft/world/timeline/AttributeTrack;->modifier:Lnet/minecraft/world/attribute/modifier/AttributeModifier;", "FIELD:Lnet/minecraft/world/timeline/AttributeTrack;->argumentTrack:Lnet/minecraft/util/KeyframeTrack;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public AttributeModifier<Value, Argument> modifier() {
        return this.modifier;
    }

    public KeyframeTrack<Argument> argumentTrack() {
        return this.argumentTrack;
    }

    public static <Value> Codec<AttributeTrack<Value, ?>> createCodec(EnvironmentAttribute<Value> $$0) {
        MapCodec<AttributeModifier<Value, ?>> $$1 = $$0.type().modifierCodec().optionalFieldOf("modifier", AttributeModifier.override());
        return $$1.dispatch((v0) -> {
            return v0.modifier();
        }, Util.memoize($$12 -> {
            return createCodecWithModifier($$0, $$12);
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <Value, Argument> MapCodec<AttributeTrack<Value, Argument>> createCodecWithModifier(EnvironmentAttribute<Value> $$0, AttributeModifier<Value, Argument> $$1) {
        return KeyframeTrack.mapCodec($$1.argumentCodec($$0)).xmap($$12 -> {
            return new AttributeTrack($$1, $$12);
        }, (v0) -> {
            return v0.argumentTrack();
        });
    }

    public AttributeTrackSampler<Value, Argument> bakeSampler(EnvironmentAttribute<Value> $$0, Optional<Integer> $$1, LongSupplier $$2) {
        return new AttributeTrackSampler<>($$1, this.modifier, this.argumentTrack, this.modifier.argumentKeyframeLerp($$0), $$2);
    }

    public static DataResult<AttributeTrack<?, ?>> validatePeriod(AttributeTrack<?, ?> $$0, int $$1) {
        return KeyframeTrack.validatePeriod($$0.argumentTrack(), $$1).map($$12 -> {
            return $$0;
        });
    }
}
