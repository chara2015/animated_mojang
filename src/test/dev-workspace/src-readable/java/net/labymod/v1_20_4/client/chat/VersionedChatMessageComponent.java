package net.labymod.v1_20_4.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/chat/VersionedChatMessageComponent.class */
public class VersionedChatMessageComponent extends vt {
    private final ChatMessage message;

    public VersionedChatMessageComponent(ChatMessage message, vf wrapped) {
        super(wm.c, List.of(wrapped), wc.a);
        this.message = message;
    }

    public ChatMessage message() {
        return this.message;
    }
}
