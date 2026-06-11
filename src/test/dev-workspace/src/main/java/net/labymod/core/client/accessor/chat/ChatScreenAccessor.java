package net.labymod.core.client.accessor.chat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/accessor/chat/ChatScreenAccessor.class */
public interface ChatScreenAccessor {
    void insertChatText(String str, boolean z, boolean z2);

    String getChatText();

    default void insertChatText(String text, boolean override) {
        insertChatText(text, override, false);
    }
}
