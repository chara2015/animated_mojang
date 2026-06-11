package com.mojang.blaze3d.buffers;

import com.mojang.blaze3d.DontObfuscate;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/buffers/GpuFence.class */
@DontObfuscate
public interface GpuFence extends AutoCloseable {
    @Override // java.lang.AutoCloseable
    void close();

    boolean awaitCompletion(long j);
}
