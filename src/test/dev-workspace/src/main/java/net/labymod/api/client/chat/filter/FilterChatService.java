package net.labymod.api.client.chat.filter;

import java.util.List;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/filter/FilterChatService.class */
@Referenceable
public interface FilterChatService {
    ChatFilter filter(List<ChatFilter> list, ChatMessage chatMessage);
}
