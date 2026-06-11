package net.labymod.api.labynet.models.textures;

import com.google.gson.annotations.SerializedName;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.ThemeTextureLocation;
import net.labymod.api.client.session.MinecraftServices;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/textures/Skin.class */
public class Skin extends Texture {
    public static final ResourceLocation LOADING = ThemeTextureLocation.of("activities/playercustomization/loading_skin").resource();

    @SerializedName("skin_variant")
    private MinecraftServices.SkinVariant skinVariant;
    private transient Icon previewIcon;

    public Skin(MinecraftServices.SkinVariant skinVariant, String imageHash, String tags, int useCount) {
        super(imageHash, tags, useCount);
        this.skinVariant = skinVariant;
    }

    public Skin(MinecraftServices.SkinVariant skinVariant, String imageHash) {
        super(imageHash, "", 0);
        this.skinVariant = skinVariant;
    }

    public Skin() {
    }

    public MinecraftServices.SkinVariant skinVariant() {
        return this.skinVariant;
    }

    public Icon previewIcon() {
        if (this.previewIcon == null) {
            this.previewIcon = Icon.url(getPreviewUrl(), LOADING);
        }
        return this.previewIcon;
    }
}
