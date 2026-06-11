package net.labymod.api.client.entity.player.badge.renderer.stack;

import java.util.List;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.client.entity.player.badge.renderer.PositionRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.util.KeyValue;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/badge/renderer/stack/HorizontalStackRenderer.class */
public class HorizontalStackRenderer implements PositionRenderer {
    private final boolean stackToLeft;

    public HorizontalStackRenderer(boolean stackToLeft) {
        this.stackToLeft = stackToLeft;
    }

    @Override // net.labymod.api.client.entity.player.badge.renderer.PositionRenderer
    public int render(ScreenContext context, float x, float y, NetworkPlayerInfo player, List<KeyValue<BadgeRenderer>> badges) {
        int width = 0;
        if (this.stackToLeft) {
            x -= 8.0f;
        }
        for (KeyValue<BadgeRenderer> badge : badges) {
            BadgeRenderer renderer = badge.getValue();
            if (renderer.shouldRender(player)) {
                float xPosition = this.stackToLeft ? x - width : x + width;
                renderer.render(context, xPosition, y, player);
                width += renderer.getSize(player) + 1;
            }
        }
        return width;
    }

    @Override // net.labymod.api.client.entity.player.badge.renderer.PositionRenderer
    public int getWidth(NetworkPlayerInfo player, List<KeyValue<BadgeRenderer>> badges) {
        int width = 0;
        for (KeyValue<BadgeRenderer> badge : badges) {
            BadgeRenderer renderer = badge.getValue();
            if (renderer.shouldRender(player)) {
                width += renderer.getSize(player) + 1;
            }
        }
        return width;
    }
}
