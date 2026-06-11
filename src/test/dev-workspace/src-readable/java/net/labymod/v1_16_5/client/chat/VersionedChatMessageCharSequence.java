package net.labymod.v1_16_5.client.chat;

import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/chat/VersionedChatMessageCharSequence.class */
public class VersionedChatMessageCharSequence implements afa {
    private final ChatMessage message;
    private final afa wrapped;

    public VersionedChatMessageCharSequence(ChatMessage message, afa wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }

    public boolean accept(afb sink) {
        return this.wrapped.accept(sink);
    }

    public ChatMessage message() {
        return this.message;
    }
}
