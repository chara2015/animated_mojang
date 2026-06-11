package net.minecraft.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.PrimitiveCodec;
import java.util.Objects;
import java.util.function.IntFunction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ByIdMap;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/TextureFilteringMethod.class */
public enum TextureFilteringMethod {
    NONE(0, "options.textureFiltering.none"),
    RGSS(1, "options.textureFiltering.rgss"),
    ANISOTROPIC(2, "options.textureFiltering.anisotropic");

    private static final IntFunction<TextureFilteringMethod> BY_ID = ByIdMap.continuous($$0 -> {
        return $$0.id;
    }, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    public static final Codec<TextureFilteringMethod> LEGACY_CODEC;
    private final int id;
    private final Component caption;

    static {
        PrimitiveCodec primitiveCodec = Codec.INT;
        IntFunction<TextureFilteringMethod> intFunction = BY_ID;
        Objects.requireNonNull(intFunction);
        LEGACY_CODEC = primitiveCodec.xmap((v1) -> {
            return r1.apply(v1);
        }, $$0 -> {
            return Integer.valueOf($$0.id);
        });
    }

    TextureFilteringMethod(int $$0, String $$1) {
        this.id = $$0;
        this.caption = Component.translatable($$1);
    }

    public Component caption() {
        return this.caption;
    }
}
