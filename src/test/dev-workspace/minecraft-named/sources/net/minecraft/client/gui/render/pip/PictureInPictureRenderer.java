package net.minecraft.client.gui.render.pip;

import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.gui.render.state.BlitRenderState;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.gui.render.state.pip.PictureInPictureRenderState;
import net.minecraft.client.renderer.CachedOrthoProjectionMatrixBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/render/pip/PictureInPictureRenderer.class */
public abstract class PictureInPictureRenderer<T extends PictureInPictureRenderState> implements AutoCloseable {
    protected final MultiBufferSource.BufferSource bufferSource;
    private GpuTexture texture;
    private GpuTextureView textureView;
    private GpuTexture depthTexture;
    private GpuTextureView depthTextureView;
    private final CachedOrthoProjectionMatrixBuffer projectionMatrixBuffer = new CachedOrthoProjectionMatrixBuffer("PIP - " + getClass().getSimpleName(), -1000.0f, 1000.0f, true);

    public abstract Class<T> getRenderStateClass();

    protected abstract void renderToTexture(T t, PoseStack poseStack);

    protected abstract String getTextureLabel();

    protected PictureInPictureRenderer(MultiBufferSource.BufferSource $$0) {
        this.bufferSource = $$0;
    }

    public void prepare(T $$0, GuiRenderState $$1, int $$2) {
        int $$3 = ($$0.x1() - $$0.x0()) * $$2;
        int $$4 = ($$0.y1() - $$0.y0()) * $$2;
        boolean $$5 = (this.texture != null && this.texture.getWidth(0) == $$3 && this.texture.getHeight(0) == $$4) ? false : true;
        if (!$$5 && textureIsReadyToBlit($$0)) {
            blitTexture($$0, $$1);
            return;
        }
        prepareTexturesAndProjection($$5, $$3, $$4);
        RenderSystem.outputColorTextureOverride = this.textureView;
        RenderSystem.outputDepthTextureOverride = this.depthTextureView;
        PoseStack $$6 = new PoseStack();
        $$6.translate($$3 / 2.0f, getTranslateY($$4, $$2), 0.0f);
        float $$7 = $$2 * $$0.scale();
        $$6.scale($$7, $$7, -$$7);
        renderToTexture($$0, $$6);
        this.bufferSource.endBatch();
        RenderSystem.outputColorTextureOverride = null;
        RenderSystem.outputDepthTextureOverride = null;
        blitTexture($$0, $$1);
    }

    protected void blitTexture(T $$0, GuiRenderState $$1) {
        $$1.submitBlitToCurrentLayer(new BlitRenderState(RenderPipelines.GUI_TEXTURED_PREMULTIPLIED_ALPHA, TextureSetup.singleTexture(this.textureView, RenderSystem.getSamplerCache().getRepeat(FilterMode.NEAREST)), $$0.pose(), $$0.x0(), $$0.y0(), $$0.x1(), $$0.y1(), 0.0f, 1.0f, 1.0f, 0.0f, -1, $$0.scissorArea(), null));
    }

    private void prepareTexturesAndProjection(boolean $$0, int $$1, int $$2) {
        if (this.texture != null && $$0) {
            this.texture.close();
            this.texture = null;
            this.textureView.close();
            this.textureView = null;
            this.depthTexture.close();
            this.depthTexture = null;
            this.depthTextureView.close();
            this.depthTextureView = null;
        }
        GpuDevice $$3 = RenderSystem.getDevice();
        if (this.texture == null) {
            this.texture = $$3.createTexture(() -> {
                return "UI " + getTextureLabel() + " texture";
            }, 12, TextureFormat.RGBA8, $$1, $$2, 1, 1);
            this.textureView = $$3.createTextureView(this.texture);
            this.depthTexture = $$3.createTexture(() -> {
                return "UI " + getTextureLabel() + " depth texture";
            }, 8, TextureFormat.DEPTH32, $$1, $$2, 1, 1);
            this.depthTextureView = $$3.createTextureView(this.depthTexture);
        }
        $$3.createCommandEncoder().clearColorAndDepthTextures(this.texture, 0, this.depthTexture, 1.0d);
        RenderSystem.setProjectionMatrix(this.projectionMatrixBuffer.getBuffer($$1, $$2), ProjectionType.ORTHOGRAPHIC);
    }

    protected boolean textureIsReadyToBlit(T $$0) {
        return false;
    }

    protected float getTranslateY(int $$0, int $$1) {
        return $$0;
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (this.texture != null) {
            this.texture.close();
        }
        if (this.textureView != null) {
            this.textureView.close();
        }
        if (this.depthTexture != null) {
            this.depthTexture.close();
        }
        if (this.depthTextureView != null) {
            this.depthTextureView.close();
        }
        this.projectionMatrixBuffer.close();
    }
}
