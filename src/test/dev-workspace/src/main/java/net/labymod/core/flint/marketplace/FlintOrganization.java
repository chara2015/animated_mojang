package net.labymod.core.flint.marketplace;

import com.google.gson.annotations.SerializedName;
import java.util.Locale;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.core.flint.FlintUrls;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/flint/marketplace/FlintOrganization.class */
public class FlintOrganization {
    private static final TextureRepository TEXTURE_REPOSITORY = Laby.references().textureRepository();
    private boolean trusted;

    @SerializedName("vanity_name")
    private String vanityName;

    @SerializedName("display_name")
    private String displayName;

    @SerializedName("icon_hash")
    private String iconHash;

    @SerializedName("flint_url")
    private String flintUrl;

    public FlintOrganization(String displayName) {
        this.displayName = displayName;
    }

    public Icon getIcon() {
        return Icon.completable(TEXTURE_REPOSITORY.loadCacheResourceAsync("labymod", this.iconHash, getIconUrl(), Textures.DEFAULT_SERVER_ICON));
    }

    public String getVanityName() {
        return this.vanityName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getFlintUrl() {
        return this.flintUrl;
    }

    public String getIconUrl() {
        return String.format(Locale.ROOT, FlintUrls.BRAND_ORGANIZATION_URL, this.iconHash);
    }

    public boolean isTrusted() {
        return this.trusted;
    }
}
