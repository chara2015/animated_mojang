package net.labymod.v1_16_5.client.chat;

import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/chat/VersionedChatMessageComponent.class */
public class VersionedChatMessageComponent extends oe {
    private final ChatMessage message;

    public /* bridge */ /* synthetic */ nn d() {
        return super.i();
    }

    public /* bridge */ /* synthetic */ nx g() {
        return super.i();
    }

    public VersionedChatMessageComponent(ChatMessage message, nr wrapped) {
        super("");
        this.message = message;
        super.a(wrapped);
    }

    public ChatMessage message() {
        return this.message;
    }
}
