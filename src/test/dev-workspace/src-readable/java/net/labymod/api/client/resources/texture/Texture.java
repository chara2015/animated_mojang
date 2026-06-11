package net.labymod.api.client.resources.texture;

import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.laby3d.api.textures.DeviceTexture;
import net.labymod.laby3d.api.textures.DeviceTextureView;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/Texture.class */
public interface Texture {
    DeviceTexture deviceTexture();

    DeviceTextureView deviceTextureView();

    default void bindTo(ResourceLocation location) {
        Laby.references().textureRepository().register(location, this);
    }

    default void bindTo(ResourceLocation location, TextureRepository.TextureRegistrationCallback callback) {
        Laby.references().textureRepository().register(location, this, callback);
    }
}
