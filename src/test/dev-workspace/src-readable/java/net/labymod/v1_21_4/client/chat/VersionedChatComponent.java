package net.labymod.v1_21_4.client.chat;

import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/chat/VersionedChatComponent.class */
public interface VersionedChatComponent {
    List<fld> getMessages();

    List<a> getFormattedMessages();

    void injectFormattedMessages(int i, wp wpVar, fld fldVar);

    void setMessageMeta(wp wpVar, ChatMessageMeta chatMessageMeta);

    void clearMessageMeta(wp wpVar);
}
