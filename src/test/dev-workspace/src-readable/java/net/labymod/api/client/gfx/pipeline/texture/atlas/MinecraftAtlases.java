package net.labymod.api.client.gfx.pipeline.texture.atlas;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/texture/atlas/MinecraftAtlases.class */
@Referenceable
public interface MinecraftAtlases {
    @Nullable
    default TextureAtlas getGuiAtlas() {
        return null;
    }
}
