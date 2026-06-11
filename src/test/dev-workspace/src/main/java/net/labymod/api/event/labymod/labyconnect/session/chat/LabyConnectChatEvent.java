package net.labymod.api.event.labymod.labyconnect.session.chat;

import net.labymod.api.event.labymod.labyconnect.LabyConnectEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/chat/LabyConnectChatEvent.class */
public class LabyConnectChatEvent extends LabyConnectEvent {
    private final Chat chat;

    protected LabyConnectChatEvent(LabyConnect api, Chat chat) {
        super(api);
        this.chat = chat;
    }

    public Chat chat() {
        return this.chat;
    }
}
