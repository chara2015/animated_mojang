package net.labymod.api.laby3d.foreign.texture;

import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/foreign/texture/ForeignDeviceTexture.class */
public class ForeignDeviceTexture implements DeviceTexture {
    private SamplerDescription samplerDescription;
    private DeviceTexture.Format format;
    private DeviceTexture.Dimension dimension;
    private int width;
    private int height;
    private int layers = 1;
    private boolean closed;

    @NotNull
    public SamplerDescription samplerDescription() {
        return this.samplerDescription;
    }

    protected void setSamplerDescription(SamplerDescription samplerDescription) {
        this.samplerDescription = samplerDescription;
    }

    @NotNull
    public DeviceTexture.Format format() {
        return this.format;
    }

    protected void setFormat(DeviceTexture.Format format) {
        this.format = format;
    }

    @NotNull
    public DeviceTexture.Dimension dimension() {
        return this.dimension;
    }

    protected void setDimension(DeviceTexture.Dimension dimension) {
        this.dimension = dimension;
    }

    public int width(int level) {
        return this.width >> level;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    public int height(int level) {
        return this.height >> level;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    public int layers() {
        return this.layers;
    }

    protected void setLayers(int layers) {
        this.layers = layers;
    }

    public boolean isClosed() {
        return this.closed;
    }

    public void close() {
        this.closed = true;
    }

    @NotNull
    public String label() {
        return "ForeignDeviceTexture";
    }
}
