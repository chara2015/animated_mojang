package net.labymod.core.main.user.group.tag;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.entity.player.tag.tags.IconTag;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.render.state.EntityExtraKeys;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.client.render.state.entity.GameUserSnapshot;
import net.labymod.api.user.group.Group;
import net.labymod.api.user.group.GroupDisplayType;
import net.labymod.core.main.user.serverfeature.DefaultServerFeatureService;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/group/tag/GroupIconTag.class */
public class GroupIconTag extends IconTag {
    private final DefaultServerFeatureService serverFeatureService;
    private final Icon wolfIcon;
    private final Icon groupIcon;

    @Nullable
    private Group group;

    public GroupIconTag() {
        super(9.0f);
        this.serverFeatureService = (DefaultServerFeatureService) Laby.references().serverFeatureService();
        this.wolfIcon = Textures.SpriteLabyMod.DEFAULT_WOLF_HIGH_RES;
        this.groupIcon = Textures.SpriteLabyMod.WHITE_WOLF_HIGH_RES;
    }

    @Override // net.labymod.api.client.entity.player.tag.tags.IconTag, net.labymod.api.client.entity.player.tag.renderer.AbstractTagRenderer, net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public void begin(EntitySnapshot snapshot) {
        this.group = getVisibleGroup(snapshot);
        super.begin(snapshot);
    }

    @Override // net.labymod.api.client.entity.player.tag.tags.IconTag, net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public boolean isVisible() {
        return this.group != null && super.isVisible() && this.serverFeatureService.get().isUserIndicatorVisible();
    }

    @Override // net.labymod.api.client.entity.player.tag.tags.IconTag
    public int getColor(EntitySnapshot snapshot) {
        Group group = this.group;
        if (group != null) {
            return group.getColor().getRGB();
        }
        return super.getColor(snapshot);
    }

    @Override // net.labymod.api.client.entity.player.tag.tags.IconTag
    public Icon getIcon(EntitySnapshot snapshot) {
        if (this.group == null) {
            return this.wolfIcon;
        }
        if (this.group.isDefault()) {
            return this.wolfIcon;
        }
        return this.groupIcon;
    }

    private Group getVisibleGroup(EntitySnapshot snapshot) {
        if (!snapshot.has(EntityExtraKeys.GAME_USER)) {
            return null;
        }
        GameUserSnapshot userSnapshot = (GameUserSnapshot) snapshot.get(EntityExtraKeys.GAME_USER);
        if (!userSnapshot.isUsingLabyMod()) {
            return null;
        }
        Group visibleGroup = userSnapshot.group();
        if (userSnapshot.showUserIndicatorBesideNames()) {
            return visibleGroup;
        }
        if (visibleGroup.getDisplayType() == GroupDisplayType.BESIDE_NAME) {
            return visibleGroup;
        }
        return null;
    }
}
