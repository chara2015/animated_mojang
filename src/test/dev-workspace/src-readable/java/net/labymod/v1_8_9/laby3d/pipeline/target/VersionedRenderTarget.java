package net.labymod.v1_8_9.laby3d.pipeline.target;

import net.labymod.api.laby3d.textures.opengl.ExternalGlDeviceTextureView;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/laby3d/pipeline/target/VersionedRenderTarget.class */
public class VersionedRenderTarget implements RenderTarget, GlResource {
    private final bfw wrapped;

    public VersionedRenderTarget(bfw wrapped) {
        this.wrapped = wrapped;
    }

    public void clear(boolean unbindTarget) {
        this.wrapped.f();
        if (!unbindTarget) {
            this.wrapped.a(true);
        }
    }

    public void resize(int newWidth, int newHeight) {
        if (this.wrapped.f >= 0) {
            this.wrapped.a();
        }
        this.wrapped.b(newWidth, newHeight);
    }

    public int width() {
        return this.wrapped.c;
    }

    public int height() {
        return this.wrapped.d;
    }

    @Nullable
    public DeviceTextureView findColorTexture(int index) {
        return new ExternalGlDeviceTextureView(this.wrapped.g, width(), height());
    }

    @Nullable
    public DeviceTextureView findDepthTexture() {
        return new ExternalGlDeviceTextureView(this.wrapped.h, width(), height());
    }

    public boolean isClosed() {
        return this.wrapped.f <= 0;
    }

    public void close() {
        this.wrapped.a();
    }

    @NotNull
    public String label() {
        return "VersionedRT(" + getId() + ")";
    }

    public int getId() {
        return this.wrapped.f;
    }
}
