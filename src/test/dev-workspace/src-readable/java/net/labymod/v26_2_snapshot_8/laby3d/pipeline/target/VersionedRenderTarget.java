package net.labymod.v26_2_snapshot_8.laby3d.pipeline.target;

import com.mojang.blaze3d.opengl.GlDevice;
import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.opengl.GlTextureView;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.util.List;
import net.labymod.api.laby3d.textures.opengl.ExternalGlDeviceTextureView;
import net.labymod.api.laby3d.util.FboWriteTracker;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.v26_2_snapshot_8.laby3d.accessor.GpuDeviceAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/laby3d/pipeline/target/VersionedRenderTarget.class */
public class VersionedRenderTarget implements RenderTarget, GlResource {
    private final com.mojang.blaze3d.pipeline.RenderTarget wrapped;

    public VersionedRenderTarget(com.mojang.blaze3d.pipeline.RenderTarget wrapped) {
        this.wrapped = wrapped;
    }

    public void clear(boolean unbindTarget) {
        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        GpuTexture colorTexture = this.wrapped.getColorTexture();
        if (colorTexture != null) {
            encoder.clearColorTexture(colorTexture, new Vector4f(0.0f, 0.0f, 0.0f, 0.0f));
            if (isGlBackend()) {
                FboWriteTracker.INSTANCE.getOrCreateState(getGlId()).written = false;
            }
        }
        GpuTexture depthTexture = this.wrapped.getDepthTexture();
        if (depthTexture != null) {
            encoder.clearDepthTexture(depthTexture, 1.0d);
        }
    }

    public void resize(int newWidth, int newHeight) {
        this.wrapped.resize(newWidth, newHeight);
    }

    public int width() {
        if (RenderSystem.outputColorTextureOverride != null) {
            return RenderSystem.outputColorTextureOverride.getWidth(0);
        }
        return this.wrapped.width;
    }

    public int height() {
        if (RenderSystem.outputColorTextureOverride != null) {
            return RenderSystem.outputColorTextureOverride.getHeight(0);
        }
        return this.wrapped.height;
    }

    @Nullable
    public DeviceTextureView findColorTexture(int index) {
        GlTexture colorTexture = this.wrapped.getColorTexture();
        if (colorTexture == null) {
            return null;
        }
        if (colorTexture instanceof GlTexture) {
            GlTexture glTexture = colorTexture;
            return new ExternalGlDeviceTextureView(glTexture.glId(), glTexture.getWidth(0), glTexture.getHeight(0));
        }
        GpuTextureView textureView = this.wrapped.getColorTextureView();
        if (RenderSystem.outputColorTextureOverride != null) {
            textureView = RenderSystem.outputColorTextureOverride;
        }
        return (DeviceTextureView) CastUtil.cast(textureView);
    }

    @Nullable
    public DeviceTextureView findDepthTexture() {
        GlTexture depthTexture = this.wrapped.getDepthTexture();
        if (depthTexture == null) {
            return null;
        }
        if (depthTexture instanceof GlTexture) {
            GlTexture glTexture = depthTexture;
            return new ExternalGlDeviceTextureView(glTexture.glId(), glTexture.getWidth(0), glTexture.getHeight(0));
        }
        GpuTextureView depthView = this.wrapped.getDepthTextureView();
        if (RenderSystem.outputDepthTextureOverride != null) {
            depthView = RenderSystem.outputDepthTextureOverride;
        }
        return (DeviceTextureView) CastUtil.cast(depthView);
    }

    public boolean isClosed() {
        return false;
    }

    public void close() {
        this.wrapped.destroyBuffers();
    }

    @NotNull
    public String label() {
        if (isGlBackend()) {
            return "VersionedRT(" + getGlId() + ")";
        }
        return "VersionedRT(vulkan)";
    }

    public com.mojang.blaze3d.pipeline.RenderTarget wrapped() {
        return this.wrapped;
    }

    public int getGlId() {
        GlDevice glDeviceLabyMod$getBackend = GpuDeviceAccessor.self(RenderSystem.getDevice()).labyMod$getBackend();
        if (!(glDeviceLabyMod$getBackend instanceof GlDevice)) {
            throw new IllegalStateException("getGlId() called on non-GL backend");
        }
        GlDevice glDevice = glDeviceLabyMod$getBackend;
        GpuTextureView fboColorTexture = this.wrapped.getColorTextureView();
        if (RenderSystem.outputColorTextureOverride != null) {
            fboColorTexture = RenderSystem.outputColorTextureOverride;
        }
        GpuTexture fboDepthTexture = this.wrapped.getDepthTexture();
        if (RenderSystem.outputDepthTextureOverride != null) {
            fboDepthTexture = RenderSystem.outputDepthTextureOverride.texture();
        }
        return glDevice.frameBufferCache().getFbo(glDevice.directStateAccess(), List.of((GlTextureView) fboColorTexture), (GlTexture) fboDepthTexture);
    }

    public boolean isGlBackend() {
        return GpuDeviceAccessor.self(RenderSystem.getDevice()).labyMod$getBackend() instanceof GlDevice;
    }

    public int getId() {
        return getGlId();
    }
}
