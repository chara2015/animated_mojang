package net.labymod.v1_21_10.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<fzs> getMessages();

    List<a> getFormattedMessages();

    void injectFormattedMessages(int i, xx xxVar, fzs fzsVar);

    void setMessageMeta(xx xxVar, ChatMessageMeta chatMessageMeta);

    void clearMessageMeta(xx xxVar);
}
