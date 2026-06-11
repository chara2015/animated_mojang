package net.labymod.core.client.chat;

import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.component.Component;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/InternalChatModifier.class */
@Referenceable
public interface InternalChatModifier {
    void fireChatClear(boolean z);

    void deleteMessage(ChatMessage chatMessage);

    void editMessage(ChatMessage chatMessage, Component component);
}
