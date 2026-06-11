package net.labymod.api.client.gfx.shader;

import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.Texture;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/shader/ShaderTextures.class */
public final class ShaderTextures {
    public static final int SIZE = 256;
    private static final TextureRepository TEXTURE_REPOSITORY = Laby.references().textureRepository();
    private static final DeviceTextureView[] SHADER_TEXTURES = new DeviceTextureView[256];

    public static void setShaderTexture(int slot, ResourceLocation location) {
        Objects.requireNonNull(location, "The location cannot be null.");
        Texture texture = TEXTURE_REPOSITORY.getOrCreateTexture(location);
        setShaderTexture(slot, texture == null ? null : texture.deviceTextureView());
    }

    public static void setShaderTexture(int slot, DeviceTextureView texture) {
        if (slot < 0 || slot >= SHADER_TEXTURES.length) {
            return;
        }
        SHADER_TEXTURES[slot] = texture;
    }

    public static void clearShaderTexture(int slot) {
        if (slot < 0 || slot >= SHADER_TEXTURES.length) {
            return;
        }
        SHADER_TEXTURES[slot] = null;
    }

    public static DeviceTextureView getShaderTexture(int slot) {
        if (slot < 0 || slot >= SHADER_TEXTURES.length) {
            return null;
        }
        return SHADER_TEXTURES[slot];
    }
}
