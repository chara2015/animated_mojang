package net.minecraft.server.permissions;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.function.Predicate;
import net.minecraft.server.permissions.PermissionSetSupplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/permissions/PermissionProviderCheck.class */
public final class PermissionProviderCheck<T extends PermissionSetSupplier> extends Record implements Predicate<T> {
    private final PermissionCheck test;

    public PermissionProviderCheck(PermissionCheck $$0) {
        this.test = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PermissionProviderCheck.class), PermissionProviderCheck.class, "test", "FIELD:Lnet/minecraft/server/permissions/PermissionProviderCheck;->test:Lnet/minecraft/server/permissions/PermissionCheck;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PermissionProviderCheck.class), PermissionProviderCheck.class, "test", "FIELD:Lnet/minecraft/server/permissions/PermissionProviderCheck;->test:Lnet/minecraft/server/permissions/PermissionCheck;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PermissionProviderCheck.class, Object.class), PermissionProviderCheck.class, "test", "FIELD:Lnet/minecraft/server/permissions/PermissionProviderCheck;->test:Lnet/minecraft/server/permissions/PermissionCheck;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public PermissionCheck test() {
        return this.test;
    }

    @Override // java.util.function.Predicate
    public boolean test(T $$0) {
        return this.test.check($$0.permissions());
    }
}
