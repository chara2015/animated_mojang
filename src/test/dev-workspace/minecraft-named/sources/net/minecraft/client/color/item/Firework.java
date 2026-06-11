package net.minecraft.client.color.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.ints.IntList;
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
import net.minecraft.world.item.component.FireworkExplosion;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/color/item/Firework.class */
public final class Firework extends Record implements ItemTintSource {
    private final int defaultColor;
    public static final MapCodec<Firework> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf(GameTestEnvironments.DEFAULT).forGetter((v0) -> {
            return v0.defaultColor();
        })).apply($$0, (v1) -> {
            return new Firework(v1);
        });
    });

    public Firework(int $$0) {
        this.defaultColor = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Firework.class), Firework.class, "defaultColor", "FIELD:Lnet/minecraft/client/color/item/Firework;->defaultColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Firework.class), Firework.class, "defaultColor", "FIELD:Lnet/minecraft/client/color/item/Firework;->defaultColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Firework.class, Object.class), Firework.class, "defaultColor", "FIELD:Lnet/minecraft/client/color/item/Firework;->defaultColor:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int defaultColor() {
        return this.defaultColor;
    }

    public Firework() {
        this(-7697782);
    }

    @Override // net.minecraft.client.color.item.ItemTintSource
    public int calculate(ItemStack $$0, ClientLevel $$1, LivingEntity $$2) {
        FireworkExplosion $$3 = (FireworkExplosion) $$0.get(DataComponents.FIREWORK_EXPLOSION);
        IntList $$4 = $$3 != null ? $$3.colors() : IntList.of();
        int $$5 = $$4.size();
        if ($$5 == 0) {
            return this.defaultColor;
        }
        if ($$5 == 1) {
            return ARGB.opaque($$4.getInt(0));
        }
        int $$6 = 0;
        int $$7 = 0;
        int $$8 = 0;
        for (int $$9 = 0; $$9 < $$5; $$9++) {
            int $$10 = $$4.getInt($$9);
            $$6 += ARGB.red($$10);
            $$7 += ARGB.green($$10);
            $$8 += ARGB.blue($$10);
        }
        return ARGB.color($$6 / $$5, $$7 / $$5, $$8 / $$5);
    }

    @Override // net.minecraft.client.color.item.ItemTintSource
    public MapCodec<Firework> type() {
        return MAP_CODEC;
    }
}
