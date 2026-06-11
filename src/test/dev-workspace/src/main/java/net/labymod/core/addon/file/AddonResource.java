package net.labymod.core.addon.file;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.net.URI;
import net.labymod.api.util.function.IoSupplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/addon/file/AddonResource.class */
public final class AddonResource extends Record {
    private final String name;
    private final URI uri;
    private final IoSupplier<InputStream> stream;

    public AddonResource(String name, URI uri, IoSupplier<InputStream> stream) {
        this.name = name;
        this.uri = uri;
        this.stream = stream;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AddonResource.class), AddonResource.class, "name;uri;stream", "FIELD:Lnet/labymod/core/addon/file/AddonResource;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/core/addon/file/AddonResource;->uri:Ljava/net/URI;", "FIELD:Lnet/labymod/core/addon/file/AddonResource;->stream:Lnet/labymod/api/util/function/IoSupplier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AddonResource.class), AddonResource.class, "name;uri;stream", "FIELD:Lnet/labymod/core/addon/file/AddonResource;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/core/addon/file/AddonResource;->uri:Ljava/net/URI;", "FIELD:Lnet/labymod/core/addon/file/AddonResource;->stream:Lnet/labymod/api/util/function/IoSupplier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AddonResource.class, Object.class), AddonResource.class, "name;uri;stream", "FIELD:Lnet/labymod/core/addon/file/AddonResource;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/core/addon/file/AddonResource;->uri:Ljava/net/URI;", "FIELD:Lnet/labymod/core/addon/file/AddonResource;->stream:Lnet/labymod/api/util/function/IoSupplier;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    public URI uri() {
        return this.uri;
    }

    public IoSupplier<InputStream> stream() {
        return this.stream;
    }

    public InputStream openStream() throws IOException {
        return this.stream.get();
    }
}
