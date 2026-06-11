package net.labymod.v1_21_8.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/chat/VersionedChatMessageComponent.class */
public class VersionedChatMessageComponent extends yc {
    private final ChatMessage message;

    public VersionedChatMessageComponent(ChatMessage message, xo wrapped) {
        super(yv.c, List.of(wrapped), yl.a);
        this.message = message;
    }

    public ChatMessage message() {
        return this.message;
    }
}
