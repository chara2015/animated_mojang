package net.labymod.v26_1_2.client.resources.texture;

import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.platform.NativeImage;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.resources.texture.TextureResourceWrapper;
import net.labymod.api.laby3d.textures.opengl.DeferredDeviceTexture;
import net.labymod.api.laby3d.textures.opengl.UnknownGlDeviceTextureView;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import net.labymod.v26_1_2.client.util.MinecraftUtil;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/client/resources/texture/VersionedTextureResourceWrapper.class */
public class VersionedTextureResourceWrapper extends TextureResourceWrapper<AbstractTexture> {
    public VersionedTextureResourceWrapper(AbstractTexture minecraftTexture) {
        super(minecraftTexture);
    }

    @Override // net.labymod.api.client.resources.texture.TextureResourceWrapper
    public GameImage getImage() {
        DynamicTexture dynamicTexture = (AbstractTexture) getDelegate();
        GameImage image = null;
        if (dynamicTexture instanceof DynamicTexture) {
            DynamicTexture dynamicTexture2 = dynamicTexture;
            NativeImage pixels = dynamicTexture2.getPixels();
            image = pixels == null ? null : new NativeGameImage(pixels);
        }
        return image;
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTexture deviceTexture() {
        AbstractTexture texture = getDelegate();
        GlTexture texture2 = texture.getTexture();
        if (!(texture2 instanceof GlTexture)) {
            throw new IllegalStateException("Texture is not an instance of GlTexture");
        }
        GlTexture glTexture = texture2;
        return DeferredDeviceTexture.createDeferredTexture(glTexture.glId(), this::onFlush);
    }

    @Override // net.labymod.api.client.resources.texture.Texture
    public DeviceTextureView deviceTextureView() {
        return UnknownGlDeviceTextureView.ofUnknown(deviceTexture());
    }

    private void onFlush() {
        MinecraftUtil.applySamplerObjectWorkaround();
    }
}
