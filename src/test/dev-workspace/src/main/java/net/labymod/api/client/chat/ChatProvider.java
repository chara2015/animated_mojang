package net.labymod.api.client.chat;

import net.labymod.api.client.chat.autotext.AutoTextService;
import net.labymod.api.client.chat.filter.FilterChatService;
import net.labymod.api.client.chat.input.ChatInputRegistry;
import net.labymod.api.client.chat.prefix.ChatPrefixRegistry;
import net.labymod.api.configuration.labymod.AutoTextConfigAccessor;
import net.labymod.api.configuration.labymod.chat.ChatConfigAccessor;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/chat/ChatProvider.class */
@Referenceable
public interface ChatProvider {
    ChatController chatController();

    ChatInputRegistry chatInputService();

    FilterChatService filterChatService();

    AutoTextService autoTextService();

    AutoTextConfigAccessor autoTextConfigAccessor();

    ChatPrefixRegistry prefixRegistry();

    ChatConfigAccessor chatConfigAccessor();
}
