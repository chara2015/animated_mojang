package net.labymod.api.event.labymod.labyconnect.session.chat;

import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.labyconnect.protocol.model.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/labyconnect/session/chat/LabyConnectChatMessageEvent.class */
public class LabyConnectChatMessageEvent extends LabyConnectChatEvent {
    private final ChatMessage message;

    public LabyConnectChatMessageEvent(LabyConnect api, Chat chat, ChatMessage message) {
        super(api, chat);
        this.message = message;
    }

    public ChatMessage message() {
        return this.message;
    }
}
