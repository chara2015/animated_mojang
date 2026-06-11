package net.labymod.api.client.entity.player.tag.tags;

import net.labymod.api.client.entity.player.tag.renderer.AbstractTagRenderer;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/tags/AbstractNameTag.class */
public abstract class AbstractNameTag extends AbstractTagRenderer {
    protected AbstractNameTag() {
    }

    @Override // net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    public float getScale() {
        return 1.0f;
    }
}
