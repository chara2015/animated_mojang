package net.minecraft.client.color.item;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GrassColor;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/color/item/GrassColorSource.class */
public final class GrassColorSource extends Record implements ItemTintSource {
    private final float temperature;
    private final float downfall;
    public static final MapCodec<GrassColorSource> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ExtraCodecs.floatRange(0.0f, 1.0f).fieldOf("temperature").forGetter((v0) -> {
            return v0.temperature();
        }), ExtraCodecs.floatRange(0.0f, 1.0f).fieldOf("downfall").forGetter((v0) -> {
            return v0.downfall();
        })).apply($$0, (v1, v2) -> {
            return new GrassColorSource(v1, v2);
        });
    });

    public GrassColorSource(float $$0, float $$1) {
        this.temperature = $$0;
        this.downfall = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GrassColorSource.class), GrassColorSource.class, "temperature;downfall", "FIELD:Lnet/minecraft/client/color/item/GrassColorSource;->temperature:F", "FIELD:Lnet/minecraft/client/color/item/GrassColorSource;->downfall:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GrassColorSource.class), GrassColorSource.class, "temperature;downfall", "FIELD:Lnet/minecraft/client/color/item/GrassColorSource;->temperature:F", "FIELD:Lnet/minecraft/client/color/item/GrassColorSource;->downfall:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GrassColorSource.class, Object.class), GrassColorSource.class, "temperature;downfall", "FIELD:Lnet/minecraft/client/color/item/GrassColorSource;->temperature:F", "FIELD:Lnet/minecraft/client/color/item/GrassColorSource;->downfall:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public float temperature() {
        return this.temperature;
    }

    public float downfall() {
        return this.downfall;
    }

    public GrassColorSource() {
        this(0.5f, 1.0f);
    }

    @Override // net.minecraft.client.color.item.ItemTintSource
    public int calculate(ItemStack $$0, ClientLevel $$1, LivingEntity $$2) {
        return GrassColor.get(this.temperature, this.downfall);
    }

    @Override // net.minecraft.client.color.item.ItemTintSource
    public MapCodec<GrassColorSource> type() {
        return MAP_CODEC;
    }
}
