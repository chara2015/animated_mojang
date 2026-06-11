package net.labymod.api.event.client.chat;

import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/chat/ChatClearEvent.class */
public class ChatClearEvent extends DefaultCancellable implements Event {
    private final boolean clearHistory;

    public ChatClearEvent(boolean clearHistory) {
        this.clearHistory = clearHistory;
    }

    public boolean isClearHistory() {
        return this.clearHistory;
    }
}
