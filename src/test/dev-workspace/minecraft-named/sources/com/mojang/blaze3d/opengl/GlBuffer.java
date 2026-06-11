package com.mojang.blaze3d.opengl;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.jtracy.MemoryPool;
import com.mojang.jtracy.TracyClient;
import java.nio.ByteBuffer;
import java.util.function.Supplier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlBuffer.class */
public class GlBuffer extends GpuBuffer {
    protected static final MemoryPool MEMORY_POOl = TracyClient.createMemoryPool("GPU Buffers");
    protected boolean closed;
    protected final Supplier<String> label;
    private final DirectStateAccess dsa;
    protected final int handle;
    protected ByteBuffer persistentBuffer;

    protected GlBuffer(Supplier<String> $$0, DirectStateAccess $$1, @GpuBuffer.Usage int $$2, long $$3, int $$4, ByteBuffer $$5) {
        super($$2, $$3);
        this.label = $$0;
        this.dsa = $$1;
        this.handle = $$4;
        this.persistentBuffer = $$5;
        int $$6 = (int) Math.min($$3, 2147483647L);
        MEMORY_POOl.malloc($$4, $$6);
    }

    @Override // com.mojang.blaze3d.buffers.GpuBuffer
    public boolean isClosed() {
        return this.closed;
    }

    @Override // com.mojang.blaze3d.buffers.GpuBuffer, java.lang.AutoCloseable
    public void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        if (this.persistentBuffer != null) {
            this.dsa.unmapBuffer(this.handle, usage());
            this.persistentBuffer = null;
        }
        GlStateManager._glDeleteBuffers(this.handle);
        MEMORY_POOl.free(this.handle);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlBuffer$GlMappedView.class */
    public static class GlMappedView implements GpuBuffer.MappedView {
        private final Runnable unmap;
        private final GlBuffer buffer;
        private final ByteBuffer data;
        private boolean closed;

        protected GlMappedView(Runnable $$0, GlBuffer $$1, ByteBuffer $$2) {
            this.unmap = $$0;
            this.buffer = $$1;
            this.data = $$2;
        }

        @Override // com.mojang.blaze3d.buffers.GpuBuffer.MappedView
        public ByteBuffer data() {
            return this.data;
        }

        @Override // com.mojang.blaze3d.buffers.GpuBuffer.MappedView, java.lang.AutoCloseable
        public void close() {
            if (this.closed) {
                return;
            }
            this.closed = true;
            this.unmap.run();
        }
    }
}
