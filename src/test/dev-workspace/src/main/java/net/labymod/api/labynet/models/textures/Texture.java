package net.labymod.api.labynet.models.textures;

import com.google.gson.annotations.SerializedName;
import java.util.Locale;
import net.labymod.api.Constants;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labynet/models/textures/Texture.class */
public class Texture {

    @SerializedName("image_hash")
    private String imageHash;
    private String tags;

    @SerializedName("use_count")
    private int useCount;

    public String getImageHash() {
        return this.imageHash;
    }

    public String getTags() {
        return this.tags;
    }

    public int getUseCount() {
        return this.useCount;
    }

    public Texture(String imageHash, String tags, int useCount) {
        this.imageHash = imageHash;
        this.tags = tags;
        this.useCount = useCount;
    }

    public Texture() {
    }

    public String getDownloadUrl() {
        return String.format(Locale.ROOT, Constants.Urls.LABYNET_TEXTURE_DOWNLOAD, getImageHash());
    }

    public String getPreviewUrl() {
        return String.format(Locale.ROOT, Constants.Urls.LABYNET_SKIN_PREVIEW, getImageHash());
    }
}
