package net.labymod.api.event.client.chat;

import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.event.client.chat.advanced.AdvancedChatTabMessageEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/chat/ChatTabMessageEvent.class */
@Deprecated
public class ChatTabMessageEvent extends AdvancedChatTabMessageEvent {
    public ChatTabMessageEvent(@NotNull ChatTab tab, @NotNull AdvancedChatMessage originalMessage) {
        super(tab, originalMessage);
    }
}
