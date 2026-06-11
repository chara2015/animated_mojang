package net.minecraft.core.component.predicates;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/component/predicates/AnyValue.class */
public final class AnyValue extends Record implements DataComponentPredicate {
    private final DataComponentType<?> type;

    public AnyValue(DataComponentType<?> $$0) {
        this.type = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AnyValue.class), AnyValue.class, "type", "FIELD:Lnet/minecraft/core/component/predicates/AnyValue;->type:Lnet/minecraft/core/component/DataComponentType;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AnyValue.class), AnyValue.class, "type", "FIELD:Lnet/minecraft/core/component/predicates/AnyValue;->type:Lnet/minecraft/core/component/DataComponentType;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AnyValue.class, Object.class), AnyValue.class, "type", "FIELD:Lnet/minecraft/core/component/predicates/AnyValue;->type:Lnet/minecraft/core/component/DataComponentType;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public DataComponentType<?> type() {
        return this.type;
    }

    @Override // net.minecraft.core.component.predicates.DataComponentPredicate
    public boolean matches(DataComponentGetter $$0) {
        return $$0.get(this.type) != null;
    }
}
