package net.labymod.api.labyconnect.protocol.model.chat;

import java.util.List;
import java.util.UUID;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.metadata.Metadata;
import net.labymod.api.util.time.TimeUtil;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/labyconnect/protocol/model/chat/Chat.class */
public interface Chat {
    UUID getId();

    List<ChatMessage> getMessages();

    List<User> getParticipants();

    boolean isTyping();

    void sendMessage(String str);

    void sendFile(String str, byte[] bArr);

    void sendImage(GameImage gameImage) throws Exception;

    void addMessage(User user, String str, long j, Metadata metadata);

    void addParticipant(User user);

    void removeParticipant(User user);

    Component title();

    Icon icon();

    int getUnreadCount();

    String getInputMessage();

    void setInputMessage(String str);

    @Nullable
    ChatMessage getLastMessage();

    void openChat();

    default void addMessage(User sender, String message) {
        addMessage(sender, message, TimeUtil.getCurrentTimeMillis());
    }

    default void addMessage(User sender, String message, long timestamp) {
        addMessage(sender, message, timestamp, null);
    }

    @Deprecated
    default void addIncomingMessage(User sender, String message, long timestamp, Metadata metadata) {
        addMessage(sender, message, timestamp, metadata);
    }
}
