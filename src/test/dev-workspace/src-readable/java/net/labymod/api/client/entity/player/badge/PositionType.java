package net.labymod.api.client.entity.player.badge;

import java.util.List;
import java.util.Set;
import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.client.entity.player.badge.renderer.PositionRenderer;
import net.labymod.api.client.entity.player.badge.renderer.stack.HorizontalStackRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.util.CollectionHelper;
import net.labymod.api.util.KeyValue;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/badge/PositionType.class */
public enum PositionType {
    OVERWRITE_PING(new PositionRenderer() { // from class: net.labymod.api.client.entity.player.badge.renderer.positions.OverwritePingRenderer
        @Override // net.labymod.api.client.entity.player.badge.renderer.PositionRenderer
        public int render(ScreenContext context, float x, float y, NetworkPlayerInfo player, List<KeyValue<BadgeRenderer>> badges) {
            boolean rendered = false;
            for (KeyValue<BadgeRenderer> badge : badges) {
                BadgeRenderer renderer = badge.getValue();
                if (renderer.shouldRender(player)) {
                    renderer.render(context, x, y, player);
                    rendered = true;
                }
            }
            return rendered ? 8 : 0;
        }

        @Override // net.labymod.api.client.entity.player.badge.renderer.PositionRenderer
        public int getWidth(NetworkPlayerInfo player, List<KeyValue<BadgeRenderer>> badges) {
            for (KeyValue<BadgeRenderer> badge : badges) {
                BadgeRenderer renderer = badge.getValue();
                if (renderer.shouldRender(player)) {
                    return 8;
                }
            }
            return 0;
        }
    }),
    RIGHT_TO_NAME(new HorizontalStackRenderer(false)),
    LEFT_TO_NAME(new HorizontalStackRenderer(false));

    public static final Set<PositionType> VALUES = CollectionHelper.asUnmodifiableSet(values());
    private final PositionRenderer renderer;

    PositionType(PositionRenderer renderer) {
        this.renderer = renderer;
    }

    public PositionRenderer renderer() {
        return this.renderer;
    }

    public boolean isExpanding() {
        return this.renderer instanceof HorizontalStackRenderer;
    }
}
