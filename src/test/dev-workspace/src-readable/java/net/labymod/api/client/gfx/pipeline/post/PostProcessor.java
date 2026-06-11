package net.labymod.api.client.gfx.pipeline.post;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ScreenResizeEvent;
import net.labymod.api.event.client.gui.window.WindowResizeEvent;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.util.matrix.CachedOrthoProjectionMatrix;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/PostProcessor.class */
public final class PostProcessor {
    private final String name;
    private final RenderTarget destinationTarget;
    private final Laby3D laby3D;

    @Nullable
    private CustomPostPassProcessor customPostPassProcessor;
    private Matrix4f projectionMatrix;
    private final CachedOrthoProjectionMatrix<Matrix4f> postProjectionMatrix = CachedOrthoProjectionMatrix.simple(0.1f, 1000.0f, false);
    private final PostProcessorRenderTargetRegistry renderTargetRegistry = new PostProcessorRenderTargetRegistry();
    private final List<PostPass> passes = new ArrayList();

    PostProcessor(RenderTarget destinationTarget, ResourceLocation location) {
        this.name = location.toString();
        this.destinationTarget = destinationTarget;
        updateProjectionMatrix();
        this.laby3D = Laby.references().laby3D();
        Laby.references().eventBus().registerListener(this);
    }

    @Subscribe
    public void onWindowResize(WindowResizeEvent event) {
        resize();
    }

    @Subscribe
    public void onScreenResize(ScreenResizeEvent event) {
        resize();
    }

    public void process(ScreenContext context) {
        ScreenCanvas canvas = context.canvas();
        float time = context.getTickDelta();
        for (PostPass pass : this.passes) {
            pass.submitToCanvas(canvas, time);
        }
    }

    public void process(float tickDelta) {
        CommandBuffer commandBuffer = this.laby3D.renderDevice().createCommandBuffer(1);
        try {
            for (PostPass pass : this.passes) {
                pass.process(tickDelta);
            }
            if (commandBuffer != null) {
                commandBuffer.close();
            }
        } catch (Throwable th) {
            if (commandBuffer != null) {
                try {
                    commandBuffer.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void resize(int width, int height) {
        updateProjectionMatrix();
        for (PostPass pass : this.passes) {
            pass.setProjectionMatrix(this.projectionMatrix);
        }
        this.renderTargetRegistry.resizeFullSizedTargets(width, height);
    }

    @Nullable
    public CustomPostPassProcessor getCustomPostPassProcessor() {
        return this.customPostPassProcessor;
    }

    public void setCustomPostPassProcessor(CustomPostPassProcessor customPostPassProcessor) {
        this.customPostPassProcessor = customPostPassProcessor;
        for (PostPass pass : this.passes) {
            pass.setCustomPostPassProcessor(customPostPassProcessor);
        }
    }

    public String toString() {
        return this.name;
    }

    private void updateProjectionMatrix() {
        this.projectionMatrix = this.postProjectionMatrix.getCached(this.destinationTarget.width(), this.destinationTarget.height());
    }

    PostPass addPass(String passName, PostPassRenderTarget sourceTarget, PostPassRenderTarget destinationTarget, PostPassEffectHolder effectHolder) {
        PostPass pass = new PostPass(this, passName, sourceTarget, destinationTarget, effectHolder);
        this.passes.add(pass);
        return pass;
    }

    @Nullable
    public RenderTarget getRenderTarget(@Nullable String name) {
        return this.renderTargetRegistry.getRenderTarget(name, this.destinationTarget);
    }

    PostProcessorRenderTargetRegistry getRenderTargetRegistry() {
        return this.renderTargetRegistry;
    }

    void requestProjectionMatrix() {
        updateProjectionMatrix();
        for (PostPass pass : this.passes) {
            pass.setProjectionMatrix(this.projectionMatrix);
        }
    }

    void resize() {
        Window window = Laby.labyAPI().minecraft().minecraftWindow();
        resize(window.getRawWidth(), window.getRawHeight());
    }
}
