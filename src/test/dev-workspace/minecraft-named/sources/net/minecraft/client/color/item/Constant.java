package net.minecraft.client.color.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/color/item/Constant.class */
public final class Constant extends Record implements ItemTintSource {
    private final int value;
    public static final MapCodec<Constant> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf("value").forGetter((v0) -> {
            return v0.value();
        })).apply($$0, (v1) -> {
            return new Constant(v1);
        });
    });

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Constant.class), Constant.class, "value", "FIELD:Lnet/minecraft/client/color/item/Constant;->value:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Constant.class), Constant.class, "value", "FIELD:Lnet/minecraft/client/color/item/Constant;->value:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Constant.class, Object.class), Constant.class, "value", "FIELD:Lnet/minecraft/client/color/item/Constant;->value:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int value() {
        return this.value;
    }

    public Constant(int $$0) {
        this.value = ARGB.opaque($$0);
    }

    @Override // net.minecraft.client.color.item.ItemTintSource
    public int calculate(ItemStack $$0, ClientLevel $$1, LivingEntity $$2) {
        return this.value;
    }

    @Override // net.minecraft.client.color.item.ItemTintSource
    public MapCodec<Constant> type() {
        return MAP_CODEC;
    }
}
