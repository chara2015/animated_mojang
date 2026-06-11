package net.labymod.api.client.entity.player.tag.tags;

import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.state.EntityExtraKeys;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.client.render.state.entity.GameUserSnapshot;
import net.labymod.api.util.CountryCode;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/tags/LanguageFlagTag.class */
public class LanguageFlagTag extends IconTag {
    public LanguageFlagTag() {
        super(5.0f, 3.5f);
    }

    @Override // net.labymod.api.client.entity.player.tag.tags.IconTag, net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public boolean isVisible() {
        return this.snapshot.has(EntityExtraKeys.GAME_USER) && ((GameUserSnapshot) this.snapshot.get(EntityExtraKeys.GAME_USER)).showCountryFlagBesideNames() && super.isVisible();
    }

    @Override // net.labymod.api.client.entity.player.tag.tags.IconTag
    public Icon getIcon(EntitySnapshot snapshot) {
        if (!snapshot.has(EntityExtraKeys.GAME_USER)) {
            return null;
        }
        GameUserSnapshot userSnapshot = (GameUserSnapshot) snapshot.get(EntityExtraKeys.GAME_USER);
        CountryCode countryCode = userSnapshot.countryCode();
        if (countryCode == null) {
            return null;
        }
        return countryCode.getIcon();
    }
}
