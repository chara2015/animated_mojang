package net.labymod.api.event.client.chat;

import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/chat/ChatMessageSendEvent.class */
public class ChatMessageSendEvent extends DefaultCancellable implements Event {
    private static final String COMMAND_LITERAL = "/";
    private final String originalMessage;
    private String message;
    private String historyText;

    public ChatMessageSendEvent(@NotNull String message, boolean showInHistory) {
        this.originalMessage = message;
        this.message = message;
        if (showInHistory) {
            this.historyText = message;
        }
    }

    public String getOriginalMessage() {
        return this.originalMessage;
    }

    public boolean isOriginalMessageCommand() {
        return this.originalMessage.startsWith(COMMAND_LITERAL);
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isMessageCommand() {
        return this.message.startsWith(COMMAND_LITERAL);
    }

    public String getHistoryText() {
        return this.historyText;
    }

    public void hideInHistory() {
        this.historyText = null;
    }

    public void changeMessage(@NotNull String message) {
        changeMessage(message, message);
    }

    public void changeMessage(@NotNull String message, @Nullable String historyText) {
        this.message = message;
        this.historyText = historyText;
    }
}
