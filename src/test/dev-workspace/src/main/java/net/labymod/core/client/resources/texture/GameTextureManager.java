package net.labymod.core.client.resources.texture;

import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/texture/GameTextureManager.class */
public interface GameTextureManager {
    boolean hasResource(ResourceLocation resourceLocation);

    void registerAndRelease(ResourceLocation resourceLocation, Object obj);
}
