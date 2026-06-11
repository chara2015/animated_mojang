package net.labymod.core.client.render.batch;

import java.util.function.Consumer;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.render.batch.LineRenderContext;
import net.labymod.api.client.render.batch.RenderContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.api.models.Implements;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/batch/DefaultLineRenderContext.class */
@Singleton
@Implements(LineRenderContext.class)
public class DefaultLineRenderContext extends DefaultRenderContext<LineRenderContext> implements LineRenderContext {
    private final Laby3D laby3D = Laby.references().laby3D();
    private BufferBuilder builder;
    private Stack stack;

    @Inject
    public DefaultLineRenderContext() {
    }

    @Override // net.labymod.api.client.render.batch.LineRenderContext
    public LineRenderContext begin(Stack stack) {
        this.stack = stack;
        this.builder = this.laby3D.begin(DrawingMode.LINES, VertexDescriptions.POSITION_COLOR_NORMAL);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.LineRenderContext
    public LineRenderContext renderGradient(float x, float y, float x1, float y1, int color, int color1) {
        Matrix4f pose = this.stack.getProvider().getPose();
        this.builder.addVertex(pose, x, y, this.zOffset).setColor(color).setNormal(1.0f, 0.0f, 0.0f);
        this.builder.addVertex(pose, x1, y1, this.zOffset).setColor(color1).setNormal(1.0f, 0.0f, 0.0f);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.LineRenderContext
    public LineRenderContext render(float x, float y, float x1, float y1, float red, float green, float blue, float alpha) {
        Matrix4f pose = this.stack.getProvider().getPose();
        this.builder.addVertex(pose, x, y, this.zOffset).setColor(red, green, blue, alpha).setNormal(1.0f, 0.0f, 0.0f);
        this.builder.addVertex(pose, x1, y1, this.zOffset).setColor(red, green, blue, alpha).setNormal(1.0f, 0.0f, 0.0f);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.RenderContext
    public RenderContext<LineRenderContext> uploadToBuffer(RenderState renderState) {
        drawImmediate(this.builder, renderState);
        return this;
    }

    @Override // net.labymod.api.client.render.batch.RenderContext
    public RenderContext<LineRenderContext> uploadToBuffer(RenderState renderState, Consumer<CommandBuffer> commandConsumer) {
        drawImmediate(this.builder, renderState, commandConsumer);
        return this;
    }
}
