package net.minecraft.core.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/particles/SculkChargeParticleOptions.class */
public final class SculkChargeParticleOptions extends Record implements ParticleOptions {
    private final float roll;
    public static final MapCodec<SculkChargeParticleOptions> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.FLOAT.fieldOf("roll").forGetter($$0 -> {
            return Float.valueOf($$0.roll);
        })).apply($$0, (v1) -> {
            return new SculkChargeParticleOptions(v1);
        });
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, SculkChargeParticleOptions> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.FLOAT, $$0 -> {
        return Float.valueOf($$0.roll);
    }, (v1) -> {
        return new SculkChargeParticleOptions(v1);
    });

    public SculkChargeParticleOptions(float $$0) {
        this.roll = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SculkChargeParticleOptions.class), SculkChargeParticleOptions.class, "roll", "FIELD:Lnet/minecraft/core/particles/SculkChargeParticleOptions;->roll:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SculkChargeParticleOptions.class), SculkChargeParticleOptions.class, "roll", "FIELD:Lnet/minecraft/core/particles/SculkChargeParticleOptions;->roll:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SculkChargeParticleOptions.class, Object.class), SculkChargeParticleOptions.class, "roll", "FIELD:Lnet/minecraft/core/particles/SculkChargeParticleOptions;->roll:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public float roll() {
        return this.roll;
    }

    @Override // net.minecraft.core.particles.ParticleOptions
    public ParticleType<SculkChargeParticleOptions> getType() {
        return ParticleTypes.SCULK_CHARGE;
    }
}
