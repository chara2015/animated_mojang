package net.labymod.api.reference;

import java.util.function.Supplier;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/reference/SingletonReference.class */
public class SingletonReference<T> extends NullableSingletonReference<T> {
    public SingletonReference(Class<T> referenceClass, Supplier<T> referenceSupplier) {
        super(referenceClass, referenceSupplier);
    }

    @Override // net.labymod.api.reference.NullableSingletonReference
    public T get() {
        T t = (T) super.get();
        if (t == null) {
            throw new NullPointerException("Reference " + String.valueOf(this.referenceClass) + " cannot be null!");
        }
        return t;
    }
}
