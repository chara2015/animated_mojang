package net.labymod.v1_21_11.client.resources.texture;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.TextureResourceWrapper;
import net.labymod.api.laby3d.foreign.texture.ForeignDeviceTexture;
import net.labymod.api.laby3d.foreign.texture.ForeignDeviceTextureRegistry;
import net.labymod.api.laby3d.textures.opengl.DeferredDeviceTexture;
import net.labymod.api.laby3d.textures.opengl.UnknownGlDeviceTextureView;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.laby3d.api.textures.SamplerDescription;
import net.labymod.v1_21_11.client.util.MinecraftUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/resources/texture/VersionedTextureResourceWrapper.class */
public class VersionedTextureResourceWrapper extends TextureResourceWrapper<ikz> {
    public VersionedTextureResourceWrapper(ikz minecraftTexture) {
        super(minecraftTexture);
    }

    @Override // net.labymod.api.client.resources.texture.TextureResourceWrapper
    public GameImage getImage() {
        ilc ilcVar = (ikz) getDelegate();
        GameImage image = null;
        if (ilcVar instanceof ilc) {
            ilc dynamicTexture = ilcVar;
            fyh pixels = dynamicTexture.e();
            image = pixels == null ? null : new NativeGameImage(pixels);
        }
        return image;
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTexture deviceTexture() {
        ikz texture = getDelegate();
        fxl fxlVarA = texture.a();
        if (!(fxlVarA instanceof fxl)) {
            throw new IllegalStateException("Texture is not an instance of GlTexture");
        }
        fxl glTexture = fxlVarA;
        ForeignDeviceTextureRegistry registry = Laby.references().laby3D().foreignDeviceTextureRegistry();
        ForeignDeviceTexture deviceTexture = registry.get(Integer.valueOf(glTexture.a()));
        SamplerDescription samplerDescription = MinecraftUtil.fromMinecraft(texture.c());
        return DeferredDeviceTexture.createDeferredTexture(glTexture.a(), deviceTexture, samplerDescription, this::onFlush);
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTextureView deviceTextureView() {
        return UnknownGlDeviceTextureView.ofUnknown(deviceTexture());
    }

    private void onFlush() {
        MinecraftUtil.applySamplerObjectWorkaround();
    }
}
