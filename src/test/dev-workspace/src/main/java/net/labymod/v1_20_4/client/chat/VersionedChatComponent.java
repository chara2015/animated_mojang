package net.labymod.v1_20_4.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<evc> getMessages();

    List<a> getFormattedMessages();

    void injectFormattedMessages(int i, vf vfVar, evc evcVar);

    void setMessageMeta(vf vfVar, ChatMessageMeta chatMessageMeta);

    void clearMessageMeta(vf vfVar);
}
