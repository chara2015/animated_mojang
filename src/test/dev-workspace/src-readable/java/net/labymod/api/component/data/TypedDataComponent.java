package net.labymod.api.component.data;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/component/data/TypedDataComponent.class */
public final class TypedDataComponent<T> extends Record {
    private final DataComponentKey key;
    private final T value;

    public TypedDataComponent(DataComponentKey key, T value) {
        this.key = key;
        this.value = value;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TypedDataComponent.class), TypedDataComponent.class, "key;value", "FIELD:Lnet/labymod/api/component/data/TypedDataComponent;->key:Lnet/labymod/api/component/data/DataComponentKey;", "FIELD:Lnet/labymod/api/component/data/TypedDataComponent;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TypedDataComponent.class), TypedDataComponent.class, "key;value", "FIELD:Lnet/labymod/api/component/data/TypedDataComponent;->key:Lnet/labymod/api/component/data/DataComponentKey;", "FIELD:Lnet/labymod/api/component/data/TypedDataComponent;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TypedDataComponent.class, Object.class), TypedDataComponent.class, "key;value", "FIELD:Lnet/labymod/api/component/data/TypedDataComponent;->key:Lnet/labymod/api/component/data/DataComponentKey;", "FIELD:Lnet/labymod/api/component/data/TypedDataComponent;->value:Ljava/lang/Object;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public DataComponentKey key() {
        return this.key;
    }

    public T value() {
        return this.value;
    }
}
