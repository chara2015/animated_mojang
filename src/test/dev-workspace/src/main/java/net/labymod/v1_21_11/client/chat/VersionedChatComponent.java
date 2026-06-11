package net.labymod.v1_21_11.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<gfc> getMessages();

    List<a> getFormattedMessages();

    void injectFormattedMessages(int i, yh yhVar, gfc gfcVar);

    void setMessageMeta(yh yhVar, ChatMessageMeta chatMessageMeta);

    void clearMessageMeta(yh yhVar);
}
