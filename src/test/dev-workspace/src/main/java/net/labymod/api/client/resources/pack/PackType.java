package net.labymod.api.client.resources.pack;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/PackType.class */
public final class PackType extends Record {
    private final String directory;

    public PackType(String directory) {
        this.directory = directory;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PackType.class), PackType.class, "directory", "FIELD:Lnet/labymod/api/client/resources/pack/PackType;->directory:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PackType.class), PackType.class, "directory", "FIELD:Lnet/labymod/api/client/resources/pack/PackType;->directory:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PackType.class, Object.class), PackType.class, "directory", "FIELD:Lnet/labymod/api/client/resources/pack/PackType;->directory:Ljava/lang/String;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String directory() {
        return this.directory;
    }
}
