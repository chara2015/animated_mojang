package net.labymod.v1_17_1.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_17_1/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<dvk<os>> getMessages();

    List<dvk<ags>> getFormattedMessages();

    void injectFormattedMessages(int i, os osVar, dvk<os> dvkVar);
}
