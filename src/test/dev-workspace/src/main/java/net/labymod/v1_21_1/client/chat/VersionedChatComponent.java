package net.labymod.v1_21_1.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_1/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<fgi> getMessages();

    List<a> getFormattedMessages();

    void injectFormattedMessages(int i, wz wzVar, fgi fgiVar);

    void setMessageMeta(wz wzVar, ChatMessageMeta chatMessageMeta);

    void clearMessageMeta(wz wzVar);
}
