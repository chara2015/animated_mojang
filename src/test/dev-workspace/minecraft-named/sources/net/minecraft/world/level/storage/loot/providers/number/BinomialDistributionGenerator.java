package net.minecraft.world.level.storage.loot.providers.number;

import com.google.common.collect.Sets;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Set;
import net.minecraft.util.RandomSource;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.LootContext;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/storage/loot/providers/number/BinomialDistributionGenerator.class */
public final class BinomialDistributionGenerator extends Record implements NumberProvider {
    private final NumberProvider n;
    private final NumberProvider p;
    public static final MapCodec<BinomialDistributionGenerator> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(NumberProviders.CODEC.fieldOf("n").forGetter((v0) -> {
            return v0.n();
        }), NumberProviders.CODEC.fieldOf("p").forGetter((v0) -> {
            return v0.p();
        })).apply($$0, BinomialDistributionGenerator::new);
    });

    public BinomialDistributionGenerator(NumberProvider $$0, NumberProvider $$1) {
        this.n = $$0;
        this.p = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BinomialDistributionGenerator.class), BinomialDistributionGenerator.class, "n;p", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/BinomialDistributionGenerator;->n:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/BinomialDistributionGenerator;->p:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BinomialDistributionGenerator.class), BinomialDistributionGenerator.class, "n;p", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/BinomialDistributionGenerator;->n:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/BinomialDistributionGenerator;->p:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BinomialDistributionGenerator.class, Object.class), BinomialDistributionGenerator.class, "n;p", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/BinomialDistributionGenerator;->n:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;", "FIELD:Lnet/minecraft/world/level/storage/loot/providers/number/BinomialDistributionGenerator;->p:Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public NumberProvider n() {
        return this.n;
    }

    public NumberProvider p() {
        return this.p;
    }

    @Override // net.minecraft.world.level.storage.loot.providers.number.NumberProvider
    public LootNumberProviderType getType() {
        return NumberProviders.BINOMIAL;
    }

    @Override // net.minecraft.world.level.storage.loot.providers.number.NumberProvider
    public int getInt(LootContext $$0) {
        int $$1 = this.n.getInt($$0);
        float $$2 = this.p.getFloat($$0);
        RandomSource $$3 = $$0.getRandom();
        int $$4 = 0;
        for (int $$5 = 0; $$5 < $$1; $$5++) {
            if ($$3.nextFloat() < $$2) {
                $$4++;
            }
        }
        return $$4;
    }

    @Override // net.minecraft.world.level.storage.loot.providers.number.NumberProvider
    public float getFloat(LootContext $$0) {
        return getInt($$0);
    }

    public static BinomialDistributionGenerator binomial(int $$0, float $$1) {
        return new BinomialDistributionGenerator(ConstantValue.exactly($$0), ConstantValue.exactly($$1));
    }

    @Override // net.minecraft.world.level.storage.loot.LootContextUser
    public Set<ContextKey<?>> getReferencedContextParams() {
        return Sets.union(this.n.getReferencedContextParams(), this.p.getReferencedContextParams());
    }
}
