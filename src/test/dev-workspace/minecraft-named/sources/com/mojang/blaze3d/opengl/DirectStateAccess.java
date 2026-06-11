package com.mojang.blaze3d.opengl;

import com.mojang.blaze3d.GraphicsWorkarounds;
import com.mojang.blaze3d.buffers.GpuBuffer;
import java.nio.ByteBuffer;
import java.util.Set;
import org.lwjgl.opengl.ARBBufferStorage;
import org.lwjgl.opengl.ARBDirectStateAccess;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GLCapabilities;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/DirectStateAccess.class */
public abstract class DirectStateAccess {
    abstract int createBuffer();

    abstract void bufferData(int i, long j, @GpuBuffer.Usage int i2);

    abstract void bufferData(int i, ByteBuffer byteBuffer, @GpuBuffer.Usage int i2);

    abstract void bufferSubData(int i, long j, ByteBuffer byteBuffer, @GpuBuffer.Usage int i2);

    abstract void bufferStorage(int i, long j, @GpuBuffer.Usage int i2);

    abstract void bufferStorage(int i, ByteBuffer byteBuffer, @GpuBuffer.Usage int i2);

    abstract ByteBuffer mapBufferRange(int i, long j, long j2, int i2, @GpuBuffer.Usage int i3);

    abstract void unmapBuffer(int i, @GpuBuffer.Usage int i2);

    abstract int createFrameBufferObject();

    abstract void bindFrameBufferTextures(int i, int i2, int i3, int i4, int i5);

    abstract void blitFrameBuffers(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12);

    abstract void flushMappedBufferRange(int i, long j, long j2, @GpuBuffer.Usage int i2);

    abstract void copyBufferSubData(int i, int i2, long j, long j2, long j3);

