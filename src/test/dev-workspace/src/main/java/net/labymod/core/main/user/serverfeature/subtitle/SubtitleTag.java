package net.labymod.core.main.user.serverfeature.subtitle;

import java.util.Collections;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.tag.tags.ComponentNameTag;
import net.labymod.api.client.render.state.EntityExtraKeys;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.client.render.state.entity.GameUserSnapshot;
import net.labymod.core.client.render.state.entity.DefaultGameUserSnapshot;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/serverfeature/subtitle/SubtitleTag.class */
public class SubtitleTag extends ComponentNameTag {
    private float scale;

    @Override // net.labymod.api.client.entity.player.tag.tags.AbstractNameTag, net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public float getScale() {
        return this.scale;
    }

    @Override // net.labymod.api.client.entity.player.tag.tags.ComponentNameTag
    @NotNull
    protected List<Component> buildComponents(EntitySnapshot snapshot) {
        if (!snapshot.has(EntityExtraKeys.GAME_USER)) {
            return super.buildComponents(snapshot);
        }
        GameUserSnapshot userSnapshot = (GameUserSnapshot) snapshot.get(EntityExtraKeys.GAME_USER);
        SubtitleComponent subtitle = ((DefaultGameUserSnapshot) userSnapshot).subtitle();
        this.scale = subtitle == null ? super.getScale() : (float) subtitle.getSubtitle().getSize();
        if (subtitle == null) {
            return super.buildComponents(snapshot);
        }
        return Collections.singletonList(subtitle.getComponent());
    }
}
