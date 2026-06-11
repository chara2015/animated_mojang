package com.mojang.blaze3d.resource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/resource/ResourceDescriptor.class */
public interface ResourceDescriptor<T> {
    T allocate();

    void free(T t);

    default void prepare(T $$0) {
    }

    default boolean canUsePhysicalResource(ResourceDescriptor<?> $$0) {
        return equals($$0);
    }
}
