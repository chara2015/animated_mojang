package net.labymod.core.main.user.serverfeature;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.user.badge.ServerBadge;
import net.labymod.api.util.CountryCode;
import net.labymod.core.main.user.serverfeature.subtitle.SubtitleComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/serverfeature/UserServerFeature.class */
public class UserServerFeature {

    @NotNull
    private List<ServerBadge> badges = new ArrayList();

    @Nullable
    private SubtitleComponent subtitle;

    @Nullable
    private CountryCode countryCode;

    @NotNull
    public List<ServerBadge> getBadges() {
        return this.badges;
    }

    public void setBadges(@NotNull List<ServerBadge> badges) {
        this.badges = badges;
    }

    @Nullable
    public SubtitleComponent getSubtitle() {
        return this.subtitle;
    }

    public void setSubtitle(@Nullable SubtitleComponent subtitle) {
        this.subtitle = subtitle;
    }

    @Nullable
    public CountryCode getCountryCode() {
        return this.countryCode;
    }

    public void setCountryCode(@Nullable CountryCode countryCode) {
        this.countryCode = countryCode;
    }
}
