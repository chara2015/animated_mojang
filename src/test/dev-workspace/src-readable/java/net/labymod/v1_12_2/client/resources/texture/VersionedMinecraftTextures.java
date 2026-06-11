package net.labymod.v1_12_2.client.resources.texture;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.AbstractMinecraftTextures;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/resources/texture/VersionedMinecraftTextures.class */
public class VersionedMinecraftTextures extends AbstractMinecraftTextures {
    private final ResourceLocation SPLASH = resource("textures/gui/", "title/mojang.png");

    @Override // net.labymod.api.client.resources.texture.AbstractMinecraftTextures, net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation panoramaOverlayTexture() {
        return null;
    }

    @Override // net.labymod.api.client.resources.texture.AbstractMinecraftTextures, net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation splashTexture() {
        return this.SPLASH;
    }
}
