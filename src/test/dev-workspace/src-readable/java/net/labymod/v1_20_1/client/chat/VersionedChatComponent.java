package net.labymod.v1_20_1.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<enh> getMessages();

    List<a> getFormattedMessages();

    void injectFormattedMessages(int i, sw swVar, enh enhVar);

    void setMessageMeta(sw swVar, ChatMessageMeta chatMessageMeta);

    void clearMessageMeta(sw swVar);
}
