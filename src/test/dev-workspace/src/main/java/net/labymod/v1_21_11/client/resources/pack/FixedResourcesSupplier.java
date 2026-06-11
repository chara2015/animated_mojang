package net.labymod.v1_21_11.client.resources.pack;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/resources/pack/FixedResourcesSupplier.class */
public final class FixedResourcesSupplier extends Record implements c {
    private final azl packResources;

    public FixedResourcesSupplier(azl packResources) {
        this.packResources = packResources;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FixedResourcesSupplier.class), FixedResourcesSupplier.class, "packResources", "FIELD:Lnet/labymod/v1_21_11/client/resources/pack/FixedResourcesSupplier;->packResources:Lazl;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FixedResourcesSupplier.class), FixedResourcesSupplier.class, "packResources", "FIELD:Lnet/labymod/v1_21_11/client/resources/pack/FixedResourcesSupplier;->packResources:Lazl;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FixedResourcesSupplier.class, Object.class), FixedResourcesSupplier.class, "packResources", "FIELD:Lnet/labymod/v1_21_11/client/resources/pack/FixedResourcesSupplier;->packResources:Lazl;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    public azl packResources() {
        return this.packResources;
    }

    public azl a(azk var1) {
        return this.packResources;
    }

    public azl a(azk var1, a var2) {
        return this.packResources;
    }
}
