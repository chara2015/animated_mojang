package net.minecraft.server.level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import java.util.Objects;
import java.util.function.IntFunction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ByIdMap;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/level/ParticleStatus.class */
public enum ParticleStatus {
    ALL(0, "options.particles.all"),
    DECREASED(1, "options.particles.decreased"),
    MINIMAL(2, "options.particles.minimal");

    private static final IntFunction<ParticleStatus> BY_ID = ByIdMap.continuous($$0 -> {
        return $$0.id;
    }, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final Codec<ParticleStatus> LEGACY_CODEC;
    private final int id;
    private final Component caption;

    static {
        PrimitiveCodec primitiveCodec = Codec.INT;
        IntFunction<ParticleStatus> intFunction = BY_ID;
        Objects.requireNonNull(intFunction);
        LEGACY_CODEC = primitiveCodec.xmap((v1) -> {
            return r1.apply(v1);
        }, $$0 -> {
            return Integer.valueOf($$0.id);
        });
    }

    ParticleStatus(int $$0, String $$1) {
        this.id = $$0;
        this.caption = Component.translatable($$1);
    }

    public Component caption() {
        return this.caption;
    }
}
