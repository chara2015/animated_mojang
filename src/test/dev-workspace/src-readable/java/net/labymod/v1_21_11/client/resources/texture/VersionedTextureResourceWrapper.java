package net.labymod.v1_21_11.client.resources.texture;

import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.platform.NativeImage;
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
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/resources/texture/VersionedTextureResourceWrapper.class */
public class VersionedTextureResourceWrapper extends TextureResourceWrapper<AbstractTexture> {
    public VersionedTextureResourceWrapper(AbstractTexture minecraftTexture) {
        super(minecraftTexture);
    }

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

    public DeviceTexture deviceTexture() {
        AbstractTexture texture = (AbstractTexture) getDelegate();
        GlTexture texture2 = texture.getTexture();
        if (!(texture2 instanceof GlTexture)) {
            throw new IllegalStateException("Texture net.minecraft.core.BlockPos not an instance of GlTexture");
        }
        GlTexture glTexture = texture2;
        ForeignDeviceTextureRegistry registry = Laby.references().laby3D().foreignDeviceTextureRegistry();
        ForeignDeviceTexture deviceTexture = registry.get(Integer.valueOf(glTexture.glId()));
        SamplerDescription samplerDescription = MinecraftUtil.fromMinecraft(texture.getSampler());
        return DeferredDeviceTexture.createDeferredTexture(glTexture.glId(), deviceTexture, samplerDescription, this::onFlush);
    }

    public DeviceTextureView deviceTextureView() {
        return UnknownGlDeviceTextureView.ofUnknown(deviceTexture());
    }

    private void onFlush() {
        MinecraftUtil.applySamplerObjectWorkaround();
    }
}

