package net.labymod.v1_21_10.client.chat;

import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/chat/VersionedChatMessageCharSequence.class */
public class VersionedChatMessageCharSequence implements bes {
    private final ChatMessage message;
    private final bes wrapped;

    public VersionedChatMessageCharSequence(ChatMessage message, bes wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }

    public boolean accept(bet sink) {
        return this.wrapped.accept(sink);
    }

    public ChatMessage message() {
        return this.message;
    }
}
