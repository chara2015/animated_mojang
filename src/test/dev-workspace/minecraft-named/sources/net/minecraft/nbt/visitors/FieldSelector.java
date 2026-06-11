package net.minecraft.nbt.visitors;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.nbt.TagType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/visitors/FieldSelector.class */
public final class FieldSelector extends Record {
    private final List<String> path;
    private final TagType<?> type;
    private final String name;

    public FieldSelector(List<String> $$0, TagType<?> $$1, String $$2) {
        this.path = $$0;
        this.type = $$1;
        this.name = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FieldSelector.class), FieldSelector.class, "path;type;name", "FIELD:Lnet/minecraft/nbt/visitors/FieldSelector;->path:Ljava/util/List;", "FIELD:Lnet/minecraft/nbt/visitors/FieldSelector;->type:Lnet/minecraft/nbt/TagType;", "FIELD:Lnet/minecraft/nbt/visitors/FieldSelector;->name:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FieldSelector.class), FieldSelector.class, "path;type;name", "FIELD:Lnet/minecraft/nbt/visitors/FieldSelector;->path:Ljava/util/List;", "FIELD:Lnet/minecraft/nbt/visitors/FieldSelector;->type:Lnet/minecraft/nbt/TagType;", "FIELD:Lnet/minecraft/nbt/visitors/FieldSelector;->name:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FieldSelector.class, Object.class), FieldSelector.class, "path;type;name", "FIELD:Lnet/minecraft/nbt/visitors/FieldSelector;->path:Ljava/util/List;", "FIELD:Lnet/minecraft/nbt/visitors/FieldSelector;->type:Lnet/minecraft/nbt/TagType;", "FIELD:Lnet/minecraft/nbt/visitors/FieldSelector;->name:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public List<String> path() {
        return this.path;
    }

    public TagType<?> type() {
        return this.type;
    }

    public String name() {
        return this.name;
    }

    public FieldSelector(TagType<?> $$0, String $$1) {
        this((List<String>) List.of(), $$0, $$1);
    }

    public FieldSelector(String $$0, TagType<?> $$1, String $$2) {
        this((List<String>) List.of($$0), $$1, $$2);
    }

    public FieldSelector(String $$0, String $$1, TagType<?> $$2, String $$3) {
        this((List<String>) List.of($$0, $$1), $$2, $$3);
    }
}
