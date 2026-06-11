package net.minecraft.util;

import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HexFormat;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/ColorRGBA.class */
public final class ColorRGBA extends Record {
    private final int rgba;
    public static final Codec<ColorRGBA> CODEC = ExtraCodecs.STRING_ARGB_COLOR.xmap((v1) -> {
        return new ColorRGBA(v1);
    }, (v0) -> {
        return v0.rgba();
    });

    public ColorRGBA(int $$0) {
        this.rgba = $$0;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ColorRGBA.class), ColorRGBA.class, "rgba", "FIELD:Lnet/minecraft/util/ColorRGBA;->rgba:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ColorRGBA.class, Object.class), ColorRGBA.class, "rgba", "FIELD:Lnet/minecraft/util/ColorRGBA;->rgba:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int rgba() {
        return this.rgba;
    }

    @Override // java.lang.Record
    public String toString() {
        return HexFormat.of().toHexDigits(this.rgba, 8);
    }
}
