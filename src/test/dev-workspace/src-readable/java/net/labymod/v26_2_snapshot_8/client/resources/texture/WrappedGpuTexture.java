package net.labymod.v26_2_snapshot_8.client.resources.texture;

import com.mojang.blaze3d.vulkan.VulkanGpuTexture;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.SamplerDescription;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/resources/texture/WrappedGpuTexture.class */
public class WrappedGpuTexture implements DeviceTexture {
    private final VulkanGpuTexture delegate;

    public WrappedGpuTexture(VulkanGpuTexture delegate) {
        this.delegate = delegate;
    }

    public SamplerDescription samplerDescription() {
        return null;
    }

    public DeviceTexture.Format format() {
        return null;
    }

    public DeviceTexture.Dimension dimension() {
        return null;
    }

    public int width(int level) {
        return 0;
    }

    public int height(int level) {
        return 0;
    }

    public int layers() {
        return 0;
    }

    public boolean isClosed() {
        return false;
    }

    public void close() {
    }

    public String label() {
        return "";
    }
}
