package net.labymod.api.client.entity.player.tag.renderer;

import net.labymod.api.client.render.state.entity.EntitySnapshot;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/tag/renderer/AbstractTagRenderer.class */
public abstract class AbstractTagRenderer implements TagRenderer {
    protected EntitySnapshot snapshot;

    @Override // net.labymod.api.client.entity.player.tag.renderer.TagRenderer
    @MustBeInvokedByOverriders
    public void begin(EntitySnapshot snapshot) {
        this.snapshot = snapshot;
    }
}
