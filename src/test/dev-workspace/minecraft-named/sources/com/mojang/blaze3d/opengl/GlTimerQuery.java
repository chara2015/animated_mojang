package com.mojang.blaze3d.opengl;

import com.mojang.blaze3d.systems.GpuQuery;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.OptionalLong;
import org.lwjgl.opengl.ARBTimerQuery;
import org.lwjgl.opengl.GL32C;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/opengl/GlTimerQuery.class */
public class GlTimerQuery implements GpuQuery {
    private final int queryId;
    private boolean closed;
    private OptionalLong result = OptionalLong.empty();

    GlTimerQuery(int $$0) {
        this.queryId = $$0;
    }

    @Override // com.mojang.blaze3d.systems.GpuQuery
    public OptionalLong getValue() {
        RenderSystem.assertOnRenderThread();
        if (this.closed) {
            throw new IllegalStateException("GlTimerQuery is closed");
        }
        if (this.result.isPresent()) {
            return this.result;
        }
        if (GL32C.glGetQueryObjecti(this.queryId, 34919) == 1) {
            this.result = OptionalLong.of(ARBTimerQuery.glGetQueryObjecti64(this.queryId, 34918));
            return this.result;
        }
        return OptionalLong.empty();
    }

    @Override // com.mojang.blaze3d.systems.GpuQuery, java.lang.AutoCloseable
    public void close() {
        RenderSystem.assertOnRenderThread();
        if (this.closed) {
            return;
        }
        this.closed = true;
        GL32C.glDeleteQueries(this.queryId);
    }
}
