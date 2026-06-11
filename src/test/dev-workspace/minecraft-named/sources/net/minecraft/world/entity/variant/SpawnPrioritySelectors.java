package net.minecraft.world.entity.variant;

import com.mojang.serialization.Codec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.world.entity.variant.PriorityProvider;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/entity/variant/SpawnPrioritySelectors.class */
public final class SpawnPrioritySelectors extends Record {
    private final List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> selectors;
    public static final SpawnPrioritySelectors EMPTY = new SpawnPrioritySelectors(List.of());
    public static final Codec<SpawnPrioritySelectors> CODEC = PriorityProvider.Selector.codec(SpawnCondition.CODEC).listOf().xmap(SpawnPrioritySelectors::new, (v0) -> {
        return v0.selectors();
    });

    public SpawnPrioritySelectors(List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> $$0) {
        this.selectors = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SpawnPrioritySelectors.class), SpawnPrioritySelectors.class, "selectors", "FIELD:Lnet/minecraft/world/entity/variant/SpawnPrioritySelectors;->selectors:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SpawnPrioritySelectors.class), SpawnPrioritySelectors.class, "selectors", "FIELD:Lnet/minecraft/world/entity/variant/SpawnPrioritySelectors;->selectors:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SpawnPrioritySelectors.class, Object.class), SpawnPrioritySelectors.class, "selectors", "FIELD:Lnet/minecraft/world/entity/variant/SpawnPrioritySelectors;->selectors:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> selectors() {
        return this.selectors;
    }

    public static SpawnPrioritySelectors single(SpawnCondition $$0, int $$1) {
        return new SpawnPrioritySelectors(PriorityProvider.single($$0, $$1));
    }

    public static SpawnPrioritySelectors fallback(int $$0) {
        return new SpawnPrioritySelectors(PriorityProvider.alwaysTrue($$0));
    }
}