    public static DirectStateAccess create(GLCapabilities $$0, Set<String> $$1, GraphicsWorkarounds $$2) {
        if ($$0.GL_ARB_direct_state_access && GlDevice.USE_GL_ARB_direct_state_access && !$$2.isGlOnDx12()) {
            $$1.add("GL_ARB_direct_state_access");
            return new Core();
        }
        return new Emulated();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/DirectStateAccess$Core.class */
    static class Core extends DirectStateAccess {
        Core() {
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        int createBuffer() {
            GlStateManager.incrementTrackedBuffers();
            return ARBDirectStateAccess.glCreateBuffers();
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void bufferData(int $$0, long $$1, @GpuBuffer.Usage int $$2) {
            ARBDirectStateAccess.glNamedBufferData($$0, $$1, GlConst.bufferUsageToGlEnum($$2));
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void bufferData(int $$0, ByteBuffer $$1, @GpuBuffer.Usage int $$2) {
            ARBDirectStateAccess.glNamedBufferData($$0, $$1, GlConst.bufferUsageToGlEnum($$2));
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void bufferSubData(int $$0, long $$1, ByteBuffer $$2, @GpuBuffer.Usage int $$3) {
            ARBDirectStateAccess.glNamedBufferSubData($$0, $$1, $$2);
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void bufferStorage(int $$0, long $$1, @GpuBuffer.Usage int $$2) {
            ARBDirectStateAccess.glNamedBufferStorage($$0, $$1, GlConst.bufferUsageToGlFlag($$2));
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void bufferStorage(int $$0, ByteBuffer $$1, @GpuBuffer.Usage int $$2) {
            ARBDirectStateAccess.glNamedBufferStorage($$0, $$1, GlConst.bufferUsageToGlFlag($$2));
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        ByteBuffer mapBufferRange(int $$0, long $$1, long $$2, int $$3, @GpuBuffer.Usage int $$4) {
            return ARBDirectStateAccess.glMapNamedBufferRange($$0, $$1, $$2, $$3);
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void unmapBuffer(int $$0, int $$1) {
            ARBDirectStateAccess.glUnmapNamedBuffer($$0);
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        public int createFrameBufferObject() {
            return ARBDirectStateAccess.glCreateFramebuffers();
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        public void bindFrameBufferTextures(int $$0, int $$1, int $$2, int $$3, @GpuBuffer.Usage int $$4) {
            ARBDirectStateAccess.glNamedFramebufferTexture($$0, GlConst.GL_COLOR_ATTACHMENT0, $$1, $$3);
            ARBDirectStateAccess.glNamedFramebufferTexture($$0, GlConst.GL_DEPTH_ATTACHMENT, $$2, $$3);
            if ($$4 != 0) {
                GlStateManager._glBindFramebuffer($$4, $$0);
            }
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        public void blitFrameBuffers(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, int $$9, int $$10, int $$11) {
            ARBDirectStateAccess.glBlitNamedFramebuffer($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11);
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void flushMappedBufferRange(int $$0, long $$1, long $$2, @GpuBuffer.Usage int $$3) {
            ARBDirectStateAccess.glFlushMappedNamedBufferRange($$0, $$1, $$2);
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void copyBufferSubData(int $$0, int $$1, long $$2, long $$3, long $$4) {
            ARBDirectStateAccess.glCopyNamedBufferSubData($$0, $$1, $$2, $$3, $$4);
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/DirectStateAccess$Emulated.class */
    static class Emulated extends DirectStateAccess {
        Emulated() {
        }

        private int selectBufferBindTarget(@GpuBuffer.Usage int $$0) {
            if (($$0 & 32) != 0) {
                return GlConst.GL_ARRAY_BUFFER;
            }
            if (($$0 & 64) != 0) {
                return GlConst.GL_ELEMENT_ARRAY_BUFFER;
            }
            if (($$0 & 128) != 0) {
                return GlConst.GL_UNIFORM_BUFFER;
            }
            return GlConst.GL_COPY_WRITE_BUFFER;
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        int createBuffer() {
            return GlStateManager._glGenBuffers();
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void bufferData(int $$0, long $$1, @GpuBuffer.Usage int $$2) {
            int $$3 = selectBufferBindTarget($$2);
            GlStateManager._glBindBuffer($$3, $$0);
            GlStateManager._glBufferData($$3, $$1, GlConst.bufferUsageToGlEnum($$2));
            GlStateManager._glBindBuffer($$3, 0);
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void bufferData(int $$0, ByteBuffer $$1, @GpuBuffer.Usage int $$2) {
            int $$3 = selectBufferBindTarget($$2);
            GlStateManager._glBindBuffer($$3, $$0);
            GlStateManager._glBufferData($$3, $$1, GlConst.bufferUsageToGlEnum($$2));
            GlStateManager._glBindBuffer($$3, 0);
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void bufferSubData(int $$0, long $$1, ByteBuffer $$2, @GpuBuffer.Usage int $$3) {
            int $$4 = selectBufferBindTarget($$3);
            GlStateManager._glBindBuffer($$4, $$0);
            GlStateManager._glBufferSubData($$4, $$1, $$2);
            GlStateManager._glBindBuffer($$4, 0);
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void bufferStorage(int $$0, long $$1, @GpuBuffer.Usage int $$2) {
            int $$3 = selectBufferBindTarget($$2);
            GlStateManager._glBindBuffer($$3, $$0);
            ARBBufferStorage.glBufferStorage($$3, $$1, GlConst.bufferUsageToGlFlag($$2));
            GlStateManager._glBindBuffer($$3, 0);
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void bufferStorage(int $$0, ByteBuffer $$1, @GpuBuffer.Usage int $$2) {
            int $$3 = selectBufferBindTarget($$2);
            GlStateManager._glBindBuffer($$3, $$0);
            ARBBufferStorage.glBufferStorage($$3, $$1, GlConst.bufferUsageToGlFlag($$2));
            GlStateManager._glBindBuffer($$3, 0);
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        ByteBuffer mapBufferRange(int $$0, long $$1, long $$2, int $$3, @GpuBuffer.Usage int $$4) {
            int $$5 = selectBufferBindTarget($$4);
            GlStateManager._glBindBuffer($$5, $$0);
            ByteBuffer $$6 = GlStateManager._glMapBufferRange($$5, $$1, $$2, $$3);
            GlStateManager._glBindBuffer($$5, 0);
            return $$6;
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void unmapBuffer(int $$0, @GpuBuffer.Usage int $$1) {
            int $$2 = selectBufferBindTarget($$1);
            GlStateManager._glBindBuffer($$2, $$0);
            GlStateManager._glUnmapBuffer($$2);
            GlStateManager._glBindBuffer($$2, 0);
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void flushMappedBufferRange(int $$0, long $$1, long $$2, @GpuBuffer.Usage int $$3) {
            int $$4 = selectBufferBindTarget($$3);
            GlStateManager._glBindBuffer($$4, $$0);
            GL30.glFlushMappedBufferRange($$4, $$1, $$2);
            GlStateManager._glBindBuffer($$4, 0);
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        void copyBufferSubData(int $$0, int $$1, long $$2, long $$3, long $$4) {
            GlStateManager._glBindBuffer(GlConst.GL_COPY_READ_BUFFER, $$0);
            GlStateManager._glBindBuffer(GlConst.GL_COPY_WRITE_BUFFER, $$1);
            GL31.glCopyBufferSubData(GlConst.GL_COPY_READ_BUFFER, GlConst.GL_COPY_WRITE_BUFFER, $$2, $$3, $$4);
            GlStateManager._glBindBuffer(GlConst.GL_COPY_READ_BUFFER, 0);
            GlStateManager._glBindBuffer(GlConst.GL_COPY_WRITE_BUFFER, 0);
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        public int createFrameBufferObject() {
            return GlStateManager.glGenFramebuffers();
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        public void bindFrameBufferTextures(int $$0, int $$1, int $$2, int $$3, int $$4) {
            int $$5 = $$4 == 0 ? GlConst.GL_DRAW_FRAMEBUFFER : $$4;
            int $$6 = GlStateManager.getFrameBuffer($$5);
            GlStateManager._glBindFramebuffer($$5, $$0);
            GlStateManager._glFramebufferTexture2D($$5, GlConst.GL_COLOR_ATTACHMENT0, GlConst.GL_TEXTURE_2D, $$1, $$3);
            GlStateManager._glFramebufferTexture2D($$5, GlConst.GL_DEPTH_ATTACHMENT, GlConst.GL_TEXTURE_2D, $$2, $$3);
            if ($$4 == 0) {
                GlStateManager._glBindFramebuffer($$5, $$6);
            }
        }

        @Override // com.mojang.blaze3d.opengl.DirectStateAccess
        public void blitFrameBuffers(int $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, int $$8, int $$9, int $$10, int $$11) {
            int $$12 = GlStateManager.getFrameBuffer(GlConst.GL_READ_FRAMEBUFFER);
            int $$13 = GlStateManager.getFrameBuffer(GlConst.GL_DRAW_FRAMEBUFFER);
            GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, $$0);
            GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, $$1);
            GlStateManager._glBlitFrameBuffer($$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11);
            GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, $$12);
            GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, $$13);
        }
    }
}
