package net.labymod.v1_20_4.client.gfx.pipeline.texture.atlas;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.texture.atlas.MinecraftAtlases;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.models.Implements;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/gfx/pipeline/texture/atlas/VersionedMinecraftAtlases.class */
@Singleton
@Implements(MinecraftAtlases.class)
public class VersionedMinecraftAtlases implements MinecraftAtlases {
    @Inject
    public VersionedMinecraftAtlases() {
    }

    @Override // net.labymod.api.client.gfx.pipeline.texture.atlas.MinecraftAtlases
    public TextureAtlas getGuiAtlas() {
        TextureAtlasHolderAccessor accessor = evi.O().aF();
        return accessor.getTextureAtlas();
    }
}
