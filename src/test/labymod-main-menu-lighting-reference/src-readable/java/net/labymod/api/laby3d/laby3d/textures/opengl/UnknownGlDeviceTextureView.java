package net.labymod.api.laby3d.textures.opengl;

import java.util.Objects;
import java.util.function.Function;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/textures/opengl/UnknownGlDeviceTextureView.class */
public final class UnknownGlDeviceTextureView implements DeviceTextureView {
    private static final Function<DeviceTexture, DeviceTextureView> FACTORY = UnknownGlDeviceTextureView::new;
    private final DeviceTexture texture;

    private UnknownGlDeviceTextureView(DeviceTexture texture) {
        this.texture = texture;
    }

    @NotNull
    public static DeviceTextureView ofUnknown(int textureId) {
        return FACTORY.apply(DeferredDeviceTexture.createDeferredTexture(textureId));
    }

    @NotNull
    public static DeviceTextureView ofUnknown(@NotNull DeviceTexture texture) {
        return FACTORY.apply(texture);
    }

    @NotNull
    public DeviceTexture texture() {
        return this.texture;
    }

    public int startLevel() {
        return 0;
    }

    public int mipLevels() {
        return this.texture.samplerDescription().mipLevels();
    }

    public boolean isClosed() {
        return this.texture.isClosed();
    }

    public void close() {
    }

    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UnknownGlDeviceTextureView that = (UnknownGlDeviceTextureView) o;
        return Objects.equals(this.texture, that.texture);
    }

    public int hashCode() {
        return Objects.hashCode(this.texture);
    }
}
