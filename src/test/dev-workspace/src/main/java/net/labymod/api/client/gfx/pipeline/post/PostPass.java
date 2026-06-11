package net.labymod.api.client.gfx.pipeline.post;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.shaders.block.CustomPostProcessorUniformBlock;
import net.labymod.api.laby3d.shaders.block.PostProcessorUniformBlock;
import net.labymod.api.laby3d.vertex.VertexDescriptions;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.BufferBuilder;
import net.labymod.laby3d.api.debugger.ScopedGroup;
import net.labymod.laby3d.api.mesh.GeometryData;
import net.labymod.laby3d.api.mesh.Mesh;
import net.labymod.laby3d.api.pipeline.DrawingMode;
import net.labymod.laby3d.api.pipeline.LoadOp;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.joml.Matrix4f;
import org.joml.Vector2f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/PostPass.class */
public class PostPass {
    private final PostProcessor processor;
    private final String name;
    private final PostPassRenderTarget source;
    private final PostPassRenderTarget destination;
    private final RenderState renderState;
    private final Map<Integer, Supplier<DeviceTextureView>> effects = new HashMap();
    private final Laby3D laby3D = Laby.references().laby3D();
    private final Map<String, CustomPostProcessorUniformBlock> uniformBlocks;
    private final PostPassData postPassData;
    private CustomPostPassProcessor customPostPassProcessor;
    private Matrix4f projectionMatrix;

