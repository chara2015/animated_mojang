package net.labymod.api.client.gfx.pipeline.texture.atlas;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/texture/atlas/AtlasRegistry.class */
@Referenceable
public interface AtlasRegistry {
    void register(ResourceLocation resourceLocation, TextureAtlas textureAtlas);

    @NotNull
    TextureAtlas getAtlas(ResourceLocation resourceLocation);

    default void register(TextureAtlas atlas) {
        register(atlas.resource(), atlas);
    }
}
