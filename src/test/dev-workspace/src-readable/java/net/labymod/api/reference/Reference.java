package net.labymod.api.reference;

import java.util.function.Supplier;
import javax.inject.Provider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/reference/Reference.class */
public abstract class Reference<T> implements Provider<T> {
    protected final Class<T> referenceClass;
    protected final Supplier<T> referenceSupplier;

    public Reference(Class<T> referenceClass, Supplier<T> referenceSupplier) {
        this.referenceClass = referenceClass;
        this.referenceSupplier = referenceSupplier;
    }

    public Class<T> getReferenceClass() {
        return this.referenceClass;
    }
}
