package net.minecraft.advancements.criterion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.server.PlayerAdvancements;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/criterion/ImpossibleTrigger.class */
public class ImpossibleTrigger implements CriterionTrigger<TriggerInstance> {
    @Override // net.minecraft.advancements.CriterionTrigger
    public void addPlayerListener(PlayerAdvancements $$0, CriterionTrigger.Listener<TriggerInstance> $$1) {
    }

    @Override // net.minecraft.advancements.CriterionTrigger
    public void removePlayerListener(PlayerAdvancements $$0, CriterionTrigger.Listener<TriggerInstance> $$1) {
    }

    @Override // net.minecraft.advancements.CriterionTrigger
    public void removePlayerListeners(PlayerAdvancements $$0) {
    }

    @Override // net.minecraft.advancements.CriterionTrigger
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/advancements/criterion/ImpossibleTrigger$TriggerInstance.class */
    public static final class TriggerInstance extends Record implements CriterionTriggerInstance {
        public static final Codec<TriggerInstance> CODEC = MapCodec.unitCodec(new TriggerInstance());

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TriggerInstance.class), TriggerInstance.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TriggerInstance.class), TriggerInstance.class, "").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TriggerInstance.class, Object.class), TriggerInstance.class, "").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        @Override // net.minecraft.advancements.CriterionTriggerInstance
        public void validate(CriterionValidator $$0) {
        }
    }
}
