package net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat;

import java.util.List;
import java.util.Objects;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.labyconnect.protocol.model.chat.ChatMessage;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.util.ChatHistoryWidget;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/labyconnect/chat/LabyConnectChatWidget.class */
@AutoWidget
public class LabyConnectChatWidget extends ChatHistoryWidget {
    private static final long FIVE_MINUTES = 300000;
    private final Chat chat;

    public LabyConnectChatWidget(Chat chat, ListSession<Widget> listSession) {
        super(listSession);
        this.chat = chat;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        clearHistory();
        List<ChatMessage> messages = this.chat.getMessages();
        int i = messages.size() - 1;
        while (i >= 0) {
            ChatMessage message = messages.get(i);
            ChatMessage previousMessage = i > 0 ? messages.get(i - 1) : null;
            message.markAsRead();
            addToHistory(createWidget(message, previousMessage));
            i--;
        }
    }

    public void addMessage(ChatMessage message) {
        Widget widget = createWidget(message, getPreviousMessage(2));
        if (this.initialized) {
            addChildInitialized(0, widget);
        } else {
            addChild(0, widget);
        }
    }

    private ChatMessage getPreviousMessage(int offset) {
        List<ChatMessage> messages = this.chat.getMessages();
        if (messages.size() > 1) {
            return messages.get(messages.size() - offset);
        }
        return null;
    }

    private Widget createWidget(ChatMessage message, ChatMessage previousMessage) {
        boolean headerVisible = previousMessage == null || !Objects.equals(previousMessage.sender(), message.sender()) || message.getTimestamp() - previousMessage.getTimestamp() > FIVE_MINUTES;
        LabyConnectChatMessageWidget messageWidget = new LabyConnectChatMessageWidget(message);
        messageWidget.setHeaderVisible(headerVisible);
        return messageWidget;
    }
}
