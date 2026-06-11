package net.minecraft.resources;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/resources/DependantName.class */
@FunctionalInterface
public interface DependantName<T, V> {
    V get(ResourceKey<T> resourceKey);

    static <T, V> DependantName<T, V> fixed(V $$0) {
        return $$1 -> {
            return $$0;
        };
    }
}
