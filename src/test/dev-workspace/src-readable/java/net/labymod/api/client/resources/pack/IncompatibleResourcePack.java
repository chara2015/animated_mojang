package net.labymod.api.client.resources.pack;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Collection;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/IncompatibleResourcePack.class */
public final class IncompatibleResourcePack extends Record {
    private final String name;
    private final Collection<String> ignoredFiles;
    private final Collection<String> unsupportedFiles;

    public IncompatibleResourcePack(String name, Collection<String> ignoredFiles, Collection<String> unsupportedFiles) {
        this.name = name;
        this.ignoredFiles = ignoredFiles;
        this.unsupportedFiles = unsupportedFiles;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, IncompatibleResourcePack.class), IncompatibleResourcePack.class, "name;ignoredFiles;unsupportedFiles", "FIELD:Lnet/labymod/api/client/resources/pack/IncompatibleResourcePack;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/resources/pack/IncompatibleResourcePack;->ignoredFiles:Ljava/util/Collection;", "FIELD:Lnet/labymod/api/client/resources/pack/IncompatibleResourcePack;->unsupportedFiles:Ljava/util/Collection;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, IncompatibleResourcePack.class), IncompatibleResourcePack.class, "name;ignoredFiles;unsupportedFiles", "FIELD:Lnet/labymod/api/client/resources/pack/IncompatibleResourcePack;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/resources/pack/IncompatibleResourcePack;->ignoredFiles:Ljava/util/Collection;", "FIELD:Lnet/labymod/api/client/resources/pack/IncompatibleResourcePack;->unsupportedFiles:Ljava/util/Collection;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, IncompatibleResourcePack.class, Object.class), IncompatibleResourcePack.class, "name;ignoredFiles;unsupportedFiles", "FIELD:Lnet/labymod/api/client/resources/pack/IncompatibleResourcePack;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/resources/pack/IncompatibleResourcePack;->ignoredFiles:Ljava/util/Collection;", "FIELD:Lnet/labymod/api/client/resources/pack/IncompatibleResourcePack;->unsupportedFiles:Ljava/util/Collection;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    public Collection<String> ignoredFiles() {
        return this.ignoredFiles;
    }

    public Collection<String> unsupportedFiles() {
        return this.unsupportedFiles;
    }

    public boolean isCompatible() {
        return this.unsupportedFiles.isEmpty();
    }

    public boolean isIncompatible() {
        return !isCompatible();
    }
}
