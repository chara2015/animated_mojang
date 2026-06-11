package net.minecraft.sounds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryFileCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/sounds/SoundEvent.class */
public final class SoundEvent extends Record {
    private final Identifier location;
    private final Optional<Float> fixedRange;
    public static final Codec<SoundEvent> DIRECT_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Identifier.CODEC.fieldOf("sound_id").forGetter((v0) -> {
            return v0.location();
        }), Codec.FLOAT.lenientOptionalFieldOf("range").forGetter((v0) -> {
            return v0.fixedRange();
        })).apply($$0, SoundEvent::create);
    });
    public static final Codec<Holder<SoundEvent>> CODEC = RegistryFileCodec.create(Registries.SOUND_EVENT, DIRECT_CODEC);
    public static final StreamCodec<ByteBuf, SoundEvent> DIRECT_STREAM_CODEC = StreamCodec.composite(Identifier.STREAM_CODEC, (v0) -> {
        return v0.location();
    }, ByteBufCodecs.FLOAT.apply(ByteBufCodecs::optional), (v0) -> {
        return v0.fixedRange();
    }, SoundEvent::create);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<SoundEvent>> STREAM_CODEC = ByteBufCodecs.holder(Registries.SOUND_EVENT, DIRECT_STREAM_CODEC);

    public SoundEvent(Identifier $$0, Optional<Float> $$1) {
        this.location = $$0;
        this.fixedRange = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SoundEvent.class), SoundEvent.class, "location;fixedRange", "FIELD:Lnet/minecraft/sounds/SoundEvent;->location:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/sounds/SoundEvent;->fixedRange:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SoundEvent.class), SoundEvent.class, "location;fixedRange", "FIELD:Lnet/minecraft/sounds/SoundEvent;->location:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/sounds/SoundEvent;->fixedRange:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SoundEvent.class, Object.class), SoundEvent.class, "location;fixedRange", "FIELD:Lnet/minecraft/sounds/SoundEvent;->location:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/sounds/SoundEvent;->fixedRange:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Identifier location() {
        return this.location;
    }

    public Optional<Float> fixedRange() {
        return this.fixedRange;
    }

    private static SoundEvent create(Identifier $$0, Optional<Float> $$1) {
        return (SoundEvent) $$1.map($$12 -> {
            return createFixedRangeEvent($$0, $$12.floatValue());
        }).orElseGet(() -> {
            return createVariableRangeEvent($$0);
        });
    }

    public static SoundEvent createVariableRangeEvent(Identifier $$0) {
        return new SoundEvent($$0, Optional.empty());
    }

    public static SoundEvent createFixedRangeEvent(Identifier $$0, float $$1) {
        return new SoundEvent($$0, Optional.of(Float.valueOf($$1)));
    }

    public float getRange(float $$0) {
        return this.fixedRange.orElse(Float.valueOf($$0 > 1.0f ? 16.0f * $$0 : 16.0f)).floatValue();
    }
}
