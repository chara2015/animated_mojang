package net.labymod.core.client.resources.texture.loader;

import java.io.IOException;
import java.net.URI;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.CompletableTextureImage;
import net.labymod.api.client.resources.texture.TextureImage;
import net.labymod.api.client.resources.texture.TextureLoader;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/loader/ResourceTextureLoader.class */
public class ResourceTextureLoader implements TextureLoader {
    @Override // net.labymod.api.client.resources.texture.TextureLoader
    public boolean canLoad(URI uri) {
        return uri.getScheme().equals("resource");
    }

    @Override // net.labymod.api.client.resources.texture.TextureLoader
    public void loadTexture(URI uri, @Nullable ResourceLocation resourceLocation, CompletableTextureImage target) throws IOException {
        String[] parts = uri.getSchemeSpecificPart().split("://", 2);
        if (parts.length != 2) {
            target.stopLoadingOnError();
            return;
        }
        String namespace = parts[0];
        String path = parts[1];
        ResourceLocation location = ResourceLocation.create(namespace, path);
        target.executeCompletableListeners(new TextureImage(location));
    }
}
