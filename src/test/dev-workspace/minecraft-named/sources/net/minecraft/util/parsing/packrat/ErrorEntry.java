package net.minecraft.util.parsing.packrat;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/ErrorEntry.class */
public final class ErrorEntry<S> extends Record {
    private final int cursor;
    private final SuggestionSupplier<S> suggestions;
    private final Object reason;

    public ErrorEntry(int $$0, SuggestionSupplier<S> $$1, Object $$2) {
        this.cursor = $$0;
        this.suggestions = $$1;
        this.reason = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ErrorEntry.class), ErrorEntry.class, "cursor;suggestions;reason", "FIELD:Lnet/minecraft/util/parsing/packrat/ErrorEntry;->cursor:I", "FIELD:Lnet/minecraft/util/parsing/packrat/ErrorEntry;->suggestions:Lnet/minecraft/util/parsing/packrat/SuggestionSupplier;", "FIELD:Lnet/minecraft/util/parsing/packrat/ErrorEntry;->reason:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ErrorEntry.class), ErrorEntry.class, "cursor;suggestions;reason", "FIELD:Lnet/minecraft/util/parsing/packrat/ErrorEntry;->cursor:I", "FIELD:Lnet/minecraft/util/parsing/packrat/ErrorEntry;->suggestions:Lnet/minecraft/util/parsing/packrat/SuggestionSupplier;", "FIELD:Lnet/minecraft/util/parsing/packrat/ErrorEntry;->reason:Ljava/lang/Object;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ErrorEntry.class, Object.class), ErrorEntry.class, "cursor;suggestions;reason", "FIELD:Lnet/minecraft/util/parsing/packrat/ErrorEntry;->cursor:I", "FIELD:Lnet/minecraft/util/parsing/packrat/ErrorEntry;->suggestions:Lnet/minecraft/util/parsing/packrat/SuggestionSupplier;", "FIELD:Lnet/minecraft/util/parsing/packrat/ErrorEntry;->reason:Ljava/lang/Object;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int cursor() {
        return this.cursor;
    }

    public SuggestionSupplier<S> suggestions() {
        return this.suggestions;
    }

    public Object reason() {
        return this.reason;
    }
}
