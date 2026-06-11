package net.labymod.v1_18_2.client.chat;

import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/chat/VersionedChatMessageComponent.class */
public class VersionedChatMessageComponent extends qx {
    private final ChatMessage message;

    public /* bridge */ /* synthetic */ qg d() {
        return super.i();
    }

    public /* bridge */ /* synthetic */ qq g() {
        return super.i();
    }

    public VersionedChatMessageComponent(ChatMessage message, qk wrapped) {
        super("");
        this.message = message;
        super.a(wrapped);
    }

    public ChatMessage message() {
        return this.message;
    }
}
