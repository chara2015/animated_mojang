package net.minecraft.util.parsing.packrat;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/util/parsing/packrat/Atom.class */
public final class Atom<T> extends Record {
    private final String name;

    public Atom(String $$0) {
        this.name = $$0;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Atom.class), Atom.class, "name", "FIELD:Lnet/minecraft/util/parsing/packrat/Atom;->name:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Atom.class, Object.class), Atom.class, "name", "FIELD:Lnet/minecraft/util/parsing/packrat/Atom;->name:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    @Override // java.lang.Record
    public String toString() {
        return "<" + this.name + ">";
    }

    public static <T> Atom<T> of(String $$0) {
        return new Atom<>($$0);
    }
}
