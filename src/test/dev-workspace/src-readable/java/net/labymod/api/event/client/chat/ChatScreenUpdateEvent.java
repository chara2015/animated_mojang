package net.labymod.api.event.client.chat;

import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/chat/ChatScreenUpdateEvent.class */
public class ChatScreenUpdateEvent implements Event {
    private final Reason reason;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/chat/ChatScreenUpdateEvent$Reason.class */
    public enum Reason {
        REFRESH_SCREEN,
        COLORS,
        ITEM_TOOLTIPS,
        VISIBILITY
    }

    public ChatScreenUpdateEvent(@NotNull Reason reason) {
        this.reason = reason;
    }

    @NotNull
    public Reason reason() {
        return this.reason;
    }
}
