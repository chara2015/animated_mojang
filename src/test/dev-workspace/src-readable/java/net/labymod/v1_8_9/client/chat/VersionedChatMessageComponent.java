package net.labymod.v1_8_9.client.chat;

import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/client/chat/VersionedChatMessageComponent.class */
public class VersionedChatMessageComponent extends fa {
    private final ChatMessage message;

    public /* bridge */ /* synthetic */ eu f() {
        return super.h();
    }

    public VersionedChatMessageComponent(ChatMessage message, eu wrapped) {
        super("");
        this.message = message;
        super.a(wrapped);
    }

    public ChatMessage message() {
        return this.message;
    }
}
