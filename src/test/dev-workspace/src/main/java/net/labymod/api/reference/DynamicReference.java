package net.labymod.api.reference;

import java.util.function.Supplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/reference/DynamicReference.class */
public class DynamicReference<T> extends NullableDynamicReference<T> {
    public DynamicReference(Class<T> referenceClass, Supplier<T> referenceSupplier) {
        super(referenceClass, referenceSupplier);
    }

    @Override // net.labymod.api.reference.NullableDynamicReference
    public T get() {
        T t = (T) super.get();
        if (t == null) {
            throw new NullPointerException("Reference " + String.valueOf(this.referenceClass) + " cannot be null!");
        }
        return t;
    }
}
