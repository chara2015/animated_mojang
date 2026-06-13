package net.labymod.api.laby3d.foreign.texture.opengl;

import net.labymod.api.laby3d.foreign.texture.ForeignDeviceTexture;
import net.labymod.laby3d.api.opengl.GlResource;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/laby3d/foreign/texture/opengl/GlForeignDeviceTexture.class */
public class GlForeignDeviceTexture extends ForeignDeviceTexture implements GlResource {
    private final int id;

    public GlForeignDeviceTexture(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    @Override // net.labymod.api.laby3d.foreign.texture.ForeignDeviceTexture
    public void setSamplerDescription(SamplerDescription samplerDescription) {
        super.setSamplerDescription(samplerDescription);
    }

    @Override // net.labymod.api.laby3d.foreign.texture.ForeignDeviceTexture
    public void setFormat(DeviceTexture.Format format) {
        super.setFormat(format);
    }

    @Override // net.labymod.api.laby3d.foreign.texture.ForeignDeviceTexture
    public void setDimension(DeviceTexture.Dimension dimension) {
        super.setDimension(dimension);
    }

    @Override // net.labymod.api.laby3d.foreign.texture.ForeignDeviceTexture
    public void setWidth(int width) {
        super.setWidth(width);
    }

    @Override // net.labymod.api.laby3d.foreign.texture.ForeignDeviceTexture
    public void setHeight(int height) {
        super.setHeight(height);
    }

    @Override // net.labymod.api.laby3d.foreign.texture.ForeignDeviceTexture
    public void setLayers(int layers) {
        super.setLayers(layers);
    }
}
