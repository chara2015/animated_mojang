package net.labymod.v1_16_5.laby3d.pipeline.target;

import net.labymod.api.laby3d.textures.opengl.ExternalGlDeviceTextureView;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.pipeline.target.RenderTarget;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/laby3d/pipeline/target/VersionedRenderTarget.class */
public class VersionedRenderTarget implements RenderTarget, GlResource {
    private final deg wrapped;

    public VersionedRenderTarget(deg wrapped) {
        this.wrapped = wrapped;
    }

    public void clear(boolean unbindTarget) {
        this.wrapped.b(djz.a);
        if (!unbindTarget) {
            this.wrapped.a(true);
        }
    }

    public void resize(int newWidth, int newHeight) {
        this.wrapped.a(newWidth, newHeight, djz.a);
    }

    public int width() {
        return this.wrapped.a;
    }

    public int height() {
        return this.wrapped.b;
    }

    @Nullable
    public DeviceTextureView findColorTexture(int index) {
        return new ExternalGlDeviceTextureView(this.wrapped.f(), this.wrapped.a, this.wrapped.b);
    }

    @Nullable
    public DeviceTextureView findDepthTexture() {
        return new ExternalGlDeviceTextureView(this.wrapped.g(), this.wrapped.a, this.wrapped.b);
    }

    public boolean isClosed() {
        return this.wrapped.f == -1;
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
