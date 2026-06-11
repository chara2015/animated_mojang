package net.labymod.v1_19_4.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<emb> getMessages();

    List<a> getFormattedMessages();

    void injectFormattedMessages(int i, tj tjVar, emb embVar);

    void setMessageMeta(tj tjVar, ChatMessageMeta chatMessageMeta);

    void clearMessageMeta(tj tjVar);
}
