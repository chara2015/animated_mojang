package net.minecraft.client.color.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.gametest.framework.GameTestEnvironments;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/color/item/Dye.class */
public final class Dye extends Record implements ItemTintSource {
    private final int defaultColor;
    public static final MapCodec<Dye> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf(GameTestEnvironments.DEFAULT).forGetter((v0) -> {
            return v0.defaultColor();
        })).apply($$0, (v1) -> {
            return new Dye(v1);
        });
    });

    public Dye(int $$0) {
        this.defaultColor = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Dye.class), Dye.class, "defaultColor", "FIELD:Lnet/minecraft/client/color/item/Dye;->defaultColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Dye.class), Dye.class, "defaultColor", "FIELD:Lnet/minecraft/client/color/item/Dye;->defaultColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Dye.class, Object.class), Dye.class, "defaultColor", "FIELD:Lnet/minecraft/client/color/item/Dye;->defaultColor:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int defaultColor() {
        return this.defaultColor;
    }

    @Override // net.minecraft.client.color.item.ItemTintSource
    public int calculate(ItemStack $$0, ClientLevel $$1, LivingEntity $$2) {
        return DyedItemColor.getOrDefault($$0, this.defaultColor);
    }

    @Override // net.minecraft.client.color.item.ItemTintSource
    public MapCodec<Dye> type() {
        return MAP_CODEC;
    }
}
