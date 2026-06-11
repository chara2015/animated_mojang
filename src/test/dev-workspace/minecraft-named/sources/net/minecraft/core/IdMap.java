package net.minecraft.core;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/IdMap.class */
public interface IdMap<T> extends Iterable<T> {
    public static final int DEFAULT = -1;

    int getId(T t);

    T byId(int i);

    int size();

    default T byIdOrThrow(int $$0) {
        T $$1 = byId($$0);
        if ($$1 == null) {
            throw new IllegalArgumentException("No value with id " + $$0);
        }
        return $$1;
    }

    default int getIdOrThrow(T $$0) {
        int $$1 = getId($$0);
        if ($$1 == -1) {
            throw new IllegalArgumentException("Can't find id for '" + String.valueOf($$0) + "' in map " + String.valueOf(this));
        }
        return $$1;
    }
}
