package net.labymod.v1_17_1.client.chat;

import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/chat/VersionedChatMessageComponent.class */
public class VersionedChatMessageComponent extends pf {
    private final ChatMessage message;

    public /* bridge */ /* synthetic */ oo d() {
        return super.i();
    }

    public /* bridge */ /* synthetic */ oy g() {
        return super.i();
    }

    public VersionedChatMessageComponent(ChatMessage message, os wrapped) {
        super("");
        this.message = message;
        super.a(wrapped);
    }

    public ChatMessage message() {
        return this.message;
    }
}
