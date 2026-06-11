package net.labymod.core.laby3d.pipeline.pass;

import java.util.function.Supplier;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.laby3d.api.mesh.Mesh;
import net.labymod.laby3d.api.opengl.GlRenderDevice;
import net.labymod.laby3d.api.pipeline.LoadOp;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.pass.PassDescriptor;
import net.labymod.laby3d.api.pipeline.pass.StencilMode;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.shaders.UniformValue;
import net.labymod.laby3d.api.shaders.block.UniformBlock;
import net.labymod.laby3d.api.shaders.block.UniformBlockData;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/laby3d/pipeline/pass/ContextAwareCommandBuffer.class */
public class ContextAwareCommandBuffer implements CommandBuffer {
    private final GlRenderDevice renderDevice;
    private final CommandBuffer delegate;
    private final Runnable resourceCleanup;
    private final int flags;
    private final boolean contextAware = hasFlag(1);

    public ContextAwareCommandBuffer(GlRenderDevice renderDevice, CommandBuffer delegate, Runnable resourceCleanup, int flags) {
        this.renderDevice = renderDevice;
        this.delegate = delegate;
        this.resourceCleanup = resourceCleanup;
        this.flags = flags;
        if (this.contextAware) {
            this.renderDevice.storeState();
        }
    }

    public int flags() {
        return this.flags;
    }

    public PassDescriptor passDescriptor() {
        return this.delegate.passDescriptor();
    }

    public void beginPass(@NotNull RenderTarget target) {
        this.delegate.beginPass(target);
    }

    public void beginPass(@NotNull RenderTarget target, @NotNull LoadOp loadOp) {
        this.delegate.beginPass(target, loadOp);
    }

    public void beginPass(RenderTarget target, LoadOp loadOp, @Nullable Supplier<String> name) {
        this.delegate.beginPass(target, loadOp, name);
    }

    public void endPass() {
        this.delegate.endPass();
    }

    public void bindPipeline(@NotNull RenderState renderState) {
        this.delegate.bindPipeline(renderState);
    }

    public void bindTexture(int index, @NotNull DeviceTextureView texture) {
        this.delegate.bindTexture(index, texture);
    }

    public void bindUniformBlock(@NotNull String name, @NotNull UniformBlock block) {
        this.delegate.bindUniformBlock(name, block);
    }

    public void bindUniformBlockData(@NotNull String name, @NotNull UniformBlockData data) {
        this.delegate.bindUniformBlockData(name, data);
    }

    public void setUniform(@NotNull String name, @NotNull UniformValue value) {
        this.delegate.setUniform(name, value);
    }

    public void draw(@NotNull Mesh mesh) {
        this.delegate.draw(mesh);
    }

    public void draw(@NotNull Mesh mesh, int instanceCount) {
        this.delegate.draw(mesh, instanceCount);
    }

    public void setScissor(int x, int y, int width, int height) {
        this.delegate.setScissor(x, y, width, height);
    }

    public void disableScissor() {
        this.delegate.disableScissor();
    }

    public void setStencilMode(StencilMode mode) {
        this.delegate.setStencilMode(mode);
    }

    public void addCleanupAction(@NotNull Runnable action) {
        this.delegate.addCleanupAction(action);
    }

    public void submit() {
        this.delegate.submit();
    }

    public void close() {
        this.delegate.close();
        if (this.resourceCleanup != null) {
            this.resourceCleanup.run();
        }
        if (this.contextAware) {
            this.renderDevice.restoreState();
        }
        this.renderDevice.invalidateRenderState();
        if (this.renderDevice.hasVertexArrays()) {
            this.renderDevice.functions().bindVertexArray(0);
        }
        this.renderDevice.functions().bindBuffer(GlConst.GL_ARRAY_BUFFER, 0);
        this.renderDevice.functions().bindBuffer(GlConst.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    private boolean hasFlag(int flag) {
        return (this.flags & flag) != 0;
    }
}
