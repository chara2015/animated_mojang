package net.minecraft.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/Criterion.class */
public final class Criterion<T extends CriterionTriggerInstance> extends Record {
    private final CriterionTrigger<T> trigger;
    private final T triggerInstance;
    private static final MapCodec<Criterion<?>> MAP_CODEC = ExtraCodecs.dispatchOptionalValue("trigger", "conditions", CriteriaTriggers.CODEC, (v0) -> {
        return v0.trigger();
    }, Criterion::criterionCodec);
    public static final Codec<Criterion<?>> CODEC = MAP_CODEC.codec();

    public Criterion(CriterionTrigger<T> $$0, T $$1) {
        this.trigger = $$0;
        this.triggerInstance = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Criterion.class), Criterion.class, "trigger;triggerInstance", "FIELD:Lnet/minecraft/advancements/Criterion;->trigger:Lnet/minecraft/advancements/CriterionTrigger;", "FIELD:Lnet/minecraft/advancements/Criterion;->triggerInstance:Lnet/minecraft/advancements/CriterionTriggerInstance;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Criterion.class), Criterion.class, "trigger;triggerInstance", "FIELD:Lnet/minecraft/advancements/Criterion;->trigger:Lnet/minecraft/advancements/CriterionTrigger;", "FIELD:Lnet/minecraft/advancements/Criterion;->triggerInstance:Lnet/minecraft/advancements/CriterionTriggerInstance;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Criterion.class, Object.class), Criterion.class, "trigger;triggerInstance", "FIELD:Lnet/minecraft/advancements/Criterion;->trigger:Lnet/minecraft/advancements/CriterionTrigger;", "FIELD:Lnet/minecraft/advancements/Criterion;->triggerInstance:Lnet/minecraft/advancements/CriterionTriggerInstance;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public CriterionTrigger<T> trigger() {
        return this.trigger;
    }

    public T triggerInstance() {
        return this.triggerInstance;
    }

    private static <T extends CriterionTriggerInstance> Codec<Criterion<T>> criterionCodec(CriterionTrigger<T> $$0) {
        return $$0.codec().xmap($$1 -> {
            return new Criterion($$0, $$1);
        }, (v0) -> {
            return v0.triggerInstance();
        });
    }
}
