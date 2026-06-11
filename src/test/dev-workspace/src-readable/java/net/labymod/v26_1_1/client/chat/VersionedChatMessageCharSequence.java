package net.labymod.v26_1_1.client.chat;

import net.labymod.api.client.chat.ChatMessage;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/client/chat/VersionedChatMessageCharSequence.class */
public class VersionedChatMessageCharSequence implements FormattedCharSequence {
    private final ChatMessage message;
    private final FormattedCharSequence wrapped;

    public VersionedChatMessageCharSequence(ChatMessage message, FormattedCharSequence wrapped) {
        this.message = message;
        this.wrapped = wrapped;
    }

    public boolean accept(FormattedCharSink sink) {
        return this.wrapped.accept(sink);
    }

    public ChatMessage message() {
        return this.message;
    }
}
