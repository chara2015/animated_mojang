package com.mojang.blaze3d;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.pipeline.RenderTarget;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.jtracy.TracyClient;
import java.util.OptionalInt;
import net.minecraft.client.renderer.RenderPipelines;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/TracyFrameCapture.class */
public class TracyFrameCapture implements AutoCloseable {
    private static final int MAX_WIDTH = 320;
    private static final int MAX_HEIGHT = 180;
    private static final long BYTES_PER_PIXEL = 4;
    private int targetWidth;
    private int targetHeight;
    private GpuTexture frameBuffer;
    private GpuTextureView frameBufferView;
    private GpuBuffer pixelbuffer;
    private int lastCaptureDelay;
    private boolean capturedThisFrame;
    private Status status = Status.WAITING_FOR_CAPTURE;
    private int width = 320;
    private int height = MAX_HEIGHT;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/blaze3d/TracyFrameCapture$Status.class */
    enum Status {
        WAITING_FOR_CAPTURE,
        WAITING_FOR_COPY,
        WAITING_FOR_UPLOAD
    }

    public TracyFrameCapture() {
        GpuDevice $$0 = RenderSystem.getDevice();
        this.frameBuffer = $$0.createTexture("Tracy Frame Capture", 10, TextureFormat.RGBA8, this.width, this.height, 1, 1);
        this.frameBufferView = $$0.createTextureView(this.frameBuffer);
        this.pixelbuffer = $$0.createBuffer(() -> {
            return "Tracy Frame Capture buffer";
        }, 9, ((long) (this.width * this.height)) * BYTES_PER_PIXEL);
    }

    private void resize(int $$0, int $$1) {
        float $$2 = $$0 / $$1;
        if ($$0 > 320) {
            $$0 = 320;
            $$1 = (int) (320.0f / $$2);
        }
        if ($$1 > MAX_HEIGHT) {
            $$0 = (int) (180.0f * $$2);
            $$1 = MAX_HEIGHT;
        }
        int $$02 = ($$0 / 4) * 4;
        int $$12 = ($$1 / 4) * 4;
        if (this.width != $$02 || this.height != $$12) {
            this.width = $$02;
            this.height = $$12;
            GpuDevice $$3 = RenderSystem.getDevice();
            this.frameBuffer.close();
            this.frameBuffer = $$3.createTexture("Tracy Frame Capture", 10, TextureFormat.RGBA8, $$02, $$12, 1, 1);
            this.frameBufferView.close();
            this.frameBufferView = $$3.createTextureView(this.frameBuffer);
            this.pixelbuffer.close();
            this.pixelbuffer = $$3.createBuffer(() -> {
                return "Tracy Frame Capture buffer";
            }, 9, ((long) ($$02 * $$12)) * BYTES_PER_PIXEL);
        }
    }

    public void capture(RenderTarget $$0) {
        if (this.status != Status.WAITING_FOR_CAPTURE || this.capturedThisFrame || $$0.getColorTexture() == null) {
            return;
        }
        this.capturedThisFrame = true;
        if ($$0.width != this.targetWidth || $$0.height != this.targetHeight) {
            this.targetWidth = $$0.width;
            this.targetHeight = $$0.height;
            resize(this.targetWidth, this.targetHeight);
        }
        this.status = Status.WAITING_FOR_COPY;
        CommandEncoder $$1 = RenderSystem.getDevice().createCommandEncoder();
        RenderPass $$2 = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> {
            return "Tracy blit";
        }, this.frameBufferView, OptionalInt.empty());
        try {
            $$2.setPipeline(RenderPipelines.TRACY_BLIT);
            $$2.bindTexture("InSampler", $$0.getColorTextureView(), RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR));
            $$2.draw(0, 3);
            if ($$2 != null) {
                $$2.close();
            }
            $$1.copyTextureToBuffer(this.frameBuffer, this.pixelbuffer, 0L, () -> {
                this.status = Status.WAITING_FOR_UPLOAD;
            }, 0);
            this.lastCaptureDelay = 0;
        } catch (Throwable th) {
            if ($$2 != null) {
                try {
                    $$2.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void upload() {
        if (this.status != Status.WAITING_FOR_UPLOAD) {
            return;
        }
        this.status = Status.WAITING_FOR_CAPTURE;
        GpuBuffer.MappedView $$0 = RenderSystem.getDevice().createCommandEncoder().mapBuffer(this.pixelbuffer, true, false);
        try {
            TracyClient.frameImage($$0.data(), this.width, this.height, this.lastCaptureDelay, true);
            if ($$0 != null) {
                $$0.close();
            }
        } catch (Throwable th) {
            if ($$0 != null) {
                try {
                    $$0.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public void endFrame() {
        this.lastCaptureDelay++;
        this.capturedThisFrame = false;
        TracyClient.markFrame();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        this.frameBuffer.close();
        this.frameBufferView.close();
        this.pixelbuffer.close();
    }
}
