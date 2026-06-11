package net.labymod.core.client.render.batch;

import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.renderer.mesh.MeshRenderer;
import net.labymod.api.client.render.batch.RenderContext;
import net.labymod.api.util.ide.IdeUtil;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/batch/DefaultRenderContext.class */
public abstract class DefaultRenderContext<T extends RenderContext<T>> implements RenderContext<T> {
    protected float zOffset;

    @Override // net.labymod.api.client.render.batch.RenderContext
    public float zOffset() {
        return this.zOffset;
    }

    @Override // net.labymod.api.client.render.batch.RenderContext
    public void zOffset(float zOffset) {
        this.zOffset = zOffset;
    }

    protected void drawImmediate(BufferBuilder buffer, RenderState renderState) {
        drawImmediate(buffer, renderState, command -> {
        });
    }

    protected void drawImmediate(BufferBuilder buffer, RenderState renderState, Consumer<CommandBuffer> commandConsumer) {
        if (!IdeUtil.RUNNING_IN_IDE || Laby.references().renderEnvironmentContext().isScreenContext()) {
        }
        MeshRenderer.drawImmediate(buffer.build(), renderState, commandConsumer);
    }
}
