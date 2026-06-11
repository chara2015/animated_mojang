package net.labymod.api.property;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/property/PropertyConvention.class */
@FunctionalInterface
public interface PropertyConvention<T> {
    T convention(T t);
}
