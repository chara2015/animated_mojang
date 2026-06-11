package net.minecraft.client.renderer.item.properties.numeric;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/properties/numeric/UseCycle.class */
public final class UseCycle extends Record implements RangeSelectItemModelProperty {
    private final float period;
    public static final MapCodec<UseCycle> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ExtraCodecs.POSITIVE_FLOAT.optionalFieldOf("period", Float.valueOf(1.0f)).forGetter((v0) -> {
            return v0.period();
        })).apply($$0, (v1) -> {
            return new UseCycle(v1);
        });
    });

    public UseCycle(float $$0) {
        this.period = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, UseCycle.class), UseCycle.class, "period", "FIELD:Lnet/minecraft/client/renderer/item/properties/numeric/UseCycle;->period:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, UseCycle.class), UseCycle.class, "period", "FIELD:Lnet/minecraft/client/renderer/item/properties/numeric/UseCycle;->period:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, UseCycle.class, Object.class), UseCycle.class, "period", "FIELD:Lnet/minecraft/client/renderer/item/properties/numeric/UseCycle;->period:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public float period() {
        return this.period;
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public float get(ItemStack $$0, ClientLevel $$1, ItemOwner $$2, int $$3) {
        LivingEntity $$4 = $$2 == null ? null : $$2.asLivingEntity();
        if ($$4 == null || $$4.getUseItem() != $$0) {
            return 0.0f;
        }
        return $$4.getUseItemRemainingTicks() % this.period;
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public MapCodec<UseCycle> type() {
        return MAP_CODEC;
    }
}
