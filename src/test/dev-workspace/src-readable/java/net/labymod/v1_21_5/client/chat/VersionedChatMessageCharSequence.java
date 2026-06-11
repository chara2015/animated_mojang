package net.labymod.v1_21_5.client.chat;

import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/chat/VersionedChatMessageCharSequence.class */
public class VersionedChatMessageCharSequence implements azk {
    private final ChatMessage message;
    private final azk wrapped;

    public VersionedChatMessageCharSequence(ChatMessage message, azk wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }

    public boolean accept(azl sink) {
        return this.wrapped.accept(sink);
    }

    public ChatMessage message() {
        return this.message;
    }
}
