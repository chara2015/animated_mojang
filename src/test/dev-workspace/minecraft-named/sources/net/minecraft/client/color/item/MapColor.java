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
import net.minecraft.world.item.component.MapItemColor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/color/item/MapColor.class */
public final class MapColor extends Record implements ItemTintSource {
    private final int defaultColor;
    public static final MapCodec<MapColor> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf(GameTestEnvironments.DEFAULT).forGetter((v0) -> {
            return v0.defaultColor();
        })).apply($$0, (v1) -> {
            return new MapColor(v1);
        });
    });

    public MapColor(int $$0) {
        this.defaultColor = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MapColor.class), MapColor.class, "defaultColor", "FIELD:Lnet/minecraft/client/color/item/MapColor;->defaultColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MapColor.class), MapColor.class, "defaultColor", "FIELD:Lnet/minecraft/client/color/item/MapColor;->defaultColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MapColor.class, Object.class), MapColor.class, "defaultColor", "FIELD:Lnet/minecraft/client/color/item/MapColor;->defaultColor:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int defaultColor() {
        return this.defaultColor;
    }

    public MapColor() {
        this(MapItemColor.DEFAULT.rgb());
    }

    @Override // net.minecraft.client.color.item.ItemTintSource
    public int calculate(ItemStack $$0, ClientLevel $$1, LivingEntity $$2) {
        MapItemColor $$3 = (MapItemColor) $$0.get(DataComponents.MAP_COLOR);
        if ($$3 != null) {
            return ARGB.opaque($$3.rgb());
        }
        return ARGB.opaque(this.defaultColor);
    }

    @Override // net.minecraft.client.color.item.ItemTintSource
    public MapCodec<MapColor> type() {
        return MAP_CODEC;
    }
}
