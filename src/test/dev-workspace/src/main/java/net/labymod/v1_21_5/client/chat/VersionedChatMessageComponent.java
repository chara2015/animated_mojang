package net.labymod.v1_21_5.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/chat/VersionedChatMessageComponent.class */
public class VersionedChatMessageComponent extends xu {
    private final ChatMessage message;

    public VersionedChatMessageComponent(ChatMessage message, xg wrapped) {
        super(yn.c, List.of(wrapped), yd.a);
        this.message = message;
    }

    public ChatMessage message() {
        return this.message;
    }
}
