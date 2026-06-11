package net.labymod.v1_21_11.client.gfx.pipeline.texture.atlas;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.gfx.pipeline.texture.atlas.MinecraftAtlases;
import net.labymod.api.client.gfx.pipeline.texture.atlas.TextureAtlas;
import net.labymod.api.models.Implements;
import net.minecraft.client.Minecraft;
import net.minecraft.data.AtlasIds;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/gfx/pipeline/texture/atlas/VersionedMinecraftAtlases.class */
@Singleton
@Implements(MinecraftAtlases.class)
public class VersionedMinecraftAtlases implements MinecraftAtlases {
    @Inject
    public VersionedMinecraftAtlases() {
    }

    public TextureAtlas getGuiAtlas() {
        return Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AtlasIds.GUI);
    }
}
