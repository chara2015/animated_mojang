package com.mojang.blaze3d.opengl;

import com.mojang.blaze3d.buffers.GpuFence;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlFence.class */
public class GlFence implements GpuFence {
    private long handle = GlStateManager._glFenceSync(GlConst.GL_SYNC_GPU_COMMANDS_COMPLETE, 0);

    @Override // com.mojang.blaze3d.buffers.GpuFence, java.lang.AutoCloseable
    public void close() {
        if (this.handle != 0) {
            GlStateManager._glDeleteSync(this.handle);
            this.handle = 0L;
        }
    }

    @Override // com.mojang.blaze3d.buffers.GpuFence
    public boolean awaitCompletion(long $$0) {
        if (this.handle == 0) {
            return true;
        }
        int $$1 = GlStateManager._glClientWaitSync(this.handle, 0, $$0);
        if ($$1 == 37147) {
            return false;
        }
        if ($$1 == 37149) {
            throw new IllegalStateException("Failed to complete GPU fence: " + GlStateManager._getError());
        }
        return true;
    }
}
