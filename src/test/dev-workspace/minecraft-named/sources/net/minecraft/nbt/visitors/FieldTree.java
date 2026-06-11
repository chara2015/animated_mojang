package net.minecraft.nbt.visitors;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.nbt.TagType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/nbt/visitors/FieldTree.class */
public final class FieldTree extends Record {
    private final int depth;
    private final Map<String, TagType<?>> selectedFields;
    private final Map<String, FieldTree> fieldsToRecurse;

    public FieldTree(int $$0, Map<String, TagType<?>> $$1, Map<String, FieldTree> $$2) {
        this.depth = $$0;
        this.selectedFields = $$1;
        this.fieldsToRecurse = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FieldTree.class), FieldTree.class, "depth;selectedFields;fieldsToRecurse", "FIELD:Lnet/minecraft/nbt/visitors/FieldTree;->depth:I", "FIELD:Lnet/minecraft/nbt/visitors/FieldTree;->selectedFields:Ljava/util/Map;", "FIELD:Lnet/minecraft/nbt/visitors/FieldTree;->fieldsToRecurse:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FieldTree.class), FieldTree.class, "depth;selectedFields;fieldsToRecurse", "FIELD:Lnet/minecraft/nbt/visitors/FieldTree;->depth:I", "FIELD:Lnet/minecraft/nbt/visitors/FieldTree;->selectedFields:Ljava/util/Map;", "FIELD:Lnet/minecraft/nbt/visitors/FieldTree;->fieldsToRecurse:Ljava/util/Map;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FieldTree.class, Object.class), FieldTree.class, "depth;selectedFields;fieldsToRecurse", "FIELD:Lnet/minecraft/nbt/visitors/FieldTree;->depth:I", "FIELD:Lnet/minecraft/nbt/visitors/FieldTree;->selectedFields:Ljava/util/Map;", "FIELD:Lnet/minecraft/nbt/visitors/FieldTree;->fieldsToRecurse:Ljava/util/Map;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int depth() {
        return this.depth;
    }

    public Map<String, TagType<?>> selectedFields() {
        return this.selectedFields;
    }

    public Map<String, FieldTree> fieldsToRecurse() {
        return this.fieldsToRecurse;
    }

    private FieldTree(int $$0) {
        this($$0, new HashMap(), new HashMap());
    }

    public static FieldTree createRoot() {
        return new FieldTree(1);
    }

    public void addEntry(FieldSelector $$0) {
        if (this.depth <= $$0.path().size()) {
            this.fieldsToRecurse.computeIfAbsent($$0.path().get(this.depth - 1), $$02 -> {
                return new FieldTree(this.depth + 1);
            }).addEntry($$0);
        } else {
            this.selectedFields.put($$0.name(), $$0.type());
        }
    }

    public boolean isSelected(TagType<?> $$0, String $$1) {
        return $$0.equals(selectedFields().get($$1));
    }
}
