package net.minecraft.core;

import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/DefaultedRegistry.class */
public interface DefaultedRegistry<T> extends Registry<T> {
    Identifier getKey(T t);

    T getValue(Identifier identifier);

    T byId(int i);

    Identifier getDefaultKey();
}
