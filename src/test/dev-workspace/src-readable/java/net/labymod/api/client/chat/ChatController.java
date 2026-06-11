package net.labymod.api.client.chat;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.component.Component;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/ChatController.class */
@Referenceable
public interface ChatController {
    int getMaxHistorySize();

    List<ChatMessage> getMessages();

    @Deprecated
    ChatMessage addMessage(Component component);

    ChatMessage addMessage(ChatMessage.Builder builder);

    ChatMessage addMessage(ChatMessage chatMessage);

    void clear();

    ChatMessage messageAt(int i);

    void storeState();

    void restoreState();
}
