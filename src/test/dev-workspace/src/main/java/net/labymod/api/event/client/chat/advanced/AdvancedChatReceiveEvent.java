package net.labymod.api.event.client.chat.advanced;

import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.event.client.chat.ChatReceiveEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/chat/advanced/AdvancedChatReceiveEvent.class */
public class AdvancedChatReceiveEvent extends ChatReceiveEvent {
    public AdvancedChatReceiveEvent(@NotNull ChatMessage message) {
        super(message);
    }
}
