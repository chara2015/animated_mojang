package net.minecraft.core.particles;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.phys.Vec3;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/particles/TrailParticleOption.class */
public final class TrailParticleOption extends Record implements ParticleOptions {
    private final Vec3 target;
    private final int color;
    private final int duration;
    public static final MapCodec<TrailParticleOption> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Vec3.CODEC.fieldOf(JigsawBlockEntity.TARGET).forGetter((v0) -> {
            return v0.target();
        }), ExtraCodecs.RGB_COLOR_CODEC.fieldOf("color").forGetter((v0) -> {
            return v0.color();
        }), ExtraCodecs.POSITIVE_INT.fieldOf("duration").forGetter((v0) -> {
            return v0.duration();
        })).apply($$0, (v1, v2, v3) -> {
            return new TrailParticleOption(v1, v2, v3);
        });
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, TrailParticleOption> STREAM_CODEC = StreamCodec.composite(Vec3.STREAM_CODEC, (v0) -> {
        return v0.target();
    }, ByteBufCodecs.INT, (v0) -> {
        return v0.color();
    }, ByteBufCodecs.VAR_INT, (v0) -> {
        return v0.duration();
    }, (v1, v2, v3) -> {
        return new TrailParticleOption(v1, v2, v3);
    });

    public TrailParticleOption(Vec3 $$0, int $$1, int $$2) {
        this.target = $$0;
        this.color = $$1;
        this.duration = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TrailParticleOption.class), TrailParticleOption.class, "target;color;duration", "FIELD:Lnet/minecraft/core/particles/TrailParticleOption;->target:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/core/particles/TrailParticleOption;->color:I", "FIELD:Lnet/minecraft/core/particles/TrailParticleOption;->duration:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TrailParticleOption.class), TrailParticleOption.class, "target;color;duration", "FIELD:Lnet/minecraft/core/particles/TrailParticleOption;->target:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/core/particles/TrailParticleOption;->color:I", "FIELD:Lnet/minecraft/core/particles/TrailParticleOption;->duration:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TrailParticleOption.class, Object.class), TrailParticleOption.class, "target;color;duration", "FIELD:Lnet/minecraft/core/particles/TrailParticleOption;->target:Lnet/minecraft/world/phys/Vec3;", "FIELD:Lnet/minecraft/core/particles/TrailParticleOption;->color:I", "FIELD:Lnet/minecraft/core/particles/TrailParticleOption;->duration:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Vec3 target() {
        return this.target;
    }

    public int color() {
        return this.color;
    }

    public int duration() {
        return this.duration;
    }

    @Override // net.minecraft.core.particles.ParticleOptions
    public ParticleType<TrailParticleOption> getType() {
        return ParticleTypes.TRAIL;
    }
}
