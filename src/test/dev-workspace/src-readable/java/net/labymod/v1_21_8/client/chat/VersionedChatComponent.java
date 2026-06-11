package net.labymod.v1_21_8.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_8/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<ftx> getMessages();

    List<a> getFormattedMessages();

    void injectFormattedMessages(int i, xo xoVar, ftx ftxVar);

    void setMessageMeta(xo xoVar, ChatMessageMeta chatMessageMeta);

    void clearMessageMeta(xo xoVar);
}
