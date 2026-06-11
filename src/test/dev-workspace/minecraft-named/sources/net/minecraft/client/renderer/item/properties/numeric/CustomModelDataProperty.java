package net.minecraft.client.renderer.item.properties.numeric;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomModelData;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/properties/numeric/CustomModelDataProperty.class */
public final class CustomModelDataProperty extends Record implements RangeSelectItemModelProperty {
    private final int index;
    public static final MapCodec<CustomModelDataProperty> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("index", 0).forGetter((v0) -> {
            return v0.index();
        })).apply($$0, (v1) -> {
            return new CustomModelDataProperty(v1);
        });
    });

    public CustomModelDataProperty(int $$0) {
        this.index = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CustomModelDataProperty.class), CustomModelDataProperty.class, "index", "FIELD:Lnet/minecraft/client/renderer/item/properties/numeric/CustomModelDataProperty;->index:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CustomModelDataProperty.class), CustomModelDataProperty.class, "index", "FIELD:Lnet/minecraft/client/renderer/item/properties/numeric/CustomModelDataProperty;->index:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CustomModelDataProperty.class, Object.class), CustomModelDataProperty.class, "index", "FIELD:Lnet/minecraft/client/renderer/item/properties/numeric/CustomModelDataProperty;->index:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int index() {
        return this.index;
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public float get(ItemStack $$0, ClientLevel $$1, ItemOwner $$2, int $$3) {
        Float $$5;
        CustomModelData $$4 = (CustomModelData) $$0.get(DataComponents.CUSTOM_MODEL_DATA);
        if ($$4 != null && ($$5 = $$4.getFloat(this.index)) != null) {
            return $$5.floatValue();
        }
        return 0.0f;
    }

    @Override // net.minecraft.client.renderer.item.properties.numeric.RangeSelectItemModelProperty
    public MapCodec<CustomModelDataProperty> type() {
        return MAP_CODEC;
    }
}
