package net.labymod.api.component.data;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/component/data/DataComponentPatch.class */
public final class DataComponentPatch extends Record {
    private final Collection<TypedDataComponent<?>> components;

    public DataComponentPatch(Collection<TypedDataComponent<?>> components) {
        this.components = components;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DataComponentPatch.class), DataComponentPatch.class, "components", "FIELD:Lnet/labymod/api/component/data/DataComponentPatch;->components:Ljava/util/Collection;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DataComponentPatch.class), DataComponentPatch.class, "components", "FIELD:Lnet/labymod/api/component/data/DataComponentPatch;->components:Ljava/util/Collection;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DataComponentPatch.class, Object.class), DataComponentPatch.class, "components", "FIELD:Lnet/labymod/api/component/data/DataComponentPatch;->components:Ljava/util/Collection;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public Collection<TypedDataComponent<?>> components() {
        return this.components;
    }

    public static DataComponentPatch createPatch(DataComponentContainer container) {
        List<TypedDataComponent<?>> components = new ArrayList<>();
        for (TypedDataComponent<?> typedDataComponent : container) {
            components.add(typedDataComponent);
        }
        return new DataComponentPatch(List.copyOf(components));
    }
}
