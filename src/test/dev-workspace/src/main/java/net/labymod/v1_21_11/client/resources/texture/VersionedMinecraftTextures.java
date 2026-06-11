package net.labymod.v1_21_11.client.resources.texture;

import net.labymod.api.Namespaces;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.AbstractMinecraftTextures;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/resources/texture/VersionedMinecraftTextures.class */
public class VersionedMinecraftTextures extends AbstractMinecraftTextures {
    private static final ResourceLocation GUI_ATLAS = ResourceLocation.create(Namespaces.MINECRAFT, "textures/atlas/gui.png");

    @Override // net.labymod.api.client.resources.texture.AbstractMinecraftTextures, net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation widgetsTexture() {
        return GUI_ATLAS;
    }

    @Override // net.labymod.api.client.resources.texture.AbstractMinecraftTextures, net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation iconsTexture() {
        return GUI_ATLAS;
    }

    @Override // net.labymod.api.client.resources.texture.AbstractMinecraftTextures, net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation serverSelectionTexture() {
        return GUI_ATLAS;
    }

    @Override // net.labymod.api.client.resources.texture.AbstractMinecraftTextures, net.labymod.api.client.resources.texture.MinecraftTextures
    public ResourceLocation barsTexture() {
        return GUI_ATLAS;
    }
}
