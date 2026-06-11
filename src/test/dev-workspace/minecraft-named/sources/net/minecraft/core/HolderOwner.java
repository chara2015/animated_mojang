package net.minecraft.core;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/core/HolderOwner.class */
public interface HolderOwner<T> {
    default boolean canSerializeIn(HolderOwner<T> $$0) {
        return $$0 == this;
    }
}
