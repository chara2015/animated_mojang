package net.labymod.v1_21_11.client.resources.texture;

import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.AbstractMinecraftTextures;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/resources/texture/VersionedMinecraftTextures.class */
public class VersionedMinecraftTextures extends AbstractMinecraftTextures {
    private static final ResourceLocation GUI_ATLAS = ResourceLocation.create("minecraft", "textures/atlas/gui.png");

    public ResourceLocation widgetsTexture() {
        return GUI_ATLAS;
    }

    public ResourceLocation iconsTexture() {
        return GUI_ATLAS;
    }

    public ResourceLocation serverSelectionTexture() {
        return GUI_ATLAS;
    }

    public ResourceLocation barsTexture() {
        return GUI_ATLAS;
    }
}
