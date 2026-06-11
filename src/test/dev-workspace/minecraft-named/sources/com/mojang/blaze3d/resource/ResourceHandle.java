package com.mojang.blaze3d.resource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/resource/ResourceHandle.class */
public interface ResourceHandle<T> {
    public static final ResourceHandle<?> INVALID_HANDLE = () -> {
        throw new IllegalStateException("Cannot dereference handle with no underlying resource");
    };

    T get();

    static <T> ResourceHandle<T> invalid() {
        return (ResourceHandle<T>) INVALID_HANDLE;
    }
}
