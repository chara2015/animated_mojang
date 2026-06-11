package net.labymod.core.client.entity.player.tag;

import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.renderer.TagRenderer;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.service.DefaultRegistry;
import net.labymod.api.util.KeyValue;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/tag/DefaultNameTagRegistry.class */
public class DefaultNameTagRegistry extends DefaultRegistry<TagRenderer> {
    private final PositionType positionType;

    public DefaultNameTagRegistry(PositionType positionType) {
        this.positionType = positionType;
    }

    public PositionType getPositionType() {
        return this.positionType;
    }

    public float getTotalOffsetX(EntitySnapshot snapshot) {
        float totalWidth = 0.0f;
        boolean first = true;
        for (KeyValue<TagRenderer> element : getElements()) {
            TagRenderer value = element.getValue();
            if (value.shouldCenterName()) {
                value.begin(snapshot);
                if (value.isVisible()) {
                    if (!first) {
                        totalWidth += 1.0f;
                    }
                    totalWidth += value.getWidth() * value.getScale();
                    first = false;
                }
            }
        }
        return (totalWidth / 2.0f) + 1.0f;
    }
}
