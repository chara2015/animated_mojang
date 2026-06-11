package net.labymod.core.client.chat.advanced;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.client.chat.ChatMessage;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.configuration.labymod.chat.AdvancedChatMessage;
import net.labymod.api.configuration.labymod.chat.ChatTab;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.chat.advanced.AdvancedChatTabMessageEvent;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/advanced/ChatDuplicateMessageHandler.class */
@Singleton
public class ChatDuplicateMessageHandler {
    private static final String LAST_MESSAGE_KEY = "ChatDuplicateMessages-LastMessage";
    private static final String IGNORE_CHANGES_KEY = "ChatDuplicateMessages-IgnoreChanges";

    @Inject
    public ChatDuplicateMessageHandler() {
    }

    @Subscribe(126)
    public void handleDuplicateChatMessage(AdvancedChatTabMessageEvent event) {
        ChatTab tab = event.tab();
        if (!tab.config().combineChatMessages().get().booleanValue()) {
            return;
        }
        AdvancedChatMessage message = event.message();
        DuplicateMessage lastMessage = (DuplicateMessage) tab.metadata().get(LAST_MESSAGE_KEY);
        if (lastMessage != null) {
            ChatMessage chatMessage = message.chatMessage();
            ChatMessage lastChatMessage = lastMessage.message.chatMessage();
            boolean ignoreChanges = chatMessage.metadata().getBoolean(IGNORE_CHANGES_KEY, false);
            boolean lastIgnoreChanges = lastChatMessage.metadata().getBoolean(IGNORE_CHANGES_KEY, false);
            Component component = ignoreChanges ? chatMessage.originalComponent() : chatMessage.component();
            Component lastComponent = lastIgnoreChanges ? lastChatMessage.originalComponent() : lastChatMessage.component();
            if (component.equals(lastComponent)) {
                lastMessage.sentCount++;
                lastMessage.message.hideMessage();
                lastMessage.message = message;
                event.setMessage(Component.empty().append(message.component()).append(Component.space().append(Component.text("(" + lastMessage.sentCount + ")", NamedTextColor.GRAY))));
                return;
            }
        }
        tab.metadata().set(LAST_MESSAGE_KEY, new DuplicateMessage(message));
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/advanced/ChatDuplicateMessageHandler$DuplicateMessage.class */
    private static class DuplicateMessage {
        private AdvancedChatMessage message;
        private int sentCount = 1;

        private DuplicateMessage(AdvancedChatMessage message) {
            this.message = message;
        }
    }
}
