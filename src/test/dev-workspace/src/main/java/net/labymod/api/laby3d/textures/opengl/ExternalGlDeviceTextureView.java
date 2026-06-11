package net.labymod.api.laby3d.textures.opengl;

import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/textures/opengl/ExternalGlDeviceTextureView.class */
public class ExternalGlDeviceTextureView implements DeviceTextureView {
    private final DeviceTexture texture;

    public ExternalGlDeviceTextureView(int id, int width, int height) {
        this(new ExternalGlDeviceTexture(id, width, height));
    }

    public ExternalGlDeviceTextureView(DeviceTexture texture) {
        this.texture = texture;
    }

    @NotNull
    public DeviceTexture texture() {
        return this.texture;
    }

    public int startLevel() {
        return 0;
    }

    public int mipLevels() {
        throw new UnsupportedOperationException("Unable to get the mip levels of an external texture view");
    }

    public boolean isClosed() {
        return false;
    }

    public void close() {
    }
}
