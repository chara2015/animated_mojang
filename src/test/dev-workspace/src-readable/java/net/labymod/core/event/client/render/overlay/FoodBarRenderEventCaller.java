package net.labymod.core.event.client.render.overlay;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.render.overlay.FoodBarRenderEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/render/overlay/FoodBarRenderEventCaller.class */
public final class FoodBarRenderEventCaller {
    private FoodBarRenderEventCaller() {
    }

    public static boolean callPre(ScreenContext screenContext, Player player, int x, int y, int jitterOffset, int foodLevel, float saturationLevel) {
        FoodBarRenderEvent event = (FoodBarRenderEvent) Laby.fireEvent(new FoodBarRenderEvent(screenContext, Phase.PRE, player, x, y, jitterOffset, foodLevel, saturationLevel));
        return event.isCancelled();
    }

    public static void callPost(ScreenContext screenContext, Player player, int x, int y, int jitterOffset, int foodLevel, float saturationLevel) {
        Laby.fireEvent(new FoodBarRenderEvent(screenContext, Phase.POST, player, x, y, jitterOffset, foodLevel, saturationLevel));
    }
}
