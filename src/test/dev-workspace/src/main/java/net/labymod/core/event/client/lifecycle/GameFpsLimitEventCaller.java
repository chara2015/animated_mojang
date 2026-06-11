package net.labymod.core.event.client.lifecycle;

import net.labymod.api.Laby;
import net.labymod.api.event.client.lifecycle.GameFpsLimitEvent;
import net.labymod.api.volt.callback.InsertInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/event/client/lifecycle/GameFpsLimitEventCaller.class */
public final class GameFpsLimitEventCaller {
    private static final GameFpsLimitEvent GAME_FPS_LIMIT_EVENT = new GameFpsLimitEvent();

    public static void callEvent(InsertInfoReturnable<Integer> iir) {
        GAME_FPS_LIMIT_EVENT.setFramerateLimit(-1);
        Laby.fireEvent(GAME_FPS_LIMIT_EVENT);
        int limit = GAME_FPS_LIMIT_EVENT.getFramerateLimit();
        if (limit == -1) {
            return;
        }
        iir.setReturnValue(Integer.valueOf(limit));
    }
}
