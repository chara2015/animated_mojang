package net.labymod.v26_1.laby3d.pipeline.target;

import com.mojang.blaze3d.opengl.GlDevice;
import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.opengl.GlTextureView;
import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.labymod.api.laby3d.textures.opengl.ExternalGlDeviceTextureView;
import net.labymod.api.laby3d.util.FboWriteTracker;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.v26_1.laby3d.accessor.GpuDeviceAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/laby3d/pipeline/target/VersionedRenderTarget.class */
public class VersionedRenderTarget implements RenderTarget, GlResource {
    private final com.mojang.blaze3d.pipeline.RenderTarget wrapped;

    public VersionedRenderTarget(com.mojang.blaze3d.pipeline.RenderTarget wrapped) {
        this.wrapped = wrapped;
    }

    public void clear(boolean unbindTarget) {
        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        GpuTexture colorTexture = this.wrapped.getColorTexture();
        if (colorTexture != null) {
            encoder.clearColorTexture(colorTexture, 0);
            FboWriteTracker.INSTANCE.getOrCreateState(getId()).written = false;
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
        GpuTexture colorTexture = this.wrapped.getColorTexture();
        if (colorTexture == null) {
            return null;
        }
        GlTexture glTexture = (GlTexture) CastUtil.requireInstanceOf(colorTexture, GlTexture.class);
        return new ExternalGlDeviceTextureView(glTexture.glId(), glTexture.getWidth(0), glTexture.getHeight(0));
    }

    @Nullable
    public DeviceTextureView findDepthTexture() {
        GpuTexture depthTexture = this.wrapped.getDepthTexture();
        if (depthTexture == null) {
            return null;
        }
        GlTexture glTexture = (GlTexture) CastUtil.requireInstanceOf(depthTexture, GlTexture.class);
        return new ExternalGlDeviceTextureView(glTexture.glId(), glTexture.getWidth(0), glTexture.getHeight(0));
    }

    public boolean isClosed() {
        return false;
    }

    public void close() {
        this.wrapped.destroyBuffers();
    }

    @NotNull
    public String label() {
        return "VersionedRT(" + getId() + ")";
    }

    public int getId() {
        GpuDeviceAccessor accessor = GpuDeviceAccessor.self(RenderSystem.getDevice());
        GlDevice glDevice = (GlDevice) CastUtil.requireInstanceOf(accessor.labyMod$getBackend(), GlDevice.class);
        GpuTextureView fboColorTexture = this.wrapped.getColorTextureView();
        if (RenderSystem.outputColorTextureOverride != null) {
            fboColorTexture = RenderSystem.outputColorTextureOverride;
        }
        GpuTexture fboDepthTexture = this.wrapped.getDepthTexture();
        if (RenderSystem.outputDepthTextureOverride != null) {
            fboDepthTexture = RenderSystem.outputDepthTextureOverride.texture();
        }
        GlTextureView glTexture = (GlTextureView) CastUtil.requireInstanceOf(fboColorTexture, GlTextureView.class);
        return glTexture.getFbo(glDevice.directStateAccess(), fboDepthTexture);
    }
}
