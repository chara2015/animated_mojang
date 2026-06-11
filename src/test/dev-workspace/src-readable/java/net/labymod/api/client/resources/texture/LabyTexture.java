package net.labymod.api.client.resources.texture;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/LabyTexture.class */
public abstract class LabyTexture implements Texture, AutoCloseable {
    protected final Laby3D laby3D = Laby.references().laby3D();
    protected DeviceTexture texture;
    protected DeviceTextureView textureView;
    private final ResourceLocation textureLocation;

    public abstract GameImage getImage();

    protected LabyTexture(ResourceLocation textureLocation) {
        this.textureLocation = textureLocation;
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTexture deviceTexture() {
        if (this.texture == null) {
            throw new IllegalStateException("Texture does not exists (" + String.valueOf(getTextureLocation()) + ")");
        }
        return this.texture;
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTextureView deviceTextureView() {
        if (this.textureView == null) {
            throw new IllegalStateException("Texture view does not exists (" + String.valueOf(getTextureLocation()) + ")");
        }
        return this.textureView;
    }

    public void release() {
        deviceTexture().close();
    }

    public void close() {
    }

    public void bindTo() {
        bindTo(getTextureLocation());
    }

    public void bindTo(TextureRepository.TextureRegistrationCallback callback) {
        bindTo(getTextureLocation(), callback);
    }

    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }
}