    public PostPass(PostProcessor processor, String name, PostPassRenderTarget source, PostPassRenderTarget destination, PostPassEffectHolder effect) {
        this.processor = processor;
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.renderState = effect.renderState();
        this.uniformBlocks = new HashMap(effect.uniformBlocks());
        this.postPassData = new PostPassData(this.name, this.uniformBlocks);
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public void setCustomPostPassProcessor(CustomPostPassProcessor customPostPassProcessor) {
        this.customPostPassProcessor = customPostPassProcessor;
    }

    public void submitToCanvas(ScreenCanvas canvas, float time) {
        this.processor.requestProjectionMatrix();
        int width = this.destination.getWidth();
        int height = this.destination.getHeight();
        RenderTarget destinationTarget = this.destination.renderTarget();
        if (this.destination.clear() && !destinationTarget.getClass().getName().contains("Versioned")) {
            RenderDevice renderDevice = this.laby3D.renderDevice();
            ScopedGroup scopedGroup = renderDevice.debugger().scopedGroup("clear-content");
            try {
                CommandBuffer commandBuffer = renderDevice.createCommandBuffer(1);
                try {
                    destinationTarget.clear(false);
                    if (commandBuffer != null) {
                        commandBuffer.close();
                    }
                    if (scopedGroup != null) {
                        scopedGroup.close();
                    }
                } finally {
                }
            } catch (Throwable th) {
                if (scopedGroup != null) {
                    try {
                        scopedGroup.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        canvas.submitPostProcessing(this.renderState, this.source.renderTarget(), destinationTarget, 0.0f, 0.0f, width, height, cmd -> {
            PostProcessorUniformBlock postProcessor = this.laby3D.postProcessor();
            postProcessor.time().set(Float.valueOf(time));
            postProcessor.projectionMatrix().set(this.projectionMatrix);
            postProcessor.sourceSize().set(new Vector2f(this.source.getWidth(), this.source.getHeight()));
            postProcessor.destinationSize().set(new Vector2f(width, height));
            postProcessor.screenSize().set(new Vector2f(Laby.labyAPI().minecraft().minecraftWindow().getRawSize()));
            cmd.bindUniformBlock(PostProcessorUniformBlock.NAME, postProcessor);
            for (CustomPostProcessorUniformBlock uniformBlock : this.uniformBlocks.values()) {
                cmd.bindUniformBlock(uniformBlock.name(), uniformBlock);
            }
            if (this.customPostPassProcessor != null) {
                this.customPostPassProcessor.process(this.postPassData, cmd, time);
            }
            for (Map.Entry<Integer, Supplier<DeviceTextureView>> entry : this.effects.entrySet()) {
                cmd.bindTexture(entry.getKey().intValue(), entry.getValue().get());
            }
        });
    }

    public void process(float time) {
        this.processor.requestProjectionMatrix();
        drawPass(time);
    }

    public PostProcessor processor() {
        return this.processor;
    }

    public RenderState renderState() {
        return this.renderState;
    }

    private void drawPass(float time) {
        BufferBuilder buffer = this.laby3D.begin(DrawingMode.QUADS, VertexDescriptions.POSITION_UV_COLOR);
        int width = this.destination.getWidth();
        int height = this.destination.getHeight();
        buffer.addVertex(0.0f, 0.0f, 500.0f).setUv(0.0f, 0.0f).setColor(-1);
        buffer.addVertex(width, 0.0f, 500.0f).setUv(0.0f, 0.0f).setColor(-1);
        buffer.addVertex(width, height, 500.0f).setUv(0.0f, 0.0f).setColor(-1);
        buffer.addVertex(0.0f, height, 500.0f).setUv(0.0f, 0.0f).setColor(-1);
        PostProcessorUniformBlock postProcessor = this.laby3D.postProcessor();
        postProcessor.time().set(Float.valueOf(time));
        postProcessor.projectionMatrix().set(this.projectionMatrix);
        postProcessor.sourceSize().set(new Vector2f(this.source.getWidth(), this.source.getHeight()));
        postProcessor.destinationSize().set(new Vector2f(width, height));
        postProcessor.screenSize().set(new Vector2f(Laby.labyAPI().minecraft().minecraftWindow().getRawSize()));
        executePass(buffer.build(), postProcessor, time);
    }

    public void addEffect(int samplerIndex, Supplier<DeviceTextureView> textureViewProvider) {
        this.effects.put(Integer.valueOf(samplerIndex), textureViewProvider);
    }

    private void executePass(GeometryData data, PostProcessorUniformBlock postProcessor, float time) {
        RenderDevice device = this.laby3D.renderDevice();
        RenderTarget destinationTarget = this.destination.renderTarget();
        if (this.destination.clear()) {
            CommandBuffer buffer = this.laby3D.renderDevice().createCommandBuffer(1);
            try {
                destinationTarget.clear(false);
                if (buffer != null) {
                    buffer.close();
                }
            } catch (Throwable th) {
                if (buffer != null) {
                    try {
                        buffer.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        CommandBuffer cmd = device.createCommandBuffer();
        try {
            cmd.beginPass(destinationTarget, LoadOp.DONT_CARE, () -> {
                return this.name;
            });
            Mesh mesh = Mesh.create(device, () -> {
                return "PostPassQuad";
            }, data);
            cmd.bindPipeline(this.renderState);
            Objects.requireNonNull(mesh);
            cmd.addCleanupAction(mesh::close);
            cmd.bindUniformBlock(PostProcessorUniformBlock.NAME, postProcessor);
            for (CustomPostProcessorUniformBlock uniformBlock : this.uniformBlocks.values()) {
                cmd.bindUniformBlock(uniformBlock.name(), uniformBlock);
            }
            if (this.customPostPassProcessor != null) {
                this.customPostPassProcessor.process(this.postPassData, cmd, time);
            }
            cmd.bindTexture(0, this.source.findColorTexture(0));
            for (Map.Entry<Integer, Supplier<DeviceTextureView>> entry : this.effects.entrySet()) {
                cmd.bindTexture(entry.getKey().intValue(), entry.getValue().get());
            }
            cmd.draw(mesh);
            cmd.endPass();
            cmd.submit();
            if (cmd != null) {
                cmd.close();
            }
        } catch (Throwable th3) {
            if (cmd != null) {
                try {
                    cmd.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
            }
            throw th3;
        }
    }
}
