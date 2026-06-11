package net.minecraft.client.renderer.item.properties.numeric;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/properties/numeric/UseDuration.class */
public final class UseDuration extends Record implements RangeSelectItemModelProperty {
    private final boolean remaining;
    public static final MapCodec<UseDuration> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.BOOL.optionalFieldOf("remaining", false).forGetter((v0) -> {
            return v0.remaining();
        })).apply($$0, (v1) -> {
            return new UseDuration(v1);
        });
    });

    public UseDuration(boolean $$0) {
        this.remaining = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, UseDuration.class), UseDuration.class, "remaining", "FIELD:Lnet/minecraft/client/renderer/item/properties/numeric/UseDuration;->remaining:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, UseDuration.class), UseDuration.class, "remaining", "FIELD:Lnet/minecraft/client/renderer/item/properties/numeric/UseDuration;->remaining:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, UseDuration.class, Object.class), UseDuration.class, "remaining", "FIELD:Lnet/minecraft/client/renderer/item/properties/numeric/UseDuration;->remaining:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public boolean remaining() {
        return this.remaining;
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public float get(ItemStack $$0, ClientLevel $$1, ItemOwner $$2, int $$3) {
        LivingEntity $$4 = $$2 == null ? null : $$2.asLivingEntity();
        if ($$4 == null || $$4.getUseItem() != $$0) {
            return 0.0f;
        }
        return this.remaining ? $$4.getUseItemRemainingTicks() : useDuration($$0, $$4);
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public MapCodec<UseDuration> type() {
        return MAP_CODEC;
    }

    public static int useDuration(ItemStack $$0, LivingEntity $$1) {
        return $$0.getUseDuration($$1) - $$1.getUseItemRemainingTicks();
    }
}
