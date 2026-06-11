package net.labymod.api.laby3d.textures.opengl;

import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/textures/opengl/ExternalGlDeviceTexture.class */
public class ExternalGlDeviceTexture implements DeviceTexture, GlResource {
    private final int id;
    private final int width;
    private final int height;

    public ExternalGlDeviceTexture(int id, int width, int height) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public int getId() {
        return this.id;
    }

    @NotNull
    public SamplerDescription samplerDescription() {
        throw new UnsupportedOperationException("Unable to get the sampler description of an external texture");
    }

    @NotNull
    public DeviceTexture.Format format() {
        throw new UnsupportedOperationException("Unable to get the format of an external texture");
    }

    @NotNull
    public DeviceTexture.Dimension dimension() {
        return DeviceTexture.Dimension.TEXTURE_2D;
    }

    public int width(int level) {
        return this.width >> level;
    }

    public int height(int level) {
        return this.height >> level;
    }

    public int layers() {
        return 1;
    }

    public boolean isClosed() {
        return false;
    }

    public void close() {
    }

    @NotNull
    public String label() {
        return "ExternalGlDeviceTexture(" + this.id + ")";
    }
}
