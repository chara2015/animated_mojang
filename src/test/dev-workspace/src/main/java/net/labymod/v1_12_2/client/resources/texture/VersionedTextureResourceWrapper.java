package net.labymod.v1_12_2.client.resources.texture;

import java.awt.image.BufferedImage;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.TextureResourceWrapper;
import net.labymod.api.laby3d.textures.opengl.DeferredDeviceTexture;
import net.labymod.api.laby3d.textures.opengl.UnknownGlDeviceTextureView;
import net.labymod.core.client.resources.BufferedGameImage;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/resources/texture/VersionedTextureResourceWrapper.class */
public class VersionedTextureResourceWrapper extends TextureResourceWrapper<cds> {
    public VersionedTextureResourceWrapper(cds minecraftTexture) {
        super(minecraftTexture);
    }

    @Override // net.labymod.api.client.resources.texture.TextureResourceWrapper
    public GameImage getImage() {
        HttpTextureAccessor httpTextureAccessor = (cds) getDelegate();
        GameImage image = null;
        if (httpTextureAccessor instanceof HttpTextureAccessor) {
            HttpTextureAccessor accessor = httpTextureAccessor;
            BufferedImage pixels = accessor.getImage();
            image = pixels == null ? null : new BufferedGameImage(pixels);
        }
        return image;
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTexture deviceTexture() {
        return DeferredDeviceTexture.createDeferredTexture(getDelegate().b());
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTextureView deviceTextureView() {
        return UnknownGlDeviceTextureView.ofUnknown(deviceTexture());
    }
}
