package net.minecraft.client.renderer.item.properties.numeric;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/properties/numeric/Damage.class */
public final class Damage extends Record implements RangeSelectItemModelProperty {
    private final boolean normalize;
    public static final MapCodec<Damage> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.BOOL.optionalFieldOf("normalize", true).forGetter((v0) -> {
            return v0.normalize();
        })).apply($$0, (v1) -> {
            return new Damage(v1);
        });
    });

    public Damage(boolean $$0) {
        this.normalize = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Damage.class), Damage.class, "normalize", "FIELD:Lnet/minecraft/client/renderer/item/properties/numeric/Damage;->normalize:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Damage.class), Damage.class, "normalize", "FIELD:Lnet/minecraft/client/renderer/item/properties/numeric/Damage;->normalize:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Damage.class, Object.class), Damage.class, "normalize", "FIELD:Lnet/minecraft/client/renderer/item/properties/numeric/Damage;->normalize:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public boolean normalize() {
        return this.normalize;
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public float get(ItemStack $$0, ClientLevel $$1, ItemOwner $$2, int $$3) {
        float $$4 = $$0.getDamageValue();
        float $$5 = $$0.getMaxDamage();
        if (this.normalize) {
            return Mth.clamp($$4 / $$5, 0.0f, 1.0f);
        }
        return Mth.clamp($$4, 0.0f, $$5);
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public MapCodec<Damage> type() {
        return MAP_CODEC;
    }
}
