package net.labymod.v1_21_5.client.resources.texture;

import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.TextureResourceWrapper;
import net.labymod.api.laby3d.textures.opengl.DeferredDeviceTexture;
import net.labymod.api.laby3d.textures.opengl.UnknownGlDeviceTextureView;
import net.labymod.api.thirdparty.LabySentry;
import net.labymod.laby3d.api.RenderDeviceException;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/resources/texture/VersionedTextureResourceWrapper.class */
public class VersionedTextureResourceWrapper extends TextureResourceWrapper<hkb> {
    public VersionedTextureResourceWrapper(hkb minecraftTexture) {
        super(minecraftTexture);
    }

    @Override // net.labymod.api.client.resources.texture.TextureResourceWrapper
    public GameImage getImage() {
        hkd hkdVar = (hkb) getDelegate();
        GameImage image = null;
        if (hkdVar instanceof hkd) {
            hkd dynamicTexture = hkdVar;
            fkf pixels = dynamicTexture.c();
            image = pixels == null ? null : new NativeGameImage(pixels);
        }
        return image;
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTexture deviceTexture() {
        hkb texture = getDelegate();
        fjl fjlVarA = texture.a();
        if (!(fjlVarA instanceof fjl)) {
            throw new IllegalStateException("Texture is not an instance of GlTexture");
        }
        fjl glTexture = fjlVarA;
        return DeferredDeviceTexture.createDeferredTexture(glTexture.b(), () -> {
            onFlush(texture);
        });
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTextureView deviceTextureView() {
        return UnknownGlDeviceTextureView.ofUnknown(deviceTexture());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    private void onFlush(hkb texture) throws RenderDeviceException {
        try {
            fjl fjlVarA = texture.a();
            if (fjlVarA instanceof fjl) {
                fjl glTexture = fjlVarA;
                glTexture.a();
            }
        } catch (Throwable throwable) {
            String name = texture.getClass().getName();
            StringBuilder bobTheCrashBuilder = new StringBuilder();
            bobTheCrashBuilder.append("Unable to get texture. (").append(name).append(")");
            if (texture instanceof hkh) {
                hkh reloadable = (hkh) texture;
                bobTheCrashBuilder.append("\n");
                bobTheCrashBuilder.append("ResourceId: ").append(reloadable.b());
            }
            RenderDeviceException exception = new RenderDeviceException(bobTheCrashBuilder.toString(), throwable);
            LabySentry.capture(exception);
            throw exception;
        }
    }
}
