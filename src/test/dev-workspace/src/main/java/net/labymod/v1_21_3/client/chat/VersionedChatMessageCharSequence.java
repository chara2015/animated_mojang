package net.labymod.v1_21_3.client.chat;

import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/chat/VersionedChatMessageCharSequence.class */
public class VersionedChatMessageCharSequence implements azq {
    private final ChatMessage message;
    private final azq wrapped;

    public VersionedChatMessageCharSequence(ChatMessage message, azq wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }

    public boolean accept(azr sink) {
        return this.wrapped.accept(sink);
    }

    public ChatMessage message() {
        return this.message;
    }
}
