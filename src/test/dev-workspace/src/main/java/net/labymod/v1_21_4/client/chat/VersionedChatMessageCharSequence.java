package net.labymod.v1_21_4.client.chat;

import net.labymod.api.client.chat.ChatMessage;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/chat/VersionedChatMessageCharSequence.class */
public class VersionedChatMessageCharSequence implements ayl {
    private final ChatMessage message;
    private final ayl wrapped;

    public VersionedChatMessageCharSequence(ChatMessage message, ayl wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }

    public boolean accept(aym sink) {
        return this.wrapped.accept(sink);
    }

    public ChatMessage message() {
        return this.message;
    }
}
