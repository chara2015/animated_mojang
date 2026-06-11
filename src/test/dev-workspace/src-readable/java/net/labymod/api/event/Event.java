package net.labymod.api.event;

import net.labymod.api.event.method.SubscribeMethod;
import org.jetbrains.annotations.ApiStatus;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/Event.class */
public interface Event {
    static void interruptUpcomingListeners() {
        throw EventInterruptException.INSTANCE;
    }

    @ApiStatus.Internal
    default void preInvoke(SubscribeMethod method) {
    }

    @ApiStatus.Internal
    default void postInvoke(SubscribeMethod method) {
    }
}
