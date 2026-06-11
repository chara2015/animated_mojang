package net.labymod.v1_20_1.client.chat;

import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/chat/VersionedChatMessageCharSequence.class */
public class VersionedChatMessageCharSequence implements aom {
    private final ChatMessage message;
    private final aom wrapped;

    public VersionedChatMessageCharSequence(ChatMessage message, aom wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }

    public boolean accept(aon sink) {
        return this.wrapped.accept(sink);
    }

    public ChatMessage message() {
        return this.message;
    }
}
