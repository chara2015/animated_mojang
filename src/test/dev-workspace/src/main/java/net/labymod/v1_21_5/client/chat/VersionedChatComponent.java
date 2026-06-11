package net.labymod.v1_21_5.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_5/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<fqj> getMessages();

    List<a> getFormattedMessages();

    void injectFormattedMessages(int i, xg xgVar, fqj fqjVar);

    void setMessageMeta(xg xgVar, ChatMessageMeta chatMessageMeta);

    void clearMessageMeta(xg xgVar);
}
