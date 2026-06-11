package net.labymod.api.component.data;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/component/data/DataComponentKey.class */
public final class DataComponentKey extends Record {
    private final String name;
    private static final Function<String, DataComponentKey> KEYS = Laby.references().functionMemoizeStorage().memoize(DataComponentKey::new);

    public DataComponentKey(String name) {
        this.name = name;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DataComponentKey.class), DataComponentKey.class, "name", "FIELD:Lnet/labymod/api/component/data/DataComponentKey;->name:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DataComponentKey.class), DataComponentKey.class, "name", "FIELD:Lnet/labymod/api/component/data/DataComponentKey;->name:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DataComponentKey.class, Object.class), DataComponentKey.class, "name", "FIELD:Lnet/labymod/api/component/data/DataComponentKey;->name:Ljava/lang/String;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    public static DataComponentKey simple(String name) {
        return KEYS.apply(name);
    }

    public static DataComponentKey fromId(String path) {
        return fromId(Namespaces.MINECRAFT, path);
    }

    public static DataComponentKey fromId(String namespace, String path) {
        return fromId(ResourceLocation.create(namespace, path));
    }

    public static DataComponentKey fromId(ResourceLocation id) {
        return KEYS.apply(id.toString());
    }
}
