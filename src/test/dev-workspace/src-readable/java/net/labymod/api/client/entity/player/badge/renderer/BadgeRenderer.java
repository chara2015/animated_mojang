package net.labymod.api.client.entity.player.badge.renderer;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.network.NetworkPlayerInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/badge/renderer/BadgeRenderer.class */
public abstract class BadgeRenderer {
    private boolean render = true;

    public abstract void render(ScreenContext screenContext, float f, float f2, NetworkPlayerInfo networkPlayerInfo);

    protected abstract boolean isVisible(NetworkPlayerInfo networkPlayerInfo);

    protected BadgeRenderer() {
    }

    public final boolean shouldRender(NetworkPlayerInfo playerInfo) {
        return this.render && isVisible(playerInfo);
    }

    public int getSize(NetworkPlayerInfo player) {
        return getSize();
    }

    public final boolean beginRender(ScreenContext context) {
        this.render = begin(context);
        return this.render;
    }

    protected boolean begin(ScreenContext context) {
        return true;
    }

    public final void endRender(ScreenContext context) {
        if (this.render) {
            end(context);
        }
    }

    protected void end(ScreenContext context) {
    }

    @Deprecated
    public int getSize() {
        return 9;
    }
}
