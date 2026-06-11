package net.minecraft.core.particles;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/particles/DustColorTransitionOptions.class */
public class DustColorTransitionOptions extends ScalableParticleOptionsBase {
    public static final int SCULK_PARTICLE_COLOR = 3790560;
    public static final DustColorTransitionOptions SCULK_TO_REDSTONE = new DustColorTransitionOptions(SCULK_PARTICLE_COLOR, DustParticleOptions.REDSTONE_PARTICLE_COLOR, 1.0f);
    public static final MapCodec<DustColorTransitionOptions> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf("from_color").forGetter($$0 -> {
            return Integer.valueOf($$0.fromColor);
        }), ExtraCodecs.RGB_COLOR_CODEC.fieldOf("to_color").forGetter($$02 -> {
            return Integer.valueOf($$02.toColor);
        }), SCALE.fieldOf("scale").forGetter((v0) -> {
            return v0.getScale();
        })).apply($$0, (v1, v2, v3) -> {
            return new DustColorTransitionOptions(v1, v2, v3);
        });
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, DustColorTransitionOptions> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.INT, $$0 -> {
        return Integer.valueOf($$0.fromColor);
    }, ByteBufCodecs.INT, $$02 -> {
        return Integer.valueOf($$02.toColor);
    }, ByteBufCodecs.FLOAT, (v0) -> {
        return v0.getScale();
    }, (v1, v2, v3) -> {
        return new DustColorTransitionOptions(v1, v2, v3);
    });
    private final int fromColor;
    private final int toColor;

    public DustColorTransitionOptions(int $$0, int $$1, float $$2) {
        super($$2);
        this.fromColor = $$0;
        this.toColor = $$1;
    }

    public Vector3f getFromColor() {
        return ARGB.vector3fFromRGB24(this.fromColor);
    }

    public Vector3f getToColor() {
        return ARGB.vector3fFromRGB24(this.toColor);
    }

    @Override // net.minecraft.core.particles.ParticleOptions
    public ParticleType<DustColorTransitionOptions> getType() {
        return ParticleTypes.DUST_COLOR_TRANSITION;
    }
}
