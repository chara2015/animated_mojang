package net.minecraft.gametest.framework;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/gametest/framework/RetryOptions.class */
public final class RetryOptions extends Record {
    private final int numberOfTries;
    private final boolean haltOnFailure;
    private static final RetryOptions NO_RETRIES = new RetryOptions(1, true);

    public RetryOptions(int $$0, boolean $$1) {
        this.numberOfTries = $$0;
        this.haltOnFailure = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RetryOptions.class), RetryOptions.class, "numberOfTries;haltOnFailure", "FIELD:Lnet/minecraft/gametest/framework/RetryOptions;->numberOfTries:I", "FIELD:Lnet/minecraft/gametest/framework/RetryOptions;->haltOnFailure:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RetryOptions.class), RetryOptions.class, "numberOfTries;haltOnFailure", "FIELD:Lnet/minecraft/gametest/framework/RetryOptions;->numberOfTries:I", "FIELD:Lnet/minecraft/gametest/framework/RetryOptions;->haltOnFailure:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RetryOptions.class, Object.class), RetryOptions.class, "numberOfTries;haltOnFailure", "FIELD:Lnet/minecraft/gametest/framework/RetryOptions;->numberOfTries:I", "FIELD:Lnet/minecraft/gametest/framework/RetryOptions;->haltOnFailure:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int numberOfTries() {
        return this.numberOfTries;
    }

    public boolean haltOnFailure() {
        return this.haltOnFailure;
    }

    public static RetryOptions noRetries() {
        return NO_RETRIES;
    }

    public boolean unlimitedTries() {
        return this.numberOfTries < 1;
    }

    public boolean hasTriesLeft(int $$0, int $$1) {
        boolean $$2 = $$0 != $$1;
        boolean $$3 = unlimitedTries() || $$0 < this.numberOfTries;
        return $$3 && !($$2 && this.haltOnFailure);
    }

    public boolean hasRetries() {
        return this.numberOfTries != 1;
    }
}
