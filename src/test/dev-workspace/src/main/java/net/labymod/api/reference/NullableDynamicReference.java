package net.labymod.api.reference;

import java.util.function.Supplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/reference/NullableDynamicReference.class */
public class NullableDynamicReference<T> extends Reference<T> {
    public NullableDynamicReference(Class<T> referenceClass, Supplier<T> referenceSupplier) {
        super(referenceClass, referenceSupplier);
    }

    public T get() {
        return this.referenceSupplier.get();
    }
}
