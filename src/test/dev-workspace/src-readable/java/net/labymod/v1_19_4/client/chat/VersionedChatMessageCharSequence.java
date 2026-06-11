package net.labymod.v1_19_4.client.chat;

import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/chat/VersionedChatMessageCharSequence.class */
public class VersionedChatMessageCharSequence implements aov {
    private final ChatMessage message;
    private final aov wrapped;

    public VersionedChatMessageCharSequence(ChatMessage message, aov wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }

    public boolean accept(aow sink) {
        return this.wrapped.accept(sink);
    }

    public ChatMessage message() {
        return this.message;
    }
}
