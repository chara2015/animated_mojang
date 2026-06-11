package net.minecraft.core.particles;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/particles/ParticleType.class */
public abstract class ParticleType<T extends ParticleOptions> {
    private final boolean overrideLimiter;

    public abstract MapCodec<T> codec();

    public abstract StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec();

    protected ParticleType(boolean $$0) {
        this.overrideLimiter = $$0;
    }

    public boolean getOverrideLimiter() {
        return this.overrideLimiter;
    }
}
