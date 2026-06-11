package net.labymod.api.models.addon.info;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/addon/info/AddonEntrypoint.class */
public final class AddonEntrypoint extends Record {
    private final String name;
    private final String version;
    private final int priority;

    public AddonEntrypoint(String name, String version, int priority) {
        this.name = name;
        this.version = version;
        this.priority = priority;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AddonEntrypoint.class), AddonEntrypoint.class, "name;version;priority", "FIELD:Lnet/labymod/api/models/addon/info/AddonEntrypoint;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/models/addon/info/AddonEntrypoint;->version:Ljava/lang/String;", "FIELD:Lnet/labymod/api/models/addon/info/AddonEntrypoint;->priority:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AddonEntrypoint.class), AddonEntrypoint.class, "name;version;priority", "FIELD:Lnet/labymod/api/models/addon/info/AddonEntrypoint;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/models/addon/info/AddonEntrypoint;->version:Ljava/lang/String;", "FIELD:Lnet/labymod/api/models/addon/info/AddonEntrypoint;->priority:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AddonEntrypoint.class, Object.class), AddonEntrypoint.class, "name;version;priority", "FIELD:Lnet/labymod/api/models/addon/info/AddonEntrypoint;->name:Ljava/lang/String;", "FIELD:Lnet/labymod/api/models/addon/info/AddonEntrypoint;->version:Ljava/lang/String;", "FIELD:Lnet/labymod/api/models/addon/info/AddonEntrypoint;->priority:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public String name() {
        return this.name;
    }

    public String version() {
        return this.version;
    }

    public int priority() {
        return this.priority;
    }
}
