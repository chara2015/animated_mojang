package net.labymod.api.models.version;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/models/version/VersionComparison.class */
public interface VersionComparison<T> {
    boolean isCompatible(T t);

    boolean isGreaterThan(T t);

    boolean isLowerThan(T t);
}
