package net.labymod.v1_17_1.client.chat;

import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/chat/VersionedChatMessageCharSequence.class */
public class VersionedChatMessageCharSequence implements ags {
    private final ChatMessage message;
    private final ags wrapped;

    public VersionedChatMessageCharSequence(ChatMessage message, ags wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }

    public boolean accept(agt sink) {
        return this.wrapped.accept(sink);
    }

    public ChatMessage message() {
        return this.message;
    }
}
