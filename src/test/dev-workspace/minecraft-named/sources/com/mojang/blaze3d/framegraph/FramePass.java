package com.mojang.blaze3d.framegraph;

import com.mojang.blaze3d.resource.ResourceDescriptor;
import com.mojang.blaze3d.resource.ResourceHandle;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/framegraph/FramePass.class */
public interface FramePass {
    <T> ResourceHandle<T> createsInternal(String str, ResourceDescriptor<T> resourceDescriptor);

    <T> void reads(ResourceHandle<T> resourceHandle);

    <T> ResourceHandle<T> readsAndWrites(ResourceHandle<T> resourceHandle);

    void requires(FramePass framePass);

    void disableCulling();

    void executes(Runnable runnable);
}
