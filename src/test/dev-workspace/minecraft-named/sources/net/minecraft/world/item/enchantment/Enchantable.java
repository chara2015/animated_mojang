package net.minecraft.world.item.enchantment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/item/enchantment/Enchantable.class */
public final class Enchantable extends Record {
    private final int value;
    public static final Codec<Enchantable> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ExtraCodecs.POSITIVE_INT.fieldOf("value").forGetter((v0) -> {
            return v0.value();
        })).apply($$0, (v1) -> {
            return new Enchantable(v1);
        });
    });
    public static final StreamCodec<ByteBuf, Enchantable> STREAM_CODEC = StreamCodec.composite(ByteBufCodecs.VAR_INT, (v0) -> {
        return v0.value();
    }, (v1) -> {
        return new Enchantable(v1);
    });

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Enchantable.class), Enchantable.class, "value", "FIELD:Lnet/minecraft/world/item/enchantment/Enchantable;->value:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Enchantable.class), Enchantable.class, "value", "FIELD:Lnet/minecraft/world/item/enchantment/Enchantable;->value:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Enchantable.class, Object.class), Enchantable.class, "value", "FIELD:Lnet/minecraft/world/item/enchantment/Enchantable;->value:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int value() {
        return this.value;
    }

    public Enchantable(int $$0) {
        if ($$0 > 0) {
            this.value = $$0;
            return;
        }
        throw new IllegalArgumentException("Enchantment value must be positive, but was " + $$0);
    }
}
