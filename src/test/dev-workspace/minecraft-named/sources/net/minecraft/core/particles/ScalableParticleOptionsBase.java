package net.minecraft.core.particles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/particles/ScalableParticleOptionsBase.class */
public abstract class ScalableParticleOptionsBase implements ParticleOptions {
    public static final float MIN_SCALE = 0.01f;
    public static final float MAX_SCALE = 4.0f;
    protected static final Codec<Float> SCALE = Codec.FLOAT.validate($$0 -> {
        if ($$0.floatValue() >= 0.01f && $$0.floatValue() <= 4.0f) {
            return DataResult.success($$0);
        }
        return DataResult.error(() -> {
            return "Value must be within range [0.01;4.0]: " + $$0;
        });
    });
    private final float scale;

    public ScalableParticleOptionsBase(float $$0) {
        this.scale = Mth.clamp($$0, 0.01f, 4.0f);
    }

    public float getScale() {
        return this.scale;
    }
}
