package net.labymod.v1_21_1.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/chat/VersionedChatMessageComponent.class */
public class VersionedChatMessageComponent extends xn {
    private final ChatMessage message;

    public VersionedChatMessageComponent(ChatMessage message, wz wrapped) {
        super(yg.c, List.of(wrapped), xw.a);
        this.message = message;
    }

    public ChatMessage message() {
        return this.message;
    }
}
