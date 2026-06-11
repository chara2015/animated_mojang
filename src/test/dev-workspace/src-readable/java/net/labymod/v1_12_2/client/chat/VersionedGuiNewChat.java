package net.labymod.v1_12_2.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/chat/VersionedGuiNewChat.class */
public interface VersionedGuiNewChat {
    List<bhx> getMessages();

    List<bhx> getFormattedMessages();

    void injectFormattedMessages(int i, hh hhVar, bhx bhxVar);

    void setChatVisibility(hh hhVar, b bVar);

    void clearChatVisibility(hh hhVar);
}
