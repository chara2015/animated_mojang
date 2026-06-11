package net.minecraft.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import java.util.Objects;
import java.util.function.IntFunction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ByIdMap;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/AttackIndicatorStatus.class */
public enum AttackIndicatorStatus {
    OFF(0, "options.off"),
    CROSSHAIR(1, "options.attack.crosshair"),
    HOTBAR(2, "options.attack.hotbar");

    private static final IntFunction<AttackIndicatorStatus> BY_ID = ByIdMap.continuous($$0 -> {
        return $$0.id;
    }, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final Codec<AttackIndicatorStatus> LEGACY_CODEC;
    private final int id;
    private final Component caption;

    static {
        PrimitiveCodec primitiveCodec = Codec.INT;
        IntFunction<AttackIndicatorStatus> intFunction = BY_ID;
        Objects.requireNonNull(intFunction);
        LEGACY_CODEC = primitiveCodec.xmap((v1) -> {
            return r1.apply(v1);
        }, $$0 -> {
            return Integer.valueOf($$0.id);
        });
    }

    AttackIndicatorStatus(int $$0, String $$1) {
        this.id = $$0;
        this.caption = Component.translatable($$1);
    }

    public Component caption() {
        return this.caption;
    }
}
