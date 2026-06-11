package net.labymod.v1_21_1.client.chat;

import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/chat/VersionedChatMessageCharSequence.class */
public class VersionedChatMessageCharSequence implements aya {
    private final ChatMessage message;
    private final aya wrapped;

    public VersionedChatMessageCharSequence(ChatMessage message, aya wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }

    public boolean accept(ayb sink) {
        return this.wrapped.accept(sink);
    }

    public ChatMessage message() {
        return this.message;
    }
}
