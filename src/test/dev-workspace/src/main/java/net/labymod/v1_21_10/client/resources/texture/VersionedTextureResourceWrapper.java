package net.labymod.v1_21_10.client.resources.texture;

import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.TextureResourceWrapper;
import net.labymod.api.laby3d.textures.opengl.DeferredDeviceTexture;
import net.labymod.api.laby3d.textures.opengl.UnknownGlDeviceTextureView;
import net.labymod.api.thirdparty.LabySentry;
import net.labymod.laby3d.api.RenderDeviceException;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/resources/texture/VersionedTextureResourceWrapper.class */
public class VersionedTextureResourceWrapper extends TextureResourceWrapper<iby> {
    public VersionedTextureResourceWrapper(iby minecraftTexture) {
        super(minecraftTexture);
    }

    @Override // net.labymod.api.client.resources.texture.TextureResourceWrapper
    public GameImage getImage() {
        icb icbVar = (iby) getDelegate();
        GameImage image = null;
        if (icbVar instanceof icb) {
            icb dynamicTexture = icbVar;
            fsy pixels = dynamicTexture.d();
            image = pixels == null ? null : new NativeGameImage(pixels);
        }
        return image;
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTexture deviceTexture() {
        iby texture = getDelegate();
        fsd fsdVarA = texture.a();
        if (!(fsdVarA instanceof fsd)) {
            throw new IllegalStateException("Texture is not an instance of GlTexture");
        }
        fsd glTexture = fsdVarA;
        return DeferredDeviceTexture.createDeferredTexture(glTexture.a(), () -> {
            onFlush(texture);
        });
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTextureView deviceTextureView() {
        return UnknownGlDeviceTextureView.ofUnknown(deviceTexture());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.labymod.laby3d.api.RenderDeviceException */
    private void onFlush(iby texture) throws RenderDeviceException {
        try {
            fsd fsdVarA = texture.a();
            if (fsdVarA instanceof fsd) {
                fsd glTexture = fsdVarA;
                glTexture.a(GlConst.GL_TEXTURE_2D);
            }
        } catch (Throwable throwable) {
            String name = texture.getClass().getName();
            StringBuilder bobTheCrashBuilder = new StringBuilder();
            bobTheCrashBuilder.append("Unable to get texture. (").append(name).append(")");
            if (texture instanceof icf) {
                icf reloadable = (icf) texture;
                bobTheCrashBuilder.append("\n");
                bobTheCrashBuilder.append("ResourceId: ").append(reloadable.c());
            }
            RenderDeviceException exception = new RenderDeviceException(bobTheCrashBuilder.toString(), throwable);
            LabySentry.capture(exception);
            throw exception;
        }
    }
}
