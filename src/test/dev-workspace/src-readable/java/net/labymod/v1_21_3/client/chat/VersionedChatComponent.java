package net.labymod.v1_21_3.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<flz> getMessages();

    List<a> getFormattedMessages();

    void injectFormattedMessages(int i, xv xvVar, flz flzVar);

    void setMessageMeta(xv xvVar, ChatMessageMeta chatMessageMeta);

    void clearMessageMeta(xv xvVar);
}
