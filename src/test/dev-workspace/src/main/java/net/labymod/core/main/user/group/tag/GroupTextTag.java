package net.labymod.core.main.user.group.tag;

import java.util.Collections;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.tag.tags.ComponentNameTag;
import net.labymod.api.client.render.state.EntityExtraKeys;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.client.render.state.entity.GameUserSnapshot;
import net.labymod.core.main.user.serverfeature.DefaultServerFeatureService;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/group/tag/GroupTextTag.class */
public class GroupTextTag extends ComponentNameTag {
    private final DefaultServerFeatureService serverFeatureService = (DefaultServerFeatureService) Laby.references().serverFeatureService();

    @Override // net.labymod.api.client.entity.player.tag.tags.ComponentNameTag, net.labymod.api.client.entity.player.tag.renderer.AbstractTagRenderer, net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public void begin(EntitySnapshot snapshot) {
        super.begin(snapshot);
    }

    @Override // net.labymod.api.client.entity.player.tag.tags.ComponentNameTag, net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public boolean isVisible() {
        return super.isVisible() && this.serverFeatureService.get().isUserIndicatorVisible();
    }

    @Override // net.labymod.api.client.entity.player.tag.tags.AbstractNameTag, net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public float getScale() {
        return 0.5f;
    }

    @Override // net.labymod.api.client.entity.player.tag.tags.ComponentNameTag
    @NotNull
    protected List<Component> buildComponents(EntitySnapshot snapshot) {
        if (!snapshot.has(EntityExtraKeys.GAME_USER)) {
            return super.buildComponents(snapshot);
        }
        GameUserSnapshot userSnapshot = (GameUserSnapshot) snapshot.get(EntityExtraKeys.GAME_USER);
        Component component = userSnapshot.groupComponent();
        if (component == null) {
            return super.buildComponents(snapshot);
        }
        return Collections.singletonList(component);
    }
}
