package net.labymod.v1_21_10.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/chat/VersionedChatMessageComponent.class */
public class VersionedChatMessageComponent extends ym {
    private final ChatMessage message;

    public VersionedChatMessageComponent(ChatMessage message, xx wrapped) {
        super(zd.b, List.of(wrapped), yv.a);
        this.message = message;
    }

    public ChatMessage message() {
        return this.message;
    }
}
