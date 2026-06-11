package net.labymod.api.client.resources.pack;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/pack/ResourceFile.class */
public final class ResourceFile extends Record {
    private final String baseDirectory;
    private final String fileName;

    public ResourceFile(String baseDirectory, String fileName) {
        this.baseDirectory = baseDirectory;
        this.fileName = fileName;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ResourceFile.class), ResourceFile.class, "baseDirectory;fileName", "FIELD:Lnet/labymod/api/client/resources/pack/ResourceFile;->baseDirectory:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/resources/pack/ResourceFile;->fileName:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ResourceFile.class, Object.class), ResourceFile.class, "baseDirectory;fileName", "FIELD:Lnet/labymod/api/client/resources/pack/ResourceFile;->baseDirectory:Ljava/lang/String;", "FIELD:Lnet/labymod/api/client/resources/pack/ResourceFile;->fileName:Ljava/lang/String;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String baseDirectory() {
        return this.baseDirectory;
    }

    public String fileName() {
        return this.fileName;
    }

    public static ResourceFile atlases(String fileName) {
        return of("atlases", fileName);
    }

    public static ResourceFile blockStates(String fileName) {
        return of("blockstates", fileName);
    }

    public static ResourceFile font(String fileName) {
        return of("font", fileName);
    }

    public static ResourceFile models(String fileName) {
        return of("models", fileName);
    }

    public static ResourceFile particles(String fileName) {
        return of("particles", fileName);
    }

    public static ResourceFile shaders(String fileName) {
        return of("shaders", fileName);
    }

    public static ResourceFile texts(String fileName) {
        return of("texts", fileName);
    }

    public static ResourceFile textures(String fileName) {
        return of("textures", fileName);
    }

    public static ResourceFile of(String baseDirectory, String fileName) {
        return new ResourceFile(baseDirectory, fileName);
    }

    @Override // java.lang.Record
    @NotNull
    public String toString() {
        return this.baseDirectory + "/" + this.fileName;
    }
}
