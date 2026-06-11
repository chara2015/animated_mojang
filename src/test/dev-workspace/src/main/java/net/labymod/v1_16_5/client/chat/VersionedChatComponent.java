package net.labymod.v1_16_5.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<dju<nr>> getMessages();

    List<dju<afa>> getFormattedMessages();

    void injectFormattedMessages(int i, nr nrVar, dju<nr> djuVar);
}
