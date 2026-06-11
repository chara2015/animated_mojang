package net.labymod.v1_18_2.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<dym<qk>> getMessages();

    List<dym<aiz>> getFormattedMessages();

    void injectFormattedMessages(int i, qk qkVar, dym<qk> dymVar);
}
