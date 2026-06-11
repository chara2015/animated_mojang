package net.minecraft.core.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.gameevent.EntityPositionSource;
import net.minecraft.world.level.gameevent.PositionSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/particles/VibrationParticleOption.class */
public class VibrationParticleOption implements ParticleOptions {
    private static final Codec<PositionSource> SAFE_POSITION_SOURCE_CODEC = PositionSource.CODEC.validate($$0 -> {
        return $$0 instanceof EntityPositionSource ? DataResult.error(() -> {
            return "Entity position sources are not allowed";
        }) : DataResult.success($$0);
    });
    public static final MapCodec<VibrationParticleOption> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(SAFE_POSITION_SOURCE_CODEC.fieldOf("destination").forGetter((v0) -> {
            return v0.getDestination();
        }), Codec.INT.fieldOf("arrival_in_ticks").forGetter((v0) -> {
            return v0.getArrivalInTicks();
        })).apply($$0, (v1, v2) -> {
            return new VibrationParticleOption(v1, v2);
        });
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, VibrationParticleOption> STREAM_CODEC = StreamCodec.composite(PositionSource.STREAM_CODEC, (v0) -> {
        return v0.getDestination();
    }, ByteBufCodecs.VAR_INT, (v0) -> {
        return v0.getArrivalInTicks();
    }, (v1, v2) -> {
        return new VibrationParticleOption(v1, v2);
    });
    private final PositionSource destination;
    private final int arrivalInTicks;

    public VibrationParticleOption(PositionSource $$0, int $$1) {
        this.destination = $$0;
        this.arrivalInTicks = $$1;
    }

    @Override // net.minecraft.core.particles.ParticleOptions
    public ParticleType<VibrationParticleOption> getType() {
        return ParticleTypes.VIBRATION;
    }

    public PositionSource getDestination() {
        return this.destination;
    }

    public int getArrivalInTicks() {
        return this.arrivalInTicks;
    }
}
