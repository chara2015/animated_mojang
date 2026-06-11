package net.labymod.core.labyconnect.protocol.model.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.Attachment;
import net.labymod.api.labyconnect.protocol.model.chat.attachment.URIAttachment;
import net.labymod.api.metadata.Metadata;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.LabyConnectChatMessageContentWidget;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labyconnect/protocol/model/chat/DefaultTextChatMessage.class */
public class DefaultTextChatMessage extends DefaultChatMessage implements TextChatMessage {
    private String rawMessage;
    private String message;
    private final List<Attachment> attachments;

    public DefaultTextChatMessage(Chat chat, User sender, long timestamp, String message) {
        this(chat, sender, timestamp, message, null);
    }

    public DefaultTextChatMessage(Chat chat, User sender, long timestamp, String message, Metadata metadata) {
        super(chat, sender, timestamp);
        this.attachments = new ArrayList();
        if (metadata != null) {
            metadata(metadata);
        }
        updateMessage(message);
    }

    @Override // net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage
    public List<Attachment> getAttachments() {
        return this.attachments;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage
    public String getRawMessage() {
        return this.rawMessage;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage
    public String getMessage() {
        return this.message;
    }

    @Override // net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage
    public void updateMessage(String message) {
        if (Objects.equals(this.rawMessage, message)) {
            return;
        }
        this.rawMessage = message;
        this.message = message.trim();
        this.attachments.clear();
        try {
            List<URIAttachment> attachments = Laby.references().attachmentParser().parse(this.rawMessage);
            for (URIAttachment attachment : attachments) {
                if (attachment.shouldHideURI()) {
                    this.message = this.message.replace(attachment.getUrl(), "").trim();
                }
                this.attachments.add(attachment);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override // net.labymod.api.labyconnect.protocol.model.chat.ChatMessage
    @NotNull
    public Widget createWidget() {
        return new LabyConnectChatMessageContentWidget(this);
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.DefaultChatMessage
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefaultTextChatMessage that = (DefaultTextChatMessage) o;
        return Objects.equals(Long.valueOf(this.timestamp), Long.valueOf(that.timestamp)) && Objects.equals(this.message, that.message);
    }

    @Override // net.labymod.core.labyconnect.protocol.model.chat.DefaultChatMessage
    public int hashCode() {
        int result = super.hashCode();
        return (31 * result) + (this.message != null ? this.message.hashCode() : 0);
    }
}
