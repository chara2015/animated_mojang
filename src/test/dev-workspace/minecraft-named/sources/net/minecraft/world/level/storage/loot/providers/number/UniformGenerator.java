package net.minecraft.world.level.storage.loot.providers.number;

import com.google.common.collect.Sets;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Set;
import net.minecraft.util.Mth;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.LootContext;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/providers/number/UniformGenerator.class */
public final class UniformGenerator extends Record implements NumberProvider {
    private final NumberProvider min;
    private final NumberProvider max;
    public static final MapCodec<UniformGenerator> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(NumberProviders.CODEC.fieldOf("min").forGetter((v0) -> {
            return v0.min();
        }), NumberProviders.CODEC.fieldOf("max").forGetter((v0) -> {
            return v0.max();
        })).apply($$0, UniformGenerator::new);
    });

    public UniformGenerator(NumberProvider $$0, NumberProvider $$1) {
        this.min = $$0;
        this.max = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, UniformGenerator.class), UniformGenerator.class, "min;max", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/UniformGenerator;->min:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/UniformGenerator;->max:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, UniformGenerator.class), UniformGenerator.class, "min;max", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/UniformGenerator;->min:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/UniformGenerator;->max:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, UniformGenerator.class, Object.class), UniformGenerator.class, "min;max", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/UniformGenerator;->min:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/UniformGenerator;->max:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public NumberProvider min() {
        return this.min;
    }

    public NumberProvider max() {
        return this.max;
    }

    @Override // net.minecraft.world.level.storage.loot.providers.number.NumberProvider
    public LootNumberProviderType getType() {
        return NumberProviders.UNIFORM;
    }

    public static UniformGenerator between(float $$0, float $$1) {
        return new UniformGenerator(ConstantValue.exactly($$0), ConstantValue.exactly($$1));
    }

    @Override // net.minecraft.world.level.storage.loot.providers.number.NumberProvider
    public int getInt(LootContext $$0) {
        return Mth.nextInt($$0.getRandom(), this.min.getInt($$0), this.max.getInt($$0));
    }

    @Override // net.minecraft.world.level.storage.loot.providers.number.NumberProvider
    public float getFloat(LootContext $$0) {
        return Mth.nextFloat($$0.getRandom(), this.min.getFloat($$0), this.max.getFloat($$0));
    }

    @Override // net.minecraft.world.level.storage.loot.LootContextUser
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Sets.union(this.min.getReferencedContextParams(), this.max.getReferencedContextParams());
    }
}
