package net.labymod.api.client.resources.texture;

import java.io.IOException;
import java.net.URI;
import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/TextureLoader.class */
public interface TextureLoader {
    boolean canLoad(URI uri);

    void loadTexture(URI uri, @Nullable ResourceLocation resourceLocation, CompletableTextureImage completableTextureImage) throws IOException;
}
