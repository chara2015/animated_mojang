package net.labymod.v1_20_6.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/client/chat/VersionedChatMessageComponent.class */
public class VersionedChatMessageComponent extends yd {
    private final ChatMessage message;

    public VersionedChatMessageComponent(ChatMessage message, xp wrapped) {
        super(yw.c, List.of(wrapped), ym.a);
        this.message = message;
    }

    public ChatMessage message() {
        return this.message;
    }
}
