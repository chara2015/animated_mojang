package net.labymod.api.client.gfx.pipeline.texture.atlas;

import net.labymod.api.client.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/texture/atlas/TextureAtlas.class */
public interface TextureAtlas {
    void register(ResourceLocation resourceLocation, TextureSprite textureSprite);

    TextureSprite findSprite(ResourceLocation resourceLocation);

    ResourceLocation resource();

    int getAtlasWidth();

    int getAtlasHeight();

    @Deprecated(forRemoval = true, since = "4.0.6")
    @Nullable
    default TextureSprite getSprite(ResourceLocation location) {
        return findSprite(location);
    }
}
