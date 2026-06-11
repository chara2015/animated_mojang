package net.minecraft.world.level.storage.loot.providers.number;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.world.level.storage.loot.LootContext;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/providers/number/ConstantValue.class */
public final class ConstantValue extends Record implements NumberProvider {
    private final float value;
    public static final MapCodec<ConstantValue> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Codec.FLOAT.fieldOf("value").forGetter((v0) -> {
            return v0.value();
        })).apply($$0, (v1) -> {
            return new ConstantValue(v1);
        });
    });
    public static final Codec<ConstantValue> INLINE_CODEC = Codec.FLOAT.xmap((v1) -> {
        return new ConstantValue(v1);
    }, (v0) -> {
        return v0.value();
    });

    public ConstantValue(float $$0) {
        this.value = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ConstantValue.class), ConstantValue.class, "value", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/ConstantValue;->value:F").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    public float value() {
        return this.value;
    }

    @Override // net.minecraft.world.level.storage.loot.providers.number.NumberProvider
    public LootNumberProviderType getType() {
        return NumberProviders.CONSTANT;
    }

    @Override // net.minecraft.world.level.storage.loot.providers.number.NumberProvider
    public float getFloat(LootContext $$0) {
        return this.value;
    }

    public static ConstantValue exactly(float $$0) {
        return new ConstantValue($$0);
    }

    @Override // java.lang.Record
    public boolean equals(Object $$0) {
        if (this == $$0) {
            return true;
        }
        return $$0 != null && getClass() == $$0.getClass() && Float.compare(((ConstantValue) $$0).value, this.value) == 0;
    }

    @Override // java.lang.Record
    public int hashCode() {
        if (this.value != 0.0f) {
            return Float.floatToIntBits(this.value);
        }
        return 0;
    }
}
