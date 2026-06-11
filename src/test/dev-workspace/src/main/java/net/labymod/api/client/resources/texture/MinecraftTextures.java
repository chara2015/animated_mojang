package net.labymod.api.client.resources.texture;

import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/resources/texture/MinecraftTextures.class */
public interface MinecraftTextures {
    ResourceLocation widgetsTexture();

    ResourceLocation barsTexture();

    ResourceLocation screenListBackgroundTexture();

    ResourceLocation screenMenuBackgroundTexture();

    ResourceLocation screenMenuHeaderSeparatorTexture();

    ResourceLocation screenMenuFooterSeparatorTexture();

    ResourceLocation iconsTexture();

    ResourceLocation serverSelectionTexture();

    ResourceLocation minecraftLogoTexture();

    ResourceLocation minecraftEditionTexture();

    ResourceLocation splashTexture();

    ResourceLocation[] panoramaTextures();

    ResourceLocation panoramaOverlayTexture();

    @Deprecated(forRemoval = true, since = "4.1.23")
    default ResourceLocation backgroundTexture() {
        return screenListBackgroundTexture();
    }
}
