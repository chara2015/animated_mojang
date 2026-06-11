package net.labymod.v1_21_5.laby3d.pipeline.target;

import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import net.labymod.api.laby3d.textures.opengl.ExternalGlDeviceTextureView;
import net.labymod.api.laby3d.util.FboWriteTracker;
import net.labymod.api.util.CastUtil;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/laby3d/pipeline/target/VersionedRenderTarget.class */
public class VersionedRenderTarget implements RenderTarget, GlResource {
    private final fjr wrapped;

    public VersionedRenderTarget(fjr wrapped) {
        this.wrapped = wrapped;
    }

    public void clear(boolean unbindTarget) {
        CommandEncoder encoder = RenderSystem.getDevice().createCommandEncoder();
        GpuTexture colorTexture = this.wrapped.c();
        if (colorTexture != null) {
            encoder.clearColorTexture(colorTexture, 0);
            FboWriteTracker.INSTANCE.getOrCreateState(getId()).written = false;
        }
        GpuTexture depthTexture = this.wrapped.d();
        if (depthTexture != null) {
            encoder.clearDepthTexture(depthTexture, 1.0d);
        }
    }

    public void resize(int newWidth, int newHeight) {
        this.wrapped.a(newWidth, newHeight);
    }

    public int width() {
        return this.wrapped.c;
    }

    public int height() {
        return this.wrapped.d;
    }

    @Nullable
    public DeviceTextureView findColorTexture(int index) {
        GpuTexture colorTexture = this.wrapped.c();
        if (colorTexture == null) {
            return null;
        }
        fjl glTexture = (fjl) CastUtil.requireInstanceOf(colorTexture, fjl.class);
        return new ExternalGlDeviceTextureView(glTexture.b(), glTexture.getWidth(0), glTexture.getHeight(0));
    }

    @Nullable
    public DeviceTextureView findDepthTexture() {
        GpuTexture depthTexture = this.wrapped.d();
        if (depthTexture == null) {
            return null;
        }
        fjl glTexture = (fjl) CastUtil.requireInstanceOf(depthTexture, fjl.class);
        return new ExternalGlDeviceTextureView(glTexture.b(), glTexture.getWidth(0), glTexture.getHeight(0));
    }

    public boolean isClosed() {
        return false;
    }

    public void close() {
        this.wrapped.a();
    }

    @NotNull
    public String label() {
        return "VersionedRT(" + getId() + ")";
    }

    public int getId() {
        fjg device = RenderSystem.getDevice();
        int fboId = this.wrapped.c().a(device.b(), this.wrapped.d());
        return fboId;
    }
}
