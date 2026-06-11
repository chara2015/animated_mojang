package net.minecraft.client.color.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.GameTestEnvironments;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/color/item/Potion.class */
public final class Potion extends Record implements ItemTintSource {
    private final int defaultColor;
    public static final MapCodec<Potion> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf(GameTestEnvironments.DEFAULT).forGetter((v0) -> {
            return v0.defaultColor();
        })).apply($$0, (v1) -> {
            return new Potion(v1);
        });
    });

    public Potion(int $$0) {
        this.defaultColor = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Potion.class), Potion.class, "defaultColor", "FIELD:Lnet/minecraft/client/color/item/Potion;->defaultColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Potion.class), Potion.class, "defaultColor", "FIELD:Lnet/minecraft/client/color/item/Potion;->defaultColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Potion.class, Object.class), Potion.class, "defaultColor", "FIELD:Lnet/minecraft/client/color/item/Potion;->defaultColor:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int defaultColor() {
        return this.defaultColor;
    }

    public Potion() {
        this(PotionContents.BASE_POTION_COLOR);
    }

    @Override // net.minecraft.client.color.item.ItemTintSource
    public int calculate(ItemStack $$0, ClientLevel $$1, LivingEntity $$2) {
        PotionContents $$3 = (PotionContents) $$0.get(DataComponents.POTION_CONTENTS);
        if ($$3 != null) {
            return ARGB.opaque($$3.getColorOr(this.defaultColor));
        }
        return ARGB.opaque(this.defaultColor);
    }

    @Override // net.minecraft.client.color.item.ItemTintSource
    public MapCodec<Potion> type() {
        return MAP_CODEC;
    }
}
