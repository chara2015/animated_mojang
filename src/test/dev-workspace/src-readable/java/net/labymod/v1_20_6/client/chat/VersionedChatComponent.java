package net.labymod.v1_20_6.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<ffb> getMessages();

    List<a> getFormattedMessages();

    void injectFormattedMessages(int i, xp xpVar, ffb ffbVar);

    void setMessageMeta(xp xpVar, ChatMessageMeta chatMessageMeta);

    void clearMessageMeta(xp xpVar);
}
