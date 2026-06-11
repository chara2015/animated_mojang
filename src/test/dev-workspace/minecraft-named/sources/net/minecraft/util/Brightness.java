package net.minecraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/Brightness.class */
public final class Brightness extends Record {
    private final int block;
    private final int sky;
    public static final Codec<Integer> LIGHT_VALUE_CODEC = ExtraCodecs.intRange(0, 15);
    public static final Codec<Brightness> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(LIGHT_VALUE_CODEC.fieldOf("block").forGetter((v0) -> {
            return v0.block();
        }), LIGHT_VALUE_CODEC.fieldOf("sky").forGetter((v0) -> {
            return v0.sky();
        })).apply($$0, (v1, v2) -> {
            return new Brightness(v1, v2);
        });
    });
    public static final Brightness FULL_BRIGHT = new Brightness(15, 15);

    public Brightness(int $$0, int $$1) {
        this.block = $$0;
        this.sky = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Brightness.class), Brightness.class, "block;sky", "FIELD:Lnet/minecraft/util/Brightness;->block:I", "FIELD:Lnet/minecraft/util/Brightness;->sky:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Brightness.class), Brightness.class, "block;sky", "FIELD:Lnet/minecraft/util/Brightness;->block:I", "FIELD:Lnet/minecraft/util/Brightness;->sky:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Brightness.class, Object.class), Brightness.class, "block;sky", "FIELD:Lnet/minecraft/util/Brightness;->block:I", "FIELD:Lnet/minecraft/util/Brightness;->sky:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int block() {
        return this.block;
    }

    public int sky() {
        return this.sky;
    }

    public static int pack(int $$0, int $$1) {
        return ($$0 << 4) | ($$1 << 20);
    }

    public int pack() {
        return pack(this.block, this.sky);
    }

    public static int block(int $$0) {
        return ($$0 >> 4) & 65535;
    }

    public static int sky(int $$0) {
        return ($$0 >> 20) & 65535;
    }

    public static Brightness unpack(int $$0) {
        return new Brightness(block($$0), sky($$0));
    }
}
