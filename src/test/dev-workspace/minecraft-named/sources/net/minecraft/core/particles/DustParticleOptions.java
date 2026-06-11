package net.minecraft.core.particles;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/particles/DustParticleOptions.class */
public class DustParticleOptions extends ScalableParticleOptionsBase {
    public static final int REDSTONE_PARTICLE_COLOR = 16711680;
    public static final DustParticleOptions REDSTONE = new DustParticleOptions(REDSTONE_PARTICLE_COLOR, 1.0f);
    public static final MapCodec<DustParticleOptions> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf("color").forGetter($$0 -> {
            return Integer.valueOf($$0.color);
        }), SCALE.fieldOf("scale").forGetter((v0) -> {
            return v0.getScale();
        })).apply($$0, (v1, v2) -> {
            return new DustParticleOptions(v1, v2);
        });
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, DustParticleOptions> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, $$0 -> {
        return Integer.valueOf($$0.color);
    }, ByteBufCodecs.FLOAT, (v0) -> {
        return v0.getScale();
    }, (v1, v2) -> {
        return new DustParticleOptions(v1, v2);
    });
    private final int color;

    public DustParticleOptions(int $$0, float $$1) {
        super($$1);
        this.color = $$0;
    }

    @Override // net.minecraft.core.particles.ParticleOptions
    public ParticleType<DustParticleOptions> getType() {
        return ParticleTypes.DUST;
    }

    public Vector3f getColor() {
        return ARGB.vector3fFromRGB24(this.color);
    }
}
