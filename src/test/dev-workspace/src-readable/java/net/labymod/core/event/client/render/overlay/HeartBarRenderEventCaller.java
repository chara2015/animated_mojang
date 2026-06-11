package net.labymod.core.event.client.render.overlay;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.overlay.HeartBarRenderEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/overlay/HeartBarRenderEventCaller.class */
public final class HeartBarRenderEventCaller {
    private HeartBarRenderEventCaller() {
    }

    public static boolean callPre(ScreenContext screenContext, Player player, int x, int y, int rowHeight, int offsetHeartIndex, int lowHealthJitter, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, boolean hardcore) {
        HeartBarRenderEvent event = (HeartBarRenderEvent) Laby.fireEvent(new HeartBarRenderEvent(screenContext, Phase.PRE, player, x, y, rowHeight, offsetHeartIndex, lowHealthJitter, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore));
        return event.isCancelled();
    }

    public static void callPost(ScreenContext screenContext, Player player, int x, int y, int rowHeight, int offsetHeartIndex, int lowHealthJitter, float maxHealth, int playerHealth, int displayHealth, int absorptionAmount, boolean renderHighlight, boolean hardcore) {
        Laby.fireEvent(new HeartBarRenderEvent(screenContext, Phase.POST, player, x, y, rowHeight, offsetHeartIndex, lowHealthJitter, maxHealth, playerHealth, displayHealth, absorptionAmount, renderHighlight, hardcore));
    }
}
