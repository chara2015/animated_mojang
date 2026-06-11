package net.labymod.api.labyconnect.protocol.model.chat;

import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.metadata.MetadataExtension;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/protocol/model/chat/ChatMessage.class */
public interface ChatMessage extends MetadataExtension {
    @NotNull
    User sender();

    void markAsRead();

    boolean isRead();

    long getTimestamp();

    void deleteMessage();

    @NotNull
    Widget createWidget();
}
