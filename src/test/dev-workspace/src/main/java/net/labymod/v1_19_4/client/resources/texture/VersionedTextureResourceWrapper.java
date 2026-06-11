package net.labymod.v1_19_4.client.resources.texture;

import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.TextureResourceWrapper;
import net.labymod.api.laby3d.textures.opengl.DeferredDeviceTexture;
import net.labymod.api.laby3d.textures.opengl.UnknownGlDeviceTextureView;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/resources/texture/VersionedTextureResourceWrapper.class */
public class VersionedTextureResourceWrapper extends TextureResourceWrapper<fsn> {
    public VersionedTextureResourceWrapper(fsn minecraftTexture) {
        super(minecraftTexture);
    }

    @Override // net.labymod.api.client.resources.texture.TextureResourceWrapper
    public GameImage getImage() {
        fsp fspVar = (fsn) getDelegate();
        GameImage image = null;
        if (fspVar instanceof fsp) {
            fsp dynamicTexture = fspVar;
            egf pixels = dynamicTexture.e();
            image = pixels == null ? null : new NativeGameImage(pixels);
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
